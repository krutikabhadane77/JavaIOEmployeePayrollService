package com.employeepayroll;
import java.io.*;
import java.nio.file.*;
import java.util.List;
public class EmployeePayrollFileIOS {
    public static String PAYROLL_FNAME = "payroll.txt";

    public void writeData(List<EmployeePayrollData> employeeDataList) {

        StringBuffer empBuffer = new StringBuffer();
        employeeDataList.forEach(employee -> {
            String employeeDataStr = employee.pushData().concat("\n");
            empBuffer.append(employeeDataStr);
        });

        try {
            Files.write(Paths.get(PAYROLL_FNAME), empBuffer.toString().getBytes());
        }catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public int countEntries() {

        int entries = 0;

        try {
            entries = (int) Files.lines(new File(PAYROLL_FNAME).toPath()).count();
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
        return entries;
    }
}
