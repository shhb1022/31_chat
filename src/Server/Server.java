package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Server {
    Vector<User> Userlist;
    Room BlindRoom = new Room();
    Room PublicRoom = new Room();

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        Userlist = new Vector<>();
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(8000);
            while (true) {
                System.out.println("[클라이언트 연결대기중]");
                socket = serverSocket.accept();

                // client가 접속할때마다 새로운 스레드 생성
                User receiveThread = new User(socket, this);
                Userlist.add(receiveThread);
                receiveThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket!=null) {
                try {
                    serverSocket.close();
                    System.out.println("[서버종료]");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("[서버소켓통신에러]");
                }
            }
        }
    }
}


