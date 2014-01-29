/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Utility for writing / reading files
 */
public class FileUtils {

    /**
     * Write string to a text file
     *
     * @param f File to be written
     * @param s String to be written
     */
    public static void writeTextFile(File f, String s) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(f);
            pw.println(s);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            pw.flush();
            pw.close();
        }

    }

    /**
     * Read string from a text file
     *
     * @param f File to be read
     * @return String being read, empty if not successful
     */
    public static String readTextFile(File f) {
        String result = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            result = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }
}
