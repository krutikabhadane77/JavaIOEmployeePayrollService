package com.employeepayroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollMain {
    public enum IOCommand
    {CONSOLE_IO,FILE_IO,DB_IO,REST_IO}
    public List<EmployeePayrollData> employeeDataList;

    public void setEmployeeDataList(List<EmployeePayrollData>employeeDataList){
        this.employeeDataList=employeeDataList;
    }

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

    void writeEmployeePayrollData(IOCommand ioType) {
        if (ioType.equals(ioType.CONSOLE_IO)){
            System.out.println("Writing Employee Payroll Data to Console.");
            for (EmployeePayrollData employee:employeeDataList) {
                employee.printData();
            }
        } else if (ioType.equals(ioType.FILE_IO)) {
            new EmployeePayrollFileIOS().writeData(employeeDataList);
            System.out.println("Write employee payroll in file");
        }
    }

    public int countEntries(IOCommand ioType){
        if (ioType.equals(IOCommand.FILE_IO))
            return new EmployeePayrollFileIOS().countEntries();
        return 0;
    }

    public void printData(){
        new EmployeePayrollFileIOS().printData();
    }

    public List<EmployeePayrollData>readData(){
        return new EmployeePayrollFileIOS().readData();
    }

    public static void main(String[] args) {
        EmployeePayrollMain employee = new EmployeePayrollMain();
        employee.readEmployeePayrollData();
        employee.writeEmployeePayrollData(IOCommand.CONSOLE_IO);
        employee.writeEmployeePayrollData(IOCommand.FILE_IO);
        employee.printData();
        for (EmployeePayrollData employeeData:employee.readData()){
            employeeData.printData();
        }
    }
}
