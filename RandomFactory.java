import java.util.Random;

/**
 * Factory class that encapsulates java.util.Random entirely within the factory.
 */
public class RandomFactory {
    private static Random rand = new Random();

    /**
     * Returns a random integer up to the specified bound.
     */
    public static int nextInt(int bound) {
        return rand.nextInt(bound);
    }

    /**
     * Returns a random double between 0.0 and 1.0.
     */
    public static double nextDouble() {
        return rand.nextDouble();
    }
}
