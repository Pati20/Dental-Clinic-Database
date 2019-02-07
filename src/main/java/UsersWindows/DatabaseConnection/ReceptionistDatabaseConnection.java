package UsersWindows.DatabaseConnection;

import Protection.Protection;
import UsersWindows.DatabaseClass.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReceptionistDatabaseConnection extends DatabaseConnection {

    public ReceptionistDatabaseConnection(String userId) {
        super(userId);
        Protection protection = new Protection("Receptionist", "ReceptionistPassword");
        setConnection(protection.getUser(), protection.getPassword());
    }


    public ObservableList<DoctorFreeDate> createFreeTermTable(String idDoctor, String dateVisit) {
        ObservableList<DoctorFreeDate> dataOfDoctorFreeDate = FXCollections.observableArrayList();

        String select = "SELECT id_wizyty,lekarz,gabinet,data_wizyty,czas_wizyty,status_wizyty,poczatek,koniec from " +
                " wizyty join dyzury on id_lekarz = lekarz where data_wizyty like '" + dateVisit + "%'  " +
                "  and dzien = DAYNAME('" + dateVisit + "') and lekarz = '" + idDoctor + "';";

        if (idDoctor.length() == 0 || idDoctor.equals("*")) {
            select = "SELECT id_wizyty,lekarz,gabinet,data_wizyty,czas_wizyty,status_wizyty,poczatek,koniec from " +
                    " wizyty join dyzury on id_lekarz = lekarz where data_wizyty like '" + dateVisit + "%'  " +
                    " and dzien = DAYNAME('" + dateVisit + "');";
        }
        try {
            ResultSet res = stmt.executeQuery(select);

            while (res.next()) {
                int id = res.getInt("id_wizyty");
                String iddoctor = res.getString("lekarz");
                int office = res.getInt("gabinet");
                String date = res.getString("data_wizyty");
                String timeOfVisit = res.getString("czas_wizyty");
                String status = res.getString("status_wizyty");
                String startDuty = res.getString("poczatek");
                String endDuty = res.getString("koniec");
                dataOfDoctorFreeDate.add(new DoctorFreeDate(id, iddoctor, office, date, timeOfVisit, status, startDuty, endDuty));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        return dataOfDoctorFreeDate;
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
                nameofColumnInDatabase = "id_usługi";
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
            if (nameofColumnInDatabase.equals("id_usługi")){
                String tmp = null;
             String selectQuery = "select nazwa,id_wykonawca from zabieg join uslugi ON zabieg_id = id_zabieg join wizyty on uslugi.id_usługi = wizyty.id_usługi  where id_wizyty = '" + idVisit + "';";
                ResultSet res = stmt.executeQuery(selectQuery);
                if(res.next())  tmp = res.getString("id_wykonawca");
              String select = "SELECT id_usługi from uslugi join zabieg z on uslugi.zabieg_id = z.id_zabieg where id_wykonawca = "+tmp+" and nazwa = '"+newValue+"';";
                 res = stmt.executeQuery(select);
                if(res.next()) newValue = res.getString("id_usługi");
                else {showErrorMessage("This doctor does not have privilages\n to this treatments");
                return false;}
            }
            String updateQuery = "UPDATE wizyty SET " + nameofColumnInDatabase + " = '" + newValue + "' where id_wizyty = '" + idVisit + "';";
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

    public boolean correctMoreFieldVisit(String Doctor, String idPatient, String office, String time, String date,
                                         String purposeOfVisit, String priority, String status, String idVisit) {

        if (idVisit.length() == 0) {
            showErrorMessage("Visit ID is not entered");
            return false;
        }
        ArrayList<String> listOfDataToCheck = new ArrayList<>();
        ArrayList<String> listOftableName = new ArrayList<>();



        if (Doctor.length() > 3) {
        try {
            String name = Doctor.substring(0, Doctor.indexOf(' '));
            String surname = Doctor.substring(Doctor.indexOf(' ') + 1);

            String selectQuery = "SELECT id_pracownik FROM pracownicy where Imie = '" + name + "' And Nazwisko = '" + surname + "';";
            System.out.println(selectQuery);
            ResultSet res = stmt.executeQuery(selectQuery);

            if (res.next()) {
                String tmp = res.getString("id_pracownik");
                listOfDataToCheck.set(0, tmp);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return false;
        }
    }

        String[] temp = {Doctor, idPatient, office, time, date, purposeOfVisit, priority, status, idVisit};
        String[] temp2 = {"lekarz", "pacjent", "gabinet", "czas_wizyty", "data_wizyty",
                "id_usługi", "priorytet", "status_wizyty", "id_wizyty"};


        for (String s : temp)
            listOfDataToCheck.add(s);

        for (int i = 0; i < temp2.length - 1; i++)
            listOftableName.add(temp2[i]);


        if (!purposeOfVisit.isEmpty()) {

            try {
                String selectQuery = "select nazwa,id_wykonawca from zabieg join uslugi ON zabieg_id = id_zabieg join wizyty on uslugi.id_usługi = wizyty.id_usługi  where id_wizyty = '" + idVisit + "';";
                ResultSet res = stmt.executeQuery(selectQuery);
                if (res.next()) {
                    String tmp = res.getString("id_wykonawca");
                    listOfDataToCheck.set(5, tmp);
                }
                String select = "SELECT id_usługi from uslugi join zabieg z on uslugi.zabieg_id = z.id_zabieg where id_wykonawca = "+listOfDataToCheck.get(5)+" and nazwa = '"+purposeOfVisit+"';";
                 res = stmt.executeQuery(select);
                if (res.next()) {
                    String tmp = res.getString("id_usługi");
                    listOfDataToCheck.set(5, tmp);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                showErrorMessage(ex.getMessage());
                return false;
            }
        }

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

    public boolean addVisit(String drID, String patID, String office, String date, int hour, int min, String timeofVisit,
                            String purpose, String priority) {
        if (drID.length() == 0) {
            showErrorMessage("You must enter doctor Id.");
            return false;
        } else if (office.length() == 0) {
            showErrorMessage("You must enter office.");
            return false;
        } else if (patID.length() == 0) {
            showErrorMessage("You must enter patient Id.");
            return false;
        } else if (!isCorrrectData(date)) {
            showErrorMessage("Choose date from calendar.");
            return false;
        }
        String idTreatment = "Test";
        try {
            String selectquerry = "SELECT uslugi.id_usługi FROM zabieg join uslugi ON zabieg_id = id_zabieg  " +
                    "  where nazwa = '" + purpose + "' AND id_wykonawca = '" + drID + "';";
            ResultSet res = stmt.executeQuery(selectquerry);
            if (res.next()) {
                idTreatment = res.getString("id_usługi");
            } else {
                showErrorMessage("This doctor does not carry out this surgery.");
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return false;
        }

        String dateOfVisit = date + " " + hour + ":" + min + ":00";
        try {
            String insertQuery = "INSERT INTO wizyty( LEKARZ, PACJENT, GABINET, DATA_WIZYTY, CZAS_WIZYTY, ID_USŁUGI, PRIORYTET, STATUS_WIZYTY) " +
                    "VALUES ('" + drID + "','" + patID + "','" + office + "','" + dateOfVisit + "','" + timeofVisit + "','" + idTreatment + "','" + priority + "','oczekująca');";
            stmt.executeUpdate(insertQuery);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return false;
        }
        showAcceptedRequest("The changes have been successfully added.");
        return true;
    }


    public ObservableList<Treatments> createTreatmentsTable(String id, String treatments) {
        ObservableList<Treatments> dataOfTreatments = FXCollections.observableArrayList();
        String select = "SELECT * from uslugi join zabieg z on uslugi.zabieg_id = z.id_zabieg where nazwa = '" + treatments + "' OR id_wykonawca = '" + id + "'";
        if (!id.isEmpty() && !treatments.isEmpty()) {
            select = "SELECT * from uslugi join zabieg z on uslugi.zabieg_id = z.id_zabieg where nazwa = '" + treatments + "' AND id_wykonawca = '" + id + "'";
        }
        try {
            ResultSet res = stmt.executeQuery(select);

            while (res.next()) {
                String Id = res.getString("id_wykonawca");
                String price = res.getString("cena");
                String nameOftreatments = res.getString("nazwa");
                dataOfTreatments.add(new Treatments(Id, price, nameOftreatments));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        return dataOfTreatments;
    }
}

