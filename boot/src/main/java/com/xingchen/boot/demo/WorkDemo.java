package com.xingchen.boot.demo;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName WorkDemo
 * @Description TODO()
 * @Author xingchen
 * @Date 2019-08-13 16:09
 * @Version 1.0.0
 */
public class WorkDemo {

    public static void main(String[] args) {
        test2();
        String str = "";

    }


    public static void test4() {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");

        Row row = sheet.createRow(1);
        Cell cell = row.createCell(1);
        cell.setCellValue("This is a test of merging");

        sheet.addMergedRegion(new CellRangeAddress(
                1, //first row (0-based)
                1, //last row  (0-based)
                1, //first column (0-based)
                2  //last column  (0-based)
        ));

        // Write the output to a file
        try (OutputStream fileOut = new FileOutputStream("/Users/xxc/Desktop/workbook.xls")) {
            wb.write(fileOut);
            wb.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void test3(){
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");

        // Create a row and put some cells in it. Rows are 0 based.
        Row row = sheet.createRow(1);

        // Aqua background
        CellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
        style.setFillPattern(FillPatternType.BIG_SPOTS);
        Cell cell = row.createCell(1);
        cell.setCellValue("X");
        cell.setCellStyle(style);

        // Orange "foreground", foreground being the fill foreground not the font color.
        style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell = row.createCell(2);
        cell.setCellValue("X");
        cell.setCellStyle(style);

        // Write the output to a file
        try (OutputStream fileOut = new FileOutputStream("/Users/xxc/Desktop/workbook.xls")) {
            wb.write(fileOut);
            wb.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void test2(){
        try {
//            Workbook wb = WorkbookFactory.create(new File("MyExcel.xls"));

            // HSSFWorkbook, File
            POIFSFileSystem fs = new POIFSFileSystem(new File("/Users/xxc/Desktop/workbook.xls"));
            HSSFWorkbook wb1 = new HSSFWorkbook(fs.getRoot(), true);

            // XSSFWorkbook, File
//            OPCPackage pkg = OPCPackage.open(new File("/Users/xxc/Desktop/工作簿1.xlsx"));
//            XSSFWorkbook wb2 = new XSSFWorkbook(pkg);

            DataFormatter formatter = new DataFormatter();
            Sheet sheet1 = wb1.getSheetAt(0);
            for (Row row : sheet1) {
                for (Cell cell : row) {
                    CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                    System.out.print(cellRef.formatAsString());
                    System.out.print(" - ");

                    //通过获取单元格值并应用任何数据格式（Date，0.00,1.23e9，$ 1.23等）获取单元格中显示的文本
                    String text = formatter.formatCellValue(cell);
                    System.out.println(text);

                    //或者，获取值并自行格式化
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            System.out.println("STRING-"+cell.getRichStringCellValue().getString());
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                System.out.println("NUMERIC-"+cell.getDateCellValue());
                            } else {
                                System.out.println("NUMERIC-"+cell.getNumericCellValue());
                            }
                            break;
                        case BOOLEAN:
                            System.out.println("BOOLEAN-"+cell.getBooleanCellValue());
                            break;
                        case FORMULA:
                            System.out.println("FORMULA-"+cell.getCellFormula());
                            break;
                        case BLANK:
                            System.out.println();
                            break;
                        default:
                            System.out.println();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void test(){
        Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();

        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(2);
        row.setHeightInPoints(30);

        createCell(wb, row, 0, HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM);
        createCell(wb, row, 1, HorizontalAlignment.CENTER_SELECTION, VerticalAlignment.BOTTOM);
        createCell(wb, row, 2, HorizontalAlignment.FILL, VerticalAlignment.CENTER);
        createCell(wb, row, 3, HorizontalAlignment.GENERAL, VerticalAlignment.CENTER);
        createCell(wb, row, 4, HorizontalAlignment.JUSTIFY, VerticalAlignment.JUSTIFY);
        createCell(wb, row, 5, HorizontalAlignment.LEFT, VerticalAlignment.TOP);
        createCell(wb, row, 6, HorizontalAlignment.RIGHT, VerticalAlignment.TOP);

        // Write the output to a file
        try (OutputStream fileOut = new FileOutputStream("/Users/xxc/dev/testFile/xssf-align.xlsx")) {
            wb.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    *创建一个单元格并以某种方式对齐它。
    *
    * @param wb 工作簿
    * @param在行中创建单元格
    * @param列用于创建单元格的列号
    * @param halign 单元格的水平对齐方式。
    * @param valign 单元格的垂直对齐方式。
    */
    private static void createCell(Workbook wb,Row row,int column,HorizontalAlignment halign,VerticalAlignment valign){
        Cell cell = row.createCell(column);
        cell.setCellValue("Align It");
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cell.setCellStyle(cellStyle);
    }

    public static void hssfWorkbookDemo(){
        HSSFWorkbook wb = new HSSFWorkbook();
        try (OutputStream out = new FileOutputStream("/Users/xxc/dev/testFile/workbook.xls")){
            wb.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void xssfWorkbookDemo(){
        XSSFWorkbook wb = new XSSFWorkbook();

        Sheet sheet = wb.createSheet("new sheel");
        XSSFCreationHelper creationHelper = wb.getCreationHelper();
        //创建一行并在其中放入一些单元格。行以0为基础。
        Row row = sheet.createRow(0);
        //创建一个单元格并在其中放置一个值。
        Cell cell = row.createCell(0);
        cell.setCellValue("1234");

        row.createCell(1).setCellValue("4321");
        row.createCell(2).setCellValue(creationHelper.createRichTextString("This is a string"));
        row.createCell(3).setCellValue(true);
        row.createCell(4).setCellValue(false);

        row.createCell(5).setCellValue(new Date());
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy / mm / dd  h:mm"));
        Cell cell1 = row.createCell(6);
        cell1.setCellValue(new Date());
        cell1.setCellStyle(cellStyle);

        row.createCell(7).setCellValue(Calendar.getInstance());
        Cell cell2 = row.createCell(8);
        cell2.setCellValue(Calendar.getInstance());
        cell2.setCellStyle(cellStyle);

        Row row2 = sheet.createRow(1);
        row2.createCell(0).setCellType(CellType.ERROR);
        row2.createCell(2).setCellType(CellType.BLANK);
        row2.createCell(3).setCellType(CellType.BOOLEAN);
        row2.createCell(4).setCellType(CellType.FORMULA);

        try (OutputStream out = new FileOutputStream("/Users/xxc/dev/testFile/workbook2.xlsx")){
            wb.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void xssfWorkbookDemo0(){
        XSSFWorkbook wb = new XSSFWorkbook();

        Sheet sheet = wb.createSheet("new sheel");
        // Note that sheet name is Excel must not exceed 31 characters
        // and must not contain any of the any of the following characters:
        // 0x0000
        // 0x0003
        // colon (:)
        // backslash (\)
        // asterisk (*)
        // question mark (?)
        // forward slash (/)
        // opening square bracket ([)
        // closing square bracket (])
        //为了安全地创建有效名称，该实用程序用空格('')替换无效字符
        String safeName = WorkbookUtil.createSafeSheetName("%1?2*3");
        Sheet sheet2 = wb.createSheet(safeName);

        try (OutputStream out = new FileOutputStream("/Users/xxc/dev/testFile/workbook2.xlsx")){
            wb.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
