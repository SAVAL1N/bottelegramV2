package Json;

import Excel.ExcelData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonDataInput {

    public List<ExcelData> importJsonData(InputStream inputStream) {
        List<ExcelData> jsonDataList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            double[][] jsonArray = objectMapper.readValue(inputStream, double[][].class);
            for (double[] array : jsonArray) {
                double first = array[0];
                double second = array[1];
                jsonDataList.add(new ExcelData(String.valueOf(first), String.valueOf(second)));
            }
            System.out.println(jsonDataList);
            System.out.println("Данные из JSON успешно импортированы.");
        } catch (IOException e) {
            System.out.println("Ошибка обработки файла JSON: " + e.getMessage());
        }
        return jsonDataList;
    }
}
