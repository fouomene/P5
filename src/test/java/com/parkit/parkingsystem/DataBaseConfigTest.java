package com.parkit.parkingsystem;

import com.parkit.parkingsystem.config.DataBaseConfig;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DataBaseConfigTest {

  private DataBaseConfig dataBaseConfig;

  @BeforeEach
  public void setUp() {
    dataBaseConfig = new DataBaseConfig();
  }

  // Check ou the connexion to the DB
  @Test
  public void chekOutDatabaseConnection() throws ClassNotFoundException, SQLException {

    Connection connection;

    connection = dataBaseConfig.getConnection();

    assert(connection) != null;
  }

}