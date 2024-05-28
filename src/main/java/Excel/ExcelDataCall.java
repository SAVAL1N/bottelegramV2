package Excel;

import java.util.List;

public class ExcelDataCall {

    public int[] calculateAndPrintResults(List<ExcelData> excelDataList, int index) {
        int[] results = null;

        // Проводим вычисления
        if (excelDataList != null && !excelDataList.isEmpty()) {
            int bolen = 0;
            int nebolen = 0;
            int Bol = 0;
            int MenD = 0;

            try {
                for (ExcelData data : excelDataList) {
                    double first = data.getFirst();
                    double second = data.getSecond();

                    if (first == 1.0) {
                        bolen++;
                        if (second <= index) {
                            Bol++;
                        }
                    }
                    if (first == 0.0) {
                        nebolen++;
                        if (second >= index) {
                            MenD++;
                        }
                    }
                }

                int TP = bolen - Bol;
                int FP = MenD;
                int FN = Bol;
                int TN = nebolen - MenD;
                int p = bolen;
                int n = nebolen;

                System.out.println("TP: " + TP);
                System.out.println("FP: " + FP);
                System.out.println("FN: " + FN);
                System.out.println("TN: " + TN);

                results = new int[]{TP, FP, FN, TN, p, n};
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No data found in Excel.");
        }

        return results;
    }
}
