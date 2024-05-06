
package domein;

import java.util.ResourceBundle;

import dto.DomeinDTO;
import exceptions.GebiedWordtTeGrootException;
import exceptions.OngeldigeRichtingException;
import exceptions.RaaktGeenTegelMetZelfdeLandschapsException;
import exceptions.TegelKanNietWeggegooidWordenException;
import exceptions.VakBezetException;
import utils.Landschap;
import utils.Richting;
import utils.Taal;
import utils.TegelMaker;

public class Koninkrijk {

	private final ResourceBundle messages;
	private final Vak[][] koninkrijk;

	/**
	 * Constructor van een Koninkrijk.
	 */
	public Koninkrijk() {
		messages = ResourceBundle.getBundle("messages", Taal.geefTaal());
		this.koninkrijk = new Vak[9][9];
		this.koninkrijk[4][4] = new Vak(Landschap.KASTEEL, -1);
	}

	/**
	 * Geeft het koninkrijk.
	 *
	 * @return een 2D array van Vakken.
	 */
	public Vak[][] geefKoninkrijk() {
		return koninkrijk;
	}

	/**
	 * Legt een tegel in het koninkrijk.
	 *
	 * @param tegelNummer        het nummer van de tegel.
	 * @param rij                de rij waarin de tegel wordt gelegd.
	 * @param kolom              de kolom waarin de tegel wordt gelegd.
	 * @param richtingTePlaatsen de richting waarin de tegel wordt gelegd.
	 */
	public void legTegelInKoninkrijk(int tegelNummer, int rij, int kolom, int richtingTePlaatsen) {
		int[] richting = Richting.geefRichting(switch (richtingTePlaatsen) {
			case 1 -> Richting.RECHTS;
			case 2 -> Richting.LINKS;
			case 3 -> Richting.BOVEN;
			case 4 -> Richting.ONDER;
			default -> null;
		});
		Tegel tegelOmTePlaatsen = new TegelMaker().geeftegels().stream().filter(t -> t.geefTegelNummer() == tegelNummer)
				.findAny().orElse(null);
		controleerRijKolomRichtingBinnenGebied(rij, kolom, richting);
		if (!tegelIsPlaatsbaar(rij, kolom, richting, tegelOmTePlaatsen))
			throw new RaaktGeenTegelMetZelfdeLandschapsException(messages.getString("doesnt_touch_right_tile"));
		koninkrijk[rij][kolom] = tegelOmTePlaatsen.geefVakLinks();
		koninkrijk[rij + richting[0]][kolom + richting[1]] = tegelOmTePlaatsen.geefVakRechts();
		vulVakkenIn();
	}

	private void controleerRijKolomRichtingBinnenGebied(int rij, int kolom, int[] richting) {
		if (rij < 0 || rij > 8 || kolom < 0 || kolom > 8)
			throw new OngeldigeRichtingException(messages.getString("invalid_box_number"));
		if (rij + richting[0] < 0 || rij + richting[0] >= koninkrijk.length || kolom + richting[1] < 0
				|| kolom + richting[1] >= koninkrijk.length)
			throw new OngeldigeRichtingException(messages.getString("invalid_direction_number"));
		if (koninkrijk[rij][kolom] != null) {
			if (koninkrijk[rij][kolom].geefLandschap() == Landschap.LEEG)
				throw new GebiedWordtTeGrootException(messages.getString("to_big"));
			throw new VakBezetException(messages.getString("invalid_box"));
		}
		if (koninkrijk[rij + richting[0]][kolom + richting[1]] != null) {
			if (koninkrijk[rij + richting[0]][kolom + richting[1]].geefLandschap() == Landschap.LEEG)
				throw new GebiedWordtTeGrootException(messages.getString("to_big"));
			throw new VakBezetException(messages.getString("invalid_direction"));
		}
	}

	private boolean tegelIsPlaatsbaar(int rij, int kolom, int[] richting, Tegel tegel) {
		boolean raaktEenJuisteTegel = false;
		for (int[] elkeRichting : Richting.geefRichtingen()) {
			if (rij + elkeRichting[0] >= 0 && rij + elkeRichting[0] < koninkrijk.length && kolom + elkeRichting[1] >= 0
					&& kolom + elkeRichting[1] < koninkrijk.length) {
				Vak buurVanLinkseVak = koninkrijk[rij + elkeRichting[0]][kolom + elkeRichting[1]];
				if (buurVanLinkseVak != null && (buurVanLinkseVak.geefLandschap() == tegel.geefVakLinks().geefLandschap()
						|| buurVanLinkseVak.geefLandschap() == Landschap.KASTEEL)) {
					raaktEenJuisteTegel = true;
					break;
				}
			}
			if (rij + richting[0] + elkeRichting[0] >= 0 && rij + richting[0] + elkeRichting[0] < koninkrijk.length
					&& kolom + richting[1] + elkeRichting[1] >= 0
					&& kolom + richting[1] + elkeRichting[1] < koninkrijk.length) {
				Vak buurVanRechtseVak = koninkrijk[rij + richting[0] + elkeRichting[0]][kolom + richting[1]
						+ elkeRichting[1]];
				if (buurVanRechtseVak != null && (buurVanRechtseVak.geefLandschap() == tegel.geefVakRechts().geefLandschap()
						|| buurVanRechtseVak.geefLandschap() == Landschap.KASTEEL)) {
					raaktEenJuisteTegel = true;
					break;
				}
			}
		}
		return raaktEenJuisteTegel;
	}

