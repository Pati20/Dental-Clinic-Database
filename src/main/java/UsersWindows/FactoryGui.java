package UsersWindows;

import LogInPanel.LogInPanel;
import javafx.stage.Stage;

public class FactoryGui {


    public static ForceGenerateGui createGui(LogInPanel logInPanel, String type, Stage mainWindow){
        switch (type){
            case "Administrator":
                return new AdministratorGui(logInPanel, mainWindow);
            case "Lekarz":
                return new DoctorGui(logInPanel,mainWindow);
            case "Recepcjonistka":
                return new ReceptionistGui(logInPanel,mainWindow);
            case "Dyrektor":
               return new DirectorGui(logInPanel, mainWindow);
        }

        return null;
    }


}
