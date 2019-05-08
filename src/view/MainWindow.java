package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {
    private JFrame generateKeysWindow;
    private JFrame signDocumentWindow;

    private JButton generateKeysButton;
    private JButton signDocumentButton;
    private JButton verifyButton;
    private VerifyWindow verifyWindow;

    public MainWindow() {
        super("RSA");
        this.setSize(500, 40);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        generateKeysWindow = null;
        signDocumentWindow = null;

        generateKeysButton = new JButton("Generuj klucze");
        generateKeysButton.setVisible(true);
        signDocumentButton = new JButton("Wygeneruj podpis");
        signDocumentButton.setVisible(true);
        verifyButton = new JButton("Weryfikuj podpis");
        verifyButton.setVisible(true);

        this.setLayout(new FlowLayout());
        this.add(generateKeysButton);
        this.add(signDocumentButton);
        this.add(verifyButton);

        this.setVisible(true);

        initCallbacks();
    }

    private void initCallbacks() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (generateKeysWindow == null
                        && signDocumentWindow == null
                        && verifyWindow == null) {
                    super.windowClosing(e);
                    System.exit(0);
                }
            }
        });

        generateKeysButton.addActionListener(actionEvent -> {
            if (generateKeysWindow == null) {
                generateKeysWindow = new GenerateKeysWindow(this);
            }
        });

        signDocumentButton.addActionListener(actionEvent -> {
            if (signDocumentWindow == null) {
                signDocumentWindow = new SignDocumentWindow(this);
            }
        });

        verifyButton.addActionListener(actionEvent -> {
            if (verifyWindow == null) {
                verifyWindow = new VerifyWindow(this);
            }
        });
    }

    void clearGenerateKeysWindow() {
        generateKeysWindow = null;
    }

    void clearSignDocumentsWindow() {
        signDocumentWindow = null;
    }

    void clearVerifyWindow() {
        verifyWindow = null;
    }
}
