package com.inz.praca.registration;

import static com.inz.praca.validators.EmailValidator.EMAIL_PATTERN;
import static com.inz.praca.validators.PasswordValidator.PASS_REGEX_PATTERN;
import static javax.persistence.FetchType.EAGER;

import com.inz.praca.login.Role;
import com.inz.praca.orders.Order;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

@Entity
@Getter
@ToString
@Slf4j
@NoArgsConstructor(force = true)
@Table(name = "USERS")
public class User {

  @Column(unique = true)
  private final String email;
  private final String name;
  private final String lastName;
  private final String passwordHash;
  private Boolean active;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = EAGER)
  @JoinColumn(name = "USER_ORDER")
  private Set<Order> orders;

  @Enumerated(EnumType.ORDINAL)
  private Role role;

  @Id
  @GeneratedValue
  private Long id;

  public User(String email, String name, String lastName, String passwordHash, Boolean active) {
    Assert.hasLength(passwordHash, "Nie może być puste hasło");
    Assert.hasLength(name, "Imie nie może być puste");
    Assert.hasLength(lastName, "Nazwisko nie może być puste");
    Assert.notNull(email, "Email nie może być pusty");
    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Assert.isTrue(pattern.matcher(email).matches(), "To nie jest prawidłowy adres Email");
    pattern = Pattern.compile(PASS_REGEX_PATTERN);
    Assert.isTrue(pattern.matcher(passwordHash).matches(), "Podane hasło jest zbyt proste");
    this.email = email;
    this.name = name;
    this.lastName = lastName;
    this.passwordHash = new BCryptPasswordEncoder().encode(passwordHash);
    role = Role.USER;
    orders = new HashSet<>();
    this.active = active;
  }

  public Set<Order> getOrders() {
    if (orders == null) {
      orders = new HashSet<>();
    }
    return Collections.unmodifiableSet(orders);
  }

  public void deactivate() {
    active = false;
  }

  public void active() {
    active = true;
  }

  Boolean isActivated() {
    return active;
  }

  public void giveAdminAuthorization() {
    role = Role.ADMIN;
  }

  public void addOrder(Order order) {
    orders.add(order);
  }
}
