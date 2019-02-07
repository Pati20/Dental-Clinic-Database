package UsersWindows.DatabaseClass;

import javafx.beans.property.SimpleStringProperty;

public class Log {

    private final SimpleStringProperty id;
    private final SimpleStringProperty visitID;
    private final SimpleStringProperty oldDate;
    private final SimpleStringProperty newDate;
    private final SimpleStringProperty oldDoctor;
    private final SimpleStringProperty newDoctor;
    private final SimpleStringProperty dateOfChange;

    public Log(String id, String visitID, String oldDate, String newDate, String oldDoctor, String newDoctor, String dateOfChange) {
        this.id = new SimpleStringProperty(id);
        this.visitID = new SimpleStringProperty(visitID);
        this.oldDate = new SimpleStringProperty(oldDate);
        this.newDate = new SimpleStringProperty(newDate);
        this.oldDoctor = new SimpleStringProperty(oldDoctor);
        this.newDoctor = new SimpleStringProperty(newDoctor);
        this.dateOfChange = new SimpleStringProperty(dateOfChange);
    }

    public String getId() {
        return id.get();
    }

    public String getVisitID() {
        return visitID.get();
    }

    public String getOldDate() {
        return oldDate.get();
    }


    public String getNewDate() {
        return newDate.get();
    }

    public String getOldDoctor() {
        return oldDoctor.get();
    }


    public String getNewDoctor() {
        return newDoctor.get();
    }


    public String getDateOfChange() {
        return dateOfChange.get();
    }


}
