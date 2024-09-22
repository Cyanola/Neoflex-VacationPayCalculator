package ru.neoflex.VacationPayCalculator.Strategy;

import ru.neoflex.VacationPayCalculator.Interface.VacationPayCalculatorInterface;

import java.time.LocalDate;
import java.util.List;

public class HolidayCalculation implements VacationPayCalculatorInterface {
    private List<LocalDate> holidays;

    public HolidayCalculation(List<LocalDate> holidays) {
        this.holidays = holidays;
    }

    @Override
    public double calculateVacationPay(double averageSalary, int vacationDays) {
        // Здесь можно добавить логику для исключения выходных и праздников
        // Предполагается, что vacationDays уже учитывает только рабочие дни
        return (averageSalary / 30) * vacationDays;
    }

    public int calculateWorkingDays(List<LocalDate> vacationDates) {
        return (int) vacationDates.stream()
                .filter(date -> !isHolidayOrWeekend(date))
                .count();
    }

    private boolean isHolidayOrWeekend(LocalDate date) {
        return holidays.contains(date) || date.getDayOfWeek().getValue() > 5; // 6 и 7 - это выходные
    }
}
