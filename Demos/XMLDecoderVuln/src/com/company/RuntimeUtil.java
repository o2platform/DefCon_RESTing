package com.company;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: abraham.kang
 * Date: 7/14/13
 * Time: 7:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class RuntimeUtil {

    public Process execute(String[] command) throws IOException {
        return Runtime.getRuntime().exec(command);
    }

    public static void anotherExecute(String partCommand) throws Exception {
        Runtime.getRuntime().exec(partCommand);
    }

}
