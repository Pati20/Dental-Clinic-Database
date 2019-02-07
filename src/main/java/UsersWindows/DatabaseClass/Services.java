package UsersWindows.DatabaseClass;

import javafx.beans.property.SimpleStringProperty;

public class Services {
    private final SimpleStringProperty id;
    private final SimpleStringProperty idtreatment;
    private final SimpleStringProperty treatment;
    private final SimpleStringProperty price;

    public Services(String id, String idtreatment, String treatment, String price) {
        this.id = new SimpleStringProperty(id);
        this.idtreatment =  new SimpleStringProperty(idtreatment);
        this.treatment = new SimpleStringProperty(treatment);
        this.price = new SimpleStringProperty(price);
    }
    public String getId() {
        return id.get();
    }

    public String getTreatment() {
        return treatment.get();
    }

    public String getIdtreatment() { return idtreatment.get(); }

    public String getPrice() {
        return price.get();
    }

}
