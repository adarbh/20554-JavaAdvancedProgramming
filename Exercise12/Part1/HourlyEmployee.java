package Part1;

import java.util.ArrayList;

/**
 * Created by Adar on 4/16/2017.
 */
public class HourlyEmployee extends Employee {
    /**
     * This class represents an HourlyEmployee
     */

    private double wage;
    private double hours;

    /**
     * Returns an HourlyEmployee object
     * @param  firstName the employee first name
     * @param  lastName the employee last name
     * @param  socialSecurityNumber the employee social security number
     * @param  children the employee list of children's social security numbers
     * @param  wage the employee wage
     * @param  hours the employee work hours
     * @return      the HourlyEmployee object
     */
    public HourlyEmployee(String firstName, String lastName, String socialSecurityNumber, ArrayList children,
                          double wage, double hours) {
        super(firstName, lastName, socialSecurityNumber, children);

        if (wage < 0.0) {
            throw new IllegalArgumentException("Hourly salary must be >= 0.0");
        }

        if ((hours < 0.0) || (hours > 168.0)) {
            throw new IllegalArgumentException("Hours worked must be >= 0.0 and <= 168.0");
        }

        this.wage = wage;
        this.hours = hours;
    }

    /**
     * Sets the employee wage
     * @param  wage the employee wage
     */
    public void setWage(double wage) {
        if (wage < 0.0) {
            throw new IllegalArgumentException("Hourly salary must be >= 0.0");
        }

        this.wage = wage;
    }

    /**
     * Returns the employee wage
     * @return   the employee wage
     */
    public double getWage() {
        return wage;
    }

    /**
     * Sets the employee hours
     * @param  hours the employee hours
     */
    public  void setHours(double hours) {
        if ((hours < 0.0) || (hours > 168.0)) {
            throw new IllegalArgumentException("Hours worked must be >= 0.0 and <= 168.0");
        }

        this.hours = hours;
    }

    /**
     * Returns the employee hours
     * @return   the employee hours
     */
    public double getHours() {
        return hours;
    }

    @Override
    public double earnings() {
        if (getHours() <= 40) {
            return getWage() * getHours();
        } else {
            return 40 * getWage() + (getHours() - 40) * getWage() * 1.5;
        }
    }

    @Override
    public String toString() {
        return String.format("hourly employee: %s%n%s: $%,.2f; %s: %,.2f",
                super.toString(), "hourly wage", getWage(), "hours worked", getHours());
    }

    @Override
    protected Employee clone() throws CloneNotSupportedException {
        HourlyEmployee hourlyEmployee = new HourlyEmployee(new String(getFirstName()),
                new String(getLastName()), new String(getSocialSecurityNumber()),
                (ArrayList<String>) getChildren().clone(), getWage(), getHours());
        return hourlyEmployee;
    }
}
