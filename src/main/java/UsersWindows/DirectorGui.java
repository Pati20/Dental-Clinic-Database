package UsersWindows;


import LogInPanel.LogInPanel;
import UsersWindows.DatabaseConnection.AdministratorDatabaseConnection;
import UsersWindows.DatabaseConnection.DirectorDatabaseConnection;
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
import java.util.logging.Level;

public class DirectorGui extends TableData implements ForceGenerateGui {

    private DirectorDatabaseConnection connection;
    private final LogInPanel logInPanel;
    private final Stage mainWindow;
    private TextField fillName;
    private TextField fillSurname;
    private TextField fillnrPatient;
    private TextField fillnrDoctor;
    private TextField fillOffice;
    private TextField fillPurposeOfVisit;
    private DatePicker datePicker;
    private ComboBox priorityCombo;
    private ComboBox statusCombo;
    private CheckBox dayCheckBox;
    private CheckBox weekCheckBox;
    private CheckBox monthCheckBox;
    private CheckBox yearCheckBox;

    public DirectorGui(LogInPanel logInPanel, Stage mainWindow) {
        this.mainWindow = mainWindow;
        this.logInPanel = logInPanel;
        connection = new DirectorDatabaseConnection(logInPanel.getUserId());
        createGui();
    }

    void unSelectOther(CheckBox name) {
        CheckBox[] tmp = {dayCheckBox,weekCheckBox,monthCheckBox,yearCheckBox};
        for (int i = 0; i < tmp.length ; i++){
            if (tmp[i] == name) continue;
            tmp[i].setSelected(false);
        }
    }

    void correctOneFieldVisit(String chosenToCorrect, String newValue, String idVisit) {
        connection.correctOneFieldVisit(chosenToCorrect, newValue, idVisit);

        dataOfVisits.removeAll();
        dataOfVisits = connection.findPatientVisit(fillnrPatient.getText(), fillnrDoctor.getText(), fillOffice.getText(),
                datePicker.getValue().toString(), fillPurposeOfVisit.getText(), priorityCombo.getValue().toString(), statusCombo.getValue().toString());
        tableOfVisits.setItems(dataOfVisits);
    }

    void correctMoreFieldVisit(String idDoctor, String idPatient, String office, String time, String date,
                               String purposeOfVisit, String priority, String status, String idVisit) {

        connection.correctMoreFieldVisit(idDoctor, idPatient, office, time, date, purposeOfVisit, priority, status, idVisit);
        dataOfVisits.removeAll();
        dataOfVisits = connection.findPatientVisit(fillnrPatient.getText(), fillnrDoctor.getText(), fillOffice.getText(),
                datePicker.getValue().toString(), fillPurposeOfVisit.getText(), priorityCombo.getValue().toString(), statusCombo.getValue().toString());
        tableOfVisits.setItems(dataOfVisits);

    }

    void removeVisit(String visitId) {
        connection.removeVisit(visitId);
        dataOfVisits.removeAll();
        dataOfVisits = connection.findPatientVisit(fillnrPatient.getText(), fillnrDoctor.getText(), fillOffice.getText(),
                datePicker.getValue().toString(), fillPurposeOfVisit.getText(), priorityCombo.getValue().toString(), statusCombo.getValue().toString());
        tableOfVisits.setItems(dataOfVisits);
    }

