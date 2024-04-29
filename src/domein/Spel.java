
package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import dto.DomeinDTO;
import exceptions.AantalSpelersOngeldigException;
import exceptions.GebiedWordtTeGrootException;
import exceptions.OngeldigeRichtingException;
import exceptions.RaaktGeenTegelMetZelfdeLandschapsException;
import exceptions.TegelAlGekozenException;
import exceptions.TegelKanNietWeggegooidWordenException;
import exceptions.TegelNietInKolomException;
import exceptions.VakBezetException;
import utils.Kleur;
import utils.Landschap;
import utils.Richting;
import utils.Taal;
import utils.TegelMaker;

public class Spel {

	private final ResourceBundle messages;
	private final List<Speler> spelers;
	private List<Tegel> startKolom;
	private List<Tegel> eindKolom;
	private List<Tegel> stapel;
	private Speler huidigeSpeler;

	public Spel() {
		messages = ResourceBundle.getBundle("messages", Taal.getTaal());
		spelers = new ArrayList<>();
		stapel = new ArrayList<>();
		startKolom = new ArrayList<>();
		eindKolom = new ArrayList<>();
	}

	public List<Speler> getSpelers() {
		return spelers;
	}

	public List<Tegel> getStartKolom() {
		return startKolom;
	}

	public List<Tegel> getEindKolom() {
		return eindKolom;
	}

	public List<Tegel> getStapel() {
		return stapel;
	}

	public Speler getHuidigeSpeler() {
		return huidigeSpeler;
	}

	public void voegSpelerToe(Speler speler, Kleur kleur) {
		speler.setKleur(kleur);
		speler.setKoninkrijk(new Vak[9][9]);
		speler.getKoninkrijk()[4][4] = new Vak(Landschap.KASTEEL, -1);
		spelers.add(speler);
	}

	public void startSpel() {
		//
		Speler test1 = new Speler("Kjellvdb", 2000);
		test1.setKleur(Kleur.GROEN);
		test1.setKoninkrijk(new Vak[9][9]);
		test1.getKoninkrijk()[4][4] = new Vak(Landschap.KASTEEL, -1);
		spelers.add(test1);
		Speler test2 = new Speler("Jasper", 2000);
		test2.setKleur(Kleur.BLAUW);
		test2.setKoninkrijk(new Vak[9][9]);
		test2.getKoninkrijk()[4][4] = new Vak(Landschap.KASTEEL, -1);
		spelers.add(test2);
		Speler test3 = new Speler("Emielvdb", 2000);
		test3.setKleur(Kleur.GEEL);
		test3.setKoninkrijk(new Vak[9][9]);
		test3.getKoninkrijk()[4][4] = new Vak(Landschap.KASTEEL, -1);
		spelers.add(test3);
		//
		if (spelers.size() != 3 && spelers.size() != 4)
			throw new AantalSpelersOngeldigException(messages.getString("invalid_amount_of_players"));
		stapel.addAll(new TegelMaker().geeftegels());
		Collections.shuffle(stapel);
		stapel = new ArrayList<>(stapel.subList(0, spelers.size() * 12));
		startKolom = new ArrayList<>(stapel.subList(0, spelers.size()));
		stapel = new ArrayList<>(stapel.subList(spelers.size(), stapel.size()));
		Collections.sort(startKolom);
		Collections.shuffle(spelers);
		huidigeSpeler = spelers.get(0);
	}

	public void setVolgendeSpelerAlsHuidigeSpeler() {
		if (eindKolom.isEmpty() && !stapel.isEmpty()) {
			int indexVoorVolgendeSpeler = spelers.indexOf(huidigeSpeler) + 1;
			if (indexVoorVolgendeSpeler == spelers.size())
				huidigeSpeler = startKolom.get(0).getSpelerOpTegel();
			else
				huidigeSpeler = spelers.get(indexVoorVolgendeSpeler);
		} else {
			boolean alleSpelersHebbenAlGeplaatst = true;
			for (Tegel tegel : startKolom) {
				Speler spelerOpTegel = tegel.getSpelerOpTegel();
				if (spelerOpTegel != null && spelerOpTegel != huidigeSpeler) {
					huidigeSpeler = spelerOpTegel;
					alleSpelersHebbenAlGeplaatst = false;
					break;
				}
			}
			if (alleSpelersHebbenAlGeplaatst && !eindKolom.isEmpty())
				huidigeSpeler = eindKolom.get(0).getSpelerOpTegel();
		}
	}

