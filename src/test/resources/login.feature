Feature: Logowanie

  Klient po poprawnej rejestacji, musi mieć możliwość zalogowania do systemu. W przypadku poprawnego logowania zostanie
  przekierowany do strony głownej sklepu. W przypadku błednego logowania dostanie odpowiedni komunikat błedu.

  Scenario: Poprawne logowanie do systemu
    Given Logujac sie na login admin@email.pl z hasłem zaq1@WSX
    When Przy kliknieciu zaloguj
    Then Zostanie przekierowany na strone głowna sklepu

  Scenario: Błedne logowanie do systemu - niepoprawne hasło
    Given Logujac sie na login admin@email.pl z hasłem zaq1@WSX1
    When Przy kliknieciu zaloguj
    Then Zostanie wyświetlony bład Niepoprawny użytkownik lub hasło

  Scenario: Błedne logowanie do systemu - nieaktywne konto
    Given Logujac sie na login nieaktywny@email.pl z hasłem zaq1@WSX
    When Przy kliknieciu zaloguj
    Then Zostanie wyświetlony bład Twoje konto nie jest aktywne
