import java.beans.EventHandler;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
        System.out.println("starting notepad");
        /*Set<Comparable> set = new TreeSet<Comparable>();
        set.add("foo");
        set.add(EventHandler.create(Comparable.class, new
                ProcessBuilder("notepad", "c:\\windows\\win.ini"), "start"));*/
        EventHandler.create(Comparable.class, new ProcessBuilder("notepad", "c:\\windows\\win.ini"), "start").toString();
    }
}
