# Описание приложение "Калькулятор отпускных"

## Условие задачи
Микросервис на SpringBoot + Java 11 c одним API:
GET "/calculacte"
<p>
Минимальные требования: Приложение принимает твою среднюю зарплату за 12 месяцев и количество дней отпуска - отвечает суммой отпускных, которые придут сотруднику.
Доп. задание: При запросе также можно указать точные дни ухода в отпуск, тогда должен проводиться рассчет отпускных с учётом праздников и выходных.</p>

## Выполнение задания
### Паттерн "Стратегия"
<details>
    <summary><ins><b>Теоретическая справка</b></ins></summary>
    <p>
        <b>Паттерн "Стратегия"</b> — это поведенческий паттерн проектирования, который определяет семейство схожих алгоритмов и помещает каждый из них в собственный класс, после чего алгоритмы можно взаимозаменять прямо во время исполнения программы.
     </p>
<hr/>
    <img src="VacationPayCalculator/src/main/resources/Images/img.png" alt="Пример стратегии" />
</details>

> [!IMPORTANT]
> Реализация интерфейса `VacationPayCalculatorInterface`:

```java
public interface VacationPayCalculatorInterface {
    double calculateVacationPay(double averageSalary, int vacationDays);
}
```
> [__Содержимое класса контроллера__](VacationPayCalculator/src/main/java/ru/neoflex/VacationPayCalculator/Controller/VacationPayController.java) `VacationPayController`<br>
> [__Реализация класса стандартного расчета отпускных__](VacationPayCalculator/src/main/java/ru/neoflex/VacationPayCalculator/Strategy/StandardCalculation.java) `StandardCalculation`<br>
>  [__Реализация класса расчета отпускных с учетом праздничных и выходных дней__](VacationPayCalculator/src/main/java/ru/neoflex/VacationPayCalculator/Strategy/HolidayCalculation.java) `HolidayCalculation`<br>
>  [__Реализация класса-навигатора__](VacationPayCalculator/src/main/java/ru/neoflex/VacationPayCalculator/Service/VacationPayCalculatorService.java) `Класс-навигатор`

## Unit-тесты
Тест на ввод отрицательного количества дней отпуска:
```java
	@Test
	public void testNegativeVacationDays() {
		VacationPayCalculatorInterface strategy = new StandardCalculation();
		double result = strategy.calculateVacationPay(60000, -5);
		assertEquals(0, result); 
	}
```
Тест на стандартный расчет отпускных:
```java
@Test
	public void testCalculateVacationPay() {
		// Arrange
		VacationPayCalculatorInterface strategy = new StandardCalculation();
		double averageSalary = 120000; // Средняя зарплата за 12 месяцев
		int vacationDays = 10; // Количество дней отпуска
		double result = strategy.calculateVacationPay(averageSalary, vacationDays);
		assertEquals(40000, result, 0.01,"Ожидается корректный расчет отпускных");
	}
```
Тест на расчет отпускных с праздниками и выходными:
```java
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
```
