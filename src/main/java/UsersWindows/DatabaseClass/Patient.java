package UsersWindows.DatabaseClass;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public  class Patient {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty pesel;
    private final SimpleIntegerProperty nr_tel;
    private final SimpleStringProperty data;
    private final SimpleStringProperty Adres;


    public Patient(Integer id, String firstName, String lastName, String pesel, Integer nr_tel, String data, String adres) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.pesel = new SimpleStringProperty(pesel);
        this.nr_tel = new SimpleIntegerProperty(nr_tel);
        this.data = new SimpleStringProperty(data);
        this.Adres = new SimpleStringProperty(adres);
    }

    public int getId() {
        return id.get();
    }

    public int getNr_tel() {
        return nr_tel.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getPesel() {
        return pesel.get();
    }

    public String getData() {
        return data.get();
    }

    public String getAdres() {
        return Adres.get();
    }

}
