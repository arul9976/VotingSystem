package com.app.votingsystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.app.votingsystem.service.jdbc.CandidateDB;
import com.app.votingsystem.model.election.ElectionStatus;
import com.app.votingsystem.model.candidate.Candidate;
import com.app.votingsystem.model.election.Election;
import com.app.votingsystem.utils.Validation;
import com.app.votingsystem.utils.utility;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ElectionDataManagement {
  private static final File FILE = new File("elections.json");

  private static final ObjectMapper objectMapper = new ObjectMapper();

  // private static List<Election> elections = getElections();

  public static void addElections(Election election) {
    List<Election> elections = getElections();
    try {
      elections.add(election);
      objectMapper.writeValue(FILE, elections);

    } catch (IOException e) {
      System.out.println(e.toString());
    }
  }

  private static List<Election> getElections() {
    try {
      List<Election> elects = objectMapper.readValue(FILE,
              objectMapper.getTypeFactory().constructCollectionType(List.class, Election.class));
      return elects;
    } catch (IOException e) {
      System.out.println(e.toString());
    } catch (Exception e) {
      System.out.println(e.toString());
    }

    return null;
  }

  public static Election getElection(String eleId) {
    try {
      List<Election> elections = getElections();
      for (Election ele : elections) {
        if (ele.getElectionId().equals(eleId)) {
          return ele;
        }
      }
      throw new IOException();
    } catch (IOException e) {
      e.toString();
    }

    return null;

  }

  public static boolean updateElectionDetails(Election election) {
    List<Election> elections = getElections();
    try {

      // for (int i = 0; i < elections.size(); i++) {
      // if (election.getElectionId().equals(elections.get(i).getElectionId())) {
      elections.set(election.getMyIDX(), election);
      objectMapper.writeValue(FILE, elections);
      return true;
      // }
      // }
    } catch (IOException e) {
      e.toString();
    }
    return false;
  }

  public static void showElections() throws IOException {
    List<Election> elections = getElections();
    for (Election election : elections) {
      Validation.currStatus(election);
    }
    utility.printAllElections(elections);
  }

  public static boolean isValidElectionId(String eleID) throws IOException {

    List<Election> elections = getElections();
    for (Election election : elections) {
      if (eleID.equals(election.getElectionId())) {
        return true;
      }
    }
    return false;
  }

  public static void getElectionResults(String eleID) throws Exception, SQLException {
    List<Candidate> candidates = CandidateDB.getCandidates(eleID);
    if (!candidates.isEmpty()){}
    // utility.printElectionResults(candidates, eleID);
    // else {
    //   utility.declinedNotification("No Votes Found");
    // }
  }

  public static boolean isOngoingElection(String eleID) {
    return getElection(eleID).getStatus().equals(ElectionStatus.ONGOING);
  }

  public static boolean isUpComingElection(String eleID) {
    return getElection(eleID).getStatus().equals(ElectionStatus.UPCOMING);
  }

  public static int lastIdx() {
    List<Election> elections = getElections();
    return elections.size();
  }

  public static void updateStatus() throws IOException {
    List<Election> elections = getElections();
    Validation.currStatus(elections);
    objectMapper.writeValue(FILE, elections);

  }

}
