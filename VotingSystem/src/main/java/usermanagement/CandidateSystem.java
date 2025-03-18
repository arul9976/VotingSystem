package usermanagement;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdbc.ElectionDB;
import utils.PrintSystem;
import utils.utility;

public class CandidateSystem {
	private static final Logger logger = LogManager.getLogger(CandidateSystem.class);

	public static boolean candidateMenu() {

		boolean isRun = true;
		try {
			ElectionDB.updateStatus();
			while (isRun) {
				PrintSystem.printMenu("Candidate Management Panel",
						new String[] { "1. Register Candidate", "2. Login Candidate", "3. Previous Menu", "4. Exit" });
				byte userInp = (byte) PrintSystem.safeReadInt("Enter Valid Options");

				switch (userInp) {
					case 1 -> {
						System.out.println("\n--- Candidate Register Panel ---");
						utility.getCandidateInfoFromUser();
					}
					case 2 -> {
						System.out.println("\n--- Candidate Login Panel ---");
						Voter voter = utility.getVoterInfoFromUserLogin();
						utility.getCandidateInfoFromUser(voter);

					}
					case 3 -> {
						isRun = false;
					}
					case 4 -> {
						return false;
					}
					default -> {
            throw new IOException("Invalid Arguments");
          }
				}

			}

		} catch (IOException e) {
			logger.error("Error in Candidate System: {}", e);
			utility.declinedNotification(e.getMessage());

		} catch (Exception e) {
			logger.error("Exception in Candidate System: {}", e);
			utility.declinedNotification("Invalid Arguments " + e.getMessage());
		}
		return true;
	}

	// private static void CandidateManagementSystem(Candidate candidate) {

	// }
}
