package UsersWindows.DatabaseClass;

import javafx.beans.property.SimpleStringProperty;

public class Doctor {

    private final SimpleStringProperty id_pracownik;
    private final SimpleStringProperty Imie;
    private final SimpleStringProperty Nazwisko;
    private final SimpleStringProperty Specjalizacja;

    public Doctor(String id_pracownik, String imie, String nazwisko, String specjalizacja) {
        this.id_pracownik = new SimpleStringProperty(id_pracownik);
        this.Imie = new SimpleStringProperty(imie);
        this.Nazwisko = new SimpleStringProperty(nazwisko);
        this.Specjalizacja = new SimpleStringProperty(specjalizacja);
    }

    public String getId_pracownik() {
        return this.id_pracownik.get();
    }

    public String getImie() {
        return this.Imie.get();
    }

    public String getNazwisko() {
        return this.Nazwisko.get();
    }

    public String getSpecjalizacja() {
        return this.Specjalizacja.get();
    }

}
