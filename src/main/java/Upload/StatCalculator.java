package Upload;

import Telegram.UserData;
import Upload.StatDefinitions.StatCalculation;
import Upload.StatDefinitions.CalculationType;

import java.util.EnumMap;
import java.util.Map;

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
        initCalculations();
    }

    private final Map<CalculationType, StatCalculation> calculations = new EnumMap<>(CalculationType.class);

    public StatCalculator(UserData userData) {
    }

    private void initCalculations() {
        calculations.put(CalculationType.SENSITIVITY_AND_SPECIFICITY, new StatCalculation() {
            @Override
            public String getName() {
                return "Sensitivity and Specificity";
            }

            @Override
            public int getId() {
                return 1;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                double sensitivity = tp / (tp + fn);
                double specificity = tn / (tn + fp);
                return (sensitivity + specificity) / 2;
            }
        });

        calculations.put(CalculationType.PREVALENCE, new StatCalculation() {
            @Override
            public String getName() {
                return "Prevalence";
            }

            @Override
            public int getId() {
                return 2;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                return (tp + fn) / (tp + fp + fn + tn);
            }
        });

        calculations.put(CalculationType.PPV, new StatCalculation() {
            @Override
            public String getName() {
                return "Positive predictive value (PPV)";
            }

            @Override
            public int getId() {
                return 3;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                return tp / (tp + fp);
            }
        });

        calculations.put(CalculationType.FOR, new StatCalculation() {
            @Override
            public String getName() {
                return "False omission rate (FOR)";
            }

            @Override
            public int getId() {
                return 4;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                return fn / (fn + tn);
            }
        });

        calculations.put(CalculationType.ACCURACY, new StatCalculation() {
            @Override
            public String getName() {
                return "Accuracy (ACC)";
            }

            @Override
            public int getId() {
                return 5;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                return (tp + tn) / (tp + fp + fn + tn);
            }
        });

        calculations.put(CalculationType.FDR, new StatCalculation() {
            @Override
            public String getName() {
                return "False discovery rate (FDR)";
            }

            @Override
            public int getId() {
                return 6;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                return fp / (tp + fp);
            }
        });

        calculations.put(CalculationType.NPV, new StatCalculation() {
            @Override
            public String getName() {
                return "Negative predictive value (NPV)";
            }

            @Override
            public int getId() {
                return 7;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                return tn / (tn + fn);
            }
        });

        calculations.put(CalculationType.BALANCED_ACCURACY, new StatCalculation() {
            @Override
            public String getName() {
                return "Balanced accuracy (BA)";
            }

            @Override
            public int getId() {
                return 8;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                double sensitivity = tp / (tp + fn);
                double specificity = tn / (tn + fp);
                return (sensitivity + specificity) / 2;
            }
        });

        calculations.put(CalculationType.FOWLKES_MALLOWS_INDEX, new StatCalculation() {
            @Override
            public String getName() {
                return "Fowlkes–Mallows index (FM)";
            }

            @Override
            public int getId() {
                return 9;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                return Math.sqrt((tp / (tp + fp)) * (tp / (tp + fn)));
            }
        });

        calculations.put(CalculationType.F1_SCORE, new StatCalculation() {
            @Override
            public String getName() {
                return "F1 score";
            }

            @Override
            public int getId() {
                return 10;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                double precision = tp / (tp + fp);
                double recall = tp / (tp + fn);
                return 2 * (precision * recall) / (precision + recall);
            }
        });

        calculations.put(CalculationType.INFORMEDNESS, new StatCalculation() {
            @Override
            public String getName() {
                return "Informedness, bookmaker informedness (BM)";
            }

            @Override
            public int getId() {
                return 11;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                double sensitivity = tp / (tp + fn);
                double specificity = tn / (tn + fp);
                return sensitivity + specificity - 1;
            }
        });

        calculations.put(CalculationType.PREVALENCE_THRESHOLD, new StatCalculation() {
            @Override
            public String getName() {
                return "Prevalence threshold (PT)";
            }

            @Override
            public int getId() {
                return 12;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                double sensitivity = tp / (tp + fn);
                double specificity = tn / (tn + fp);
                return (Math.sqrt(sensitivity * (1 - specificity)) - (1 - specificity)) / (sensitivity - (1 - specificity));
            }
        });

        calculations.put(CalculationType.TPR, new StatCalculation() {
            @Override
            public String getName() {
                return "True positive rate (TPR), recall, sensitivity (SEN)";
            }

            @Override
            public int getId() {
                return 13;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                return tp / (tp + fn);
            }
        });

        calculations.put(CalculationType.FNR, new StatCalculation() {
            @Override
            public String getName() {
                return "False negative rate (FNR)";
            }

            @Override
            public int getId() {
                return 14;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                return fn / (tp + fn);
            }
        });

        calculations.put(CalculationType.FPR, new StatCalculation() {
            @Override
            public String getName() {
                return "False positive rate (FPR)";
            }

            @Override
            public int getId() {
                return 15;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                return fp / (fp + tn);
            }
        });

        calculations.put(CalculationType.TNR, new StatCalculation() {
            @Override
            public String getName() {
                return "True negative rate (TNR)";
            }

            @Override
            public int getId() {
                return 16;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                return tn / (fp + tn);
            }
        });

        calculations.put(CalculationType.LR_PLUS, new StatCalculation() {
            @Override
            public String getName() {
                return "Positive likelihood ratio (LR+)";
            }

            @Override
            public int getId() {
                return 17;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                double sensitivity = tp / (tp + fn);
                double specificity = tn / (tn + fp);
                return sensitivity / (1 - specificity);
            }
        });

        calculations.put(CalculationType.LR_MINUS, new StatCalculation() {
            @Override
            public String getName() {
                return "Negative likelihood ratio (LR−)";
            }

            @Override
            public int getId() {
                return 18;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                double sensitivity = tp / (tp + fn);
                double specificity = tn / (tn + fp);
                return (1 - sensitivity) / specificity;
            }
        });

        calculations.put(CalculationType.MARKEDNESS, new StatCalculation() {
            @Override
            public String getName() {
                return "Markedness (MK)";
            }

            @Override
            public int getId() {
                return 19;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                double ppv = tp / (tp + fp);
                double npv = tn / (tn + fn);
                return ppv + npv - 1;
            }
        });

        calculations.put(CalculationType.DOR, new StatCalculation() {
            @Override
            public String getName() {
                return "Diagnostic odds ratio (DOR)";
            }

            @Override
            public int getId() {
                return 20;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                double lrPlus = (tp / (tp + fn)) / (fp / (fp + tn));
                double lrMinus = (fn / (tp + fn)) / (tn / (fp + tn));
                return lrPlus / lrMinus;
            }
        });

        calculations.put(CalculationType.MCC, new StatCalculation() {
            @Override
            public String getName() {
                return "Matthews correlation coefficient";
            }

            @Override
            public int getId() {
                return 21;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                return (tp * tn - fp * fn) / Math.sqrt((tp + fp) * (tp + fn) * (tn + fp) * (tn + fn));
            }
        });

        calculations.put(CalculationType.THREAT_SCORE, new StatCalculation() {
            @Override
            public String getName() {
                return "Threat score (TS), critical success index (CSI), Jaccard index";
            }

            @Override
            public int getId() {
                return 22;
            }

            @Override
            public double calculate(double tp, double fp, double fn, double tn) {
                return tp / (tp + fn + fp);
            }
        });
    }

    public String calculateStat(CalculationType type) {
        StatCalculation calculation = calculations.get(type);
        if (calculation != null) {
            return calculation.toString(tp, fp, fn, tn);
        } else {
            return "Calculation type not found.";
        }
    }

    public String calculateStats(String userInput) {
        StringBuilder result = new StringBuilder();
        String[] inputs = userInput.split(" ");
        for (String input : inputs) {
            try {
                int typeId = Integer.parseInt(input.trim());
                if (typeId == 23) {
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

    public String getCalculationList() {
        StringBuilder result = new StringBuilder("Укажите какие вычисления нам необходимы:\n");
        for (CalculationType type : CalculationType.values()) {
            result.append(type.getId()).append(" - ").append(type.getName()).append("\n");
        }
        result.append("23 - Print All\n");
        return result.toString();
    }
}
