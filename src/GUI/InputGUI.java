package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputGUI {
    private static JFrame frame = new JFrame("InputGUI");
    private JTextField name;
    private JRadioButton blind;
    private JRadioButton common;
    private JButton join;
    private JLabel nameINPUT;
    private JPanel jpanel1;

    public InputGUI() {
        join.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Name;
                Name = name.getText();
                String gameMode;
                if (Name.isEmpty()){
                    JOptionPane.showMessageDialog(null, "이름을 입력해주세요!");
                }
                else if (blind.isSelected()==false&&common.isSelected()==false) {
                    JOptionPane.showMessageDialog(null, "게임 옵션을 선택해주세요!");
                }
                else{
                    JOptionPane.showMessageDialog(null, "환영합니다 "+Name+" 님!");
                    System.out.println(Name);
                    MainGUI.windowClose();
                    GameGUI d = new GameGUI(); d.frame();
                }
            }
        });
    }

    public void frame(){
        ButtonGroup RadioGameMode = new ButtonGroup();
        RadioGameMode.add(blind); RadioGameMode.add(common);
        frame.setContentPane(jpanel1);
        frame.setTitle("배스킨라빈스31/입력");//타이틀
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);//창이 가운데 나오게
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
