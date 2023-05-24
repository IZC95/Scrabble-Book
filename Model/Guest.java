package Model;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Guest implements ScrabModel{
    int totalScore;
    public final String name;
    char[] tiles;
    String Ip;
    int Port;

   public Guest(String ip, int port )
    {
        try {
            Ip=ip;
            Port=port;
            tiles=new char[6];
            String _name=null;
            Socket socket=new Socket(ip,port);
            totalScore=0;
            String answer;
            Scanner scanner=new Scanner(System.in);
            System.out.println("Enter a name");
            _name=scanner.nextLine();
            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outToServer.println(_name);
            while(!(answer=in.readLine()).equals("available")) {
                socket=new Socket(ip,port);
                if(answer.equals("The session is full")) {
                    System.out.println("The session is full");
                    socket.close();
                    scanner.close();
                    break;
                }
                outToServer = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Try another one");
                _name= scanner.next();

                outToServer.println(_name);

            }

                name=_name;
socket.close();
scanner.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int tryPlaceWord(String name,int row,int col,boolean vertical,char[] _tiles) {

        try {
            Socket socket=new Socket(Ip,Port);
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToServer=new PrintWriter(socket.getOutputStream());
            String word=name;
            word+=",Q";
            word+=","+row+","+col+","+ vertical;
            for(char c:_tiles) {
                word += ",";
                word+=c;
            }
            outToServer.println(word);
            String result=in.readLine();
            socket.close();
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


    @Override
    public void startGame() {
        try {
            Socket socket=new Socket(Ip,Port);
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToServer=new PrintWriter(socket.getOutputStream());
            outToServer.println(name+",startGame");
            String chars=in.readLine();
            tiles=chars.toCharArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
public char[] getTiles(){
       return tiles;
}
    @Override
    public void endGame() {

        try {
            Socket socket=new Socket(Ip,Port);
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToServer=new PrintWriter(socket.getOutputStream());
            outToServer.println(name+"endGame");
            System.out.println(name+"-"+in.readLine());
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int challenge(String name,int row,int col,boolean vertical,char[] _tiles){
        try {
            Socket socket=new Socket(Ip,Port);
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToServer=new PrintWriter(socket.getOutputStream());
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
            Socket socket=new Socket(Ip,Port);
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToServer=new PrintWriter(socket.getOutputStream());
            outToServer.println(name+",endTurn");

            tiles=in.readLine().toCharArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
