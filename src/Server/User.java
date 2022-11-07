package Server;
import java.io.*;
import java.net.Socket;
import java.util.Vector;

class User extends Thread {
    int mode = 0; //블라인드 여부
    Socket socket = null;
    //BufferedReader in = null;
    //PrintWriter out = null;
    InputStream in;
    Vector<User> Userlist;
    Room Publicroom;
    Room Blindroom;
    Room room;
    String nick_id = "";
    public User(Socket socket, Server Server) throws IOException {
        this.Userlist = Server.Userlist;
        this.socket = socket;
        this.Blindroom = Server.BlindRoom;
        this.Publicroom = Server.PublicRoom;

        try {
            in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        MessagePacker packet;
        try {
            System.out.println("[새연결생성]");
            while (in != null) {
                packet = MessagePacker.unpack(in);
                String protocol;
                protocol = packet.getFlag();
                switch (protocol) {
                    case "P":
                        if (Integer.parseInt(packet.getRoomId()) == 1) {
                            nick_id = packet.getNickId();
                            room = Blindroom;
                            room.userlist.add(this);
                            //클라한테 P 패킷 보내야함
                            packet = new MessagePacker("p", 1, 0, 0, nick_id);
                            RoomMsg(room, packet);
                        } else {
                            room = Publicroom;
                            room.userlist.add(this);
                            //클라한테 P 패킷 보내야함
                            packet = new MessagePacker("P", 0, 0, 0, nick_id);
                            RoomMsg(room, packet);
                        }
                    case "U":
                        room.num = Integer.parseInt(packet.getCurNum());
                        if (room.num == 31) {
                            packet = new MessagePacker("R", 0, 0, 0, nick_id);
                            RoomMsg(room, packet);
                        } else {
                            if (Integer.parseInt(packet.getStatus()) == 1) {
                                room.Plusidx();
                                packet = new MessagePacker("T", 0, 0, room.num, room.userlist.get(room.idx).nick_id);
                                RoomMsg(room, packet);
                            } else {
                                packet = new MessagePacker("T", 0, 0, room.num, room.userlist.get(room.idx).nick_id);
                                RoomMsg(room, packet);
                            }
                        }
                }
            }
        } catch (IOException e) {
            System.out.println("[접속끊김]");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            room.userlist.remove(this);
            Userlist.remove(this);
            //out 패킷 보내기
            packet = new MessagePacker("O", room.room_id, 0, 0, nick_id);
            RoomMsg(room,packet);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[" + nick_id + " 연결종료]");
    }
    public void RoomMsg(Room room, MessagePacker packet) {
        for(int i = 0; i < room.userlist.size(); i++) { //방에 참가한 모든 유저들에게
            User user = room.userlist.get(i);
            user.send(packet.getPacket());
        }
    }

    void send(byte[] byteArr) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(byteArr);
            outputStream.flush();
        } catch (Exception e) {
            try {
                System.out.println("클라이언트 통신 안됨");
                Userlist.remove(this);
                room.userlist.remove(this);
                socket.close();
            } catch (IOException e2) {
            }
        }
    }

}