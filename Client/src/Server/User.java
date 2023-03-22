package Server;

import java.io.Serializable;

public class User implements Serializable {
    public   int type;
    public String name;
    public String password;
    public   byte[] pic;

    public User(int type, String name, String password,byte[] pic) {
        super();
        this.type = type;
        this.pic = pic;
        this.name = name;
        this.password = password;
    }

}