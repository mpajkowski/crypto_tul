package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import logic.RSAKeyGenerator;

public class GenerateKeysWindow extends JFrame {
    private MainWindow parentWindow;
    private JLabel pathLabel;
    private JTextField pathTextField;

    private JButton selectPathButton;
    private JButton generateKeysButton;

    public GenerateKeysWindow(MainWindow parentWindow) {
        super("Generuj klucze");
        this.parentWindow = parentWindow;

        pathLabel = new JLabel("Ścieżka:");
        pathTextField = new JTextField(50);
        pathTextField.setVisible(true);

        selectPathButton = new JButton("Wybierz ścieżkę");
        selectPathButton.setVisible(true);
        generateKeysButton = new JButton("Generuj parę kluczy");
        generateKeysButton.setVisible(true);

        this.setSize(500, 70);
        this.setResizable(false);

        this.setLayout(new FlowLayout());

        this.add(pathLabel);
        this.add(pathTextField);
        this.add(selectPathButton);
        this.add(generateKeysButton);

        this.pack();
        this.setVisible(true);

        initCallbacks();
    }

    private void initCallbacks() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                parentWindow.clearGenerateKeysWindow();
            }
        });

        generateKeysButton.addActionListener(actionEvent -> {
            var rsaKeyGen = new RSAKeyGenerator();
            var keys = rsaKeyGen.generateKeys();
            System.out.println("public key:");
            System.out.println(keys.getPublicKey());
            System.out.println("private key");
            System.out.println(keys.getPrivateKey());
        });
    }
}
