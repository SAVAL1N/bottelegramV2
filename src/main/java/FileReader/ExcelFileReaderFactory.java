package FileReader;



import Excel.ExcelData;
import Excel.ExcelDataInput;

import java.io.InputStream;
import java.util.List;

public class ExcelFileReaderFactory implements FileReaderFactory {
    private final ExcelDataInput excelDataInput;

    public ExcelFileReaderFactory(ExcelDataInput excelDataInput) {
        this.excelDataInput = excelDataInput;
    }

    @Override
    public List<ExcelData> importData(InputStream inputStream) {
        return excelDataInput.importExcelData(inputStream);
    }
}
