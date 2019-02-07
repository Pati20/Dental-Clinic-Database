package UsersWindows;

import LogInPanel.LogInPanel;
import UsersWindows.DatabaseConnection.ReceptionistDatabaseConnection;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class ReceptionistGui extends TableData implements ForceGenerateGui {

    private final LogInPanel logInPanel;
    private final Stage mainWindow;
    private ReceptionistDatabaseConnection receptionistDatabaseConnection;
    private TextField fillName;
    private TextField fillSurname;
    private TextField fillnrPatient;
    private TextField fillnrDoctor;
    private TextField fillOffice;
    private TextField fillDate;
    private TextField fillPurposeOfVisit;
    private TextField fillPriority;
    private TextField fillStatus;

    public ReceptionistGui(LogInPanel logInPanel, Stage mainWindow) {

        this.logInPanel = logInPanel;
        this.mainWindow = mainWindow;
        receptionistDatabaseConnection = new ReceptionistDatabaseConnection(logInPanel.getUserId());
        createGui();
    }


    void correctOneFieldPatient(String chosenToCorrect, String newValue, String idPatient) {
        receptionistDatabaseConnection.correctOneFieldPatient(chosenToCorrect, newValue, idPatient);
        dataOfPatient.removeAll();
        dataOfPatient = receptionistDatabaseConnection.findPatient(fillName.getText(), fillSurname.getText(), null);
        tableOfPatient.setItems(dataOfPatient);
    }

    void correctMoreFieldPatient(String name, String surname, String pesel, String nrPhone,
                                 String dateOfBirth, String address, String id) {
        receptionistDatabaseConnection.correctMoreFieldPatient(name, surname, pesel, nrPhone, dateOfBirth, address, id);
        dataOfPatient.removeAll();
        dataOfPatient = receptionistDatabaseConnection.findPatient(fillName.getText(), fillSurname.getText(), null);
        tableOfPatient.setItems(dataOfPatient);
    }

    void  addNewPatient(String name, String surname, String pesel,
                       String nrPhone, String dateOfBirth, String address) {
        receptionistDatabaseConnection.addNewPatient(name, surname, pesel, nrPhone, dateOfBirth, address);
    }

    void correctOneFieldVisit(String chosenToCorrect, String newValue, String idVisit) {
        receptionistDatabaseConnection.correctOneFieldVisit(chosenToCorrect, newValue, idVisit);

        dataOfVisits.removeAll();
        dataOfVisits = receptionistDatabaseConnection.findPatientVisit(fillnrPatient.getText(), fillnrDoctor.getText(), fillOffice.getText(),
                fillDate.getText(), fillPurposeOfVisit.getText(), fillPriority.getText(), fillStatus.getText());
        tableOfVisits.setItems(dataOfVisits);
    }

    void correctMoreFieldVisit(String idDoctor,String idPatient, String office,String time, String date,
                               String purposeOfVisit, String priority, String status, String idVisit){

        receptionistDatabaseConnection.correctMoreFieldVisit(idDoctor,idPatient,office,time,date,purposeOfVisit,priority,status,idVisit);
        dataOfVisits.removeAll();
        dataOfVisits = receptionistDatabaseConnection.findPatientVisit(fillnrPatient.getText(), fillnrDoctor.getText(), fillOffice.getText(),
                fillDate.getText(), fillPurposeOfVisit.getText(), fillPriority.getText(), fillStatus.getText());
        tableOfVisits.setItems(dataOfVisits);

    }
    void removeVisit(String visitId){
        receptionistDatabaseConnection.removeVisit(visitId);
        dataOfVisits.removeAll();
        dataOfVisits = receptionistDatabaseConnection.findPatientVisit(fillnrPatient.getText(), fillnrDoctor.getText(), fillOffice.getText(),
                fillDate.getText(), fillPurposeOfVisit.getText(), fillPriority.getText(), fillStatus.getText());
        tableOfVisits.setItems(dataOfVisits);
    }

    void showTreatments(String id,String treatments){

        VBox mainWindowBox = new VBox(20);
        mainWindowBox.setPadding(new Insets(30));
        tableOfTreatments.setMaxHeight(250);
        tableOfTreatments.setMinHeight(250);
        dataOfTreatments = receptionistDatabaseConnection.createTreatmentsTable(id,treatments);
        tableOfTreatments.setItems(dataOfTreatments);

        mainWindowBox.getChildren().addAll(new Separator(),tableOfTreatments, new Separator());
        Stage stage = new Stage();
        Scene scene = new Scene(mainWindowBox, 800, 400);
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(950);
        stage.setMaxHeight(400);
        stage.setMaxWidth(950);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    void searchDuty(String id,String day,String start,String end){

        VBox mainWindowBox = new VBox(20);
        mainWindowBox.setPadding(new Insets(30));
        System.out.println("day "+day);
        tableOfficeHoursExtend.setMaxHeight(250);
        tableOfficeHoursExtend.setMinHeight(250);
        dataOfficeHoursExtend = receptionistDatabaseConnection.createDutyTable(id,day,start,end);
        tableOfficeHoursExtend.setItems(dataOfficeHoursExtend);

        mainWindowBox.getChildren().addAll(new Separator(), tableOfficeHoursExtend, new Separator());
        Stage stage = new Stage();
        Scene scene = new Scene(mainWindowBox, 800, 400);
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(950);
        stage.setMaxHeight(400);
        stage.setMaxWidth(950);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    void serchFreeTerm(String idDoctor, String dateVisit){

        VBox mainWindowBox = new VBox(20);
        mainWindowBox.setPadding(new Insets(30));

        HBox hBox = new HBox(5);

        TextField doctor = new TextField();
        TextField patient = new TextField();
        TextField office = new TextField();

        doctor.setPromptText("dr.id");
        patient.setPromptText("pat id");
        office.setPromptText("office ");

        int MIN_Width = 70;
        doctor.setMaxWidth(50);
        patient.setMaxWidth(50);
        office.setMaxWidth(MIN_Width);

        DatePicker datePicker = new DatePicker();
        datePicker.setMaxWidth(140);
        datePicker.setValue((LocalDate.now()));
        datePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            { datePicker.setPromptText(pattern.toLowerCase()); }

            @Override public String toString(LocalDate date) {
                if (date != null) { return dateFormatter.format(date); } else { return ""; } }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) { return LocalDate.parse(string, dateFormatter); } else { return null; } }
        });

        final Spinner<Integer> spinner = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(7, 19);
        spinner.setValueFactory(valueFactory);
        spinner.setPromptText("hh");
        spinner.setMaxWidth(60);


        final Spinner<Integer> spinner2 = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactory2 = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59,0,15);
        spinner2.setValueFactory(valueFactory2);
        spinner2.setMaxWidth(60);

        ChoiceBox choiceBoxOfTime = new ChoiceBox(FXCollections.observableArrayList( "30", "60", "90","120"));
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Select time of visit");
        choiceBoxOfTime.setTooltip(tooltip);
        choiceBoxOfTime.setValue("30");

        ChoiceBox choiceBoxOfPurpose = new ChoiceBox(FXCollections.observableArrayList("Przegląd",
                "Konsultacja z planem leczenia", "Znieczulenie miejscowe", "Wypełnienie zęba",
                "Leczenie próchnicy","Usuwanie kamienia","Piaskowanie","Wybielanie","Usuwanie zęba",
                "Założenie aparatu","Leczenie kanałowe","Instruktaż higieny jamy ustnej","Fluoryzacja",
                "Konsultacja chirurgiczna","Nacięcie i drenaż ropnia","Znieczulenie","Leczenie zębów mlecznych"));
        final Tooltip tooltip2 = new Tooltip();
        tooltip2.setText("Select purpose of visit");
        choiceBoxOfPurpose.setTooltip(tooltip2);
        choiceBoxOfPurpose.setValue("Przegląd");

        ChoiceBox choiceBoxOfPriority = new ChoiceBox(FXCollections.observableArrayList( "1", "2", "3"));
        final Tooltip tooltip3 = new Tooltip();
        tooltip3.setText("Select priority of visit");
        choiceBoxOfPriority.setTooltip(tooltip3);
        choiceBoxOfPriority.setValue("1");

        Button addVisit = new Button("Add");

        addVisit.setOnMouseClicked( e -> receptionistDatabaseConnection.addVisit(doctor.getText(),patient.getText(),office.getText(),datePicker.getValue().toString(),
                spinner.getValue(),spinner2.getValue(),choiceBoxOfTime.getValue().toString(),choiceBoxOfPurpose.getValue().toString(),choiceBoxOfPriority.getValue().toString()));

        hBox.getChildren().addAll(addVisit,doctor,patient,office,datePicker,spinner,spinner2,choiceBoxOfTime,choiceBoxOfPurpose,choiceBoxOfPriority);


        tableOfDoctorFreeDate.setMaxHeight(250);
        tableOfDoctorFreeDate.setMinHeight(250);
        dataOfDoctorFreeDate = receptionistDatabaseConnection.createFreeTermTable(idDoctor,dateVisit);
        tableOfDoctorFreeDate.setItems(dataOfDoctorFreeDate);

        mainWindowBox.getChildren().addAll(new Separator(),tableOfDoctorFreeDate, new Separator(),hBox);
        Stage stage = new Stage();
        Scene scene = new Scene(mainWindowBox, 800, 500);
        stage.setScene(scene);
        stage.setMinHeight(500);
        stage.setMinWidth(950);
        stage.setMaxHeight(500);
        stage.setMaxWidth(950);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

    }

    void showListOfDoctors(){

        VBox mainWindowBox = new VBox(20);
        mainWindowBox.setPadding(new Insets(30));

        tableOfDoctor.setMaxHeight(250);
        tableOfDoctor.setMinHeight(250);
        dataOffDoctor = receptionistDatabaseConnection.createDoctorTable();
        tableOfDoctor.setItems(dataOffDoctor);

        mainWindowBox.getChildren().addAll(new Separator(),tableOfDoctor, new Separator());
        Stage stage = new Stage();
        Scene scene = new Scene(mainWindowBox, 800, 400);
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(950);
        stage.setMaxHeight(400);
        stage.setMaxWidth(950);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    boolean findVisit(String idPatient, String idDoctor, String office, String date, String purposeOfVisit, String priority, String status) {

        if (idPatient.length() == 0 && idDoctor.length() == 0 && office.length() == 0 && date.length() == 0 && purposeOfVisit.length() == 0 &&
                priority.length() == 0 && status.length() == 0) {
            receptionistDatabaseConnection.showErrorMessage("No data was entered");
            return false;
        }

        VBox mainWindowBox = new VBox(20);
        mainWindowBox.setPadding(new Insets(30));

        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList("Doctor", "idPat.", "Office", "Date of the visit", "Time", "Purpose", "Priority", "Status"));
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Choose the item you want to improve.");
        choiceBox.setTooltip(tooltip);
        choiceBox.setValue("Office");


        TextField newValue = new TextField();
        TextField fillIdVisit = new TextField();
        TextField fillIdDoctor = new TextField("");
        TextField fillIdPatient = new TextField("");
        TextField fillOffice = new TextField("");
        TextField fillDate = new TextField("");
        TextField fillPuropse = new TextField("");
        TextField fillPriority = new TextField("");
        TextField fillStatus = new TextField("");
        TextField fillTime = new TextField("");
        TextField fillIdVisit2 = new TextField("");
        TextField fillIdVisit3 = new TextField("");

        newValue.setPromptText("new value");
        fillIdVisit.setPromptText("id visit");
        fillIdDoctor.setPromptText("id doctor");
        fillIdPatient.setPromptText("pat");
        fillOffice.setPromptText("office");
        fillDate.setPromptText("date");
        fillPuropse.setPromptText("purpose");
        fillPriority.setPromptText("priority");
        fillStatus.setPromptText("status");
        fillTime.setPromptText("time");
        fillIdVisit2.setPromptText("visit");
        fillIdVisit3.setPromptText("visit");

        int MAX_WIDTH = 85;

        fillIdDoctor.setMaxWidth(MAX_WIDTH);
        fillOffice.setMaxWidth(50);
        fillDate.setMaxWidth(MAX_WIDTH);
        fillPuropse.setMaxWidth(MAX_WIDTH);
        fillPriority.setMaxWidth(MAX_WIDTH);
        fillStatus.setMaxWidth(MAX_WIDTH);
        fillIdPatient.setMaxWidth(50);
        fillTime.setMaxWidth(50);
        fillIdVisit2.setMaxWidth(50);
        fillIdVisit3.setMaxWidth(50);

        tooltip.setText("Enter the date, format: \"YYYY-MM-DD \"");
        fillPriority.setTooltip(tooltip);

        Label idVisit = new Label("IdVisit");
        Label idVisit1 = new Label("IdVisit");


        Button correctOne = new Button("Correct");
        Button correctMore = new Button("Correct");
        Button removeVisit = new Button("Remove Visit");

        correctOne.setMinWidth(150);
        correctMore.setMinWidth(150);
        removeVisit.setMinWidth(150);

        correctOne.setOnMouseClicked(e -> correctOneFieldVisit((String) choiceBox.getValue(), newValue.getText(), fillIdVisit.getText()));
        correctMore.setOnMouseClicked(e -> correctMoreFieldVisit(fillIdDoctor.getText(),fillIdPatient.getText(),fillOffice.getText(), fillTime.getText(),
                fillDate.getText(),fillPuropse.getText(),fillPriority.getText(),fillStatus.getText(),fillIdVisit2.getText()));
        removeVisit.setOnMouseClicked(e -> removeVisit(fillIdVisit3.getText()));

        HBox hBox = new HBox(5);
        HBox hBox1 = new HBox(5);
        HBox hBox2 = new HBox(5);

        hBox.getChildren().addAll(correctOne, choiceBox, newValue, idVisit, fillIdVisit);
        hBox1.getChildren().addAll(correctMore, fillIdDoctor, fillIdPatient,fillOffice, fillDate,fillTime,fillPuropse, fillPriority, fillStatus, idVisit1, fillIdVisit2);
        hBox2.getChildren().addAll(removeVisit,fillIdVisit3);

        tableOfVisits.setMaxHeight(150);
        tableOfVisits.setMinHeight(150);
        firstVisitNameCol.setMinWidth(50);
        dataOfVisits = receptionistDatabaseConnection.findPatientVisit(idPatient, idDoctor, office, date, purposeOfVisit, priority, status);
        tableOfVisits.setItems(dataOfVisits);
        mainWindowBox.getChildren().addAll(tableOfVisits, new Separator(), hBox, hBox1,hBox2);
        Stage stage = new Stage();
        Scene scene = new Scene(mainWindowBox, 800, 400);
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(950);
        stage.setMaxHeight(400);
        stage.setMaxWidth(950);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        return true;
    }

    void findPatinetRecepcionist(String name, String surname) {

        VBox mainWindowBox = new VBox(20);
        mainWindowBox.setPadding(new Insets(30));

        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList("Name", "Surname", "Pesel", "nr.Phone", "Date of birth", "Adress"));
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Choose the item you want to improve.");
        choiceBox.setTooltip(tooltip);
        choiceBox.setValue("Pesel");


        TextField newValue = new TextField();
        TextField fillIdPatient = new TextField();

        TextField fillName = new TextField("");
        TextField fillSurname = new TextField("");
        TextField fillPesel = new TextField("");
        TextField fillnrPhone = new TextField("");
        TextField fillDateOfBirth = new TextField("");
        TextField fillAdress = new TextField("");
        TextField fillIdPatient2 = new TextField("");

        newValue.setPromptText("new value");
        fillIdPatient.setPromptText("id patient");
        fillName.setPromptText("name");
        fillSurname.setPromptText("surname");
        fillPesel.setPromptText("pesel");
        fillnrPhone.setPromptText("nr phone");
        fillDateOfBirth.setPromptText("date of birth");
        fillAdress.setPromptText("adress");
        fillIdPatient2.setPromptText("id patient");

        int MAX_WIDTH = 85;

        fillName.setMaxWidth(MAX_WIDTH);
        fillSurname.setMaxWidth(MAX_WIDTH);
        fillPesel.setMaxWidth(MAX_WIDTH);
        fillnrPhone.setMaxWidth(MAX_WIDTH);
        fillDateOfBirth.setMaxWidth(MAX_WIDTH);
        fillAdress.setMaxWidth(MAX_WIDTH);
        fillIdPatient2.setMaxWidth(MAX_WIDTH);

        tooltip.setText("Enter the date, format: \"YYYY-MM-DD \"");
        fillDateOfBirth.setTooltip(tooltip);

        Label idPatient = new Label("IdPatient");
        Label idPatient2 = new Label("IdPatient");


        Button correctOne = new Button("Correct");
        Button correctMore = new Button("Correct");
        correctOne.setMinWidth(150);
        correctMore.setMinWidth(150);
        correctOne.setOnMouseClicked(e -> correctOneFieldPatient((String) choiceBox.getValue(), newValue.getText(), fillIdPatient.getText()));
        correctMore.setOnMouseClicked(e -> correctMoreFieldPatient(fillName.getText(), fillSurname.getText(), fillPesel.getText(),
                fillnrPhone.getText(), fillDateOfBirth.getText(), fillAdress.getText(), fillIdPatient2.getText()));
        HBox hBox = new HBox(5);
        HBox hBox1 = new HBox(5);

        hBox.getChildren().addAll(correctOne, choiceBox, newValue, idPatient, fillIdPatient);
        hBox1.getChildren().addAll(correctMore, fillName, fillSurname, fillPesel, fillnrPhone, fillDateOfBirth, fillAdress, idPatient2, fillIdPatient2);

        tableOfPatient.setMaxHeight(150);
        tableOfPatient.setMinHeight(150);
        dataOfPatient = receptionistDatabaseConnection.findPatient(name, surname, null);
        tableOfPatient.setItems(dataOfPatient);
        mainWindowBox.getChildren().addAll(tableOfPatient, new Separator(), hBox, hBox1);
        Stage stage = new Stage();
        Scene scene = new Scene(mainWindowBox, 800, 400);
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(950);
        stage.setMaxHeight(400);
        stage.setMaxWidth(950);
        if(name.length() !=  0 || surname.length() != 0)
            stage.show();

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }


    /**
     * recepcjonistka@2443
     * rehcept123
     */

    @Override
    public void createGui() {
        mainWindow.setTitle("Receptionist Panel ");

        HBox mainWindowBox = new HBox(30);
        mainWindowBox.setPadding(new Insets(30));

        int MIN_WIDTH = 85;

        VBox patientvBox = new VBox(10); //-poziomo
        VBox visitvBox = new VBox(10); //-poziomo
        VBox doctorvBox = new VBox(10); //-poziomo

        Label patient = new Label("Patient");
        Label searchLeabel = new Label("Search for the patient in the database.");
        Label patientName = new Label("Name");
        Label patientSurname = new Label("Surname");

        searchLeabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));
        patient.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));

        patientSurname.setMinWidth(MIN_WIDTH);
        patient.setMinWidth(MIN_WIDTH);
        patientName.setMinWidth(MIN_WIDTH);

        fillName = new TextField("");
        fillSurname = new TextField("");

        fillSurname.setMinWidth(MIN_WIDTH);
        fillName.setMinWidth(MIN_WIDTH);

        Button patientButton = new Button("OK");
        patientButton.setMinWidth(MIN_WIDTH);
        patientButton.setOnMouseClicked(e -> findPatinetRecepcionist(fillName.getText(), fillSurname.getText()));

        HBox temp1 = new HBox(5);
        temp1.getChildren().addAll(patientName, fillName);

        HBox temp2 = new HBox(5);
        temp2.getChildren().addAll(patientSurname, fillSurname);

        Label addNewPatient = new Label("Add new patient");
        addNewPatient.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));

        Label name = new Label("Name");
        Label surname = new Label("Surname");
        Label pesel = new Label("Pesel");
        Label nrPhone = new Label("nr. Phone");
        Label dateOdBirth = new Label("Date of Birth");
        Label address = new Label("Address");

        name.setMinWidth(MIN_WIDTH);
        surname.setMinWidth(MIN_WIDTH);
        pesel.setMinWidth(MIN_WIDTH);
        nrPhone.setMinWidth(MIN_WIDTH);
        dateOdBirth.setMinWidth(MIN_WIDTH);
        address.setMinWidth(MIN_WIDTH);

        TextField fillNamePatient = new TextField("");
        TextField fillSurnamePatient = new TextField("");
        TextField fillPeselPatient = new TextField("");
        TextField fillNrPfonePatient = new TextField("");
        TextField fillDateOfBirthPatient = new TextField("");
        TextField fillAddressPatient = new TextField("");

        fillNamePatient.setMinWidth(MIN_WIDTH);
        fillSurnamePatient.setMinWidth(MIN_WIDTH);
        fillPeselPatient.setMinWidth(MIN_WIDTH);
        fillNrPfonePatient.setMinWidth(MIN_WIDTH);
        fillDateOfBirthPatient.setMinWidth(MIN_WIDTH);
        fillAddressPatient.setMinWidth(MIN_WIDTH);

        HBox temporary1 = new HBox(5);
        HBox temporary2 = new HBox(5);
        HBox temporary3 = new HBox(5);
        HBox temporary4 = new HBox(5);
        HBox temporary5 = new HBox(5);
        HBox temporary6 = new HBox(5);

        temporary1.getChildren().addAll(name, fillNamePatient);
        temporary2.getChildren().addAll(surname, fillSurnamePatient);
        temporary3.getChildren().addAll(pesel, fillPeselPatient);
        temporary4.getChildren().addAll(nrPhone, fillNrPfonePatient);
        temporary5.getChildren().addAll(dateOdBirth, fillDateOfBirthPatient);
        temporary6.getChildren().addAll(address, fillAddressPatient);

        Button addPatient = new Button("Add");
        addPatient.setMinWidth(80);
        addPatient.setOnMouseClicked(e -> addNewPatient(fillNamePatient.getText(), fillSurnamePatient.getText(), fillPeselPatient.getText(),
                fillNrPfonePatient.getText(), fillDateOfBirthPatient.getText(), fillAddressPatient.getText()));

        patientvBox.getChildren().addAll(patient, new Separator(), searchLeabel, temp1, temp2, patientButton, new Separator()
                , addNewPatient, temporary1, temporary2, temporary3, temporary4, temporary5, temporary6, addPatient);

        //Creating second column

        Label visit = new Label("Visit");
        Label searchVisit = new Label("Search for a visit");
        Label addNewVisit = new Label("Add a new visit");
        Label nrPatient = new Label("nr. Patient");
        Label nrDoctor = new Label("nr. Doctor");
        Label doctorNr = new Label("nr. Doctor");
        Label office = new Label("Office");
        Label date = new Label("Date");
        Label purposeOfVisit = new Label("Purpose");
        Label priority = new Label("Proority");
        Label status = new Label("Status");
        Label dateOfVisit = new Label("Date of visit");

        visit.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
        searchVisit.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));
        addNewVisit.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));

        nrPatient.setMinWidth(MIN_WIDTH);
        nrDoctor.setMinWidth(MIN_WIDTH);
        doctorNr.setMinWidth(MIN_WIDTH);
        office.setMinWidth(MIN_WIDTH);
        date.setMinWidth(MIN_WIDTH);
        purposeOfVisit.setMinWidth(MIN_WIDTH);
        priority.setMinWidth(MIN_WIDTH);
        status.setMinWidth(MIN_WIDTH);
        dateOfVisit.setMinWidth(MIN_WIDTH);

        fillnrPatient = new TextField("");
        fillnrDoctor = new TextField("");
        fillOffice = new TextField("");
        fillDate = new TextField("");
        fillPurposeOfVisit = new TextField("");
        fillPriority = new TextField("");
        fillStatus = new TextField("");

        TextField fillDoctorNr = new TextField("");

        Tooltip tooltip = new Tooltip();
        tooltip.setText("Enter the date, format: \"YYYY-MM-DD \"");
        fillDate.setTooltip(tooltip);

        fillnrPatient.setMinWidth(MIN_WIDTH);
        fillnrDoctor.setMinWidth(MIN_WIDTH);
        fillOffice.setMinWidth(MIN_WIDTH);
        fillDate.setMinWidth(MIN_WIDTH);
        fillPurposeOfVisit.setMinWidth(MIN_WIDTH);
        fillPriority.setMinWidth(MIN_WIDTH);
        fillStatus.setMinWidth(MIN_WIDTH);
        fillDoctorNr.setMinWidth(MIN_WIDTH);

        DatePicker datePicker = new DatePicker();
        datePicker.setMinWidth(MIN_WIDTH);
        datePicker.setValue((LocalDate.now()));
        datePicker.isShowWeekNumbers();
        datePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                datePicker.setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        Button searchVisitButton = new Button("Search");
        Button serchFreeTerm = new Button("Search Free Date");

        createVisitTableColumn();
        searchVisitButton.setOnMouseClicked(e -> findVisit(fillnrPatient.getText(), fillnrDoctor.getText(), fillOffice.getText(),
                fillDate.getText(), fillPurposeOfVisit.getText(), fillPriority.getText(), fillStatus.getText()));
        serchFreeTerm.setOnMouseClicked(e -> serchFreeTerm(fillDoctorNr.getText(),datePicker.getValue().toString()));

        serchFreeTerm.setMinWidth(MIN_WIDTH);
        searchVisitButton.setMinWidth(MIN_WIDTH);

        HBox visitTemp1 = new HBox(5);
        HBox visitTemp2 = new HBox(5);
        HBox visitTemp3 = new HBox(5);
        HBox visitTemp4 = new HBox(5);
        HBox visitTemp5 = new HBox(5);
        HBox visitTemp6 = new HBox(5);
        HBox visitTemp7 = new HBox(5);
        HBox visitTemp8 = new HBox(5);
        HBox visitTemp9 = new HBox(5);

        visitTemp1.getChildren().addAll(nrPatient, fillnrPatient);
        visitTemp2.getChildren().addAll(nrDoctor, fillnrDoctor);
        visitTemp3.getChildren().addAll(office, fillOffice);
        visitTemp4.getChildren().addAll(date, fillDate);
        visitTemp5.getChildren().addAll(purposeOfVisit, fillPurposeOfVisit);
        visitTemp6.getChildren().addAll(priority, fillPriority);
        visitTemp7.getChildren().addAll(status, fillStatus);
        visitTemp8.getChildren().addAll(doctorNr, fillDoctorNr);
        visitTemp9.getChildren().addAll(dateOfVisit, datePicker);

        visitvBox.getChildren().addAll(visit, new Separator(), searchVisit, visitTemp1, visitTemp2, visitTemp3,
                visitTemp4, visitTemp5, visitTemp6, visitTemp7, searchVisitButton, new Separator(), addNewVisit,
                visitTemp8, visitTemp9, serchFreeTerm);

        //Creating third column

        Label doctor = new Label("Doctor");
        Label chceckDuty = new Label("Check duty");
        Label doctorId = new Label("nr. Doctor");
        Label doctorData = new Label("Day");
        Label doctorStart = new Label("Start hour");
        Label doctorEnd = new Label("End hour");
        Label performedTreatments = new Label("Performed treatments");
        Label doctorIdx2 = new Label("nr. Doctor");
        Label treatments = new Label("Treatments");


        doctor.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
        chceckDuty.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));
        performedTreatments.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));

        doctorId.setMinWidth(MIN_WIDTH);
        doctorData.setMinWidth(MIN_WIDTH);
        doctorStart.setMinWidth(MIN_WIDTH);
        doctorEnd.setMinWidth(MIN_WIDTH);
        doctorIdx2.setMinWidth(MIN_WIDTH);
        treatments.setMinWidth(MIN_WIDTH);


        TextField filldoctorID = new TextField();
        TextField filldoctorIDx2 = new TextField();
        TextField filldoctorStart = new TextField();
        TextField filldoctorEnd = new TextField();
        ChoiceBox choiceBoxOfTreatment = new ChoiceBox(FXCollections.observableArrayList("Przegląd",
                "Konsultacja z planem leczenia", "Znieczulenie miejscowe", "Wypełnienie zęba",
                "Leczenie próchnicy","Usuwanie kamienia","Piaskowanie","Wybielanie","Usuwanie zęba",
                "Założenie aparatu","Leczenie kanałowe","Instruktaż higieny jamy ustnej","Fluoryzacja",
                "Konsultacja chirurgiczna","Nacięcie i drenaż ropnia","Znieczulenie","Leczenie zębów mlecznych",""));
        createTableOfOfficeHoursExtend(0);

        final Tooltip tooltip3 = new Tooltip();
        tooltip3.setText("Select treatments");
        choiceBoxOfTreatment.setTooltip(tooltip3);
        choiceBoxOfTreatment.setValue("Przegląd");
        choiceBoxOfTreatment.setMaxWidth(MIN_WIDTH+90);

        ChoiceBox choiceBoxOfDay = new ChoiceBox(FXCollections.observableArrayList("Monday","Tuesday","Wednesday","Thursday", "Friday",""));
        final Tooltip tooltip2 = new Tooltip();
        tooltip2.setText("Select day of duty");
        choiceBoxOfDay.setTooltip(tooltip2);
        choiceBoxOfDay.setValue("Monday");



        filldoctorID.setMinWidth(MIN_WIDTH);
        filldoctorIDx2.setMinWidth(MIN_WIDTH);
        choiceBoxOfDay.setMinWidth(MIN_WIDTH+90);
        filldoctorStart.setMinWidth(MIN_WIDTH);
        filldoctorEnd.setMinWidth(MIN_WIDTH);
        choiceBoxOfTreatment.setMinWidth(MIN_WIDTH);

        Button showListOfDoctor = new Button("Show list of doctors");
        Button showDuty = new Button("Show");           // <------------------------------------------DODAĆ IMPLEMENTACJE
        Button showTreatments = new Button("Show");     // <------------------------------------------DODAĆ IMPLEMENTACJE

        showListOfDoctor.setMinWidth(100);
        showDuty.setMinWidth(MIN_WIDTH);
        showTreatments.setMinWidth(MIN_WIDTH);

        showListOfDoctor.setOnMouseClicked(e -> showListOfDoctors());
        showDuty.setOnMouseClicked(e -> searchDuty(filldoctorID.getText(),choiceBoxOfDay.getValue().toString(),filldoctorStart.getText(),filldoctorEnd.getText()));
        showTreatments.setOnMouseClicked(e -> showTreatments(filldoctorIDx2.getText(),choiceBoxOfTreatment.getValue().toString()));

        HBox doctorTmp1 = new HBox(5);
        HBox doctorTmp2 = new HBox(5);
        HBox doctorTmp3 = new HBox(5);
        HBox doctorTmp4 = new HBox(5);
        HBox doctorTmp5 = new HBox(5);
        HBox doctorTmp6 = new HBox(5);

        doctorTmp1.getChildren().addAll(doctorId, filldoctorID);
        doctorTmp2.getChildren().addAll(doctorData, choiceBoxOfDay);
        doctorTmp3.getChildren().addAll(doctorStart, filldoctorStart);
        doctorTmp4.getChildren().addAll(doctorEnd, filldoctorEnd);
        doctorTmp5.getChildren().addAll(doctorIdx2, filldoctorIDx2);
        doctorTmp6.getChildren().addAll(treatments, choiceBoxOfTreatment);


        doctorvBox.getChildren().addAll(doctor, new Separator(), showListOfDoctor, chceckDuty, doctorTmp1, doctorTmp2, doctorTmp3, doctorTmp4
                , showDuty, new Separator(), performedTreatments, doctorTmp5, doctorTmp6, showTreatments);

        mainWindowBox.getChildren().addAll(patientvBox, visitvBox, doctorvBox);
        Scene scene = new Scene(mainWindowBox, 800, 500);
        mainWindow.setScene(scene);
        mainWindow.setMinHeight(650);
        mainWindow.setMinWidth(950);
        mainWindow.setMaxHeight(650);
        mainWindow.setMaxWidth(950);
        mainWindow.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        mainWindow.setX((primScreenBounds.getWidth() - mainWindow.getWidth()) / 2);
        mainWindow.setY((primScreenBounds.getHeight() - mainWindow.getHeight()) / 2);
    }
}
