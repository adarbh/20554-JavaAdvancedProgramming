package Part1;

import java.util.ArrayList;

/**
 * Created by Adar on 4/16/2017.
 */
public class CommissionEmployee extends Employee {
    /**
     * This class represents an CommissionEmployee
     */

    private double grossSales;
    private double commissionRate;

    /**
     * Returns an CommissionEmployee object
     * @param  firstName the employee first name
     * @param  lastName the employee last name
     * @param  socialSecurityNumber the employee social security number
     * @param  children the employee list of children's social security numbers
     * @param  grossSales the employee gross sales
     * @param  commissionRate the employee commission rate
     * @return      the CommissionEmployee object
     */
    public CommissionEmployee(String firstName, String lastName, String socialSecurityNumber, ArrayList children,
                              double grossSales, double commissionRate) {
        super(firstName, lastName, socialSecurityNumber, children);

        if (commissionRate <= 0.0 || commissionRate >= 1.0) {
            throw new IllegalArgumentException("Commission rate must be > 0.0 and < 1.0");
        }

        if (grossSales < 0.0) {
            throw new IllegalArgumentException("Gross sales must be >= 0.0");
        }

        this.grossSales = grossSales;
        this.commissionRate = commissionRate;
    }

    /**
     * Sets the employee gross sales
     * @param  grossSales the employee gross sales
     */
    public void setGrossSales(double grossSales) {
        if (grossSales < 0.0) {
            throw new IllegalArgumentException("Gross sales must be >= 0.0");
        }

        this.grossSales = grossSales;
    }

    /**
     * Returns the employee gross sales
     * @return   the employee gross sales
     */
    public double getGrossSales() {
        return grossSales;
    }

    /**
     * Sets the employee commission rate
     * @param  commissionRate the employee commission rate
     */
    public void setCommissionRate(double commissionRate) {
        if (commissionRate <= 0.0 || commissionRate >= 1.0) {
            throw new IllegalArgumentException("Commission rate must be > 0.0 and < 1.0");
        }

        this.commissionRate = commissionRate;
    }

    /**
     * Returns the employee commission rate
     * @return   the employee commission rate
     */
    public double getCommissionRate() {
        return commissionRate;
    }

    @Override
    public double earnings() {
        return getCommissionRate() * getGrossSales();
    }

    @Override
    public String toString() {
        return String.format("%s: %s%n%s: $%,.2f; %s: %,.2f",
                "commission employee", super.toString(),
                "gross sales", getGrossSales(),
                "commission rate", getCommissionRate());
    }

    @Override
    protected Employee clone() throws CloneNotSupportedException {
        CommissionEmployee commissionEmployee = new CommissionEmployee(new String(getFirstName()),
                new String(getLastName()), new String(getSocialSecurityNumber()),
                (ArrayList<String>) getChildren().clone(), getGrossSales(), getCommissionRate());
        return commissionEmployee;
    }
}
