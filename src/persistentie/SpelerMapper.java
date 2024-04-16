
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

	public void voegToe(Speler speler) {
		Connectie ssh = new Connectie();
		try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
				PreparedStatement query = conn.prepareStatement(INSERT_SPELER)) {
			query.setString(1, speler.getGebruikersnaam());
			query.setInt(2, speler.getGeboortejaar());
			query.setInt(3, speler.getAantalGewonnen());
			query.setInt(4, speler.getAantalGespeeld());
			query.executeUpdate();
		}
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		finally {
			ssh.closeConnection();
		}
	}

	public Speler geefSpeler(String gebruikersnaam) {
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

	public List<Speler> geefAlleSpelers() {
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

	public void updateAantalGewonnenEnAantalGespeeld(List<List<Speler>> spelers) {
		Connectie ssh = new Connectie();
		try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
				PreparedStatement queryUpdateAantalGespeeld = conn.prepareStatement(UPDATE_AANTAL_GESPEELD)) {
			for (Speler speler : spelers.get(0)) {
				queryUpdateAantalGespeeld.setString(1, speler.getGebruikersnaam());
				queryUpdateAantalGespeeld.executeUpdate();
			}
		}
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
				PreparedStatement queryUpdateAantalGewonnen = conn.prepareStatement(UPDATE_AANTAL_GEWONNEN)) {
			for (Speler speler : spelers.get(1)) {
				queryUpdateAantalGewonnen.setString(1, speler.getGebruikersnaam());
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