    boolean findVisit(String idPatient, String idDoctor, String office, String date, String purposeOfVisit, String priority, String status) {

        if (idPatient.length() == 0 && idDoctor.length() == 0 && office.length() == 0 && date.length() == 0 && purposeOfVisit.length() == 0 &&
                priority.length() == 0 && status.length() == 0) {
            connection.showErrorMessage("No data was entered");
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
        correctMore.setOnMouseClicked(e -> correctMoreFieldVisit(fillIdDoctor.getText(), fillIdPatient.getText(), fillOffice.getText(), fillTime.getText(),
                fillDate.getText(), fillPuropse.getText(), fillPriority.getText(), fillStatus.getText(), fillIdVisit2.getText()));
        removeVisit.setOnMouseClicked(e -> removeVisit(fillIdVisit3.getText()));

        HBox hBox = new HBox(5);
        HBox hBox1 = new HBox(5);
        HBox hBox2 = new HBox(5);

        hBox.getChildren().addAll(correctOne, choiceBox, newValue, idVisit, fillIdVisit);
        hBox1.getChildren().addAll(correctMore, fillIdDoctor, fillIdPatient, fillOffice, fillDate, fillTime, fillPuropse, fillPriority, fillStatus, idVisit1, fillIdVisit2);
        hBox2.getChildren().addAll(removeVisit, fillIdVisit3);

        tableOfVisits.setMaxHeight(150);
        tableOfVisits.setMinHeight(150);
        firstVisitNameCol.setMinWidth(50);
        dataOfVisits = connection.findPatientVisit(idPatient, idDoctor, office, date, purposeOfVisit, priority, status);
        tableOfVisits.setItems(dataOfVisits);
        mainWindowBox.getChildren().addAll(tableOfVisits, new Separator(), hBox, hBox1, hBox2);
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

    void correctOneFieldPatient(String chosenToCorrect, String newValue, String idPatient) {
        connection.correctOneFieldPatient(chosenToCorrect, newValue, idPatient);
        dataOfPatient.removeAll();
        dataOfPatient = connection.findPatient(fillName.getText(), fillSurname.getText(), null);
        tableOfPatient.setItems(dataOfPatient);
    }

    void correctOneFieldEmployee(String chosenToCorrect, String newValue, String idPatient) {
        if (connection.correctOneFieldEmployee(chosenToCorrect, newValue, idPatient)) {
            dataOfEmployee.removeAll();
            dataOfEmployee = connection.createEmployeeTable("", "", "", "");
            tableOfEmployee.setItems(dataOfEmployee);
        }

    }

    void correctMoreFieldPatient(String name, String surname, String pesel, String nrPhone,
                                 String dateOfBirth, String address, String id) {
        connection.correctMoreFieldPatient(name, surname, pesel, nrPhone, dateOfBirth, address, id);
        dataOfPatient.removeAll();
        dataOfPatient = connection.findPatient(fillName.getText(), fillSurname.getText(), null);
        tableOfPatient.setItems(dataOfPatient);
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
        dataOfPatient = connection.findPatient(name, surname, null);
        tableOfPatient.setItems(dataOfPatient);
        mainWindowBox.getChildren().addAll(tableOfPatient, new Separator(), hBox, hBox1);
        Stage stage = new Stage();
        Scene scene = new Scene(mainWindowBox, 800, 400);
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(950);
        stage.setMaxHeight(400);
        stage.setMaxWidth(950);
        // if(name.length() !=  0 || surname.length() != 0)
        stage.show();

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    void createLogGui() {
        VBox mainWindowBox = new VBox(20);
        mainWindowBox.setPadding(new Insets(30));

        tableOfLog.setMaxHeight(250);
        tableOfLog.setMinHeight(250);
        dataOfLog = connection.createLogTable();
        tableOfLog.setItems(dataOfLog);

        mainWindowBox.getChildren().addAll(new Separator(), tableOfLog, new Separator());
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

    void searchEmployee(String id, String name, String surname, String postion) {
        //delete EMPLOYEEE
        VBox mainWindowBox = new VBox(20);
        mainWindowBox.setPadding(new Insets(30));


        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList("Name", "Surname", "Pesel", "nr.Phone", "Date of birth", "Adress"));
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Choose the item you want to improve.");
        choiceBox.setTooltip(tooltip);
        choiceBox.setValue("Pesel");

        TextField newValue = new TextField();
        TextField fillIdPatient = new TextField();
        TextField fillIdPatient2 = new TextField();

        newValue.setPromptText("new value");
        fillIdPatient.setPromptText("id employee");

        Label idPatient = new Label("Id employee");
        Label idPatient2 = new Label("Id employee");
        Button delete = new Button("Delete Employee");
        delete.setMinWidth(150);
        delete.setOnMouseClicked(e -> connection.removeEmployee(fillIdPatient2.getText()));

        Button correctOne = new Button("Correct");
        correctOne.setMinWidth(150);
        correctOne.setOnMouseClicked(e -> correctOneFieldEmployee((String) choiceBox.getValue(), newValue.getText(), fillIdPatient.getText()));

        HBox hBox = new HBox(10);
        HBox hBox1 = new HBox(10);

        hBox.getChildren().addAll(correctOne, choiceBox, newValue, idPatient, fillIdPatient);
        hBox1.getChildren().addAll(delete, idPatient2, fillIdPatient2);

        tableOfEmployee.setMaxHeight(250);
        tableOfEmployee.setMinHeight(250);
        tableOfEmployee.setMinWidth(600);
        dataOfEmployee = connection.createEmployeeTable(id, name, surname, postion);
        tableOfEmployee.setItems(dataOfEmployee);

        mainWindowBox.getChildren().addAll(new Separator(), tableOfEmployee, new Separator(), hBox, hBox1);
        Stage stage = new Stage();
        Scene scene = new Scene(mainWindowBox, 800, 400);
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

    void correctOneFieldServices(String treatment, String newValue, String idExecutor, String idServices) {
        if (connection.correctOneFieldServices(treatment, newValue, idExecutor, idServices)) {
            dataOfServices.removeAll();
            dataOfServices = connection.createServicesTable("", "", "");
            tableOfServices.setItems(dataOfServices);
        }
    }

    void correctDuty(String comboValue, String newValue, String id) {
        if (connection.correctDuty(comboValue, newValue, id)) {
            dataOfficeHoursExtend.removeAll();
            dataOfficeHoursExtend = connection.createDutyTable("", "", "", "");
            tableOfficeHoursExtend.setItems(dataOfficeHoursExtend);
        }
    }


    void searchDuty(String id, String day, String start, String end) {

        VBox mainWindowBox = new VBox(20);
        mainWindowBox.setPadding(new Insets(30));

        HBox first = new HBox(5);
        HBox second = new HBox(5);
        HBox third = new HBox(5);

        ChoiceBox choiceBoxOfTreatment = new ChoiceBox(FXCollections.observableArrayList("ID Doctor", "Day of week", "Start", "End", ""));
        Label idOfficeHour = new Label("Id office Hour");

        TextField idOffice = new TextField("");
        TextField newValue = new TextField("");
        newValue.setPromptText("new value");
        Button crrectOfficeHour = new Button("Correct");
        crrectOfficeHour.setMinWidth(100);
        crrectOfficeHour.setOnMouseClicked(e -> correctDuty(choiceBoxOfTreatment.getValue().toString(), newValue.getText(), idOffice.getText()));

        first.getChildren().addAll(crrectOfficeHour, choiceBoxOfTreatment, newValue, idOfficeHour, idOffice);


        TextField fillidDoctor = new TextField();
        TextField fillDay = new TextField();
        TextField fillStart = new TextField();
        TextField fillEnd = new TextField();

        fillidDoctor.setPromptText("Id dr.");
        fillDay.setPromptText("DAY");
        fillStart.setPromptText("START");
        fillEnd.setPromptText("END");

        fillidDoctor.setMinWidth(80);
        fillDay.setMinWidth(80);
        fillStart.setMinWidth(80);
        fillEnd.setMinWidth(80);

        Button addOfficeHour = new Button("Add");
        addOfficeHour.setMinWidth(100);
        addOfficeHour.setOnMouseClicked(c -> connection.addDuty(fillidDoctor.getText(), fillDay.getText(), fillStart.getText(), fillEnd.getText()));

        second.getChildren().addAll(addOfficeHour, fillidDoctor, fillDay, fillStart, fillEnd);

        Label officeHourId = new Label("Id office hour");
        TextField fillofficeHourId = new TextField();

        Button deleteDuty = new Button("Delete");
        deleteDuty.setMinWidth(100);
        deleteDuty.setOnMouseClicked(e -> connection.deleteDuty(fillofficeHourId.getText()));

        third.getChildren().addAll(deleteDuty, officeHourId, fillofficeHourId);

        tableOfficeHoursExtend.setMaxHeight(250);
        tableOfficeHoursExtend.setMinHeight(250);
        dataOfficeHoursExtend = connection.createDutyTable(id, day, start, end);
        tableOfficeHoursExtend.setItems(dataOfficeHoursExtend);

        mainWindowBox.getChildren().addAll(new Separator(), tableOfficeHoursExtend, new Separator(), first, second, third);
        Stage stage = new Stage();

        int height = 600;
        Scene scene = new Scene(mainWindowBox, 800, height);
        stage.setScene(scene);
        stage.setMinHeight(height);
        stage.setMinWidth(950);
        stage.setMaxHeight(height);
        stage.setMaxWidth(950);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    void createServices(String executor, String price, String service) {

        //delete EMPLOYEEE
        VBox mainWindowBox = new VBox(20);
        mainWindowBox.setPadding(new Insets(30));


        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList("Price", "Executor", "Id treatment"));
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Choose the item you want to improve.");
        choiceBox.setTooltip(tooltip);
        choiceBox.setValue("Price");

        TextField newValue = new TextField();
        TextField fillIdExecutor = new TextField();
        TextField fillIdExecutor2 = new TextField();
        TextField fillIdServices = new TextField();
        TextField fillIdServices2 = new TextField();

        fillIdServices.setMaxWidth(60);
        fillIdServices2.setMaxWidth(60);

        newValue.setPromptText("new value");
        fillIdExecutor.setPromptText("id employee");

        Label idExector = new Label("Id executor");
        Label idExecutor2 = new Label("Id executor");
        Label IdServices = new Label("Id services");
        Label IdServices2 = new Label("Id services");
        Button delete = new Button("Delete service");
        delete.setMinWidth(150);
        delete.setOnMouseClicked(e -> connection.removeServices(fillIdExecutor2.getText(), fillIdServices.getText()));

        Button correctOne = new Button("Correct");
        correctOne.setMinWidth(150);
        correctOne.setOnMouseClicked(e -> correctOneFieldServices((String) choiceBox.getValue(), newValue.getText(), fillIdExecutor.getText(), fillIdServices2.getText()));

        HBox hBox = new HBox(10);
        HBox hBox1 = new HBox(10);

        hBox.getChildren().addAll(correctOne, choiceBox, newValue, idExector, fillIdExecutor, IdServices2, fillIdServices2);
        hBox1.getChildren().addAll(delete, idExecutor2, fillIdExecutor2, IdServices, fillIdServices);

        tableOfServices.setMaxHeight(250);
        tableOfServices.setMinHeight(250);
        tableOfServices.setMinWidth(600);
        dataOfServices = connection.createServicesTable(executor, price, service);
        tableOfServices.setItems(dataOfServices);

        mainWindowBox.getChildren().addAll(new Separator(), tableOfServices, new Separator(), hBox, hBox1);
        Stage stage = new Stage();
        Scene scene = new Scene(mainWindowBox, 800, 400);
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

    void createTableGuiHoliday() {

        System.out.println("I'm here");
        VBox mainWindowBox = new VBox(20);
        mainWindowBox.setPadding(new Insets(30));

        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList("przyjęty", "odrzucony",
                "oczekujący na decyzję"));
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Choose the item you want to improve.");
        choiceBox.setTooltip(tooltip);
        choiceBox.setValue("Oczekujący na decyzję");

        TextField fillIdHoliday = new TextField();
        fillIdHoliday.setMaxWidth(80);
        fillIdHoliday.setPromptText("id holiday");
        fillIdHoliday.setMinWidth(choiceBox.getWidth());

        Label titleLabel = new Label("Change of holiday status");
        titleLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
        Label idHoliday = new Label("Id holiday");
        idHoliday.setMinWidth(80);

        Label status = new Label("Status");
        status.setMinWidth(80);

        Button confirm_changes = new Button("Confirm changes");
        confirm_changes.setOnMouseClicked(e -> connection.changeStatusHoliday(fillIdHoliday.getText(), choiceBox.getValue().toString()));

        HBox hBox = new HBox(10);
        HBox hBox1 = new HBox(10);
        HBox hBox2 = new HBox(10);

        VBox temp = new VBox(5);
        temp.getChildren().addAll(hBox, hBox1, hBox2);

        hBox.getChildren().addAll(idHoliday, fillIdHoliday);
        hBox1.getChildren().addAll(status, choiceBox);
        hBox2.getChildren().addAll(confirm_changes);
        tableOfHoliday.setMaxHeight(250);
        tableOfHoliday.setMinHeight(250);
        tableOfHoliday.setMinWidth(600);
        dataOfHoliday = connection.createHolidayTable();
        tableOfHoliday.setItems(dataOfHoliday);

        mainWindowBox.getChildren().addAll(new Separator(), tableOfHoliday, new Separator(), titleLabel, temp);
        Stage stage = new Stage();
        Scene scene = new Scene(mainWindowBox, 800, 400);
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(950);
        stage.setMaxHeight(600);
        stage.setMaxWidth(950);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    @Override   //DOPISAĆ IMPLEMENTACJE
    public void createGui() {

        mainWindow.setTitle("Director");


        HBox mainWindowBox = new HBox(30);
        mainWindowBox.setPadding(new Insets(30));

        int MIN_WIDTH = 85;

        VBox patientvBox = new VBox(10);

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

        patientvBox.getChildren().addAll(patient, new Separator(), searchLeabel, temp1, temp2, patientButton);

        //Creating second column

        Label visit = new Label("Visit");
        Label searchVisit = new Label("Search for a visit");
        Label nrPatient = new Label("nr. Patient");
        Label nrDoctor = new Label("nr. Doctor");
        Label office = new Label("Office");
        Label date = new Label("Date");
        Label purposeOfVisit = new Label("Purpose");
        Label priority = new Label("Proority");
        Label status = new Label("Status");


        visit.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
        searchVisit.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));

        nrPatient.setMinWidth(MIN_WIDTH);
        nrDoctor.setMinWidth(MIN_WIDTH);
        office.setMinWidth(MIN_WIDTH);
        date.setMinWidth(MIN_WIDTH);
        purposeOfVisit.setMinWidth(MIN_WIDTH);
        priority.setMinWidth(MIN_WIDTH);
        status.setMinWidth(MIN_WIDTH);


        fillnrPatient = new TextField("");
        fillnrDoctor = new TextField("");
        fillOffice = new TextField("");
        fillPurposeOfVisit = new TextField("");

        priorityCombo = new ComboBox();
        priorityCombo.getItems().addAll("1", "2", "3");
        priorityCombo.setValue("1");
        priorityCombo.setMinWidth(MIN_WIDTH + 90);

        statusCombo = new ComboBox();
        statusCombo.getItems().addAll("do przełożenia", "oczekująca", "zakończona");
        statusCombo.setValue("oczekująca");
        statusCombo.setMinWidth(MIN_WIDTH + 90);

        TextField fillDoctorNr = new TextField("");

        Tooltip tooltip = new Tooltip();
        tooltip.setText("Enter the date, format: \"YYYY-MM-DD \"");

        fillnrPatient.setMinWidth(MIN_WIDTH);
        fillnrDoctor.setMinWidth(MIN_WIDTH);
        fillOffice.setMinWidth(MIN_WIDTH);
        fillPurposeOfVisit.setMinWidth(MIN_WIDTH);
        fillDoctorNr.setMinWidth(MIN_WIDTH);

        datePicker = new DatePicker();
        datePicker.setMinWidth(MIN_WIDTH);
        datePicker.setValue((LocalDate.now()));
        datePicker.isShowWeekNumbers();
        datePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                datePicker.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        Button searchVisitButton = new Button("Search");//<========================================
        searchVisitButton.setMinWidth(MIN_WIDTH);

        createVisitTableColumn();
        searchVisitButton.setOnMouseClicked(e -> findVisit(fillnrPatient.getText(), fillnrDoctor.getText(), fillOffice.getText(),
                datePicker.getValue().toString(), fillPurposeOfVisit.getText(), priorityCombo.getValue().toString(), priorityCombo.getValue().toString()));

        HBox visitTemp1 = new HBox(5);
        HBox visitTemp2 = new HBox(5);
        HBox visitTemp3 = new HBox(5);
        HBox visitTemp4 = new HBox(5);
        HBox visitTemp5 = new HBox(5);
        HBox visitTemp6 = new HBox(5);
        HBox visitTemp7 = new HBox(5);

        visitTemp1.getChildren().addAll(nrPatient, fillnrPatient);
        visitTemp2.getChildren().addAll(nrDoctor, fillnrDoctor);
        visitTemp3.getChildren().addAll(office, fillOffice);
        visitTemp4.getChildren().addAll(date, datePicker);
        visitTemp5.getChildren().addAll(purposeOfVisit, fillPurposeOfVisit);
        visitTemp6.getChildren().addAll(priority, priorityCombo);
        visitTemp7.getChildren().addAll(status, statusCombo);

        createTableOfLog();
        Button showLogTableVisit = new Button("Show log table of visit");
        showLogTableVisit.setMinWidth(270);
        showLogTableVisit.setOnMouseClicked(e -> createLogGui());

        patientvBox.getChildren().addAll(visit, new Separator(), searchVisit, visitTemp1, visitTemp2, visitTemp3,
                visitTemp4, visitTemp5, visitTemp6, visitTemp7, searchVisitButton, new Separator(), showLogTableVisit);
        //############# Creating second column ##############

        VBox secondvBox = new VBox(10);
        Label employee = new Label("Employee");
        Label writeDate = new Label("Write date");
        Label idEmployee = new Label("Id employee");
        Label nameEmployee = new Label("Name");
        Label surnameEmployee = new Label("Surname");
        Label postionEmployee = new Label("Postion");

        employee.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
        writeDate.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));

        idEmployee.setMinWidth(MIN_WIDTH);
        nameEmployee.setMinWidth(MIN_WIDTH);
        surnameEmployee.setMinWidth(MIN_WIDTH);
        postionEmployee.setMinWidth(MIN_WIDTH);

        TextField fillidEmployee = new TextField();
        TextField fillnameEmployee = new TextField();
        TextField fillsurnameEmployee = new TextField();

        fillidEmployee.setMinWidth(MIN_WIDTH);
        fillnameEmployee.setMinWidth(MIN_WIDTH);
        fillsurnameEmployee.setMinWidth(MIN_WIDTH);

        ComboBox postionEmployeeCombo = new ComboBox();
        postionEmployeeCombo.getItems().addAll("Lekarz", "Recepcjonistka", "");
        postionEmployeeCombo.setValue("Lekarz");
        postionEmployeeCombo.setMinWidth(MIN_WIDTH + 90);

        createTableOfEmploye();
        Button searchEmployee = new Button("Search employee");
        searchEmployee.setMinWidth(MIN_WIDTH);
        searchEmployee.setOnMouseClicked(e -> searchEmployee(fillidEmployee.getText(), fillnameEmployee.getText(),
                fillsurnameEmployee.getText(), postionEmployeeCombo.getValue().toString()));

        HBox employee1 = new HBox(idEmployee, fillidEmployee);
        HBox employee2 = new HBox(nameEmployee, fillnameEmployee);
        HBox employee3 = new HBox(surnameEmployee, fillsurnameEmployee);
        HBox employee4 = new HBox(postionEmployee, postionEmployeeCombo);

        // Add new employeee

        Label addNewEmployee = new Label("Add new employee");
        Label addNameEmployee = new Label("Name");
        Label addSurnameEmployee = new Label("Surname");
        Label addDateEmployee = new Label("Birth");
        Label addNrTelephone = new Label("Nr tel.");
        Label addPesel = new Label("Pesel");
        Label addSalary = new Label("Salary");
        Label addAddress = new Label("Address");

        addNewEmployee.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));

