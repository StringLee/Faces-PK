package Server;

import java.io.Serializable;

public class Pictrue implements Serializable {
    public int type;
    public  byte[]b;
    public  String name;
    public  boolean isRight;
    public  int age;
    public  String gender_type;
    public  String face_type;
    public  String emotion;
    public  String race;
    public int likes;
    public double beauty;

    public Pictrue(int type,boolean isRight)
    {
        this.type = type;
        this.isRight = isRight;
        System.out.println(isRight);
    }
    public Pictrue(int type,int likes,String name,byte[]b,double beauty)
    {

        this.likes=likes;
        this.type=type;
        this.name=name;
        this.b=b;
        this.beauty = beauty;
    }
    public Pictrue(int type,byte[]b, double beauty,int age,String gender_type,String face_type,String emotion,String race){
        this.type = type;
        this.b = b;
        this.age = age;
        this.beauty = beauty;
        this.emotion = emotion;
        this.gender_type = gender_type;
        this.face_type = face_type;
        this.race = race;
    }
}
