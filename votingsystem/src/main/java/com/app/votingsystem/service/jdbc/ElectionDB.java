package com.app.votingsystem.service.jdbc;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.app.votingsystem.model.election.ElectionStatus;
import com.app.votingsystem.utils.utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.app.votingsystem.model.election.Election;
import com.app.votingsystem.model.candidate.ResultCandidates;
import com.app.votingsystem.model.voter.Voter;
import com.app.votingsystem.model.Vote;
import org.json.JSONArray;
import org.json.JSONObject;

public class ElectionDB {
  private static final Logger logger = LogManager.getLogger(ElectionDB.class);
  private static final Connection connection = DB_Management.getInstance("VotingSystem").dbConnection();
  private static Statement statement = null;
  private static PreparedStatement preparedStatement = null;
  private static CallableStatement callableStatement = null;

  public static boolean createElection(Election election) throws SQLException {
    String query = """
        INSERT INTO
          `Election` (
            `electionId`,
            `electionName`,
            `startDate`,
            `endDate`,
            `status`
          )
        VALUES
          (?, ?, ?, ?, ?);
        """;

    preparedStatement = connection.prepareStatement(query);
    preparedStatement.setString(1, election.getElectionId());
    preparedStatement.setString(2, election.getElectionName());
    preparedStatement.setString(3, election.getStartDate());
    preparedStatement.setString(4, election.getEndDate());
    preparedStatement.setString(5, election.getStatus().toString());
    return preparedStatement.executeUpdate() > 0;
  }

  public static void castVoting(Voter voter) throws Exception, IOException {
    String voterId = voter.getVoterId();

    String eletionID = utility.electionOption();

    if (validateVoterWithEC(voterId, eletionID)) {
      utility.declinedNotification("You Already Voted");
      return;
    }
    String candidateID = utility.candidateOption(eletionID);

    if (isOngoingElection(eletionID)
        && CandidateDB.isValidCandidateID(candidateID)) {
      Vote vote = new Vote(voterId, eletionID, candidateID);
      if (castVote(vote)) {
        utility.approvedNotification("You Have Successfully Cast Your Vote");
      }
    } else
      throw new IOException("Given Information is Not Valid");

  }

  private static boolean castVote(Vote vote) throws SQLException {
    String query = "CALL castVote(?, ?, ?)";
    callableStatement = connection.prepareCall(query);
    callableStatement.setString(1, vote.getVoterId());
    callableStatement.setString(2, vote.getCandidateId());
    callableStatement.setString(3, vote.getElectionId());

    return !callableStatement.execute();
  }

