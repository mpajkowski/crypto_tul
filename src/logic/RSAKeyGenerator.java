package logic;

import java.math.BigInteger;
import java.util.Random;

public class RSAKeyGenerator {
    static final int BITS = 128;
    static final int CERTAINTY = 100;

    private Random rng;

    public RSAKeyGenerator() {
        rng = new Random();
    }

    public KeyPair generateKeys() {
        BigInteger bigTwo = new BigInteger("2");
        BigInteger bigThree = new BigInteger("3");

        BigInteger p = new BigInteger(BITS, CERTAINTY, rng);
        BigInteger q = new BigInteger(BITS, CERTAINTY, rng);

        ensureInequality(p, q);

        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        BigInteger e = bigThree;
        while (!e.gcd(phi).equals(BigInteger.ONE)) {
            e = e.add(bigTwo);
        }

        BigInteger d = e.modInverse(phi);

        var publicKey = e.toString().concat("-").concat(n.toString());
        var privateKey = d.toString().concat("-").concat(n.toString());

        return new KeyPair(publicKey, privateKey);
    }

    private void ensureInequality(BigInteger num1, BigInteger num2) {
        while (num1.equals(num2)) {
            num1 = new BigInteger(BITS, CERTAINTY, rng);
            num2 = new BigInteger(BITS, CERTAINTY, rng);
        }
    }
}
