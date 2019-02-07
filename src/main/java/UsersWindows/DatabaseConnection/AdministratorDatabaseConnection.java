package UsersWindows.DatabaseConnection;

import Protection.Protection;
import UsersWindows.DatabaseClass.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdministratorDatabaseConnection extends DatabaseConnection {

    private Protection protection;
    public AdministratorDatabaseConnection(String userId) {
        super(userId);
         protection = new Protection("Administrator", "AdministratorPassword");
        setConnection(protection.getUser(), protection.getPassword());
    }

    public ArrayList<String> getMysqlUsers() {
        ArrayList<String> users = new ArrayList<>();
        try {
            String select = "SELECT  User from mysql.user;";
            ResultSet res = stmt.executeQuery(select);

            while (res.next()) {
                 users.add(res.getString("User"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        return users;
    }

    public ArrayList<String> getMysqlTables() {
        ArrayList<String> tablesName = new ArrayList<>();
        try {
            String select = "Select TABLE_NAME from information_schema.TABLES where TABLE_SCHEMA = 'przychodnia';";
            ResultSet res = stmt.executeQuery(select);

            while (res.next()) {
                tablesName.add(res.getString("TABLE_NAME"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        tablesName.add("przychodnia.*");
        return tablesName;
    }

    public ArrayList<String> getMysqlPrivilegeType() {
        ArrayList<String> PrivilegeType = new ArrayList<>();
        try {
            String select = "SELECT PRIVILEGE_TYPE from information_schema.USER_PRIVILEGES where GRANTEE = \"'root'@'localhost'\";";
            ResultSet res = stmt.executeQuery(select);

            while (res.next()) {
                PrivilegeType.add(res.getString("PRIVILEGE_TYPE").toLowerCase());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        PrivilegeType.add("all");
        return PrivilegeType;
    }

    public ArrayList<String> getEmployee() {
        ArrayList<String> employee = new ArrayList<>();
        try {
            String select = "Select Login from użytkownicy;";
            ResultSet res = stmt.executeQuery(select);

            while (res.next()) {
                employee.add(res.getString("Login").toLowerCase());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        return employee;
    }

    public boolean deleteEmployee(String nick){
        try {
            String deleteQuery = "CALL deleteUsers('"+nick+"');";
            stmt.executeQuery(deleteQuery);

        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return false;
        }
        showAcceptedRequest("The employee \""+nick+"\" has been successfully removed.");
        return true;
    }

    public boolean deleteUser(String nick){
        try {
            String deleteQuery = "DELETE FROM mysql.user where user like '"+nick+"'";
            stmt.executeQuery(deleteQuery);

        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return false;
        }
        showAcceptedRequest("The user \""+nick+"\" has been successfully removed.");
        return true;
    }

    public boolean givPrivileges(String user, String privileges, String table, String column){

        String privilegesQuery;
        if(!column.isEmpty()){
             privilegesQuery = "GRANT "+privileges+"  ("+column+") on "+table+" TO '"+user+"'@'localhost' ;";
        }

        try {
            privilegesQuery = "GRANT "+privileges+" ON przychodnia."+table+" TO '"+user+"'@'localhost';";
            stmt.executeQuery(privilegesQuery);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return false;
        }
        showAcceptedRequest("The user \""+user+"\" has been successfully recived privileges.");
        return true;
    }

    public boolean revokePrivelages(String user, String privileges, String table){
        try {
            String revokeQuery = "REVOKE "+privileges+" ON przychodnia."+table+" FROM '"+user+"'@'localhost';";
            stmt.executeQuery(revokeQuery);

        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return false;
        }
        showAcceptedRequest("\n" +
                "The \""+privileges+"\" privileges have been revoked from the user \""+user+"\";");
        return true;
    }

    public boolean addUser(String login, String password, String postion,String id){
        try {
            String insert = "INSERT INTO `przychodnia`.`użytkownicy` (`id_użytkownika`, `Hasło`, `Login`, `Stanowsko`) " +
                    "VALUES ("+id+", '"+password+"', '"+login+"', '"+postion+"');";
            stmt.executeQuery(insert);

        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return false;
        }
        showAcceptedRequest("\n" +
                "The user have been revoked created");
        return true;
    }

    public ObservableList<Users> createUsersTable(){
        ObservableList<Users> dataOfUsers = FXCollections.observableArrayList();

        try {
            String select = "Select * FROM użytkownicy;";
            ResultSet res = stmt.executeQuery(select);

            while (res.next()) {
                String id = res.getString("id_użytkownika");
                String pasword = res.getString("Hasło");
                String login = res.getString("Login");
                String postion = res.getString("Stanowsko");
                dataOfUsers.add(new Users(id, login, pasword, postion));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        return dataOfUsers;
    }

    public boolean correctUser(String login, String password,String postion,String id){

        if (id.length() == 0) {
            showErrorMessage("User ID is not entered");
            return false;
        }
        ArrayList<String> listOfDataToCheck = new ArrayList<>();
        listOfDataToCheck.add(postion);
        listOfDataToCheck.add(login);
        listOfDataToCheck.add(password);
        listOfDataToCheck.add(id);
        ArrayList<String> listOftableName = new ArrayList<>();
        listOftableName.add("Stanowsko");
        listOftableName.add("Login");
        listOftableName.add("Hasło");
        listOftableName.add("id_użytkownika");

        for (int i = listOfDataToCheck.size() - 2; i >= 0; i--) {
            if (listOfDataToCheck.get(i).length() == 0) {
                listOfDataToCheck.remove(i);
                listOftableName.remove(i);
            }
        }

        if (listOfDataToCheck.size() == 1) {
            showErrorMessage("No data has been added to the changes");
            return false;
        }

        StringBuilder updateQuery = new StringBuilder("UPDATE użytkownicy SET ");
        int i = 0;
        for (; i <= listOfDataToCheck.size() - 3; i++) {
            updateQuery.append(listOftableName.get(i)).append(" = '").append(listOfDataToCheck.get(i)).append("',");
        }
        updateQuery.append(listOftableName.get(i)).append(" = '").append(listOfDataToCheck.get(i)).append("' ");
        updateQuery.append("where id_użytkownika = '").append(listOfDataToCheck.get(i + 1)).append("';");

        boolean isQueryCorrect = true;
        try {
            System.out.println(updateQuery.toString());
            stmt.executeUpdate(updateQuery.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            isQueryCorrect = false;
        }
        if (isQueryCorrect)
            showAcceptedRequest("The changes have been successfully added.");
        return true;

    }


    public boolean backupDB() {

        Date dateNow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss");
        String date_to_string = dateFormat.format(dateNow);
        String saving = date_to_string+"_backup.sql";
        String executeCmd = "mysqldump -u "+protection.getUser()+" -p"+protection.getPassword()+" --add-drop-database -B  przychodnia -r src/main/Backup/"+saving;
        Process runtimeProcess;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                showAcceptedRequest("Backup has been successfully done.");
                return true;
            } else {
                showErrorMessage("Something gone wrong.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
        }

        return false;
    }

    public boolean restoreDB(String source) {

        String[] executeCmd = new String[]{"mysql", "--user="+protection.getUser(), "--password="+protection.getUser(),
                "-e", "source /home/albert/IdeaProjects/"+"Dental Clinic Database"+"/src/main/Backup/"+source};
        Process runtimeProcess;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                showAcceptedRequest("Restore has been successfully done.");
                return true;
            } else {
                showErrorMessage("Something gone wrong.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
        }

        return false;
    }

    public boolean add_update_NewDoctor(String id, String specialisation, int carry) {
        if (id.isEmpty()) {
            showErrorMessage("Doctor ID is not entered");
            return false;
        }
        String querry = null;
        if (carry == 0) {
            querry = "INSERT INTO lekarz(ID_LEKARZ, SPECJALIZACJA) VALUES(" + id + ",'" + specialisation + "');";
        } else {
            querry = "Update lekarz SET Specjalizacja = '"+specialisation+"' where id_lekarz = '"+id+"';";
        }
        try {
            if(carry == 0)stmt.executeQuery(querry);
            else stmt.executeUpdate(querry);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return false;
        }
        if (carry == 0) {
            showAcceptedRequest("The doctor has been  created");
        } else {
            showAcceptedRequest("The doctor has been  updated");
        }
        return true;
    }

    public boolean removeDoctor(String id){
        if(id.length() == 0){
            showErrorMessage("Doctor ID is not entered");
            return false;
        }
        try {
            String deleteQuerry = "DELETE  FROM lekarz WHERE id_lekarz = '"+id+"';";
            stmt.executeQuery(deleteQuerry);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return false;
        }
        showAcceptedRequest("The doctor has been  created");
        return true;
    }


    public boolean removeofficeHour(String id){
        if(id.length() == 0){
            showErrorMessage("Duty ID is not entered");
            return false;
        }
        try {
            String deleteQuerry = "DELETE FROM dyzury WHERE id_dyzuru = '"+id+"';";
            stmt.executeQuery(deleteQuerry);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return false;
        }
        showAcceptedRequest("The office hour has been  deleted");
        return true;
    }


    public boolean addOfficeHour(String idDoctor,String day, String start,String end,String holiday){
        if(idDoctor.isEmpty()){
            showErrorMessage("You must fill doctor id file.");
            return false;
        }
        try {
            String deleteQuerry = "Insert into dyzury( ID_LEKARZ, DZIEN, POCZATEK, KONIEC) " +
                    "VALUES ('"+idDoctor+"','"+day+"','"+start+"','"+end+"');";
            stmt.executeUpdate(deleteQuerry);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return false;
        }
        showAcceptedRequest("The office hour has been  deleted");
        return true;
    }

    public boolean updateofficeHour(String idOfficeHour,String idDoctor,String day, String start,String end,String holiday){

        if (idOfficeHour.length() == 0) {
            showErrorMessage("Office hour ID is not entered");
            return false;
        }
        ArrayList<String> listOfDataToCheck = new ArrayList<>();
        listOfDataToCheck.add(holiday);
        listOfDataToCheck.add(end);
        listOfDataToCheck.add(start);
        listOfDataToCheck.add(day);
        listOfDataToCheck.add(idDoctor);
        listOfDataToCheck.add(idOfficeHour);
        ArrayList<String> listOftableName = new ArrayList<>();
        listOftableName.add("URLOP");
        listOftableName.add("KONIEC");
        listOftableName.add("POCZATEK");
        listOftableName.add("DZIEN");
        listOftableName.add("ID_LEKARZ");
        listOftableName.add("ID_DYZURU");

        for (int i = listOfDataToCheck.size() - 2; i >= 0; i--) {
            if (listOfDataToCheck.get(i).length() == 0) {
                listOfDataToCheck.remove(i);
                listOftableName.remove(i);
            }
        }

        if (listOfDataToCheck.size() == 1) {
            showErrorMessage("No data has been added to the changes");
            return false;
        }

        StringBuilder updateQuery = new StringBuilder("UPDATE dyzury SET ");
        int i = 0;
        for (; i <= listOfDataToCheck.size() - 3; i++) {
            updateQuery.append(listOftableName.get(i)).append(" = '").append(listOfDataToCheck.get(i)).append("',");
        }
        updateQuery.append(listOftableName.get(i)).append(" = '").append(listOfDataToCheck.get(i)).append("' ");
        updateQuery.append("where ID_DYZURU = '").append(listOfDataToCheck.get(i + 1)).append("';");
        boolean isQueryCorrect = true;
        try {
            System.out.println(updateQuery.toString());
            stmt.executeUpdate(updateQuery.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            isQueryCorrect = false;
        }
        if (isQueryCorrect)
            showAcceptedRequest("The changes have been successfully added.");
        return true;
    }
}


