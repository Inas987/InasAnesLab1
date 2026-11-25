# Fitness App – Spring Boot MVC & REST Application

**Članovi tima:** Inas Karahmet i Anes Kubat

Web aplikacija za upravljanje fitnes treninzima, trenerima i fitnes opremom, razvijena u sklopu laboratorijskih vježbi (**InasAnesLab1** i nadogradnja **Lab 2**). Aplikacija omogućava pregled dostupnih vježbi, dodjeljivanje i angažovanje trenera, simulaciju treninga s izračunom osvojenih bodova i potrošenih kalorija, kao i kompletno upravljanje fitnes opremom putem web interfejsa (MVC) i REST API-ja uz trajnu pohranu podataka u **MySQL** bazi podataka.

---

## 📋 Logički tok kreiranja i nadogradnje aplikacije

Projekat je građen postepeno kroz sljedeće faze i slojeve:

1. **Inicijalizacija i konfiguracija projekta (`pom.xml`):**
   * Kreiran je Spring Boot projekat sa Java 17 i Mavenom.
   * U `pom.xml` su uključene zavisnosti za **Spring Web**, **Thymeleaf**, **Spring Data JPA** i **MySQL Connector/J**.
   * Modeli su realizovani kroz eksplicitne konstruktore, getter i setter metode radi potpune kontrole nad entitetima i JPA mapiranjem.

2. **Izrada i proširenje domenskog modela (`model` sloj):**
   * `Trainer` – JPA entitet (tabela `trainers`) sa atributima `id`, `name`, `calorieBoost`, `staminaBoost`.
   * `Exercise` – JPA entitet (tabela `exercises`) sa atributima `id`, `name`, `caloriesBurned`, `stamina`, `points`, `completedSessions` i `@ManyToMany` vezom prema trenerima (`exercise_trainers`), uz poslovne metode za dodavanje trenera i izračun ukupnih kalorija i stamine.
   * `Equipment` (**novi model u Lab 2**) – JPA entitet (tabela `equipment`) povezan sa entitetom `Exercise`.

3. **Sloj pristupa podacima i inicijalizacija (`repository` i `data` sloj):**
   * In-memory skladište iz prvog laba (`FitnessData`) zamijenjeno je **Spring Data JPA** repozitorijima (`ExerciseRepository`, `TrainerRepository`, `EquipmentRepository`) koji nasljeđuju `JpaRepository`.
   * Kreirana klasa `DataInitializer` (`CommandLineRunner`) koja pri prvom pokretanju aplikacije automatski popunjava praznu MySQL bazu početnim trenerima, vježbama i opremom.

4. **Poslovna logika (`service` sloj):**
   * `WorkoutService` upravlja izvođenjem treninga, kalkulacijom ciljanog intenziteta i uspješnosti treninga, dodjeljivanjem osvojenih bodova te trajnim spremanjem ažuriranog stanja vježbe u MySQL bazu (`exerciseRepository.save(e)`).

5. **Kontroleri i rutiranje (`controller` sloj):**
   * `FitnessController` (`@Controller`) – upravlja osnovnim MVC rutama:
     * `/` $\rightarrow$ preusmjeravanje (`redirect`) na `/exercises`.
     * `/exercises` $\rightarrow$ prikaz svih vježbi iz baze.
     * `/trainers` i `/trainers/hire` $\rightarrow$ prikaz trenera i angažovanje trenera za odabranu vježbu.
     * `/workout` i `/workout/start` $\rightarrow$ pokretanje simulatora treninga.
   * `EquipmentController` (`@Controller`) – novi MVC kontroler za rad sa opremom putem Thymeleaf šablona.
   * `EquipmentRestController` (`@RestController`) – novi REST kontroler koji izlaže JSON API na putanji `/api/equipment`.

6. **Korisnički interfejs (`templates` sloj):**
   * U `src/main/resources/templates/` kreirani su HTML fajlovi uz Thymeleaf sintaksu (`th:each`, `th:text`, `th:action`, `th:object`, `th:field`):
     * `exercises.html`, `trainers.html`, `workout.html`
     * `equipment.html` (prikaz tabele opreme, forma za dodavanje i brisanje)
     * `equipment-edit.html` (forma za izmjenu postojeće opreme – `update`)

7. **Podešavanje skeniranja komponenti i pokretanje:**
   * Konfigurisana glavna klasa `InasAnesLab1Application` sa `@SpringBootApplication` i `@ComponentScan(basePackages = "com.example.fitnessapp")` kako bi Spring učitao sve komponente iz podpaketa.

---

## 🧩 Novi model i relacija u bazi podataka (`Equipment`)

U okviru nadogradnje za Lab 2 uveden je novi entitet **`Equipment`** (mapiran na tabelu `equipment`) koji predstavlja fitnes opremu potrebnu za izvođenje vježbi:

* `id` (`Long`) – primarni ključ (`@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`)
* `name` (`String`) – naziv opreme (npr. *Olimpijska šipka*, *Traka za trčanje*, *Strunjača*)
* `category` (`String`) – kategorija opreme (npr. *Tegovi*, *Kardio*, *Podloga*)
* `weightKg` (`double`) – težina opreme u kilogramima
* `exercise` (`Exercise`) – referenca na vježbu za koju se oprema koristi (`@JoinColumn(name = "exercise_id")`)

**Ostvarena relacija:**
* **`@ManyToOne` (N:1)** između entiteta **`Equipment`** i **`Exercise`** – više različitih komada opreme može biti dodijeljeno istoj vježbi, dok je svaki komad opreme povezan sa jednom konkretnom vježbom.

---

## ⚙️ Kontroleri za novi model

Za entitet `Equipment` implementirana su dva odvojena kontrolera:

1. **`EquipmentController` (`@Controller`, bazna ruta `/equipment`):**
   * `GET /equipment` – dohvaća svu opremu i vježbe iz baze i prikazuje ih u šablonu `equipment.html`.
   * `POST /equipment/add` – prima podatke iz HTML forme, povezuje opremu sa odabranom vježbom (`exerciseId`) i sprema je u MySQL bazu.
   * `GET /equipment/edit/{id}` – otvara formu `equipment-edit.html` popunjenu podacima odabrane opreme.
   * `POST /equipment/update/{id}` – ažurira postojeći zapis opreme u bazi podataka (`update`).
   * `POST /equipment/delete/{id}` – briše odabrani komad opreme iz baze.

2. **`EquipmentRestController` (`@RestController`, bazna ruta `/api/equipment`):**
   * `GET /api/equipment` – vraća listu sve opreme iz baze u **JSON** formatu.
   * `GET /api/equipment/{id}` – vraća jedan zapis opreme po ID-u u JSON formatu (ili HTTP `404 Not Found`).
   * `POST /api/equipment` – prima JSON objekat opreme (`@RequestBody`) i sprema ga u bazu.
   * `DELETE /api/equipment/{id}` – briše opremu po ID-u i vraća odgovarajući HTTP status.

---

## 🗄️ Konfiguracija MySQL baze podataka

Aplikacija koristi **MySQL Server 8.0** i povezuje se na bazu podataka **`fitness_db`**. Baza se automatski kreira prilikom prvog pokretanja zahvaljujući parametru `createDatabaseIfNotExist=true`, dok Hibernate automatski kreira i ažurira tabele (`exercises`, `trainers`, `exercise_trainers`, `equipment`) putem opcije `ddl-auto=update`.

Konfiguracija u `src/main/resources/application.properties`:

```properties
spring.application.name=InasAnesLab2

spring.datasource.url=jdbc:mysql://localhost:3306/fitness_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## 🛠️ Tehnologije i Biblioteke

* **Java 17**
* **Spring Boot 3.5.6**
  * Spring Boot Starter Web (MVC & REST)
  * Spring Boot Starter Thymeleaf
  * Spring Boot Starter Data JPA (Hibernate)
  * Spring Boot DevTools
* **MySQL Server 8.0 & MySQL Connector/J**
* **Maven**
* **HTML5 / CSS3**

---

## 🌐 Korišteni internet izvori i AI alati

Tokom izrade ove aplikacije i rješavanja problema pri konfiguraciji korišteni su sljedeći izvori i alati:

* **Internet izvori i dokumentacija:**
  * [Spring Boot Official Documentation](https://spring.io/projects/spring-boot) – referentna dokumentacija za Spring MVC, Spring Data JPA i REST kontrolere.
  * [Baeldung - Spring Boot Guides](https://www.baeldung.com/) – tutorijali za postavljanje kontrolera, Thymeleaf integraciju, JPA relacije (`@ManyToOne`) i Maven zavisnosti.
  * [Stack Overflow](https://stackoverflow.com/) – rješavanje grešaka pri kompajliranju i konfiguraciji (`TemplateInputException`, MySQL konekcija).

* **AI alati i integracija u IDE:**
  * **IntelliJ AI Assistant / GitHub Copilot (ugrađeni AI u IDE):** Korišten unutar IntelliJ IDEA okruženja za automatsko dopunjavanje koda (code completion), generisanje getter/setter metoda i brzu dijagnostiku grešaka u Build/Run konzoli.

---

## 🚀 Pokretanje Aplikacije

1. Klonirajte repozitorij i prebacite se na branch za Lab 2:
   ```bash
   git clone <URL_repozitorija>
   cd InasAnesLab1
   git checkout lab2-nadogradnja
   ```
2. Provjerite da je lokalni **MySQL Server** pokrenut na portu `3306` i po potrebi prilagodite `spring.datasource.username` i `spring.datasource.password` u `src/main/resources/application.properties`.
3. Pokrenite aplikaciju preko klase `InasAnesLab1Application.java` i pristupite u browseru:
   * MVC aplikacija: `http://localhost:8080/exercises` i `http://localhost:8080/equipment`
   * REST API (JSON): `http://localhost:8080/api/equipment`

---