	public void plaatsKoningOpTegel(String koningVanSpelerString, int gekozenTegel) {
		Speler koningVanSpeler = null;
		boolean koningGeplaatst = false;
		boolean plaatsInStartKolom = eindKolom.isEmpty();
		for (Speler speler : spelers)
			if (speler.getGebruikersnaam().equals(koningVanSpelerString)) {
				koningVanSpeler = speler;
				break;
			}
		for (Tegel tegel : plaatsInStartKolom ? startKolom : eindKolom)
			if (tegel.getTegelNummer() == gekozenTegel)
				if (tegel.getSpelerOpTegel() == null) {
					tegel.setSpelerOpTegel(koningVanSpeler);
					koningGeplaatst = true;
					break;
				} else
					throw new TegelAlGekozenException(String.format(messages.getString("tile_already_taken"),
							tegel.getSpelerOpTegel().getGebruikersnaam()));
		if (!koningGeplaatst)
			throw new TegelNietInKolomException(
					messages.getString(plaatsInStartKolom ? "tile_not_in_startcolumn" : "tile_not_in_endcolumn"));
	}

	public void vulKolommenAan() {
		if (!eindKolom.isEmpty()) {
			startKolom.clear();
			startKolom.addAll(eindKolom);
			eindKolom.clear();
		}
		if (!stapel.isEmpty()) {
			eindKolom = new ArrayList<>(stapel.subList(0, spelers.size()));
			stapel = new ArrayList<>(stapel.subList(spelers.size(), stapel.size()));
			Collections.sort(eindKolom);
		}
	}

	public void legTegelInKoninkrijk(int tegelNummer, String tegelVanSpelerString, String plaats,
			int richtingTePlaatsen) {
		Speler tegelVanSpeler = null;
		Tegel tegelOmTePlaatsen = null;
		for (Speler speler : spelers)
			if (speler.getGebruikersnaam().equals(tegelVanSpelerString)) {
				tegelVanSpeler = speler;
				break;
			}
		if (tegelVanSpeler == null)
			throw new NullPointerException();
		for (Tegel tegel : startKolom)
			if (tegel.getTegelNummer() == tegelNummer) {
				tegelOmTePlaatsen = tegel;
				break;
			}
		if (tegelOmTePlaatsen == null)
			throw new NullPointerException();
		Vak[][] koninkrijk = tegelVanSpeler.getKoninkrijk();
		int[] richting = Richting.geefRichting(switch (richtingTePlaatsen) {
			case 1 -> Richting.RECHTS;
			case 2 -> Richting.LINKS;
			case 3 -> Richting.BOVEN;
			case 4 -> Richting.ONDER;
			default -> null;
		});
		if (richting == null)
			throw new NullPointerException();
		int rij = Integer.parseInt(plaats.split("")[0]);
		int kolom = Integer.parseInt(plaats.split("")[1]);
		controleerRijKolomRichtingBinnenGebied(rij, kolom, richting, koninkrijk);
		if (!tegelIsPlaatsbaar(rij, kolom, richting, tegelOmTePlaatsen, koninkrijk))
			throw new RaaktGeenTegelMetZelfdeLandschapsException(messages.getString("doesnt_touch_right_tile"));
		koninkrijk[rij][kolom] = tegelOmTePlaatsen.getVakLinks();
		koninkrijk[rij + richting[0]][kolom + richting[1]] = tegelOmTePlaatsen.getVakRechts();
		vulVakkenIn(koninkrijk);
		for (Tegel tegel : startKolom)
			if (tegel.getSpelerOpTegel() != null && tegel.getSpelerOpTegel().equals(tegelVanSpeler)) {
				tegel.setSpelerOpTegel(null);
				break;
			}
	}

