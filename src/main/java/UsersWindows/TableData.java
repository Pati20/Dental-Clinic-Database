package UsersWindows;

import UsersWindows.DatabaseClass.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


class TableData {

    final TableView<Patient> tableOfPatient = new TableView<>();
    final TableView<Visit> tableOfVisits = new TableView<>();
    final TableView<OfficeHours> tableOfficeHours = new TableView<>();
    final TableView<Doctor> tableOfDoctor = new TableView<>();
    final TableView<Treatments> tableOfTreatments = new TableView<>();
    final TableView<DoctorFreeDate> tableOfDoctorFreeDate = new TableView<>();
    final TableView<OfficeHoursExtend> tableOfficeHoursExtend = new TableView<>();
    final TableView<Users> tableOfUsers = new TableView<>();
    final TableView<Employee> tableOfEmployee= new TableView<>();
    final TableView<Log> tableOfLog= new TableView<>();
    final TableView<Services> tableOfServices= new TableView<>();
    final TableView<Holiday> tableOfHoliday= new TableView<>();
    TableColumn firstVisitNameCol;

    ObservableList<DoctorFreeDate> dataOfDoctorFreeDate = FXCollections.observableArrayList();
    ObservableList<Patient> dataOfPatient = FXCollections.observableArrayList();
    ObservableList<OfficeHoursExtend> dataOfficeHoursExtend = FXCollections.observableArrayList();
    ObservableList<Doctor> dataOffDoctor = FXCollections.observableArrayList();
    ObservableList<Visit> dataOfVisits = FXCollections.observableArrayList();
    ObservableList<OfficeHours> dataOfficeHours = FXCollections.observableArrayList();
    ObservableList<Treatments> dataOfTreatments = FXCollections.observableArrayList();
    ObservableList<Users> dataOfUsers = FXCollections.observableArrayList();
    ObservableList<Employee> dataOfEmployee = FXCollections.observableArrayList();
    ObservableList<Log> dataOfLog = FXCollections.observableArrayList();
    ObservableList<Services> dataOfServices = FXCollections.observableArrayList();
    ObservableList<Holiday> dataOfHoliday = FXCollections.observableArrayList();

    TableData() {
        createTableOfPatient();
        createDoctorTableColumn();
        createDoctorFreeDateTableColumn();
        createTreatmentsTableColumn();
    }

