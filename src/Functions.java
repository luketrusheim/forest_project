import java.util.*;

public final class Functions {

    public static int getIntFromRange(int max, int min) {
        Random rand = new Random();
        return min + rand.nextInt(max-min);
    }

    public static double getNumFromRange(double max, double min) {
        Random rand = new Random();
        return min + rand.nextDouble() * (max - min);
    }


    public static int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }

}
