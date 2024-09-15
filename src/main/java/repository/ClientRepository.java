package main.java.repository;

import main.java.config.DatabaseConnection;
import main.java.domain.entities.Client;
import main.java.repository.interfaces.ClientInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements ClientInterface {

    private Connection connection;

    public ClientRepository() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public Client save(Client client) {
        String query = "INSERT INTO clients (name, address, phone, isProfessional) VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getAddress());
            preparedStatement.setString(3, client.getPhone());
            preparedStatement.setBoolean(4, client.isProfessional());

            try (ResultSet generatedKeys = preparedStatement.executeQuery()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    client.setId(id);
                    System.out.println("Client with name " + client.getName() + " was successfully saved with ID " + id);
                } else {
                    throw new SQLException("Creating client failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return client;
    }



    @Override
    public Optional<Client> findById(Client client) {
        try {
            connection.setAutoCommit(false);
            String query = "SELECT * FROM clients WHERE id = ?";
            var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, client.getId());
            var resultSet = preparedStatement.executeQuery();
            connection.commit();
            if (resultSet.next()) {
                return Optional.of(client);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        String sql = "SELECT * FROM clients";
        List<Client> clients = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("id"));
                client.setName(resultSet.getString("name"));
                client.setAddress(resultSet.getString("address"));
                client.setPhone(resultSet.getString("phone"));
                client.setProfessional(resultSet.getBoolean("isProfessional"));
            }
            return clients;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return clients;
    }

    @Override
    public Client update(Client client) {
        String sql = "UPDATE clients SET name = ?, address = ?, phone = ?, isProfessional = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getAddress());
            preparedStatement.setString(3, client.getPhone());
            preparedStatement.setBoolean(4, client.isProfessional());
            preparedStatement.setInt(5, client.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return client;
    }

    @Override
    public boolean delete(Client client) {
        String query = "DELETE FROM clients WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, client.getId());
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public List<Client> findByName(String name) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Client client = mapResultSetToClient(rs);
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    private Client mapResultSetToClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setName(rs.getString("name"));
        client.setAddress(rs.getString("address"));
        client.setPhone(rs.getString("phone"));
        client.setProfessional(rs.getBoolean("isProfessional"));
        return client;
    }
}


