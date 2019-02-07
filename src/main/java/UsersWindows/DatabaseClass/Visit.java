package UsersWindows.DatabaseClass;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Visit {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty lekarz;
    private final SimpleIntegerProperty idPacjent;
    private final SimpleIntegerProperty gabinet;
    private final SimpleStringProperty data_wizyty;
    private final SimpleStringProperty czas_wizyty;
    private final SimpleStringProperty cel_wizyty;
    private final SimpleStringProperty priorytet;
    private final SimpleStringProperty status_wizyty;

    public Visit(Integer id, String lekarz, Integer idPacjent, Integer gabinet, String data_wizyty, String czas_wizyty,
                 String cel_wizyty, String priorytet, String status_wizyty) {

        this.id = new SimpleIntegerProperty(id);
        this.lekarz = new SimpleStringProperty(lekarz);
        this.idPacjent = new SimpleIntegerProperty(idPacjent);
        this.gabinet = new SimpleIntegerProperty(gabinet);
        this.data_wizyty = new SimpleStringProperty(data_wizyty);
        this.czas_wizyty = new SimpleStringProperty(czas_wizyty);
        this.cel_wizyty = new SimpleStringProperty(cel_wizyty);
        this.priorytet = new SimpleStringProperty(priorytet);
        this.status_wizyty = new SimpleStringProperty(status_wizyty);
    }

    public int getId() {
        return id.get();
    }

    public String getLekarz() {
        return lekarz.get();
    }

    public int getIdPacjent() {
        return idPacjent.get();
    }

    public int getGabinet() {
        return gabinet.get();
    }

    public String getData_wizyty() {
        return data_wizyty.get();
    }

    public String getCzas_wizyty() { return czas_wizyty.get(); }

    public String getCel_wizyty() {
        return cel_wizyty.get();
    }

    public String getPriorytet() {
        return priorytet.get();
    }

    public String getStatus_wizyty() {
        return status_wizyty.get();
    }

}
