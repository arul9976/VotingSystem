package com.app.votingsystem.utils;

import com.app.votingsystem.model.candidate.Candidate;
import com.app.votingsystem.model.candidate.ResultCandidates;
import com.app.votingsystem.model.election.Election;
import com.app.votingsystem.model.election.ElectionStatus;
import com.app.votingsystem.model.voter.Voter;
import com.app.votingsystem.service.AdminDataManagement;
import com.app.votingsystem.service.jdbc.CandidateDB;
import com.app.votingsystem.service.jdbc.ElectionDB;
import com.app.votingsystem.service.jdbc.VotingSystemDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class utility {
    private static final Logger logger = LogManager.getLogger(utility.class);

    public static Voter getVoterInfoFromUser() throws Exception, IOException, SQLException {
        String username = PrintSystem.getInput("Enter your username: ");
        byte age = (byte) PrintSystem.safeReadInt("Enter Your Age: ", "Enter Valid Age");

        if (age < 18 || age > 110) {
            throw new IOException("You Are Not Eligible");
        }

        String[] getEmailandPassword = getEmail(false);
        String email = getEmailandPassword[0].toLowerCase();
        String password = getEmailandPassword[1];

        Voter voter = new Voter(username, age, email, password);

        if (VotingSystemDB.ValidateRegisterUser(voter)) {
            logger.info("Registration successful for user " + voter.getEmail());
            approvedNotification("You have register in successfully!");
            return voter;
        } else {
            logger.info("Registration failed for user " + voter.getEmail());
            declinedNotification("Register failed!");
        }
        return null;
    }

    private static String[] getEmail(boolean isLog) {
        String email = "";
        String password = "";

        while (true) {
            if (email.isEmpty())
                email = PrintSystem.getInput("Enter your email: ");
            if (!Validation.isValidEmail(email)) {
                logger.error("Invalid email address or password: " + email);
                utility.declinedNotification("Given Email is Not Valid!!!");
                PrintSystem.userInp.nextLine();
                email = "";
                continue;
            }
            password = PrintSystem.getInput("Enter your password: ");
            if (!Validation.isValidPassword(password)) {
                // System.out.println("dede");
                logger.error("Invalid email address or password: " + email);
                utility.declinedNotification("Password is Must be Strong!!!");
                PrintSystem.userInp.nextLine();
                continue;
            } else if (!isLog) {
                password = Validation.hashPassword(password);
            }
            break;
        }
        return new String[]{email, password};
    }

    public static Voter getVoterInfoFromUserLogin() throws Exception, IOException {

        String[] getEmailandPassword = getEmail(true);
        String userId = getEmailandPassword[0].toLowerCase();
        String password = getEmailandPassword[1];

        Object obj = VotingSystemDB.ValidateLoginUser(userId, password);
        switch (obj) {
            case null -> {
                logger.info(" login failed with error code " + obj.toString());
                throw new IOException("Enter Valid Information");
            }
            case Voter voter -> {
                logger.info("Successfully logged In");
                utility.approvedNotification("Successfully logged In");
                return voter;
            }
            default -> {
            }
        }
        return null;

    }

    public static void getCandidateInfoFromUser() throws Exception, IOException, SQLException {
        Voter voter = getVoterInfoFromUser();
        PrintSystem.userInp.nextLine();
        String partyName = PrintSystem.getInput("Enter your Party Name: ");
        String manifesto = PrintSystem.getInput("Enter your Manifesto: ");
        String electionId = utility.electionOption();

        if (!ElectionDB.isUpComingElection(electionId)) {

            throw new IOException("Election is Ongoing or Ended");
        }
        if (ElectionDB.isValidElectionId(electionId)) {
            Candidate candidate = new Candidate(partyName, manifesto, voter, electionId);
            candidateValidationAndApproval(candidate);
        } else {
            throw new IOException("Something Went Wrong");
        }

    }

    public static void getCandidateInfoFromUser(Voter voter) throws Exception, IOException {
        PrintSystem.userInp.nextLine();

        String partyName = PrintSystem.getInput("Enter your Party Name: ");
        String manifesto = PrintSystem.getInput("Enter your Manifesto: ");
        String electionId = utility.electionOption();
        if (!ElectionDB.isUpComingElection(electionId)) {
            throw new IOException("Election is Ongoing or Ended");
        }
        if (ElectionDB.isValidElectionId(electionId)) {
            Candidate candidate = new Candidate(partyName, manifesto, voter, electionId);
            candidateValidationAndApproval(candidate);
        } else {
            throw new IOException("Something Went Wrong");
        }
    }

    private static void candidateValidationAndApproval(Candidate candidate) throws IOException, SQLException {

        if (isValidCandidate(candidate)) {
            if (CandidateDB.addCandidate(candidate)) {
                logger.info("You have registered as a candidate");
                approvedNotification("You have registered as a Candidate!");
            } else {
                logger.info("Candidate approval failed!");
                declinedNotification("Candidate approval failed! Please try again");
            }
        } else {
            throw new IOException("Given Information is not Valid, Please try again later...");

        }
    }

    public static boolean adminLogIn() {
        String email = PrintSystem.getInput("Enter admin email: ");
        String pass = PrintSystem.getInput("Enter admin password: ");
        return AdminDataManagement.valiDateAdmin(email, pass);
    }

    public static void printAllElections(List<Election> elections) {
        if (elections.size() < 1) {
            declinedNotification("No Elections Found");
            return;
        }

        System.out.println(
                "+-----------+-----------------+-------------------------+-------------------+-------------------+--------------+");
        System.out.println(
                "|  OPTION   | Election ID     | Election Name           | Start Date        | End Date          | Status       |");
        System.out.println(
                "+-----------+-----------------+-------------------------+-------------------+-------------------+--------------+");
        int count = 0;
        for (Election election : elections) {
            System.out.printf("| %-9s | %-15s | %-23s | %-17s | %-17s | %-12s |\n", "    " + ++count,
                    election.getElectionId(),
                    election.getElectionName(), election.getStartDate()
                            .substring(0, 16),
                    election.getEndDate().substring(0, 16), election.getStatus());
        }

        System.out.println(
                "+-----------+-----------------+-------------------------+-------------------+-------------------+--------------+");
        PrintSystem.isContinue();
    }

    public static void printCandidates(List<Candidate> candidates) throws IOException {

        if (candidates.size() < 1) {
            throw new IOException("No Candidates Found");
            // declinedNotification();
        }
        System.out.println();
        System.out.println(
                PrintSystem.GREEN
                        + "+----------------+-------------------------+-------------------------+----------------------------------------+-----------------+"
                        + PrintSystem.RESET);
        System.out.println(
                PrintSystem.CYAN +
                        "| Candidate ID     | Name                    | Party                   | Manifesto                            | Election ID     |"
                        + PrintSystem.RESET);
        System.out.println(
                PrintSystem.GREEN
                        + "+----------------+-------------------------+-------------------------+----------------------------------------+-----------------+"
                        + PrintSystem.RESET);

        for (Candidate candidate : candidates) {
            System.out.printf(PrintSystem.YELLOW
                            + "| %-16s | %-23s | %-23s | %-36s | %-15s |\n" + PrintSystem.RESET,
                    candidate.getCandidateId(),
                    candidate.getName(),
                    candidate.getParty(),
                    candidate.getManifesto(),
                    candidate.getElectionId());
            System.out.println(
                    PrintSystem.GREEN +
                            "+----------------+-------------------------+-------------------------+----------------------------------------+-----------------+"
                            + PrintSystem.RESET);

        }
        System.out.println();

    }

    public static void printElectionResults(List<ResultCandidates> results, String eleID, ResultCandidates winner,
                                            int totalVotes, boolean isDraw) throws SQLException {
        if (results.isEmpty())
            return;

        String LINE = "─".repeat(100);

        Election election = ElectionDB.getElection(eleID);
        String electionText = "Election: " + election.getElectionName();

        System.out.println();
        System.out.println(LINE);
        System.out.println(String.format("| %-122s  |",
                PrintSystem.BLUE + electionText + PrintSystem.RESET + PrintSystem.YELLOW + "    ("
                        + (statusFormatter(election.getStatus())) + ")" + PrintSystem.RESET));
        System.out.println(LINE);
        System.out.println(String.format("| %-18s | %-20s | %-16s | %-12s | %-18s |", "Candidate ID", "Candidate",
                "Votes Received", "Party", "Vote %"));
        System.out.println(LINE);

        for (ResultCandidates result : results) {
            double votePercentage = ((double) result.getVotes() / totalVotes) * 100;
            System.out.println(String.format(
                    "| " + PrintSystem.GREEN + "%-18s" + PrintSystem.RESET
                            + " |" + (result.getVotes() == winner.getVotes() ? PrintSystem.YELLOW : "")
                            + " %-19s " + PrintSystem.RESET
                            + " | %-16d | " + PrintSystem.CYAN + "%-12s"
                            + PrintSystem.RESET + " | " + PrintSystem.CYAN + "%-18s"
                            + PrintSystem.RESET + " |",
                    result.getCandidateId(),
                    result.getName(),
                    result.getVotes(),
                    result.getParty(),
                    votePercentage));
            System.out.println(LINE);
        }

        System.out.println(LINE);
        System.out.println(String.format("| %-18s | %-20s | %-16d | %-12s | %-18s |",
                "Total Votes", "", totalVotes, "", ""));
        System.out.println(LINE);
        if (election.getStatus().equals(ElectionStatus.ENDED) && !isDraw) {
            System.out.println(String.format("| %-27s | %-29s | %-52s |",
                    (PrintSystem.GREEN + "      Winner  " + PrintSystem.RESET),
                    (PrintSystem.GREEN + winner.getName() + PrintSystem.RESET),
                    "   Percentage    |  " + ((double) winner.getVotes() / totalVotes) * 100));
            System.out.println(LINE);
        } else if (election.getStatus().equals(ElectionStatus.ENDED) && isDraw) {
            System.out.println(String.format("| %-27s | %-29s | %-52s |",
                    "Election Draw ", " None ", " Next Election Coming Soon "));
            System.out.println(LINE);
        }

        System.out.println();
        PrintSystem.isContinue();
    }

    public static void approvedNotification(String notifyMsg) {
        printCenteredMessageWithBorderAndColor(notifyMsg, true);
        PrintSystem.isContinue();
    }

    public static void declinedNotification(String notifyMsg) {
        printCenteredMessageWithBorderAndColor(notifyMsg, false);
        PrintSystem.isContinue();

    }

    private static void printCenteredMessageWithBorderAndColor(String message, boolean isPositive) {
        int messageLength = message.length();
        int consoleWidth = 50;
        int spacesBefore = (consoleWidth - messageLength - 2) / 2;
        String LINE = "─".repeat(consoleWidth);

        String centeredMessage = "|" + " ".repeat(spacesBefore) + PrintSystem.BOLD
                + (isPositive ? PrintSystem.GREEN : PrintSystem.RED)
                + message
                + " ".repeat(consoleWidth - messageLength - spacesBefore - 2) + "|" + PrintSystem.RESET;

        System.out
                .println(PrintSystem.BOLD + (isPositive ? PrintSystem.GREEN : PrintSystem.RED) + LINE + PrintSystem.RESET);
        System.out.println(PrintSystem.BOLD + (isPositive ? PrintSystem.GREEN : PrintSystem.RED) + centeredMessage);
        System.out
                .println(PrintSystem.BOLD + (isPositive ? PrintSystem.GREEN : PrintSystem.RED) + LINE + PrintSystem.RESET);
    }

    private static boolean isValidCandidate(Candidate candidate) {
        return candidate.getParty().length() > 0 && candidate.getManifesto().length() > 0;
    }

    private static String statusFormatter(ElectionStatus electionStatus) {
        return "" + (electionStatus.equals(ElectionStatus.ONGOING) ? PrintSystem.YELLOW + electionStatus
                : PrintSystem.RED + electionStatus) + PrintSystem.RESET;
    }

    // private static String getWinner(List<Candidate> candidates) {
    // String winner = candidates.get(0).getName();
    // int votes = candidates.get(0).getVotes();
    // for (int i = 1; i < candidates.size(); i++) {
    // Candidate candidate = candidates.get(i);
    // if (candidate.getVotes() == votes) {
    // return "DRAW";
    // } else if (candidate.getVotes() > votes) {
    // winner = candidate.getName();
    // votes = candidate.getVotes();
    // }
    // }
    // return winner;
    // }



    public static String electionOption() throws Exception, IOException {
//        ElectionDB.showAndGetElections();
        System.out.print("Enter Election Option ex:1 :");

        int opt = PrintSystem.safeReadInt("");
        return "Election2025_0" + opt;

    }

    public static String candidateOption(String electionID) throws Exception, IOException {
        CandidateDB.showCandidates(electionID);
        System.out.print("Enter Candidate Option ex:1 :");
        int opt = PrintSystem.safeReadInt("");

        if (opt > CandidateDB.lastIdx()) {
            throw new IOException("Select Valid Candidate");
        }

        return "CANDIDATE2025_0" + opt;
    }

    public static JSONObject getResponseData(BufferedReader reader) throws Exception, IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        System.out.println(sb.toString());
        return new JSONObject(sb.toString());
    }

    public static void printResponseDatas(String data, JSONObject jsonData) {
        System.out.println("Json Data\n" + jsonData.toString());
        System.out.println("Object Mapper Data\n" + data);
    }

//    public static JSONObject pojoToJson(Object pojo) {
//        JSONObject jsonObject = new JSONObject();
//        for (Field field : person.getClass().getDeclaredFields()) {
//            field.setAccessible(true);  // In case fields are private
//            jsonObject.put(field.getName(), field.get(person));
//        }
//    }

}
