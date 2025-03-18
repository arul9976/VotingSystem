package datamanagement;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import usermanagement.Candidate;
import usermanagement.Voter;
import utils.utility;
import vote.Vote;

public class VoteDataManagement {
  private static final File FILE = new File("votes.json");
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final List<Vote> voteDataBase = getVoteDataBase();

  public static void castVoting(Voter voter) throws Exception, IOException {
    String voterId = voter.getVoterId();

    String eletionID = utility.electionOption();

    if (!validateVoterWithEC(voterId, eletionID, false)) {
      utility.declinedNotification("You Already Voted");
      return;
    }
    String candidateID = utility.candidateOption(eletionID);
    // PrintSystem.getInput("Enter Candidate ID: ");

    if (ElectionDataManagement.isOngoingElection(eletionID)
        && CandidateDataManagement.isValidCandidateID(candidateID)) {
      Vote vote = new Vote(voterId, eletionID, candidateID);
      if (castVote(vote)) {
        CandidateDataManagement.incrementVote(candidateID);
        utility.approvedNotification("You Have Successfully Cast Your Vote");
      }
    } else
      throw new IOException("Given Information is Not Valid");

  }

  private static List<Vote> getVoteDataBase() {
    try {
      List<Vote> totalVotes = objectMapper.readValue(FILE,
          objectMapper.getTypeFactory().constructCollectionType(List.class, Vote.class));
      return totalVotes;
    } catch (IOException e) {
      System.out.println(e.toString());
    }

    return null;
  }

  private static boolean castVote(Vote vote) throws IOException {
    voteDataBase.add(vote);
    objectMapper.writeValue(FILE, voteDataBase);
    // setVoteDataBase(voteDataBase);
    return true;
  }

  public static boolean validateVoterWithEC(String vId, String eId, boolean fromCheck) {
    // System.out.println(voteDataBase.size());
    for (Vote vote : voteDataBase) {
      if (vId.equals(vote.getVoterId()) && eId.equals(vote.getElectionId())) {
        // System.out.println("-----> NOT VALID");
        if (fromCheck) {
          iamVoteTo(CandidateDataManagement.getCandidate(vote.getCandidateId()));
        }
        return false;
      }
    }
    // System.out.println("-----> VALID");
    return true;
  }

  // private static void setVoteDataBase(List<Vote> voteDataBase) {
  // VoteDataManagement.voteDataBase = voteDataBase;
  // }

  public static int getResultUsingElectionID(String eleID) {
    int totalVotes = 0;
    for (Vote vote : voteDataBase) {
      if (eleID.equals(vote.getElectionId())) {
        totalVotes++;
      }
    }
    return totalVotes;
  }

  private static void iamVoteTo(Candidate candidate) {
    utility.approvedNotification("You Voted : " + candidate.getCandidateId());
  }
}
