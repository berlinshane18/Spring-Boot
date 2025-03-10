package com.accolite.example.demo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AttendanceReportGenerator {

    static FileInputStream fis;
    static Workbook workbook;

    static Map<Integer, Employee> employeeData = new HashMap<>();
    static Map<Integer, List<LeaveRecord>> leaveData = new HashMap<>();
    static Set<Holiday> holidays = new HashSet<>();

    private static BasicDataSource dataSource = new BasicDataSource();

    static {

        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("berlin2002");
        dataSource.setMaxTotal(10);
        dataSource.setMaxIdle(5);
        dataSource.setMinIdle(1);
    }

    public static void main(String[] args) {
        try {
            fis = new FileInputStream("C://demo//demo//Employee Leaves.xlsx");
            workbook = new XSSFWorkbook(fis);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        loadEmployeeData();
        readLeaveData();
        loadHolidays();

        createAttendanceReport("January", 31, Calendar.JANUARY);
        createAttendanceReport("February", 28, Calendar.FEBRUARY);

        insertAttendanceData();

        System.out.println("Attendance data inserted into PostgreSQL successfully.");
    }

    public static void loadEmployeeData() {
        Sheet employeeSheet = workbook.getSheetAt(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (int i = 1; i <= employeeSheet.getPhysicalNumberOfRows(); i++) {
            Row row = employeeSheet.getRow(i);
            if (row != null) {
                try {
                    int employeeId = (int) row.getCell(0).getNumericCellValue();
                    String employeeName = row.getCell(1).getStringCellValue();
                    Date joiningDate = row.getCell(2).getDateCellValue();
                    Employee emp = new Employee(employeeId, employeeName, joiningDate);
                    employeeData.put(employeeId, emp);
                } catch (Exception e) {
                    System.err.println("Error processing employee row " + i + ": " + e.getMessage());
                }
            }
        }
    }

    private static void readLeaveData() {
        Sheet leaveSheet = workbook.getSheetAt(1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (int i = 1; i <= leaveSheet.getPhysicalNumberOfRows(); i++) {
            Row row = leaveSheet.getRow(i);
            if (row != null) {
                try {
                    int employeeId = (int) row.getCell(0).getNumericCellValue();
                    String fromDateString = formatDateString(row.getCell(2), sdf);
                    String toDateString = formatDateString(row.getCell(3), sdf);
                    String leaveType = row.getCell(4).getStringCellValue();

                    Date fromDate = sdf.parse(fromDateString);
                    Date toDate = sdf.parse(toDateString);
                    LeaveRecord leave = new LeaveRecord(fromDate, toDate, leaveType);

                    leaveData.putIfAbsent(employeeId, new ArrayList<>());
                    leaveData.get(employeeId).add(leave);
                } catch (Exception e) {
                    System.err.println("Error processing leave row " + i + ": " + e.getMessage());
                }
            }
        }
    }

    public static void loadHolidays() {
        Sheet holidaySheet = workbook.getSheetAt(2);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (int i = 1; i <= holidaySheet.getPhysicalNumberOfRows(); i++) {
            Row row = holidaySheet.getRow(i);
            if (row != null) {
                try {
                    String holidayDateString = formatDateString(row.getCell(0), sdf);
                    String holidayName = row.getCell(1).getStringCellValue();
                    Date holidayDate = sdf.parse(holidayDateString);

                    Holiday holiday = new Holiday(holidayDate, holidayName);
                    holidays.add(holiday);
                } catch (Exception e) {
                    System.err.println("Error processing holiday row " + i + ": " + e.getMessage());
                }
            }
        }
    }

    public static String formatDateString(Cell cell, SimpleDateFormat sdf) {
        if (cell != null) {
            try {
                if (cell.getCellType() == CellType.NUMERIC) {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        return sdf.format(date);
                    } else {
                        return String.valueOf(cell.getNumericCellValue());
                    }
                } else if (cell.getCellType() == CellType.STRING) {
                    return cell.getStringCellValue();
                }
            } catch (Exception e) {
                System.err.println("Error formatting date: " + e.getMessage());
            }
        }
        return "";
    }

    public static void createAttendanceReport(String monthName, int numDays, int month) {
        Sheet attendanceSheet = workbook.createSheet("Monthly Attendance " + monthName);

        Row headerRow1 = attendanceSheet.createRow(0);

        Row headerRow2 = attendanceSheet.createRow(1);

        headerRow1.createCell(0).setCellValue("ID\\Day");
        headerRow2.createCell(0).setCellValue("");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, month, 1);

        for (int i = 0; i < numDays; i++) {
            String date = dateFormat.format(calendar.getTime());
            String dayOfWeek = dayFormat.format(calendar.getTime());

            headerRow1.createCell(i + 1).setCellValue(date);

            headerRow2.createCell(i + 1).setCellValue(dayOfWeek);

            calendar.add(Calendar.DATE, 1);
        }

        int rowNum = 2;
        for (Employee emp : employeeData.values()) {
            Row row = attendanceSheet.createRow(rowNum++);
            row.createCell(0).setCellValue(emp.getEmployee_id());

            calendar.set(2025, month, 1);

            for (int i = 0; i < numDays; i++) {
                String currentDate = dateFormat.format(calendar.getTime());
                String attendance = getAttendanceStatus(emp.getEmployee_id(), currentDate);
                row.createCell(i + 1).setCellValue(attendance);
                calendar.add(Calendar.DATE, 1);
            }
        }

        System.out.println(monthName + " Attendance report generated successfully!");
    }


    public static void insertAttendanceData() {
        String insertSQL = "INSERT INTO attendance (employee_id, date, status) VALUES (?, ?, ?)";
        int recordsInserted = 0;

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                for (Employee emp : employeeData.values()) {
                    for (int month = Calendar.JANUARY; month <= Calendar.FEBRUARY; month++) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(2025, month, 1);
                        int numDays = (month == Calendar.JANUARY) ? 31 : 28;

                        for (int i = 0; i < numDays; i++) {
                            String currentDate = dateFormat.format(calendar.getTime());
                            String attendanceStatus = getAttendanceStatus(emp.getEmployee_id(), currentDate);

                            preparedStatement.setInt(1, emp.getEmployee_id());
                            preparedStatement.setDate(2, java.sql.Date.valueOf(currentDate));
                            preparedStatement.setString(3, attendanceStatus);

                            preparedStatement.addBatch();
                            calendar.add(Calendar.DATE, 1);
                        }
                    }
                }

                int[] affectedRows = preparedStatement.executeBatch();
                recordsInserted = Arrays.stream(affectedRows).sum();  // Sum the number of affected rows

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                System.err.println("Error inserting data: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(recordsInserted + " attendance records inserted into the database.");
    }

    public static String getAttendanceStatus(int employeeId, String date) {
        String status = "P";

        if (isHoliday(date) || isWeekend(date)) {
            return "H";
        }

        if (leaveData.containsKey(employeeId)) {
            for (LeaveRecord leave : leaveData.get(employeeId)) {
                if (isDateInRange(date, leave.getFromDate(), leave.getToDate())) {
                    return leave.getLeaveType();
                }
            }
        }

        return status;
    }

    public static boolean isHoliday(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date parsedDate = sdf.parse(date);
            for (Holiday holiday : holidays) {
                if (parsedDate.equals(holiday.getHoliday_date())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isDateInRange(String date, Date fromDate, Date toDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date dateToCheck = sdf.parse(date);
            return !dateToCheck.before(fromDate) && !dateToCheck.after(toDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isWeekend(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date parsedDate = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
