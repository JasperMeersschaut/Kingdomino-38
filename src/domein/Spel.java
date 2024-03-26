
package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import dto.DomeinDTO;
import dto.SpelerDTO;
import dto.TegelDTO;
import exceptions.AantalSpelersOngeldigException;
import exceptions.TegelAlGekozenException;
import exceptions.TegelNietInKolomException;
import utils.Kleur;
import utils.TegelMaker;

public class Spel {

	private ResourceBundle messages;
	private List<SpelerDTO> spelers;
	private List<TegelDTO> stapel;
	private List<TegelDTO> startKolom;
	private List<TegelDTO> eindKolom;
	private SpelerDTO huidigeSpeler;
	private final TegelMaker tegelMaker;

	public Spel() {
		messages = ResourceBundle.getBundle("messages", Locale.getDefault());
		spelers = new ArrayList<>();
		spelers.add(new SpelerDTO("Kjellvdb", Kleur.GROEN, new Vak[5][5]));
		spelers.add(new SpelerDTO("jasper", Kleur.GEEL, new Vak[5][5]));
		spelers.add(new SpelerDTO("emielvdb", Kleur.BLAUW, new Vak[5][5]));
		spelers.add(new SpelerDTO("Anoniem", Kleur.ROOS, new Vak[5][5]));
		stapel = new ArrayList<>();
		startKolom = new ArrayList<>();
		eindKolom = new ArrayList<>();
		tegelMaker = new TegelMaker();
		for (Tegel tegel : tegelMaker.geeftegels())
			stapel.add(new TegelDTO(tegel, null, null));
	}

	public void voegSpelerToe(SpelerDTO speler) {
		spelers.add(speler);
	}

	public void startSpel() {
		if (spelers.size() != 3 && spelers.size() != 4)
			throw new AantalSpelersOngeldigException(messages.getString("invalid_amount_of_players"));
		Collections.shuffle(stapel);
		stapel = stapel.subList(0, spelers.size() * 3); // DE 3 moet een 12 zijn
		for (int index = 1; index <= spelers.size(); index++) {
			startKolom.add(stapel.get(0));
			stapel.remove(0);
		}
		Collections.sort(startKolom);
		Collections.shuffle(spelers);
		huidigeSpeler = spelers.get(0);
	}

	public List<SpelerDTO> geefSpelers() {
		return spelers;
	}

	public SpelerDTO geefHuidigeSpeler() {
		return huidigeSpeler;
	}

	public List<TegelDTO> geefStapel() {
		return stapel;
	}

	public List<TegelDTO> geefStartKolom() {
		return startKolom;
	}

	public List<TegelDTO> geefEindKolom() {
		return eindKolom;
	}

	public void plaatsKoningOpTegel(int gekozenTegel) {
		boolean plaatsInStartKolom = eindKolom.isEmpty();
		TegelDTO tegel;
		boolean aangepast = false;
		int indexHuidigeSpeler = -1;
		for (int index = 0; index < spelers.size(); index++) {
			tegel = (plaatsInStartKolom ? startKolom.get(index) : eindKolom.get(index));
			if (tegel.nummer() == gekozenTegel)
				if (tegel.gebruikersnaam() == null) {
					aangepast = true;
					if (plaatsInStartKolom)
						startKolom.set(index,
								new TegelDTO(
										new Tegel(tegel.nummer(), new Vak(tegel.landschapLinks(), tegel.aantalKronenLinks()),
												new Vak(tegel.landschapRechts(), tegel.aantalKronenRechts())),
										huidigeSpeler.gebruikersnaam(), huidigeSpeler.kleur()));
					else {
						eindKolom.set(index,
								new TegelDTO(
										new Tegel(tegel.nummer(), new Vak(tegel.landschapLinks(), tegel.aantalKronenLinks()),
												new Vak(tegel.landschapRechts(), tegel.aantalKronenRechts())),
										huidigeSpeler.gebruikersnaam(), huidigeSpeler.kleur()));
						for (int jndex = 0; jndex < spelers.size(); jndex++)
							if (startKolom.get(jndex).gebruikersnaam() != null
									&& startKolom.get(jndex).gebruikersnaam().equals(huidigeSpeler.gebruikersnaam())) {
								startKolom.set(jndex,
										new TegelDTO(new Tegel(tegel.nummer(),
												new Vak(tegel.landschapLinks(), tegel.aantalKronenLinks()),
												new Vak(tegel.landschapRechts(), tegel.aantalKronenRechts())), null, null));
								indexHuidigeSpeler = jndex;
								break;
							}
					}
				} else
					throw new TegelAlGekozenException(
							String.format(messages.getString("tile_already_taken"), tegel.gebruikersnaam()));
		}
		if (!aangepast)
			if (plaatsInStartKolom)
				throw new TegelNietInKolomException(messages.getString("tile_not_in_startcolumn"));
			else
				throw new TegelNietInKolomException(messages.getString("tile_not_in_endcolumn"));
		if (plaatsInStartKolom)
			huidigeSpeler = spelers
					.get(spelers.indexOf(huidigeSpeler) == spelers.size() - 1 ? 0 : spelers.indexOf(huidigeSpeler) + 1);
		else
			if (indexHuidigeSpeler != spelers.size() - 1)
				for (int index = 0; index < spelers.size(); index++)
					if (startKolom.get(indexHuidigeSpeler + 1).gebruikersnaam()
							.equals(spelers.get(index).gebruikersnaam())) {
						huidigeSpeler = spelers.get(index);
						break;
					}
	}

