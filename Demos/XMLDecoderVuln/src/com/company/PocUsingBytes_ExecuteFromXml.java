package com.company;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PocUsingBytes_ExecuteFromXml
{
    public static String test(String value)
    {
        System.out.println(value);
        return "ok";
    }
    public static void main(String[] args) throws IOException
    {
        String className  = "com.company.PocUsingBytes";
        String methodName = "test";
        String firstParam = "From XML Decoder";

        test("From Java code");

        String xmlPayload = "<?xml version=\"1.0\"?><java>  " +
                            "<object class=\"" + className + "\" method=\""+ methodName + "\"> "+
                            "<string>" + firstParam + "</string>   " +
                            "</object></java>";
        ExecutefromXml(xmlPayload);

    }
    public static void ExecutefromXml(String xmlPayload)
    {
        byte[] bytes = xmlPayload.getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        XMLDecoder xmlDecoder = new XMLDecoder(inputStream);
        xmlDecoder.readObject();
    }
}
