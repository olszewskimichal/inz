[![Build Status](https://travis-ci.org/olszewskimichal/inz.svg?branch=master)](https://travis-ci.org/olszewskimichal/inz)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=pl.michal.olszewski:inz)](https://sonarcloud.io/dashboard?id=pl.michal.olszewski%3Ainz)
[![codecov](https://codecov.io/gh/olszewskimichal/inz/branch/master/graph/badge.svg)](https://codecov.io/gh/olszewskimichal/inz)

Projekt związany z praca inżynierska pisana w 2017r. 
Optymalizacja pracy z wykorzystaniem metodologii TDD i BDD.

Aplikacja realizująca te metodologie to atrapa sklepu internetowego, który pozwala na:
* rejestracje/logowanie użytkowników
* dodawanie nowych kategorii produków
* dodawanie/edycje/usuwanie produktów
* zamawianie/kupowanie produktów z wykorzystaniem koszyka internetowego 
* realizuje podstawowe rabaty
* wyswietlanie swoich zamówień



### Uruchomienie ###
```
mvn clean package
java -jar target/*.jar  - podmienić * na rzeczywistą nazwe Jara
```

Domyslny profil z jakim uruchamiana jest aplikacja to development, czyli :
* wyłączony jest liquibase
* baza ładowana jest przy uruchomieniu z klasy DevDBConfig, która posiada 2 kategorie ("komputery","inne") oraz 2 uzytkowników ( zwykły user oraz administrator ) a także 1 produkt
* baza to H2 tworzona w scieżce `jdbc:h2:file:D:/targetDB;DB_CLOSE_ON_EXIT=FALSE` z uzytkownikiem h2 i hasłem h2
* baza jest przy kazdym uruchomieniu czyszczona i inicjalizowana
* domyślny port to 8282

Konto administratorskie
```
admin@email.pl
zaq1@WSX
```

Konto zwykłego uzytkownika
```
user@email.pl
zaq1@WSX
```

### Testy ###
Aplikacja posiada około 210 testów jednostkowych, ponad 30 testów integracyjnych oraz 28 testów akceptacyjnych (Selenium)



### Technologie zastosowane w pracy: ###
  - SpringBoot
  - liquibase
  - H2
  - assertJ
  - mockito
  - selenium
  - cucumber (BDD)
  - hikariCP
  - JaCoCO
  - Pitest
