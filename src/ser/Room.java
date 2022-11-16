package ser;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Room {
    int num = 0;
    int room_id = 0;
    int idx = 0;
    boolean Flag = false; //게임 시작 여부
    Vector<User> userlist;
    void Plusidx(){
        if(idx == (userlist.size() - 1)){
            idx = 0;
        }
        idx++;
    }
}
