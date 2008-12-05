package spelstegen.server;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import spelstegen.client.entities.League;
import spelstegen.client.entities.Player;
import spelstegen.client.entities.Sport;

/**
 * This is the main class of the console administration tool.
 * 
 * @author Henrik Segesten
 */
public class ConsoleTool {
	
	private StorageInterface storage = new MySQLStorageImpl();

	public static void main(String[] args) {
		ConsoleTool ct = new ConsoleTool();
		if (args.length == 0) {
			System.out.println("Välkommen till administrationsverktyget för spelstegen!");
			System.out.println("");
			System.out.println("");
			System.out.println("Ange ett av följande alternativ och ge som argument till verktyget:");
			System.out.println("");
			System.out.println("");
			System.out.println("1 - Lägg till spelare");
			System.out.println("2 - Lägg till liga");
			System.out.println("3 - Lägg till spelare till liga");
		}
		if (args.length == 1) {
			switch (Integer.parseInt(args[0].trim())) {
			case 1:
				ct.addPlayer();
				break;
			case 2:
				ct.addLeague();
				break;
			case 3:
				ct.addPlayerToLeague();
				break;
			default:
				break;
			}
		}
	}
	
	private void addLeague() {
		String name;
		List<Sport> sports = storage.getSports();
		Scanner sc = new Scanner(System.in);
		System.out.println("Skriv in namnet på ligan");
		name = sc.nextLine();
		List<Sport> selectedSports = new ArrayList<Sport>();
		while (true) {
			System.out.println("Möjliga sporter:");
			for (Sport sport : sports) {
				System.out.println("Id: " + sport.getId() + " Namn: " + sport.getName());
			}
			System.out.println("Välj sport genom att ange sportens id eller -1 om du inte vill lägga till fler sporter.");
			int sport = sc.nextInt();
			if (sport == -1) {
				break;
			}
			selectedSports.add(sports.get(sport));
		}
		League league = new League(name);
		league.setSports(selectedSports);
		storage.addLeague(league);
	}

	private void addPlayer() {
		String name;
		String email;
		String nickName;
		String password;
		String image;
		Scanner sc = new Scanner(System.in);
		System.out.println("Skriv in ditt namn");
		name = sc.nextLine();
		System.out.println("Skriv in din email");
		email = sc.nextLine();
		System.out.println("Skriv in ditt lösenord");
		String temp = sc.nextLine();
		System.out.println("Skriv in ditt lösenord igen");
		password = sc.nextLine();
		if (password.equals(temp)) {
			password = getMd5Digest(temp);
		}
		System.out.println("Skriv in ditt smeknamn");
		nickName = sc.nextLine();
		System.out.println("Skriv in en URL för din bild");
		image = sc.nextLine();
		Player p = new Player(name, email);
		p.setEncryptedPassword(password);
		p.setNickName(nickName);
		p.setImageURL(image);
		storage.addPlayer(p);
	}
	
	private void addPlayerToLeague() {
		int playerId;
		int leagueId;
		String playerEmail;
		Scanner sc = new Scanner(System.in);
		System.out.println("Skriv in epostadressen för den spelare som skall läggas till i en liga.");
		playerEmail = sc.nextLine();
		Player p = storage.getPlayer(playerEmail);
		if (p == null) {
			System.out.println("Fanns ingen spelare med den epostadressen.");
			return;
		}
		playerId = p.getId();
		System.out.println("");
		System.out.println("");
		System.out.println("Tillgängliga ligor:");
		for (League league : storage.getLeagues(null)) {
			System.out.println("Id: " + league.getId() + " Namn: " + league.getName());
		}
		System.out.println("");
		System.out.println("Välj vilken liga du vill lägga till spelaren till genom att mata in ligans id");
		leagueId = sc.nextInt();
		System.out.println("Ska spelaren vara administratör för den här ligan? (J/N)");
		String admin = sc.nextLine();
		storage.addPlayerToLeague(leagueId, playerId);
		if (admin.toLowerCase().equals("j")) {
			storage.addLeagueAdminRoles(playerId, leagueId, true, true);
		}
	}
	
	static String getMd5Digest(String input) {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1,messageDigest);
            return pad(number.toString(16),32,'0');
        }
        catch(NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }
	
	private static String pad(String s, int length, char pad) {
		StringBuffer buffer = new StringBuffer(s);
		while (buffer.length() < length) {
			buffer.insert(0, pad);
		}
		return buffer.toString();
	}
	
}
