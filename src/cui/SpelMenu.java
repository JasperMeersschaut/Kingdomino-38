package cui;

import DTO.AlleSpelersDTO;
import DTO.SpelerDTO;
import domein.DomeinController;
import utils.Kleur;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class SpelMenu {
    List<SpelerDTO> spelers = new ArrayList<>();
    DomeinController dc = new DomeinController();
    Scanner scanner = new Scanner(System.in);

    public void spelerToevoegen() {
        int i = 0;
        String naam;
        Kleur kleur;
        toonBeschikbareSpelers();
        do {
            i++;
            System.out.println("Geef de naam van de speler die je wilt toevoegen: ");
            naam = scanner.nextLine();
            System.out.println("Geef de kleur van de speler die je wilt toevoegen: ");
            kleur = Kleur.valueOf(scanner.nextLine());
            spelers.add(new SpelerDTO(naam, kleur));
            dc.toonBeschikbareSpelers().remove(naam);
            if (i < 4 && i > 2) {
                System.out.println("Wil je nog een speler toevoegen? (ja/nee)");
                String antwoord = scanner.nextLine();
                if (antwoord.equals("nee")) {
                    i = 4;
                }
            }
        } while (i >= 4);
      dc.startSpel(spelers);
    }


    private void toonBeschikbareSpelers() {
        System.out.println("Beschikbare bestaande speler spelers: ");
        List<AlleSpelersDTO> spelers = dc.toonBeschikbareSpelers();
        for (AlleSpelersDTO speler : spelers) {
            System.out.println(speler.gebruikersnaam());
        }
    }
}
