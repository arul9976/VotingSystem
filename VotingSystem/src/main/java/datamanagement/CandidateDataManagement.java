package datamanagement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import usermanagement.Candidate;
import utils.utility;

public class CandidateDataManagement {

  private static final File FILE = new File("candidates.json");
  private static final ObjectMapper objectMapper = new ObjectMapper();
  // private static 

  private static List<Candidate> getCandidateDataBase() {
    try {
      List<Candidate> totalCandidates = objectMapper.readValue(FILE,
          objectMapper.getTypeFactory().constructCollectionType(List.class, Candidate.class));
      return totalCandidates;
    } catch (IOException e) {
      System.out.println(e.toString());
    }

    return null;
  }

  public static void addCandidate(Candidate candidate) throws IOException {
    List<Candidate> candidateDataBase = getCandidateDataBase();
    candidateDataBase.add(candidate);
    objectMapper.writeValue(FILE, candidateDataBase);
  }

  public static void showCandidates(String electionID) throws IOException {
    List<Candidate> candidates = filterCandidates(electionID);
    utility.printCandidates(candidates);

  }

  public static List<Candidate> filterCandidates(String eleID) {
    List<Candidate> electionCandidates = new ArrayList<>();
    List<Candidate> totalCandidates = getCandidateDataBase();

    for (Candidate candidate : totalCandidates) {
      if (eleID.equals(candidate.getElectionId())) {
        electionCandidates.add(candidate);
      }
    }
    return electionCandidates;

  }

  public static Candidate getCandidate(String cID) {
    List<Candidate> candidateDataBase = getCandidateDataBase();
    for (Candidate candidate : candidateDataBase) {
      if (candidate.getCandidateId().equals(cID))
        return candidate;

    }
    return null;
  }

  private static void updateCandidate(Candidate candidate) throws IOException {
    List<Candidate> candidateDataBase = getCandidateDataBase();
    candidateDataBase.set(candidate.getMyIDX(), candidate);
    objectMapper.writeValue(FILE, candidateDataBase);
  }

  public static boolean isValidCandidateID(String cID) {
    List<Candidate> candidateDataBase = getCandidateDataBase();
    for (Candidate candidate : candidateDataBase) {
      if (cID.equals(candidate.getCandidateId())) {
        return true;
      }
    }
    return false;
  }

  public static void incrementVote(String canID) throws IOException {
    Candidate candidate = getCandidate(canID);
    if (candidate != null) {
      // candidate.incrementVote();
      updateCandidate(candidate);
      return;
    }

    throw new IOException("Invalid Candidate ID");
  }


  public static int lastIdx(){
    List<Candidate> candidateDataBase = getCandidateDataBase();
    return candidateDataBase.size();
  }
}
