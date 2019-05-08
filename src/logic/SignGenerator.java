package logic;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

public class SignGenerator {
    private Path path;

    public SignGenerator(Path path) {
        this.path = path;
    }

    public String generateCert(String publicKey) {
        byte[] fileContent = null;

        try {
            fileContent = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        var hasher = new SimpleHash();
        var hash = hasher.computeHash(fileContent);

        return RSACrypt.crypt(hash.getBytes(), publicKey);
    }
}
