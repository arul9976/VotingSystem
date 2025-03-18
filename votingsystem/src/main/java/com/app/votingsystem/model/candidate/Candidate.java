package com.app.votingsystem.model.candidate;


import com.app.votingsystem.model.voter.Voter;
import com.app.votingsystem.service.jdbc.CandidateDB;

public class Candidate extends Voter {
	private String candidateId;
	private String party;
	private String manifesto;
	private String electionId;
	private final int myIDX;

	private static int id = CandidateDB.lastIdx();

	public Candidate() {
		myIDX = id - 1;
	}

	public Candidate(String party, String manifesto, Voter voter, String electionId) {
		super(voter);
		this.candidateId = "CANDIDATE2025_0" + ++id;
		this.party = party;
		this.electionId = electionId;
		this.manifesto = manifesto;

		myIDX = id - 1;
	}

	public Candidate(String cName, byte cAge, String email, String voterId, String party, String manifesto,
			String electionId, String candidateId) {
		super(cName, cAge, email,"Hidden", voterId, false);
		this.candidateId = candidateId;
		this.party = party;
		this.electionId = electionId;
		this.manifesto = manifesto;

		myIDX = id - 1;
	}

	public Candidate(String party, String manifesto, Voter voter, String electionId, String candidateId) {
		super(voter);
		this.candidateId = candidateId;
		this.party = party;
		this.electionId = electionId;
		this.manifesto = manifesto;
		myIDX = id - 1;

	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getManifesto() {
		return manifesto;
	}

	public void setManifesto(String manifesto) {
		this.manifesto = manifesto;
	}

	public String getElectionId() {
		return electionId;
	}

	public void setElectionId(String electionId) {
		this.electionId = electionId;
	}

	public int getMyIDX() {
		return this.myIDX;
	}

}
