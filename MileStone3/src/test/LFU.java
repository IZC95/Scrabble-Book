package test;


import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Objects;

public class LFU implements CacheReplacementPolicy {

    HashMap<String,Integer> cache;
    HashMap<Integer, LinkedHashSet<String>> lists;
    int min=-1;
    int capacity;
    LFU()
    {
        cache=new HashMap<>();
        lists=new HashMap<>();
        lists.put(1,new LinkedHashSet<>());
    }

    public void add(String s)
    {
        if(!cache.containsKey(s)) {
            min = 1;
            lists.get(1).add(s);
            cache.put(s, 1);
        }
        else {
            int count=cache.get(s);
            lists.get(count).remove(s);
            cache.put(s,cache.get(s)+1);

            if(count==min&&lists.get(count).size()==0)
                min++;
            if(!lists.containsKey(count+1))
                lists.put(count+1,new LinkedHashSet<>());
            lists.get(count+1).add(s);

        }
    }
    public String remove() {
        String ev = lists.get(min).iterator().next();
        lists.get(min).remove(ev);
        cache.remove(ev);
        return ev;
    }
}