	private void controleerWeggooienMogelijk(Speler tegelVanSpeler, Tegel tegel) {
		Vak[][] koninkrijk = tegelVanSpeler.getKoninkrijk();
		boolean tegelKanNogGeplaatsWorden = false;
		for (int i = 0; i < koninkrijk.length; i++)
			for (int j = 0; j < koninkrijk[i].length; j++)
				if (koninkrijk[i][j] == null)
					for (int[] richting : Richting.geefRichtingen()) {
						int iBuur = i + richting[0];
						int jBuur = j + richting[1];
						if (iBuur >= 0 && iBuur < koninkrijk.length && jBuur >= 0 && jBuur < koninkrijk[iBuur].length
								&& koninkrijk[iBuur][jBuur] == null && tegelIsPlaatsbaar(i, j, richting, tegel, koninkrijk)) {
							tegelKanNogGeplaatsWorden = true;
							break;
						}
					}
		if (tegelKanNogGeplaatsWorden)
			throw new TegelKanNietWeggegooidWordenException(messages.getString("tile_cannot_be_discarded"));
	}

	private boolean tegelIsPlaatsbaar(int rij, int kolom, int[] richting, Tegel tegel, Vak[][] koninkrijk) {
		boolean raaktEenJuisteTegel = false;
		for (int[] elkeRichting : Richting.geefRichtingen()) {
			if (rij + elkeRichting[0] >= 0 && rij + elkeRichting[0] < koninkrijk.length && kolom + elkeRichting[1] >= 0
					&& kolom + elkeRichting[1] < koninkrijk.length) {
				Vak buurVanLinkseVak = koninkrijk[rij + elkeRichting[0]][kolom + elkeRichting[1]];
				if (buurVanLinkseVak != null && (buurVanLinkseVak.getLandschap() == tegel.getVakLinks().getLandschap()
						|| buurVanLinkseVak.getLandschap() == Landschap.KASTEEL)) {
					raaktEenJuisteTegel = true;
					break;
				}
			}
			if (rij + richting[0] + elkeRichting[0] >= 0 && rij + richting[0] + elkeRichting[0] < koninkrijk.length
					&& kolom + richting[1] + elkeRichting[1] >= 0
					&& kolom + richting[1] + elkeRichting[1] < koninkrijk.length) {
				Vak buurVanRechtseVak = koninkrijk[rij + richting[0] + elkeRichting[0]][kolom + richting[1]
						+ elkeRichting[1]];
				if (buurVanRechtseVak != null && (buurVanRechtseVak.getLandschap() == tegel.getVakRechts().getLandschap()
						|| buurVanRechtseVak.getLandschap() == Landschap.KASTEEL)) {
					raaktEenJuisteTegel = true;
					break;
				}
			}
		}
		return raaktEenJuisteTegel;
	}

	private void controleerRijKolomRichtingBinnenGebied(int rij, int kolom, int[] richting, Vak[][] koninkrijk) {
		if (rij < 0 || rij > 8 || kolom < 0 || kolom > 8)
			throw new OngeldigeRichtingException(messages.getString("invalid_box_number"));
		if (rij + richting[0] < 0 || rij + richting[0] >= koninkrijk.length || kolom + richting[1] < 0
				|| kolom + richting[1] >= koninkrijk.length)
			throw new OngeldigeRichtingException(messages.getString("invalid_direction_number"));
		if (koninkrijk[rij][kolom] != null) {
			if (koninkrijk[rij][kolom].getLandschap() == Landschap.LEEG)
				throw new GebiedWordtTeGrootException(messages.getString("to_big"));
			throw new VakBezetException(messages.getString("invalid_box"));
		}
		if (koninkrijk[rij + richting[0]][kolom + richting[1]] != null) {
			if (koninkrijk[rij + richting[0]][kolom + richting[1]].getLandschap() == Landschap.LEEG)
				throw new GebiedWordtTeGrootException(messages.getString("to_big"));
			throw new VakBezetException(messages.getString("invalid_direction"));
		}
	}

