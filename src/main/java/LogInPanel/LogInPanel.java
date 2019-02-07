package LogInPanel;

import UsersWindows.FactoryGui;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * @author Albert Piekielny && Patrycja Paradowska
 * @version 1.0
 */
public class LogInPanel extends Application  {

    private Stage mainWindow;
    private TextField login;
    private PasswordField passwordField;
    private ChoiceBox choiceBox;
    private String userId;

    void onExit(){
        Platform.exit();
    }

    boolean sentPasswordLogintoCheck(String login, String password,String position ){
        ConnectorDatabase connectorDatabase = new ConnectorDatabase();
        boolean result =  connectorDatabase.checkPasswordandLogin(login,password,position);
        userId = ""+connectorDatabase.getIdUser();
        return result;
    }

     void createWindowForUser(){
        if(sentPasswordLogintoCheck(login.getText(),passwordField.getText(),(String) choiceBox.getValue())){
            FactoryGui.createGui(this,(String) choiceBox.getValue(),mainWindow);
        }
    }

    public String getUserId(){
        return userId;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        mainWindow = primaryStage;
        mainWindow.setTitle("Log In Window");
        mainWindow.setOnCloseRequest(e -> onExit());

        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(30));

        Label label1 = new Label("Login");
        Label label2 = new Label("Password");
        Label label3 = new Label("Position");

        login = new TextField();
        login.setPromptText("Your Login");
        passwordField = new PasswordField();
        passwordField.setPromptText("Your password");


//        login = new TextField("recepcjonistka@2443");  //---------------------RECEPTA
//        passwordField.setText("rehcept123");   ////

         passwordField.setText("admin1");   ////--------------------------------- ADMIN
        login = new TextField("administrator");//

//         passwordField.setText("gdggs4y52r");   ////---------------------- LEKARZ
//        login = new TextField("lek@1");  //
//
//         passwordField.setText("dyr124");   ////--------------------------- DYREK
//         login = new TextField("Dyrektor@12");  //



        choiceBox = new ChoiceBox(FXCollections.observableArrayList("Lekarz", "Recepcjonistka", "Dyrektor", "Administrator"));
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Select your position");
        choiceBox.setTooltip(tooltip);
        choiceBox.setValue("Lekarz");

        Button button = new Button("Log in");
        button.setMinSize(340,30);
        button.setOnMouseClicked(event -> createWindowForUser());


        vBox.getChildren().addAll(label1,login,label2,passwordField,label3,choiceBox,new Separator(),button);

        Scene scene = new Scene(vBox,400,400);
        mainWindow.setScene(scene);
        mainWindow.setMinHeight(400);
        mainWindow.setMinWidth(400);
        mainWindow.setMaxHeight(400);
        mainWindow.setMaxWidth(400);
        mainWindow.show();



    }
}
