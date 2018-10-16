/**
 * 盘子：
 * 若干属性
 */

import java.awt.Color;
import javax.swing.JButton;

public class Disc extends JButton{

    private static final long serialVersionUID = 1L;

    int number;
    Point point;

    Disc(){
        setBackground(Color.pink);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

}