        addNameEmployee.setMinWidth(MIN_WIDTH);
        addSurnameEmployee.setMinWidth(MIN_WIDTH);
        addDateEmployee.setMinWidth(MIN_WIDTH);
        addNrTelephone.setMinWidth(MIN_WIDTH);
        addPesel.setMinWidth(MIN_WIDTH);
        addSalary.setMinWidth(MIN_WIDTH);
        addAddress.setMinWidth(MIN_WIDTH);

        TextField filladdNameEmployee = new TextField();
        TextField filladdSurnameEmployee = new TextField();
        TextField filladdNrTelephone = new TextField();
        TextField filladdPesel = new TextField();
        TextField filladdSalary = new TextField();
        TextField filladdAddress = new TextField();

        filladdAddress.setMinWidth(MIN_WIDTH);
        filladdSalary.setMinWidth(MIN_WIDTH);
        filladdPesel.setMinWidth(MIN_WIDTH);
        filladdNrTelephone.setMinWidth(MIN_WIDTH);
        filladdSurnameEmployee.setMinWidth(MIN_WIDTH);
        filladdNameEmployee.setMinWidth(MIN_WIDTH);

//81050222563
        DatePicker addEmployeedatePicker = new DatePicker();
        addEmployeedatePicker.setMinWidth(MIN_WIDTH);
        //addEmployeedatePicker.setValue((LocalDate.now()));
        addEmployeedatePicker.isShowWeekNumbers();
        addEmployeedatePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                datePicker.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        HBox add1 = new HBox(addNameEmployee, filladdNameEmployee);
        HBox add2 = new HBox(addSurnameEmployee, filladdSurnameEmployee);
        HBox add3 = new HBox(addDateEmployee, addEmployeedatePicker);
        HBox add4 = new HBox(addNrTelephone, filladdNrTelephone);
        HBox add5 = new HBox(addPesel, filladdPesel);
        HBox add6 = new HBox(addSalary, filladdSalary);
        HBox add7 = new HBox(addAddress, filladdAddress);
        HBox empty = new HBox();
        empty.setMinHeight(20);

