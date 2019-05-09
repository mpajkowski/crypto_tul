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

public class SignDocumentWindow extends JFrame {
    private MainWindow parentWindow;

    private File privateKeyPath;
    private JTextField privateKeyPathTextField;
    private JButton privateKeyPathButton;

    private File documentPath;
    private JTextField documentPathTextField;
    private JButton documentPathButton;

    private JButton generateSignButton;

    public SignDocumentWindow(MainWindow parentWindow) {
        super("Wygeneruj podpis");
        this.parentWindow = parentWindow;

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

        JLabel messageLabel = new JLabel("Wiadomość:");
        JTextArea messageTextArea = new JTextArea(24, 83);
        messageTextArea.setVisible(true);

        generateSignButton = new JButton("Wygeneruj podpis");

        this.setLayout(new FlowLayout(FlowLayout.RIGHT));

        this.add(privateKeyPathLabel);
        this.add(privateKeyPathTextField);
        this.add(privateKeyPathButton);

        this.add(documentPathLabel);
        this.add(documentPathTextField);
        this.add(documentPathButton);

        this.add(messageLabel);
        this.add(messageTextArea);

        this.add(generateSignButton);

        this.pack();
        this.setSize(950, 700);
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
            if (privateKeyPath == null || documentPath == null) {
                JOptionPane.showMessageDialog(this, "Wypełnij wszystkie pola!");
                return;
            }
            byte[] docContent = null;
            try {
                docContent = Files.readAllBytes(documentPath.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            String key = null;
            try {
                key = Files.readString(privateKeyPath.toPath());
            } catch (IOException e) {
                e.printStackTrace();
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

            JOptionPane.showMessageDialog(this,
                    "Wygenerowano podpis." +
                            "Znajduje się on w tej samej lokalizacji co wskazany plik.");
        });

    }
}