	private void vulVakkenIn() {
		int lengteLangsteKolom = -1;
		int indexEersteIngevuldVakVanKolom = koninkrijk.length;
		int indexLaatsteIngevuldVakVanKolom = -1;
		int lengteLangsteRij = -1;
		int indexEersteIngevuldVakVanRij = koninkrijk.length;
		int indexLaatsteIngevuldVakVanRij = -1;
		for (int i = 0; i < koninkrijk.length; i++) {
			for (int j = 0; j < koninkrijk[i].length; j++) {
				Vak vak = koninkrijk[i][j];
				if (vak != null && vak.geefLandschap() != Landschap.LEEG) {
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

	/**
	 * Gooit een tegel weg.
	 *
	 * @param tegelNummer het nummer van de tegel die wordt weggegooid.
	 */
	public void gooiWeg(int tegelNummer) {
		Tegel tegelOmTePlaatsen = new TegelMaker().geeftegels().stream().filter(t -> t.geefTegelNummer() == tegelNummer)
				.findAny().orElse(null);
		controleerWeggooienMogelijk(tegelOmTePlaatsen);
	}

	private void controleerWeggooienMogelijk(Tegel tegel) {
		boolean tegelKanNogGeplaatsWorden = false;
		for (int i = 0; i < koninkrijk.length; i++)
			for (int j = 0; j < koninkrijk[i].length; j++)
				if (koninkrijk[i][j] == null)
					for (int[] richting : Richting.geefRichtingen()) {
						int iBuur = i + richting[0];
						int jBuur = j + richting[1];
						if (iBuur >= 0 && iBuur < koninkrijk.length && jBuur >= 0 && jBuur < koninkrijk[iBuur].length
								&& koninkrijk[iBuur][jBuur] == null && tegelIsPlaatsbaar(i, j, richting, tegel)) {
							tegelKanNogGeplaatsWorden = true;
							break;
						}
					}
		if (tegelKanNogGeplaatsWorden)
			throw new TegelKanNietWeggegooidWordenException(messages.getString("tile_cannot_be_discarded"));
	}

	/**
	 * Bereken de score van het koninkrijk.
	 *
	 * @return de score van het koninkrijk.
	 */
	public int berekenScore() {
		int score = 0;
		boolean[][] bezocht = new boolean[koninkrijk.length][koninkrijk[0].length];
		for (int i = 0; i < bezocht.length; i++)
			for (int j = 0; j < bezocht[i].length; j++)
				bezocht[i][j] = false;
		DomeinDTO domein = new DomeinDTO(0, 0, bezocht);
		for (int i = 0; i < koninkrijk.length; i++)
			for (int j = 0; j < koninkrijk[i].length; j++)
				if (koninkrijk[i][j] != null && !domein.bezocht()[i][j]
						&& koninkrijk[i][j].geefLandschap() != Landschap.LEEG
						&& koninkrijk[i][j].geefLandschap() != Landschap.KASTEEL) {
					domein = dfs(i, j, new DomeinDTO(0, 0, domein.bezocht()));
					score += domein.aantalVanZelfdeType() * domein.aantalKronenInDomein();
				}
		return score;
	}

	private DomeinDTO dfs(int x, int y, DomeinDTO domein) {
		boolean[][] bezocht = domein.bezocht();
		bezocht[x][y] = true;
		Vak huidigVak = koninkrijk[x][y];
		domein = new DomeinDTO(domein.aantalVanZelfdeType() + 1,
				domein.aantalKronenInDomein() + huidigVak.geefAantalKronen(), bezocht);
		for (int[] richtingsPaar : Richting.geefRichtingen()) {
			int nx = x + richtingsPaar[0];
			int ny = y + richtingsPaar[1];
			if (nx >= 0 && nx < koninkrijk.length && ny >= 0 && ny < koninkrijk[nx].length && koninkrijk[nx][ny] != null
					&& !bezocht[nx][ny] && koninkrijk[nx][ny].geefLandschap() == huidigVak.geefLandschap())
				domein = dfs(nx, ny, domein);
		}
		return domein;
	}

	/**
	 * Telt het aantal tegels in het grootste domein van het koninkrijk.
	 *
	 * @return het aantal tegels in het grootste domein.
	 */
	public int telAantalTegelsInGrootsteDomein() {
		int aantalTegelsInHuidigDomein;
		int aantalTegelsInGrootsteDomein = 0;
		boolean[][] bezocht = new boolean[koninkrijk.length][koninkrijk[0].length];
		for (int i = 0; i < bezocht.length; i++)
			for (int j = 0; j < bezocht[i].length; j++)
				bezocht[i][j] = false;
		DomeinDTO domein = new DomeinDTO(0, 0, bezocht);
		for (int i = 0; i < koninkrijk.length; i++)
			for (int j = 0; j < koninkrijk[i].length; j++)
				if (koninkrijk[i][j] != null && !domein.bezocht()[i][j]
						&& koninkrijk[i][j].geefLandschap() != Landschap.LEEG
						&& koninkrijk[i][j].geefLandschap() != Landschap.KASTEEL) {
					domein = dfs(i, j, new DomeinDTO(0, 0, domein.bezocht()));
					aantalTegelsInHuidigDomein = domein.aantalVanZelfdeType();
					if (aantalTegelsInHuidigDomein > aantalTegelsInGrootsteDomein)
						aantalTegelsInGrootsteDomein = domein.aantalVanZelfdeType();
				}
		return aantalTegelsInGrootsteDomein;
	}

	/**
	 * Telt het aantal kronen in het koninkrijk.
	 *
	 * @return het aantal kronen in het koninkrijk.
	 */
	public int telAantalKronen() {
		int aantalKronen = 0;
		for (Vak[] rij : koninkrijk)
			for (Vak vak : rij)
				if (vak != null && vak.geefLandschap() != Landschap.LEEG && vak.geefLandschap() != Landschap.KASTEEL)
					aantalKronen += vak.geefAantalKronen();
		return aantalKronen;
	}

}
