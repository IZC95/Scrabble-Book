package test;


import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Objects;

public class LRU implements CacheReplacementPolicy {
    LinkedHashSet<String> cache;



    public LRU() {

       cache=new LinkedHashSet<String>();
    }

    public void  add(String s) {

            if(!cache.contains(s))
                cache.add(s);
            else {
                cache.remove(s);
                cache.add(s);
            }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LRU lru = (LRU) o;
        return Objects.equals(cache, lru.cache);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cache);
    }

    public String remove()
    {
        String s=cache.iterator().next();
        cache.remove(s);
        return s;
    }
}
