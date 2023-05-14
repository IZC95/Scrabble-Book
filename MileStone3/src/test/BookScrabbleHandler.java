package test;


import java.io.*;


public class BookScrabbleHandler implements ClientHandler {
    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient)
    {
        BufferedReader reader=new BufferedReader(new InputStreamReader(inFromclient));
        try {
            String first;
            PrintWriter out= new PrintWriter(outToClient);
            String[] oldWords;
            String line=reader.readLine();
            oldWords = line.split(",");
            first=oldWords[0];
            String[] newWords = new String[oldWords.length-1];
            for(int i=1,j=0;i< oldWords.length;j++,i++)
                newWords[j]=oldWords[i];

        DictionaryManager dm=DictionaryManager.get();
            if(first.equals("Q"))
            {
                if(dm.query(newWords))
                    out.print("true\n");
                else out.print("false\n");
            }
            else
                if(dm.challenge(newWords))
                    out.print("true\n");
                else out.print("false\n");
                out.flush();
            reader.close();
            out.close();
            inFromclient.close();
            outToClient.close();
        }
        catch (IOException e){}

    }

    @Override
    public void close() {

    }
}
