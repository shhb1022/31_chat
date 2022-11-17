package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame{
    private static JFrame frame = new JFrame("MainGUI");
    private JButton start;
    private JButton explain;
    private JPanel Jpanel1;

    private JPanel image = new JPanel() {
        Image background=new ImageIcon(MainGUI.class.getResource("../GUI/main.png")).getImage();
        public void paint(Graphics g) {
            g.drawImage(background, 0, 0, null);

        }
    };

    public MainGUI() {
        explain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowClose();
                ExplainGUI a = new ExplainGUI(); a.frame();
            }
        });
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //windowClose();
                InputGUI b = new InputGUI(); b.frame();
            }
        });
    }

    public static void windowClose(){
        frame.dispose();
    }

    public void frame(){
        frame.setContentPane(Jpanel1);
        frame.setTitle("배스킨라빈스31/메인");//타이틀
        frame.setMinimumSize(new Dimension(830, 500));//프레임의 크기
        frame.setResizable(false);//창의 크기를 변경하지 못하게
        frame.setLocationRelativeTo(null);//창이 가운데 나오게
        frame.pack();
        frame.setVisible(true);
        image.setOpaque(false);
        image.setBounds(180,100,600,400);
        image.setLayout(null);
        frame.add(image);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
