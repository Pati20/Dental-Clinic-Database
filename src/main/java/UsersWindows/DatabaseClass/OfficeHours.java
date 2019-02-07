package UsersWindows.DatabaseClass;

import javafx.beans.property.SimpleStringProperty;

public class OfficeHours {

    private final SimpleStringProperty dzien;
    private final SimpleStringProperty poczatek;
    private final SimpleStringProperty koniec;

    public OfficeHours(String dzien, String poczatek, String koniec) {
        this.dzien = new SimpleStringProperty(dzien);
        this.poczatek = new SimpleStringProperty(poczatek);
        this.koniec = new SimpleStringProperty(koniec);
    }

    public String getDzien(){
        return dzien.get();
    }
    public String getPoczatek(){
        return poczatek.get();
    }
    public String getKoniec(){
        return koniec.get();
    }
}
