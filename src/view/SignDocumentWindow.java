package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SignDocumentWindow extends JFrame {
    private MainWindow parentWindow;

    private JLabel privateKeyPathLabel;
    private JTextField privateKeyPathTextField;
    private JButton privateKeyPathButton;

    private JLabel documentPathLabel;
    private JTextField documentPathTextField;
    private JButton documentPathButton;

    private JLabel messageLabel;
    private JTextArea messageTextArea;

    public SignDocumentWindow(MainWindow parentWindow) {
        super("Wygeneruj podpis");
        this.parentWindow = parentWindow;

        privateKeyPathLabel = new JLabel("Ścieżka do klucza prywatnego:");
        privateKeyPathTextField = new JTextField(50);
        privateKeyPathLabel.setVisible(true);

        privateKeyPathButton = new JButton("Wybierz ścieżkę");
        privateKeyPathButton.setVisible(true);

        documentPathLabel = new JLabel("Ścieżka do dokumentu:");
        documentPathTextField = new JTextField(50);
        documentPathTextField.setVisible(true);

        documentPathButton = new JButton("Wybierz ścieżkę");
        documentPathButton.setVisible(true);

        messageLabel = new JLabel("Wiadomość:");
        messageTextArea = new JTextArea(24, 100);
        messageTextArea.setVisible(true);

        this.setLayout(new FlowLayout(FlowLayout.RIGHT));

        this.add(privateKeyPathLabel);
        this.add(privateKeyPathTextField);
        this.add(privateKeyPathButton);

        this.add(documentPathLabel);
        this.add(documentPathTextField);
        this.add(documentPathButton);

        this.add(messageLabel);
        this.add(messageTextArea);

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
    }
}
