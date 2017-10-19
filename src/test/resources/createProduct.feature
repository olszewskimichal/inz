Feature: Tworzenie nowych produktów

  Bedąc zalogowanym jako użytkownik z uprawnieniami administratora, chciałbym mieć możliwość tworzenia produktów.
  Produkt zostanie stworzony gdy zostanie podana nazwa, opis i poprawna cena oraz wybrana jedna z kategorii

  Scenario Outline: Próba tworzenia produktu
    Given Podajac nazwe= <name> z opisem = <description> cena = <price> oraz wybrana kategoria <category>
    When Przy kliknieciu dodaj produkt
    Then Otrzymamy <errorCount> komunikatów błedu, w przypadku 0 błedów - zostanie stworzony nowy produkt
    Examples:
      | name   | description | price | category | errorCount |
      | nazwa1 | opis        | 3.0   | inne     | 0          |
      | nazwa2 | opis2       | 2,5   | inne     | 1          |
      | nazwa3 | o           | -3    | inne3    | 1          |
      | na     | o           | a     | c        | 2          |

  Scenario: Próba tworzenia produktu z nieprawidłowa cena
    Given Podajac nazwe= nazwa z opisem = opis cena = -4.0 oraz wybrana kategoria inne
    When Przy kliknieciu dodaj produkt
    Then Otrzymamy komunikat Nieprawidłowo podana cena

  Scenario: Próba tworzenia produktu z za krotka nazwa
    Given Podajac nazwe= na z opisem = opis cena = 4.0 oraz wybrana kategoria inne
    When Przy kliknieciu dodaj produkt
    Then Otrzymamy komunikat Podana nazwa jest zbyt krótka
