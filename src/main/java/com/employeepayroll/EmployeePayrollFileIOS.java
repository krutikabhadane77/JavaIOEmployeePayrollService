package com.employeepayroll;
import java.io.*;
import java.nio.file.*;
import java.util.*;
public class EmployeePayrollFileIOS {
    public static String payrollFile = "payroll.txt";

    public void writeData(List<EmployeePayrollData> employeeDataList) {

        StringBuffer empBuffer = new StringBuffer();
        employeeDataList.forEach(employee -> {
            String employeeDataStr = employee.pushData().concat("\n");
            empBuffer.append(employeeDataStr);
        });

        try {
            Files.write(Paths.get(payrollFile), empBuffer.toString().getBytes());
        }catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public int countEntries() {

        int entries = 0;

        try {
            entries = (int) Files.lines(new File(payrollFile).toPath()).count();
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
        return entries;
    }

    public void printData(){
        try {
            Files.lines(new File(payrollFile).toPath()).forEach(System.out::println);
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public List<EmployeePayrollData> readData() {
        List<EmployeePayrollData> employeeDataList = new ArrayList<EmployeePayrollData>();

        try {
            Files.lines(new File(payrollFile).toPath())
                    .map(line->line.trim())
                    .forEach(line->{
                        String data = line.toString();
                        String[] dataArr = data.split(",");
                        for(int i=0;i<dataArr.length;i++){
                            int id = Integer.valueOf(dataArr[i].split(" = ")[1]);
                            i++;
                            String name = dataArr[i].replaceAll("name =", "");
                            i++;
                            double salary = Double.parseDouble(dataArr[i].replaceAll("salary =", ""));
                            EmployeePayrollData employee = new EmployeePayrollData(id,name,salary);
                            employeeDataList.add(employee);
                        }
                    });
        }catch(IOException exception) {
            exception.printStackTrace();
        }
        return employeeDataList;
    }
}