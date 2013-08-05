package com.company;

import javax.swing.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class MainOriginal {

        public static void main_2(String[] args)
        {
            //try
            //{
            //XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Test.xml")));
            //XMLEncoder e = new XMLEncoder(outputStream);

            //JButton jb = new JButton();
            //jb.setText("Hello, world");
            /*JButton jb = new JButton("Test");
            jb.setName("SomeName");
            */
            //RuntimeUtil ru = new RuntimeUtil();
            //String[] sa =  {"c:\\Windows\\System32\\calc.exe"};
            //ru.execute(sa);
            //e.writeObject(new JButton("Hello, world"));
            //Runtime rt = Runtime.getRuntime();
            //e.writeObject(rt);
            //e.writeObject(ru);
            //e.close();

            XMLDecoder d = null;
            try {
                d = new XMLDecoder(
                    new BufferedInputStream(
                            new FileInputStream("Test2.xml")));
                            //new FileInputStream("TestGroovyShell.xml")));
            } catch (FileNotFoundException fio) {
                ClassLoader cl = MainOriginal.class.getClassLoader();
                d = new XMLDecoder(
                        new BufferedInputStream(
                                cl.getResourceAsStream("com/company/Test2.xml")));
            }
            Object result = d.readObject();
            d.close();

      /*  }  catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        */
    }
}
