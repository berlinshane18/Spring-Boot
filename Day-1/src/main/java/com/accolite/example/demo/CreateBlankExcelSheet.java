package com.accolite.example.demo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateBlankExcelSheet
{
    public static void main(String[] args)
    {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attendance Tracker");
        try (FileOutputStream fileOut = new FileOutputStream(new File("Employee_Leaves.xlsx")))
        {
            workbook.write(fileOut);
            System.out.println("Blank Excel file created successfully!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                workbook.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
