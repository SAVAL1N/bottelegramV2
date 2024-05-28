package Calc;

@FunctionalInterface
public interface StatCalculation {
    double calculate(double tp, double fp, double fn, double tn);
}
