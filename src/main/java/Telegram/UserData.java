package Telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class UserData {
    private double truePositive;
    private double falsePositive;
    private double falseNegative;
    private double trueNegative;

    public UserData(double truePositive, double falsePositive, double falseNegative, double trueNegative, double prevalence) {
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
        table.append("|\t\t\tTP\t=\t").append(this.truePositive).append("\t\t|").append("\t\t\tFP\t=\t").append(this.falsePositive).append("\t\t\t|\n");
        table.append("-----------------------------------\n");
        table.append("|\t\t\tFN\t=\t").append(this.falseNegative).append("\t|").append("\t\t\tTN\t=\t").append(this.trueNegative).append("\t\t\t|\n");
        table.append("-----------------------------------\n");
        return table.toString();
    }
}
