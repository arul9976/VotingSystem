package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DB_Management {
  private static final Logger logger = LogManager.getLogger(DB_Management.class);
  String dbname;
  static DB_Management db_Management = null;

  public static DB_Management getInstance(String dbName) {
    Objects.requireNonNull(dbName);
    return db_Management == null ? db_Management = new DB_Management(dbName) : db_Management;
  }

  private DB_Management(String dbname) {
    this.dbname = dbname;
  }

  public Connection dbConnection() {
    try {
      String url = "jdbc:mysql://localhost:3306/" + this.dbname;
      String user = "root", password = "newpassword";
      return DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
      logger.error("Error while connecting to database : ", e);
      System.err.println("Connection Error: " + e.getMessage());
    } catch (Exception e) {
      logger.error("Error while connecting to database : ", e);
      System.err.println("DB Error: " + e.getMessage());
    }
    return null;
  }

}
