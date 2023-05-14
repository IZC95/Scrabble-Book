package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;

public class BloomFilter {

    BitSet by;
    String[]  algos;
    char[] index;
BloomFilter(int size, String... algs) {
    by = new BitSet(size);
    algos=new String[algs.length];
    algos=algs;
    index=new char[size];

}
void add(String s)
{
    for (String algo : algos)  {
        try {
            MessageDigest sr = MessageDigest.getInstance(algo);
            byte[] bytes= sr.digest(s.getBytes());
            BigInteger big=new BigInteger(bytes);
            int light=Math.abs((big.intValue()));
            light=light%(index.length);
            by.set(light,true);
            index[light]='1';

        }

        catch (NoSuchAlgorithmException | NullPointerException e) {

            System.out.println("Exception thrown : " + e);
        }
    }
}
Boolean contains(String s)
{
    for (String algo : algos)  {
        try {
            MessageDigest sr = MessageDigest.getInstance(algo);
            byte[] bytes= sr.digest(s.getBytes());
            BigInteger big=new BigInteger(bytes);
            int light=Math.abs((big.intValue()));
            light=light% (index.length);
            if(!by.get(light))
                return false;

        }
        catch (NoSuchAlgorithmException | NullPointerException e) {

            System.out.println("Exception thrown : " + e);
        }
}
    return true;
}

    @Override
    public String toString() {
     StringBuilder s=new StringBuilder();
     for(int i=0;i<by.length();i++)
         s.append((by.get(i))==true ? 1:0);
     String st=new String(s.toString());
     return st;
    }
}
