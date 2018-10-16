/**
 * 鼠标点击、移动、释放盘子等过程
 *
 */

import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class HandleMouse implements MouseListener,MouseMotionListener{

    Point[] pointA,pointB,pointC;
    Point startPoint = null,endPoint=null;
    int leftX,leftY,x0,y0;
    boolean move = false;
    Container con;

    HandleMouse(Container con){
        this.con = con;
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

    @Override
    public void mouseDragged(MouseEvent e) {       //鼠标移动盘子时位置变化
        Disc disc = null;
        disc = (Disc) e.getSource();
        leftX=disc.getBounds().x;
        leftY=disc.getBounds().y;
        int x=e.getX();
        int y=e.getY();
        leftX=leftX+x;
        leftY=leftY+y;
        if(move == true)
            disc.setLocation(leftX-x0, leftY-y0);
    }

    @Override
    public void mousePressed(MouseEvent e) {    //鼠标选中盘子按下
        move = false;
        Disc disc = null;
        disc = (Disc) e.getSource();
        startPoint = disc.getPoint();
        x0=e.getX();
        y0=e.getY();
        int m=0;
        for(int i=0; i<pointA.length; i++) {       //当盘子位置超出塔范围时，变成鼠标移动盘子操作
            if(pointA[i].equals(startPoint)) {
                m = i;
                if(m>0&&(pointA[m-1].isHaveDisc() == false)) {
                    move = true;
                    break;
                }else if(m ==0) {
                    move=true;
                    break;
                }
            }
        }
        for(int i=0;i<pointB.length;i++) {
            if(pointB[i].equals(startPoint)) {
                m=i;
                if(m>0&&(pointB[m-1].isHaveDisc() == false)) {
                    move = true;
                    break;
                }else if(m ==0) {
                    move=true;
                    break;
                }
            }
        }
        for(int i=0;i<pointC.length;i++) {
            if(pointC[i].equals(startPoint)) {
                m=i;
                if(m>0&&(pointC[m-1].isHaveDisc() == false)) {
                    move = true;
                    break;
                }else if(m ==0) {
                    move=true;
                    break;
                }
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {         //鼠标释放，盘子放到某塔上，覆盖某塔点
        Disc disc = null;
        disc =(Disc)e.getSource();
        Rectangle rect = disc.getBounds();
        boolean location= false;
        int x=-1,y=-1;

        for(int i=0;i<pointA.length;i++) {
            x=pointA[i].getX();
            y=pointA[i].getY();
            if(rect.contains(x, y)) {
                endPoint=pointA[i];
                if(i==pointA.length-1 && endPoint.isHaveDisc() == false) {
                    location=true;
                    break;
                }else if(i<pointA.length-1 && pointA[i+1].isHaveDisc()==true && endPoint.isHaveDisc() ==false
                        && pointA[i+1].getDiscOnPoint().getNumber() >disc.getNumber() ) {
                    location = true;
                    break;
                }
            }
        }

        for(int i=0;i<pointB.length;i++) {
            x=pointB[i].getX();
            y=pointB[i].getY();
            if(rect.contains(x, y)) {
                endPoint=pointB[i];
                if(i==pointB.length-1 && endPoint.isHaveDisc() == false) {
                    location=true;
                    break;
                }else if(i<pointB.length-1 && pointB[i+1].isHaveDisc()==true && endPoint.isHaveDisc() ==false
                        && pointB[i+1].getDiscOnPoint().getNumber() >disc.getNumber() ) {
                    location = true;
                    break;
                }
            }
        }

        for(int i=0;i<pointC.length;i++) {
            x=pointC[i].getX();
            y=pointC[i].getY();
            if(rect.contains(x, y)) {
                endPoint=pointC[i];
                if(i==pointC.length-1 && endPoint.isHaveDisc() == false) {
                    location=true;
                    break;
                }else if(i<pointC.length-1 && pointC[i+1].isHaveDisc()==true && endPoint.isHaveDisc() ==false
                        && pointC[i+1].getDiscOnPoint().getNumber() >disc.getNumber() ) {
                    location = true;
                    break;
                }
            }
        }

        if(endPoint != null && location==true) {
            endPoint.putDisc(disc, con);
            startPoint.setHaveDisc(false);
        }else {
            startPoint.putDisc(disc, con);
        }
    }
    @Override
    public void mouseMoved(MouseEvent e){}

    @Override
    public void mouseClicked(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e){}

}
