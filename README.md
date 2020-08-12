# Spring-MVC-REST
Aplikacja zapewnia funkcjonalność beckendową do katalogowanie książek i autorów metodą REST.
Do stworzenia API wykorzystano m.in.: Spring MVC, Spring Data, Jackson, Hibernate Core i Hibernate Validator.
Aplikacja zawiera dwa kontrolery: BookController oraz AuthorController. 
Kontrolery te wykorzystują serwisy implementujące przetwarzanie danych z użyciem bazy MySQl. 
Dane z REST API przed zapisem do bazy poddawane są walidacji. 

##BookController akcje: 
####Pobieranie listy danych: GET [serverName]/books
Zwraca listę wszystkich książek dostępnych w bazie danych.
Przykładowy JSON:
````json
[
    {
        "id": "4",
        "isbn": "ISBN: 9781617294945",
        "title": "Hibernate in Action",
        "author": {
            "id": "12",
            "firstName": "CHRISTIAN",
            "lastName": "BAUER",
            "fullName": "CHRISTIAN BAUER"
        },
        "authorName": "CHRISTIAN BAUER",
        "publisher": "ISBN: 9781617294945",
        "type": "programming"
    },
    {
        "id": "5",
        "isbn": "978-161-729-045-9",
        "title": "Effective Java",
        "author": {
            "id": "13",
            "firstName": "Joshua ",
            "lastName": "Bloch",
            "fullName": "Joshua  Bloch"
        },
        "authorName": "Joshua Bloch",
        "publisher": "978-161-729-045-9",
        "type": "programming"
    },
    {
        "id": "6",
        "isbn": "ISBN: 9781617294945",
        "title": "Spring in Action",
        "author": {
            "id": "14",
            "firstName": "CRAIG ",
            "lastName": "WALLS",
            "fullName": "CRAIG  WALLS"
        },
        "authorName": "CRAIG WALLS",
        "publisher": "ISBN: 9781617294945",
        "type": "programming"
    }
]
````
####Pobieranie obiektu po wskazanym identyfikatorze: GET [serverName]/id 
Zwraca wybraną książkę na podstawie przekazanego parametru id .
Przykładowy JSON:
#####1
````json
{
    "id": "4",
    "isbn": "ISBN: 9781617294945",
    "title": "Hibernate in Action",
    "author": {
        "id": "12",
        "firstName": "CHRISTIAN",
        "lastName": "BAUER",
        "fullName": "CHRISTIAN BAUER"
    },
    "authorName": "CHRISTIAN BAUER",
    "publisher": "ISBN: 9781617294945",
    "type": "programming"
}
````
####Dodawanie obiektu: POST [serverName]/books
Dodaje przekazaną w formacie JSON książkę do bazy danych.
Jeżeli autor przypisany do książki nie istnieje w bazie danych, 
zostanie do niej zapisany.
Przykładowy JSON:
````json
 {
        "id": "5",
        "isbn": "978-161-729-045-9",
        "title": "Effective Java",
        "authorName": "Joshua Bloch",
        "publisher": "978-161-729-045-9",
        "type": "programming"
    }
````
####Edycje obiektu: PUT [serverName]/books/id
Wyszukuje, a następnie modyfikuje wybraną książkę na 
podstawie przekazanego id i danych w formacie JSON.
zostanie do niej zapisany.
Przykładowy JSON
````json
{
        "id": "0",
        "isbn": "978-161-729-045-9",
        "title": "Effective Java",
        "authorName": "Joshua Bloch",
        "publisher": "978-161-729-045-9",
        "type": "programming"
    }
````
####Usuwanie obiektu: DELETE [serverName]/books/id
Wyszuka, a następnie usunie książkę na podstawie
przekazanego id. 
