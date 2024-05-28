package Excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.InputStream;

public class ExcelDataInput {

    public List<ExcelData> importExcelData(InputStream inputStream) {
        List<ExcelData> excelDataList = new ArrayList<>();

        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();

            if (rowIterator.hasNext()) {
                rowIterator.next();
            } else {
                System.out.println("No rows found in the Excel file.");
                return excelDataList;
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                double first = row.getCell(0).getNumericCellValue();
                double second = row.getCell(1).getNumericCellValue();

                String firstStr = String.valueOf(first);
                String secondStr = String.valueOf(second);

                excelDataList.add(new ExcelData(firstStr, secondStr));
            }

            System.out.println(excelDataList);
            System.out.println("Data from Excel successfully imported.");
        } catch (IOException e) {
            System.out.println("Error processing Excel file: " + e.getMessage());
        }
        return excelDataList;
    }

}

