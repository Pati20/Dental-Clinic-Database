package UsersWindows.DatabaseClass;

import javafx.beans.property.SimpleStringProperty;

public class OfficeHoursExtend extends OfficeHours {

    private final SimpleStringProperty id;
    private final SimpleStringProperty idOfficeHour;

    public OfficeHoursExtend(String dzien, String poczatek, String koniec, String id,String idOfficeHour) {
        super(dzien, poczatek, koniec);
        this.id = new SimpleStringProperty(id);
        this.idOfficeHour = new SimpleStringProperty(idOfficeHour);
    }

    public String getId(){
        return id.get();
    }
    public String getIdOfficeHour(){
        return idOfficeHour.get();
    }
}
