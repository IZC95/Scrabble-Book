package Model;

import java.io.*;
import java.net.Socket;

public class Guest implements ScrabModel{
    Socket socket;
    int totalScore;
    final String name;
    Guest(int ip, int port, String _name)
    {
        try {
            socket=new Socket(String.valueOf(ip),port);
            totalScore=0;

            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(!in.readLine().equals("available")) {
                _name= System.in.toString();
                outToServer.println(_name);
            }
            name=_name;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int tryPlaceWord(Word word) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(name+','+word);
           return Integer.parseInt(in.readLine());

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

}
