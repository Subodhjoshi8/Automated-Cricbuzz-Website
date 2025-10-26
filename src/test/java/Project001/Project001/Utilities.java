package Project001.Project001;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
 
public class Utilities {
 
    public static void sleep(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
 
    public static void writeExcel(List<String> data){
        XSSFWorkbook workbook=new XSSFWorkbook();
        Sheet sheet=workbook.createSheet("Cricbuzz");
        Row row=sheet.createRow(0);
        row.createCell(0).setCellValue("Output");
        for (int i = 0; i < data.size() ; i++) {
            Row tempRow=sheet.createRow(i+1);
            tempRow.createCell(0).setCellValue(data.get(i));
        }
        try (FileOutputStream fis=new FileOutputStream("Cricbuzz_Output.xlsx")){
            workbook.write(fis);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Enhanced method to write both regular data and table data
    public static void writeExcelWithTable(List<String> data, List<String[]> tableData){
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        // Create header style
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
        
        // Create Test Execution Log Sheet
        Sheet logSheet = workbook.createSheet("Test Execution Log");
        Row logHeaderRow = logSheet.createRow(0);
        Cell logHeaderCell = logHeaderRow.createCell(0);
        logHeaderCell.setCellValue("Test Execution Steps");
        logHeaderCell.setCellStyle(headerStyle);
        
        // Add test execution data
        for (int i = 0; i < data.size(); i++) {
            Row tempRow = logSheet.createRow(i + 1);
            tempRow.createCell(0).setCellValue(data.get(i));
        }
        
        // Auto-size the column
        logSheet.autoSizeColumn(0);
        
        // Create Top Teams Table Sheet
        if (tableData != null && !tableData.isEmpty()) {
            Sheet tableSheet = workbook.createSheet("Top Teams Rankings");
            
            // Add table data
            for (int i = 0; i < tableData.size(); i++) {
                Row tableRow = tableSheet.createRow(i);
                String[] rowData = tableData.get(i);
                
                for (int j = 0; j < rowData.length; j++) {
                    Cell cell = tableRow.createCell(j);
                    cell.setCellValue(rowData[j]);
                    
                    // Apply header style to first row
                    if (i == 0) {
                        cell.setCellStyle(headerStyle);
                    }
                }
            }
            
            // Auto-size columns
            for (int i = 0; i < tableData.get(0).length; i++) {
                tableSheet.autoSizeColumn(i);
            }
        }
        
        try (FileOutputStream fis = new FileOutputStream("Cricbuzz_Output.xlsx")){
            workbook.write(fis);
            workbook.close();
            System.out.println("Excel file 'Cricbuzz_Output.xlsx' created successfully with multiple sheets!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 
    public static int strToInt(String text){
        int length=text.length();
        String price="";
        for(int i=0;i<length;i++){
            if(text.substring(i,i+1).matches("\\d")){
                price+=text.substring(i,i+1);
            }
        }
        return Integer.parseInt(price);
    }
} 