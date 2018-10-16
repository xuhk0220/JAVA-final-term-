/**
 * 塔：
 *
 */

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class HannoiTower extends JPanel {

    private static final long serialVersionUID = 1L;

    int amountOfDisc = 3;       //默认盘子数为3
    Disc[] disc;
    int maxDiscWidth,minDiscWidth,discHeight;
    char[] towerName;
    Point[] pointA,pointB,pointC ;

    HandleMouse handleMouse;
    AutoMoveDisc autoMoveDisc;
    OtherOfDisc otherOfDisc = new OtherOfDisc(this);

    HannoiTower(char[] towerName){
        handleMouse=new HandleMouse(this);
        this.towerName = towerName;
        setLayout(null);
        setBackground(new Color(200,226,226));
    }

    public void setAutoMoveDisc(int number) {
        if(number<=1)
            amountOfDisc=1;
        else
            amountOfDisc=number;
    }

    public void putDiscOnTower() {              //往塔上放盘子，塔上盘子的数量变化
        removeDisc();
        int n = (maxDiscWidth-minDiscWidth)/amountOfDisc;
        disc = new Disc[amountOfDisc];
        for(int i=0;i<disc.length;i++) {
            disc[i]=new Disc();
            disc[i].setNumber(i);
            int discwidth=minDiscWidth+i*n;              //每塔上最大盘和最小盘大小确定，中间盘子大小依次递增，由数量决定
            disc[i].setSize(discwidth, discHeight);
            disc[i].addMouseListener(handleMouse);
            disc[i].addMouseMotionListener(handleMouse);;
        }
        pointA=new Point[amountOfDisc];
        pointB=new Point[amountOfDisc];
        pointC=new Point[amountOfDisc];

        int vertialDistance=discHeight;         //塔高，每层盘子不重叠地依次堆叠
        for(int i=0;i<pointA.length;i++) {
            pointA[i]=new Point(maxDiscWidth, vertialDistance+100);
            vertialDistance=vertialDistance+discHeight;
        }
        vertialDistance=discHeight;
        for(int i=0;i<pointB.length;i++) {
            pointB[i]=new Point(2*maxDiscWidth, vertialDistance+100);
            vertialDistance=vertialDistance+discHeight;
        }
        vertialDistance=discHeight;
        for(int i=0;i<pointC.length;i++) {
            pointC[i]=new Point(3*maxDiscWidth, vertialDistance+100);
            vertialDistance=vertialDistance+discHeight;
        }

        for(int i=0;i<pointA.length;i++) {            //游戏开始的默认盘子状态
            pointA[i].putDisc(disc[i], this);
        }
        handleMouse.setPointA(pointA);
        handleMouse.setPointB(pointB);
        handleMouse.setPointC(pointC);

        autoMoveDisc=new AutoMoveDisc(this);
        autoMoveDisc.setTowerName(towerName);
        autoMoveDisc.setAmountOfDisc(amountOfDisc);
        autoMoveDisc.setPointA(pointA);
        autoMoveDisc.setPointB(pointB);
        autoMoveDisc.setPointC(pointC);
        validate();
        repaint();
    }

    public void removeDisc() {
        if(pointA != null) {
            for(int i=0;i<pointA.length;i++) {
                pointA[i].removeDisc(pointA[i].getDiscOnPoint(), this);
                pointB[i].removeDisc(pointB[i].getDiscOnPoint(), this);
                pointC[i].removeDisc(pointC[i].getDiscOnPoint(), this);
            }
        }
    }

    public void paintComponent(Graphics g) {       //画盘子，以及塔底座和标识
        super.paintComponent(g);
        int x1,y1,x2,y2;
        x1=pointA[0].getX();
        y1=pointA[0].getY()-discHeight/2;
        x2=pointA[amountOfDisc-1].getX();
        y2=pointA[amountOfDisc-1].getY()+discHeight/2;
        g.drawLine(x1, y1, x2, y2);

        x1=pointB[0].getX();
        y1=pointB[0].getY()-discHeight/2;
        x2=pointB[amountOfDisc-1].getX();
        y2=pointB[amountOfDisc-1].getY()+discHeight/2;
        g.drawLine(x1, y1, x2, y2);

        x1=pointC[0].getX();
        y1=pointC[0].getY()-discHeight/2;
        x2=pointC[amountOfDisc-1].getX();
        y2=pointC[amountOfDisc-1].getY()+discHeight/2;
        g.drawLine(x1, y1, x2, y2);
        g.setColor(Color.BLUE);

        x1=pointA[amountOfDisc-1].getX()-maxDiscWidth/2;
        y1=pointA[amountOfDisc-1].getY()+discHeight/2;
        x2=pointC[amountOfDisc-1].getX()+maxDiscWidth/2;
        y2=pointC[amountOfDisc-1].getY()+discHeight/2;

        int length=x2-x1,height=6;
        g.fillRect(x1, y1, length, height);                      //画底座
        int size=5;
        for(int i=0;i<pointA.length;i++) {                       //画塔上的点
            g.fillOval(pointA[i].getX()-size/2, pointA[i].getY()-size/2, size, size);
            g.fillOval(pointB[i].getX()-size/2, pointB[i].getY()-size/2, size, size);
            g.fillOval(pointC[i].getX()-size/2, pointC[i].getY()-size/2, size, size);
        }                                                       //标识
        g.drawString(towerName[0]+"座", pointA[amountOfDisc-1].getX(), pointA[amountOfDisc-1].getY()+50);
        g.drawString(towerName[1]+"座", pointB[amountOfDisc-1].getX(), pointB[amountOfDisc-1].getY()+50);
        g.drawString(towerName[2]+"座", pointC[amountOfDisc-1].getX(), pointC[amountOfDisc-1].getY()+50);
    }

    public void setAmountOfDisc(int number) {
        if(number<=1)
            amountOfDisc=1;
        else
            amountOfDisc=number;
    }

    public void setMaxDiscWidth(int maxDiscWidth) {
        this.maxDiscWidth = maxDiscWidth;
    }

    public void setMinDiscWidth(int minDiscWidth) {
        this.minDiscWidth = minDiscWidth;
    }

    public void setDiscHeight(int discHeight) {
        this.discHeight = discHeight;
    }

    public void setAutoMoveDisc(AutoMoveDisc autoMoveDisc) {
        this.autoMoveDisc = autoMoveDisc;
    }

    public AutoMoveDisc getAutoMoveDisc() {
        return autoMoveDisc;
    }


    public void setOtherOfDisc(OtherOfDisc otherOfDisc) {
        this.otherOfDisc=otherOfDisc;
    }

    public OtherOfDisc getOtherOfDisc() {
        return otherOfDisc;
    }

}
