import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseInterface {

	private Connection connection = null;

	private PreparedStatement insertPersonal;
	private PreparedStatement deletePersonal;
	private PreparedStatement selectPersonalContact;

	public DatabaseInterface(String filepath, String username, String password) {
		initInterface(filepath, username, password);
	}

	
	private void initInterface(String filepath, String username, String password) {
		try {
			Class.forName("org.firebirdsql.jdbc.FBDriver");
			connection = DriverManager.getConnection(
					"jdbc:firebirdsql:localhost/3050:" + filepath, username,
					password);
			insertPersonal = connection
					.prepareStatement("INSERT INTO personal VALUES (?,?,?)");
			deletePersonal = connection
					.prepareStatement("DELETE FROM personal WHERE p_num = ?");
			selectPersonalContact = connection
					.prepareStatement("SELECT personal.p_num, personal.name, personal.vorname, contact.mail " +
							"FROM personal JOIN contact JOIN per_con ON contact.con_ID=per_con.con_ID ON personal.p_num=per_con.p_num " +
							"WHERE (personal.name = ? OR contact.mail LIKE ?)");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Inserts a set of person
	 * @param values
	 */
	public void insertPersonal(String[][] values) {
		for (String[] rows : values) {
			try {
				for (int i = 0; i < rows.length; i++) {
					insertPersonal.setString(i + 1, rows[i]);
				}
				insertPersonal.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Deletes a set of person by their personal ID
	 * @param personalNumbers IDs of the people
	 */
	public void deletePersonalByNum(String[] personalNumbers) {
		for (String personalNumber : personalNumbers) {
			try {
				deletePersonal.setString(1, personalNumber);
				deletePersonal.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Abfrage von Personalnummer, Name, Vorname und Mail. Nur wenn Name oder
	 * Beginn der Mailaddresse mit dem Parameter übereinstimmt, wird ausgewählt.
	 * 
	 * @param name
	 *            searched for
	 */
	public void selectPersonByName(String name) {
		try {
			selectPersonalContact.setString(1, name);
			selectPersonalContact.setString(2, name + "%");
			selectPersonalContact.execute();
			ResultSet set = selectPersonalContact.getResultSet();

			while (set.next()) {
				System.out.print(set.getString(1) + " ");
				System.out.print(set.getString(2) + " ");
				System.out.print(set.getString(3) + " ");
				System.out.print(set.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DatabaseInterface jdbc = new DatabaseInterface(
				"C:/Users/IBM_ADMIN/Documents/MyEclipse/Persistenzlayer/firebird.fdb",
				"sysdba", "masterkey");
		jdbc.deletePersonalByNum(new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10" });
		jdbc.insertPersonal(new String[][] { { "1", "Baumann", "Steffen" },
				{ "2", "Baumann", "Steffen" }, { "3", "Müller", "Hans" },
				{ "4", "Maier", "Kurt" }, { "5", "Schultz", "Sebastian" },
				{ "6", "Baumann", "Steffen" }, { "7", "Merkel", "Angela" },
				{ "8", "Steubär", "Edmund" }, { "9", "Kaufmann", "Ingo" },
				{ "10", "Baumann", "Steffen" } });
		jdbc.selectPersonByName("Baumann");
		jdbc.selectPersonByName("Merkel");
	}
}
