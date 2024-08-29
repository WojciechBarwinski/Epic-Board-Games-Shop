# Epic Board Game Shop

## Opis Projektu

Epic Board Game Shop to aplikacja umożliwiająca zarządzanie zamówieniami oraz produktami w sklepie stacjonarnym z 
grami planszowymi. Klient przychodzi do sklepu, gdzie wraz ze sprzedawcą tworzą zamówienie, 
które następnie jest przetwarzane przez aplikację. 
Program integruje się z zewnętrznym systemem magazynowym [Legendary Warehouse](https://github.com/WojciechBarwinski/legendary-warehouse), 
który symuluje pracę magazynu.

## Funkcjonalności

- **Pełna obsługa procesu zamówienia**: Od złożenia zamówienia, przez potwierdzenie, wysłanie maila, przyjęcie płatności, 
po przesłanie zamówienia do Legendary Warehouse.
- **Security**: Autentykacji (JWT) pracowników sklepu oraz właściciela.
- **Walidacja Danych**: Walidacja danych wejściowych zarówno przy składaniu zamówienia, jak i danych odbieranych z Legendary Warehouse.
- **Zarządzanie danymi**: Pełny CRUD zamówienia.

## Technologia

- **Język**: Java
- **Framework**: Spring Boot (JPA, Security, Web)
- **Baza Danych**: MySQL
- **Mapowanie Obiektów**: MapStruct
- **Walidacja**: Hibernate Validator
- **Testy**: JUnit5, Mockito, Hamcrest
- **Inne**: Liquibase, Feign, Cron, CriteriaQuery, Docker


## Instalacja i Uruchomienie
1. git clone https://github.com/WojciechBarwinski/epic-board-games-shop.git
2. cd epic-board-games-shop
3. mvn clean install
4. mvn spring-boot:run
5. program zawiera docker-compose.yml do utworzenia wymaganych kontenerów
6. wymagany [Legendary Warehouse](https://github.com/WojciechBarwinski/legendary-warehouse) na porcie 8082


## Plany na przyszłość

W przyszłych wersjach aplikacji planujemy dodać:

1. Dodanie płatności korzystając z sandboxa PayU
2. Autoryzacja
3. Zarządzanie produktami oraz danymi pracowników
4. Audytowanie zamówień, pracowników oraz produktów
5. Szyfrowanie linków do usuwania/opłacania zamówienia