package Test;

import Model.Host;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class HostTest {
    public static void main(String[] args) {
        Host host = new Host();
        host.startGame();
        char[] tiles= host.getTiles();
        if(tiles==null)
            System.out.println("Problem by getting tiles");
        /* Only for the test, dictionaryLegal() will return true */

        char[] ex=new char[1];
        ex[0]=tiles[0];
        int result= host.tryPlaceWord(host.name, 0,0,true,ex);
        if(result==0)
            System.out.println("problem by tryPlaceWord()");
        ex[0]=tiles[1];
        result=host.challenge(host.name, 1,1,true,ex);
        if(result==0)
            System.out.println("problem by challenge()");
        for(int i=0;i<20;i++)
            host.endTurn();

        try {
            Socket socket = new Socket("localhost", 8082); // Replace with the appropriate host and port
            // Code to handle the opened socket connection
        } catch (ConnectException e) {
            // Handle connection refused error
            System.err.println("Connection refused: " + e.getMessage());
        } catch (SocketTimeoutException e) {
            // Handle connection timeout error
            System.err.println("Connection timeout: " + e.getMessage());
        } catch (IOException e) {
            // Handle other I/O-related errors
            System.err.println("I/O error: " + e.getMessage());
        }

    }
}
