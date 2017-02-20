Feature: Składanie zamówien

  Klient musi mieć zamawiania produktów które znajduja się w  koszyku.
  Administrator ma możliwość podejrzenia wszystkich zamowien złozonych w sklepie.

  Scenario: Złozenie zamowenia
    Given Posiadajac w koszyku 1 przedmiot wart 35.00 PLN
    When Po kliknieciu kupuje i wypełnieniu danych do wysyłki
    Then Otrzyma potwierdzenie złożenia zamowienia

