package com.employeepayroll;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.employeepayroll.EmployeePayrollMain.IOCommand;
public class EmployeePayrollServiceTest {
    EmployeePayrollMain employee;

    @Before
    public void init() {
        EmployeePayrollData[] arrayOfEmp = {
                new EmployeePayrollData(1,"Jeff Bezos",100000.0),
                new EmployeePayrollData(2, "Bill Gates",200000.0),
                new EmployeePayrollData(3, "Mark Zuckerberg",300000.0)
        };

        employee = new EmployeePayrollMain();
        employee.setEmployeeDataList(Arrays.asList(arrayOfEmp));
        employee.writeEmployeePayrollData(IOCommand.FILE_IO);
        employee.printData();
    }

    @Test
    public void givenThreeEmployeesWhenWrittenToFileShouldMatchEmployeeEntries() {
        assertEquals(3, employee.countEntries(IOCommand.FILE_IO));
    }
    @Test
    public void givenFileOnReadingFromFileShouldMatchEmployeeCount() {
        List<EmployeePayrollData> employeeList = employee.readData();
        assertEquals(3, employee.countEntries(IOCommand.FILE_IO));
    }
}
