Feature: Składanie zamówien

  Klient musi mieć zamawiania produktów które znajduja się w  koszyku.
  Administrator ma możliwość podejrzenia wszystkich zamowien złozonych w sklepie.
  Klienci którzy zamowili wiecej niz 3x maja znizke 10%.

  Scenario: Złozenie zamowenia
    Given Posiadajac w koszyku 1 przedmiot wart 35.00 PLN
    When Po kliknieciu kupuje i wypełnieniu danych do wysyłki
    Then Otrzyma potwierdzenie złożenia zamowienia


  Scenario: Złozenie zamowenia jako stały klient
    Given Jako staly klient posiadajac w koszyku 1 przedmiot wart 35.00 PLN
    When Po kliknieciu kupuje i wypełnieniu danych do wysyłki
    Then Jako staly klient otrzyma potwierdzenie złożenia zamowienia na kwote 31.50 PLN