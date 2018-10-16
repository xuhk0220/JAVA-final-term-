/**
 * 主程：
 * 启动GUI界面
 */


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;

public class HannoiGame extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;    //序列化机制，验证版本的一致性

    HannoiTower tower = null;
    int amountOfDisc = 3;                    //盘子数
    char[] towerName = {'A','B','C'};        //塔名

    JMenuBar bar;                            //菜单栏
    JMenu mainMenu,stepsMenu,writerMenu;                         //一级菜单
    JMenuItem ThreeGrades,FourGrades,FiveGrades,SixGrades,otherGrades,perfectSteps,aboutWriter;//二级菜单
    JButton renew = null;                    //按钮：重修开始
    JButton autoButton = null;               //按钮：自动演示
    JPanel north,south;
    Timer timer;
    OtherOfDisc otherOfDisc = new OtherOfDisc(this);
    JLabel fewerLable;

    public HannoiGame() {

        Font newFont = new Font("微软雅黑", Font.BOLD,15);

        setTitle("Hannoi 益智");
        tower = new HannoiTower(towerName);
        tower.setAmountOfDisc(amountOfDisc);
        tower.setMaxDiscWidth(120);
        tower.setMinDiscWidth(50);
        tower.setDiscHeight(16);
        tower.putDiscOnTower();
        add(tower,BorderLayout.CENTER);

        bar =new JMenuBar();
        mainMenu=new JMenu("选择级别");
        stepsMenu=new JMenu("最佳步数");
        writerMenu = new JMenu("关于作者");

        ThreeGrades=new JMenuItem("三级");
        FourGrades=new JMenuItem("四级");
        FiveGrades=new JMenuItem("五级");
        SixGrades=new JMenuItem("六级");
        otherGrades=new JMenuItem("自定义");
        perfectSteps=new JMenuItem("最佳步数参考");
        aboutWriter = new JMenuItem("关于作者");
        mainMenu.setFont(newFont);stepsMenu.setFont(newFont);writerMenu.setFont(newFont);
        ThreeGrades.setFont(newFont);FourGrades.setFont(newFont);FiveGrades.setFont(newFont);
        SixGrades.setFont(newFont);otherGrades.setFont(newFont);
        perfectSteps.setFont(newFont);aboutWriter.setFont(newFont);

        mainMenu.add(ThreeGrades);
        mainMenu.add(FourGrades);
        mainMenu.add(FiveGrades);
        mainMenu.add(SixGrades);
        mainMenu.add(otherGrades);
        stepsMenu.add(perfectSteps);
        writerMenu.add(aboutWriter);
        bar.add(mainMenu);bar.add(stepsMenu);bar.add(writerMenu);
        setJMenuBar(bar);
        ThreeGrades.addActionListener(this);
        FourGrades.addActionListener(this);
        FiveGrades.addActionListener(this);
        SixGrades.addActionListener(this);
        otherGrades.addActionListener(this);

        perfectSteps.addActionListener(this);
        aboutWriter.addActionListener(this);

        renew=new JButton("重新开始");
        renew.addActionListener(this);

        autoButton=new JButton("自动演示");
        autoButton.addActionListener(this);

        renew.setFont(newFont);
        autoButton.setFont(newFont);

        north=new JPanel();
        north.add(renew);
        north.add(autoButton);

        String mess="规则：将所有盘子照原从"+towerName[0]+"座搬运到"+towerName[2]+"座";

        JLabel hintMess = new JLabel(mess);
        hintMess.setFont(newFont);
        north.add(hintMess);

        timer = new Timer();
        add(timer,BorderLayout.SOUTH);
        add(north,BorderLayout.NORTH);

        setResizable(true);                       //可改变窗口大小
        setVisible(true);
        setBounds(0,0,500,500);
        validate();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    //二级菜单的点击事件，和两个按钮的点击事件
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ThreeGrades) {
            amountOfDisc=3;                     //3盘
            tower.setAmountOfDisc(amountOfDisc);
            tower.putDiscOnTower();
        }else if(e.getSource()==FourGrades) {
            amountOfDisc=4;                     //4盘
            tower.setAmountOfDisc(amountOfDisc);
            tower.putDiscOnTower();
        }else if(e.getSource()==FiveGrades) {
            amountOfDisc = 5;                     //5盘
            tower.setAmountOfDisc(amountOfDisc);
            tower.putDiscOnTower();
        }else if(e.getSource()==SixGrades) {
            amountOfDisc = 6;                     //6盘
            tower.setAmountOfDisc(amountOfDisc);
            tower.putDiscOnTower();
        }else if(e.getSource() == otherGrades){   //自定义盘数

            int x=this.getBounds().x+this.getBounds().width;
            int y=this.getBounds().y;
            tower.getOtherOfDisc().setLocation(x,y);
            tower.getOtherOfDisc().setSize(300, 150);
            tower.getOtherOfDisc().setVisible(true);

            amountOfDisc = tower.getOtherOfDisc().amountOfDisc;
            tower.setAmountOfDisc(amountOfDisc);
            tower.putDiscOnTower();

        }else if(e.getSource() == aboutWriter){     //弹出消息框
            UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("微软雅黑", Font.BOLD, 15)));
            JOptionPane.showMessageDialog(this,"兰州大学\n17级信息院--计算机二班--许红凯\n学号：320170941570");

        }else if(e.getSource() == perfectSteps){
            UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("微软雅黑", Font.BOLD, 15)));
            JOptionPane.showMessageDialog(this,"最少步骤f(n)=2*n-1\n"+(int)(Math.pow(2,amountOfDisc)-1)+"步\n笑嘻嘻");

        }else if(e.getSource()==renew) {
            tower.setAmountOfDisc(amountOfDisc);//重新开始：上局默认盘
            tower.putDiscOnTower();
        }else if(e.getSource()==autoButton) {   //自动演示：上局默认盘，
            tower.setAmountOfDisc(amountOfDisc);
            tower.putDiscOnTower();

            int x=this.getBounds().x+this.getBounds().width;
            int y=this.getBounds().y;
            tower.getAutoMoveDisc().setLocation(x,y);
            tower.getAutoMoveDisc().setSize(300, this.getBounds().height);
            tower.getAutoMoveDisc().setVisible(true);
        }
        validate();
    }



    public static void main(String[] args) {
        HannoiGame hannoiWindow = new HannoiGame();
        System.out.println(hannoiWindow.amountOfDisc);
    }
}
