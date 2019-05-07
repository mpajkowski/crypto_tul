package logic;

public class SimpleHash implements Hash {
    private static final int MAGIC = 0x9e3779b9;
    @Override
    public String computeHash(String message) {
        int seed = message.length();

        for (var elem: message.getBytes()) {
            seed ^= (int)elem + MAGIC + (seed << 6) + (seed >> 2);
        }

        return Integer.toString(seed);
    }
}
