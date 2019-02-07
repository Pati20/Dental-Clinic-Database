package UsersWindows.DatabaseClass;

import javafx.beans.property.SimpleStringProperty;

public class Treatments {

    private final SimpleStringProperty id;
    private final SimpleStringProperty cena;
    private final SimpleStringProperty nazwa;

    public Treatments(String id, String cena, String nazwa) {
        this.id = new SimpleStringProperty(id);
        this.cena = new SimpleStringProperty(cena);
        this.nazwa = new SimpleStringProperty(nazwa);
    }

    public String getId() {
        return id.get();
    }

    public String getCena() {
        return cena.get();
    }

    public String getNazwa() {
        return nazwa.get();
    }
}
