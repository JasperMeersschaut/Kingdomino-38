# TODO

## Feedback moment 2

- UC2: ERD, relationeel model, implementeren in MySQL
  - [ ] ERD
  - [ ] Relationeel Model
  - [ ] Implementeren in SQL
- [ ] UC2: domeinklasse -> domeinregels toevoegen + TESTEN
- [ ] UC2: console uitwerken (java)

- UC3: analyse opstellen (AD, DM, SSD, OC)
  - [x] UC3: AD
    - [ ] controle
  - [x] UC3: DM
    - [ ] controle
  - [x] UC3: SSD
    - [ ] controle
  - [x] UC3: OC
    - [ ] controle

## Feedback moment 1

### visual paradigm

#### Activity diagram
- [x] Bij de keuze nodes schrijven wat de richtingen doen
    - [x] controle (keuzes zijn toegevoegd)
- [x] Eerste speler is random koning, volgende ook?
    - [x] controle (Ja dit is random volgens UC2 normaal verloop punt 8)

#### SSD

- [ ] Na loop keuze speler en kleur iets terugsturen naar de console, (toont nog eens alle kleuren en spelerlijst) tussen 2 en 3
    - [ ] controle
- [x] 3 ook in de loop
    - [x] controle (staat nu in een loop voor zolang niet alle koningen geplaatst zijn)
- [x] pre en post condities aanpassen
    - [ ] controle

#### domein model

- [x] Koninkrijk = een groep vakjes en de speler heeft een konkrijk
    - [ ] controle
- [x] aggregatie/compisisite nakijken. Dus moet waarschijnlijk gewoon weg.
    - [x] De prompts moeten een extra spatie hebben zodat de tekst niet tegen de invoer aanplakt
        - [ ] controle
    - [x] De error messages zijn vaste strings en worden niet uit de resourcebundle gehaald
        - [ ] controle

#### DCD

- [x] Scanner verwijderen bij de domeincontroller
    - [ ] controle
- [x] toonHoofdMenu() moet niet in de domeincontroller
    - [ ] controle
- [ ] Gebruik van DTO's
    - [ ] controle

#### Java

- [x] Meertaligheid implementeren in javaproject
    - [x] controle

<!-- Test -->