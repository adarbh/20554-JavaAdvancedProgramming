package Part1;

import java.util.ArrayList;

/**
 * Created by Adar on 4/16/2017.
 */
public class BasePlusCommissionEmployee extends CommissionEmployee {
    /**
     * This class represents an BasePlusCommissionEmployee
     */

    private double baseSalary;

    /**
     * Returns an CommissionEmployee object
     * @param  firstName the employee first name
     * @param  lastName the employee last name
     * @param  socialSecurityNumber the employee social security number
     * @param  children the employee list of children's social security numbers
     * @param  grossSales the employee gross sales
     * @param  commissionRate the employee commission rate
     * @param  baseSalary the employee base salary
     * @return      the CommissionEmployee object
     */
    public BasePlusCommissionEmployee(String firstName, String lastName, String socialSecurityNumber, ArrayList children,
                                      double grossSales, double commissionRate, double baseSalary) {
        super(firstName, lastName, socialSecurityNumber, children, grossSales, commissionRate);

        if (baseSalary < 0.0) {
            throw new IllegalArgumentException("Base salary must be >= 0.0");
        }

        this.baseSalary = baseSalary;
    }

    /**
     * Sets the employee base salary
     * @param  baseSalary the employee base salary
     */
    public void setBaseSalary(double baseSalary) {
        if (baseSalary < 0.0) {
            throw new IllegalArgumentException("Base salary must be >= 0.0");
        }

        this.baseSalary = baseSalary;
    }

    /**
     * Returns the employee base salary
     * @return   the employee base salary
     */
    public double getBaseSalary() {
        return baseSalary;
    }

    @Override
    public double earnings() {
        return getBaseSalary() + super.earnings();
    }

    @Override
    public String toString() {
        return String.format("%s %s; %s: $%,.2f",
                "base-salaried", super.toString(),
                "base salary", getBaseSalary());
    }
}
