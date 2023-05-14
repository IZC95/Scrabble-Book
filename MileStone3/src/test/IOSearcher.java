package test;


import java.io.*;
import java.util.Scanner;

public class IOSearcher {

     static Boolean search(String s, String... filenames) {
        int c;
        BufferedReader reader=null;
        for (String st : filenames) {

            try {
                reader=new BufferedReader(new FileReader(st));
                String line;
                while ((line = reader.readLine()) != null) {
                    Scanner sc = new Scanner(line);
                    while (sc.hasNext()) {
                        if (s.equals(sc.next())) {
                            reader.close();
                            return true;
                        }
                    }

                }
                reader.close();
            } catch(IOException ex) {
                System.out.println("Exception thrown : " + ex);
                return false;
            }
        }

        return false;
    }
}
