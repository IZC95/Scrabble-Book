package test;


import java.util.HashMap;
import java.util.Map;

public class DictionaryManager {
Map<String,Dictionary> DM;
private static DictionaryManager dm=new DictionaryManager();
DictionaryManager(){DM=new HashMap<>();
}
boolean query(String...args)
{
    boolean find=false;
    int count=0;
    String q=args[args.length-1];
    for (String s:args) {
        count++;
        if (count<(args.length)&&!DM.containsKey(s))
            DM.put(s, new Dictionary(s));
        if (count<(args.length)&&DM.containsKey(s) && DM.get(s).query(q))
            find= true;
    }
    return find;
}

    boolean challenge(String...args){
boolean find=false;
int count=0;
        String q=args[args.length-1];
        for (String s:args) {
            count++;
            if ( count<(args.length)&&DM.get(s).challenge(q))
                find=true;

        }
    return find;}

    int getSize(){return DM.size();}
     static DictionaryManager get(){return dm;}
}
