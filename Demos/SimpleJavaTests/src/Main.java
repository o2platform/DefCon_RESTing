import java.beans.EventHandler;
import java.io.*;
import java.util.Set;
import java.util.TreeSet;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        ProcessBuilder processBuilder = new ProcessBuilder("ipconfig");
        processBuilder.start();
        File file = new File("_test.txt");
        processBuilder.redirectOutput(file);
        processBuilder.start();

        System.out.println("test");
    }


    public static void main_2(String[] args) throws FileNotFoundException {
        System.out.println("starting notepad");
        Set<Comparable> set = new TreeSet<Comparable>();
        set.add("foo");
        set.add(EventHandler.create(Comparable.class,
                new FileOutputStream("test 2.txt"),"close"));
                //new ProcessBuilder("notepad", "c:\\windows\\win.ini"), "start"));

        //EventHandler.create(Comparable.class, new ProcessBuilder("notepad", "c:\\windows\\win.ini"), "start").toString();
    }
}
