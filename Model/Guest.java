package Model;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Guest implements ScrabModel{
    Socket socket;
    int totalScore;
    final String name;
    char[] tiles;
    BufferedReader in;
    PrintWriter outToServer;
    Guest(int ip, int port )
    {
        try {
            tiles=new char[6];
            String _name=null;
            socket=new Socket(String.valueOf(ip),port);
            totalScore=0;
            String answer;

            outToServer = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(!(answer=in.readLine()).equals("available")) {
                if(answer.equals("The session is full")) {
                    close();
                    break;
                }
                _name= System.in.toString();
                outToServer.println(_name);
            }

                name=_name;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int tryPlaceWord(String name,int row,int col,boolean vertical,char[] _tiles) {

        try {
            String word=name;
            word+=",Q";
            word+=","+row+","+col+","+ vertical;
            for(char c:_tiles) {
                word += ",";
                word+=c;
            }
            outToServer.println(word);
            String result=in.readLine();
            if(result.equals("false, challenge?")) {
                //The player will decide whether he wants to challenge
                int res=challenge(name, row, col, vertical,_tiles);
                        totalScore+=res;
               return res;
            } else if (result.equals("Illegal move")) {
                return 0;
            }
            else{
                totalScore+=Integer.parseInt(result);
                return Integer.parseInt(result);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    void close()
    {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startGame() {
        try {
            outToServer.println(name+",tiles");
            String chars=in.readLine();
            tiles=chars.toCharArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void endGame() {
        outToServer.println(name+"rank");

        try {
            System.out.println(name+"-"+in.readLine());
            close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int challenge(String name,int row,int col,boolean vertical,char[] _tiles){
        try {
        String word=name;
        word+=",C";

        word+=","+row+","+col+","+ vertical;
        for(char c:_tiles) {
                word += ",";
                word+=c;
            }
        outToServer.println(word);

            String result=in.readLine();
            if(result.equals("-5"))
                System.out.println("Challenge Unsuccessful");
            return Integer.parseInt(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void endTurn() {
        try {
            outToServer.println(name+",Pass");

            tiles=in.readLine().toCharArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
