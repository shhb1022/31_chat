package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExplainGUI {
    private static JFrame frame = new JFrame("ExplainGUI");
    private JPanel panel1;
    private JButton button1;
    private JButton a31Button;
    private JPanel image = new JPanel() {
        Image background=new ImageIcon(MainGUI.class.getResource("../GUI/txt.png")).getImage();
        public void paint(Graphics g) {
            g.drawImage(background, 0, 0, null);

        }
    };

    public ExplainGUI() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowClose();
                MainGUI a = new MainGUI();
                a.frame(); //수정필요
            }
        });
    }

    public void windowClose(){
        frame.dispose();
    }

    public void frame(){
        frame.setContentPane(panel1);
        frame.setTitle("배스킨라빈스31/설명");//타이틀
        frame.setMinimumSize(new Dimension(830, 500));//프레임의 크기
        frame.setResizable(true);//창의 크기를 변경하지 못하게
        frame.setLocationRelativeTo(null);//창이 가운데 나오게
        frame.pack();
        frame.setVisible(true);
        //image.setOpaque(false);
        image.setBounds(10,35,600,400);
        //image.setLayout(null);
        //image2.setOpaque(false);
        //image2.setBounds(300,10,600,150);
        //image2.setLayout(null);
        frame.add(image);
        //frame2.add(image2);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}