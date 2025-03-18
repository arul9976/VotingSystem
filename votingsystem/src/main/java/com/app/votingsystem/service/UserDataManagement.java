package com.app.votingsystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.app.votingsystem.model.voter.Voter;
import com.app.votingsystem.utils.Validation;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UserDataManagement {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final File FILE = new File("voters.json");
	private static final List<Voter> users = getUsers();

	public static boolean registerUser(Voter user) {
		try {

			users.add(user);
			objectMapper.writeValue(FILE, users);
			return true;

		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	private static Object checkLoginCredentials(String userId, String passWord) {
		try {

			for (Voter user : users) {
				if ((userId.equals(user.getVoterId()) || userId.equals(user.getEmail()))) {
					return Validation.verifyPassword(passWord, user.getPassword()) ? user : "Invalid Credentials";
				}
			}
			throw new IOException("User Not Found");

		} catch (IOException e) {
			return e.getMessage();
		}
	}

	public static void updateUser(Voter voter, String email) {
		try {
			for (int i = 0; i < users.size(); i++) {
				if (voter.getEmail().equals(users.get(i).getEmail())) {
					voter.setEmail(email);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updateUserPassword(Voter voter, String password) {
		try {
			for (int i = 0; i < users.size(); i++) {
				if (voter.getEmail().equals(users.get(i).getEmail())) {
					voter.setPassword(password);
					users.set(i, voter);
					break;
				}
			}
			objectMapper.writeValue(FILE, users);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private static List<Voter> getUsers() {
		try {
			List<Voter> us = objectMapper.readValue(FILE,
					objectMapper.getTypeFactory().constructCollectionType(List.class, Voter.class));
			return us;
		} catch (IOException e) {

			System.out.println(e.toString());
		}

		return null;
	}

	public static Object loginUser(String userId, String passWord) {
		return checkLoginCredentials(userId, passWord);

	}

	public static boolean isUserExists(String userId) {
		if (users != null) {
			for (Voter user : users) {
				if (userId.equals(user.getEmail()) || userId.equals(user.getVoterId())) {
					return true;
				}
			}
		} else {
			System.out.println("------------ Users is Null --------------");
		}
		return false;
	}

	public static int lastUserId() {
		return getUsers().size();
	}



}
