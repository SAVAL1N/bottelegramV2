package FileReader;


import Excel.ExcelData;
import Json.JsonDataInput;

import java.io.InputStream;
import java.util.List;

public class JsonFileReaderFactory implements FileReaderFactory {
    private final JsonDataInput jsonDataInput;

    public JsonFileReaderFactory(JsonDataInput jsonDataInput) {
        this.jsonDataInput = jsonDataInput;
    }

    @Override
    public List<ExcelData> importData(InputStream inputStream) {
        return jsonDataInput.importJsonData(inputStream);
    }
}