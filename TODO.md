# TODO

## UC1: Registreer nieuwe speler

### normaal verloop

- [x] 1. De gebruiker wenst zich te registreren
     > Eerst [DR_HOOFDMENU](#dr_hoofdmenu) aanmaken. Hierbij krijgt de gebruiker de keuze om een nieuwe speler aan te maken.

- [x] 2. Het systeem vraagt gebruikersnaam en geboortejaar.
- [x] 3. De speler geeft de gegevens in.
- [ ] 4. Het systeem valideert ([DR_NIEUWE_SPELER](#dr_nieuwe_speler)).
     > Zorgen dat de gegevens niet meteen in de constructor worden gestoken maar dat ze eerst gevalideerd worden door de setters.

- [ ] 5. Het systeem registreert de speler.
- [ ] 6.  Het systeem toont een bevestiging en de beschikbare keuzemogelijkheden volgens
      [DR_HOOFDMENU](#dr_hoofdmenu).

### Alternatieve verlopen:

- [ ] 4A. Het systeem detecteert dat niet voldaan is aan [DR_NIEUWE_SPELER](#dr_nieuwe_speler)
- [ ] 4A1. Het systeem geeft een gepaste melding.
- [ ] 4A2. Keer terug naar stap 2 van het [normale verloop](#normaal-verloop).
> Het systeem vraagt opnieuw voor de gegevens.

### DR_NIEUWE_SPELER

- [x] Gebruikersnaam is uniek en is minstens 6 karakters lang
  > - [ ] Controle?
- [x] Gebruikersnaam mag niet enkel uit spaties bestaan
  > - [ ] Controle?
- [x] Geboortejaar moet geldig zijn en elke gebruiker is minstens 6 jaar oud
  > - [ ] Controle?
- [x] Aantal overwinningen is 0, aantal gespeelde spelletjes is 0
  > - [ ] Controle?

### DR_HOOFDMENU

- [X] Volgend menu wordt getoond:

```
1. Registreer nieuwe speler
2. Start nieuw spel
3. Afsluiten
```
