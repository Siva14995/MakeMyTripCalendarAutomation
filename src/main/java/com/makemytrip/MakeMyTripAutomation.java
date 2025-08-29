package com.makemytrip;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MakeMyTripAutomation {

	public static void main(String[] args) {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-debugging-port=63292");
		options.addArguments("--disable-notifications");
		options.addArguments("--start-maximized");
		WebDriver driver = new ChromeDriver(options);
		driver.get("https://www.makemytrip.com/");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		By closeModalButtonLocator = By.xpath("//span[@data-cy='closeModal']");
		WebElement closeModalButton = wait.until(ExpectedConditions.elementToBeClickable(closeModalButtonLocator));
		closeModalButton.click();

		By forCityLabelLocator = By.xpath("//label[@for=\"fromCity\"]");
		WebElement forCityLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(forCityLabelLocator));
		forCityLabel.click();

		By forCityInputTextBoxLocator = By.xpath("//input[@placeholder='From']");
		WebElement forCityInputTextBox = wait
				.until(ExpectedConditions.visibilityOfElementLocated(forCityInputTextBoxLocator));
		forCityInputTextBox.sendKeys("Pune");

		By fromCitySuggestionListLocator = By.xpath(
				"//p[contains(text(),\"SUGGESTIONS \")]/ancestor::div[contains(@class,\"react-autosuggest\")]/ul/li");

		boolean state = wait.until(
				ExpectedConditions.and(ExpectedConditions.visibilityOfElementLocated(fromCitySuggestionListLocator),
						ExpectedConditions.numberOfElementsToBeLessThan(fromCitySuggestionListLocator, 12)));

		List<WebElement> forCitySuggestionList = null;

		if (state) {
			forCitySuggestionList = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(fromCitySuggestionListLocator));
		}

		for (WebElement suggestion : forCitySuggestionList) {
			System.out.println(suggestion.getText());
		}
		forCitySuggestionList.get(0).click(); // Selecting the first city from the list!!

		By toCityLabelLocator = By.xpath("//label[@for='toCity']");
		WebElement toCityLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(toCityLabelLocator));
		toCityLabel.click();

		By toCityInputTextBoxLocator = By.xpath("//input[@placeholder=\"To\"]");
		WebElement toCityTextBox = wait.until(ExpectedConditions.visibilityOfElementLocated(toCityInputTextBoxLocator));
		toCityTextBox.sendKeys("Hyderabad");

		By toSuggestionListLocator = By.xpath(
				"//p[contains(text(),\"SUGGESTIONS\")]/ancestor::div[contains(@class,\"react-autosuggest\")]/ul/li");

		state = wait.until(
				ExpectedConditions.and(ExpectedConditions.visibilityOfAllElementsLocatedBy(toSuggestionListLocator),
						ExpectedConditions.numberOfElementsToBeMoreThan(toSuggestionListLocator, 10)));

		List<WebElement> toSuggestionList = null;
		if (state) {
			toSuggestionList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(toSuggestionListLocator));
		}

		toSuggestionList.get(0).click(); // Selecting the first city from the list!! [to location]

		LocalDate targetdate = LocalDate.now();
		targetdate = targetdate.plusDays(5);
		String targetMonth = targetdate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH); 
		int targetYear = targetdate.getYear(); // 2025
		int targetDay = targetdate.getDayOfMonth(); // 21

		By calendarMonthLocator = By.xpath("//div[contains(text(),'" + targetMonth + " " + targetYear
				+ "')]/ancestor::div[@class=\"DayPicker-Month\"]");

		WebElement calendarMonth = wait.until(ExpectedConditions.visibilityOfElementLocated(calendarMonthLocator));

		By dateLocator = By.xpath(".//p[text()='" + targetDay + "']/ancestor::div[contains(@class,\"DayPicker-Day\")]");
		WebElement date = calendarMonth.findElement(dateLocator);
		date.click();

		driver.quit();

	}

}
