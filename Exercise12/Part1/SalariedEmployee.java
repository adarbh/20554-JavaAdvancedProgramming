package Part1;

import java.util.ArrayList;

/**
 * Created by Adar on 4/16/2017.
 */
public class SalariedEmployee extends Employee {
    /**
     * This class represents an SalariedEmployee
     */

    private double weeklySalary;

    /**
     * Returns an SalariedEmployee object
     * @param  firstName the employee first name
     * @param  lastName the employee last name
     * @param  socialSecurityNumber the employee social security number
     * @param  children the employee list of children's social security numbers
     * @param  weeklySalary the employee weekly salary
     * @return      the SalariedEmployee object
     */
    public SalariedEmployee(String firstName, String lastName, String socialSecurityNumber, ArrayList children,
                            double weeklySalary) {
        super(firstName, lastName, socialSecurityNumber, children);

        if(weeklySalary < 0.0) {
            throw new IllegalArgumentException("Weekly salary must be >= 0.0");
        }

        this.weeklySalary = weeklySalary;
    }

    /**
     * Sets the employee weekly salary
     * @param  weeklySalary the employee weekly salary
     */
    public void setWeeklySalary(double weeklySalary) {
        if(weeklySalary < 0.0) {
            throw new IllegalArgumentException("Weekly salary must be >= 0.0");
        }

        this.weeklySalary = weeklySalary;
    }

    /**
     * Returns the employee weekly salary
     * @return   the employee weekly salary
     */
    public double getWeeklySalary() {
        return weeklySalary;
    }

    @Override
    public double earnings() {
        return getWeeklySalary();
    }

    @Override
    public String toString() {
        return String.format("salaried employee: %s%n%s: $%,.2f",
                super.toString(), "weekly salary", getWeeklySalary());
    }

    @Override
    protected Employee clone() throws CloneNotSupportedException {
        SalariedEmployee salariedEmployee = new SalariedEmployee(new String(getFirstName()),
                new String(getLastName()), new String(getSocialSecurityNumber()),
                (ArrayList<String>) getChildren().clone(), getWeeklySalary());
        return salariedEmployee;
    }
}
