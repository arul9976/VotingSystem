package vote;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import election.ElectionDashboard;
import jdbc.CandidateDB;
import jdbc.ElectionDB;
import usermanagement.Voter;
import utils.PrintSystem;
import utils.utility;

public class VoteDashboard {

  private static final Logger logger = LogManager.getLogger(VoteDashboard.class);

  public static boolean voteDashboardPanel(Voter voter) {
    boolean isRun = true;
    while (isRun) {

      // PrintSystem.userInp.nextLine();

      try {
        ElectionDB.updateStatus();

        PrintSystem.printMenu("Voting Dashboard Panel",
            new String[] { "1. Vote", "2. My Vote Information", "3. Show Candidates", "4. Election Info",
                "5. Previous Menu", "6. Exit" });
        byte userInp = (byte) PrintSystem.safeReadInt("");

        switch (userInp) {
          case 1 -> {
            ElectionDB.castVoting(voter);

          }
          case 2 -> {
            String eleID = utility.electionOption();
            if (!ElectionDB.validateVoterWithEC(voter.getVoterId(), eleID)) {
              utility.declinedNotification("You Not Voted On this Election...");
            }else {
              utility.approvedNotification("You Voted On this Election...");
            }
          }
          case 3 -> {
            String eleID = utility.electionOption();
            CandidateDB.showCandidates(eleID);
            PrintSystem.isContinue();
          }
          case 4 -> {
            ElectionDashboard.voterDashBoard(voter);
          }
          case 5 -> {
            isRun = false;
          }
          case 6 -> {
            return false;
          }
        }
      } catch (IOException e) {
        logger.error("Error in VoteDashboard: {}", e);
        utility.declinedNotification(e.getMessage());
      } catch (Exception e) {
        logger.error("Exception in VoteDashboard: {}", e);
        utility.declinedNotification(e.getMessage());
      }

    }

    return true;
  }
}
