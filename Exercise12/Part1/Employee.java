package Part1;

import java.util.ArrayList;

/**
 * Created by Adar on 4/16/2017.
 */
public abstract class Employee implements Cloneable{
    /**
     * This class represents an Employee
     */

    private final String firstName;
    private final String lastName;
    private final String socialSecurityNumber;
    private ArrayList<String> children;

    /**
     * Returns an Employee object
     * @param  firstName the employee first name
     * @param  lastName the employee last name
     * @param  socialSecurityNumber the employee social security number
     * @param  children the employee list of children's social security numbers
     * @return      the Employee object
     */
    public Employee(String firstName, String lastName, String socialSecurityNumber, ArrayList<String> children) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
        this.children = children;
    }

    /**
     * Returns the employee first name
     * @return   the employee first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the employee last name
     * @return   the employee last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the employee social security number
     * @return   the employee social security number
     */
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    /**
     * Adds a child to the employee child list
     * @param  socialSecurityNumber the child social security number
     */
    public void addChild(String socialSecurityNumber) {
        this.children.add(socialSecurityNumber);
    }

    /**
     * Returns the list of employee children
     * @return   the employee list of employee children
     */
    public ArrayList getChildren() {
        return this.children;
    }

    @Override
    public String toString() {
        return String.format("%s %s%nsocial security number: %s; children: %s",
                getFirstName(), getLastName(), getSocialSecurityNumber(), getChildren());
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Employee) {
            Employee otherEmployee = (Employee)other;
            return getSocialSecurityNumber().equals(otherEmployee.getSocialSecurityNumber());
        } else {
            return false;
        }
    }

    @Override
    protected abstract Employee clone() throws CloneNotSupportedException;

    /**
     * Returns the employee earnings
     * @return   the employee earnings
     */
    public abstract double earnings();
}
