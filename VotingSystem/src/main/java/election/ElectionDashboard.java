package election;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdbc.ElectionDB;
import usermanagement.Voter;
import utils.PrintSystem;
import utils.utility;

public class ElectionDashboard {
	private  static final Logger logger = LogManager.getLogger(ElectionDashboard.class);
	public static boolean electionDashboardMenu() {

		boolean isRun = true;
		try {
			ElectionDB.updateStatus();
			while (isRun) {
				PrintSystem.printMenu("Election Dashboard Panel",
						new String[] { "1. Voter Login", "2. Admin Login", "3. Previous Menu", "4. Exit" });
				byte userInp = (byte) PrintSystem.safeReadInt("Enter Valid Option");
					switch (userInp) {
						case 1 -> {
							Voter voter = utility.getVoterInfoFromUserLogin();
							if (voter != null) {
								ElectionDashboard.voterDashBoard(voter);
							}
						}
						case 2 -> {
							if (utility.adminLogIn())
								isRun = ElectionSystem.electionAdminMenu();
							else {
								throw new IOException("Admin Credential Not Valid");
							}
						}
						case 3 -> {
							isRun = false;
						}
						case 4 -> {
							return false;
						}
						default -> {
							throw new IOException("Invalid Argument");
						}
					}
			}
		} catch (IOException e) {
			logger.error("IOException while election login class", e);
			utility.declinedNotification("Invalid Arguments");
		} catch (Exception e) {
			logger.error("Exception while election login class", e);
			utility.declinedNotification(e.getMessage());
		}
		return true;
	}

	public static void voterDashBoard(Voter voter) throws Exception, IOException {
		boolean isRun = true;

		try {

			while (isRun) {

				PrintSystem.printMenu("Voter Dashboard Panel",
						new String[] { "1. Current Elections", "2. Election Result", "3. Previous Menu", "4. Exit" });
				byte userInp = (byte) PrintSystem.safeReadInt("");
				switch (userInp) {
					case 1 -> {
						ElectionDB.showElections();
					}
					case 2 -> {
						String eleID = utility.electionOption();
						ElectionDB.getWinner(eleID);

					}
					case 3 -> {
						isRun = false;
					}

					default -> {
						throw new IOException("Invalid Argument");
					}

				}

			}
		} catch (IOException e) {
			logger.error("IOException while election login class", e);
			utility.declinedNotification(e.getMessage());
		} catch (Exception e) {
			logger.error("Exception while election login class", e);
			utility.declinedNotification(e.getMessage());
		}
	}
}
