import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.WebcamUtils;
import com.github.sarxos.webcam.util.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test {



    private static JFrame window;



    public static String take_photos(JLabel lp,JLabel pic) throws InterruptedException {



        final Webcam webcam = Webcam.getDefault();

        webcam.setViewSize(WebcamResolution.VGA.getSize());



        WebcamPanel panel = new WebcamPanel(webcam);

        panel.setFPSDisplayed(true);

        panel.setDisplayDebugInfo(true);

        panel.setImageSizeDisplayed(true);

        panel.setMirrored(true);



        final JFrame window = new JFrame("com.company.Test webcam panel");

        window.setAlwaysOnTop(true);

        window.add(panel);

        window.setResizable(true);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.pack();

        window.setVisible(true);







        final JButton button = new JButton("拍照");

        window.add(panel, BorderLayout.CENTER);

        window.add(button, BorderLayout.SOUTH);

        window.setResizable(true);

        window.pack();

        window.setVisible(true);


        final String[] fileName = new String[1];
        fileName[0] = "D:\\" + System.currentTimeMillis();       //保存路径即图片名称（不用加后缀）
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)

            {

                button.setEnabled(false);  //设置按钮不可点击


                //实现拍照保存-------start



                WebcamUtils.capture(webcam, fileName[0], ImageUtils.FORMAT_JPG);

                SwingUtilities.invokeLater(new Runnable() {

                    @Override

                    public void run()

                    {

                        JOptionPane.showMessageDialog(null, "拍照成功");



                         return ;

                    }

                });

                //实现拍照保存-------end

              window.setVisible(false);
                button.setEnabled(true);    //设置// 按钮可点击

               String paths = fileName[0] + ".jpg";
                ImageIcon image = new ImageIcon(paths);
                lp.setText("文件目录：" + paths);
                image.setImage(image.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT));
                pic.setIcon(image);
                System.out.println(paths);

            }

        });
        System.out.println(fileName[0]);



        return fileName[0];

    }
}