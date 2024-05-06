
package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domein.Speler;

public class SpelerMapper {

	private static final String INSERT_SPELER = "INSERT INTO Speler (gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld) VALUES (?, ?, ?, ?)";
	private static final String SELECT_SPELER = "SELECT * FROM Speler WHERE gebruikersnaam = ?";
	private static final String SELECT_ALL_SPELERS = "SELECT * FROM Speler";
	private static final String UPDATE_AANTAL_GEWONNEN = "UPDATE Speler SET aantalGewonnen = aantalGewonnen + 1 WHERE gebruikersnaam = ?";
	private static final String UPDATE_AANTAL_GESPEELD = "UPDATE Speler SET aantalGespeeld = aantalGespeeld + 1 WHERE gebruikersnaam = ?";

	/**
	 * Voegt een nieuwe speler toe aan de database.
	 *
	 * @param speler de speler om toe te voegen.
	 */
	public void voegToe(Speler speler) {
		Connectie ssh = new Connectie();
		try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
				PreparedStatement query = conn.prepareStatement(INSERT_SPELER)) {
			query.setString(1, speler.geefGebruikersnaam());
			query.setInt(2, speler.geefGeboortejaar());
			query.setInt(3, speler.geefAantalGewonnenSpellen());
			query.setInt(4, speler.geefAantalGespeeldeSpellen());
			query.executeUpdate();
		}
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		finally {
			ssh.closeConnection();
		}
	}

	/**
	 * Haalt een speler op uit de database op basis van de gebruikersnaam.
	 *
	 * @param gebruikersnaam de gebruikersnaam van de speler.
	 * @return de opgehaalde speler.
	 */
	public Speler geefSpelerUitDataBase(String gebruikersnaam) {
		Connectie ssh = new Connectie();
		Speler speler = null;
		try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
				PreparedStatement query = conn.prepareStatement(SELECT_SPELER)) {
			query.setString(1, gebruikersnaam);
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					gebruikersnaam = rs.getString("gebruikersnaam");
					int geboortejaar = rs.getInt("geboortejaar");
					int aantalGewonnen = rs.getInt("aantalGewonnen");
					int aantalGespeeld = rs.getInt("aantalGespeeld");
					speler = new Speler(gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld);
				}
			}
		}
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		finally {
			ssh.closeConnection();
		}
		return speler;
	}

	/**
	 * Haalt alle spelers op uit de database.
	 *
	 * @return een lijst van alle spelers.
	 */
	public List<Speler> geefAlleSpelersUitDataBase() {
		Connectie ssh = new Connectie();
		List<Speler> spelers = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
				PreparedStatement query = conn.prepareStatement(SELECT_ALL_SPELERS)) {
			try (ResultSet rs = query.executeQuery()) {
				while (rs.next()) {
					String gebruikersnaam = rs.getString("gebruikersnaam");
					int geboortejaar = rs.getInt("geboortejaar");
					int aantalGewonnen = rs.getInt("aantalGewonnen");
					int aantalGespeeld = rs.getInt("aantalGespeeld");
					spelers.add(new Speler(gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld));
				}
			}
		}
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		finally {
			ssh.closeConnection();
		}
		return spelers;
	}

	/**
	 * Update het aantal gewonnen en gespeelde spellen voor een lijst van spelers in
	 * de database.
	 *
	 * @param spelers de lijst van spelers om te updaten.
	 */
	public void updateAantalGewonnenEnAantalGespeeldeSpellen(List<List<Speler>> spelers) {
		Connectie ssh = new Connectie();
		try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
				PreparedStatement queryUpdateAantalGespeeld = conn.prepareStatement(UPDATE_AANTAL_GESPEELD)) {
			for (Speler speler : spelers.get(0)) {
				queryUpdateAantalGespeeld.setString(1, speler.geefGebruikersnaam());
				queryUpdateAantalGespeeld.executeUpdate();
			}
		}
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
				PreparedStatement queryUpdateAantalGewonnen = conn.prepareStatement(UPDATE_AANTAL_GEWONNEN)) {
			for (Speler speler : spelers.get(1)) {
				queryUpdateAantalGewonnen.setString(1, speler.geefGebruikersnaam());
				queryUpdateAantalGewonnen.executeUpdate();
			}
		}
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		finally {
			ssh.closeConnection();
		}
	}

}
