import Server.Pictrue;
import Server.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Start {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        startWindow();
    }

    public static void startWindow() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        Cilent cilent = new Cilent();
        //开始界面
        JFrame f1 = new JFrame("登陆界面");
        f1.setSize(280, 300);
        f1.setLocation(580, 200);
        f1.setLayout(null);
        f1.getContentPane().setBackground(Color.WHITE);
        ImageIcon icon_tou=new ImageIcon("D:\\IDEA project\\Client\\pics\\camera.jpg");
        f1.setIconImage(icon_tou.getImage());
        JLabel logoLabel =  new JLabel(new ImageIcon("D:\\IDEA project\\Client\\pics\\san.jpg"));//100*250
        logoLabel.setBounds(10,10,250,100);

        JLabel nameLabel = new JLabel("用户名：");
        nameLabel.setBounds(10, 87, 100, 100);
        JTextField nameField = new JTextField();
        nameField.setBounds(60, 125, 200, 25);

        JLabel passwordLabel = new JLabel("密码：");
        passwordLabel.setBounds(20, 125, 100, 100);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(60, 165, 200, 25);

        JButton loginButton = new JButton("登 陆");
        loginButton.setBounds(25, 210, 100, 30);
        JButton registerButton = new JButton("注 册");
        registerButton.setBounds(145, 210, 100, 30);

        f1.add(nameLabel);
        f1.add(nameField);
        f1.add(passwordLabel);
        f1.add(passwordField);
        f1.add(loginButton);
        f1.add(registerButton);
        f1.add(logoLabel);

        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setVisible(true);
        nameField.grabFocus();
        passwordField.grabFocus();

        //点击注册
        JFrame f2 = new JFrame("注册界面");
        f2.setSize(352, 190);
        f2.setLocation(580, 200);
        f2.setLayout(null);
        JLabel name = new JLabel("用户名：");
        name.setBounds(40, 20, 100, 30);
        JTextField nameText = new JTextField();
        nameText.setBounds(90, 20, 200, 30);
        JLabel password = new JLabel("密码：");
        password.setBounds(50, 60, 100, 30);
        JTextField passwordText = new JTextField();
        passwordText.setBounds(90, 60, 200, 30);
        JButton ok = new JButton("确认");
        ok.setBounds(120, 110, 100, 30);
        //为确认键增加按钮事件监听
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane tishi = new JOptionPane();

                if(passwordText.getText().isEmpty() || nameText.getText().isEmpty()){
                    tishi.showMessageDialog(null, "注册失败！请确认是否输入用户名或密码");
                }else {

                    //给服务器发送询问请求

                    try {
                        cilent.register(nameText.getText(),passwordText.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    JOptionPane.showConfirmDialog(null,"确认注册？", "确认", JOptionPane.YES_NO_OPTION);

                    if (cilent.cilentHandleThread.pic.type != 5) {
                        tishi.showMessageDialog(null, "注册成功！即将返回登陆界面");
                        f2.setVisible(false);
                        f1.setVisible(true);
                    }else{
                        tishi.showMessageDialog(null, "注册失败！用户名已存在");
                    }
                }
            }
        });
        f2.add(name);
        f2.add(nameText);
        f2.add(password);
        f2.add(passwordText);
        ButtonGroup xingbie = new ButtonGroup();
        f2.add(ok);
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f2.getContentPane().setBackground(Color.WHITE);
        f2.setVisible(false);

        //增加注册按钮事件监听
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f1.setVisible(false);
                f2.setVisible(true);
            }
        });

        //增加登陆按钮事件监听
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //给服务器发送消息
                try {
                    cilent.loginIn(nameField.getText(),String.valueOf(passwordField.getPassword()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                if(JOptionPane.showConfirmDialog(null,"确认登陆？", "确认", JOptionPane.YES_NO_OPTION) == 0){
                    if (cilent.cilentHandleThread.pic.isRight) {
                        JOptionPane.showMessageDialog(null, "登陆成功");
                        System.out.println("开mainWindow前");
                        MainWindow mainWindow = new MainWindow(cilent,nameField.getText(),String.valueOf(passwordField.getPassword()));
                        System.out.println("开mainWindow后");
                        f1.setVisible(false);
                    }else{
                        JOptionPane.showMessageDialog(null, "登录失败，账号密码错误");
                    }
                }
            }
        });
    }
}