import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.SplitPaneUI;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import Server.*;

public class MainWindow {
    public static String paths="";
    Pictrue pictrue = null;
    MainWindow(Cilent cilent,String name,String password) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch(Exception e) {
            System.out.println(e);
        }
        //前面的即是基本的界面布局
        JFrame f = new JFrame("颜值PK神器");
        JPanel con=new JPanel();
        con.setSize(800,600);
        f.setResizable(false);//固定窗口大小
        f.setSize(800, 600);//整个窗口大小
        f.setLocation(200, 200);//整个窗口位置
        f.setLayout(null);//不设置布局
        ImageIcon icon_tou=new ImageIcon("D:\\IDEA project\\Client\\pics\\camera.jpg");
        f.setIconImage(icon_tou.getImage());
        JButton b2=new JButton("上传");
        b2.setBounds(200,400,80,30);//b2是发送按钮

        JLabel lp=new JLabel("");
        int width1=350;//方便设置图片的大小
        int height=350;
        JLabel pic=new JLabel();//pic用于展现图片
        pic.setBounds(23,10,350,350);
        JButton bOpen = new JButton("打开文件");
        bOpen.setBounds(100,400,80,30);
        bOpen.addActionListener(new ActionListener() {
            @Override
            //这个用来打开照片，首选png、jpg、gif，但是所有文件也可用
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileFilter filter = new FileFilter() {
                    //要过滤的文件
                    public boolean accept(File f) {
                        //显示的文件类型
                        if (f.isDirectory()) {
                            return true;
                        }
                        //显示满足条件的文件
                        return f.getName().endsWith(".jpg") || f.getName().endsWith(".gif")|| f.getName().endsWith(".png");
                    }
                    /**
                     * 这就是显示在打开框中
                     */
                    public String getDescription() {
                        return "*.jpg,*.gif,*.png";
                    }
                };
                //jdk1.6 FileNameExtensionFilter
                //new FileNameExtensionFilter("图像文件(jpg/gif)","jpg","jpeg","gif");
                fileChooser.setFileFilter(filter);
                int i = fileChooser.showOpenDialog(f);
                if(i==JFileChooser.APPROVE_OPTION)  //判断是否为打开的按钮
                {
                    File selectedFile = fileChooser.getSelectedFile();  //取得选中的文件
                    lp.setText(selectedFile.getPath());   //取得路径
                    paths=selectedFile.getAbsolutePath();
                    ImageIcon image = new ImageIcon(paths);
                    lp.setText("文件目录：" + paths);
                    System.out.println(paths);
                    image.setImage(image.getImage().getScaledInstance(width1, height,Image.SCALE_DEFAULT ));
                    pic.setIcon(image);
                }
                b2.setEnabled(true);
            }
        });

        JButton shoot = new JButton("拍照上传");
        shoot.setBounds(100,450,180,30);
        shoot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    paths = Test.take_photos(lp,pic) + ".jpg";
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                b2.setEnabled(true);
            }
        });



        JButton refresh = new JButton("刷新");
        refresh.setBounds(120,400,60,30);

        JPanel pLeft = new JPanel();//大的左面板
        pLeft.setLayout(null);
        pLeft.setSize(400,600);
        lp.setBounds(70,500,370,30);
        pLeft.setBounds(0, 0, 400, 600);


        //左边的面板添加这些基本的控件

        pLeft.add(bOpen);
        pLeft.add(pic);
        pLeft.add(b2);
        pLeft.add(lp);
        pLeft.add(shoot);
        //现在开始处理右边的这个选项卡，有三个面板
        JPanel p1 = new JPanel();
        p1.setLayout(null);
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        p2.setLayout(null);
        p3.setLayout(null);
        //如下全部是P1的控件
        JLabel basic_name=new JLabel("用户名: ");
        basic_name.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        JLabel basic_name_s=new JLabel(name);//用来展示用户名，代填数据
        basic_name_s.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        JLabel basic_beauty=new JLabel("颜值: ");
        basic_beauty.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        JLabel basic_beauty_s=new JLabel();//这里修改
        basic_beauty_s.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        JLabel basic_age=new JLabel("年龄: ");
        basic_age.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        JLabel basic_age_s=new JLabel();//这里填入修改
        basic_age_s.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        JLabel basic_sex=new JLabel("性别: ");
        basic_sex.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        JLabel basic_sex_s=new JLabel();//这里修改
        basic_sex_s.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        JLabel basic_emotion=new JLabel("情绪: ");
        basic_emotion.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        JLabel basic_emotion_s=new JLabel();//这里修改
        basic_emotion_s.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        JLabel basic_faceType=new JLabel("类型: ");
        basic_faceType.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        JLabel basic_faceType_s=new JLabel();//这里修改
        basic_faceType_s.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        JLabel basic_race=new JLabel("人种: ");
        basic_race.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        JLabel basic_race_s=new JLabel();//这里修改
        basic_race_s.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        JLabel h1=new JLabel("");
        basic_name.setBounds(20,70,120,30);
        basic_name_s.setBounds(130,70,200,30);
        basic_beauty.setBounds(20,110,120,30);
        basic_beauty_s.setBounds(130,110,200,30);
        basic_age.setBounds(20,150,120,30);
        basic_age_s.setBounds(130,150,200,30);
        basic_sex.setBounds(20,190,120,30);
        basic_sex_s.setBounds(130,190,200,30);
        basic_emotion.setBounds(20,230,120,30);
        basic_emotion_s.setBounds(130,230,200,30);
        basic_faceType.setBounds(20,270,120,30);
        basic_faceType_s.setBounds(130,270,200,30);
        basic_race.setBounds(20,310,120,30);
        basic_race_s.setBounds(130,310,200,30);
        p1.add(basic_name);
        p1.add(basic_name_s);
        p1.add(basic_beauty);
        p1.add(basic_beauty_s);
        p1.add(basic_age);
        p1.add(basic_age_s);
        p1.add(basic_sex);
        p1.add(basic_sex_s);
        p1.add(basic_emotion);
        p1.add(basic_emotion_s);
        p1.add(basic_faceType);
        p1.add(basic_faceType_s);
        p1.add(basic_race);
        p1.add(basic_race_s);
        //p1的控件就此
        //开始处理p2的控件

        JLabel pic1=new JLabel();
        pic1.setBounds(5,0,100,100);
        JLabel rank1=new JLabel("username1",JLabel.LEFT);
        rank1.setBounds(120,0,150,25);
        JLabel rank11=new JLabel("point",JLabel.LEFT);
        rank11.setBounds(120,25,150,25);
        JButton rank12=new JButton("赞ta");
        rank12.setBounds(115,50,80,45);
        JLabel div1=new JLabel();
        ImageIcon icon_dl=new ImageIcon("././out/picture/DL.png");
        div1.setIcon(icon_dl);
        div1.setBounds(0,100,400,5);
        JLabel like1=new JLabel("点赞数:");
        like1.setBounds(210,50,45,45);
        JLabel likenum1 = new JLabel("0");
        likenum1.setBounds(260,50,60,45);
        rank12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rank12.getText().equals("赞ta")) {
                    try {
                        cilent.like(rank1.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum1.setText(Integer.toString(Integer.parseInt(likenum1.getText())+1));
                    rank12.setText("取消赞");
                }else{
                    try {
                        cilent.unlike(rank1.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum1.setText(Integer.toString(Integer.parseInt(likenum1.getText())-1));
                    rank12.setText("赞ta");
                }
            }
        });
        p2.add(rank1);
        p2.add(rank11);
        p2.add(rank12);
        p2.add(like1);
        p2.add(div1);
        p2.add(likenum1);
        p2.add(pic1);
        //处理第二子模块
        JLabel pic2=new JLabel();
        pic2.setBounds(5,103,100,100);
        JLabel rank2=new JLabel("username2",JLabel.LEFT);
        rank2.setBounds(120,105,150,25);
        JLabel rank21=new JLabel("point2",JLabel.LEFT);
        rank21.setBounds(120,130,150,25);
        JLabel div2=new JLabel();
        div2.setIcon(icon_dl);
        JButton rank22=new JButton("赞ta");
        rank22.setBounds(115,155,80,45);
        div2.setBounds(0,205,400,5);
        JLabel like2=new JLabel("点赞数:");
        like2.setBounds(210,155,45,45);
        JLabel likenum2 = new JLabel("0");
        likenum2.setBounds(260,155,60,45);
        rank22.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rank22.getText().equals("赞ta")) {
                    try {
                        cilent.like(rank2.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum2.setText(Integer.toString(Integer.parseInt(likenum2.getText())+1));
                    rank22.setText("取消赞");
                }else{
                    try {
                        cilent.unlike(rank2.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum2.setText(Integer.toString(Integer.parseInt(likenum2.getText())-1));
                    rank22.setText("赞ta");
                }
            }
        });
        p2.add(rank2);
        p2.add(rank21);
        p2.add(rank22);
        p2.add(div2);
        p2.add(like2);
        p2.add(likenum2);
        p2.add(pic2);

        //第三个
        JLabel pic3=new JLabel();
        pic3.setBounds(5,207,100,100);
        JLabel rank3=new JLabel("username3",JLabel.LEFT);
        rank3.setBounds(120,210,150,25);
        JLabel rank31=new JLabel("point3",JLabel.LEFT);
        rank31.setBounds(120,235,150,25);
        JLabel div3=new JLabel();
        div3.setIcon(icon_dl);
        JButton rank32=new JButton("赞ta");
        rank32.setBounds(115,260,80,45);
        div3.setBounds(0,310,400,5);
        JLabel like3=new JLabel("点赞数:");
        like3.setBounds(210,260,45,45);
        JLabel likenum3 = new JLabel("0");
        likenum3.setBounds(260,260,60,45);
        rank32.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rank32.getText().equals("赞ta")) {
                    try {
                        cilent.like(rank3.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum3.setText(Integer.toString(Integer.parseInt(likenum3.getText())+1));
                    rank32.setText("取消赞");
                }else{
                    try {
                        cilent.unlike(rank3.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum3.setText(Integer.toString(Integer.parseInt(likenum3.getText())-1));
                    rank32.setText("赞ta");
                }
            }
        });
        p2.add(rank3);
        p2.add(rank31);
        p2.add(rank32);
        p2.add(div3);
        p2.add(like3);
        p2.add(likenum3);
        p2.add(pic3);

        //第四个
        JLabel pic4=new JLabel();
        pic4.setBounds(5,312,100,100);
        JLabel rank4=new JLabel("username4",JLabel.LEFT);
        rank4.setBounds(120,315,150,25);
        JLabel rank41=new JLabel("point4",JLabel.LEFT);
        rank41.setBounds(120,340,150,25);
        JLabel div4=new JLabel();
        div4.setIcon(icon_dl);
        JButton rank42=new JButton("赞ta");
        rank42.setBounds(115,365,80,45);
        div4.setBounds(0,415,400,5);
        JLabel like4=new JLabel("点赞数:");
        like4.setBounds(210,365,45,45);
        JLabel likenum4 = new JLabel("0");
        likenum4.setBounds(260,365,60,45);
        rank42.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rank42.getText().equals("赞ta")) {
                    try {
                        cilent.like(rank4.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum4.setText(Integer.toString(Integer.parseInt(likenum4.getText())+1));
                    rank42.setText("取消赞");
                }else{
                    try {
                        cilent.unlike(rank4.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum4.setText(Integer.toString(Integer.parseInt(likenum4.getText())-1));
                    rank42.setText("赞ta");
                }
            }
        });
        p2.add(rank4);
        p2.add(rank41);
        p2.add(rank42);
        p2.add(div4);
        p2.add(like4);
        p2.add(likenum4);
        p2.add(pic4);

        //第五个
        JLabel pic5=new JLabel();
        pic5.setBounds(5,417,100,100);
        JLabel rank5=new JLabel("username5",JLabel.LEFT);
        rank5.setBounds(120,425,150,25);
        JLabel rank51=new JLabel("point5",JLabel.LEFT);
        rank51.setBounds(120,450,150,25);
        JLabel div5=new JLabel();
        div5.setIcon(icon_dl);
        JButton rank52=new JButton("赞ta");
        rank52.setBounds(115,475,80,45);
        div5.setBounds(0,520,400,5);
        JLabel like5=new JLabel("点赞数:");
        like5.setBounds(210,475,45,45);
        JLabel likenum5 = new JLabel("0");
        likenum5.setBounds(260,475,60,45);
        rank52.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rank52.getText().equals("赞ta")) {
                    try {
                        cilent.like(rank5.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum5.setText(Integer.toString(Integer.parseInt(likenum5.getText())+1));
                    rank52.setText("取消赞");
                }else{
                    try {
                        cilent.unlike(rank5.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum5.setText(Integer.toString(Integer.parseInt(likenum5.getText())-1));
                    rank52.setText("赞ta");
                }
            }
        });
        p2.add(rank5);
        p2.add(rank51);
        p2.add(rank52);
        p2.add(div5);
        p2.add(like5);
        p2.add(likenum5);
        p2.add(pic5);

        JButton getInfo = new JButton("刷新");
        getInfo.setBounds(110,532,80,25);
        getInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cilent.loginIn(name,password);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                JOptionPane tips = new JOptionPane();
                tips.showMessageDialog(null, "更新成功");

                List<Pictrue> pictrueList = cilent.cilentHandleThread.pictrues;
                int size = pictrueList.size();
                System.out.println(pictrueList.size());
                for(int i = 0; i < size; i ++){
                    System.out.println("现在list数量"+pictrueList.size());
                    double max = 0;
                    int place = 0;
                    for(int j = 0; j < pictrueList.size(); j ++){
                        if(pictrueList.get(j).beauty > max){
                            max = pictrueList.get(j).beauty;
                            place = j;
                        }
                    }
                    switch (i) {
                        case 0:rank1.setText(String.valueOf(pictrueList.get(place).name));
                            rank11.setText(String.valueOf(pictrueList.get(place).beauty));
                            likenum1.setText(String.valueOf(pictrueList.get(place).likes));
                            System.out.println(0);
                            savePic(pictrueList.get(place).b,pictrueList.get(place).name);

                            ImageIcon image1 = new ImageIcon("clientPics/" + pictrueList.get(place).name + ".jpg");
                            image1.setImage(image1.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                            pic1.setIcon(image1);
                            System.out.println("clientPics/" + pictrueList.get(place).name + ".jpg");

                            break;
                        case 1:rank2.setText(String.valueOf(pictrueList.get(place).name));
                            rank21.setText(String.valueOf(pictrueList.get(place).beauty));
                            likenum2.setText(String.valueOf(pictrueList.get(place).likes));
                            System.out.println(1);
                            savePic(pictrueList.get(place).b,pictrueList.get(place).name);
                            ImageIcon image2 = new ImageIcon("clientPics/" + pictrueList.get(place).name + ".jpg");
                            image2.setImage(image2.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                            pic2.setIcon(image2);
                            System.out.println("clientPics/" + pictrueList.get(place).name + ".jpg");
                            break;
                        case 2:rank3.setText(String.valueOf(pictrueList.get(place).name));
                            rank31.setText(String.valueOf(pictrueList.get(place).beauty));
                            likenum3.setText(String.valueOf(pictrueList.get(place).likes));
                            System.out.println(2);
                            savePic(pictrueList.get(place).b,pictrueList.get(place).name);
                            ImageIcon image3 = new ImageIcon("clientPics/" + pictrueList.get(place).name + ".jpg");
                            image3.setImage(image3.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                            pic3.setIcon(image3);
                            System.out.println("clientPics/" + pictrueList.get(place).name + ".jpg");
                            break;
                        case 3:rank4.setText(String.valueOf(pictrueList.get(place).name));
                            rank41.setText(String.valueOf(pictrueList.get(place).beauty));
                            likenum4.setText(String.valueOf(pictrueList.get(place).likes));
                            System.out.println(3);
                            savePic(pictrueList.get(place).b,pictrueList.get(place).name);
                            ImageIcon image4 = new ImageIcon("clientPics/" + pictrueList.get(place).name + ".jpg");
                            image4.setImage(image4.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                            pic4.setIcon(image4);
                            System.out.println("clientPics/" + pictrueList.get(place).name + ".jpg");
                            break;
                        case 4:rank5.setText(String.valueOf(pictrueList.get(place).name));
                            rank51.setText(String.valueOf(pictrueList.get(place).beauty));
                            likenum5.setText(String.valueOf(pictrueList.get(place).likes));
                            System.out.println(4);
                            savePic(pictrueList.get(place).b,pictrueList.get(place).name);
                            ImageIcon image5 = new ImageIcon("clientPics/" + pictrueList.get(place).name + ".jpg");
                            image5.setImage(image5.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                            pic5.setIcon(image5);
                            System.out.println("clientPics/" + pictrueList.get(place).name + ".jpg");
                            break;

                        default:System.out.println(421321);break;
                    }
                    pictrueList.remove(place);
                }
            }
        });
        p2.add(getInfo);

//点赞排行
        JPanel p5 = new JPanel();
        p5.setLayout(null);
        JLabel pic1s=new JLabel();
        pic1s.setBounds(5,0,100,100);
        JLabel rank1s=new JLabel("username1",JLabel.LEFT);
        rank1s.setBounds(120,0,150,25);
        JLabel rank11s=new JLabel("point",JLabel.LEFT);
        rank11s.setBounds(120,25,150,25);
        JButton rank12s=new JButton("赞ta");
        rank12s.setBounds(115,50,80,45);
        JLabel div1s=new JLabel();
        ImageIcon icon_dls=new ImageIcon("././out/picture/DL.png");
        div1s.setIcon(icon_dl);
        div1s.setBounds(0,100,400,5);
        JLabel like1s=new JLabel("点赞数:");
        like1s.setBounds(210,50,45,45);
        JLabel likenum1s = new JLabel("0");
        likenum1s.setBounds(260,50,60,45);
        rank12s.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rank12s.getText().equals("赞ta")) {
                    try {
                        cilent.like(rank1s.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum1s.setText(Integer.toString(Integer.parseInt(likenum1s.getText())+1));
                    rank12s.setText("取消赞");
                }else{
                    try {
                        cilent.unlike(rank1s.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum1s.setText(Integer.toString(Integer.parseInt(likenum1s.getText())-1));
                    rank12s.setText("赞ta");
                }
            }
        });
        p5.add(rank1s);
        p5.add(rank11s);
        p5.add(rank12s);
        p5.add(like1s);
        p5.add(div1s);
        p5.add(likenum1s);
        p5.add(pic1s);
        //处理第二子模块
        JLabel pic2s=new JLabel();
        pic2s.setBounds(5,103,100,100);
        JLabel rank2s=new JLabel("username2",JLabel.LEFT);
        rank2s.setBounds(120,105,150,25);
        JLabel rank21s=new JLabel("point2",JLabel.LEFT);
        rank21s.setBounds(120,130,150,25);
        JLabel div2s=new JLabel();
        div2s.setIcon(icon_dl);
        JButton rank22s=new JButton("赞ta");
        rank22s.setBounds(115,155,80,45);
        div2s.setBounds(0,205,400,5);
        JLabel like2s=new JLabel("点赞数:");
        like2s.setBounds(210,155,45,45);
        JLabel likenum2s = new JLabel("0");
        likenum2s.setBounds(260,155,60,45);
        rank22s.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rank22s.getText().equals("赞ta")) {
                    try {
                        cilent.like(rank2s.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum2s.setText(Integer.toString(Integer.parseInt(likenum2s.getText())+1));
                    rank22s.setText("取消赞");
                }else{
                    try {
                        cilent.unlike(rank2s.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum2s.setText(Integer.toString(Integer.parseInt(likenum2.getText())-1));
                    rank22s.setText("赞ta");
                }
            }
        });
        p5.add(rank2s);
        p5.add(rank21s);
        p5.add(rank22s);
        p5.add(div2s);
        p5.add(like2s);
        p5.add(likenum2s);
        p5.add(pic2s);

        //第三个
        JLabel pic3s=new JLabel();
        pic3s.setBounds(5,207,100,100);
        JLabel rank3s=new JLabel("username3",JLabel.LEFT);
        rank3s.setBounds(120,210,150,25);
        JLabel rank31s=new JLabel("point3",JLabel.LEFT);
        rank31s.setBounds(120,235,150,25);
        JLabel div3s=new JLabel();
        div3s.setIcon(icon_dl);
        JButton rank32s=new JButton("赞ta");
        rank32s.setBounds(115,260,80,45);
        div3s.setBounds(0,310,400,5);
        JLabel like3s=new JLabel("点赞数:");
        like3s.setBounds(210,260,45,45);
        JLabel likenum3s = new JLabel("0");
        likenum3s.setBounds(260,260,60,45);
        rank32s.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rank32s.getText().equals("赞ta")) {
                    try {
                        cilent.like(rank3s.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum3s.setText(Integer.toString(Integer.parseInt(likenum3s.getText())+1));
                    rank32s.setText("取消赞");
                }else{
                    try {
                        cilent.unlike(rank3s.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum3s.setText(Integer.toString(Integer.parseInt(likenum3s.getText())-1));
                    rank32s.setText("赞ta");
                }
            }
        });
        p5.add(rank3s);
        p5.add(rank31s);
        p5.add(rank32s);
        p5.add(div3s);
        p5.add(like3s);
        p5.add(likenum3s);
        p5.add(pic3s);

        //第四个
        JLabel pic4s=new JLabel();
        pic4s.setBounds(5,312,100,100);
        JLabel rank4s=new JLabel("username4",JLabel.LEFT);
        rank4s.setBounds(120,315,150,25);
        JLabel rank41s=new JLabel("point4",JLabel.LEFT);
        rank41s.setBounds(120,340,150,25);
        JLabel div4s=new JLabel();
        div4s.setIcon(icon_dl);
        JButton rank42s=new JButton("赞ta");
        rank42s.setBounds(115,365,80,45);
        div4s.setBounds(0,415,400,5);
        JLabel like4s=new JLabel("点赞数:");
        like4s.setBounds(210,365,45,45);
        JLabel likenum4s = new JLabel("0");
        likenum4s.setBounds(260,365,60,45);
        rank42s.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rank42s.getText().equals("赞ta")) {
                    try {
                        cilent.like(rank4s.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum4s.setText(Integer.toString(Integer.parseInt(likenum4s.getText())+1));
                    rank42s.setText("取消赞");
                }else{
                    try {
                        cilent.unlike(rank4s.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum4s.setText(Integer.toString(Integer.parseInt(likenum4s.getText())-1));
                    rank42s.setText("赞ta");
                }
            }
        });
        p5.add(rank4s);
        p5.add(rank41s);
        p5.add(rank42s);
        p5.add(div4s);
        p5.add(like4s);
        p5.add(likenum4s);
        p5.add(pic4s);

        //第五个
        JLabel pic5s=new JLabel();
        pic5s.setBounds(5,417,100,100);
        JLabel rank5s=new JLabel("username5",JLabel.LEFT);
        rank5s.setBounds(120,425,150,25);
        JLabel rank51s=new JLabel("point5",JLabel.LEFT);
        rank51s.setBounds(120,450,150,25);
        JLabel div5s=new JLabel();
        div5s.setIcon(icon_dl);
        JButton rank52s=new JButton("赞ta");
        rank52s.setBounds(115,475,80,45);
        div5s.setBounds(0,520,400,5);
        JLabel like5s=new JLabel("点赞数:");
        like5s.setBounds(210,475,45,45);
        JLabel likenum5s = new JLabel("0");
        likenum5s.setBounds(260,475,60,45);
        rank52s.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rank52s.getText().equals("赞ta")) {
                    try {
                        cilent.like(rank5s.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum5s.setText(Integer.toString(Integer.parseInt(likenum5.getText())+1));
                    rank52s.setText("取消赞");
                }else{
                    try {
                        cilent.unlike(rank5s.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    likenum5s.setText(Integer.toString(Integer.parseInt(likenum5s.getText())-1));
                    rank52s.setText("赞ta");
                }
            }
        });
        p5.add(rank5s);
        p5.add(rank51s);
        p5.add(rank52s);
        p5.add(div5s);
        p5.add(like5s);
        p5.add(likenum5s);
        p5.add(pic5s);

        JButton getInfos = new JButton("刷新");
        getInfos.setBounds(110,532,80,25);
        getInfos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cilent.loginIn(name,password);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                JOptionPane tips = new JOptionPane();
                tips.showMessageDialog(null, "更新成功");

                List<Pictrue> pictrueList = cilent.cilentHandleThread.pictrues;
                int size = pictrueList.size();
                System.out.println(pictrueList.size());
                for(int i = 0; i < size; i ++){
                    System.out.println("现在list数量"+pictrueList.size());
                    double max = 0;
                    int place = 0;
                    for(int j = 0; j < pictrueList.size(); j ++){
                        if(pictrueList.get(j).likes > max){
                            max = pictrueList.get(j).likes;
                            place = j;
                        }
                    }

                    switch (i) {
                        case 0:rank1s.setText(String.valueOf(pictrueList.get(place).name));
                            rank11s.setText(String.valueOf(pictrueList.get(place).beauty));
                            likenum1s.setText(String.valueOf(pictrueList.get(place).likes));
                            System.out.println(0);


                            ImageIcon image1 = new ImageIcon("clientPics/" + pictrueList.get(place).name + ".jpg");
                            image1.setImage(image1.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                            pic1s.setIcon(image1);
                            System.out.println("clientPics/" + pictrueList.get(place).name + ".jpg");

                            break;
                        case 1:rank2s.setText(String.valueOf(pictrueList.get(place).name));
                            rank21s.setText(String.valueOf(pictrueList.get(place).beauty));
                            likenum2s.setText(String.valueOf(pictrueList.get(place).likes));
                            System.out.println(1);

                            ImageIcon image2 = new ImageIcon("clientPics/" + pictrueList.get(place).name + ".jpg");
                            image2.setImage(image2.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                            pic2s.setIcon(image2);
                            System.out.println("clientPics/" + pictrueList.get(place).name + ".jpg");
                            break;
                        case 2:rank3s.setText(String.valueOf(pictrueList.get(place).name));
                            rank31s.setText(String.valueOf(pictrueList.get(place).beauty));
                            likenum3s.setText(String.valueOf(pictrueList.get(place).likes));
                            System.out.println(2);

                            ImageIcon image3 = new ImageIcon("clientPics/" + pictrueList.get(place).name + ".jpg");
                            image3.setImage(image3.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                            pic3s.setIcon(image3);
                            System.out.println("clientPics/" + pictrueList.get(place).name + ".jpg");
                            break;
                        case 3:rank4s.setText(String.valueOf(pictrueList.get(place).name));
                            rank41s.setText(String.valueOf(pictrueList.get(place).beauty));
                            likenum4s.setText(String.valueOf(pictrueList.get(place).likes));
                            System.out.println(3);

                            ImageIcon image4 = new ImageIcon("clientPics/" + pictrueList.get(place).name + ".jpg");
                            image4.setImage(image4.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                            pic4s.setIcon(image4);
                            System.out.println("clientPics/" + pictrueList.get(place).name + ".jpg");
                            break;
                        case 4:rank5s.setText(String.valueOf(pictrueList.get(place).name));
                            rank51s.setText(String.valueOf(pictrueList.get(place).beauty));
                            likenum5s.setText(String.valueOf(pictrueList.get(place).likes));
                            System.out.println(4);

                            ImageIcon image5 = new ImageIcon("clientPics/" + pictrueList.get(place).name + ".jpg");
                            image5.setImage(image5.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                            pic5s.setIcon(image5);
                            System.out.println("clientPics/" + pictrueList.get(place).name + ".jpg");
                            break;

                        default:savePic(pictrueList.get(place).b,pictrueList.get(place).name);
                        System.out.println(421321);
                        break;
                    }
                    pictrueList.remove(place);
                }
            }
        });
        p5.add(getInfos);


        //这边的话是P3的控件
        JLabel setting=new JLabel("设置中心");
        setting.setFont(new Font (Font.DIALOG, Font.BOLD, 30));
        setting.setBounds(100,20,230,30);
        JButton c_pass=new JButton("修改密码");
        JTextField c_pass1=new JTextField("修改你的密码");
        c_pass.addActionListener(new ActionListener() {
            //这里给b2加上了一个监听，自定义函数，待命（不知道写啥）
            public void actionPerformed(ActionEvent e) {

            }
        });

        JLabel colorful=new JLabel("切换皮肤");
        colorful.setFont(new Font (Font.DIALOG, Font.BOLD, 30));
        colorful.setBounds(100,230,180,40);
        JButton ch1=new JButton("二次元");
        ch1.setBounds(115,280,80,30);
        JButton ch2=new JButton("草の莓");
        ch2.setBounds(115,330,80,30);
        JButton ch3=new JButton("树与花");
        ch3.setBounds(115,380,80,30);
        JButton ch4=new JButton("古典画");
        ch4.setBounds(115,430,80,30);
        p3.add(ch1);
        p3.add(ch2);
        p3.add(ch3);
        p3.add(ch4);



        p3.add(setting);
        c_pass.setBounds(60,60,90,30);
        c_pass1.setBounds(152,60,100,30);
        p3.add(c_pass);//黄色波浪线是由于代码重复度高不用管
        p3.add(c_pass1);
        p3.add(colorful);



        refresh.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                pictrue = cilent.cilentHandleThread.pic;
                basic_age_s.setText(Integer.toString(pictrue.age));

                String gender;
                if(pictrue.gender_type.equals("male")){
                    gender = "男";
                }else if(pictrue.gender_type.equals("female")){
                    gender = "女";
                }else{
                    gender = "错误";
                }

                basic_sex_s.setText(gender);

                basic_beauty_s.setText(Double.toString(pictrue.beauty));

                String emotion;
                if(pictrue.emotion.equals("angry")){
                    emotion = "生气";
                }else if(pictrue.emotion.equals("neutral")) {
                    emotion = "平静";
                }else if(pictrue.emotion.equals("happy")) {
                    emotion = "开心";
                } else if (pictrue.emotion.equals("disgust")) {
                    emotion = "厌恶";
                }else{
                    emotion = "错误";
                }
                System.out.println(pictrue.emotion);
                basic_emotion_s.setText(emotion);

                String faceType;
                if(pictrue.face_type.equals("human")){
                    faceType = "人类";
                }else if(pictrue.face_type.equals("cartoon")){
                    faceType = "卡通";
                }else{
                    faceType = "错误";
                }
                basic_faceType_s.setText(faceType);

                String race;
                if(pictrue.race.equals("yellow")){
                    race = "黄种人";
                }else if(pictrue.race.equals("black")){
                    race = "黑种人";
                }else if(pictrue.race.equals("white")) {
                    race = "白种人";
                }else{
                    race = "错误";
                }
                basic_race_s.setText(race);

                refresh.setEnabled(false);
            }

        });

        p1.add(refresh);
        JPanel p4 = new JPanel();
        p4.setLayout(null);
        File clientPicsFolder = new File("clientPics");
        String[] pkPicsName = clientPicsFolder.list();
        if(pkPicsName.length != 0 && pkPicsName.length != 1) {
            final int[] first = {(int) (Math.random() * pkPicsName.length)};
            final int[] second = {(int) (Math.random() * pkPicsName.length)};
            while (second[0] == first[0]) {
                second[0] = (int) (Math.random() * pkPicsName.length);
            }

            System.out.println(first[0] + " " + second[0]);


            JLabel pic41 = new JLabel();
            ImageIcon icon41 = new ImageIcon("clientPics/" + pkPicsName[first[0]]);
            JButton b41 = new JButton("我更喜欢ta");
            pic41.setBounds(55, 20, 200, 200);
            icon41.setImage(icon41.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
            pic41.setIcon(icon41);
            b41.setBounds(105, 230, 100, 30);
            JLabel pic42 = new JLabel();
            ImageIcon icon42 = new ImageIcon("clientPics/" + pkPicsName[second[0]]);
            JButton b42 = new JButton("我更喜欢ta");
            pic42.setBounds(55, 280, 200, 200);
            icon42.setImage(icon42.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
            pic42.setIcon(icon42);
            b42.setBounds(105, 490, 100, 30);
            p4.add(pic41);
            p4.add(pic42);
            p4.add(b41);
            p4.add(b42);

            b41.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        cilent.like(pkPicsName[first[0]].substring(0, pkPicsName[first[0]].length() - 4));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    first[0] = (int) (Math.random() * pkPicsName.length);
                    second[0] = (int) (Math.random() * pkPicsName.length);
                    while (second[0] == first[0]) {
                        second[0] = (int) (Math.random() * pkPicsName.length);
                    }
                    ImageIcon icon41 = new ImageIcon("clientPics/" + pkPicsName[first[0]]);
                    icon41.setImage(icon41.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
                    pic41.setIcon(icon41);
                    ImageIcon icon42 = new ImageIcon("clientPics/" + pkPicsName[second[0]]);
                    icon42.setImage(icon42.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
                    pic42.setIcon(icon42);

                }
            });

            b42.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        cilent.like(pkPicsName[second[0]].substring(0, pkPicsName[second[0]].length() - 4));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    first[0] = (int) (Math.random() * pkPicsName.length);
                    second[0] = (int) (Math.random() * pkPicsName.length);
                    while (second[0] == first[0]) {
                        second[0] = (int) (Math.random() * pkPicsName.length);
                    }
                    ImageIcon icon41 = new ImageIcon("clientPics/" + pkPicsName[first[0]]);
                    icon41.setImage(icon41.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
                    pic41.setIcon(icon41);
                    ImageIcon icon42 = new ImageIcon("clientPics/" + pkPicsName[second[0]]);
                    icon42.setImage(icon42.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
                    pic42.setIcon(icon42);

                }
            });

        }

        //现在开始分开
        JTabbedPane tp = new JTabbedPane(JTabbedPane.RIGHT);//设置选项卡标签在右边
        tp.addTab("基本信息",null,p1,"基本信息");
        tp.addTab("颜值排行",null,p2,"颜值排行");
        tp.addTab("点赞排行",null,p5,"点赞排行");
        tp.addTab("pk!",null,p4,"pk!");
        tp.addTab("设置",null,p3,"设置");
        // 创建一个水平JSplitPane，左边是p1,右边是p2
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pLeft, tp);
        // 设置分割条的位置
        sp.setDividerLocation(400);
        //分割线大小，0则为一条线
        sp.setDividerSize(3);
        //设置线不可被移动，方便分列展示
        sp.setEnabled(false);
        // 把sp当作ContentPane
        String lo_b1 = "D:\\IDEA project\\Client\\pics\\beauty_1.jpg";
        String lo_b2 = "D:\\IDEA project\\Client\\pics\\beauty_2.jpg";
        String lo_v1 = "D:\\IDEA project\\Client\\pics\\fruit_1.jpg";
        String lo_v2 = "D:\\IDEA project\\Client\\pics\\fruit_2.jpg";
        String lo_t1 = "D:\\IDEA project\\Client\\pics\\flower_1.jpg";
        String lo_t2 = "D:\\IDEA project\\Client\\pics\\flower_2.jpg";
        String lo_g1 = "D:\\IDEA project\\Client\\pics\\paint_1.jpg";
        String lo_g2 = "D:\\IDEA project\\Client\\pics\\paint_2.jpg";
        JLabel path_q = new JLabel();
        path_q.setBounds(0,0,400,600);
        pLeft.add(path_q,new Integer(Integer.MIN_VALUE));
        JLabel path_f = new JLabel();
        path_f.setBounds(0,0,400,600);
        p1.add(path_f,new Integer(Integer.MIN_VALUE));
        JLabel path_s = new JLabel();
        path_s.setBounds(0,0,400,600);
        p2.add(path_s,new Integer(Integer.MIN_VALUE));
        JLabel path_r = new JLabel();
        path_r.setBounds(0,0,400,600);
        p3.add(path_r,new Integer(Integer.MIN_VALUE));
        JLabel path_i = new JLabel();
        path_i.setBounds(0,0,400,600);
        p4.add(path_i,new Integer(Integer.MIN_VALUE));
        JLabel path_5 = new JLabel();
        path_5.setBounds(0,0,400,600);
        p5.add(path_5,new Integer(Integer.MIN_VALUE));
        ch1.addActionListener(new ActionListener() {
            //这里给b2加上了一个监听，自定义函数，待命（不知道写啥）
            public void actionPerformed(ActionEvent e) {
                path_q.setIcon(new ImageIcon(lo_b1));
                path_f.setIcon(new ImageIcon(lo_b2));
                path_r.setIcon(new ImageIcon(lo_b2));
                path_s.setIcon(new ImageIcon(lo_b2));
                path_i.setIcon(new ImageIcon(lo_b2));
                path_5.setIcon(new ImageIcon(lo_b2));
            }
        });
        ch2.addActionListener(new ActionListener() {
            //这里给b2加上了一个监听，自定义函数，待命（不知道写啥）
            public void actionPerformed(ActionEvent e) {
                path_q.setIcon(new ImageIcon(lo_v1));
                path_f.setIcon(new ImageIcon(lo_v2));
                path_r.setIcon(new ImageIcon(lo_v2));
                path_s.setIcon(new ImageIcon(lo_v2));
                path_i.setIcon(new ImageIcon(lo_v2));
                path_5.setIcon(new ImageIcon(lo_v2));
            }
        });

        ch3.addActionListener(new ActionListener() {
            //这里给b2加上了一个监听，自定义函数，待命（不知道写啥）
            public void actionPerformed(ActionEvent e) {
                path_q.setIcon(new ImageIcon(lo_t1));
                path_f.setIcon(new ImageIcon(lo_t2));
                path_r.setIcon(new ImageIcon(lo_t2));
                path_s.setIcon(new ImageIcon(lo_t2));
                path_i.setIcon(new ImageIcon(lo_t2));
                path_5.setIcon(new ImageIcon(lo_t2));
            }
        });

        ch4.addActionListener(new ActionListener() {
            //这里给b2加上了一个监听，自定义函数，待命（不知道写啥）
            public void actionPerformed(ActionEvent e) {
                path_q.setIcon(new ImageIcon(lo_g1));
                path_f.setIcon(new ImageIcon(lo_g2));
                path_r.setIcon(new ImageIcon(lo_g2));
                path_s.setIcon(new ImageIcon(lo_g2));
                path_i.setIcon(new ImageIcon(lo_g2));
                path_5.setIcon(new ImageIcon(lo_g2));
            }
        });


        List<Pictrue> pictrueList = cilent.cilentHandleThread.pictrues;
        List<Pictrue> pictrueLists = new ArrayList<Pictrue>();
        pictrueLists.addAll(pictrueList);
        int size = pictrueList.size();
        System.out.println(pictrueList.size());
        for(int i = 0; i < size; i ++){
            System.out.println("现在list数量"+pictrueList.size());
            double max = 0;
            int place = 0;
            for(int j = 0; j < pictrueList.size(); j ++){
                if(pictrueList.get(j).beauty > max){
                    max = pictrueList.get(j).beauty;
                    place = j;
                }
            }
            switch (i) {
                case 0:rank1.setText(String.valueOf(pictrueList.get(place).name));
                    rank11.setText(String.valueOf(pictrueList.get(place).beauty));
                    likenum1.setText(String.valueOf(pictrueList.get(place).likes));
                    System.out.println(0);

                    ImageIcon image1 = new ImageIcon("clientPics/" + pictrueList.get(place).name + ".jpg");
                    image1.setImage(image1.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                    pic1.setIcon(image1);
                    System.out.println("clientPics/" + pictrueList.get(place).name + ".jpg");

                    break;
                case 1:rank2.setText(String.valueOf(pictrueList.get(place).name));
                    rank21.setText(String.valueOf(pictrueList.get(place).beauty));
                    likenum2.setText(String.valueOf(pictrueList.get(place).likes));
                    System.out.println(1);

                    ImageIcon image2 = new ImageIcon("clientPics/" + pictrueList.get(place).name + ".jpg");
                    image2.setImage(image2.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                    pic2.setIcon(image2);
                    System.out.println("clientPics/" + pictrueList.get(place).name + ".jpg");
                    break;
                case 2:rank3.setText(String.valueOf(pictrueList.get(place).name));
                    rank31.setText(String.valueOf(pictrueList.get(place).beauty));
                    likenum3.setText(String.valueOf(pictrueList.get(place).likes));
                    System.out.println(2);

                    ImageIcon image3 = new ImageIcon("clientPics/" + pictrueList.get(place).name + ".jpg");
                    image3.setImage(image3.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                    pic3.setIcon(image3);
                    System.out.println("clientPics/" + pictrueList.get(place).name + ".jpg");
                    break;
                case 3:rank4.setText(String.valueOf(pictrueList.get(place).name));
                    rank41.setText(String.valueOf(pictrueList.get(place).beauty));
                    likenum4.setText(String.valueOf(pictrueList.get(place).likes));
                    System.out.println(3);

                    ImageIcon image4 = new ImageIcon("clientPics/" + pictrueList.get(place).name + ".jpg");
                    image4.setImage(image4.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                    pic4.setIcon(image4);
                    System.out.println("clientPics/" + pictrueList.get(place).name + ".jpg");
                    break;
                case 4:rank5.setText(String.valueOf(pictrueList.get(place).name));
                    rank51.setText(String.valueOf(pictrueList.get(place).beauty));
                    likenum5.setText(String.valueOf(pictrueList.get(place).likes));
                    System.out.println(4);

                    ImageIcon image5 = new ImageIcon("clientPics/" + pictrueList.get(place).name + ".jpg");
                    image5.setImage(image5.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                    pic5.setIcon(image5);
                    System.out.println("clientPics/" + pictrueList.get(place).name + ".jpg");
                    break;

                default:System.out.println(421321);break;
            }
            savePic(pictrueList.get(place).b,pictrueList.get(place).name);
            pictrueList.remove(place);
        }



        for(int i = 0; i < size; i ++){
            double max = 0;
            int place = 0;
            for(int j = 0; j < pictrueLists.size(); j ++){
                if(pictrueLists.get(j).likes > max){
                    max = pictrueLists.get(j).likes;
                    place = j;
                }
            }
            switch (i) {
                case 0:rank1s.setText(String.valueOf(pictrueLists.get(place).name));
                    rank11s.setText(String.valueOf(pictrueLists.get(place).beauty));
                    likenum1s.setText(String.valueOf(pictrueLists.get(place).likes));
                    System.out.println(0);

                    ImageIcon image1 = new ImageIcon("clientPics/" + pictrueLists.get(place).name + ".jpg");
                    image1.setImage(image1.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                    pic1s.setIcon(image1);
                    System.out.println("clientPics/" + pictrueLists.get(place).name + ".jpg");

                    break;
                case 1:rank2s.setText(String.valueOf(pictrueLists.get(place).name));
                    rank21s.setText(String.valueOf(pictrueLists.get(place).beauty));
                    likenum2s.setText(String.valueOf(pictrueLists.get(place).likes));
                    System.out.println(1);

                    ImageIcon image2 = new ImageIcon("clientPics/" + pictrueLists.get(place).name + ".jpg");
                    image2.setImage(image2.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                    pic2s.setIcon(image2);
                    System.out.println("clientPics/" + pictrueLists.get(place).name + ".jpg");
                    break;
                case 2:rank3s.setText(String.valueOf(pictrueLists.get(place).name));
                    rank31s.setText(String.valueOf(pictrueLists.get(place).beauty));
                    likenum3s.setText(String.valueOf(pictrueLists.get(place).likes));
                    System.out.println(2);

                    ImageIcon image3 = new ImageIcon("clientPics/" + pictrueLists.get(place).name + ".jpg");
                    image3.setImage(image3.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                    pic3s.setIcon(image3);
                    System.out.println("clientPics/" + pictrueLists.get(place).name + ".jpg");
                    break;
                case 3:rank4s.setText(String.valueOf(pictrueLists.get(place).name));
                    rank41s.setText(String.valueOf(pictrueLists.get(place).beauty));
                    likenum4s.setText(String.valueOf(pictrueLists.get(place).likes));
                    System.out.println(3);

                    ImageIcon image4 = new ImageIcon("clientPics/" + pictrueLists.get(place).name + ".jpg");
                    image4.setImage(image4.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                    pic4s.setIcon(image4);
                    System.out.println("clientPics/" + pictrueLists.get(place).name + ".jpg");
                    break;
                case 4:rank5s.setText(String.valueOf(pictrueLists.get(place).name));
                    rank51s.setText(String.valueOf(pictrueLists.get(place).beauty));
                    likenum5s.setText(String.valueOf(pictrueLists.get(place).likes));
                    System.out.println(4);

                    ImageIcon image5 = new ImageIcon("clientPics/" + pictrueLists.get(place).name + ".jpg");
                    image5.setImage(image5.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
                    pic5s.setIcon(image5);
                    System.out.println("clientPics/" + pictrueLists.get(place).name + ".jpg");
                    break;

                default:System.out.println(421321);break;
            }
            pictrueLists.remove(place);
        }


        b2.addActionListener(new ActionListener() {
            //这里给b2加上了一个监听，自定义函数，待命（不知道写啥）
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println(3);
                    System.out.println(paths);
                    cilent.picture(paths,name,password);

                    JOptionPane tips = new JOptionPane();
                    tips.showMessageDialog(null, "上传成功");

                    refresh.setEnabled(true);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                b2.setEnabled(false);
            }
        });




        JLabel divs1=new JLabel();
        ImageIcon icon_ds=new ImageIcon("././out/picture/DL1.png");
        divs1.setIcon(icon_ds);
        divs1.setBounds(0,0,5,600);

        JLabel divs2=new JLabel();
        divs2.setIcon(icon_ds);
        divs2.setBounds(0,0,5,600);

        JLabel divs3=new JLabel();
        divs3.setIcon(icon_ds);
        divs3.setBounds(0,0,5,600);

        JLabel divs4=new JLabel();
        divs4.setIcon(icon_ds);
        divs4.setBounds(0,0,5,600);

        JLabel divs5=new JLabel();
        divs5.setIcon(icon_ds);
        divs5.setBounds(0,0,5,600);
        
        p1.add(divs1);
        p2.add(divs2);
        p3.add(divs3);
        p4.add(divs4);
        p5.add(divs5);

        refresh.setEnabled(false);
        b2.setEnabled(false);
        f.setContentPane(sp);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void savePic(byte[] bytes,String name){
        try {
            File file = new File("clientPics/" + name + ".jpg");//使用 File() 方法来创建一个文件对象
            if(file.createNewFile()) {//创建一个zhl.txt文件
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                BufferedImage bi = ImageIO.read(bais);
                ImageIO.write(bi, "jpg", file);
            }else{
                file.delete();
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                BufferedImage bi = ImageIO.read(bais);
                ImageIO.write(bi, "jpg", file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}