    void createTableOfPatient() {
        TableColumn firstNameCol = new TableColumn("Id");
        firstNameCol.setMaxWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn secondNameCol = new TableColumn("Name");
        secondNameCol.setMinWidth(110);
        secondNameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));

        TableColumn thirdNameCol = new TableColumn("Surname");
        thirdNameCol.setMinWidth(110);
        thirdNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));

        TableColumn fourthNameCol = new TableColumn("Pesel");
        fourthNameCol.setMinWidth(100);
        fourthNameCol.setCellValueFactory(
                new PropertyValueFactory<>("pesel"));

        TableColumn fifthNameCol = new TableColumn("No. tel.");
        fifthNameCol.setMinWidth(100);
        fifthNameCol.setCellValueFactory(
                new PropertyValueFactory<>("nr_tel"));

        TableColumn sixthNameCol = new TableColumn("Date of birth.");
        sixthNameCol.setMinWidth(100);
        sixthNameCol.setCellValueFactory(
                new PropertyValueFactory<>("data"));

        TableColumn seventhNameCol = new TableColumn("Address");
        seventhNameCol.setMinWidth(250);
        seventhNameCol.setCellValueFactory(
                new PropertyValueFactory<>("adres"));

        tableOfPatient.setItems(dataOfPatient);
        tableOfPatient.getColumns().addAll(firstNameCol, secondNameCol, thirdNameCol, fourthNameCol, fifthNameCol, sixthNameCol, seventhNameCol);

    }

    void createVisitTableColumn() {
         firstVisitNameCol = new TableColumn("Id");
        firstVisitNameCol.setMaxWidth(35);
        firstVisitNameCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn secondVisitNameCol = new TableColumn("Doctor");
        secondVisitNameCol.setMinWidth(110);
        secondVisitNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lekarz"));

        TableColumn thirdVisitNameCol = new TableColumn("IdPat.");
        thirdVisitNameCol.setMaxWidth(46);
        thirdVisitNameCol.setCellValueFactory(
                new PropertyValueFactory<>("idPacjent"));

        TableColumn fourthVisitNameCol = new TableColumn("Office");
        fourthVisitNameCol.setMaxWidth(50);
        fourthVisitNameCol.setCellValueFactory(
                new PropertyValueFactory<>("gabinet"));

        TableColumn fifthVisitNameCol = new TableColumn("Date of the visit");
        fifthVisitNameCol.setMinWidth(150);
        fifthVisitNameCol.setCellValueFactory(
                new PropertyValueFactory<>("data_wizyty"));

        TableColumn sixthVisitNameCol = new TableColumn("Time");
        sixthVisitNameCol.setMaxWidth(50);
        sixthVisitNameCol.setCellValueFactory(
                new PropertyValueFactory<>("czas_wizyty"));

        TableColumn seventhVisitNameCol = new TableColumn("Purpose");
        seventhVisitNameCol.setMinWidth(205);
        seventhVisitNameCol.setCellValueFactory(
                new PropertyValueFactory<>("cel_wizyty"));

        TableColumn eighthVisitNameCol = new TableColumn("Priority");
        eighthVisitNameCol.setMaxWidth(65);
        eighthVisitNameCol.setCellValueFactory(
                new PropertyValueFactory<>("priorytet"));


        TableColumn ninethVisitNameCol = new TableColumn("Status");
        ninethVisitNameCol.setMinWidth(80);
        ninethVisitNameCol.setCellValueFactory(
                new PropertyValueFactory<>("status_wizyty"));

        tableOfVisits.setItems(dataOfVisits);
        tableOfVisits.getColumns().addAll(firstVisitNameCol, secondVisitNameCol, thirdVisitNameCol, fourthVisitNameCol, fifthVisitNameCol,
                sixthVisitNameCol, seventhVisitNameCol, eighthVisitNameCol, ninethVisitNameCol);
    }

    void createOfficeHoursTableColumn() {

        TableColumn firstOfficeHoursNameCol = new TableColumn("Day");
        firstOfficeHoursNameCol.setMinWidth(133);
        firstOfficeHoursNameCol.setCellValueFactory(
                new PropertyValueFactory<>("dzien"));

        TableColumn secondOfficeHoursNameCol = new TableColumn("Start");
        secondOfficeHoursNameCol.setMinWidth(133);
        secondOfficeHoursNameCol.setCellValueFactory(
                new PropertyValueFactory<>("poczatek"));

        TableColumn thirdOfficeHoursNameCol = new TableColumn("End");
        thirdOfficeHoursNameCol.setMinWidth(133);
        thirdOfficeHoursNameCol.setCellValueFactory(
                new PropertyValueFactory<>("koniec"));

        tableOfficeHours.setItems(dataOfficeHours);
        tableOfficeHours.getColumns().addAll(firstOfficeHoursNameCol, secondOfficeHoursNameCol, thirdOfficeHoursNameCol);
    }

    void createDoctorTableColumn() {

        TableColumn firstDoctorNameCol = new TableColumn("Doctor ID");
        firstDoctorNameCol.setMinWidth(133);
        firstDoctorNameCol.setCellValueFactory(
                new PropertyValueFactory<>("id_pracownik"));

        TableColumn secondDoctorCol = new TableColumn("Name");
        secondDoctorCol.setMinWidth(133);
        secondDoctorCol.setCellValueFactory(
                new PropertyValueFactory<>("Imie"));

        TableColumn thirdDoctorCol = new TableColumn("Surname");
        thirdDoctorCol.setMinWidth(180);
        thirdDoctorCol.setCellValueFactory(
                new PropertyValueFactory<>("Nazwisko"));

        TableColumn fourthDoctorNameCol = new TableColumn("Specialization");
        fourthDoctorNameCol.setMinWidth(410);
        fourthDoctorNameCol.setCellValueFactory(
                new PropertyValueFactory<>("Specjalizacja"));

        tableOfDoctor.setItems(dataOffDoctor);
        tableOfDoctor.getColumns().addAll(firstDoctorNameCol, secondDoctorCol, thirdDoctorCol, fourthDoctorNameCol);
    }

    void createDoctorFreeDateTableColumn() {

        TableColumn firstDoctorFreeDateTableCol = new TableColumn("ID visit");
        firstDoctorFreeDateTableCol.setMinWidth(35);
        firstDoctorFreeDateTableCol.setCellValueFactory(
                new PropertyValueFactory<>("idwizyta"));

        TableColumn secondDoctorFreeDateTableCol = new TableColumn("ID Doctor");
        secondDoctorFreeDateTableCol.setMinWidth(35);
        secondDoctorFreeDateTableCol.setCellValueFactory(
                new PropertyValueFactory<>("idlekarz"));

        TableColumn thirdDoctorFreeDateTableCol = new TableColumn("Office");
        thirdDoctorFreeDateTableCol.setMinWidth(60);
        thirdDoctorFreeDateTableCol.setCellValueFactory(
                new PropertyValueFactory<>("gabinet"));

        TableColumn fourthDoctorFreeDateTableCol = new TableColumn("Date");
        fourthDoctorFreeDateTableCol.setMinWidth(80);
        fourthDoctorFreeDateTableCol.setCellValueFactory(
                new PropertyValueFactory<>("data_wizyty"));

        TableColumn fifthDoctorFreeDateTableCol = new TableColumn("Time ");
        fifthDoctorFreeDateTableCol.setMinWidth(80);
        fifthDoctorFreeDateTableCol.setCellValueFactory(
                new PropertyValueFactory<>("czas_wizyty"));

        TableColumn sixthDoctorFreeDateTableCol = new TableColumn("Status");
        sixthDoctorFreeDateTableCol.setMaxWidth(150);
        sixthDoctorFreeDateTableCol.setCellValueFactory(
                new PropertyValueFactory<>("status_wizyty"));

        TableColumn seventhDoctorFreeDateTableCol = new TableColumn("Start duty");
        seventhDoctorFreeDateTableCol.setMinWidth(100);
        seventhDoctorFreeDateTableCol.setCellValueFactory(
                new PropertyValueFactory<>("poczatek"));

        TableColumn eighthDoctorFreeDateTableColl = new TableColumn("End duty");
        eighthDoctorFreeDateTableColl.setMaxWidth(100);
        eighthDoctorFreeDateTableColl.setCellValueFactory(
                new PropertyValueFactory<>("koniec"));

        tableOfDoctorFreeDate.setItems(dataOfDoctorFreeDate);
        tableOfDoctorFreeDate.getColumns().addAll(firstDoctorFreeDateTableCol, secondDoctorFreeDateTableCol, thirdDoctorFreeDateTableCol
                , fourthDoctorFreeDateTableCol, fifthDoctorFreeDateTableCol, sixthDoctorFreeDateTableCol, seventhDoctorFreeDateTableCol
                , eighthDoctorFreeDateTableColl);
    }

    void createTreatmentsTableColumn(){
        TableColumn firstTreatmentsTableCol = new TableColumn("ID Doctor");
        firstTreatmentsTableCol.setMinWidth(100);
        firstTreatmentsTableCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn secondOTreatmentsTableCol = new TableColumn("Price");
        secondOTreatmentsTableCol.setMinWidth(100);
        secondOTreatmentsTableCol.setCellValueFactory(
                new PropertyValueFactory<>("cena"));

        TableColumn thirdTreatmentsTableCol = new TableColumn("Name of treatments");
        thirdTreatmentsTableCol.setMinWidth(300);
        thirdTreatmentsTableCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa"));

        tableOfTreatments.setItems(dataOfTreatments);
        tableOfTreatments.getColumns().addAll(firstTreatmentsTableCol,secondOTreatmentsTableCol,thirdTreatmentsTableCol);
    }

    void createTableOfOfficeHoursExtend(int guard) {

        TableColumn firstOfficeHoursRecepcjonistleCol = new TableColumn("ID Doctor");
        firstOfficeHoursRecepcjonistleCol.setMinWidth(35);
        firstOfficeHoursRecepcjonistleCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn secondOfficeHoursRecepcjonistTableCol = new TableColumn("Day of week");
        secondOfficeHoursRecepcjonistTableCol.setMinWidth(100);
        secondOfficeHoursRecepcjonistTableCol.setCellValueFactory(
                new PropertyValueFactory<>("dzien"));

        TableColumn thirdOfficeHoursRecepcjonistTableCol = new TableColumn("Start");
        thirdOfficeHoursRecepcjonistTableCol.setMinWidth(150);
        thirdOfficeHoursRecepcjonistTableCol.setCellValueFactory(
                new PropertyValueFactory<>("poczatek"));

        TableColumn fourthOfficeHoursRecepcjonistTableCol = new TableColumn("End");
        fourthOfficeHoursRecepcjonistTableCol.setMinWidth(150);
        fourthOfficeHoursRecepcjonistTableCol.setCellValueFactory(
                new PropertyValueFactory<>("koniec"));

//        TableColumn fifthOfficeHoursRecepcjonistTableCol = new TableColumn("Holiday");
//        fifthOfficeHoursRecepcjonistTableCol.setMinWidth(100);
//        fifthOfficeHoursRecepcjonistTableCol.setCellValueFactory(
//                new PropertyValueFactory<>("urlop"));

        TableColumn sixthOfficeHoursRecepcjonistTableCol = new TableColumn("Id Office Hour");
        sixthOfficeHoursRecepcjonistTableCol.setMinWidth(100);
        sixthOfficeHoursRecepcjonistTableCol.setCellValueFactory(
                new PropertyValueFactory<>("idOfficeHour"));

        tableOfficeHoursExtend.setItems(dataOfficeHoursExtend);

        if(guard == 0){
            tableOfficeHoursExtend.getColumns().addAll(firstOfficeHoursRecepcjonistleCol, secondOfficeHoursRecepcjonistTableCol, thirdOfficeHoursRecepcjonistTableCol
                    , fourthOfficeHoursRecepcjonistTableCol /*,fifthOfficeHoursRecepcjonistTableCol*/);
        }else {
            tableOfficeHoursExtend.getColumns().addAll(sixthOfficeHoursRecepcjonistTableCol,firstOfficeHoursRecepcjonistleCol, secondOfficeHoursRecepcjonistTableCol, thirdOfficeHoursRecepcjonistTableCol
                    , fourthOfficeHoursRecepcjonistTableCol/*, fifthOfficeHoursRecepcjonistTableCol*/);
        }
    }

    void createUsersTableColumn() {

        TableColumn firstUserNameCol = new TableColumn("User ID");
        firstUserNameCol.setMinWidth(133);
        firstUserNameCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn secondUserCol = new TableColumn("Login");
        secondUserCol.setMinWidth(180);
        secondUserCol.setCellValueFactory(
                new PropertyValueFactory<>("login"));

        TableColumn thirdUserCol = new TableColumn("Password");
        thirdUserCol.setMinWidth(200);
        thirdUserCol.setCellValueFactory(
                new PropertyValueFactory<>("password"));


        TableColumn fourthUserNameCol = new TableColumn("Postion");
        fourthUserNameCol.setMinWidth(200);
        fourthUserNameCol.setCellValueFactory(
                new PropertyValueFactory<>("postion"));

        tableOfUsers.setItems(dataOfUsers);
        tableOfUsers.getColumns().addAll(firstUserNameCol, secondUserCol, thirdUserCol, fourthUserNameCol);
    }

    void createTableOfEmploye() {
        TableColumn firstNameColOfEmployee = new TableColumn("Id");
        firstNameColOfEmployee.setMaxWidth(35);
        firstNameColOfEmployee.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn secondNameColOfEmployee = new TableColumn("Name");
        secondNameColOfEmployee.setMinWidth(110);
        secondNameColOfEmployee.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));

        TableColumn thirdNameColOfEmployee = new TableColumn("Surname");
        thirdNameColOfEmployee.setMinWidth(110);
        thirdNameColOfEmployee.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));

        TableColumn fourthNameColOfEmployee = new TableColumn("Pesel");
        fourthNameColOfEmployee.setMinWidth(100);
        fourthNameColOfEmployee.setCellValueFactory(
                new PropertyValueFactory<>("pesel"));

        TableColumn fifthNameColOfEmployee = new TableColumn("Nr tel.");
        fifthNameColOfEmployee.setMinWidth(100);
        fifthNameColOfEmployee.setCellValueFactory(
                new PropertyValueFactory<>("nr_tel"));

        TableColumn sixthNameColOfEmployee = new TableColumn("Date of birth.");
        sixthNameColOfEmployee.setMinWidth(100);
        sixthNameColOfEmployee.setCellValueFactory(
                new PropertyValueFactory<>("data"));

        TableColumn seventhNameColOfEmployee = new TableColumn("Address");
        seventhNameColOfEmployee.setMinWidth(250);
        seventhNameColOfEmployee.setCellValueFactory(
                new PropertyValueFactory<>("Adres"));

        TableColumn eigthNameColOfEmployee = new TableColumn("Salary");
        eigthNameColOfEmployee.setMinWidth(100);
        eigthNameColOfEmployee.setCellValueFactory(
                new PropertyValueFactory<>("salary"));

        tableOfEmployee.setItems(dataOfEmployee);
        tableOfEmployee.getColumns().addAll(firstNameColOfEmployee, secondNameColOfEmployee, thirdNameColOfEmployee, fourthNameColOfEmployee,
                fifthNameColOfEmployee, sixthNameColOfEmployee, seventhNameColOfEmployee,eigthNameColOfEmployee);

    }

    void createTableOfLog(){

        TableColumn firstNameCol = new TableColumn("Id");
        firstNameCol.setMaxWidth(35);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn secondNameCol = new TableColumn("Id visit");
        secondNameCol.setMinWidth(110);
        secondNameCol.setCellValueFactory(
                new PropertyValueFactory<>("visitID"));

        TableColumn thirdNameCol = new TableColumn("Old Date");
        thirdNameCol.setMinWidth(110);
        thirdNameCol.setCellValueFactory(
                new PropertyValueFactory<>("oldDate"));

        TableColumn fourthNameCol = new TableColumn("New Date");
        fourthNameCol.setMinWidth(100);
        fourthNameCol.setCellValueFactory(
                new PropertyValueFactory<>("newDate"));

        TableColumn fifthNameCol = new TableColumn("Old Dr.");
        fifthNameCol.setMinWidth(100);
        fifthNameCol.setCellValueFactory(
                new PropertyValueFactory<>("oldDoctor"));

        TableColumn sixthNameCol = new TableColumn("New Dr.");
        sixthNameCol.setMinWidth(100);
        sixthNameCol.setCellValueFactory(
                new PropertyValueFactory<>("newDoctor"));

        TableColumn seventhNameCol = new TableColumn("Date of Change");
        seventhNameCol.setMinWidth(250);
        seventhNameCol.setCellValueFactory(
                new PropertyValueFactory<>("dateOfChange"));

        tableOfLog.setItems(dataOfLog);
        tableOfLog.getColumns().addAll(firstNameCol, secondNameCol, thirdNameCol, fourthNameCol,fifthNameCol,
                sixthNameCol,seventhNameCol);
    }

    void createTableColumnOfServices(){

        TableColumn firstNameColOfServices = new TableColumn("Id executor");
        firstNameColOfServices.setMaxWidth(100);
        firstNameColOfServices.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn secondNameColOfServices = new TableColumn("Treatment");
        secondNameColOfServices.setMinWidth(250);
        secondNameColOfServices.setCellValueFactory(
                new PropertyValueFactory<>("treatment"));

        TableColumn thirdNameColOfServices = new TableColumn("Price");
        thirdNameColOfServices.setMinWidth(110);
        thirdNameColOfServices.setCellValueFactory(
                new PropertyValueFactory<>("price"));

        TableColumn forthNameColOfServices = new TableColumn("Id treatment");
        forthNameColOfServices.setMinWidth(110);
        forthNameColOfServices.setCellValueFactory(
                new PropertyValueFactory<>("idtreatment"));

        tableOfServices.getColumns().addAll(firstNameColOfServices,forthNameColOfServices,secondNameColOfServices,thirdNameColOfServices);
        tableOfServices.setItems(dataOfServices);
    }

    void createHolidayTableColumn(){

        TableColumn firstNameColOfHoliday = new TableColumn("Id holiday");
        firstNameColOfHoliday.setMinWidth(100);
        firstNameColOfHoliday.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn secondNameColOfHolida = new TableColumn("Employee Id");
        secondNameColOfHolida.setMinWidth(110);
        secondNameColOfHolida.setCellValueFactory(
                new PropertyValueFactory<>("employeeId"));

        TableColumn thirdNameColOfHolida = new TableColumn("Start Holiday");
        thirdNameColOfHolida.setMinWidth(110);
        thirdNameColOfHolida.setCellValueFactory(
                new PropertyValueFactory<>("startHoliday"));

        TableColumn fourthNameColOfHolida = new TableColumn("End Holiday");
        fourthNameColOfHolida.setMinWidth(100);
        fourthNameColOfHolida.setCellValueFactory(
                new PropertyValueFactory<>("endHoliday"));

        TableColumn fifthNameColOfHolida = new TableColumn("Status");
        fifthNameColOfHolida.setMinWidth(200);
        fifthNameColOfHolida.setCellValueFactory(
                new PropertyValueFactory<>("status"));

        tableOfHoliday.getColumns().addAll(firstNameColOfHoliday,secondNameColOfHolida,thirdNameColOfHolida,
                fourthNameColOfHolida,fifthNameColOfHolida);
    }
}
