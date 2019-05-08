package view.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileHelper {
    public static File setPath(JTextField pathTextField,
                               JFrame parentWindow,
                               boolean selectDir,
                               boolean filterByKey) {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));

        if (filterByKey) {
            fc.setFileFilter(new FileNameExtensionFilter("RSA key format", "key"));
        }

        if (selectDir) {
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }

        File path = null;
        if (fc.showOpenDialog(parentWindow) == JFileChooser.APPROVE_OPTION) {
            path = fc.getSelectedFile();
        }

        pathTextField.setText(path.toString());
        return path;
    }
}
