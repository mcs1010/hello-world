/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.neuroph.core.NeuralNetwork;

/**
 *
 * @author Marc S
 */
public class BciDatabaseAccess {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public BciDatabaseAccess() throws BciException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace(System.err);
            throw new BciException(e);
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/brain_computer_interface?"
                    + "user=root&password=1234");
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            throw new BciException(e);
        }
    }

    public boolean loadUserFromDatabase(Long userId) throws BciException {
        try {
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("Select user_name, user_neural_net from"
                    + " users where user_id = " + userId.toString() + ";");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User.getInstance().setUserName(resultSet.getString("user_name"));
                //User.getInstance().setUserNeuralNet((NeuralNetwork) resultSet.getBlob("user_neural_net"));
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            throw new BciException("Error selecting user from the database");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }

                resultSet = null;
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                }

                statement = null;
            }
        }
        return true;
    }

    public boolean createUserInDatabase(String newUserName) throws BciException {
        NeuralNetwork newNeuralNet = new NeuralNetwork();
        newNeuralNet.setLabel(newUserName);

        //String newNeuralNet = newUserName.concat("NeuralNet");
        try {
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("INSERT INTO "
                    + "USERS values (default, '" + newUserName + "', '" + newNeuralNet.getLabel() + ".nnet');");
            preparedStatement.executeUpdate();

            User.getInstance().setUserName(newUserName);
            User.getInstance().setUserNeuralNet(newNeuralNet);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            throw new BciException("Error inserting the new user into the database");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }

                resultSet = null;
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                }

                statement = null;
            }
        }
        return true;
    }
}
