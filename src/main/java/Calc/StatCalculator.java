package Calc;

public class StatCalculator {
    private double tp;
    private double fp;
    private double fn;
    private double tn;
    private double p;
    private double n;

    public StatCalculator(double tp, double fp, double fn, double tn, double p, double n) {
        this.tp = tp;
        this.fp = fp;
        this.fn = fn;
        this.tn = tn;
        this.p = p;
        this.n = n;
    }

    public String calculateStat(CalculationType type) {
        return type.getId() + ". " + type.getName() + ": " + String.format("%.2f", type.calculate(tp, fp, fn, tn));
    }

    public String calculateStats(String userInput) {
        StringBuilder result = new StringBuilder();
        String[] inputs = userInput.split(" ");
        for (String input : inputs) {
            try {
                int typeId = Integer.parseInt(input.trim());
                if (typeId == 24) {
                    return calculateAllStats();
                }
                CalculationType type = CalculationType.fromId(typeId);
                if (type != null) {
                    result.append(calculateStat(type)).append("\n");
                } else {
                    result.append("Invalid calculation type: ").append(input).append("\n");
                }
            } catch (NumberFormatException e) {
                result.append("Invalid input: ").append(input).append("\n");
            }
        }
        return result.toString();
    }

    public String calculateAllStats() {
        StringBuilder result = new StringBuilder();
        for (CalculationType type : CalculationType.values()) {
            result.append(calculateStat(type)).append("\n");
        }
        return result.toString();
    }
}
