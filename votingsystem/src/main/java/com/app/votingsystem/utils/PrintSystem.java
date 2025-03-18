package com.app.votingsystem.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PrintSystem {
	public static final Scanner userInp = new Scanner(System.in);

	public static final String RESET = "\u001B[0m";
	public static final String BOLD = "\u001B[1m";

	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String CYAN = "\u001B[36m";

	public static final String BG_BLACK = "\033[40m";
	public static final String BG_RED = "\033[41m";
	public static final String BG_GREEN = "\033[42m";
	public static final String BG_YELLOW = "\033[43m";
	public static final String BG_BLUE = "\033[44m";
	public static final String BG_MAGENTA = "\033[45m";
	public static final String BG_CYAN = "\033[46m";
	public static final String BG_WHITE = "\033[47m";

	public static void printMenu(String title, String[] options) {
		int width = 50;
		int maxLength = title.length();

		for (String option : options) {
			maxLength = Math.max(maxLength, option.length());
		}

		String border = YELLOW + "*".repeat(width) + RESET;

		System.out.println(border);

		int padding = (width - title.length()) / 2;
		System.out.println("*" + " ".repeat(padding - 1) + CYAN + title + RESET + " ".repeat(padding - 1) + "*");

		System.out.println(border);

		for (String option : options) {
			int optionPadding = (width - option.length() - 4);
			System.out.println("*  " + GREEN + option + RESET + " ".repeat(optionPadding) + "*");
		}

		System.out.println(border);
	}

	public static String getInput(String prompt) {
		System.out.print(prompt);
		return PrintSystem.userInp.nextLine();
	}

	public static int safeReadInt(String err) throws Exception {
		while (true) {
			if (userInp.hasNextInt()) {
				int value = userInp.nextInt();
				userInp.nextLine();
				return value;
			} else {
				utility.declinedNotification(err.isEmpty() ? "Invalid input. Please enter a number:" : err);
				userInp.nextLine();
				throw new Exception("Please enter a number");
			}
		}
	}

	public static int safeReadInt(String msg, String err) throws Exception {
		System.out.println(msg);
		return safeReadInt(err);
	}

	public static boolean isContinue() {
		System.out.println("Enter 'e' to Exit or any to continue ? ");

		return !PrintSystem.userInp.next().equals("e");
	}

	public static LocalDateTime dateFormater(String date) throws IOException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
		return dateTime;
	}

	public static LocalDateTime getCurrentTime() {
		return LocalDateTime.now();
	}

}
