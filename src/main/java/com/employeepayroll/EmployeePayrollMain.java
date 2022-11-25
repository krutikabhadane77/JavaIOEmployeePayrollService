package com.employeepayroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollMain {
    public enum IOCommand
    {CONSOLE_IO,FILE_IO,DB_IO,REST_IO}
    private List<EmployeePayrollData> employeeDataList;

    public EmployeePayrollMain() {
        employeeDataList = new ArrayList<EmployeePayrollData>();
    }

    private void readEmployeePayrollData() {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter employee ID : ");
        int id = sc.nextInt();
        System.out.print("Enter employee name : ");
        String name = sc.next();
        System.out.print("Enter employee salary : ");
        double salary = sc.nextDouble();
        EmployeePayrollData employee=new EmployeePayrollData(id,name,salary);
        System.out.println(employee);
        employeeDataList.add(employee);
        sc.close();
    }

    private void writeEmployeePayrollData() {
        System.out.println("Writing Employee Payroll Data to Console.");
        for (EmployeePayrollData employee:employeeDataList) {
            employee.printData();
        }
    }

    public static void main(String[] args) {
        EmployeePayrollMain employee = new EmployeePayrollMain();
        employee.readEmployeePayrollData();
        employee.writeEmployeePayrollData();
    }
}
