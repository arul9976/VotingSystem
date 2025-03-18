package votingsystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import election.ElectionDashboard;
import usermanagement.CandidateSystem;
import usermanagement.VoterSystem;
import utils.PrintSystem;
import utils.utility;

public class Main {
	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		// PrintSystem.printCenteredMessageWithBorderAndColor("Hello Word" ,true);
		boolean isRunning = true;
		while (isRunning) {

			try {

				PrintSystem.printMenu("Welcome to the Voting System", new String[] { "1. Voter Dashboard",
						"2. Election Dashboard", "3. Candidate Dashboard", "4. Exit" });
				int getInp = PrintSystem.safeReadInt("Choose Valid Option");

				switch (getInp) {
					case 1 -> {
						isRunning = VoterSystem.voterMenu();
					}
					case 2 -> {
						// System.out.println("\n--- Election Management ---");
						isRunning = ElectionDashboard.electionDashboardMenu();

					}
					case 3 -> {
						// System.out.println("\n--- Candidate Management ---");
						isRunning = CandidateSystem.candidateMenu();
					}
					case 4 -> {
						// System.out.println("\n--- Election Commission Officer Portal ---");
						isRunning = false;
					}
					case 6 -> {
						System.out.println("\nExiting the system. Goodbye!\n");
						isRunning = false;
					}

					default -> {
						utility.declinedNotification("Invalid Arguments");
					}
				}

			} catch (Exception e) {
				logger.error("Error in Main: {}", e);
				utility.declinedNotification("Error in Main: " + e.getMessage());
			}
		}
		System.out.println("\n------- Thank for using our product!!!  -------\n");
	}

}
