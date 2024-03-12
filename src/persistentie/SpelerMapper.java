
package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.AlleSpelersDTO;
import domein.Speler;

public class SpelerMapper {

	private static final String INSERT_SPELER = "INSERT INTO Speler (gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld)"
			+ "VALUES (?, ?, ?, ?)";

	public void voegToe(Speler speler) { // Voegt een speler toe aan de database met zijn gebruikersnaam, geboortejaar,
											// aantal gewonnen en aantal gespeeld
		Connectie ssh = new Connectie();
		try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
				PreparedStatement query = conn.prepareStatement(INSERT_SPELER)) {
			query.setString(1, speler.getGebruikersnaam());
			query.setInt(2, speler.getGeboortejaar());
			query.setInt(3, speler.getAantalGewonnen());
			query.setInt(4, speler.getAantalGespeeld());
			query.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			ssh.closeConnection();
		}
	}

	public Speler geefSpeler(String gebruikersnaam) { // Retourneerd alle gegevens van een speler op basis van zijn
														// gebruikersnaam
		Connectie ssh = new Connectie();
		Speler speler = null;
		try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
				PreparedStatement query = conn
						.prepareStatement("SELECT * FROM ID429632_kingdominoG38.Speler WHERE gebruikersnaam = ?")) {
			query.setString(1, gebruikersnaam);
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					int geboortejaar = rs.getInt("geboortejaar");
					int aantalGewonnen = rs.getInt("aantalGewonnen");
					int aantalGespeeld = rs.getInt("aantalGespeeld");
					speler = new Speler(gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld);
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			ssh.closeConnection();
		}
		return speler;
	}

	public List<AlleSpelersDTO> geefAlleSpelers() { // Retourneerd alle spelers in de database met hun gebruikersnaam en
													// geboortejaar
		List<AlleSpelersDTO> alleSpelers = new ArrayList<>();
		String gebruikersnaam = null;
		int geboortejaar = 0;
		int aantalGewonnen = 0;
		int aantalGespeeld = 0;
		Connectie ssh = new Connectie();
		List<String[]> spelers = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
				PreparedStatement query = conn.prepareStatement("SELECT * FROM ID429632_kingdominoG38.Speler")) {
			try (ResultSet rs = query.executeQuery()) {
				while (rs.next()) {
					gebruikersnaam = rs.getString("gebruikersnaam");
					geboortejaar = rs.getInt("geboortejaar");
					aantalGewonnen = rs.getInt("aantalGewonnen");
					aantalGespeeld = rs.getInt("aantalGespeeld");
					alleSpelers.add(new AlleSpelersDTO(gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld));
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			ssh.closeConnection();
		}

		return alleSpelers; // Retourneerd een 2D array van alle spelers met hun gebruikersnaam en
							// geboortejaar
	}
}
