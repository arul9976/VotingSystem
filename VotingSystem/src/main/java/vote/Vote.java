package vote;

public class Vote {
  private String voterId, electionId, candidateId;

  public Vote() {
  }

  public Vote(String voterId, String electionId, String candidateId) {
    this.voterId = voterId;
    this.electionId = electionId;
    this.candidateId = candidateId;
  }

  public String getVoterId() {
    return voterId;
  }

  public void setVoterId(String voterId) {
    this.voterId = voterId;
  }

  public String getElectionId() {
    return electionId;
  }

  public void setElectionId(String electionId) {
    this.electionId = electionId;
  }

  public String getCandidateId() {
    return candidateId;
  }

  public void setCandidateId(String candidateId) {
    this.candidateId = candidateId;
  }


  public void castVote(){

  }

}
