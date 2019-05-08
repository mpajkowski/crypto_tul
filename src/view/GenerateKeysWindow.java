package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import logic.RSAKeyGenerator;

public class GenerateKeysWindow extends JFrame {
    private MainWindow parentWindow;
    private File path;
    private JTextField pathTextField;

    private JButton selectPathButton;
    private JButton generateKeysButton;

    public GenerateKeysWindow(MainWindow parentWindow) {
        super("Generuj klucze");
        this.parentWindow = parentWindow;

        path = null;

        JLabel pathLabel = new JLabel("Ścieżka:");
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

        selectPathButton.addActionListener(actionEvent -> {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File("."));
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setAcceptAllFileFilterUsed(false);

            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                path = fc.getSelectedFile();
            }
            pathTextField.setText(path.toString());
        });

        generateKeysButton.addActionListener(actionEvent -> {
            var rsaKeyGen = new RSAKeyGenerator();
            var keys = rsaKeyGen.generateKeys();

            var publicKeyFile = Paths.get(path.toString(), "public_rsa.key");
            var privateKeyFile = Paths.get(path.toString(), "private_rsa.key");

            try (BufferedWriter br = new BufferedWriter(new FileWriter(publicKeyFile.toFile()))) {
                br.write(keys.getPublicKey());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedWriter br = new BufferedWriter(new FileWriter(privateKeyFile.toFile()))) {
                br.write(keys.getPrivateKey());
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("public key:");
            System.out.println(keys.getPublicKey());
            System.out.println("private key");
            System.out.println(keys.getPrivateKey());
        });
    }
}
