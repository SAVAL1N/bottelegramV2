package Telegram;

public class UserData {
    private double truePositive;
    private double falsePositive;
    private double falseNegative;
    private double trueNegative;

    public UserData(double truePositive, double falsePositive, double falseNegative, double trueNegative) {
        this.truePositive = truePositive;
        this.falsePositive = falsePositive;
        this.falseNegative = falseNegative;
        this.trueNegative = trueNegative;
    }

    public double getTruePositive() {
        return truePositive;
    }

    public void setTruePositive(double truePositive) {
        this.truePositive = truePositive;
    }

    public double getFalsePositive() {
        return falsePositive;
    }

    public void setFalsePositive(double falsePositive) {
        this.falsePositive = falsePositive;
    }

    public double getFalseNegative() {
        return falseNegative;
    }

    public void setFalseNegative(double falseNegative) {
        this.falseNegative = falseNegative;
    }

    public double getTrueNegative() {
        return trueNegative;
    }

    public void setTrueNegative(double trueNegative) {
        this.trueNegative = trueNegative;
    }

    public String getTableAsString() {
        StringBuilder table = new StringBuilder();
        table.append("-----------------------------------\n");
        table.append("|").append("TP\t").append("|\t").append("FP\t").append("|\t").append("FN\t").append("|\t").append("TN\t").append("|\n");
        table.append("-----------------------------------\n");
        table.append("|").append(getTruePositive()).append("\t").append("|\t").append(getFalsePositive()).append("\t").append("|\t").append(getFalseNegative()).append("\t").append("|\t").append(getTrueNegative()).append("\t").append("|\n");
        table.append("-----------------------------------\n");
        return table.toString();
    }
}
