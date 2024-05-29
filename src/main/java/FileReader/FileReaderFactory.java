package FileReader;


import Excel.ExcelData;

import java.io.InputStream;
import java.util.List;

public interface FileReaderFactory {
    List<ExcelData> importData(InputStream inputStream);
}
