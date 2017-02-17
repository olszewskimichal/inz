Feature: Aktywacja uzytkownika

  Uzytkownik po zarejestrowaniu ma posiadać nieaktywne konto. Konto może aktywować tylko użytkownik
  który ma uprawnienia administratora. Przy próbie logowania na konto nieaktywne użytkownik musi otrzymać odpowiedni komunikat.

  Scenario: Próba logowania nieaktywnego uzytkownika
    Given Logujac sie na uzytkownika nieaktywnego
    When Po kliknieciu zaloguj
    Then Zostanie zwrocony komunikat Twoje konto nie jest aktywne

  Scenario: Próba logowania aktywnego uzytkownika
    Given Logujac sie na zwyklego uzytkownika aktywnego
    When Po kliknieciu zaloguj
    Then Poprawnie zaloguje się do systemu

  Scenario: Aktywacja konta przez administratora
    Given Po zalogowaniu na konto administratora, ma dostęp do panelu administracji uzytkowników
    When Może aktywowac uzytkownika nieaktywnego
    Then Uzytkownik bedzie mogl sie zalagowac do systemu

  Scenario: Próba aktywacji konta przez zwyklego uzytkownika
    Given Logujac sie na zwyklego uzytkownika aktywnego
    When Próbujac wejsc na panel administracji uzytkownikami
    Then Nie bedzie mial uprawnien, otrzyma komunikat zabroniony