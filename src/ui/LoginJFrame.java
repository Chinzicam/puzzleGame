package ui;

import javax.swing.*;

public class LoginJFrame extends JFrame {
    public LoginJFrame() {//登录界面
        this.setTitle("拼图 登录");
        this.setSize(488, 430);
        this.setAlwaysOnTop(true);//设置顶置
        this.setLocation(650, 280);//设置窗口初始位置
        this.setDefaultCloseOperation(3);//设置点击，关闭退出程序
        this.setVisible(true);//展示画面
    }
}
