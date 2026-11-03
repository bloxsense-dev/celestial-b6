import net.minecraft.client.main.Main;
import java.util.Arrays;

public class Start {
    public static void main(String[] args) {
        String[] defaultArgs = new String[] {
            "--version", "26.2",
            "--gameDir", "run",
            "--assetsDir", "run/assets",
            "--accessToken", "0"
        };
        String[] combinedArgs = concat(defaultArgs, args);
        Main.main(combinedArgs);
    }

    public static <T> T[] concat(T[] first, T[] second) {
        if (first == null) return second;
        if (second == null) return first;
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
