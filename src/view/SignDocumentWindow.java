package view;

import logic.RSACrypt;
import logic.SimpleHash;
import view.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SignDocumentWindow extends JFrame {
    private MainWindow parentWindow;

    private File privateKeyPath;
    private JTextField privateKeyPathTextField;
    private JButton privateKeyPathButton;

    private File documentPath;
    private JTextField documentPathTextField;
    private JButton documentPathButton;
    private JTextArea messageTextArea;
    private JCheckBox checkBox;

    private JButton generateSignButton;

    public SignDocumentWindow(MainWindow parentWindow) {
        super("Wygeneruj podpis");
        this.parentWindow = parentWindow;

        setLayout(new FlowLayout(FlowLayout.RIGHT));

        privateKeyPath = null;
        documentPath = null;

        JLabel privateKeyPathLabel = new JLabel("Ścieżka do klucza prywatnego:");
        privateKeyPathTextField = new JTextField(50);
        privateKeyPathLabel.setVisible(true);

        privateKeyPathButton = new JButton("Wybierz ścieżkę");
        privateKeyPathButton.setVisible(true);

        JLabel documentPathLabel = new JLabel("Ścieżka do dokumentu:");
        documentPathTextField = new JTextField(50);
        documentPathTextField.setVisible(true);

        documentPathButton = new JButton("Wybierz ścieżkę");
        documentPathButton.setVisible(true);

        checkBox = new JCheckBox("Dokument z pliku", true);
        checkBox.setVisible(true);

        JLabel messageLabel = new JLabel("Wiadomość:");
        messageTextArea = new JTextArea(24, 83);

        generateSignButton = new JButton("Wygeneruj podpis");

        JPanel privateKeyPathPanel = new JPanel();
        privateKeyPathPanel.add(privateKeyPathLabel);
        privateKeyPathPanel.add(privateKeyPathTextField);
        privateKeyPathPanel.add(privateKeyPathButton);
        getContentPane().add(privateKeyPathPanel);

        JPanel documentPathPanel = new JPanel();
        getContentPane().add(documentPathPanel);
        documentPathPanel.add(documentPathLabel);
        documentPathPanel.add(documentPathTextField);
        documentPathPanel.add(documentPathButton);

        this.add(checkBox);
        this.add(messageLabel);
        this.add(messageTextArea);

        this.add(generateSignButton);

        this.pack();
        this.setSize(1000, 700);
        this.setResizable(false);

        this.setVisible(true);

        initCallbacks();
    }

    private void initCallbacks() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                parentWindow.clearSignDocumentsWindow();
            }
        });

        privateKeyPathButton.addActionListener(actionEvent -> {
            privateKeyPath = FileHelper.setPath(privateKeyPathTextField,
                    this,
                    false,
                    true);
        });

        documentPathButton.addActionListener(actionEvent -> {
            documentPath = FileHelper.setPath(documentPathTextField, this, false, false);
        });

        generateSignButton.addActionListener(actionEvent -> {
            if ((privateKeyPath == null) || (documentPath == null && checkBox.isSelected())) {
                JOptionPane.showMessageDialog(this, "Wypełnij wszystkie pola!");
                return;
            }

            String key = null;
            try {
                key = Files.readString(privateKeyPath.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] docContent = null;

            if (checkBox.isSelected()) {
                try {
                    docContent = Files.readAllBytes(documentPath.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                docContent = messageTextArea.getText().getBytes();

                documentPath = new File(Paths.get(privateKeyPath.toString()).getParent() + "/important_message.txt");

                try (BufferedWriter br = new BufferedWriter(new FileWriter(documentPath))) {
                    br.write(new String(docContent));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            var hasher = new SimpleHash();
            var docContentHash = hasher.computeHash(docContent);

            var cert = RSACrypt.crypt(docContentHash, key);
            var certFile = documentPath.toString().concat(".cert");

            try (BufferedWriter br = new BufferedWriter(new FileWriter(certFile))) {
                br.write(cert);
            } catch (IOException e) {
                e.printStackTrace();
            }

            var msg = "Wygenerowano podpis.\n";

            if (!checkBox.isSelected()) {
                msg += "Plik z wiadomością oraz podpisem znajduje się w tej samej lokalizacji\n" + "co klucz prywatny.";
            } else {
                msg += "Znajduje się on w tej samej lokalizacji co wybrany plik dokumentu.";
            }

            JOptionPane.showMessageDialog(this, msg);
        });

    }
}
