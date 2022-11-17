package cli;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

public class RoomController extends Thread{
    Socket socket=null;
    InputStream in;
    //Users user;
    Vector<String> Userlist;
    int num;
    int status;
    int room;
    String nick_id;
    String myNick;
    int myRoom;

    public RoomController(Socket socket, Client client) throws IOException {
        this.socket=socket;
        myRoom=client.room;
        myNick=client.nick;
        MessagePacker packet = new MessagePacker("P", myRoom, 0, 0, myNick);
        try {
            in = socket.getInputStream();
            System.out.println("[접속 성공]");
            System.out.println("이름>> "+myNick);
            System.out.println("방>> "+myRoom);
            send(packet.getPacket());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        MessagePacker packet;
        try {
            System.out.println(in.read());
            System.out.println(in.toString());
            System.out.println(in);
            while (in != null) {
                packet = MessagePacker.unpack(in);
                String protocol = packet.getFlag();
                room=Integer.parseInt(packet.getRoomId());
                num = Integer.parseInt(packet.getCurNum());
                status=Integer.parseInt(packet.getStatus());
                nick_id = packet.getNickId();
                switch (protocol) {
                    case "P":
                        Userlist.add(nick_id);
                        //userlist UI상 갱신 필요===display(Userlist);
                    case "T":
                        if (status==1&&num>=18) {
                            //블라인드 처리
                        } else {
                            //해당 숫자로 업데이트
                        }
                        //userlist UI상 갱신: 해당 차례인 사람 색깔 다르게
                    case "R":
                        //게임종료 알림창
                    case "O":
                        Userlist.remove(nick_id);
                        //userlist UI상 갱신
                    case "Q":
                        //참가 불가 알림창 출력
                }
            }
        } catch (IOException e) {
            System.out.println("[접속끊김]");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void pass (int n) {
        //차례 넘김
        //아이스크림 세 번 이상 누름: 아이스크림 버튼 비활성화: 사용자가 직접 차례 넘김 버튼 누르도록 유도
        MessagePacker packet=new MessagePacker("T",myRoom,1,n,myNick);
        send(packet.getPacket());
    }
    public void updateNum (int n) {
        //숫자 갱신
        MessagePacker packet=new MessagePacker("U",myRoom,0,n,myNick);
        send(packet.getPacket());
    }
    void send(byte[] byteArr){
        try{
            OutputStream out=socket.getOutputStream();
            out.write(byteArr);
            out.flush();
        }catch(Exception e){
            try {
                System.out.println("서버 통신 안됨");
                socket.close();
            }catch(IOException e2){
            }
        }
    }
}
//게임 스타트 기준
//UI에서 해결
//vector.remove()로 특정 인덱스 값 삭제 시 자동으로 앞으로 한칸 씩 당겨짐
//즉, 0번 인덱스에 저장된 닉네임을 방장으로 생각한다면, 현재 방장인 사람이 퇴장한다고 해도 방장 권환 변경에 대해 추가 작업 필요 없음
//UI에서 본인이 Userlist상 0번 인덱스에 저장된 사람이라면 게임 스타트 버튼을 활성화
//아니라면, 비활성화하도록 하면 될듯

//지금 상태대로면 클라이언트들마다 각자 자신만의 USserlist를 가지고 있는 구조인데,
//첫번째로 들어오지 않는 한, 완전한 Userlist를 가지고 있지 못한다.
//자기보다 앞에 들어온 사람에 대해서는 자신의 vector에 포함X
//그렇기 때문에, 서버와 같은 vector를 클라들이 가져야 하는데, 그러면 서버가 클라에게 보내줘야하는데 굉장히 번거로울 것 같단 말이지(바이트로 해야하니까,,,)
//서버랑 클라가 함께 공유하는 전역변수느낌으로 Userlist를 선언할 방법이 없을까