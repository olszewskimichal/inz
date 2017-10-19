Feature: Tworzenie nowej kategori

  Bedąc zalogowanym jako użytkownik z uprawnieniami administratora, chciałbym mieć możliwość dodawania kategorii.
  Kategoria zostanie stworzona gdy zostanie podana nazwa, opis. Nazwa musi być unikalna!

  Scenario Outline: Próba tworzenia kategorii
    Given Mając nazwe kategorii <name> z opisem <description>
    When Po kliknieciu dodaj kategorie
    Then Otrzymamy <errorCount> komunikatów błedu, w przypadku 0 błedów - zostanie stworzona nowa kategoria
    Examples:
      | name   | description | errorCount |
      | nazwa1 | opisDlugi   | 0          |
      | nazwa1 | opis        | 1          |
      | n      | o           | 2          |

  Scenario: Próba tworzenia kategorii z za ktotka nazwa
    Given Mając nazwe kategorii na z opisem description
    When Po kliknieciu dodaj kategorie
    Then Dostaniemy bład = Podana nazwa jest zbyt krótka

  Scenario: Próba tworzenia kategorii z za ktotkim opisem
    Given Mając nazwe kategorii nazwa z opisem  opis
    When Po kliknieciu dodaj kategorie
    Then Dostaniemy bład = Podany opis jest za krótki


