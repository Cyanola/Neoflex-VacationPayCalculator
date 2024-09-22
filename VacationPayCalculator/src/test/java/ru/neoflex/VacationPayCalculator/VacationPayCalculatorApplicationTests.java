package ru.neoflex.VacationPayCalculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.neoflex.VacationPayCalculator.Interface.VacationPayCalculatorInterface;
import ru.neoflex.VacationPayCalculator.Service.VacationPayCalculatorService;
import ru.neoflex.VacationPayCalculator.Strategy.HolidayCalculation;
import ru.neoflex.VacationPayCalculator.Strategy.StandardCalculation;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VacationPayCalculatorApplicationTests {

	@Test
	public void testNegativeVacationDays() {
		VacationPayCalculatorInterface strategy = new StandardCalculation();

		// Если передаем отрицательное значение дней отпуска, ожидаем 0 или исключение
		double result = strategy.calculateVacationPay(60000, -5);
		assertEquals(0, result); // Логично возвращать 0, так как отпускных быть не может
	}
	@Test
	public void testCalculateVacationPay() {
		// Arrange
		VacationPayCalculatorInterface strategy = new StandardCalculation();
		double averageSalary = 120000; // Средняя зарплата за 12 месяцев
		int vacationDays = 10; // Количество дней отпуска

		// Act
		double result = strategy.calculateVacationPay(averageSalary, vacationDays);

		// Assert
		// Ожидаемый результат: (120000 / 30) * 10 = 40000
		assertEquals(40000, result, 0.01,"Ожидается корректный расчет отпускных");
	}
	@Test
	public void testVacationOnlyOnHolidaysAndWeekends() {
		List<LocalDate> holidays = List.of(
				LocalDate.of(2024, 1, 1),  // Новый год
				LocalDate.of(2024, 5, 1)   // День труда
		);

		HolidayCalculation strategy = new HolidayCalculation(holidays);

		// Все указанные дни - праздники или выходные
		List<LocalDate> vacationDates = List.of(
				LocalDate.of(2024, 1, 1),  // Праздник
				LocalDate.of(2024, 1, 6),  // Суббота
				LocalDate.of(2024, 1, 7)   // Воскресенье
		);

		int workingDays = strategy.calculateWorkingDays(vacationDates);
		assertEquals(0, workingDays); // Нет рабочих дней
	}
	@Test
	public void testLargeValues() {
		VacationPayCalculatorInterface strategy = new StandardCalculation();

		// Очень большая зарплата и большое количество дней отпуска
		double result = strategy.calculateVacationPay(1_000_000, 365);
		assertEquals(12_166_666.67, result, 0.01); // Пример результата для большого значения
	}
}
