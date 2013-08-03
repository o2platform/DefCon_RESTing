import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: o2
 * Date: 8/2/13
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class XmlDecodeTest
{
    public static void main(String[] args) throws IOException
    {

        String className  = "java.io.FileOutputStream";
        String value = "test.xml";

        String xmlPayload = "<?xml version=\"1.0\"?><java>  " +
                "<object class=\""+className+"\">" +


                "<string>" + value + "</string>" +
                //"<void method=\"write\"> " +

                "</object></java>";

        FileOutputStream object = (FileOutputStream)(new XMLDecoder(new ByteArrayInputStream(xmlPayload.getBytes())).readObject());
        System.out.println(object.getFD());
    }
}
