package UsersWindows.DatabaseClass;

import javafx.beans.property.SimpleIntegerProperty;

public class Employee extends Patient {
    private final SimpleIntegerProperty salary;

    public Employee(Integer id, String firstName, String lastName, String pesel,
                    Integer nr_tel, String data, String adres, int salary) {

        super(id, firstName, lastName, pesel, nr_tel, data, adres);
        this.salary = new SimpleIntegerProperty(salary);
    }

    public int getSalary() {
        return salary.get();
    }
}
