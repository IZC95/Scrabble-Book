package Model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.*;

public class Host extends Observable implements ScrabModel {
    Board board;
    PriorityQueue<Tile> firstPositions;
    HashMap<Tile,String> randomPick;
    Queue<String> players;
    static boolean stop;
    ServerSocket server;
    HashMap<String,Tile[]> tileStands;
    HashMap<String,Integer> score;
    int countTurns;
    public final String name;
    HashMap<String,Boolean> connections;

   public Host()
    {

        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter a name");
        name=scanner.next();
        Tile.Bag b = Tile.Bag.getBag();

        score=new HashMap<>();
        randomPick=new HashMap<>();
        firstPositions= new PriorityQueue<>((t1,t2)->t1.letter-t2.letter);
        tileStands=new HashMap<>();
        stop=false;
        randomPick.put(b.getRand(), name);
        connections=new HashMap<>();
        score.put(name, 0);
        connections.put(name,true);
        start();
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
                    Socket aClient = server.accept();//Blocking call
                    handleClient(aClient.getInputStream(), aClient.getOutputStream());

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
                    if (randomPick.size() == 4)
                        out.println("The session is full");
                    else if (randomPick.containsValue(data[0]))
                        out.println("Name is already taken");
                    else {
                        Tile.Bag b = Tile.Bag.getBag();
                        randomPick.put(b.getRand(), data[0]);
                        score.put(data[0], 0);
                        connections.put(data[0],true);

                        out.println("available");
                    }
                }
            else if(connections.get(data[0])) {
                if (data[1].equals("startGame")) {
                    String chars = "";
                    for (Tile t : tileStands.get(data[0]))
                        chars += t.letter;
                    out.println(chars);
                } else if (data[1].equals("endGame")) {

                     connections.put(data[0], false);
                } else if (data[1].equals("endTurn")) {
                    String pullTiles = "";
                    for (Tile tile : tileStands.get(data[0])) {
                        if (tile == null)
                            tile = Tile.Bag.getBag().getRand();
                        pullTiles += tile.letter;
                    }
                    out.println(pullTiles);
                    endTurn();
                } else {
                    if (players.peek().equals(data[0])) {
                        int result = 0;

                        if (data[1].equals("Q")) {
                            result = tryPlaceWord(data[0], Integer.parseInt(data[2]), Integer.parseInt(data[3]), Boolean.parseBoolean(data[4]), data[5].toCharArray());
                            if (result == 0)
                                out.println("Illegal move");
                            if (result == -1)
                                out.println("false, challenge?");
                        } else if ((data[1].equals("C"))) {
                            result = challenge(data[0], Integer.parseInt(data[2]), Integer.parseInt(data[3]), Boolean.parseBoolean(data[4]), data[5].toCharArray());
                        }
                        out.println(result);
                    } else {
                        out.println("Not your turn");
                    }

                }
            }
            else out.println("disconnected from server");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
           static void close()
           {
               stop=true;
           }

    @Override
    public int tryPlaceWord(String name,int row,int col,boolean vertical,char[] _tiles) {
        Tile[] _word=new Tile[_tiles.length-1];
        for(int i=0;i< _tiles.length;i++)
            for (Tile tile:tileStands.get(name))
                if(tile.letter==_tiles[i]) {
                    _word[i] = tile;
                    tile=null;
                }



        board=Board.getBoard();
        int result=board.tryPlaceWord(new Word(_word,row,col,vertical));
        if (result>0) {
            setChanged();
            notifyObservers();
        }
        return result;
    }

    public void startGame(){


        countTurns=0;
        for(Tile t : firstPositions) {
            players.offer(randomPick.get(t));
            Tile.Bag.getBag().put(t);
        }
        for(String s:players)
            tileStands.put(s,get_Tiles());

    }
    public Tile[] get_Tiles(){
        Tile[] tiles=new Tile[6];
        for(int i=0;i<7;i++)
            tiles[i]=Tile.Bag.getBag().getRand();
        return tiles;

    }
   public int challenge(String name,int row,int col,boolean vertical,char[] _tiles)
    {
        int result=0;
        List<Character> temp=new ArrayList<>();
        String word="";
        for (char c:_tiles) {
            temp.add(c);
            word+=c;
        }
        for(Tile t:tileStands.get(name))
            if(temp.contains(t.letter))
                temp.remove(t.letter);


        if(temp.size()==0){//The player has the right tiles to make the move
        try {


            Socket socket=new Socket("localhost",8080);
            PrintWriter out=new PrintWriter(socket.getOutputStream());
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println("C,alice_in_wonderland.txt,Frank Herbert - Dune.txt,Harray Potter.txt,mobydick.txt,pg10.txt,shakespeare.txt,The Matrix.txt"+word);
            if(in.readLine().equals("True")) {
                result+=5;
                score.put(name,score.get(name)+5);
                tryPlaceWord(name,col,row,vertical,_tiles);
            }
            else {
                score.put(name,score.get(name)-5);
                result-=5;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }else result= -1;
return result;
    }

    @Override
    public void endTurn() {
        players.offer(players.remove());
        countTurns++;
        if(countTurns==20*players.size())
            endGame();
    }
public char[] getTiles(){
       String tiles="";
    for(Tile t:tileStands.get(name))
        tiles+=t.letter;
    return tiles.toCharArray();

}
    @Override
    public void endGame() {
int winnerScore=0;
String Winner="";
for (String p:score.keySet())
    if(score.get(p)>winnerScore)
    {
        winnerScore=score.get(p);
        Winner=p;
    }
System.out.println("The Winner Is:"+Winner+"-"+winnerScore+"Points");
        for (boolean b : connections.values())
            b = false;
        close();
    }

    Tile[][] getData(){
        return Board.getBoard().getTile();
    }
}