        Button addnewEmployee = new Button("Add employee");
        addnewEmployee.setMinWidth(MIN_WIDTH);
        addnewEmployee.setOnMouseClicked(e -> connection.addNewEmploye(filladdNameEmployee.getText(), filladdSurnameEmployee.getText(),
                addEmployeedatePicker.getValue().toString(), filladdNrTelephone.getText(), filladdPesel.getText(),
                filladdSalary.getText(), filladdAddress.getText()));


        secondvBox.getChildren().addAll(employee, new Separator(), writeDate, employee1, employee2, employee3, employee4, searchEmployee,
                empty, addNewEmployee, add1, add2, add3, add4, add5, add6, add7, addnewEmployee);

        //third column of services

        VBox thirdvBox = new VBox(10);

        Label services = new Label("Services");
        Label idExecutor = new Label("Executor id");
        Label price = new Label("Price");
        Label idServices = new Label("Services id");

        services.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));

        idExecutor.setMinWidth(MIN_WIDTH);
        price.setMinWidth(MIN_WIDTH);
        idServices.setMinWidth(MIN_WIDTH);

        TextField fillidExecutor = new TextField();
        TextField fillprice = new TextField();
        TextField fillidServices = new TextField();

        fillidExecutor.setMinWidth(MIN_WIDTH);
        fillprice.setMinWidth(MIN_WIDTH);
        fillidServices.setMinWidth(MIN_WIDTH);

        HBox serv1 = new HBox(idExecutor, fillidExecutor);
        HBox serv2 = new HBox(price, fillprice);
        HBox serv3 = new HBox(idServices, fillidServices);

        createTableColumnOfServices();
        Button searchServices = new Button("Search"); //<=====================================================
        searchServices.setMinWidth(MIN_WIDTH);
        searchServices.setOnMouseClicked(e -> createServices(fillidExecutor.getText(), fillprice.getText(), fillidServices.getText()));

        //DUTY FIELD

        Label duty = new Label("Duty");
        Label chceckDuty = new Label("Check duty");
        Label doctorId = new Label("nr. Doctor");
        Label doctorData = new Label("Day");
        Label doctorStart = new Label("Start hour");
        Label doctorEnd = new Label("End hour");

        duty.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
        chceckDuty.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));

        doctorId.setMinWidth(MIN_WIDTH);
        doctorData.setMinWidth(MIN_WIDTH);
        doctorStart.setMinWidth(MIN_WIDTH);
        doctorEnd.setMinWidth(MIN_WIDTH);

        TextField filldoctorID = new TextField();
        TextField filldoctorIDx2 = new TextField();
        TextField filldoctorStart = new TextField();
        TextField filldoctorEnd = new TextField();

        ChoiceBox choiceBoxOfDay = new ChoiceBox(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"));
        final Tooltip tooltip2 = new Tooltip();
        tooltip2.setText("Select day of duty");
        choiceBoxOfDay.setTooltip(tooltip2);
        choiceBoxOfDay.setValue("Monday");

        filldoctorID.setMinWidth(MIN_WIDTH);
        filldoctorIDx2.setMinWidth(MIN_WIDTH);
        choiceBoxOfDay.setMinWidth(MIN_WIDTH + 90);
        filldoctorStart.setMinWidth(MIN_WIDTH);
        filldoctorEnd.setMinWidth(MIN_WIDTH);

        createTableOfOfficeHoursExtend(1);
        Button showDuty = new Button("Show");           // <------------------------------------------DODAĆ IMPLEMENTACJE
        showDuty.setMinWidth(MIN_WIDTH);
        showDuty.setOnMouseClicked(e -> searchDuty(filldoctorID.getText(), choiceBoxOfDay.getValue().toString(), filldoctorStart.getText(), filldoctorEnd.getText()));

        HBox dutyTmp1 = new HBox(5);
        HBox dutyTmp2 = new HBox(5);
        HBox dutyTmp3 = new HBox(5);
        HBox dutyTmp4 = new HBox(5);
        HBox empt = new HBox();
        empt.setMinHeight(10);

        dutyTmp1.getChildren().addAll(doctorId, filldoctorID);
        dutyTmp2.getChildren().addAll(doctorData, choiceBoxOfDay);
        dutyTmp3.getChildren().addAll(doctorStart, filldoctorStart);
        dutyTmp4.getChildren().addAll(doctorEnd, filldoctorEnd);

        // TRATMENTS

        Label performedTreatments = new Label("Performed treatments");
        Label doctorIdx2 = new Label("nr. Doctor");
        Label treatments = new Label("Treatments");

        performedTreatments.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));

        doctorIdx2.setMinWidth(MIN_WIDTH);
        treatments.setMinWidth(MIN_WIDTH);

        TextField filldoctor = new TextField();
        filldoctor.setMinWidth(MIN_WIDTH);

        ChoiceBox choiceBoxOfTreatment = new ChoiceBox(FXCollections.observableArrayList("Przegląd",
                "Konsultacja z planem leczenia", "Znieczulenie miejscowe", "Wypełnienie zęba",
                "Leczenie próchnicy", "Usuwanie kamienia", "Piaskowanie", "Wybielanie", "Usuwanie zęba",
                "Założenie aparatu", "Leczenie kanałowe", "Instruktaż higieny jamy ustnej", "Fluoryzacja",
                "Konsultacja chirurgiczna", "Nacięcie i drenaż ropnia", "Znieczulenie", "Leczenie zębów mlecznych"));

        final Tooltip tooltip3 = new Tooltip();
        tooltip3.setText("Select treatments");
        choiceBoxOfTreatment.setTooltip(tooltip3);
        choiceBoxOfTreatment.setValue("Przegląd");
        choiceBoxOfTreatment.setMaxWidth(MIN_WIDTH + 90);

        HBox treat1 = new HBox(doctorIdx2, filldoctor);
        HBox treat2 = new HBox(treatments, choiceBoxOfTreatment);
        HBox blank = new HBox();
        blank.setMinHeight(10);

        Button searchTreatment = new Button("Search");
        searchTreatment.setMinWidth(MIN_WIDTH);

        thirdvBox.getChildren().addAll(services, new Separator(), serv1, serv2, serv3, searchServices, empt,
                duty, new Separator(), dutyTmp1, dutyTmp2, dutyTmp3, dutyTmp4, showDuty, blank,
                performedTreatments, new Separator(), treat1, treat2, searchTreatment);


        //fourth column - request for holidays

        VBox fourthvBox = new VBox(10);

        Label requestforHoliday = new Label("Requests For Holidays");
        requestforHoliday.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));

        createHolidayTableColumn();
        Button requestHoliday = new Button("Show list of Holidays");
        requestHoliday.setMinWidth(230);
        requestHoliday.setOnMouseClicked(e -> createTableGuiHoliday());

        //salary

        Label salaryTitle = new Label("Salary of employees");
        salaryTitle.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));

        Label idWorker = new Label("Id employee");
        Label salary = new Label("Salary");
        Label raise = new Label("Raise");

        idWorker.setMinWidth(MIN_WIDTH);
        salary.setMinWidth(MIN_WIDTH);
        raise.setMinWidth(MIN_WIDTH);

        TextField fillidWorker = new TextField();
        TextField fillsalary = new TextField();
        TextField fillraise = new TextField();

        fillidWorker.setMinWidth(MIN_WIDTH);
        fillsalary.setMinWidth(MIN_WIDTH);
        fillraise.setMinWidth(MIN_WIDTH);

        Button change = new Button("Change");//<=============================================
        change.setMinWidth(MIN_WIDTH);

        HBox salary1 = new HBox(idWorker, fillidWorker);
        HBox salary2 = new HBox(salary, fillsalary);
        HBox salary3 = new HBox(raise, fillraise);
        HBox empty2 = new HBox();
        empty2.setMinWidth(10);

        //incoming

        Label incomingTitle = new Label("Income");
        Label informationIncome = new Label("Show the income of the clinic \n" +
                "from the last period:");
        Label day = new Label("Day");
        Label week = new Label("Week");
        Label month = new Label("Month");
        Label year = new Label("Year");

        day.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
        week.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
        month.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
        year.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
        informationIncome.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
        incomingTitle.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));

        day.setMinWidth(MIN_WIDTH);
        week.setMinWidth(MIN_WIDTH);
        month.setMinWidth(MIN_WIDTH);
        year.setMinWidth(MIN_WIDTH);

        dayCheckBox = new CheckBox("Day");
        weekCheckBox = new CheckBox("Week");
        monthCheckBox = new CheckBox("Month");
        yearCheckBox = new CheckBox("Year");


        HBox dayHbox = new HBox(day, dayCheckBox);
        HBox weekHbox = new HBox(week, weekCheckBox);
        HBox monthHbox = new HBox(month, monthCheckBox);
        HBox yearHbox = new HBox(year, yearCheckBox);

        dayCheckBox.setOnMouseClicked(e -> unSelectOther(dayCheckBox));
        weekCheckBox.setOnMouseClicked(e -> unSelectOther(weekCheckBox));
        monthCheckBox.setOnMouseClicked(e -> unSelectOther(monthCheckBox));
        yearCheckBox.setOnMouseClicked(e -> unSelectOther(yearCheckBox));

        HBox inemty = new HBox();
        inemty.setMinHeight(10);

        Label result = new Label("0.0");
        result.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));

        Button calculateIncome = new Button("Calculate");//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<,
        calculateIncome.setMinWidth(MIN_WIDTH);
        calculateIncome.setOnMouseClicked(e -> result.setText(connection.showIncome(isSelected())));

        fourthvBox.getChildren().addAll(requestforHoliday, new Separator(), requestHoliday, empty2, salaryTitle, new Separator(),
                salary1, salary2, salary3, change, inemty, incomingTitle, new Separator(), informationIncome,
                dayHbox, weekHbox, monthHbox, yearHbox, calculateIncome, result);

        mainWindowBox.getChildren().addAll(patientvBox, secondvBox, thirdvBox, fourthvBox);
        Scene scene = new Scene(mainWindowBox, 800, 500);
        mainWindow.setScene(scene);
        mainWindow.setMinHeight(720);
        mainWindow.setMinWidth(1280);
        mainWindow.setMaxHeight(720);
        mainWindow.setMaxWidth(1280);
        mainWindow.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        mainWindow.setX((primScreenBounds.getWidth() - mainWindow.getWidth()) / 2);
        mainWindow.setY((primScreenBounds.getHeight() - mainWindow.getHeight()) / 2);
    }
    String isSelected(){
        if(dayCheckBox.isSelected()) return dayCheckBox.getText();
        if(weekCheckBox.isSelected()) return weekCheckBox.getText();
        if(monthCheckBox.isSelected()) return monthCheckBox.getText();
        return yearCheckBox.getText();
    }
}
