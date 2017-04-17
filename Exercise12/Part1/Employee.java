package Part1;

import java.util.ArrayList;

/**
 * Created by Adar on 4/16/2017.
 */
public abstract class Employee implements Cloneable{
    private final String firstName;
    private final String lastName;
    private final String socialSecurityNumber;
    private ArrayList<String> children;

    public Employee(String firstName, String lastName, String socialSecurityNumber, ArrayList<String> children) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
        this.children = children;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void addChild(String socialSecurityNumber) {
        this.children.add(socialSecurityNumber);
    }

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
    protected Object clone() throws CloneNotSupportedException{

        /*
        Employee cloneEmployee = new Employee(new String(getFirstName()),
                new String(getLastName()), new String(getSocialSecurityNumber()),
                new ArrayList(getChildren()));
        return cloneEmployee;
        */
        return super.clone();
    }

    public abstract double earnings();
}
