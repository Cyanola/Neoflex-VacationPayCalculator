package ru.neoflex.VacationPayCalculator.Strategy;

import ru.neoflex.VacationPayCalculator.Interface.VacationPayCalculatorInterface;

public class StandardCalculation implements VacationPayCalculatorInterface {
    @Override
    public double calculateVacationPay(double averageSalary, int vacationDays) {
        // Простая формула расчета отпускных
        return (averageSalary / 30) * vacationDays;
    }
}