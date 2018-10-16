/**
 * 塔点：
 * 属性 坐标
 * 将盘子从塔上移动
 */

import java.awt.Component;
import java.awt.Container;

public class Point {

    int x,y;
    boolean haveDisc;
    Disc disc = null;

    public boolean equals(Point p) {
        if(p.getX() == this.x && p.getY() == this.getY())
            return true;
        else
            return false;
    }

    public void putDisc(Component com,Container con) {
        disc = (Disc) com;        //该组件就是盘子，容器就是盘点
        con.setLayout(null);
        con.add(disc);            //把组件添加到容器中
        int w = disc.getBounds().width;
        int h = disc.getBounds().height;
        disc.setBounds(x-w/2, y-h/2, w, h);  //将盘子与盘点坐标对准
        haveDisc = true;          //移动盘子后该盘点上有盘子了
        disc.setPoint(this);
        con.validate();
    }

    public Disc getDiscOnPoint() {
        return disc;
    }

    public void removeDisc(Component com,Container con) {
        if(com != null)
            con.remove(com);            //把组件从容器中移走
        con.validate();
    }
    public Point(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public boolean isHaveDisc() {
        return haveDisc;
    }
    public void setHaveDisc(boolean haveDisc) {
        this.haveDisc = haveDisc;
    }
    public void setDisc(Disc disc) {
        this.disc = disc;
    }

}
