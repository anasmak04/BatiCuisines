package main.java.repository;

import main.java.config.DatabaseConnection;
import main.java.domain.entities.Client;
import main.java.repository.interfaces.ClientInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements ClientInterface<Client> {

    private final Connection connection;

    public ClientRepository() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public Client save(Client client) {

        String query = "INSERT INTO clients (name, address, phone, isProfessional) VALUES (?, ?, ?, ?);";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getaddress());
            preparedStatement.setString(3, client.getphone());
            preparedStatement.setBoolean(4, client.isProfessional());
            int result = preparedStatement.executeUpdate();

            if(result == 1){
                System.out.println("Client added successfully");
            }else{
                System.out.println("Client could not be added");
            }

        } catch (SQLException e) {
                System.out.println(e.getMessage());
        }

        return client;
    }

    @Override
    public Optional<Client> findById(Client client) {
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        return List.of();
    }

    @Override
    public Client update(Client client) {
        return null;
    }

    @Override
    public boolean delete(Client client) {
        return false;
    }
}
