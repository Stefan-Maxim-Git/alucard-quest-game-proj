package main;

import main.ContextSettings;

import java.sql.*;

public class DatabaseManager {
    Connection connection;
    Statement statement;
    String databaseName;
    ContextSettings cs;

    public DatabaseManager(ContextSettings cs) {

        this.cs = cs;
        createDatabase();
    }

    public void createDatabase () {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:DataB.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);
            String sql = """
                        CREATE TABLE IF NOT EXISTS "DataB" (
                            "playerX"                    INTEGER,
                            "playerY"                    INTEGER,
                            "playerHP"            INTEGER,
                            "mapIndex"            INTEGER)
                          
                        """.stripIndent();
            statement.execute(sql);
            System.out.println("Started database successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void save() {
        String sql = "INSERT INTO DataB (playerX, playerY, playerHP, mapIndex) VALUES (?, ?, ?, ?)";

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, cs.player.x);
            preparedStatement.setInt(2, cs.player.y);
            preparedStatement.setInt(3, cs.player.life);
            preparedStatement.setInt(4, cs.currentLevel);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        String sql = "SELECT * FROM DataB";

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int value1 = resultSet.getInt("playerX");
                int value2 = resultSet.getInt("playerY");
                int value3 = resultSet.getInt("playerHP");
                int value4 = resultSet.getInt("mapIndex");

                System.out.println("PlayerX: " + value1 +
                        ", PlayerY: " + value2 +
                        ", PlayerHP: " + value3 +
                        ", mapIndex: " + value4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        String sql = "SELECT * FROM DataB ORDER BY ROWID DESC LIMIT 1";

        try  {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int playerX = resultSet.getInt("playerX");
                int playerY = resultSet.getInt("playerY");
                int playerHP = resultSet.getInt("playerHP");
                int mapIndex = resultSet.getInt("mapIndex");

                // Update the player's properties with the loaded values
                cs.player.x = playerX;
                cs.player.y = playerY;
                cs.player.life = playerHP;
                cs.currentLevel = mapIndex;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}