	public void vulEindKolomAan() {
		if (!eindKolom.isEmpty()) {
			startKolom.clear();
			startKolom.addAll(eindKolom);
			eindKolom.clear();
		}
		for (int index = 1; index <= spelers.size(); index++) {
			eindKolom.add(stapel.get(0));
			stapel.remove(0);
		}
		Collections.sort(eindKolom);
		for (int index = 0; index < spelers.size(); index++)
			if (startKolom.get(0).gebruikersnaam().equals(spelers.get(index).gebruikersnaam())) {
				huidigeSpeler = spelers.get(index);
				break;
			}
	}

	public int berekenScore(SpelerDTO speler) {
		int score = 0;
		Vak[][] koninkrijk = speler.koninkrijk();
		boolean[][] bezocht = new boolean[koninkrijk.length][koninkrijk[0].length];
		for (int i = 0; i < bezocht.length; i++)
			for (int j = 0; j < bezocht[i].length; j++)
				bezocht[i][j] = false;
		DomeinDTO domein = new DomeinDTO(0, 0, bezocht);
		for (int i = 0; i < koninkrijk.length; i++)
			for (int j = 0; j < koninkrijk[i].length; j++)
				if (koninkrijk[i][j] != null && !domein.bezocht()[i][j]) {
					domein = dfs(i, j, koninkrijk, new DomeinDTO(0, 0, domein.bezocht()));
					score += domein.aantalVanZelfdeType() * domein.aantalKronenInDomein();
				}
		return score;
	}

	private DomeinDTO dfs(int x, int y, Vak[][] koninkrijk, DomeinDTO domein) {
		boolean[][] bezocht = domein.bezocht();
		bezocht[x][y] = true;
		Vak huidigVak = koninkrijk[x][y];
		domein = new DomeinDTO(domein.aantalVanZelfdeType() + 1,
				domein.aantalKronenInDomein() + huidigVak.getAantalKronen(), bezocht);
		int[][] richtingen = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
		for (int[] richtingsPaar : richtingen) {
			int nx = x + richtingsPaar[0];
			int ny = y + richtingsPaar[1];
			if (nx >= 0 && nx < koninkrijk.length && ny >= 0 && ny < koninkrijk[nx].length && koninkrijk[nx][ny] != null
					&& !bezocht[nx][ny] && koninkrijk[nx][ny].getLandschap() == huidigVak.getLandschap())
				domein = dfs(nx, ny, koninkrijk, domein);
		}
		return domein;
	}

	public List<SpelerDTO> geefWinnaars() {
		List<SpelerDTO> winnaars = new ArrayList<>();
		int score = 0;
		int maxScore = 0;
		int aantalKronenWinnaar = 0;
		for (SpelerDTO speler : spelers) {
			score = berekenScore(speler);
			if (score > maxScore) {
				maxScore = score;
				aantalKronenWinnaar = telAantalKronen(speler);
				winnaars.clear();
				winnaars.add(speler);
			} else
				if (score == maxScore && aantalKronenWinnaar == telAantalKronen(speler))
					winnaars.add(speler);
		}
		return winnaars;
	}

	private int telAantalKronen(SpelerDTO speler) {
		int aantalKronen = 0;
		for (int rij = 0; rij < speler.koninkrijk().length; rij++)
			for (int kolom = 0; kolom < speler.koninkrijk()[rij].length; kolom++)
				if (speler.koninkrijk()[rij][kolom] != null)
					aantalKronen += speler.koninkrijk()[rij][kolom].getAantalKronen();
		return aantalKronen;
	}

}
