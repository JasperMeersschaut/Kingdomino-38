package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.Spel;
import domein.Speler;
import exceptions.AantalSpelersOngeldigException;
import utils.Kleur;

class SpelTest {
    private Spel spel;

    @BeforeEach
    void setUp() {
        spel = new Spel();
    }

    // Testen op spelers toevoegen
    @Test
    void voegSpelerToe_JuisteWaarden_VoegtSpelerToe() {
        Speler speler = new Speler("TestSpeler", 2000);
        spel.voegSpelerToe(speler, Kleur.GROEN);
        assertEquals(1, spel.getSpelers().size());
        assertEquals(speler, spel.getSpelers().get(0));
    }

    @Test
    void startSpel_3Spelers_MaaktSpel() {

        Speler speler1 = new Speler("Speler 1", 2000);
        Speler speler2 = new Speler("Speler 2", 2000);
        Speler speler3 = new Speler("Speler 3", 2000);

        spel.voegSpelerToe(speler1, Kleur.GROEN);
        spel.voegSpelerToe(speler2, Kleur.BLAUW);
        spel.voegSpelerToe(speler3, Kleur.GEEL);
        spel.startSpel();

        assertEquals(3, spel.getSpelers().size());
        assertEquals(3, spel.getStartKolom().size());
        assertEquals(33, spel.getStapel().size());
    }

    @Test
    void startSpel_4Spelers_MaaktSpel() {

        Speler speler1 = new Speler("Speler 1", 2000);
        Speler speler2 = new Speler("Speler 2", 2000);
        Speler speler3 = new Speler("Speler 3", 2000);
        Speler speler4 = new Speler("Speler 4", 2000);

        spel.voegSpelerToe(speler1, Kleur.GROEN);
        spel.voegSpelerToe(speler2, Kleur.BLAUW);
        spel.voegSpelerToe(speler3, Kleur.GEEL);
        spel.voegSpelerToe(speler4, Kleur.ROOS);
        spel.startSpel();

        assertEquals(4, spel.getSpelers().size());
        assertEquals(4, spel.getStartKolom().size());
        assertEquals(32, spel.getStapel().size());
    }

    @Test
    public void startSpel_2Spelers_ExceptionThrown() {

        Spel spel = new Spel();
        Speler speler1 = new Speler("Speler1", 2000);
        Speler speler2 = new Speler("Speler2", 2000);
        spel.voegSpelerToe(speler1, Kleur.GROEN);
        spel.voegSpelerToe(speler2, Kleur.BLAUW);

        assertThrows(AantalSpelersOngeldigException.class, () -> {
            spel.startSpel();
        });
    }
}