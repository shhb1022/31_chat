package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI {
    private static JFrame frame4 = new JFrame("GameGUI");
    private JLabel turn;
    private JList list1;
    private JButton count;
    private JButton nextTurn;
    private JPanel Jpanel1;

    public GameGUI() {
        count.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void frame(){
        frame4.setContentPane(Jpanel1);
        frame4.setTitle("배스킨라빈스31/게임");//타이틀
        frame4.setMinimumSize(new Dimension(830, 500));//프레임의 크기
        frame4.setResizable(false);//창의 크기를 변경하지 못하게
        frame4.setLocationRelativeTo(null);//창이 가운데 나오게
        frame4.pack();
        frame4.setVisible(true);
        frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
