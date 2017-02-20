Feature: Koszyk sklepu

  Klient musi mieć możliwośc dodawania produktów do koszyka, ktory automatycznie bedzie przeliczał kwotę do zapłaty oraz ilość
  zamowionych tych samych przedmiotów. Uzytkownik musi miec też możliwość usuwania elementów z koszyka


  Scenario: Dodanie 2 tych samych produktów i 1 innego do koszyka
    Given Zalogowany jako uzytkownik
    When Dodajemy 2x produkt warty 35.00 PLN oraz raz produkt warty 3.00 PLN
    Then Nasz koszyk bedzie wart 73.00 PLN oraz bedzie posiadał 2 pozycje w koszyku

  Scenario: Dodanie 3 produktów a następnie usuniecie 1 z nich
    Given Jako uzytkownik dodamy 3 produkty do naszego koszyka o lacznej wartosci 48.00 PLN
    When Usuniemy 1 produkt warty 10.00
    Then Nasz koszyk bedzie wart 38.00 PLN oraz bedzie posiadał 2 pozycje w koszyku
