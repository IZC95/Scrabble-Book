package test;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Dictionary {

    CacheManager exist;
    CacheManager noExist;
    BloomFilter bf;
    IOSearcher ios;
    String[] files;
    Dictionary(String... filenames)
    {
        exist=new CacheManager(400,new LRU());
        noExist=new CacheManager(100,new LFU());
        bf=new BloomFilter(256,"MD5","SHA1");
        int c;
        BufferedReader reader=null;
        for (String st : filenames) {

            try {
                reader=new BufferedReader(new FileReader(st));
                String line;
                while ((line = reader.readLine()) != null) {
                    Scanner sc = new Scanner(line);
                    while (sc.hasNext()) {
                        bf.add(sc.next());
                    }

                }
                reader.close();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }

        files=filenames;

    }
    Boolean query(String s)
    {
        if(exist.query(s))
            return true;
        if(noExist.query(s))
            return false;
        if(bf.contains(s))
        {
            exist.add(s);
            return true;
        }
        else{
            noExist.add(s);
            return false;
        }
    }
    Boolean challenge(String s)
    {
        return IOSearcher.search(s,files);
    }
}
