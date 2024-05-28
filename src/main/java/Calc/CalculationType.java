package Calc;

public enum CalculationType {
    SENSITIVITY_AND_SPECIFICITY(1, "Чувствительность и специфичность", (tp, fp, fn, tn) -> {
        double sensitivity = tp / (tp + fn);
        double specificity = tn / (tn + fp);
        return (sensitivity + specificity) / 2;
    }),
    PREVALENCE(2, "Распространенность", (tp, fp, fn, tn) -> (tp + fn) / (tp + fp + fn + tn)),
    PPV(3, "Прогностическая ценность положительного результата", (tp, fp, fn, tn) -> tp / (tp + fp)),
    FOR(4, "Коэффициент ложных пропусков (FOR)", (tp, fp, fn, tn) -> fn / (fn + tn)),
    ACCURACY(5, "Диагностическая эффективность теста (достоверность) (ACC)", (tp, fp, fn, tn) -> (tp + tn) / (tp + fp + fn + tn)),
    FDR(6, "Коэффициент ложного обнаружения (FDR)", (tp, fp, fn, tn) -> fp / (tp + fp)),
    NPV(7, "Прогностическая ценность отрицательного результата (NPV)", (tp, fp, fn, tn) -> tn / (tn + fn)),
    BALANCED_ACCURACY(8, "Сбалансированная достоверность (точность) (BA)", (tp, fp, fn, tn) -> {
        double sensitivity = tp / (tp + fn);
        double specificity = tn / (tn + fp);
        return (sensitivity + specificity) / 2;
    }),
    FOWLKES_MALLOWS_INDEX(9, "Индекс Фоулкса–Мэллова (FM)", (tp, fp, fn, tn) -> Math.sqrt((tp * tn) / ((tp + fp) * (tp + fn)))),
    F1_SCORE(10, "Оценка F1", (tp, fp, fn, tn) -> {
        double precision = tp / (tp + fp);
        double recall = tp / (tp + fn);
        return 2 * (precision * recall) / (precision + recall);
    }),
    INFORMEDNESS(11, "Информированность (BM)", (tp, fp, fn, tn) -> {
        double sensitivity = tp / (tp + fn);
        double specificity = tn / (tn + fp);
        return sensitivity + specificity - 1;
    }),
    PREVALENCE_THRESHOLD(12, "Порог распространённости (PT)", (tp, fp, fn, tn) -> {
        double sensitivity = tp / (tp + fn);
        double specificity = tn / (tn + fp);
        return (Math.sqrt(sensitivity * (1 - specificity)) - (1 - specificity)) / (sensitivity - (1 - specificity));
    }),
    TPR(13, "Доля истинно положительных результатов, чувствительность (SEN)", (tp, fp, fn, tn) -> tp / (tp + fn)),
    FNR(14, "Доля ложноотрицательных результатов (FNR)", (tp, fp, fn, tn) -> fn / (tp + fn)),
    FPR(15, "Доля ложноположительных результатов (FPR)", (tp, fp, fn, tn) -> fp / (tn + fp)),
    TNR(16, "Доля истинно отрицательных результатов, специфичность (TNR)", (tp, fp, fn, tn) -> tn / (tn + fp)),
    LR_PLUS(17, "Положительное значение коэффициента правдоподобия (LR+)", (tp, fp, fn, tn) -> {
        double sensitivity = tp / (tp + fn);
        double specificity = tn / (tn + fp);
        return sensitivity / (1 - specificity);
    }),
    LR_MINUS(18, "Отрицательное значение коэффициента правдоподобия (LR−)", (tp, fp, fn, tn) -> {
        double sensitivity = tp / (tp + fn);
        double specificity = tn / (tn + fp);
        return (1 - sensitivity) / specificity;
    }),
    MARKEDNESS(19, "Маркированность (MK)", (tp, fp, fn, tn) -> {
        double ppv = tp / (tp + fp);
        double npv = tn / (tn + fn);
        return ppv + npv - 1;
    }),
    DOR(20, "Диагностическое отношение шансов (DOR)", (tp, fp, fn, tn) -> {
        double lrPlus = (tp / (tp + fn)) / (fp / (tn + fp));
        double lrMinus = (fn / (tp + fn)) / (tn / (tn + fp));
        return lrPlus / lrMinus;
    }),
    MCC(21, "Коэффициент корреляции\n" +
            "Метьюза (MCC)", (tp, fp, fn, tn) -> {
        return (tp * tn - fp * fn) / Math.sqrt((tp + fp) * (tp + fn) * (tn + fp) * (tn + fn));
    }),
    THREAT_SCORE(22, "Оценка угрозы (TS), индекс критического успеха (CSI), индекс Жаккара", (tp, fp, fn, tn) -> tp / (tp + fp + fn)),
    YOUDENS_J(23, "Индекс Юдена (J)", (tp, fp, fn, tn) -> {
        double sensitivity = tp / (tp + fn);
        double specificity = tn / (tn + fp);
        return sensitivity + specificity - 1;
    });


    private final int id;
    private final String name;
    private final StatCalculation calculation;

    CalculationType(int id, String name, StatCalculation calculation) {
        this.id = id;
        this.name = name;
        this.calculation = calculation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public StatCalculation getCalculation() {
        return calculation;
    }

    public double calculate(double tp, double fp, double fn, double tn) {
        return calculation.calculate(tp, fp, fn, tn);
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
