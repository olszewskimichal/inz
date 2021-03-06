package com.inz.praca.selenium.configuration;

import com.inz.praca.integration.SeleniumProfileTestBase;
import com.inz.praca.orders.OrderRepository;
import com.inz.praca.registration.User;
import com.inz.praca.registration.UserBuilder;
import com.inz.praca.registration.UserRepository;
import com.inz.praca.selenium.pageObjects.LoginPage;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class SeleniumTestBase extends SeleniumProfileTestBase {

  private static final BrowserConfig browserConfiguration = new BrowserConfig();
  protected static WebDriver driver;
  @Autowired
  public UserRepository userRepository;
  @Autowired
  public OrderRepository orderRepository;
  protected LoginPage loginPage;

  public void prepareBeforeTest() throws Exception {
    if (SeleniumTestBase.driver == null) {
      SeleniumTestBase.driver = SeleniumTestBase.browserConfiguration.firefox();
    } else {
      SeleniumTestBase.driver.manage().deleteAllCookies();
    }
    orderRepository.deleteAll();
    userRepository.deleteAll();
    userRepository.save(new UserBuilder().withEmail("nieaktywny@email.pl").withPasswordHash("zaq1@WSX").build());
    userRepository.save(
        new UserBuilder().withEmail("aktywny@email.pl").withPasswordHash("zaq1@WSX").activate().build());
    User admin = new UserBuilder().withEmail("admin@email.pl").withPasswordHash("zaq1@WSX").activate().build();
    admin.giveAdminAuthorization();
    userRepository.save(admin);

  }

}
