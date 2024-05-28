package Excel;

public class ExcelData {

    public ExcelData(String firstStr, String secondStr) {
        this.first = Double.parseDouble(firstStr);
        this.second = Double.parseDouble(secondStr);
    }

    public double getFirst() {
        return first;
    }

    public void setFirst(double first) {
        this.first = first;
    }

    public double getSecond() {
        return second;
    }

    public void setSecond(double second) {
        this.second = second;
    }

    private double first;
    private double second;
}
