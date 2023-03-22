import Server.User;

import java.io.*;
import java.net.Socket;

public class Cilent {

    public void picture(String path,String name,String password) throws IOException {
        try {
            User user = new User(3,name,password,load_picture_from_file(path));
            System.out.println(4);
            oos.writeObject(user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void register(String name,String password) throws IOException {
        try {
            User user = new User(1,name,password,null);
            System.out.println(4);
            oos.writeObject(user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loginIn(String name, String password) throws IOException {
        try {
            User user = new User(2,name,password,null);
            System.out.println(4);
            oos.writeObject(user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void changePassword(String name,String password) throws IOException {
        try {
            User user = new User(4,name,password,null);
            System.out.println(4);
            oos.writeObject(user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void like(String name) throws IOException {
        try {
            User user = new User(5,name,null,null);
            System.out.println(4);
            oos.writeObject(user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void unlike(String name) throws IOException {
        try {
            User user = new User(6,name,null,null);
            System.out.println(4);
            oos.writeObject(user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static Socket socket;

    static {
        try {
            socket = new Socket("192.168.43.23", 8888);
//            socket = new Socket("127.0.0.1", 8888);
//            socket = new Socket("192.168.137.183", 8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static  OutputStream os;

    static {
        try {
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObjectOutputStream oos;

    static {
        try {
            oos = new ObjectOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] load_picture_from_file(String pic_url) {

        try {

            File f = new File(pic_url);

            InputStream is = new FileInputStream(f);

            byte[] b = new byte[(int) f.length()];

            is.read(b);

            is.close();

            return b;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }


    public static InputStream is;
    static {
        try {
            is = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ObjectInputStream ois;

    static {
        try {
            ois = new ObjectInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   public CilentHandleThread cilentHandleThread = null;

    Cilent(){
        cilentHandleThread = new CilentHandleThread(socket,os,is,oos,ois);
        System.out.println(2);
        cilentHandleThread.start();
    }
}