	private void vulVakkenIn(Vak[][] koninkrijk) {
		int lengteLangsteKolom = -1;
		int indexEersteIngevuldVakVanKolom = koninkrijk.length;
		int indexLaatsteIngevuldVakVanKolom = -1;
		int lengteLangsteRij = -1;
		int indexEersteIngevuldVakVanRij = koninkrijk.length;
		int indexLaatsteIngevuldVakVanRij = -1;
		for (int i = 0; i < koninkrijk.length; i++) {
			for (int j = 0; j < koninkrijk[i].length; j++) {
				Vak vak = koninkrijk[i][j];
				if (vak != null && vak.getLandschap() != Landschap.LEEG) {
					if (i < indexEersteIngevuldVakVanKolom)
						indexEersteIngevuldVakVanKolom = i;
					if (i > indexLaatsteIngevuldVakVanKolom)
						indexLaatsteIngevuldVakVanKolom = i;
					if (j < indexEersteIngevuldVakVanRij)
						indexEersteIngevuldVakVanRij = j;
					if (j > indexLaatsteIngevuldVakVanRij)
						indexLaatsteIngevuldVakVanRij = j;
				}
			}
			int lengteKolom = indexLaatsteIngevuldVakVanKolom - indexEersteIngevuldVakVanKolom + 1;
			if (lengteKolom > lengteLangsteKolom)
				lengteLangsteKolom = lengteKolom;
			int lengteRij = indexLaatsteIngevuldVakVanRij - indexEersteIngevuldVakVanRij + 1;
			if (lengteRij > lengteLangsteRij)
				lengteLangsteRij = lengteRij;
		}
		for (int i = 0; i < koninkrijk.length; i++)
			for (int j = 0; j < koninkrijk[i].length; j++) {
				if (i < indexEersteIngevuldVakVanKolom - 5 + lengteLangsteKolom
						|| i > indexLaatsteIngevuldVakVanKolom + 5 - lengteLangsteKolom)
					koninkrijk[i][j] = new Vak(Landschap.LEEG, -1);
				if (j < indexEersteIngevuldVakVanRij - 5 + lengteLangsteRij
						|| j > indexLaatsteIngevuldVakVanRij + 5 - lengteLangsteRij)
					koninkrijk[i][j] = new Vak(Landschap.LEEG, -1);
			}
	}

