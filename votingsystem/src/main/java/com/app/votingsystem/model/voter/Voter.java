package com.app.votingsystem.model.voter;

import com.app.votingsystem.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.app.votingsystem.service.jdbc.VotingSystemDB;

public class Voter extends User implements VoterManagement {

	@JsonProperty("voterId")
	private String voterId;

	@JsonProperty("isEligible")
	private boolean isEligible;

	// @JsonProperty("address")
	// private String address;

	private static int id = 0;

	public Voter() {
		super();
		id = VotingSystemDB.lastIdx();
	}

	public Voter(User user) {
		this(user.getName(), user.getAge(), user.getEmail(), user.getPassword());

	}

	public Voter(Voter user) {
		this(user.getName(), user.getAge(), user.getEmail(), user.getPassword(), user.getVoterId(), user.isEligible());
	}

	public Voter(String name, byte age, String email, String password) {
		super(name, age, email, password);
		id = VotingSystemDB.lastIdx();
		this.voterId = "VOTERID_2025_0" + ++id;
		this.isEligible = true;
		// this.setCurrUser(this);
	}

	public Voter(String name, byte age, String email, String password, String voterId, boolean isEligible) {
		super(name, age, email, password);
		this.voterId = voterId;
		this.isEligible = isEligible;
		// this.setCurrUser(this);
	}

	public String getVoterId() {
		return voterId;
	}

	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}

	public boolean isEligible() {
		return isEligible;
	}

	public void setEligible(boolean isEligible) {
		this.isEligible = isEligible;
	}

	@Override
	public void register() {
		if (this.getAge() >= 18) {
			setEligible(true);

			System.out.println("You are eligible to cast vote");
		}
	}

	@Override
	public boolean login() {
		return true;
	}

	@Override
	public void vote() {
		// TODO Auto-generated method stub

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
		sb.append(bold).append(green).append("|          USER INFORMATION             |\n").append(reset);
		sb.append(bold).append(green).append("+---------------------------------------+\n").append(reset);
		sb.append(bold).append(white).append("| ").append(yellow).append("Username   ").append(white).append(": ")
				.append(this.getName()).append("\n");
		sb.append(bold).append(white).append("| ").append(yellow).append("Password   ").append(white).append(": ")
				.append("*".repeat(12)).append("\n");
		sb.append(bold).append(white).append("| ").append(yellow).append("Age        ").append(white).append(": ")
				.append(this.getAge()).append("\n");
		sb.append(bold).append(white).append("| ").append(yellow).append("Email      ").append(white).append(": ")
				.append(this.getEmail()).append("\n");
		sb.append(bold).append(white).append("| ").append(yellow).append("Is Eligible").append(white).append(": ")
				.append(this.isEligible()).append("\n");
		sb.append(bold).append(green).append("+---------------------------------------+\n").append(reset);

		return sb.toString();
	}

}
