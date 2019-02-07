package Protection;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Protection {

    private String password;
    private String user;

    public Protection(String user,String password) {
        this.password = setData(password);
        this.user = setData(user);
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    private String setData(String type) {
        String temp = null;
        try {
            ObjectInputStream os = new ObjectInputStream((new FileInputStream("src/main/SavingData/" + type + ".ser")));
            temp = (String) os.readObject();
            os.close();

        } catch (Exception e2x) {
            e2x.printStackTrace();
        }
        return temp;
    }

}

