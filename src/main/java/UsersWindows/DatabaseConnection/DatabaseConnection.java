package UsersWindows.DatabaseConnection;

import UsersWindows.DatabaseClass.Doctor;
import UsersWindows.DatabaseClass.OfficeHoursExtend;
import UsersWindows.DatabaseClass.Patient;
import UsersWindows.DatabaseClass.Visit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseConnection {

    Statement stmt;
    String userId;

     DatabaseConnection(String userId){
         this.userId = userId;
    }

    void setConnection(String user, String userPassword) {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/przychodnia?characterEncoding=UTF-8", user,userPassword);
            stmt = conn.createStatement();

        } catch (
                SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ObservableList<Visit> findPatientVisit(String idPatient, String idDoctor, String office, String date,
                                                  String purposeOfVisit, String priority, String status) {
        ObservableList<Visit> dataOfVisits = FXCollections.observableArrayList();

        if(!purposeOfVisit.isEmpty()){
            try {
                String selectquerry = "SELECT zabieg_id FROM zabieg join uslugi ON zabieg_id = id_zabieg  " +
                        "  where nazwa = '" + purposeOfVisit + "' ;";
                ResultSet res = stmt.executeQuery(selectquerry);
                if (res.next()) {
                    purposeOfVisit = res.getString("zabieg_id");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                showErrorMessage(ex.getMessage());
                return null;
            }
        }
        System.out.println(purposeOfVisit);
        try {
            String select = "SELECT  id_wizyty,Imie,Nazwisko,pacjent,gabinet,data_wizyty,czas_wizyty,nazwa,priorytet,status_wizyty" +
                    " FROM wizyty join pracownicy  ON wizyty.lekarz =  pracownicy.id_pracownik join uslugi u on wizyty.id_usługi = u.id_usługi join zabieg z on u.zabieg_id = z.id_zabieg   where  wizyty.lekarz = '" + idDoctor + "' OR pacjent = '" + idPatient + "'" +
                    " OR gabinet = '" + office + "' OR data_wizyty = '" + date + "'  OR zabieg_id = '" + purposeOfVisit + "' OR priorytet = '" + priority + "' OR status_wizyty = '" + status + "';";
            ResultSet res = stmt.executeQuery(select);

            while (res.next()) {
                int id = res.getInt("id_wizyty");
                int idPat = res.getInt("pacjent");
                int off = res.getInt("gabinet");
                String name = res.getString("Imie");
                String surname = res.getString("Nazwisko");
                String dateVisit = res.getString("data_wizyty");
                String timeVisit = res.getString("czas_wizyty");
                String goalVisit = res.getString("nazwa");
                String priority2 = res.getString("priorytet");
                String stateVisit = res.getString("status_wizyty");
                String doctor = name + " " + surname;
                dataOfVisits.add(new Visit(id, doctor, idPat, off, dateVisit, timeVisit, goalVisit, priority2, stateVisit));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        return dataOfVisits;
    }
    public ObservableList<Patient> findPatient(String name, String surname, String pesel) {
        ObservableList<Patient> dataOfPatient = FXCollections.observableArrayList();
//        if(name.length() ==  0 && surname.length() == 0 && pesel == null ) {
//            showErrorMessage("Before search enter any data");
//            return null;
//        }

        try {
            String insertQuery = "SELECT * FROM pacjent WHERE (Imie = '" + name + "' OR Nazwisko = '" + surname + "') OR (Pesel = '" + pesel + "');";
            if(name.length() ==  0 && surname.length() == 0 && pesel == null ) insertQuery = "SELECT * FROM pacjent;";
            ResultSet res = stmt.executeQuery(insertQuery);

            while (res.next()) {
                int id = res.getInt("id_pacjent");
                String firstName = res.getString("Imie");
                String lastName = res.getString("Nazwisko");
                String peselNr = res.getString("Pesel");
                int nr = res.getInt("nr_telefonu");
                String date = res.getString("Data_urodzenia");
                String adres = res.getString("Adres");
                dataOfPatient.add(new Patient(id, firstName, lastName, peselNr, nr, date, adres));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        if (dataOfPatient.size() == 0) {
            showErrorMessage("Patient not found.");
        }
        return dataOfPatient;
    }

    public boolean addNewPatient(String name, String surname, String pesel, String nrPhone, String dateOfBirth, String address){

         if(name.length() == 0 || pesel.length() == 0 || nrPhone.length() == 0 || dateOfBirth.length() == 0 ||  address.length() == 0 ){
             showErrorMessage("You must enter all data in fields");
             return false;
         }
        boolean isQueryCorrect = true;
        try {
            String insertQuery = "INSERT INTO pacjent(Imie, Nazwisko, Pesel, nr_telefonu, Data_urodzenia, Adres)" +
                    "VALUES ('"+name+"', '"+surname+"', '"+pesel+"', '"+nrPhone+"', '"+dateOfBirth+"', '"+address+"');";
            stmt.executeUpdate(insertQuery);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            isQueryCorrect = false;
        }
        if (isQueryCorrect)
            showAcceptedRequest("The patient was successfully added \n to the database.");
        return true;
    }

    public boolean removeVisit(String visitId){

        boolean isQueryCorrect = true;
        try {
            String insertQuery = "DELETE  FROM wizyty where id_wizyty = '"+visitId+"';";
            stmt.executeUpdate(insertQuery);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            isQueryCorrect = false;
        }
        if (isQueryCorrect)
            showAcceptedRequest("The visit was successfully \n removed from database.");
        return true;
    }

    public ObservableList<Doctor> createDoctorTable() {
        ObservableList<Doctor> dataOfDoctors = FXCollections.observableArrayList();

        try {
            String select = "SELECT id_pracownik,Imie,Nazwisko,Specjalizacja FROM pracownicy join lekarz ON id_pracownik = lekarz.id_lekarz;";
            ResultSet res = stmt.executeQuery(select);

            while (res.next()) {
                String id = res.getString("id_pracownik");
                String name = res.getString("Imie");
                String surname = res.getString("Nazwisko");
                String specialization = res.getString("Specjalizacja");
                dataOfDoctors.add(new Doctor(id, name, surname, specialization));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        return dataOfDoctors;
    }

    public ObservableList<OfficeHoursExtend> createDutyTable(String id, String day, String start, String end) {
        ObservableList<OfficeHoursExtend> dataOfOfficeHoursExtend = FXCollections.observableArrayList();

        String select;
        if (id.isEmpty() && day.isEmpty() && start.isEmpty() && end.isEmpty()) {
            select = "SELECT * FROM dyzury ";
        } else {
            select = "SELECT * FROM dyzury where dzien = '" + day + "' OR poczatek = '" + start + "' OR koniec = '" + end + "' OR id_lekarz = '" + id + "';";
        }
        try {
            ResultSet res = stmt.executeQuery(select);
            while (res.next()) {
                String Id = res.getString("id_lekarz");
                String IdOfficeHour = res.getString("id_dyzuru");
                String Day = res.getString("dzien");
                String Start = res.getString("poczatek");
                String End = res.getString("koniec");
                dataOfOfficeHoursExtend.add(new OfficeHoursExtend(Day, Start, End, Id,IdOfficeHour));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        return dataOfOfficeHoursExtend;
    }

    public void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Error message");
        alert.setContentText("" + message);
        alert.show();
    }

    public void showAcceptedRequest(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Information message");
        alert.setContentText(message);
        alert.show();
    }

    boolean isCorrrectData(String first) {
        try {
            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(first);
        } catch (Exception ex) {
            showErrorMessage("Inncorect data format!\n" +
                    "Date should look like: \"yyyy-MM-dd\"");
            return false;
        }
        return true;
    }

    public boolean correctOneFieldPatient(String chosenToCorrect, String newValue, String idPatient) {
        if (idPatient.equals("") || idPatient.equals(null)) {
            showErrorMessage("Patient ID is not entered");
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
            String updateQuery = "UPDATE pacjent SET " + nameofColumnInDatabase + " = '" + newValue + "' where id_pacjent = '" + idPatient + "';";
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

    public boolean correctMoreFieldPatient(String name, String surname, String pesel, String nrPhone, String dateOfBirth, String address, String id) {

        if (id.length() == 0) {
            showErrorMessage("Patient ID is not entered");
            return false;
        }
        ArrayList<String> listOfDataToCheck = new ArrayList<>();
        ArrayList<String> listOftableName = new ArrayList<>();

        String[] temp = {name, surname, pesel, nrPhone, dateOfBirth, address, id};
        String[] temp2 = {"Imie", "Nazwisko", "Pesel", "nr_telefonu", "Data_urodzenia", "Adres", "id_pacjent"};

        for (String s : temp)
            listOfDataToCheck.add(s);

        for (int i = 0; i < temp2.length - 1; i++)
            listOftableName.add(temp2[i]);


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


        StringBuilder updateQuery = new StringBuilder("UPDATE pacjent SET ");
        int i = 0;
        for (; i <= listOfDataToCheck.size() - 3; i++) {
            updateQuery.append(listOftableName.get(i)).append(" = '").append(listOfDataToCheck.get(i)).append("',");
        }
        updateQuery.append(listOftableName.get(i)).append(" = '").append(listOfDataToCheck.get(i)).append("' ");
        updateQuery.append("where id_pacjent = '").append(listOfDataToCheck.get(i + 1)).append("';");

        boolean isQueryCorrect = true;
        try {
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

    public boolean correctOneFieldVisit(String chosenToCorrect, String newValue, String idVisit) {
        if (idVisit.equals("") || idVisit.equals(null)) {
            showErrorMessage("Visit ID is not entered");
            return false;
        }
        boolean isChangeInDoctor = false;
        String nameofColumnInDatabase = null;
        switch (chosenToCorrect) {
            case "Doctor":
                nameofColumnInDatabase = "lekarz";
                isChangeInDoctor = true;
                break;
            case "idPat":
                nameofColumnInDatabase = "pacjent";
                break;
            case "Office":
                nameofColumnInDatabase = "gabinet";
                break;
            case "Date of the visit":
                nameofColumnInDatabase = "data_wizyty";
                break;
            case "Time":
                nameofColumnInDatabase = "czas_wizyty";
                break;
            case "Purpose":
                nameofColumnInDatabase = "cel_wizyty";
                break;
            case "Priority":
                nameofColumnInDatabase = "priorytet";
                break;
            case "Status":
                nameofColumnInDatabase = "status_wizyty";
                break;
        }


        boolean isQueryCorrect = true;
        try {
            if (isChangeInDoctor) {

                String name = newValue.substring(0, newValue.indexOf(' '));
                String surname = newValue.substring(newValue.indexOf(' ') + 1);

                String selectQuery = "SELECT id_pracownik FROM pracownicy where Imie = '" + name + "' And Nazwisko = '" + surname + "';";
                ResultSet res = stmt.executeQuery(selectQuery);

                if (res.next()) {
                    newValue = res.getString("id_pracownik");
                }
            }
            String updateQuery = "UPDATE wizyty SET " + nameofColumnInDatabase + " = '" + newValue + "' where id_wizyty = '" + idVisit + "';";
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

    public boolean correctMoreFieldVisit(String Doctor, String idPatient, String office, String time, String date,
                                         String purposeOfVisit, String priority, String status, String idVisit) {

        if (idVisit.length() == 0) {
            showErrorMessage("Visit ID is not entered");
            return false;
        }
        ArrayList<String> listOfDataToCheck = new ArrayList<>();
        ArrayList<String> listOftableName = new ArrayList<>();

        boolean isChangeInDoctor = false;
        if (Doctor.length() > 3) {
            isChangeInDoctor = true;
        }
        try {
            if (isChangeInDoctor) {

                String name = Doctor.substring(0, Doctor.indexOf(' '));
                String surname = Doctor.substring(Doctor.indexOf(' ') + 1);

                String selectQuery = "SELECT id_pracownik FROM pracownicy where Imie = '" + name + "' And Nazwisko = '" + surname + "';";
                System.out.println(selectQuery);
                ResultSet res = stmt.executeQuery(selectQuery);

                if (res.next()) {
                    String tmp = res.getString("id_pracownik");
                    listOfDataToCheck.set(0, tmp);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return false;
        }

        String[] temp = {Doctor, idPatient, office, time, date, purposeOfVisit, priority, status, idVisit};
        String[] temp2 = {"lekarz", "pacjent", "gabinet", "czas_wizyty", "data_wizyty",
                "cel_wizyty", "priorytet", "status_wizyty", "id_wizyty"};


        for (String s : temp)
            listOfDataToCheck.add(s);

        for (int i = 0; i < temp2.length - 1; i++)
            listOftableName.add(temp2[i]);


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

        StringBuilder updateQuery = new StringBuilder("UPDATE wizyty SET ");
        int i = 0;
        for (; i <= listOfDataToCheck.size() - 3; i++) {
            updateQuery.append(listOftableName.get(i)).append(" = '").append(listOfDataToCheck.get(i)).append("',");
        }
        updateQuery.append(listOftableName.get(i)).append(" = '").append(listOfDataToCheck.get(i)).append("' ");
        updateQuery.append("where id_wizyty = '").append(listOfDataToCheck.get(i + 1)).append("';");

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
    Alert showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert;
    }
}
