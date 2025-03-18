package usermanagement;

public class ResultCandidates extends Candidate {

  int votes;

  public ResultCandidates(String cName, byte cAge, String email, String voterId, String party, String manifesto,
      String electionId, String candidateId, int votes) {
    super(cName, cAge, email, voterId, party, manifesto, electionId, candidateId);
    this.votes = votes;
  }

  public int getVotes() {
    return votes;
  }

  public void setVotes(int votes) {
    this.votes = votes;
  }

  

}
