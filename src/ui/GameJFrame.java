package ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    int[][] data = new int[4][4];//用二维数组存打乱后的图片
    int x, y;//存储0的位置
    int step = 0;//计步器
    String defaultImage = "animal";
    String path = "image\\" + defaultImage + "\\" + defaultImage + "1\\";//默认初始图片路径
    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };
    JMenuItem replayItem = new JMenuItem("重新游戏");//创建下拉对象
    JMenuItem reLoginItem = new JMenuItem("重新登录");//创建下拉对象
    JMenuItem exitItem = new JMenuItem("关闭游戏");//创建下拉对象

    JMenuItem girl = new JMenuItem("美女");
    JMenuItem animal = new JMenuItem("动物");
    JMenuItem sport = new JMenuItem("运动");

    JMenuItem accountItem = new JMenuItem("打灭");//创建下拉对象

    //游戏界面
    public GameJFrame() {

        initMenu();//初始化界面

        initJMenubar();//初始化上面一栏

        initData();//打乱图片

        initImage();//把图片放进去

        this.setVisible(true);//展示画面

        this.addKeyListener(this);
    }

    //打乱图片
    private void initData() {
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};//1.定义一个一维数组
        //2.打乱数组中的数据的顺序
        //遍历数组，得到每一个元素，拿着每一个元素跟随机索引上的数据进行交换
        Random r = new Random();
        for (int i = 0; i < tempArr.length; i++) {
            //获取到随机索引
            int index = r.nextInt(tempArr.length);
            //拿着遍历到的每一个数据，跟随机索引上的数据进行交换
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }

        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] == 0) {
                x = i / 4;
                y = i % 4;//记录0的位置存在x，y
            }
            data[i / 4][i % 4] = tempArr[i];


        }
    }

    //加载图片
    private void initImage() {
        this.getContentPane().removeAll();//每次调用清空原本已经出现的所有图片
        if (victory()) {
            //显示胜利的图标
            JLabel winJLabel = new JLabel(new ImageIcon("image\\win.png"));
            winJLabel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(winJLabel);
        }
        //展示步数
        JLabel stepCount = new JLabel("步数：" + step);
        stepCount.setBounds(50, 30, 100, 20);
        this.getContentPane().add(stepCount);

        //将图片加进去
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num = data[i][j];
                JLabel jLabel = new JLabel(new ImageIcon(path + num + ".jpg")); //创建一个JLabel的对象（管理容器）
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105); //指定图片位置
                jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));//这个功能是产生单个小图片边缘凹凸效果
                this.getContentPane().add(jLabel);//把管理容器添加到 界面中
            }
        }
        //添加背景图片，后加的放下面
        JLabel background = new JLabel(new ImageIcon("image/background.png"));
        background.setBounds(40, 40, 508, 560);
        //把背景图片添加到界面当中
        this.getContentPane().add(background);

        this.getContentPane().repaint();//刷新界面
    }

    //初始化上面一栏
    private void initJMenubar() {
        JMenuBar menuBar = new JMenuBar();//创建菜单对象

        JMenu functionMenu = new JMenu("功能");//创建选项对象
        JMenu aboutMenu = new JMenu("关于我们");//创建选项对象
        JMenu changeItem = new JMenu("更换图片");//创建下拉对象

        changeItem.add(girl);
        changeItem.add(animal);
        changeItem.add(sport);

        functionMenu.add(changeItem);
        functionMenu.add(replayItem);
        functionMenu.add(reLoginItem);
        functionMenu.add(exitItem);

        aboutMenu.add(accountItem);

        menuBar.add(functionMenu);
        menuBar.add(aboutMenu);

        //给下面的选项绑定事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        exitItem.addActionListener(this);
        accountItem.addActionListener(this);
        girl.addActionListener(this);
        animal.addActionListener(this);
        sport.addActionListener(this);

        this.setJMenuBar(menuBar);
    }

    //初始化界面
    private void initMenu() {
        this.setTitle("拼图游戏");
        this.setSize(603, 680);
        this.setAlwaysOnTop(true);//设置顶置
        this.setLocation(650, 280);//设置窗口初始位置
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//设置点击，关闭退出程序
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 96) {//数字键0
            //把界面中所有的图片全部删除
            this.getContentPane().removeAll();
            //加载第一张完整的图片
            JLabel all = new JLabel(new ImageIcon(path + "all.jpg"));
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);
            //加载背景图片
            //添加背景图片
            JLabel background = new JLabel(new ImageIcon("image\\background.png"));
            background.setBounds(40, 40, 508, 560);
            //把背景图片添加到界面当中
            this.getContentPane().add(background);
            //刷新界面
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {//将空白区域的数字移动
        if (victory()) {//判断是否胜利
            //结束方法
            return;
        }
        int code = e.getKeyCode();//左：37 上：38 右：39 下：40 数字键 0: 96 数字键 1: 97 数字键 2: 98
        if (code == 37) {
            System.out.println("向左移动");
            if (y == 3) {
                return;
            }
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            //每移动一次，计数器就自增一次。
            step++;
            initImage();//调用初始化图片方法
        } else if (code == 38) {
            System.out.println("向上移动");
            if (x == 3) {
                return;
            }
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            //每移动一次，计数器就自增一次。
            step++;
            initImage();//调用初始化图片方法
        } else if (code == 39) {
            System.out.println("向右移动");
            if (y == 0) {
                return;
            }
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            //每移动一次，计数器就自增一次。
            step++;
            initImage();//调用初始化图片方法
        } else if (code == 40) {
            System.out.println("向下移动");
            if (x == 0) {
                return;
            }
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            //每移动一次，计数器就自增一次。
            step++;
            initImage();//调用初始化图片方法
        } else if (code == 97) {//数字键1表示作弊
            data = win;
            initImage();
            System.out.println("作弊成功");
        } else if (code == 96) {//按住数字键0表示暂时显示完整图片
            System.out.println("暂时显示完整图片");
            initImage();
        }
    }

    public boolean victory() {//判断是否胜利
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == replayItem) {
            System.out.println("重新游戏");
            //计步器清零
            step = 0;
            //再次打乱二维数组中的数据
            initData();
            //重新加载图片
            initImage();
        } else if (obj == reLoginItem) {
            System.out.println("重新登录");
            this.setVisible(false);
            //打开登录界面
            new LoginJFrame();
        } else if (obj == exitItem) {
            System.out.println("关闭游戏");
            System.exit(0);
        } else if (obj == accountItem) {
            System.out.println("打灭");
            JDialog dialog = new JDialog();
            JLabel jLabel = new JLabel(new ImageIcon("image//damie.jpg"));
            //设置位置和宽高
            jLabel.setBounds(0, 0, 258, 258);
            //把图片添加到弹框当中
            dialog.getContentPane().add(jLabel);
            //给弹框设置大小
            dialog.setSize(344, 344);
            //让弹框置顶
            dialog.setAlwaysOnTop(true);
            //让弹框居中
            dialog.setLocationRelativeTo(null);
            //弹框不关闭则无法操作下面的界面
            dialog.setModal(true);
            //让弹框显示出来
            dialog.setVisible(true);
        } else if (obj == girl) {
            System.out.println("更换girl图片");
            defaultImage = "girl";
            changeGirl(defaultImage);
            System.out.println(defaultImage);
            System.out.println(path);
            //计步器清零
            step = 0;
            //再次打乱二维数组中的数据
            initData();
            //重新加载图片
            initImage();
        } else if (obj == animal) {
            System.out.println("更换animal图片");
            defaultImage = "animal";
            changeAnimal(defaultImage);
            //计步器清零
            step = 0;
            //再次打乱二维数组中的数据
            initData();
            //重新加载图片
            initImage();
        } else if (obj == sport) {
            System.out.println("更换sport图片");
            defaultImage = "sport";
            changeSport(defaultImage);
            //计步器清零
            step = 0;
            //再次打乱二维数组中的数据
            initData();
            //重新加载图片
            initImage();
        }
    }

    //更换path图片路径
    public void changeGirl(String defaultImage) {
        Random r = new Random();
        int i = r.nextInt(12) + 1;
        path = "image\\" + defaultImage + "\\" + defaultImage + i + "\\";
    }

    //更换path图片路径
    public void changeAnimal(String defaultImage) {
        Random r = new Random();
        int i = r.nextInt(7) + 1;
        path = "image\\" + defaultImage + "\\" + defaultImage + i + "\\";
    }

    //更换path图片路径
    public void changeSport(String defaultImage) {
        Random r = new Random();
        int i = r.nextInt(9) + 1;
        path = "image\\" + defaultImage + "\\" + defaultImage + i + "\\";
    }
}