	public int berekenScore(Vak[][] koninkrijk) {
		int score = 0;
		boolean[][] bezocht = new boolean[koninkrijk.length][koninkrijk[0].length];
		for (int i = 0; i < bezocht.length; i++)
			for (int j = 0; j < bezocht[i].length; j++)
				bezocht[i][j] = false;
		DomeinDTO domein = new DomeinDTO(0, 0, bezocht);
		for (int i = 0; i < koninkrijk.length; i++)
			for (int j = 0; j < koninkrijk[i].length; j++)
				if (koninkrijk[i][j] != null && !domein.bezocht()[i][j] && koninkrijk[i][j].getLandschap() != Landschap.LEEG
						&& koninkrijk[i][j].getLandschap() != Landschap.KASTEEL) {
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
		for (int[] richtingsPaar : Richting.geefRichtingen()) {
			int nx = x + richtingsPaar[0];
			int ny = y + richtingsPaar[1];
			if (nx >= 0 && nx < koninkrijk.length && ny >= 0 && ny < koninkrijk[nx].length && koninkrijk[nx][ny] != null
					&& !bezocht[nx][ny] && koninkrijk[nx][ny].getLandschap() == huidigVak.getLandschap())
				domein = dfs(nx, ny, koninkrijk, domein);
		}
		return domein;
	}

	public List<Speler> geefWinnaars() {
		List<Speler> winnaars = new ArrayList<>();
		int scoreSpeler;
		int aantalTegelsInGrootsteDomeinSpeler;
		int aantalKronenSpeler;
		int scoreWinnaar = 0;
		int aantalTegelsInGrootsteDomeinWinnaar = 0;
		int aantalKronenWinnaar = 0;
		for (Speler speler : spelers) {
			scoreSpeler = berekenScore(speler.getKoninkrijk());
			aantalTegelsInGrootsteDomeinSpeler = telAantalTegelsInGrootsteDomein(speler.getKoninkrijk());
			aantalKronenSpeler = telAantalKronen(speler.getKoninkrijk());
			boolean hogereScore = scoreSpeler > scoreWinnaar;
			boolean meerTegelsInGrootsteDomein = scoreSpeler == scoreWinnaar
					&& aantalTegelsInGrootsteDomeinSpeler > aantalTegelsInGrootsteDomeinWinnaar;
			boolean meerKronenInKoninkrijk = scoreSpeler == scoreWinnaar
					&& aantalTegelsInGrootsteDomeinSpeler == aantalTegelsInGrootsteDomeinWinnaar
					&& aantalKronenSpeler > aantalKronenWinnaar;
			boolean zelfdeScoreEnAantalTegelsInGrootsteDomeinEnAantalKronen = scoreSpeler == scoreWinnaar
					&& aantalTegelsInGrootsteDomeinSpeler == aantalTegelsInGrootsteDomeinWinnaar
					&& aantalKronenSpeler == aantalKronenWinnaar;
			if (hogereScore || meerTegelsInGrootsteDomein || meerKronenInKoninkrijk) {
				scoreWinnaar = scoreSpeler;
				aantalTegelsInGrootsteDomeinWinnaar = aantalTegelsInGrootsteDomeinSpeler;
				aantalKronenWinnaar = aantalKronenSpeler;
				winnaars.clear();
				winnaars.add(speler);
			} else
				if (zelfdeScoreEnAantalTegelsInGrootsteDomeinEnAantalKronen)
					winnaars.add(speler);
		}
		return winnaars;
	}

	private int telAantalTegelsInGrootsteDomein(Vak[][] koninkrijk) {
		int aantalTegelsInHuidigDomein;
		int aantalTegelsInGrootsteDomein = 0;
		boolean[][] bezocht = new boolean[koninkrijk.length][koninkrijk[0].length];
		for (int i = 0; i < bezocht.length; i++)
			for (int j = 0; j < bezocht[i].length; j++)
				bezocht[i][j] = false;
		DomeinDTO domein = new DomeinDTO(0, 0, bezocht);
		for (int i = 0; i < koninkrijk.length; i++)
			for (int j = 0; j < koninkrijk[i].length; j++)
				if (koninkrijk[i][j] != null && !domein.bezocht()[i][j] && koninkrijk[i][j].getLandschap() != Landschap.LEEG
						&& koninkrijk[i][j].getLandschap() != Landschap.KASTEEL) {
					domein = dfs(i, j, koninkrijk, new DomeinDTO(0, 0, domein.bezocht()));
					aantalTegelsInHuidigDomein = domein.aantalVanZelfdeType();
					if (aantalTegelsInHuidigDomein > aantalTegelsInGrootsteDomein)
						aantalTegelsInGrootsteDomein = domein.aantalVanZelfdeType();
				}
		return aantalTegelsInGrootsteDomein;
	}

	private int telAantalKronen(Vak[][] koninkrijk) {
		int aantalKronen = 0;
		for (Vak[] rij : koninkrijk)
			for (Vak vak : rij)
				if (vak != null && vak.getLandschap() != Landschap.LEEG && vak.getLandschap() != Landschap.KASTEEL)
					aantalKronen += vak.getAantalKronen();
		return aantalKronen;
	}

	public Vak[][] geefKoninkrijk(String gebruikersnaam) {
		for (Speler speler : spelers)
			if (speler.getGebruikersnaam().equals(gebruikersnaam))
				return speler.getKoninkrijk();
		return null;
	}

	public void gooiWeg(int tegelNummer, String gebruikersnaam) {
		Speler tegelVanSpeler = null;
		Tegel tegelOmTePlaatsen = null;
		for (Speler speler : spelers)
			if (speler.getGebruikersnaam().equals(gebruikersnaam)) {
				tegelVanSpeler = speler;
				break;
			}
		if (tegelVanSpeler == null)
			throw new NullPointerException();
		for (Tegel tegel : startKolom)
			if (tegel.getTegelNummer() == tegelNummer) {
				tegelOmTePlaatsen = tegel;
				break;
			}
		if (tegelOmTePlaatsen == null)
			throw new NullPointerException();
		controleerWeggooienMogelijk(tegelVanSpeler, tegelOmTePlaatsen);
		for (Tegel tegel : startKolom)
			if (tegel.getSpelerOpTegel() != null && tegel.getSpelerOpTegel().equals(tegelVanSpeler)) {
				tegel.setSpelerOpTegel(null);
				break;
			}
	}

}
