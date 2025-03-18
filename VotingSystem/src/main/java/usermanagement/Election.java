package usermanagement;

import election.ElectionManagementSystem;
import election.ElectionStatus;
import jdbc.ElectionDB;

public class Election implements ElectionManagementSystem {
  private static int id = ElectionDB.lastIdx();
  private String electionId;
  private String electionName;
  private String startDate;
  private String endDate;
  private ElectionStatus status;
  private int myIDX;

  public Election() {
    id = ElectionDB.lastIdx();
  }

  public Election(String electionName, String startDate, String endDate, ElectionStatus status) {
    this();
    this.electionName = electionName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
    this.myIDX = id;
    this.electionId = "Election2025_0" + ++id;
  }

  public Election(String electionId, String electionName, String startDate, String endDate, ElectionStatus status) {
    this.electionName = electionName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
    this.myIDX = id;
    this.electionId = electionId;
  }

  public String getElectionId() {
    return electionId;
  }

  public void setElectionId(String electionId) {
    this.electionId = electionId;
  }

  public String getElectionName() {
    return electionName;
  }

  public void setElectionName(String electionName) {
    this.electionName = electionName;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public ElectionStatus getStatus() {
    return status;
  }

  public void setStatus(ElectionStatus status) {
    this.status = status;
  }

  @Override
  public void closeElection() {

  }

  @Override
  public void createElection() {

  }

  @Override
  public void displayResults() {

  }

  @Override
  public String toString() {
    String reset = "\u001B[0m";
    // String blue = "\u001B[34m";
    String green = "\u001B[32m";
    String white = "\u001B[37m";
    String yellow = "\u001B[33m";
    String bold = "\u001B[1m";

    StringBuilder sb = new StringBuilder();
    sb.append(bold).append(green).append("+---------------------------------------+\n").append(reset);
    sb.append(bold).append(green).append("|          ELECTION INFORMATION         |\n").append(reset);
    sb.append(bold).append(green).append("+---------------------------------------+\n").append(reset);
    sb.append(bold).append(white).append("| ").append(yellow).append("Election Name   ").append(white).append(": ")
        .append(this.getElectionName()).append("\n");
    sb.append(bold).append(white).append("| ").append(yellow).append("Election ID   ").append(white).append(": ")
        .append(this.getElectionId()).append("\n");
    sb.append(bold).append(white).append("| ").append(yellow).append("Election Start date ").append(white)
        .append(": ")
        .append(this.getStartDate().substring(0, 16)).append("\n");
    sb.append(bold).append(white).append("| ").append(yellow).append("Election End date ").append(white)
        .append(": ")
        .append(this.getEndDate().substring(0, 16)).append("\n");
    sb.append(bold).append(white).append("| ").append(yellow).append("Status").append(white).append(": ")
        .append(this.getStatus()).append("\n");
    sb.append(bold).append(green).append("+---------------------------------------+\n").append(reset);

    return sb.toString();
  }

  public int getMyIDX() {
    return myIDX;
  }

}
