package election;

import java.io.IOException;
import java.sql.SQLException;

import jdbc.ElectionDB;
import usermanagement.Election;
import utils.PrintSystem;
import utils.Validation;
import utils.utility;

public class ElectionSystem {

	public static boolean electionAdminMenu() {
		boolean isRun = true;
		try {

			while (isRun) {
				PrintSystem.printMenu("Election Management Panel", new String[] { "1. Create Election",
						"2. Close Election", "3. Show All Elections", "4. Election Details", "5. Previous Menu", "6. Exit" });

				byte eleOption = (byte) PrintSystem.safeReadInt("");

				switch (eleOption) {
					case 1 -> {
						System.out.println("\n--- Election Register Panel ---");

						String electionName = PrintSystem.getInput("Enter Election Name: ");

						String startDate = PrintSystem.getInput("Enter Start date (YYYY-MM-DD HH:mm):  ");
						String endDate = PrintSystem.getInput("Enter End date (YYYY-MM-DD HH:mm):  ");
						try {
							if (Validation.isValidDate(startDate, endDate)) {
								Election election = new Election(electionName, startDate, endDate, ElectionStatus.UPCOMING);
								if (ElectionDB.createElection(election)) {
									utility.approvedNotification("Election Created Succesfully....");
								} else {
									utility.declinedNotification("Failed to Create Election");
					
								}
							}

						} catch (SQLException e) {
							utility.declinedNotification("Error in Create Election creation");
						} catch (IOException e) {
							utility.declinedNotification("Invalid Arguments");
						} catch (Exception e) {
							utility.declinedNotification("Enter Valid Information");

						}
					}
					case 2 -> {
						System.out.println("\n--- Election Update Panel ---");
						String electionID = utility.electionOption();
						if (ElectionDB.closeElection(electionID)) {
							utility.approvedNotification("Election Closed Succesfully....");
							PrintSystem.isContinue();

						}

					}

					case 3 -> {
						ElectionDB.showElections();
					}

					case 4 -> {
						// ElectionDataManagement.showElections();
						String electionID = utility.electionOption();
						Election election = ElectionDB.getElection(electionID);

						if (election != null) {
							System.out.println(election);
							PrintSystem.isContinue();
						} else
							utility.declinedNotification("Given ID is Not Valid : " + electionID);
					}

					case 5 -> {
						isRun = false;
					}
					case 6 -> {
						return false;
					}
					default -> {
						throw new IOException("Invalid Option -> " + eleOption);

					}
				}
			}

		} catch (SQLException e) {
			utility.declinedNotification("Error: Creating Election failed");
		} catch (IOException e) {
			utility.declinedNotification("Invalid Input!");
		} catch (Exception e) {
			utility.declinedNotification("Something went wrong");
		}
		return true;
	}

	// private static boolean updateElection(Election election) throws IOException {
	// 	PrintSystem.printMenu(("Update Election : " + election.getElectionId()),
	// 			new String[] { "1. Close Election", "2. Extend Election Date", "3. Exit" });
	// 	byte option = PrintSystem.userInp.nextByte();
	// 	PrintSystem.userInp.nextLine();

	// 	switch (option) {
	// 		case 1 -> {
	// 			election.setStatus(ElectionStatus.ENDED);

	// 			if (ElectionDataManagement.updateElectionDetails(election)) {
	// 				System.out.println("Election Updated Successfully");
	// 			} else {
	// 				System.out.println("Election Update Failed");
	// 			}

	// 		}

	// 		case 3 -> {
	// 			System.out.println("\nExiting Sucessfully...\n");
	// 			return false;
	// 		}
	// 		default -> {
	// 			throw new IOException("Invalid Option....");
	// 		}
	// 	}
	// 	return true;
	// }
}
