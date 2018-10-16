/**
 * 自动演示：
 * GUI中盘子移动过程+文本框中移动记录+按钮效果
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class AutoMoveDisc extends JDialog implements ActionListener{

    private static final long serialVersionUID = 1L;

    int amountOfDisc=3;
    Point[] pointA,pointB,pointC;
    char[] towerName;
    Container con;
    StringBuffer moveStep;
    JTextArea showStep;
    JButton bStart,bStop,bContinue,bClose;
    Timer time;
    int i=0,number=0;

    AutoMoveDisc(Container con){
        setModal(true);
        setTitle("自动演示最少搬运过程");
        this.con =con;
        moveStep=new StringBuffer();
        time=new Timer(100, this);           //延时
        time.setInitialDelay(10);
        showStep = new JTextArea(10, 12);
        bStart=new JButton("演示");
        bStop=new JButton("暂停");
        bContinue=new JButton("继续");
        bClose=new JButton("关闭");

        Font newFont = new Font("微软雅黑",Font.BOLD,15);
        bStart.setFont(newFont);
        bStop.setFont(newFont);
        bContinue.setFont(newFont);
        bClose.setFont(newFont);

        bStart.addActionListener(this);
        bStop.addActionListener(this);
        bContinue.addActionListener(this);
        bClose.addActionListener(this);

        JPanel south = new JPanel();
        south.setLayout(new FlowLayout());
        south.add(bStart);
        south.add(bStop);
        south.add(bContinue);
        south.add(bClose);
        add(new JScrollPane(showStep),BorderLayout.CENTER);
        add(south,BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        towerName = new char[3];

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                time.stop();
                setVisible(false);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {   //四个按钮效果
        if(e.getSource() == time) {
            number++;
            char cStart,cEnd;
            if(i<= moveStep.length()-2) {
                cStart=moveStep.charAt(i);
                cEnd=moveStep.charAt(i+1);
                showStep.append("("+number+")从"+cStart+"座搬一个盘子到"+cEnd+"座\n");
                autoMoveDisc(cStart,cEnd);
            }
            i=i+2;
            if(i>= moveStep.length()-1)
                time.stop();
        }else if(e.getSource() == bStart) {             //演示
            if(moveStep.length() ==0) {
                if(time.isRunning() == false) {
                    i=0;
                    moveStep=new StringBuffer();
                    setMoveStep(amountOfDisc,towerName[0],towerName[1],towerName[2]);
                    number =0;
                    time.start();
                }
            }
        }else if(e.getSource()==bStop) {              //暂停
            if(time.isRunning() == true) {
                time.stop();
            }
        }else if(e.getSource()==bContinue) {         //继续
            if(time.isRunning() == false) {
                time.restart();;
            }
        }else if(e.getSource()==bClose) {            //关闭
            time.stop();
            setVisible(false);
        }
    }

    private void setMoveStep(int mountOfDisc,char one,char two,char three) {    //盘子移动过程记录
        if(mountOfDisc ==1) {
            moveStep.append(one);
            moveStep.append(three);
        }else {
            setMoveStep(mountOfDisc-1,one,three,two);
            moveStep.append(one);
            moveStep.append(three);
            setMoveStep(mountOfDisc-1,two,one,three);
        }
    }
    private void autoMoveDisc(char cStart,char cEnd) {        //在图形上进行盘子移动演示
        Disc disc=null;
        if(cStart == towerName[0]) {
            for(int i=0;i<pointA.length;i++) {
                if(pointA[i].isHaveDisc() == true) {
                    disc = pointA[i].getDiscOnPoint();
                    pointA[i].setHaveDisc(false);
                    break;
                }
            }
        }
        if(cStart == towerName[1]) {
            for(int i=0;i<pointB.length;i++) {
                if(pointB[i].isHaveDisc() == true) {
                    disc = pointB[i].getDiscOnPoint();
                    pointB[i].setHaveDisc(false);
                    break;
                }
            }
        }
        if(cStart == towerName[2]) {
            for(int i=0;i<pointC.length;i++) {
                if(pointC[i].isHaveDisc() == true) {
                    disc = pointC[i].getDiscOnPoint();
                    pointC[i].setHaveDisc(false);
                    break;
                }
            }
        }

        Point endPoint=null;
        int i=0;
        if(cEnd == towerName[0]) {
            for(i=0;i<pointA.length;i++) {
                if(pointA[i].isHaveDisc() == true) {
                    if(i>0) {
                        endPoint=pointA[i-1];
                        break;
                    }else if(i==0) {
                        break;
                    }
                }
            }
            if(i==pointA.length)
                endPoint=pointA[pointA.length-1];
        }

        if(cEnd == towerName[1]) {
            for(i=0;i<pointB.length;i++) {
                if(pointB[i].isHaveDisc() == true) {
                    if(i>0) {
                        endPoint=pointB[i-1];
                        break;
                    }else if(i==0) {
                        break;
                    }
                }
            }
            if(i==pointB.length)
                endPoint=pointB[pointB.length-1];
        }

        if(cEnd == towerName[2]) {
            for(i=0;i<pointC.length;i++) {
                if(pointC[i].isHaveDisc() == true) {
                    if(i>0) {
                        endPoint=pointC[i-1];
                        break;
                    }else if(i==0) {
                        break;
                    }
                }
            }
            if(i==pointC.length)
                endPoint=pointC[pointA.length-1];
        }

        if(endPoint!= null && disc!=null) {
            endPoint.putDisc(disc, con);
            endPoint.setHaveDisc(true);
        }

    }

    public Point[] getPointA() {
        return pointA;
    }

    public void setPointA(Point[] pointA) {
        this.pointA = pointA;
    }

    public Point[] getPointB() {
        return pointB;
    }

    public void setPointB(Point[] pointB) {
        this.pointB = pointB;
    }

    public Point[] getPointC() {
        return pointC;
    }

    public void setPointC(Point[] pointC) {
        this.pointC = pointC;
    }

    public char[] getTowerName() {
        return towerName;
    }

    public void setTowerName(char[] name) {
        if(name[0] == name[1] || name[0]==name[2]|| name[1]==name[2] ) {
            towerName[0]='A';
            towerName[1]='B';
            towerName[2]='C';
        }else {
            towerName = name;
        }
    }

    public Container getCon() {
        return con;
    }

    public StringBuffer getMoveStep() {
        return moveStep;
    }

    public void setMoveStep(StringBuffer moveStep) {
        this.moveStep = moveStep;
    }



    public void setShowStep(JTextArea showStep) {
        this.showStep = showStep;
    }



    public void setbStart(JButton bStart) {
        this.bStart = bStart;
    }



    public void setbStop(JButton bStop) {
        this.bStop = bStop;
    }


    public void setbContinue(JButton bContinue) {
        this.bContinue = bContinue;
    }



    public void setbClose(JButton bClose) {
        this.bClose = bClose;
    }



    public void setTime(Timer time) {
        this.time = time;
    }


    public void setI(int i) {
        this.i = i;
    }


    public void setNumber(int number) {
        this.number = number;
    }

    public void setAmountOfDisc(int amountOfDisc) {
        this.amountOfDisc= amountOfDisc;
    }

}
