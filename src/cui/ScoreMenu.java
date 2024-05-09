
package cui;

import java.util.List;
import java.util.ResourceBundle;

import domein.DomeinController;
import dto.SpelerDTO;
import utils.Taal;

public class ScoreMenu {

	private final ResourceBundle messages;
	private final DomeinController dc;

	/**
	 * Constructor voor het ScoreMenu.
	 *
	 * @param dc de domeincontroller.
	 */
	public ScoreMenu(DomeinController dc) {
		messages = ResourceBundle.getBundle("messages", Taal.geefTaal());
		this.dc = dc;
		toonScoreOverzicht();
	}

	private void toonScoreOverzicht() {
		StringBuilder scoreOverzicht = new StringBuilder();
		try {
			List<List<Integer>> scores = dc.geefAantalGespeeldeEnAantalGewonnenSpellen();
			List<SpelerDTO> winnaars = dc.geefWinnaars();
			List<SpelerDTO> spelers = dc.geefSpelers();
			int grootsteLengte = 0;
			for (SpelerDTO speler : spelers)
				if (speler.gebruikersnaam().length() > grootsteLengte)
					grootsteLengte = speler.gebruikersnaam().length();
			System.out.print(toonMenuTitel(messages.getString("scores")));
			System.out.printf("%8s%20s%20s%19s%n", messages.getString("players"), messages.getString("amount_played"),
					messages.getString("amount_won"), messages.getString("winner"));
			for (int index = 0; index < spelers.size(); index++)
				scoreOverzicht.append(String.format("%s%s%12s%20s%28s%n",
						" ".repeat(grootsteLengte - spelers.get(index).gebruikersnaam().length()),
						spelers.get(index).gebruikersnaam(), scores.get(0).get(index), scores.get(1).get(index),
						winnaars.contains(spelers.get(index)) ? messages.getString("won") : ""));
			scoreOverzicht.append("\n");
		}
		catch (RuntimeException re) {
			System.err.println(messages.getString("no_connection"));
			scoreOverzicht = new StringBuilder();
		}
		catch (Exception e) {
			System.err.println(messages.getString("error_occurred"));
			scoreOverzicht = new StringBuilder();
		}
		System.out.println(scoreOverzicht);
	}

	private String toonMenuTitel(String menuTitel) {
		String titel = menuTitel + "\n";
		titel += "=".repeat(menuTitel.length()) + "\n";
		return titel;
	}

}
