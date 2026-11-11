import java.lang.System.Logger;
import java.lang.System.Logger.Level;

public class Log1 {
    private static final Logger LOG = System.getLogger(Log1.class.getName());

    public static void main(String[] args) {
        LOG.log(Level.DEBUG, "screens loaded in 12 ms");
        LOG.log(Level.INFO, "Box office started, screens loaded: {0}", 5);
        LOG.log(Level.WARNING, "{0} almost full: {1} seats left", "Screen 3", 4);
        try { Integer.parseInt("12a"); }
        catch (NumberFormatException e) { LOG.log(Level.ERROR, "invalid seat code", e); }
    }
}
