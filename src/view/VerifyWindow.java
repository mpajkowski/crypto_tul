package view;

import view.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class VerifyWindow extends JFrame {
    private MainWindow parentWindow;

    private File publicKeyPath;
    private JTextField publicKeyPathTextField;
    private JButton publicKeyPathButton;

    private File documentPath;
    private JTextField documentPathTextField;
    private JButton documentPathButton;

    private File certPath;
    private JTextField certPathTextField;
    private JButton certPathButton;

    private JButton verifyCertButton;

    public VerifyWindow(MainWindow parentWindow) {
        super("Weryfikuj podpis");
        this.parentWindow = parentWindow;

        publicKeyPath = null;
        documentPath = null;

        JLabel publicKeyPathLabel = new JLabel("Ścieżka do klucza publicznego:");
        publicKeyPathTextField = new JTextField(50);
        publicKeyPathLabel.setVisible(true);

        publicKeyPathButton = new JButton("Wybierz ścieżkę");
        publicKeyPathButton.setVisible(true);

        JLabel documentPathLabel = new JLabel("Ścieżka do dokumentu:");
        documentPathTextField = new JTextField(50);
        documentPathTextField.setVisible(true);

        documentPathButton = new JButton("Wybierz ścieżkę");
        documentPathButton.setVisible(true);

        JLabel certPathLabel = new JLabel("Ścieżka do podpisu:");
        certPathTextField = new JTextField(50);
        certPathTextField.setVisible(true);

        certPathButton = new JButton("Wybierz ścieżkę");
        certPathButton.setVisible(true);

        verifyCertButton = new JButton("Weryfikuj podpis");
        verifyCertButton.setVisible(true);

        this.setLayout(new FlowLayout(FlowLayout.RIGHT));

        this.add(publicKeyPathLabel);
        this.add(publicKeyPathTextField);
        this.add(publicKeyPathButton);

        this.add(documentPathLabel);
        this.add(documentPathTextField);
        this.add(documentPathButton);

        this.add(certPathLabel);
        this.add(certPathTextField);
        this.add(certPathButton);

        this.add(verifyCertButton);
        this.pack();
        this.setSize(950, 700);

        this.setVisible(true);
        initCallbacks();
    }

    private void initCallbacks() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                parentWindow.clearVerifyWindow();
            }
        });

        // TODO
        publicKeyPathButton.addActionListener(actionEvent ->
                publicKeyPath = FileHelper.setPath(publicKeyPathTextField,
                        VerifyWindow.this,
                        false,
                        true));

        documentPathButton.addActionListener(actionEvent ->
                documentPath = FileHelper.setPath(documentPathTextField,
                        VerifyWindow.this,
                        false,
                        false));
        certPathButton.addActionListener(actionEvent ->
                certPath = FileHelper.setPath(certPathTextField,
                        VerifyWindow.this,
                        false,
                        false));

        verifyCertButton.addActionListener(actionEvent -> {
        });
    }
}
