package com.app.votingsystem.service.jdbc;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.app.votingsystem.model.candidate.Candidate;
import com.app.votingsystem.model.voter.Voter;
import com.app.votingsystem.utils.utility;
import org.json.JSONArray;
import org.json.JSONObject;

public class CandidateDB {
  private static final Connection connection = DB_Management.getInstance("VotingSystem").dbConnection();
  private static Statement statement = null;
  private static PreparedStatement preparedStatement = null;
  private static boolean is = true;
  private static CallableStatement callableStatement = null;

  public static boolean addCandidate(Candidate candidate) throws SQLException {
    String query = "CALL addcandidate(?,?,?,?,?);";
    callableStatement = connection.prepareCall(query);
    callableStatement.setString(1, candidate.getCandidateId());
    callableStatement.setString(2, candidate.getParty());
    callableStatement.setString(3, candidate.getManifesto());
    callableStatement.setString(4, candidate.getVoterId());
    callableStatement.setString(5, candidate.getElectionId());

    return !callableStatement.execute();

  }

  public static void isFirst(String electionId) throws SQLException {
    String query = "CALL getCandidates(?)";
    callableStatement = connection.prepareCall(query);
    callableStatement.setString(1, electionId);
    callableStatement.execute();
    is = false;
  }

  public static List<Candidate> getCandidates(String electionId) throws SQLException {
    ResultSet resultSet;
    if (is)
      isFirst(electionId);

    List<Candidate> candidates = new ArrayList<>();
    String query = "CALL getCandidates(?)";
    callableStatement = connection.prepareCall(query);
    callableStatement.setString(1, electionId);
    boolean hasNext = callableStatement.execute();
    while (hasNext) {
      resultSet = callableStatement.getResultSet();
      resultSet.setFetchSize(100);
      while (resultSet.next()) {
        String name = resultSet.getString("name");
        byte age = resultSet.getByte("age");
        String email = resultSet.getString("email");
        String voterId = resultSet.getString("voterId");
        String party = resultSet.getString("party");
        String manifesto = resultSet.getString("manifesto");
        String eletionId = resultSet.getString("electionId");
        String candidateId = resultSet.getString("candidateId");

        candidates.add(new Candidate(name, age, email, voterId, party, manifesto, eletionId, candidateId));

      }
      hasNext = callableStatement.getMoreResults();

    }

    System.out.println(candidates.size());
    callableStatement.close();
    return candidates;
  }


  public  static JSONArray getCandidatesJson(String electionId) throws SQLException {
    try{
      String query = "SELECT DISTINCT\n" +
              "  c.`voterId`,\n" +
              "  (SELECT name FROM `Voter` where id = c.`voterId`) CandidateName,\n" +
              "  c.`candidateId`,\n" +
              "  c.manifesto,\n" +
              "  c.party,\n" +
              "  ve.`electionId`\n" +
              "FROM\n" +
              "  `Voter_Election` ve,\n" +
              "  Candidate c\n" +
              "WHERE\n" +
              "  c.id = ve.`candidateId`\n" +
              "  AND ve.`electionId` = ?;";

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, electionId);
      ResultSet resultSet = preparedStatement.executeQuery();
      JSONArray jsonArray = new JSONArray();
      while (resultSet.next()) {
        JSONObject candidate = new JSONObject();
        String candidateId = resultSet.getString("candidateId");
        String party = resultSet.getString("party");
        String manifesto = resultSet.getString("manifesto");
        String voterId = resultSet.getString("voterId");
        String candidateName = resultSet.getString("candidateName");

        candidate.put("candidateId", candidateId);
        candidate.put("party", party);
        candidate.put("manifesto", manifesto);
        candidate.put("voterId", voterId);
        candidate.put("candidateName", candidateName);
        jsonArray.put(candidate);

      }
      return jsonArray;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public static Candidate getCandidate(String candidateId) throws SQLException {
    String query = "SELECT * FROM `Candidate` WHERE `candidateId` = ?";
    preparedStatement = connection.prepareStatement(query);
    preparedStatement.setString(1, candidateId);
    ResultSet resultSet  = preparedStatement.executeQuery();

    if (resultSet.next()) {
      Voter voter = VotingSystemDB.getVoter(resultSet.getString("voterId"));
      if (voter != null) {
        return new Candidate(resultSet.getString("party"), resultSet.getString("manifesto"), voter,
            resultSet.getString("electionId"), resultSet.getString("candidateId"));

      }
    }

    return null;
  }

  public static void showCandidates(String eleID) throws IOException, SQLException {
    utility.printCandidates(getCandidates(eleID));
  }

  public static int lastIdx() {
    int id = 0;
    ResultSet resultSet;
    String query = "SELECT MAX(id) FROM Candidate";
    try {
      statement = connection.createStatement();
      resultSet = statement.executeQuery(query);
      if (resultSet.next()) {
        id = resultSet.getInt(1);
      }
      return id;
    } catch (SQLException e) {
      utility.declinedNotification("Error: Getting maximum");
    }
    return id;
  }

  public static boolean isValidCandidateID(String candidateId) {
    String query = "SELECT candidateId FROM Candidate WHERE candidateId = ?";
    ResultSet resultSet;
    try {
      preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, candidateId);
      resultSet = preparedStatement.executeQuery();
      return resultSet.next();
    } catch (SQLException e) {
      utility.declinedNotification("Error: Checking if candidate is valid");
    }
    return false;
  }
}
