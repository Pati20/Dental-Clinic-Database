package UsersWindows;

import LogInPanel.LogInPanel;
import UsersWindows.DatabaseConnection.AdministratorDatabaseConnection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdministratorGui extends TableData implements ForceGenerateGui {

    private final LogInPanel logInPanel;
    private final Stage mainWindow;
    private AdministratorDatabaseConnection connection;
    private ComboBox deleteEmployeeCombo;
    private ComboBox deleteCombo;

    public AdministratorGui(LogInPanel logInPanel, Stage mainWindow) {
        this.logInPanel = logInPanel;
        this.mainWindow = mainWindow;
        connection = new AdministratorDatabaseConnection(null);
        createGui();
    }

    void givePrivileges(String user, String privileges, String table, String column) {
        System.out.println(column);
        Optional<ButtonType> action = showAlert("Are you sure to give \"" + user + "\" " + privileges + " privileges " +
                " \n to \"" + table + "\"?").showAndWait();
        if (action.get() == ButtonType.OK) {
            connection.givPrivileges(user, privileges, table, column);
        }
    }

    void revokePrivelages(String user, String privileges, String table) {
        Optional<ButtonType> action = showAlert("Are you sure to revoke privileges \"" + privileges + "\" \n" +
                " form user \"" + user + "\" to table \"" + table + "\"?").showAndWait();
        if (action.get() == ButtonType.OK) {
            connection.revokePrivelages(user, privileges, table);
        }
    }

    void deleteEmploye(String employe) {
        Optional<ButtonType> action = showAlert("Are you sure to delete " + employe + " ?").showAndWait();
        if (action.get() == ButtonType.OK) {
            connection.deleteEmployee(employe);
            deleteEmployeeCombo.getItems().addAll(connection.getEmployee());
            deleteEmployeeCombo.setValue("lek@1");
        }
    }

    void deleteUser(String user) {
        Optional<ButtonType> action = showAlert("Are you sure to delete " + user + " ?").showAndWait();
        if (action.get() == ButtonType.OK) {
            connection.deleteUser(user);
            deleteCombo.getItems().addAll(connection.getMysqlUsers());
            deleteCombo.setValue("root");
        }
    }

    void addUser(String login, String password, String postion, String id) {
        Optional<ButtonType> action = showAlert("Are you sure to add " + login + " ?").showAndWait();
        if (action.get() == ButtonType.OK) {
            connection.addUser(login, password, postion, id);
        }
    }

    void correctUser(String login, String password, String postion, String id) {
        Optional<ButtonType> action = showAlert("Are you sure to correct date under ID: " + id + " ?").showAndWait();
        if (action.get() == ButtonType.OK) {
            connection.correctUser(login, password, postion, id);
            dataOfUsers.removeAll();
            dataOfUsers = connection.createUsersTable();
            tableOfUsers.setItems(dataOfUsers);
        }
    }

    private List<String> listOfFiles() {
        List<String> results = new ArrayList<String>();
        File[] files = new File("src/main/Backup").listFiles();
        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        return results;
    }

    void add_update_NewDoctor(String id, String specialisation, int carry) {
        if (connection.add_update_NewDoctor(id, specialisation, carry)) {
            dataOffDoctor.removeAll();
            dataOffDoctor = connection.createDoctorTable();
            tableOfDoctor.setItems(dataOffDoctor);
        }

    }
    void updateofficeHour(String idOfficeHour,String idDoctor,String day, String start,String end,String holiday){

        if(day.equals("null")) day ="";
        if(holiday.equals("null")) holiday ="";

        Optional<ButtonType> action = showAlert("Are you sure to correct office under ID: " + idOfficeHour + " ?").showAndWait();
        if (action.get() == ButtonType.OK) {
            if(connection.updateofficeHour(idOfficeHour,idDoctor,day,start,end,holiday)){
                dataOfficeHoursExtend.removeAll();
                dataOfficeHoursExtend = connection.createDutyTable("","","","");
                tableOfficeHoursExtend.setItems(dataOfficeHoursExtend);
            }
        }
    }

    Alert showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert;
    }

    @Override
    public void createGui() {

        createUsersTableColumn();
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(createTabUser(), createTabDatabase(), createTabDoctor(), createTabOfficeHour());
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        mainWindow.setTitle("Administrator Panel ");
        Scene scene = new Scene(tabPane, 800, 500);
        int maxWidth = 1000;
        mainWindow.setScene(scene);
        mainWindow.setMinHeight(800);
        mainWindow.setMinWidth(maxWidth);
        mainWindow.setMaxHeight(800);
        mainWindow.setMaxWidth(maxWidth);
        mainWindow.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        mainWindow.setX((primScreenBounds.getWidth() - mainWindow.getWidth()) / 2);
        mainWindow.setY((primScreenBounds.getHeight() - mainWindow.getHeight()) / 2);
    }

    private Tab createTabDoctor() {
        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(30));

        int MIN_WIDTH = 135;
        HBox first = new HBox(10);

        dataOffDoctor = connection.createDoctorTable();
        tableOfDoctor.setItems(dataOffDoctor);
        tableOfDoctor.setMaxHeight(250);
        tableOfDoctor.setMinWidth(300);

        Label idUser = new Label("ID Doctor");
        idUser.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));

        ComboBox specialisationCombo = new ComboBox();
        specialisationCombo.getItems().addAll("Chirurgia stomatologiczna", "Ortodoncja", "Protetyka stomatologiczna",
                "Stomatologia dziecięca", "Stomatologia zachowawcza z endodoncją");
        specialisationCombo.setValue("Ortodoncja");

        TextField fillIdDoctro = new TextField();
        fillIdDoctro.setPromptText("id");
        fillIdDoctro.setMaxWidth(50);

        Button addNewDoctor = new Button("Add doctor");
        addNewDoctor.setMinWidth(MIN_WIDTH);
        addNewDoctor.setOnMouseClicked(e -> add_update_NewDoctor(fillIdDoctro.getText(), specialisationCombo.getValue().toString(), 0));

        first.getChildren().addAll(addNewDoctor, idUser, fillIdDoctro, specialisationCombo);

        //second
        HBox second = new HBox(10);

        Label doctor = new Label("ID Doctor");
        doctor.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));

        TextField fillIdDoctor = new TextField();
        fillIdDoctor.setPromptText("id");
        fillIdDoctor.setMaxWidth(50);

        Button deleteDoctor = new Button("Delete doctor");
        deleteDoctor.setMinWidth(MIN_WIDTH);
        deleteDoctor.setOnMouseClicked(e -> connection.removeDoctor(fillIdDoctor.getText()));

        second.getChildren().addAll(deleteDoctor, doctor, fillIdDoctor);

        //thrd
        HBox third = new HBox(10);

        Label doctorLabel = new Label("ID Doctor");
        doctorLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));

        TextField filldr = new TextField();
        filldr.setPromptText("id");
        filldr.setMaxWidth(50);

        ComboBox specialisationCombothird = new ComboBox();
        specialisationCombothird.getItems().addAll("Chirurgia stomatologiczna", "Ortodoncja", "Protetyka stomatologiczna",
                "Stomatologia dziecięca", "Stomatologia zachowawcza z endodoncją");
        specialisationCombothird.setValue("Ortodoncja");

        Button updateDoctor = new Button("Update doctor");
        updateDoctor.setMinWidth(MIN_WIDTH);
        updateDoctor.setOnMouseClicked(e -> add_update_NewDoctor(filldr.getText(), specialisationCombothird.getValue().toString(), 1));

        third.getChildren().addAll(updateDoctor, doctorLabel, filldr, specialisationCombothird);
        vBox.getChildren().addAll(new Separator(), tableOfDoctor, new Separator(), first, second, third);

        Tab databaseTab = new Tab();
        databaseTab.setText("Doctor Table");
        databaseTab.setContent(vBox);

        return databaseTab;
    }

    private Tab createTabDatabase() {

        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(30));

        int MIN_WIDTH = 135;

        HBox first = new HBox(10);

        Button doBackUp = new Button("Do backup");
        doBackUp.setMinWidth(MIN_WIDTH);
        doBackUp.setOnMouseClicked(e -> connection.backupDB());

        ComboBox filesRestoreCombo = new ComboBox();
        filesRestoreCombo.getItems().addAll(listOfFiles());
        filesRestoreCombo.setValue(listOfFiles().get(0));

        Button doRestore = new Button("Restore ");
        doRestore.setMinWidth(MIN_WIDTH);
        doRestore.setOnMouseClicked(e -> connection.restoreDB(filesRestoreCombo.getValue().toString()));

        first.getChildren().addAll(doBackUp, doRestore, filesRestoreCombo);

        vBox.getChildren().addAll(first);
        Tab databaseTab = new Tab();
        databaseTab.setText("Advanced Options");
        databaseTab.setContent(vBox);

        return databaseTab;
    }

    private Tab createTabUser() {

        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(30));

        HBox create = new HBox(10);

        Label loginCreate = new Label("Login");
        Label passwordCreate = new Label("Password");
        Label postionCreate = new Label("Postion");
        Label idCreate = new Label("Id");

        loginCreate.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));
        passwordCreate.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));
        postionCreate.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));
        idCreate.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));

        TextField fillLoginCreate = new TextField();
        TextField fillPasswordCreate = new TextField();
        TextField fillIdCreate = new TextField();

        ComboBox postionCombo = new ComboBox();
        postionCombo.getItems().addAll("Lekarz", "Dyrektor", "Recepcjonistka");
        postionCombo.setValue("Lekarz");

        int MAX_WIDTH = 135;
        fillLoginCreate.setMaxWidth(MAX_WIDTH);
        fillPasswordCreate.setMaxWidth(MAX_WIDTH);
        fillIdCreate.setMaxWidth(50);

        Button createUser = new Button("Create User");
        createUser.setMinWidth(MAX_WIDTH);

        createUser.setOnMouseClicked(e -> addUser(fillLoginCreate.getText(), fillPasswordCreate.getText(), postionCombo.getValue().toString(), fillIdCreate.getText()));
        create.getChildren().addAll(createUser, loginCreate, fillLoginCreate, passwordCreate, fillPasswordCreate,
                postionCreate, postionCombo, idCreate, fillIdCreate);

        //second row
        HBox privileges = new HBox(10);

        ComboBox userCombo = new ComboBox();
        ComboBox tablesCombo = new ComboBox();
        ComboBox privilegesCombo = new ComboBox();

        userCombo.getItems().addAll(connection.getMysqlUsers());
        tablesCombo.getItems().addAll(connection.getMysqlTables());
        privilegesCombo.getItems().addAll(connection.getMysqlPrivilegeType());

        final Tooltip tooltipUser = new Tooltip();
        tooltipUser.setText("Select user to get privelages");
        userCombo.setTooltip(tooltipUser);
        userCombo.setValue("root");

        final Tooltip tooltipTables = new Tooltip();
        tooltipTables.setText("Select tables");
        tablesCombo.setTooltip(tooltipTables);
        tablesCombo.setValue("dyzury");

        final Tooltip tooltipPrivileges = new Tooltip();
        tooltipPrivileges.setText("Select tables");
        privilegesCombo.setTooltip(tooltipTables);
        privilegesCombo.setValue("select");

        Label privelagesUser = new Label("User");
        Label privilegesOn = new Label("On");

        privelagesUser.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));
        privilegesOn.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));


        TextField fillColumnPrivileges = new TextField();
        fillColumnPrivileges.setPromptText("Chose column(a,b,...)");

        Button givePrivileges = new Button("Give privileges");
        givePrivileges.setOnMouseClicked(e -> givePrivileges(userCombo.getValue().toString(), privilegesCombo.getValue().toString(),
                tablesCombo.getValue().toString(), fillColumnPrivileges.getText()));

        givePrivileges.setMinWidth(MAX_WIDTH);
        fillColumnPrivileges.setMaxWidth(MAX_WIDTH);

        privileges.getChildren().addAll(givePrivileges, privelagesUser, userCombo, privilegesCombo, privilegesOn, tablesCombo, fillColumnPrivileges);

        //third row
        HBox revoke = new HBox(10);

        ComboBox userComboRevoke = new ComboBox();
        ComboBox tablesComboRevoke = new ComboBox();
        ComboBox privilegesComboRevoke = new ComboBox();

        userComboRevoke.getItems().addAll(connection.getMysqlUsers());
        tablesComboRevoke.getItems().addAll(connection.getMysqlTables());
        privilegesComboRevoke.getItems().addAll(connection.getMysqlPrivilegeType());

        final Tooltip tooltipUserRevoke = new Tooltip();
        tooltipUserRevoke.setText("Select user to revoke privelages");
        userComboRevoke.setTooltip(tooltipUserRevoke);
        userComboRevoke.setValue("lekarz");

        final Tooltip tooltipTablesRevoke = new Tooltip();
        tooltipTablesRevoke.setText("Select tables");
        tablesComboRevoke.setTooltip(tooltipTablesRevoke);
        tablesComboRevoke.setValue("dyzury");

        final Tooltip tooltipPrivilegesRevoke = new Tooltip();
        tooltipPrivilegesRevoke.setText("Select tables");
        privilegesComboRevoke.setTooltip(tooltipTablesRevoke);
        privilegesComboRevoke.setValue("select");

        Label privelagesUserRevoke = new Label("User");
        Label privilegesOnRevoke = new Label("On");

        privelagesUserRevoke.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));
        privilegesOnRevoke.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));


        Button revokePrivileges = new Button("Revoke privileges");
        revokePrivileges.setMinWidth(MAX_WIDTH);
        revokePrivileges.setOnMouseClicked(e -> revokePrivelages(userComboRevoke.getValue().toString(), tablesComboRevoke.getValue().toString(),
                privilegesComboRevoke.getValue().toString()));

        revoke.getChildren().addAll(revokePrivileges, privelagesUserRevoke, userComboRevoke, privilegesComboRevoke, privilegesOnRevoke, tablesComboRevoke);

        //fourth
        HBox employee = new HBox(10);

        Label elmpoyeeUserLeabel = new Label("User");
        elmpoyeeUserLeabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));
        Button deleteEmployee = new Button("Delete employee");
        deleteEmployee.setMinWidth(MAX_WIDTH);
        deleteEmployeeCombo = new ComboBox();
        deleteEmployeeCombo.getItems().addAll(connection.getEmployee());
        deleteEmployeeCombo.setValue("lekarz@1");

        deleteEmployee.setOnMouseClicked(e -> deleteEmploye(deleteEmployeeCombo.getValue().toString()));
        employee.getChildren().addAll(deleteEmployee, elmpoyeeUserLeabel, deleteEmployeeCombo);


        //fifth
        HBox delete = new HBox(10);

        Label deleteUserLeabel = new Label("User");
        deleteUserLeabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));
        Button deleteUser = new Button("Delete user");
        deleteUser.setMinWidth(MAX_WIDTH);
        deleteCombo = new ComboBox();
        deleteCombo.getItems().addAll(connection.getMysqlUsers());
        deleteCombo.setValue("lekarz");

        delete.getChildren().addAll(deleteUser, deleteUserLeabel, deleteCombo);
        deleteUser.setOnMouseClicked(e -> deleteUser(deleteCombo.getValue().toString()));
        //correct
        HBox correct = new HBox(10);

        Label forId = new Label("For id");
        forId.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));

        TextField fillCorrectId = new TextField();
        TextField fillCorrectLogin = new TextField();
        TextField fillCorrectPassword = new TextField();
        TextField fillCorrectPostion = new TextField();

        fillCorrectId.setPromptText("Id user");
        fillCorrectLogin.setPromptText("login");
        fillCorrectPassword.setPromptText("password");
        fillCorrectPostion.setPromptText("postion");

        fillCorrectId.setMinWidth(MAX_WIDTH - 85);
        fillCorrectLogin.setMinWidth(MAX_WIDTH);
        fillCorrectPassword.setMinWidth(MAX_WIDTH);
        fillCorrectPostion.setMinWidth(MAX_WIDTH);

        Button correctUser = new Button("Correct");
        correctUser.setMinWidth(MAX_WIDTH);
        correct.getChildren().addAll(correctUser, fillCorrectLogin, fillCorrectPassword, fillCorrectPostion, forId, fillCorrectId);
        correctUser.setOnMouseClicked(e -> correctUser(fillCorrectLogin.getText(), fillCorrectPassword.getText(),
                fillCorrectPostion.getText(), fillCorrectId.getText()));
        //table

        tableOfUsers.setMaxHeight(150);
        tableOfUsers.setMinHeight(250);
        dataOfUsers = connection.createUsersTable();
        tableOfUsers.setItems(dataOfUsers);

        //
        vBox.getChildren().addAll(new Separator(), create, new Separator(), privileges, new Separator(), revoke, new Separator(), employee,
                new Separator(), delete, new Separator(), correct, tableOfUsers);

        Tab tabUsers = new Tab();
        tabUsers.setText("User Options");
        tabUsers.setContent(vBox);

        return tabUsers;
    }

    private Tab createTabOfficeHour() {

        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(30));

        HBox first = new HBox(10);
        first.setAlignment(Pos.CENTER);
        createTableOfOfficeHoursExtend(1);
        dataOfficeHoursExtend = connection.createDutyTable("", "", "", "");
        tableOfficeHoursExtend.setItems(dataOfficeHoursExtend);
        tableOfficeHoursExtend.setMinHeight(250);
        tableOfficeHoursExtend.setMinWidth(400);

        first.getChildren().addAll(tableOfficeHoursExtend);

        //second row
        int MIN_WIDTH = 135;
        HBox second = new HBox(10);

        Label day = new Label("Day");
        Label holiday = new Label("Holiday");

        day.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));
        holiday.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));

        ComboBox holidayCombo = new ComboBox();
        holidayCombo.getItems().addAll("TAK","NIE");
        holidayCombo.setValue("NIE");

        ComboBox dayCombo = new ComboBox();
        dayCombo.getItems().addAll("Monday","Tuesday","Wednesday","Thursday","Friday");
        dayCombo.setValue("Monday");

        final Spinner<Integer> startMin = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59,0,15);
        startMin.setValueFactory(valueFactory);
        startMin.setMaxWidth(60);

        final Spinner<Integer> startHour = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactory2 = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(7, 19,7,1);
        startHour.setValueFactory(valueFactory2);
        startHour.setMaxWidth(60);

        final Spinner<Integer> EndMin = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactoryEndMin = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59,0,15);
        EndMin.setValueFactory(valueFactoryEndMin);
        EndMin.setMaxWidth(60);

        final Spinner<Integer> EndHour = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactoryEndH = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(7, 19,7,1);
        EndHour.setValueFactory(valueFactoryEndH);
        EndHour.setMaxWidth(60);

        HBox timeStart = new HBox(0);
        timeStart.getChildren().addAll(startHour,startMin);

        HBox timeEnd = new HBox(0);
        timeEnd.getChildren().addAll(EndHour,EndMin);

        TextField fillIdDoctor = new TextField();

        fillIdDoctor.setPromptText("id doctor");
        fillIdDoctor.setMaxWidth(170);

        Button addOfficeHour = new Button("Add office Hour");
        addOfficeHour.setMinWidth(MIN_WIDTH);
        addOfficeHour.setOnMouseClicked(e-> connection.addOfficeHour(fillIdDoctor.getText(),dayCombo.getValue().toString(),
                timeCorrecter(startHour.getValue().toString(),startMin.getValue().toString()),
                timeCorrecter(EndHour.getValue().toString(),EndMin.getValue().toString()),
                holidayCombo.getValue().toString()));

        second.getChildren().addAll(addOfficeHour,fillIdDoctor,day,dayCombo
        ,timeStart,timeEnd,holiday,holidayCombo);

        //third row
        HBox third = new HBox(10);

        Label idDeleteOfficeHour = new Label("id Duty");
        idDeleteOfficeHour.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));

        TextField fillidDeleteOfficeHour = new TextField();
        fillidDeleteOfficeHour.setMaxWidth(50);

        Button deleteOfficeHour = new Button("Delete Office Hour");
        deleteOfficeHour.setMinWidth(MIN_WIDTH);
        deleteOfficeHour.setOnMouseClicked(e -> connection.removeofficeHour(fillidDeleteOfficeHour.getText()));

        third.getChildren().addAll(deleteOfficeHour,idDeleteOfficeHour,fillidDeleteOfficeHour);

        //fourth

        HBox fourth = new HBox(10);

        TextField fillIdDoctorUpdate = new TextField();
        fillIdDoctorUpdate.setPromptText("id doctor");
        fillIdDoctorUpdate.setMaxWidth(50);

        Label idUpdateOfficeHour = new Label("Id Duty");
        Label dayUpdate = new Label("Day");
        Label holidayUpdate = new Label("Holiday");

        idUpdateOfficeHour.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));
        dayUpdate.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));
        holidayUpdate.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));

        TextField fillidUpdateOfficeHour = new TextField();
        fillidDeleteOfficeHour.setPromptText("Id Duty");
        fillidUpdateOfficeHour.setMaxWidth(50);

        final  Spinner<Integer> startHourUpdate = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactoryUpdate =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 19,0,1);
        startHourUpdate.setValueFactory(valueFactoryUpdate);
        startHourUpdate.setMaxWidth(60);

        final Spinner<Integer> startMinUpdate = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactoryStartUpdate = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        ((SpinnerValueFactory.IntegerSpinnerValueFactory) valueFactoryStartUpdate).setAmountToStepBy(15);
        startMinUpdate.setValueFactory(valueFactoryStartUpdate);
        startMinUpdate.setMaxWidth(60);

        final  Spinner<Integer> EndMinUpdate = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactoryEndMinUpdate =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        ((SpinnerValueFactory.IntegerSpinnerValueFactory) valueFactoryEndMinUpdate).setAmountToStepBy(15);
        EndMinUpdate.setValueFactory(valueFactoryEndMinUpdate);
        EndMinUpdate.setMaxWidth(60);

        final Spinner<Integer> EndHourUpdate = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactoryEndHUpdate =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 19,0,1);
        EndHourUpdate.setValueFactory(valueFactoryEndHUpdate);
        EndHourUpdate.setMaxWidth(60);

        HBox timeStartUpdate = new HBox(0);
        timeStartUpdate.getChildren().addAll(startHourUpdate,startMinUpdate);

        HBox timeEndUpdate = new HBox(0);
        timeEndUpdate.getChildren().addAll(EndHourUpdate,EndMinUpdate);

        ComboBox dayComboUpdate = new ComboBox();
        dayComboUpdate.getItems().addAll("Monday","Tuesday","Wednesday","Thursday","Friday","null");
        dayComboUpdate.setValue("null");

        ComboBox holidayComboUpdate = new ComboBox();
        holidayComboUpdate.getItems().addAll("TAK","NIE","null");
        holidayComboUpdate.setValue("null");

       // String a = startHourUpdate.getValue().toString()+":"+startMinUpdate.getValue().toString()+":00";
        Button updateOfficeHour = new Button("Update");//<<<<<<<<<<<<<<<<<<<<<<<<<
        updateOfficeHour.setMinWidth(MIN_WIDTH);
        updateOfficeHour.setOnMouseClicked(e -> updateofficeHour(fillidUpdateOfficeHour.getText(),fillIdDoctorUpdate.getText()
        ,dayComboUpdate.getValue().toString(),timeCorrecter(startHourUpdate.getValue().toString(),startMinUpdate.getValue().toString()),
                timeCorrecter(EndHourUpdate.getValue().toString(),EndMinUpdate.getValue().toString()),
                holidayComboUpdate.getValue().toString()));

        fourth.getChildren().addAll(updateOfficeHour,idUpdateOfficeHour,fillidUpdateOfficeHour,fillIdDoctorUpdate,dayUpdate,dayComboUpdate,timeStartUpdate,timeEndUpdate,
                holidayUpdate,holidayComboUpdate);

        vBox.getChildren().addAll(new Separator(), first, new Separator(),second,third,fourth);
        Tab tabUsers = new Tab();
        tabUsers.setText("Office Hours Tab");
        tabUsers.setContent(vBox);

        return tabUsers;
    }

    private String timeCorrecter(String hour,String min){
        String correct = "";

        if(hour.equals("0") && min.equals("0")){
            return correct;
        }
        if(min.length() == 1){
         min = min+"0";
        }
        return hour+":"+min+":00";
    }

}

