package UsersWindows.DatabaseClass;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DoctorFreeDate {


    private final SimpleIntegerProperty idwizyta;
    private final SimpleStringProperty idlekarz;
    private final SimpleIntegerProperty gabinet;
    private final SimpleStringProperty data_wizyty;
    private final SimpleStringProperty czas_wizyty;
    private final SimpleStringProperty status_wizyty;
    private final SimpleStringProperty poczatek;
    private final SimpleStringProperty koniec;

    public DoctorFreeDate(Integer idwizyta, String idlekarz, Integer gabinet, String data_wizyty, String czas_wizyty,
                          String status_wizyty, String poczatek, String koniec) {

        this.idwizyta = new SimpleIntegerProperty(idwizyta);
        this.idlekarz = new SimpleStringProperty(idlekarz);
        this.gabinet = new SimpleIntegerProperty(gabinet);
        this.data_wizyty = new SimpleStringProperty(data_wizyty);
        this.czas_wizyty = new SimpleStringProperty(czas_wizyty);
        this.status_wizyty = new SimpleStringProperty(status_wizyty);
        this.poczatek = new SimpleStringProperty(poczatek);
        this.koniec = new SimpleStringProperty(koniec);
    }


    public String getPoczatek() { return this.poczatek.get(); }

    public String getKoniec() { return this.koniec.get(); }

    public String getIdlekarz() { return this.idlekarz.get(); }

    public int getGabinet() {
        return gabinet.get();
    }

    public int getIdwizyta() { return idwizyta.get(); }

    public String getStatus_wizyty() {
        return status_wizyty.get();
    }

    public String getData_wizyty() {
        return data_wizyty.get();
    }

    public String getCzas_wizyty() { return czas_wizyty.get(); }
}
