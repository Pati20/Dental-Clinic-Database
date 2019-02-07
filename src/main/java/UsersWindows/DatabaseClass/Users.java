package UsersWindows.DatabaseClass;

import javafx.beans.property.SimpleStringProperty;

public class Users {

    private final SimpleStringProperty id;
    private final SimpleStringProperty login;
    private final SimpleStringProperty password;
    private final SimpleStringProperty postion;

    public Users(String id, String login, String password, String postion) {
        this.id = new SimpleStringProperty(id);
        this.password = new SimpleStringProperty(password);
        this.login = new SimpleStringProperty(login);
        this.postion = new SimpleStringProperty(postion);
    }

    public String getId() {
        return id.get();
    }
    public String getPassword() {
        return password.get();
    }
    public String getLogin() {
        return login.get();
    }
    public String getPostion() {
        return postion.get();
    }
}
