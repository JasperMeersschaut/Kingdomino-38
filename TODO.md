# TODO

## UC1: Registreer nieuwe speler

### normaal verloop

- [x] 1. De gebruiker wenst zich te registreren (Jasper Meersschaut)
     > Eerst [DR_HOOFDMENU](#dr_hoofdmenu) aanmaken. Hierbij krijgt de gebruiker de keuze om een nieuwe speler aan te maken.
     >
     > - [ ] Controle? ()

- [x] 2. Het systeem vraagt gebruikersnaam en geboortejaar. (Jasper Meersschaut)
     > - [ ] Controle? ()
- [x] 3. De speler geeft de gegevens in. (Jasper Meersschaut)
     > - [ ] Controle? ()
- [x] 4. Het systeem valideert ([DR_NIEUWE_SPELER](#dr_nieuwe_speler)). ()
     > Zorgen dat de gegevens niet meteen in de constructor worden gestoken maar dat ze eerst gevalideerd worden door de setters.

- [x] 5. Het systeem registreert de speler. ()
- [x] 6.  Het systeem toont een bevestiging en de beschikbare keuzemogelijkheden volgens [DR_HOOFDMENU](#dr_hoofdmenu). ()

### Alternatieve verlopen:

- [x] 4A. Het systeem detecteert dat niet voldaan is aan [DR_NIEUWE_SPELER](#dr_nieuwe_speler) ()
- [x] 4A1. Het systeem geeft een gepaste melding. ()
- [x] 4A2. Keer terug naar stap 2 van het [normale verloop](#normaal-verloop).
  > Het systeem vraagt opnieuw voor de gegevens. ()

### DR_NIEUWE_SPELER
- [x] Gebruikersnaam is uniek en is minstens 6 karakters lang
  > - [x] Controle? (Sam Ramaswami)
- [x] Gebruikersnaam mag niet enkel uit spaties bestaan
  > - [x] Controle? (Sam Ramaswami)
- [x] Geboortejaar moet geldig zijn en elke gebruiker is minstens 6 jaar oud
  > - [x] Controle? (Sam Ramaswami, extra checks toegevoegd.)
- [x] Aantal overwinningen is 0, aantal gespeelde spelletjes is 0
  > - [x] Controle? (Sam Ramaswami)

### DR_HOOFDMENU 
- [x] Volgend menu wordt getoond:

```
1. Registreer nieuwe speler
2. Start nieuw spel
3. Afsluiten
```

> - [ ] Controle? ()

### Nog te doen voor eerste contactmoment

- [ ] Jasper moet nog een commit op VP maken
- [ ] Iedereen moet opvolgingsformulier nog invullen
- [ ] Java speler geboortejaarcheck aanpassen
  - Controleert nu eerst of speler jonger is dan 6, gooit exception en kijkt daarna of speler jonger is dan 0, wat ook jonger dus 6. In dat geval zou al een exception geworpen moeten zijn en kan dit dus niet voorvallen (is dus overbodig). geboortejaar kan wel nog altijd negatief zijn of in de middeleeuwen liggen.
  - Ideetje: we veranderen bij de 2de controle `huidigJaar - geboortejaar < 0` naar `huidigJaar - geboortejaar > 150`. Dan kan niemand ouder zijn dan 150 jaar. Als je dan een negatief geboortejaar ingeeft gaat er sowieso ook een exception geworpen worden want volgens onze controle zou je dan 2000+ jaar oud zijn en dat is dus > 150.
  - de `else if` mag een gewone `if` worden, die else is overbodig

### Ideetjes om nog te doen

- [ ] Java project in een apart mapje stoppen (bv. java), zo is onze github pagina ordelijker. 
  - Dan heb je een database mapje, een java mapje, de gitignore, readme en de todo. Dan staan die .classpath en .project files niet in de weg. Nadeel is wel dat iedereen waarschijnlijk bij eclips het projectfolder gaat moeten aanpassen omdat de src map nog 1 mapje dieper zit dan daarvoor. Wat vinden jullie daarvan?
  - Indien we dit gaan doen gaan we waarschijnlijk ook de gitignore file moeten aanpassen
- [ ] Op VP elk diagram in een apart mapje steken. Dus in hoofdmap 3 mappen (AD, DM , SSDenOC) en dan in elke map een submap met Use Case X als naam, of in de hoofdmap telkens een Use Case X map met in elke map een (AD, DM en SSDenOC) map
  - is makkelijker om bij te houden en ordelijker voor de lectoren
  - Het is nu al supper onoverzichtelijk en we hebben nog maar 1 use case geanalyseerd