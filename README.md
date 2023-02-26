<div id="top"></div>

<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

<!-- PROJECT LOGO -->
<br />
<div align="center">


<h3 align="center">LIBRARY-JAKARTA-EE-PROJECT</h3>

  <p align="center">
    Jakarte ee, restful web services tasks for Fundamentals of Network Applications course in TUL.
   
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-the-project">About the project</a></li>
    <li><a href="#built-with">Built With</a></li>
    <li><a href="#Roles-and-permissions">Roles and permissions</a></li>
    <li><a href="#Additional-feature-description">Additional feature description</a></li>
    <li><a href="#business-rules">Business rules</a></li>
    <li><a href="#endpoints">Endpoints</a></li>
    <li><a href="#Todo">Todo</a></li>
    <li><a href="#license">License</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project
  This project was made for fundamentals of network application class in TUL. It provides CRUD
  operations for library API - rentable items (books and articles), rents and users. Given actions are based on
  user permissions (more information below). Authentication and authorization are build using JWT and
  Jakarta EE Security (IdentityStoreHandler/HttpAuthenticationMechanism). MySQL is used for data storage and test are built
  with Rest Assured library. Also TLS is utilized for secure communication.

<p align="right">(<a href="#top">back to top</a>)</p>

## Built With

* [JDK 17](https://docs.microsoft.com/en-us/dotnet/csharp/)
* [Jakarta EE](https://jakarta.ee/)
* [Hibernate](https://hibernate.org/orm/documentation/)
* [MySQL](https://dev.mysql.com/doc/)
* [Docker](https://docs.docker.com/)
* [Payara Server](https://docs.payara.fish/)
* [Rest Assured](https://rest-assured.io/)
* [Lombok](https://www.projectlombok.org/features/all)



<p align="right">(<a href="#top">back to top</a>)</p>


## Roles and permissions
### Client can
- register an account (need to provide name, login, password, address and client type)
- login to account
- change password
- make a rent with given rentable items
- get rentable items list (algo separately books and articles)


### Manager (besides all client permissions) can:
- make crud operations for rentable items
- filter users by name or login
- end client rent


### Admin (besides all client and manager permissions) can:
- make crud operations for all user types (manager and admin
user types can only be added by admin - not register)
- activate / deactivate client account

<p align="right">(<a href="#top">back to top</a>)</p>


## Additional feature description
- There are three types of client:
  - university employee
  - student
  - outsider
- Different client types have individual:
  -  limit for current rented items, 
  -  max rent duration and
  -  penalty for overdue items

<p align="right">(<a href="#top">back to top</a>)</p>

## Business rules
- Rents can only be made for client (not managers nor admins)
- deactivated account cannot make rents
- login cannot be duplicated
- not available item cannot be rented
- already ended rent cannot be ended

<p align="right">(<a href="#top">back to top</a>)</p>

## Endpoints


### Client register (managers and admins can only be added by admin)
```
POST https://localhost:8181/pas/api/auth/register
Content-Type: application/json

{
  "name": "John",
  "surname": "Doe",
  "login": "login123",
  "password": "password123",
  "clientType": "STUDNET",
  "address": {
    "street": "sampleStreet",
    "city": "sampleCity",
    "number": "52a"
  }
}

RESPONSE: HTTP 201 (Created)
{
  User created successfully
}

```

### Login

```
POST https://localhost:8181/pas/api/auth/login
Content-Type: application/x-www-form-urlencoded

login=admin123&password=admin123

RESPONSE: HTTP 200
{
  eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.... rest of JWT
}
```

### Add new book (only admin or manager)

```
POST https://localhost:8181/pas/api/rentable-item/book
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9... rest of admin JWT

{
    "serialNumber": "12312312312", 
    "author": "Homer", 
    "title": "Odyssey", 
    "publishingHouse": "Signum"
}

RESPONSE: HTTP 201 (Created)
{
    "author": "Homer",
    "available": true,
    "serialNumber": "12312312312",
    "title": "Odyssey",
    "publishingHouse": "Signum"
}
```

### Get all rentable items
```
https://localhost:8181/pas/api/rentable-item
Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9... rest of JWT

[
    {
        "author": "Juliusz Słowacki",
        "available": false,
        "rentableItemId": 1,
        "serialNumber": "1111111111111",
        "title": "Balladyna",
        "publishingHouse": "PWN"
    },
    {
        "author": "J.R.R. Tolkien",
        "available": false,
        "rentableItemId": 2,
        "serialNumber": "2222222222222",
        "title": "Władca pierścieni",
        "publishingHouse": "Muza"
    },
    {
        "author": "J.K Rowling",
        "available": false,
        "rentableItemId": 3,
        "serialNumber": "3333333333333",
        "title": "Harry Potter i Zakon Feniksa",
        "publishingHouse": "Media"
    },
  other results...
```

### Add new rent

```
POST https://localhost:8181/pas/api/rent
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.... rest of JWT

{
    "clientId": "3", 
    "rentableItemIds": [
        5
    ]
}

RESPONSE: HTTP 201 (Created)
{
    "clientId": 3,
    "rentableItemIds": [
        5
    ]
}
```

### Of course there is also exception handling for unauthorized/unauthenticated/incorrect data, for example

### Client try to get all users info

```
GET https://desktop-namfmud:8181/pas/api/user
Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.... rest of user JWT

RESPONSE: HTTP 200
{
  403 | Forbidden | Caller not in requested role
}
```

### Missing field when adding new rentable item

```
POST https://localhost:8181/pas/api/rentable-item/book
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9... rest of admin JWT

{
    "serialNumber": "12312312312", 
    "author": "Homer", 
    "publishingHouse": "Signum"
}

RESPONSE: HTTP 400
{
  createBook.arg0.title: nie może być puste
}
```

### Rentable item is rented

```
POST https://localhost:8181/pas/api/rent
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9... rest of JWT

{
    "clientId": "3", 
    "rentableItemIds": [
        1, 2
    ]
}

RESPONSE: HTTP 400
{
  400 | Bad Request | RentableItem is rented
}
```


<p align="right">(<a href="#top">back to top</a>)</p>

## Todo

- [ ] add refresh token
- [ ] add security constraints to current tests
- [ ] add swagger for documentation

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>







<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/pStrachota/LIBRARY-JAKARTA-EE-PROJECT.svg?style=for-the-badge
[contributors-url]: https://github.com/pStrachota/LIBRARY-JAKARTA-EE-PROJECT/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/pStrachota/LIBRARY-JAKARTA-EE-PROJECT.svg?style=for-the-badge
[forks-url]: https://github.com/pStrachota/LIBRARY-JAKARTA-EE-PROJECT/network/members
[stars-shield]: https://img.shields.io/github/stars/pStrachota/LIBRARY-JAKARTA-EE-PROJECT.svg?style=for-the-badge
[stars-url]: https://github.com/pStrachota/LIBRARY-JAKARTA-EE-PROJECT/stargazers
[issues-shield]: https://img.shields.io/github/issues/pStrachota/LIBRARY-JAKARTA-EE-PROJECT.svg?style=for-the-badge
[issues-url]: https://github.com/pStrachota/LIBRARY-JAKARTA-EE-PROJECT/issues
[license-shield]: https://img.shields.io/github/license/pStrachota/LIBRARY-JAKARTA-EE-PROJECT.svg?style=for-the-badge
[license-url]: https://github.com/pStrachota/LIBRARY-JAKARTA-EE-PROJECT/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/linkedin_username
[product-screenshot]: images/screenshot.png



