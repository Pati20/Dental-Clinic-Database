package UsersWindows.DatabaseClass;

import javafx.beans.property.SimpleStringProperty;

public class Holiday {
    private final SimpleStringProperty id;
    private final SimpleStringProperty employeeId;
    private final SimpleStringProperty startHoliday;
    private final SimpleStringProperty endHoliday;
    private final SimpleStringProperty status;

    public Holiday(String id, String employeeId, String startHoliday, String endHoliday, String status) {
        this.id = new SimpleStringProperty(id);
        this.employeeId = new SimpleStringProperty(employeeId);
        this.startHoliday = new SimpleStringProperty(startHoliday);
        this.endHoliday = new SimpleStringProperty(endHoliday);
        this.status = new SimpleStringProperty(status);
    }

    public String getId() {
        return id.get();
    }

    public String getEmployeeId() {
        return employeeId.get();
    }

    public String getStartHoliday() {
        return startHoliday.get();
    }

    public String getEndHoliday() {
        return endHoliday.get();
    }

    public String getStatus() {
        return status.get();
    }
}
