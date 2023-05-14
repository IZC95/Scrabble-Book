package Model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Host implements ScrabModel{
    Board board;
    PriorityQueue<Integer> positions;
    HashMap<Tile,String> randomPick;
    Queue<String> players;
    static boolean stop=false;
    ServerSocket server;
    Host()
    {
        try {
randomPick=new HashMap<>();
            positions= div();
            for
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void start()
    {
        new Thread(()-> {
            try {
                startServer();
            } catch (Exception e) {
            }
        }).start();
    }
    public void startServer() throws Exception {
        try{
            server = new ServerSocket(8082);
            server.setSoTimeout(1000);
            while (!stop) {
                try {
                    Socket aClient = server.accept();
                    handleClient(aClient.getInputStream(), aClient.getOutputStream());
                    close();
                    aClient.close();
                } catch (SocketTimeoutException s) {}
            }
            server.close();
        } catch (IOException e) {e.printStackTrace();}
           }
     void handleClient(InputStream inFromclient, OutputStream outToClient) {

        try {
            PrintWriter out=new PrintWriter(outToClient,true);
            BufferedReader in=new BufferedReader(new InputStreamReader(inFromclient));
            String[] data=in.readLine().split(",");
            if(data.length==1)
            {
                Tile.Bag b= Tile.Bag.getBag();
                randomPick.put(b.getRand(),data[0]);
            }
            else {
                if(players.peek().equals(data[0]))
                    Word word=(Word)data[1];
                    out.println(tryPlaceWord());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
           static void close()
           {
               stop=true;
           }

    @Override
    public int tryPlaceWord(Word word) {
        board=Board.getBoard();
        return board.tryPlaceWord(word);
    }
}
