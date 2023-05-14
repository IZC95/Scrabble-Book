package test;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class MyServer {
    int port;
    ClientHandler ch;
Socket aClient;
    volatile boolean stop;
    volatile boolean finish;
    ServerSocket server;
MyServer(int port, ClientHandler ch)
{
    this.ch=ch;
    this.port=port;
     stop=false;
     finish=true;
}
public void start()
{
                new Thread(()-> {
                    try {
                        runServer();
                    } catch (Exception e) {
                    }
                }).start();
}
void close()  {
        stop = true;
        }
	public void runServer() throws Exception {
    try{
        server = new ServerSocket(port);
        server.setSoTimeout(1000);
        while (!stop) {
            try {
                    aClient = server.accept();
                    ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                    ch.close();
                    aClient.close();
            } catch (SocketTimeoutException s) {}
        }
        server.close();
    } catch (IOException e) {e.printStackTrace();}
;    }
    }


