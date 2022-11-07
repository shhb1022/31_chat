package Server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;


public class MessagePacker {
    private JSONObject body;

    public MessagePacker(String flag, int room_id, int status, int cur_num, String nick_id) {
        body = new JSONObject();
        body.put("flag",flag);
        body.put("room_id",String.valueOf(room_id));
        body.put("status", String.valueOf(status));
        body.put("cur_num", String.valueOf(cur_num));
        body.put("nick_id", nick_id);
    }

/*    public MessagePacker(String flag,String nick_id, int room_id) {
        body = new JSONObject();
        body.put("flag",flag);
        body.put("nick_id", nick_id);
        body.put("cur_num", String.valueOf(room_id));
    }*/

    public MessagePacker(byte[] data) throws Exception {
        JSONParser parser = new JSONParser();
        body = (JSONObject) parser.parse(new String(data, StandardCharsets.UTF_8));
    }

    public static MessagePacker unpack(InputStream is) throws Exception {
        byte[] sizeBuf = new byte[2];
        int readByCount = is.read(sizeBuf,0, sizeBuf.length);
        if(readByCount == -1) throw new IOException();
        int size = byteArrayToInt(sizeBuf, 2);

        // Message 내용을 담을 버퍼
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] receiveData = new byte[size];
        int read;

        // 버퍼 안의 데이터를 다 읽을 때까지 반복문을 돌린다.
        while((read = is.read(receiveData,0,receiveData.length))!=-1) {
            buffer.write(receiveData,0,read);
            size = size - read;
            if (size<=0) {
                break;
            }
        }

        byte[] data = buffer.toByteArray();
        buffer.flush();
        buffer.close();

        MessagePacker packet = new MessagePacker(data);
        return packet;
    }

    public byte[] getPacket() {
        //패킹하는 부분: 정보 규칙을 만들어주자
        byte[] jsonData = body.toJSONString().getBytes(StandardCharsets.UTF_8);
        byte[] data = new byte[jsonData.length];
        for(int i=0; i< jsonData.length; i++) {
            data[i] = jsonData[i];
        }
        return data;
    }

    public String getFlag(){ return (String) body.get("flag");}
    public String getRoomId() { return (String) body.get("room_id"); }
    public String getStatus() { return (String) body.get("status"); }
    public String getCurNum() { return (String) body.get("cur_num"); }
    public String getNickId() { return (String) body.get("nick_id"); }


    public static byte[] intToByteArray(int value, int lengthDiv) {
        byte[] byteArray = new byte[lengthDiv];
        if (lengthDiv == 2){
            byteArray[0] = (byte) value;
            byteArray[1] = (byte) (value >>> 8);
        }else if (lengthDiv == 4){
            byteArray[0] = (byte)(value >> 24);
            byteArray[1] = (byte)(value >> 16);
            byteArray[2] = (byte)(value >> 8);
            byteArray[3] = (byte)(value);
        }
        return byteArray;
    }

    private static int byteArrayToInt(byte[] b, int lengthDiv) {
        int byteInt = 0;
        if (lengthDiv == 2) {
            byteInt = ((b[1] & 0xFF) << 8) | (b[0] & 0xFF);
        } else if (lengthDiv == 4) {
            byteInt = b[0] & 0xFF |
                    (b[1] & 0xFF) << 8 |
                    (b[2] & 0xFF) << 16 |
                    (b[3] & 0xFF) << 24;
        }
        return byteInt;
    }

    public String toString() {
        return body.toJSONString();
    }
}
