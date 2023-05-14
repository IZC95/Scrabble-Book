package test;


import java.util.HashSet;

public class CacheManager {
    HashSet<String> words;
    CacheReplacementPolicy crp;
    int capacity;

	 CacheManager(int size, CacheReplacementPolicy c)
    {
        words=new HashSet<String>(size);
        capacity=size;
        crp= c;

    }
	Boolean query(String s)
    {
        return words.contains(s);
    }
    void add(String s)
    {
        if (words.size() >= capacity) {
            String ev = crp.remove();
            words.remove(ev);
        }
        crp.add(s);
        words.add(s);

    }
}
