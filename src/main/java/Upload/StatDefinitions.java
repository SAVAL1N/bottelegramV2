package Upload;

public class StatDefinitions {
    public interface StatCalculation {
        String getName();
        int getId();
        double calculate(double tp, double fp, double fn, double tn);
        default String toString(double tp, double fp, double fn, double tn) {
            return getName() + ": " + String.format("%.2f", calculate(tp, fp, fn, tn));
        }
    }

    public enum CalculationType {
        SENSITIVITY_AND_SPECIFICITY(1, "Sensitivity and Specificity"),
        PREVALENCE(2, "Prevalence"),
        PPV(3, "Positive predictive value (PPV)"),
        FOR(4, "False omission rate (FOR)"),
        ACCURACY(5, "Accuracy (ACC)"),
        FDR(6, "False discovery rate (FDR)"),
        NPV(7, "Negative predictive value (NPV)"),
        BALANCED_ACCURACY(8, "Balanced accuracy (BA)"),
        FOWLKES_MALLOWS_INDEX(9, "Fowlkes–Mallows index (FM)"),
        F1_SCORE(10, "F1 score"),
        INFORMEDNESS(11, "Informedness, bookmaker informedness (BM)"),
        PREVALENCE_THRESHOLD(12, "Prevalence threshold (PT)"),
        TPR(13, "True positive rate (TPR), recall, sensitivity (SEN)"),
        FNR(14, "False negative rate (FNR)"),
        FPR(15, "False positive rate (FPR)"),
        TNR(16, "True negative rate (TNR)"),
        LR_PLUS(17, "Positive likelihood ratio (LR+)"),
        LR_MINUS(18, "Negative likelihood ratio (LR−)"),
        MARKEDNESS(19, "Markedness (MK)"),
        DOR(20, "Diagnostic odds ratio (DOR)"),
        MCC(21, "Matthews correlation coefficient (MCC)"),
        THREAT_SCORE(22, "Threat score (TS), critical success index (CSI), Jaccard index");

        private final int id;
        private final String name;

        CalculationType(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public static CalculationType fromId(int id) {
            for (CalculationType type : values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return null;
        }
    }
}
