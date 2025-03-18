package usermanagement;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdbc.ElectionDB;
import jdbc.VotingSystemDB;
import utils.PrintSystem;
import utils.utility;
import vote.VoteDashboard;

public class VoterSystem {
	private static final Logger logger = LogManager.getLogger(VoterSystem.class);

	public static boolean voterMenu() {
		boolean isRun = true;
		try {
			ElectionDB.updateStatus();
			while (isRun) {
				PrintSystem.printMenu("Voter Management Panel",
						new String[] { "1. Register", "2. Login", "3. Previous Menu", "4. Exit" });

				try {
					byte userInp = (byte) PrintSystem.safeReadInt("Choose Valid Option");

					switch (userInp) {
						case 1 -> {
							System.out.println("\n--- Voter Management Register ---");
							Voter voter = utility.getVoterInfoFromUser();
							PrintSystem.userInp.nextLine();

							if (voter != null) {
								voterManagementsystem(voter);
							}

						}

						case 2 -> {
							System.out.println("\n--- Voter Management Login ---");

							Voter voter = utility.getVoterInfoFromUserLogin();
							if (voter != null) {
								isRun = voterManagementsystem(voter);
								return isRun;
							}

						}
						case 3 -> {
							System.out.println("--------    Going to previous menu...    ---------\n");

							return true;
						}
						case 4 -> {
							System.out.println("\n--------      Exiting      ---------\n");
							return false;
						}
						default -> {
							throw new IOException("Enter Valid Credentials");
						}
					}
				} catch (IOException e) {
					logger.error("Voter Menu IOException: " + e);
					utility.declinedNotification("Invalid Input: " + e.getMessage());
				} catch (Exception e) {
					logger.error("Voter Menu Exception: " + e);
					utility.declinedNotification(e.getMessage());
				}

			}

		} catch (Exception e) {
			logger.error("Voter System Error: " + e);
			utility.declinedNotification("Voter Panel Error: " + e.getMessage());

		}
		return true;
	}

	public static boolean voterManagementsystem(Voter voter) throws Exception, IOException {
		boolean isRun = true;
		try {

			while (isRun) {
				PrintSystem.printMenu("Voter Dashboard System",
						new String[] { "1. My Information", "2. Update My data", "3. Vote Dashboard", "4. Previous Menu",
								"5. Exit" });
				byte userInp = (byte) PrintSystem.safeReadInt("Choose Valid Option");

				switch (userInp) {
					case 1 -> {
						System.out.println(voter.toString());
						isRun = PrintSystem.isContinue();
					}

					case 2 -> {
						PrintSystem.printMenu("Update Voter Information",
								new String[] { "1. Update Email", "2. Change password", "3. Exit" });
						byte updateOption = (byte) PrintSystem.safeReadInt("");

						switch (updateOption) {
							case 1 -> {
								System.out.println("Enter new email ? ");
								String email = PrintSystem.userInp.next();
								if (VotingSystemDB.updateUser(voter, email)) {
									utility.approvedNotification("Email updated successfully!");
								}
							}
							case 2 -> {
								System.out.println("Enter new password ? ");
								String password = PrintSystem.userInp.next();
								if (VotingSystemDB.updatePassword(voter, password)) {
									utility.approvedNotification("Password updated successfully!");
								}

							}

							case 3 -> {
								return false;
							}

							default -> {
								throw new IOException("Invalid Option");

							}
						}
					}

					case 3 -> {
						VoteDashboard.voteDashboardPanel(voter);
					}

					case 4 -> {
						isRun = false;
					}

					case 5 -> {
						return false;
					}
					default -> {
						throw new IOException("Invalid Option");

					}
				}
			}
		} catch (Exception e) {
			logger.error("Voter Dashboard Error: " + voter.getEmail() + " -> " + e);
			utility.declinedNotification("Voter Dashboard Error: " + e.getMessage());
		}

		return true;
	}
}
