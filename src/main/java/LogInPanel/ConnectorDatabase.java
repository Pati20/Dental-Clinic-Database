package LogInPanel;

import Protection.Protection;
import javafx.scene.control.Alert;

import java.sql.*;

public class ConnectorDatabase {

    int idUser;

    public boolean checkPasswordandLogin(String login, String password, String position) {


        Protection p = new Protection("user", "password");

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/przychodnia?characterEncoding=UTF-8", p.getUser(), p.getPassword());
                Statement stmt = conn.createStatement();
        ) {

            String getIdWorker = "SELECT id_użytkownika from użytkownicy where Login = '" + login + "'";
            ResultSet result1 = stmt.executeQuery(getIdWorker);
            if (result1.next()) {
                idUser = result1.getInt("id_użytkownika");
            }

            String testquery = "Select Login,Hasło,Stanowsko  FROM użytkownicy";
            ResultSet res = stmt.executeQuery(testquery);


            while (res.next()) {
                String databasePassword = res.getString("Hasło");
                String databaseLogin = res.getString("Login");
                String databasePosition = res.getString("Stanowsko");
                if (password.equals(databasePassword) && login.equals(databaseLogin) && !position.equals(databasePosition)) {
                    showAlert(position);
                    conn.close();
                    return false;
                } else if (password.equals(databasePassword) && login.equals(databaseLogin) && position.equals(databasePosition)) {
                    conn.close();
                    System.out.println("Połączono jako " + position);
                    return true;
                } else if (password.equals(p.getPassword()) && login.equals(p.getUser()) && position.equals("Administrator")) {
                    conn.close();
                    System.out.println("Połączono jako " + position);
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        showAlertAboutWrogngEnteredData();
        return false;
    }

    int getIdUser() {
        return idUser;
    }

    void showAlert(String position) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Error while entering");
        alert.setContentText("You do not have permission to: " + position);
        alert.show();
    }

    void showAlertAboutWrogngEnteredData() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Error while entering");
        alert.setContentText("Incorrect password or login.");
        alert.show();
    }
}
