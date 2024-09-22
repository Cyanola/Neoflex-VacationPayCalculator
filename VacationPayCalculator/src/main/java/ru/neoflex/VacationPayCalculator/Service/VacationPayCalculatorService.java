package ru.neoflex.VacationPayCalculator.Service;

import ru.neoflex.VacationPayCalculator.Interface.VacationPayCalculatorInterface;

public class VacationPayCalculatorService {
    private VacationPayCalculatorInterface strategy;

    public VacationPayCalculatorService(VacationPayCalculatorInterface strategy) {
        this.strategy = strategy;
    }

    public double calculate(double averageSalary, int vacationDays) {
        return strategy.calculateVacationPay(averageSalary, vacationDays);
    }
}
