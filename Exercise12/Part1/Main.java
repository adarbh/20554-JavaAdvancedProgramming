package Part1;

import java.util.ArrayList;

/**
 * Created by Adar on 4/16/2017.
 */
public class Main {
    public static void main(String [ ] args) throws CloneNotSupportedException {

        /* Create an array of employees */
        ArrayList<Employee> employees = new ArrayList<Employee>();
        SalariedEmployee salariedEmployee = new SalariedEmployee("Eli", "Shavit",
                "207013455", new ArrayList<String>(), 500);
        salariedEmployee.addChild("317011453");

        CommissionEmployee commissionEmployee = new CommissionEmployee("David", "Shalom",
                "221013354", new ArrayList<String>(), 10, 0.3);
        commissionEmployee.addChild("302121452");
        commissionEmployee.addChild("216742218");

        BasePlusCommissionEmployee basePlusCommissionEmployee = new BasePlusCommissionEmployee("Shir", "Levi",
                "307622341", new ArrayList<String>(), 10, 0.3, 5000);

        HourlyEmployee hourlyEmployee = new HourlyEmployee("Tal", "Shani",
                "300811321", new ArrayList<String>(), 40, 60);
        hourlyEmployee.addChild("213006155");
        hourlyEmployee.addChild("206142331");
        hourlyEmployee.addChild("376061233");

        employees.add(salariedEmployee);
        employees.add(commissionEmployee);
        employees.add(basePlusCommissionEmployee);
        employees.add(hourlyEmployee);

        /* Display the employees */
        System.out.println("There are a total of " + employees.size() + " employees:");
        System.out.println("_________________________________");

        for (int i = 0 ; i < employees.size() ; i++) {
            System.out.println(String.format(employees.get(i).toString() + "; earnings: $%,.2f\n", employees.get(i).earnings()));
        }

        /* Demonstrate the equals function */
        SalariedEmployee salariedEmployee2 = new SalariedEmployee("Adam", "Tal",
                "207013455", new ArrayList<String>(), 600);

        System.out.println("Demonstrate the equals function: ");
        System.out.println("________________________________");
        System.out.println(salariedEmployee.toString() + "\n");
        System.out.println(salariedEmployee2.toString() + "\n");
        System.out.println(String.format("Are %s and %s equal? ",
                salariedEmployee.getFirstName(), salariedEmployee2.getFirstName()) +
                salariedEmployee.equals(salariedEmployee2));

        /* Demonstrate the clone function */
        CommissionEmployee commissionEmployee2 = (CommissionEmployee)commissionEmployee.clone();

        System.out.println("Demonstrate the clone function: ");
        System.out.println("_______________________________");
        System.out.println("The original employee: \n" + commissionEmployee.toString() + "\n");
        System.out.println("The cloned employee: \n" + commissionEmployee2.toString() + "\n");
        System.out.println(String.format("Are %s and %s equal? ",
                commissionEmployee.getFirstName(), commissionEmployee2.getFirstName()) +
                commissionEmployee.equals(commissionEmployee2));

        System.out.println(String.format("Adding a child to %s.\n", commissionEmployee.getFirstName()));
        commissionEmployee.addChild("318063451");
        System.out.println("The original employee: \n" + commissionEmployee.toString() + "\n");
        System.out.println("The cloned employee: \n" + commissionEmployee2.toString() + "\n");
    }
}
