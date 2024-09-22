package ru.neoflex.VacationPayCalculator.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.VacationPayCalculator.Interface.VacationPayCalculatorInterface;
import ru.neoflex.VacationPayCalculator.Service.VacationPayCalculatorService;
import ru.neoflex.VacationPayCalculator.Strategy.HolidayCalculation;
import ru.neoflex.VacationPayCalculator.Strategy.StandardCalculation;

import java.time.LocalDate;
import java.util.List;

/**
// * Контроллер расчета отпускных
 *
 * @version 1.0
 * @author Artemova Olga
 */

@RestController
@RequestMapping("/calculate")
public class VacationPayController {

    VacationPayCalculatorService context;
    /**
    @param averageSalary средняя зарплата
     @param vacationDays количество дней отпуска
     @param vacationDates точные дни отпуска
     @return отпускные
     **/

    @GetMapping
    public double calculateVacationPay(@RequestParam double averageSalary,
                                       @RequestParam int vacationDays,
                                       @RequestParam(required = false) List<LocalDate> vacationDates) {


        if (vacationDates == null || vacationDates.isEmpty()) {
            // Используем стандартную стратегию
            context = new VacationPayCalculatorService(new StandardCalculation());
        } else {
            // Используем стратегию с учетом праздников и выходных
            List<LocalDate> holidays = List.of( /* список праздничных дат */ );
            HolidayCalculation holidayStrategy = new HolidayCalculation(holidays);
            int workingDays = holidayStrategy.calculateWorkingDays(vacationDates);
            context = new VacationPayCalculatorService(holidayStrategy);
            vacationDays = workingDays;
        }

        return context.calculate(averageSalary, vacationDays);
    }
}