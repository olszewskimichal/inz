Feature: Rejestracja

  Jako nowy klient chciałbym mieć możliwośc rejestracji do systemu. Rejestracja przejdzie prawidłowo tylko w przypadku podania dobrych danych.
  Dobre dane to takie które zawieraja minimum 3literowe imie oraz nazwisko, poprawny adres email oraz trudne przynajmniej 8 znakowe z symbolem specjalnym itp hasło
  Oraz gdy zostało podane dokładnie takie same hasło w potwierdzeniu

  Scenario Outline: Próba rejestracji
    Given Podajac imie= <name> nazwisko = <lastName> email = <email> oraz hasło <password> i potwierdzeniu <confirmPassword>
    When Przy kliknieciu zarejestruj
    Then Dostane <errorCount> komunikatów błedu
    Examples:
      | name | lastName | email        | password | confirmPassword | errorCount |
      | Imie | Nazwisko | email@o2.pl  | zaq1@WSX | zaq1@WSX        | 0          |
      | Imie | Nazwisko | email2@o2.pl | zaq1@WS  | zaq1@WS         | 1          |
      | Imie | Nazwisko | email3@o2    | zaq1@WSX | zaq1@WSX        | 1          |
      | Im   | Nazwisko | email4@o2.pl | zaq1@WSX | zaq1@WSX        | 1          |
      |      |          |              |          |                 | 6          |

  Scenario: Próba stworzenia istniejącego już użytkownika
    Given Podajac imie= imie nazwisko = nazwisko email = istniejacyemail@o2.pl oraz hasło zaq1@WSX i potwierdzeniu zaq1@WSX
    When Przy kliknieciu zarejestruj
    Then Otrzymamy bład że Podany uzytkownik istnieje

  Scenario: Próba stworzenia uzytkownika ze zbyt prostym hasłem
    Given Podajac imie= imie nazwisko = nazwisko email = nowy@o2.pl oraz hasło aaaaa i potwierdzeniu aaaaa
    When Przy kliknieciu zarejestruj
    Then Otrzymamy bład że Podane hasło jest zbyt proste

  Scenario: Próba stworzenia uzytkownika ze zbyt krotkim imieniem
    Given Podajac imie= im nazwisko = nazwisko email = nowy@o2.pl oraz hasło zaq1@WSX i potwierdzeniu zaq1@WSX
    When Przy kliknieciu zarejestruj
    Then Otrzymamy bład że Podane imie jest zbyt krótkie

  Scenario: Próba stworzenia uzytkownika z pustym imieniem
    Given Podajac imie=  nazwisko = nazwisko email = nowy@o2.pl oraz hasło zaq1@WSX i potwierdzeniu zaq1@WSX
    When Przy kliknieciu zarejestruj
    Then Otrzymamy bład że Imie nie moze byc puste
  Scenario: Próba stworzenia uzytkownika z nieprawidłowym adresem email
    Given Podajac imie= imie  nazwisko = nazwisko email = nowy.pl oraz hasło zaq1@WSX i potwierdzeniu zaq1@WSX
    When Przy kliknieciu zarejestruj
    Then Otrzymamy bład że Nieprawidłowy adres email