  public static boolean validateVoterWithEC(String voterId, String eletionID) {
    ResultSet resultSet;

    String query = """
        SELECT name, age, `candidateId`  FROM `Voter_Election` ve
          JOIN `Voter` v ON v.`id` = ve.`voterId`
          JOIN `Election` e ON e.`id` = ve.`electionId`
        WHERE
          v.`voterId` = ? AND e.`electionId` = ?;
                """;
    try {

      preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, voterId);
      preparedStatement.setString(2, eletionID);
      resultSet = preparedStatement.executeQuery();
      return resultSet.next();

    } catch (SQLException e) {
      logger.error("Exception in ValidateVoterWithEC", e);
      utility.declinedNotification("Error With Checking V With EC");
    }
    return false;
  }

  public static int lastIdx() {
    ResultSet resultSet;

    int lastIdx = 0;
    try {
      statement = connection.createStatement();
      resultSet = statement.executeQuery("SELECT MAX(id) FROM `Election`;");
      if (resultSet.next()) {
        lastIdx = resultSet.getInt(1);
      }
    } catch (SQLException e) {
      logger.error("Exception in getting maximum index", e);
      utility.declinedNotification("Error getting maximum index");
    }
    return lastIdx;
  }

  public static JSONArray showAndGetElections(String electionType) throws SQLException {

    ResultSet resultSet;
    System.out.println("Election Type: " + electionType);

    String query = "SELECT " +
            "e.`electionName` ElectionName, " +
            "e.`id` ElectionId, " +
            "COUNT(DISTINCT c.id) Candidates, " +
            "COUNT(ve.`electionId`) TotalVotes, " +
            "e.`startDate`, " +
            "e.`endDate` " +
            "FROM " +
            "`Voter_Election` ve " +
            "JOIN `Voter` v ON v.id = ve.`voterId` " +
            "JOIN `Election` e ON e.id = ve.`electionId` " +
            "JOIN `Candidate` c ON c.id = ve.`candidateId` " +
            "WHERE " +
            "e.status = ? " +
            "GROUP BY " +
            "e.`electionName`, e.`startDate`, e.`endDate`, e.`status`, e.`id` " +
            "ORDER BY " +
            "e.`startDate`";

    statement = connection.createStatement();
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.setString(1, electionType);
    resultSet = preparedStatement.executeQuery();
    JSONArray elections = new JSONArray();

    while (resultSet.next()) {
      JSONObject election = new JSONObject();
      // System.out.println(resultSet.getRow());
      String electionName = resultSet.getString("ElectionName");
      String startDate = resultSet.getString("startDate");
      String endDate = resultSet.getString("endDate");
      String totalVotes = resultSet.getString("totalVotes");
      int electionId = resultSet.getInt("ElectionId");
      String totalCandidates = resultSet.getString("Candidates");

      election.put("electionName", electionName);
      election.put("electionId", electionId);
      election.put("startDate", startDate);
      election.put("endDate", endDate);
      election.put("totalVotes", totalVotes);
      election.put("totalCandidates", totalCandidates);
      election.put("status", electionType);

      elections.put(election);
    }

    return elections;
  }

  public static boolean closeElection(String eletionId) throws SQLException {
    String query = """
        UPDATE `Election`
        SET
          status = ?
        WHERE
          `electionId` = ?;
        """;

    preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, ElectionStatus.ENDED.ordinal());
    preparedStatement.setString(2, eletionId);
    return preparedStatement.executeUpdate() > 0;
  }

  public static Election getElection(String eletionId) throws SQLException {
    ResultSet resultSet;
    String query = "SELECT * FROM `Election` WHERE `electionId` = ?";
    preparedStatement = connection.prepareStatement(query);
    preparedStatement.setString(1, eletionId);
    resultSet = preparedStatement.executeQuery();

    if (resultSet.next()) {
      return new Election(resultSet.getString("electionId"), resultSet.getString("electionName"),
          resultSet.getString("startDate"), resultSet.getString("endDate"),
          ElectionStatus.valueOf(
              resultSet.getString("status")));
    }

    return null;
  }

  public static void getWinner(String electionId) throws SQLException {
    ResultSet resultSet;
    List<ResultCandidates> candidates = new ArrayList<>();
    String query = "CALL getWinner(?, ?);";
    callableStatement = connection.prepareCall(query);
    callableStatement.setString(1, electionId);
    callableStatement.registerOutParameter(2, Types.INTEGER);
    int totalVotes = 0;

    if (callableStatement.execute()) {

      resultSet = callableStatement.getResultSet();
      while (resultSet.next()) {
        String name = resultSet.getString("name");
        byte age = resultSet.getByte("age");
        String email = resultSet.getString("email");
        String voterId = resultSet.getString("voterId");
        String party = resultSet.getString("party");
        String manifesto = resultSet.getString("manifesto");
        String eletionId = resultSet.getString("electionId");
        String candidateId = resultSet.getString("candidateId");
        int votes = resultSet.getInt("totalVotes");
        totalVotes += votes;
        candidates
            .add(new ResultCandidates(name, age, email, voterId, party, manifesto, eletionId, candidateId, votes));

      }
    }

    if (candidates.isEmpty())
      throw new SQLException("There are no candidates!");

    int maxVotes = callableStatement.getInt(2);

    List<ResultCandidates> winners = candidates.stream().filter((c) -> maxVotes == c.getVotes()).toList();

    if (winners.size() == 1) {
      utility.printElectionResults(candidates, electionId, winners.get(0), totalVotes, false);
    } else {
      utility.printElectionResults(candidates, electionId, winners.get(0), totalVotes, true);
    }

  }

  public static boolean isOngoingElection(String eleID) throws SQLException {
    return getElection(eleID).getStatus().equals(ElectionStatus.ONGOING);
  }

  public static boolean isUpComingElection(String eleID) throws SQLException {
    return getElection(eleID).getStatus().equals(ElectionStatus.UPCOMING);
  }

  public static boolean isValidElectionId(String eletionId) throws SQLException {
    return getElection(eletionId) != null;
  }


  public static void updateStatus() throws Exception {
    try {
      String query = """
          UPDATE `Election`
          SET
            `status` = 'ONGOING'
          WHERE
            `startDate` < CURTIME();
          """;

      preparedStatement = connection.prepareStatement(query);
      preparedStatement.executeUpdate();

      String queryForEnd = """
          UPDATE `Election`
          SET
            `status` = 'ENDED'
          WHERE
            `endDate` < CURTIME();
          """;

      preparedStatement = connection.prepareStatement(queryForEnd);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      logger.error("Exception in updating election status", e);
      utility.declinedNotification("Could not update Election");
    }
  }

}
