/**
 * 计时
 * 手动点击开始后计时，完成后手动结束
 * 中途可以暂停，可继续
 * 新一局游戏之前清零计时
 */

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Timer extends JPanel {

    private static final long serialVersionUID = 1L;

    //计时字符串
    private static final String INITIAL_LABEL_TEXT = "00:00:00 000";

    // 计数线程
    CountingThread countingThread = new CountingThread();

    // 记录程序打开的时间
    private long programStart = System.currentTimeMillis();

    //记录每一轮开始暂停的时间点，默认程序一开始就是暂停的
    private long pauseStart = programStart;

    // 程序暂停的总时间
    private long pauseCount = 0;

    private JLabel label = new JLabel(INITIAL_LABEL_TEXT);

    //开始按钮，点击事件后可变成 暂停，继续
    JButton startPauseButton;

    //清零按钮
    JButton resetButton;

    public Timer(){

        Font newFont = new Font("微软雅黑", Font.BOLD,15);
        startPauseButton = new JButton("开始");
        resetButton = new JButton("清零");
        startPauseButton.setFont(newFont);
        resetButton.setFont(newFont);

        setLayout(new BorderLayout());
        JPanel pp = new JPanel();
        pp.add(startPauseButton);
        pp.add(resetButton);

        startPauseButton.addActionListener(startPauseButtonListener);//开始、暂停、继续 按钮事件
        resetButton.addActionListener(resetButtonListener);//清零 按钮事件

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 40));
        this.add(label, BorderLayout.CENTER);
        this.add(pp,BorderLayout.SOUTH);

        countingThread.start();

    }

    private ActionListener startPauseButtonListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {                                      //点击 "开始"或"继续"
            if (countingThread.stopped) {//当线程出于停止状态
                pauseCount += (System.currentTimeMillis() - pauseStart);//总暂停时间
                countingThread.stopped = false;//线程开始
                startPauseButton.setText("暂停");
            } else {                                                                      //点击 "暂停"
                pauseStart = System.currentTimeMillis();//新一轮的开始暂停的计时
                countingThread.stopped = true;//线程停止
                startPauseButton.setText("继续");
            }
        }
    };

    //清零操作
    private ActionListener resetButtonListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            pauseStart = programStart;                                                    //程序回到刚打开的状态
            pauseCount = 0;
            countingThread.stopped = true;
            label.setText(INITIAL_LABEL_TEXT);
            startPauseButton.setText("开始");

        }
    };

    //线程子类
    private class CountingThread extends Thread {

        public boolean stopped = true;

        private CountingThread() {
            setDaemon(true);           //将该线程标记为守护线程或用户线程。
        }

        @Override
        public void run() {
            while (true) {
                if (!stopped) {
                    long elapsed = System.currentTimeMillis() - programStart - pauseCount;
                    //计时显示为=当前时间-程序打开时间-总暂停事件
                    label.setText(format(elapsed));
                }

                try {
                    sleep(1);  // 1毫秒更新一次显示
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }

        // 将毫秒数格式化
        private String format(long elapsed) {
            int hour, minute, second, milli;

            milli = (int) (elapsed % 1000);
            elapsed = elapsed / 1000;

            second = (int) (elapsed % 60);
            elapsed = elapsed / 60;

            minute = (int) (elapsed % 60);
            elapsed = elapsed / 60;

            hour = (int) (elapsed % 60);

            return String.format("%02d:%02d:%02d %03d", hour, minute, second, milli);
        }

    }


}
