import javax.swing.*;
import java.beans.*;
import java.io.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: o2
 * Date: 8/2/13
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class XmlEncodeTest
{
    public static void main(String[] args) throws IOException
    {
        PipedOutputStream outstr = new PipedOutputStream();
        PipedInputStream instr = new PipedInputStream(outstr);

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outstr);
        XMLEncoder xmlEncoder = new XMLEncoder(bufferedOutputStream);

        //Object obj = "A string 2";
        //Object obj = new ProcessBuilder("123");
        /*  //this will pop on XmlEncode
        Set<Comparable> set = new TreeSet<Comparable>();
        set.add("foo");
        set.add(EventHandler.create(Comparable.class, new
                ProcessBuilder("notepad", "c:\\windows\\win.ini"), "start"));*/
        //Object obj = new JButton[] { new JButton("Hello, world")};

        String file = "C:\\_Presentation\\DefCon_RESTing\\Demos\\SimpleJavaTests\\out\\production\\SimpleJavaTests\\com\\company\\RuntimeUtil_.class";
        FileInputStream fileInputStream = new FileInputStream(file);
        int available = fileInputStream.available();
        System.out.println(available);
        byte[] obj = new byte[200];
        fileInputStream.read(obj,0,20);
        //Object obj = "hello World".getBytes();
        xmlEncoder.writeObject(obj);

        xmlEncoder.close();

        int size = instr.available();
        byte[] bytes = new byte[size];
        instr.read(bytes,0, size);

        String result =  new String(bytes, "UTF-8");
        //return bytes.ascii();
        System.out.println("done");
        System.out.println(result);
    }
}
