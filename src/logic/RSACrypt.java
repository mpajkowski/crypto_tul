package logic;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class RSACrypt {
    public static String crypt(byte[] message, String key) {
        List<String> kAndN = Arrays.asList(key.split("-"));

        BigInteger messageNum = new BigInteger(message);

        BigInteger k = new BigInteger(kAndN.get(0));
        BigInteger n = new BigInteger(kAndN.get(1));

        return messageNum.modPow(k, n).toString();
    }
}
