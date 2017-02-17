package com.inz.praca.selenium.configuration;

import com.inz.praca.builders.UserBuilder;
import com.inz.praca.domain.Role;
import com.inz.praca.domain.User;
import com.inz.praca.integration.SeleniumProfileTestBase;
import com.inz.praca.repository.UserRepository;
import com.inz.praca.selenium.pageObjects.LoginPage;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;

import org.springframework.beans.factory.annotation.Autowired;


public abstract class SeleniumTestBase extends SeleniumProfileTestBase {
	public static BrowserConfig browserConfiguration = new BrowserConfig();

	@Autowired
	public UserRepository userRepository;

	protected LoginPage loginPage;

	public void prepareBeforeTest() throws Exception {
		if (driver == null)
			driver = browserConfiguration.firefox();
		else driver.manage().deleteAllCookies();
		userRepository.deleteAll();
		userRepository.save(new UserBuilder().withEmail("nieaktywny@email.pl").withPasswordHash("zaq1@WSX").build());
		userRepository.save(new UserBuilder().withEmail("aktywny@email.pl").withPasswordHash("zaq1@WSX").activate().build());
		User admin = new UserBuilder().withEmail("admin@email.pl").withPasswordHash("zaq1@WSX").activate().build();
		admin.setRole(Role.ADMIN);
		userRepository.save(admin);
	}

	@Rule
	public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

	public static WebDriver driver;

}
