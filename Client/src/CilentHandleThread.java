import Server.Pictrue;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class CilentHandleThread extends Thread{

    public Pictrue pic = null ;
    public List<Pictrue> pictrues = new LinkedList<Pictrue>() ;
    private Socket socket=null;
    private       OutputStream os = null;
    private      InputStream is = null;
    private       ObjectInputStream ois =null;
    private       ObjectOutputStream oos = null;
    public CilentHandleThread(Socket socket,OutputStream os,InputStream is,ObjectOutputStream oos ,ObjectInputStream ois) {
        super();
        this.is=is;
        this.ois=ois;
        this.oos = oos;
        this.os = os;
        this.socket = socket;
    }
    public void run()  {

        while(true)
        {

            try {
                Pictrue picture = (Pictrue)ois.readObject();
                if(picture.type != 4) {
                    pic = picture;
                    System.out.println("收到了非4");
                }else{
                    pictrues.add(picture);
                    System.out.println("收到排行");
                }

                System.out.println(1);
                System.out.println(pic.age+pic.race+pic.beauty);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }

        }
    };
}
