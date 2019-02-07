package UsersWindows;

import LogInPanel.LogInPanel;
import UsersWindows.DatabaseConnection.DoctorDatabaseConnection;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class DoctorGui extends TableData implements ForceGenerateGui {

    private final LogInPanel logInPanel;
    private final Stage mainWindow;
    private TextField fillFrom;
    private TextField fillTo;
    private TextField fillName;
    private TextField fillSurname;
    private TextField fillPesel;
    private TextField patientName;
    private TextField patientSurName;
    private TextField patientID;


    private DoctorDatabaseConnection doctorDatabaseConnection;

    public DoctorGui(LogInPanel logInPanel, Stage mainWindow) {
        this.logInPanel = logInPanel;
        this.mainWindow = mainWindow;
        createGui();
        doctorDatabaseConnection = new DoctorDatabaseConnection(logInPanel.getUserId());
    }


    void clearHolidayTextField() {
        fillFrom.setText("");
        fillTo.setText("");
    }

    void clearPatientTextField() {
        fillName.setText("");
        fillSurname.setText("");
        fillPesel.setText("");
    }

    void clearVisitTextField() {
        patientName.setText("");
        patientSurName.setText("");
        patientID.setText("");
    }
    void findPatient(String name,String surname,String pesel){
        dataOfPatient = doctorDatabaseConnection.findPatient(name,surname,pesel);
        tableOfPatient.setItems(dataOfPatient);
    }
    void findPatientVisit(String patientName,String patientSurname,String patientId){
        dataOfVisits.removeAll();
        dataOfVisits = doctorDatabaseConnection.findPatientVisit(patientName,patientSurname,patientId);
        tableOfVisits.setItems(dataOfVisits);

    }

    void showDutyHours(){
        tableOfficeHours.setMinWidth(450);
        dataOfficeHours = doctorDatabaseConnection.showDutyHours(logInPanel.getUserId());
        tableOfficeHours.setItems(dataOfficeHours);

    }
    /**
     * lek@1
     * gdggs4y52r
     * 13-06-1995
     */
    @Override
    public void createGui( ) {

        mainWindow.setTitle("Doctor Panel ");

        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(30));

        final Tooltip tooltip = new Tooltip();

        HBox hBox = new HBox(20);
        Button holiday = new Button("Take a holiday");
        holiday.setOnMouseClicked(e -> doctorDatabaseConnection.getHoliday(fillFrom.getText(),fillTo.getText()));

        Label from = new Label("From:");
        from.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));
        Label to = new Label("To:");
        to.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));

        fillFrom = new TextField();
        fillFrom.setPromptText("Start Holiday");
        tooltip.setText("Enter the date, format: \"YYYY-MM-DD \"");
        fillFrom.setTooltip(tooltip);
        fillTo = new TextField();
        fillTo.setPromptText("End Holiday");
        fillTo.setTooltip(tooltip);

        Button clearHoliday = new Button("Clear");
        clearHoliday.setOnMouseClicked(e -> clearHolidayTextField());

        hBox.getChildren().addAll(holiday, from, fillFrom, to, fillTo, clearHoliday);

        Button findPatient = new Button("Find Patient");
        findPatient.setOnMouseClicked(e -> findPatient(fillName.getText(),fillSurname.getText(),fillPesel.getText()));

        Label name = new Label("Name:");
        name.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));
        Label surname = new Label("Surname:");
        surname.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));
        Label pesel = new Label("Pesel:");
        pesel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));

        fillName = new TextField();
        fillName.setPromptText(" name");
        tooltip.setText("You can search for patients by name and surname, or pesel");
        fillName.setTooltip(tooltip);
        final int width = 82;
        fillName.setMaxWidth(width);
        fillSurname = new TextField();
        fillSurname.setPromptText(" surname");
        fillSurname.setTooltip(tooltip);
        fillSurname.setMaxWidth(width);
        fillPesel = new TextField();
        fillPesel.setPromptText(" pesel");
        fillPesel.setTooltip(tooltip);
        fillPesel.setMaxWidth(width);


        Button clearPatientData = new Button("Clear");
        clearPatientData.setOnMouseClicked(e -> clearPatientTextField());

        HBox hBoxPatient = new HBox(15);
        hBoxPatient.getChildren().addAll(findPatient, name, fillName, surname, fillSurname, pesel, fillPesel, clearPatientData);


          tableOfPatient.setMaxHeight(100);

        Button historyOfVisits = new Button("History of visits");
        historyOfVisits.setOnMouseClicked(e -> findPatientVisit(patientName.getText(),patientSurName.getText(),patientID.getText()));

        Label name1 = new Label("Name:");
        name1.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));
        Label surname1 = new Label("Surname:");
        surname1.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));
        Label idPatient = new Label("idPatient:");
        idPatient.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));

        final int widthVisit = 82;
        patientName = new TextField();
        patientName.setPromptText("name");
        tooltip.setText("You can search for patients by name and surname, or idPatient");
        patientName.setTooltip(tooltip);
        patientName.setMaxWidth(widthVisit);

        patientSurName = new TextField();
        patientSurName.setPromptText("surname");
        patientSurName.setTooltip(tooltip);
        patientSurName.setMaxWidth(widthVisit);

        patientID = new TextField();
        patientID.setPromptText("id");
        patientID.setTooltip(tooltip);
        patientID.setMaxWidth(30);

        Button clearVisit = new Button("Clear");
        clearVisit.setOnMouseClicked(e -> clearVisitTextField());

        HBox visitHbox = new HBox(15);
        visitHbox.getChildren().addAll(historyOfVisits, name1, patientName, surname1, patientSurName, idPatient, patientID, clearVisit);

        createVisitTableColumn();
        tableOfVisits.setMaxHeight(100);

        Button showDuty = new Button("Show duty hours for the whole week");
        showDuty.setOnMouseClicked(e -> showDutyHours());
        showDuty.setMinWidth(300);

        createOfficeHoursTableColumn();
        tableOfficeHours.setMaxWidth(400);
        tableOfficeHours.setMaxHeight(160);

        vBox.getChildren().addAll(hBox, new Separator(), hBoxPatient, tableOfPatient, new Separator(), visitHbox,tableOfVisits,new Separator(),showDuty,tableOfficeHours);
        Scene scene = new Scene(vBox, 950, 500);
        mainWindow.setScene(scene);
        mainWindow.setX(400);
        mainWindow.setY(0);
        mainWindow.setMinHeight(800);
        mainWindow.setMinWidth(950);
        mainWindow.setMaxHeight(800);
        mainWindow.setMaxWidth(950);
        mainWindow.show();
    }


}
