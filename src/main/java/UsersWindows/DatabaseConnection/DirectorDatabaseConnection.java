package UsersWindows.DatabaseConnection;

import Protection.Protection;
import UsersWindows.DatabaseClass.Employee;
import UsersWindows.DatabaseClass.Holiday;
import UsersWindows.DatabaseClass.Log;
import UsersWindows.DatabaseClass.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;

import java.awt.desktop.SystemSleepEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DirectorDatabaseConnection extends DatabaseConnection {

    public DirectorDatabaseConnection(String userId) {
        super(userId);
        Protection protection = new Protection("Director", "DirectorPassword");
        setConnection(protection.getUser(), protection.getPassword());
    }

    public ObservableList<Log> createLogTable() {
        ObservableList<Log> dataOfLog = FXCollections.observableArrayList();
        String select = "Select * from  logi;";

        try {
            ResultSet res = stmt.executeQuery(select);
            while (res.next()) {
                String Id = res.getString("id");
                String visitID = res.getString("wizyta_id");
                String oldDate = res.getString("Poprzednia_data_wizyty");
                String newDate = res.getString("Nowa_data_wizyty");
                String oldDoctor = res.getString("Poprzedni_lekarz");
                String newDoctor = res.getString("Nowy_lekarz");
                String dateOfChange = res.getString("Data_zmiany");
                dataOfLog.add(new Log(Id,visitID,oldDate,newDate,oldDoctor,newDoctor,dateOfChange));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        return dataOfLog;
    }


    public boolean removeEmployee(String id) {

        Optional<ButtonType> action = showAlert("Are you sure to delete employee  under ID: " + id + " ?").showAndWait();
        if (action.get() == ButtonType.OK) {
            try {
                String deleteQuery = "CALL deleteEmployee('"+id+"');";
                stmt.executeQuery(deleteQuery);

            } catch (SQLException ex) {
                ex.printStackTrace();
                showErrorMessage(ex.getMessage());
                return false;
            }
            showAcceptedRequest("The employee under ID \"" + id + "\" has been successfully removed.");
            return true;
        }
        return true;
    }

    public boolean addNewEmploye(String name, String surname, String birth, String nr, String pesel, String salary, String address){
        if(name.isEmpty() || surname.isEmpty() || birth.isEmpty() || nr.isEmpty() || pesel.isEmpty() || salary.isEmpty() || address.isEmpty()){
            showErrorMessage("You have to fill all empty files.");
            return  false;
        }else if(!isCorrrectData(birth)){
            showErrorMessage("You have an error in birth file.");
            return  false;
        }
        System.out.println("data = "+birth);
        boolean isQueryCorrect = true;
        try {
            String updateQuery = "INSERT INTO pracownicy(Imie, Nazwisko, Data_urodzenia, nr_telefonu, Pesel, Pensja, Adres)\n" +
                    "VALUES ('"+name+"', '"+surname+"', '"+birth+"', '"+nr+"', '"+pesel+"', '"+salary+"', '"+address+"');";
            stmt.executeUpdate(updateQuery);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            isQueryCorrect = false;
        }
        if (isQueryCorrect)
            showAcceptedRequest("The changes have been successfully added.");
        return true;
    }

    public ObservableList<Employee> createEmployeeTable(String id,String name, String surname,String postion) {
        ObservableList<Employee> dataOfEmployee = FXCollections.observableArrayList();
        String select = "Select * from pracownicy join użytkownicy ON id_pracownik = id_użytkownika " +
                "where Imie ='"+name+"' OR Nazwisko ='"+surname+"' OR Stanowsko = '"+postion+"' OR id_użytkownika ='"+id+"';";
        if(id.isEmpty() && name.isEmpty() && surname.isEmpty() && postion.isEmpty() ){
            select = "Select * from pracownicy";
        }
        System.out.println(select);
        try {
            ResultSet res = stmt.executeQuery(select);
            while (res.next()) {
                int Id = res.getInt("id_pracownik");
                String firstName = res.getString("imie");
                String lastName = res.getString("nazwisko");
                String pesel = res.getString("pesel");
                int nr_tel = res.getInt("nr_telefonu");
                String data = res.getString("data_urodzenia");
                String Adres = res.getString("adres");
                int salary = res.getInt("pensja");
                dataOfEmployee.add(new Employee(Id, firstName, lastName, pesel, nr_tel, data, Adres,salary));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        return dataOfEmployee;
    }

    public ObservableList<Services> createServicesTable(String executor,String price, String service) {
        ObservableList<Services> dataOfServices = FXCollections.observableArrayList();
        String select = "SELECT * FROM uslugi JOIN zabieg ON id_zabieg = zabieg_id where id_wykonawca = '"+executor+"' or cena = '"+price+"' or zabieg_id = '"+service+"';";
        if(executor.isEmpty() && price.isEmpty() && service.isEmpty() ){
            select = "SELECT * FROM uslugi JOIN zabieg ON id_zabieg = zabieg_id";
        }
        System.out.println(select);
        try {
            ResultSet res = stmt.executeQuery(select);
            while (res.next()) {
                String id = res.getString("id_wykonawca");
                String pricee = res.getString("cena");
                String treatment = res.getString("nazwa");
                String Idtreatment = res.getString("zabieg_id");
                dataOfServices.add(new Services(id, Idtreatment, treatment,pricee));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        return dataOfServices;
    }

    public boolean correctOneFieldEmployee(String chosenToCorrect, String newValue, String idEmployee) {
        if (idEmployee.equals("") || idEmployee.equals(null)) {
            showErrorMessage("Employee ID is not entered");
            return false;
        }
        String nameofColumnInDatabase = null;
        switch (chosenToCorrect) {
            case "Name":
                nameofColumnInDatabase = "Imie";
                break;
            case "Surname":
                nameofColumnInDatabase = "Nazwisko";
                break;
            case "Pesel":
                nameofColumnInDatabase = "Pesel";
                break;
            case "nr.Phone":
                nameofColumnInDatabase = "nr_telefonu";
                break;
            case "Date of birth":
                nameofColumnInDatabase = "Data_urodzenia";
                break;
            case "Adress":
                nameofColumnInDatabase = "Adres";
                break;
        }

        boolean isQueryCorrect = true;
        try {
            String updateQuery = "UPDATE pracownicy SET " + nameofColumnInDatabase + " = '" + newValue + "' where id_pracownik = '" + idEmployee + "';";
            stmt.executeUpdate(updateQuery);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            isQueryCorrect = false;
        }
        if (isQueryCorrect)
            showAcceptedRequest("The changes have been successfully added.");
        return true;
    }

    public boolean removeServices(String idExecutor, String idServices){
        if (idExecutor.equals("") || idServices.isEmpty() ){
            showErrorMessage("Executor ID or Service ID  is not entered");
            return false;
        }
        Optional<ButtonType> action = showAlert("Are you sure to delete services ?").showAndWait();
        if (action.get() == ButtonType.OK) {
            boolean isQueryCorrect = true;
            try {
                String updateQuery = "DELETE  FROM uslugi  where id_wykonawca = '" + idExecutor + "' AND zabieg_id = '" + idExecutor + "';";
                stmt.executeUpdate(updateQuery);
            } catch (SQLException ex) {
                ex.printStackTrace();
                showErrorMessage(ex.getMessage());
                isQueryCorrect = false;
            }
            if (isQueryCorrect)
                showAcceptedRequest("The services have been successfully deleted.");
        }
        return true;
    }


    public boolean correctOneFieldServices(String chosenToCorrect, String newValue , String idExecutor, String idServices){
        if (idExecutor.equals("") || idServices.isEmpty() ){
            showErrorMessage("Executor ID or Service ID  is not entered");
            return false;
        }
        String nameofColumnInDatabase = null;
        switch (chosenToCorrect) {
            case "Price":
                nameofColumnInDatabase = "cena";
                break;
            case "Executor":
                nameofColumnInDatabase = "id_wykonawca";
                break;
            case "Id treatment":
                nameofColumnInDatabase = "zabieg_id";
                break;
        }

        boolean isQueryCorrect = true;
        try {
            String updateQuery = "UPDATE zabieg JOIN uslugi ON id_zabieg = zabieg_id SET " + nameofColumnInDatabase + " = '" + newValue + "' " +
                    " where  id_wykonawca = '"+idExecutor+"' and  zabieg_id = '"+idServices+"';";
            stmt.executeUpdate(updateQuery);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            isQueryCorrect = false;
        }
        if (isQueryCorrect)
            showAcceptedRequest("The changes have been successfully added.");
        return true;
    }

    public String showIncome(String range){
        String returnSteatment = "0.0";
        //String selectQuery = "SELECT Sum(cena) FROM wizyty JOIN  zabieg  z
        // ON cel_wizyty = z.nazwa JOIN uslugi u ON z.id_zabieg = u.zabieg_id WHERE data_wizyty LIKE '2018-01-01%';";
        int amount = 0;
        switch (range){
            case "Day":
                amount = 1;break;
            case "Week":
                amount = 7;break;
            case "Month":
                amount = 30;break;
            case "Year":
                amount = 365;break;
        }
        try {
            String procedure = "CALL dochody('"+amount+"');";
            ResultSet res = stmt.executeQuery(procedure);
            if(res.next()){
                returnSteatment = res.getString("SUM(cena)");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return  "0.0";
        }

        return returnSteatment;
    }

    public boolean correctDuty(String comboValue,String newValue,String id){

        String nameofColumnInDatabase = null;
        switch (comboValue) {
            case "ID Doctor":
                nameofColumnInDatabase = "id_lekarz";
                break;
            case "Day of week":
                nameofColumnInDatabase = "dzien";
                break;
            case "Start":
                nameofColumnInDatabase = "poczatek";
                break;
            case "End":
                nameofColumnInDatabase = "koniec";
                break;
        }

        boolean isQueryCorrect = true;
        try {
            String updateQuery = "UPDATE dyzury SET " + nameofColumnInDatabase + " = '" + newValue + "'  WHERE id_dyzuru = '"+id+"';";
            stmt.executeUpdate(updateQuery);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            isQueryCorrect = false;
        }
        if (isQueryCorrect)
            showAcceptedRequest("The changes have been successfully added.");
        return true;
    }

    public boolean deleteDuty(String id){
        if (id.equals("") ){
            showErrorMessage("OfdiceHourID is not entered");
            return false;
        }
        Optional<ButtonType> action = showAlert("Are you sure to office hour services ?").showAndWait();
        if (action.get() == ButtonType.OK) {
            boolean isQueryCorrect = true;
            try {
                String updateQuery = "DELETE  FROM dyzury WHERE id_dyzuru = '"+id+"';";
                stmt.executeUpdate(updateQuery);
            } catch (SQLException ex) {
                ex.printStackTrace();
                showErrorMessage(ex.getMessage());
                isQueryCorrect = false;
            }
            if (isQueryCorrect)
                showAcceptedRequest("The duty have been successfully deleted.");
        }
        return true;
    }
    public boolean addDuty(String id,String day, String start, String end){
        if(id.isEmpty() || day.isEmpty() || start.isEmpty() || end.isEmpty()){
            showErrorMessage("You must fill all empty files.");
            return false;
        }

        boolean isQueryCorrect = true;
        try {
            String updateQuery = "INSERT INTO dyzury(ID_DYZURU, ID_LEKARZ, DZIEN, POCZATEK, KONIEC)  " +
                    "VALUES ('"+id+"','"+day+"','"+start+"','"+end+"');";
            stmt.executeUpdate(updateQuery);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            isQueryCorrect = false;
        }
        if (isQueryCorrect)
            showAcceptedRequest("The changes have been successfully added.");
        return true;
    }

    public  ObservableList<Holiday> createHolidayTable(){
        ObservableList<Holiday> dataOfHoliday = FXCollections.observableArrayList();
        String select = "SELECT * FROM urlopy;";
        try {
            ResultSet res = stmt.executeQuery(select);
            //Insert Into urlopy(ID_URLOPU, PRACOWNIK, POCZĄTEK_URLOPU, KONIEC_URLOPU, STATUS)
            while (res.next()) {
                String idHoliday = res.getString("ID_URLOPU");
                String employeeId = res.getString("PRACOWNIK");
                String endHoliday = res.getString("POCZĄTEK_URLOPU");
                String startHoliday = res.getString("KONIEC_URLOPU");
                String ststus = res.getString("STATUS");
                dataOfHoliday.add(new Holiday(idHoliday, employeeId, startHoliday,endHoliday,ststus));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        return dataOfHoliday;
    }

    public boolean changeStatusHoliday(String id, String value){
        if(id.isEmpty()){
            showErrorMessage("You have to fill ID Holiday file.");
            return  false;
        }

        boolean isQueryCorrect = true;
        try {
            String updateQuery = "UPDATE `przychodnia`.`urlopy` t SET t.`status` = '"+value+"' WHERE t.`id_urlopu` = '"+id+"'";
            System.out.println(updateQuery);
            stmt.executeUpdate(updateQuery);
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
