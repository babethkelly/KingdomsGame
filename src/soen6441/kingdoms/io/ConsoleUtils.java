/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Utility for console based inputs
 */
public class ConsoleUtils {

    /**
     * Prompt user for an integer
     * @param def Default value
     * @return user entered integer
     */
    public static int promptUserInteger(int def) {
        int returnVal = def;
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            String input = bufferRead.readLine();
            try {
                returnVal = Integer.parseInt(input);
            } catch (NumberFormatException nfe) {
                System.out.println("Warning: Default to " + def);
            }
        } catch (IOException ioe) {
            System.out.println("Warning: Default to " + def);
        }
        return returnVal;
    }

    /**
     * Prompt user for a string
     * @param def Default value
     * @return user entered string
     */
    public static String promptUserString(String def) {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            String input = bufferRead.readLine();
            if (input.length() == 0 || input.charAt(0) == '\n') {
                return def;
            }
            return input;
        } catch (IOException ioe) {
            System.out.println("Warning: " + ioe);
        }
        return def;
    }    
}
