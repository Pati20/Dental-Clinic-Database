package UsersWindows.DatabaseConnection;

import Protection.Protection;
import UsersWindows.DatabaseClass.OfficeHours;
import UsersWindows.DatabaseClass.Visit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DoctorDatabaseConnection extends DatabaseConnection{

    public DoctorDatabaseConnection(String userId) {
        super(userId);
        Protection protection = new Protection("Doctor", "DoctorPassword");
        setConnection(protection.getUser(), protection.getPassword());
    }

    public void getHoliday(String dateStart, String dateEnd) {

        if (isCorrrectData(dateStart) && isCorrrectData(dateEnd)) {
            boolean isQueryCorrect = true;
            try {
                String insertQuery = "INSERT INTO urlopy(ID_URLOPU, PRACOWNIK, POCZĄTEK_URLOPU, KONIEC_URLOPU, STATUS) " +
                        "VALUES (NULL," + Integer.parseInt(userId) + ",'" + dateStart + "','" + dateEnd + "','oczekujący na decyzję')";
                System.out.println(insertQuery);
                stmt.executeUpdate(insertQuery);
            } catch (SQLException ex) {
                ex.printStackTrace();
                showErrorMessage(ex.getMessage());
                isQueryCorrect = false;
            }
            if (isQueryCorrect)
                showAcceptedRequest("A request for a holiday was accepted.");
        }

    }


    public ObservableList<Visit> findPatientVisit(String patientName, String patientSurname, String patientId) {
        ObservableList<Visit> dataOfVisits = FXCollections.observableArrayList();

        try {
            String select = "SELECT * from wizyty  w join pacjent p on w.pacjent = p.id_pacjent  JOIN uslugi u on w.id_usługi = u.id_usługi " +
                    "JOIN zabieg z on u.zabieg_id = z.id_zabieg where " +
                    "pacjent = '" + patientId + "' OR (p.Imie = '" + patientName + "' and p.Nazwisko = '" + patientSurname + "');";
            ResultSet res = stmt.executeQuery(select);

            while (res.next()) {
                int id = res.getInt("id_wizyty");
                int idPatient = res.getInt("pacjent");
                int office = res.getInt("gabinet");
                String dateVisit = res.getString("data_wizyty");
                String timeVisit = res.getString("czas_wizyty");
                String goalVisit = res.getString("nazwa");
                String priority = res.getString("priorytet");
                String stateVisit = res.getString("status_wizyty");

               // String dataDoctor=null;
                String idDoctor = res.getString("lekarz");
//                String doctorSelectData = "SELECT imie,Nazwisko from pracownicy WHERE id_pracownik = '" + idDoctor + "';";
//                Statement stm2 = stmt;
//                ResultSet resultSet = stm2.executeQuery(doctorSelectData);
//                if(resultSet.next()){
//                    String name = resultSet.getString("Imie");
//                    String surname = resultSet.getString("Nazwisko");
//                    dataDoctor = name + " " + surname;
//                }
                dataOfVisits.add(new Visit(id, idDoctor, idPatient, office, dateVisit, timeVisit, goalVisit, priority, stateVisit));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        return dataOfVisits;

    }

    public ObservableList<OfficeHours> showDutyHours(String userId){
          ObservableList<OfficeHours> dataOfficeHours = FXCollections.observableArrayList();

        try {
            String selectQuery = "SELECT dzien,poczatek,koniec from dyzury where id_lekarz = '"+userId+"';";
            ResultSet res = stmt.executeQuery(selectQuery);

            while (res.next()) {
                String day = res.getString("dzien");
                String start = res.getString("poczatek");
                String end = res.getString("koniec");
                dataOfficeHours.add(new OfficeHours(day,start,end));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage(ex.getMessage());
            return null;
        }
        return dataOfficeHours;
    }




}

