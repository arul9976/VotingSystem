package utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import datamanagement.ElectionDataManagement;
import datamanagement.UserDataManagement;
import election.ElectionStatus;
import usermanagement.Election;
import usermanagement.Voter;

public class Validation {
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	private static final String PASSWORD_REGEX = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
	private static final Logger logger = LogManager.getLogger(Validation.class);

	public static boolean validateUser(Voter voter) {

		boolean isValid = true;
		try {
			if (voter.getPassword().length() < 8) {
				isValid = false;
			}
			if (isValid && UserDataManagement.isUserExists(voter.getEmail())) {
				isValid = false;
			}
		} catch (Exception e) {
			logger.error("User Validation Failed : " + e);
			isValid = false;
			utility.declinedNotification("User Validate failed : "+e.getMessage());
		}

		return isValid;
	}

	public static boolean validateUser(String userId, String password) {
		return UserDataManagement.isUserExists(userId);

	}

	public static boolean isValidDate(String sDate, String eDate) throws IOException {
		LocalDateTime sTime = PrintSystem.dateFormater(sDate);
		LocalDateTime eTime = PrintSystem.dateFormater(eDate);

		if (sTime.isAfter(eTime) || PrintSystem.getCurrentTime().isAfter(eTime)) {
			throw new IOException();
		} else if (PrintSystem.getCurrentTime().isBefore(sTime)) {
			return true;
		}
		return false;
	}

	public static void currStatus(Election election) throws IOException {
		LocalDateTime sTime = PrintSystem.dateFormater(election.getStartDate());
		LocalDateTime eTime = PrintSystem.dateFormater(election.getEndDate());
		if (PrintSystem.getCurrentTime().isBefore(sTime)) {
			election.setStatus(ElectionStatus.UPCOMING);
		} else if (PrintSystem.getCurrentTime().isAfter(sTime) && PrintSystem.getCurrentTime().isBefore(eTime)) {
			election.setStatus(ElectionStatus.ONGOING);
		} else if (PrintSystem.getCurrentTime().isAfter(eTime)) {
			election.setStatus(ElectionStatus.ENDED);
		}
		if (ElectionDataManagement.updateElectionDetails(election)) {
		} else {
			throw new IOException("Some Went Wrong");
		}
	}

	public static void currStatus(List<Election> elections) throws IOException {
		for (Election election : elections) {
			LocalDateTime sTime = PrintSystem.dateFormater(election.getStartDate());
			LocalDateTime eTime = PrintSystem.dateFormater(election.getEndDate());
			if (PrintSystem.getCurrentTime().isBefore(sTime)) {
				election.setStatus(ElectionStatus.UPCOMING);
			} else if (PrintSystem.getCurrentTime().isAfter(sTime) && PrintSystem.getCurrentTime().isBefore(eTime)) {
				election.setStatus(ElectionStatus.ONGOING);
			} else if (PrintSystem.getCurrentTime().isAfter(eTime)) {
				election.setStatus(ElectionStatus.ENDED);
			}
		}

	}

	public static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(12));
	}

	public static boolean verifyPassword(String password, String hashedPassword) {
		return BCrypt.checkpw(password, hashedPassword);
	}

	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static boolean isValidPassword(String password) {
		Pattern pattern = Pattern.compile(PASSWORD_REGEX);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

}
