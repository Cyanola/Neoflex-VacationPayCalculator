package ru.neoflex.VacationPayCalculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.neoflex.VacationPayCalculator.Interface.VacationPayCalculatorInterface;
import ru.neoflex.VacationPayCalculator.Service.VacationPayCalculatorService;
import ru.neoflex.VacationPayCalculator.Strategy.StandardCalculation;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VacationPayCalculatorApplicationTests {

	@Test
	void contextLoads() {
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

}
