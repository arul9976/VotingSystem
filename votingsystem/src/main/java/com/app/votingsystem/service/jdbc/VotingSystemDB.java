package com.app.votingsystem.service.jdbc;

import com.app.votingsystem.model.voter.Voter;
import com.app.votingsystem.utils.Validation;
import com.app.votingsystem.utils.utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class VotingSystemDB {
    private static final Logger logger = LogManager.getLogger(VotingSystemDB.class);
    private static final Connection connection = DB_Management.getInstance("VotingSystem").dbConnection();
    // private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static CallableStatement callableStatement = null;

    public static boolean ValidateRegisterUser(Voter voter) throws SQLException {

        String sql = "CALL ValidateRegisterUser(?, ?, ?, ?, ?, ?, ?)";
        callableStatement = connection.prepareCall(sql);
        callableStatement.setString(1, voter.getName());
        callableStatement.setInt(2, voter.getAge());
        callableStatement.setString(3, voter.getEmail());
        callableStatement.setString(4, voter.getPassword());
        callableStatement.setString(5, voter.getVoterId());
        callableStatement.setBoolean(6, voter.isEligible());

        callableStatement.registerOutParameter(7, Types.BOOLEAN);

        try {
            callableStatement.execute();
            boolean success = callableStatement.getBoolean(7);
            return success;
        } catch (SQLException se) {
            logger.error("Error occurred while validating user : " + se.getMessage());
            utility.declinedNotification("Voter register failed : " + se.getMessage());
        }

        return false;

    }

    public static Voter ValidateLoginUser(String email, String password) throws SQLException {
        ResultSet resultSet;
        String query = "SELECT * FROM Voter WHERE email = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Voter voter = Validation.verifyPassword(password, resultSet.getString("password"))
                    ? new Voter(resultSet.getString("name"), resultSet.getByte("age"), resultSet.getString("email"),
                    resultSet.getString("password"), resultSet.getString("voterId"), resultSet.getBoolean("isEligible"))
                    : null;
            if (voter != null) {
                voter.setUserRole(resultSet.getString("userRole"));
                return voter;
            }
        }

        return null;
    }

    public static Voter getVoter(String voterId) throws SQLException {
        ResultSet resultSet;
        String query = "SELECT * FROM Voter WHERE id = ?";

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, voterId);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new Voter(resultSet.getString("name"), resultSet.getByte("age"), resultSet.getString("email"),
                    resultSet.getString("password"), resultSet.getString("voterId"), resultSet.getBoolean("isEligible"));
        }

        return null;
    }

    public static int lastIdx() {
        try {
            callableStatement = connection.prepareCall("CALL maintainCount(?)");
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.execute();
            return callableStatement.getInt(1);
        } catch (SQLException e) {
            logger.error("Error while executing last index statement", e);
            utility.declinedNotification(e.getMessage());
        }
        return -1;
    }

    public static boolean updateUser(Voter voter, String email) throws IllegalStateException {
        try {
            if (Validation.isValidEmail(email)) {
                callableStatement = connection.prepareCall("CALL UpdateVoter(?,?)");
                callableStatement.setString(1, voter.getVoterId());
                callableStatement.setString(2, email);
                callableStatement.execute();
                return true;

            } else {
                throw new IllegalStateException("Email is not valid");
            }

        } catch (SQLException e) {
            logger.error("Error while updating error : ", e);
            utility.declinedNotification(e.getMessage());
        }
        return false;
    }

    public static boolean updatePassword(Voter voter, String password) throws IllegalStateException {
        try {
            if (Validation.isValidPassword(password)) {
                callableStatement = connection.prepareCall("CALL UpdateVoterPassword(?,?)");
                callableStatement.setString(1, voter.getVoterId());
                callableStatement.setString(2, Validation.hashPassword(password));
                callableStatement.execute();
                return true;

            } else {
                throw new IllegalStateException("Password is not valid");
            }

        } catch (SQLException e) {
            logger.error("Error while updating password : ", e);
            utility.declinedNotification(e.getMessage());
        }
        return false;
    }
}
