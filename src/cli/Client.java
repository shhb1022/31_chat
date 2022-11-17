package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public int room;
    public String nick;
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
        Socket socket = null;
        BufferedReader in = null;
        try {
            socket = new Socket("localhost", 8000);
            Scanner sc = new Scanner(System.in);
            System.out.println("사용자 이름을 입력해주세요.");
            nick = sc.nextLine();
            System.out.println("방의 종류를 입력해주세요. ");
            room=sc.nextInt();
            RoomController sendThread = new RoomController(socket, this);
            sendThread.start();

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (in != null) {
                String inputMsg = in.readLine();
                if(("[" + nick + "]님이 나가셨습니다").equals(inputMsg)) break;
                System.out.println("From:" + inputMsg);
            }
        } catch (IOException e) {
            System.out.println("[서버 접속끊김]");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[서버 연결종료]");
    }
}

/*
class SendThread extends Thread {
    Socket socket = null;
    String name;
    int room;
    Scanner scanner = new Scanner(System.in);

    public SendThread(Socket socket, String name,int room) {
        this.socket = socket;
        this.name = name;
        this.room=room;
    }

    @Override
    public void run() {
        try {
            // 최초1회는 client의 name을 서버에 전송
            PrintStream out = new PrintStream(socket.getOutputStream());
            out.println(name);
            out.flush();

            while (true) {
                String outputMsg = scanner.nextLine();
                out.println(outputMsg);
                out.flush();
                if("quit".equals(outputMsg)) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/
