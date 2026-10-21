# Fitness App - Spring Boot MVC Application

Ovo je web aplikacija za upravljanje fitnes treninzima i trenerima, razvijena u sklopu laboratorijskih vježbi (**InasAnesLab1**). Aplikacija omogućava pregled dostupnih vježbi, dodjeljivanje i angažovanje trenera te simulaciju treninga s izračunom osvojenih bodova i potrošenih kalorija.

---

## 📋 Logički tok kreiranja aplikacije

Projekat je građen postepeno kroz sljedeće faze i slojeve:

1. **Inicijalizacija i konfiguracija projekta:**
   * Kreiran je Spring Boot projekat sa Java 17 i Mavenom.
   * U `pom.xml` su uključene zavisnosti za **Spring Web** i **Thymeleaf**.
   * Onemogućen/uklonjen je Lombok kako bi se izbjegli konflikti pri kompajliranju i obezbijedila potpuna kontrola nad modelima kroz eksplicitne konstruktore, getter i setter metode.

2. **Izrada domenskog modela (`model` sloj):**
   * Definisana klasa `Trainer` (id, ime, iskustvo, cijena/bodovi).
   * Definisana klasa `Exercise` (id, naziv, bodovi, kalorije, lista dodijeljenih trenera) uz metode za dodavanje trenera i provjeru uslova.

3. **Upravljanje podacima (`data` sloj):**
   * Kreirana klasa `FitnessData` označena sa `@Component` kao in-memory skladište podataka.
   * Inicijalizovane su početne kolekcije vježbi i trenera sa identifikacijskim sekvencama (`exerciseSeq`, `trainerSeq`).
   * Implemetirana poslovna logika za povezivanje trenera sa vježbama (`hireTrainer`) uz provjeru bodovnog salda.

4. **Poslovna logika (`service` sloj):**
   * Kreiran `WorkoutService` koji upravlja izvođenjem treninga, kalkulacijom uspješnosti i dodjeljivanjem osvojenih bodova.

5. **Kontroler i rutiranje (`controller` sloj):**
   * Implemented `FitnessController` sa `@Controller` anotacijom.
   * Definisane rute i mapiranja:
     * `/` $\rightarrow$ preusmjeravanje (redirect) na `/exercises`.
     * `/exercises` $\rightarrow$ prikaz svih vježbi.
     * `/trainers` $\rightarrow$ forma i akcija za angažovanje trenera.
     * `/workout` $\rightarrow$ pokretanje simulatora treninga.

6. **Korisnički interfejs (`templates` sloj):**
   * U `src/main/resources/templates/` kreirani HTML fajlovi uz Thymeleaf sintaksu (`th:each`, `th:text`, `th:action`):
     * `exercises.html`
     * `trainers.html`
     * `workout.html`

7. **Podešavanje skeniranja komponenti i pokretanje:**
   * Konfigurisana glavna klasa `InasAnesLab1Application` sa `@SpringBootApplication` i `@ComponentScan("com.example.fitnessapp")` kako bi Spring uspešno učitao sve komponente iz podpaketa.

---

## 🛠️ Tehnologije i Biblioteke

* **Java 17**
* **Spring Boot 3.5.6**
  * Spring Boot Starter Web (MVC)
  * Spring Boot Starter Thymeleaf
  * Spring Boot DevTools
* **Maven**
* **HTML5 / CSS3**

---

## 🌐 Korišteni internet izvori i AI alati

Tokom izrade ove aplikacije i rješavanja problema pri konfiguraciji korišteni su sljedeći izvori i alati:

* **Internet izvori i dokumentacija:**
  * [Spring Boot Official Documentation](https://spring.io/projects/spring-boot) – referentna dokumentacija za Spring MVC i Dependency Injection.
  * [Baeldung - Spring Boot Guides](https://www.baeldung.com/) – tutorijali za postavljanje kontrolera, Thymeleaf integraciju i ručno rješavanje Maven zavisnosti.
  * [Stack Overflow](https://stackoverflow.com/) – rješavanje grešaka pri kompajliranju (`ExceptionInInitializerError`, 404 Whitelabel Error).

* **AI alati i integracija u IDE:**
  * **IntelliJ AI Assistant / GitHub Copilot (ugrađeni AI u IDE):** Korišten unutar IntelliJ IDEA okruženja za automatsko dopunjavanje koda (code completion), generisanje getter/setter metoda i brz dohvatanje opisa grešaka u Build Output-u.

---

## 🚀 Pokretanje Aplikacije

1. Klonirajte repozitorij:
   ```bash
   git clone [https://github.com/TVOJ_USERNAME/InasAnesLab1.git](https://github.com/TVOJ_USERNAME/InasAnesLab1.git)


   Slike aplikacije :https://postimg.cc/gallery/bsPvJpN