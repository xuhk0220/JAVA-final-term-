/**
 * 自定义盘子数目（>0即可）
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OtherOfDisc extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    int amountOfDisc = 3;
    Container con;
    JLabel otherDisc;
    JTextField otherDiscAmount;//输入盘子数
    JButton yes, no;
    JPanel north,south;

    public OtherOfDisc(Container con){
        otherDisc = new JLabel("自定义盘子数");
        otherDiscAmount = new JTextField(10);
        yes = new JButton("确定");
        no = new JButton("取消");
        setTitle("自定义盘子数");
        setModal(true);   //设置模式对话框
        this.con =con;

        Font newFont = new Font("微软雅黑", Font.BOLD,15);
        otherDisc.setFont(newFont);
        yes.setFont(newFont);
        no.setFont(newFont);

        north = new JPanel();
        north.add(otherDisc);north.add(otherDiscAmount);
        south = new JPanel();
        south.add(yes);south.add(no);

        yes.addActionListener(this);
        no.addActionListener(this);

        add(north,BorderLayout.NORTH);
        add(south,BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });


    }


    @Override
    public void actionPerformed(ActionEvent a){
        if(a.getSource() == yes){
            amountOfDisc = Integer.parseInt(otherDiscAmount.getText());
            setVisible(false);
        }else if(a.getSource() == no){
            otherDiscAmount.setText("");
        }
    }

    public Container getCon() {
        return con;
    }

}

