package domein;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;

    public Menu() {
        scanner = new Scanner(System.in);
        toonHoofdMenu();
    }

    public int toonHoofdMenu() {
        int keuze = 0;
        boolean keuzeGeldig = false;

            try {
                System.out.println("1. Registreer nieuwe speler");
                System.out.println("2. Start nieuw spel");
                System.out.println("3. Afsluiten");
                System.out.print("Geef je keuze: ");
                keuze = scanner.nextInt();
                scanner.nextLine();
                keuzeGeldig = true;
            } catch (InputMismatchException ime) {
                System.err.println("Vul een getal in!");
                scanner.nextLine();
            }
            //all catcher
            catch (Exception e) {
                System.err.println("Er is iets fout gelopen");
                scanner.nextLine();
            }
        return keuze;
    }
}
