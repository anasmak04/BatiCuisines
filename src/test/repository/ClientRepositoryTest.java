package test.repository;

import main.java.domain.entities.Client;
import main.java.repository.ClientRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientRepositoryTest  {

    private ClientRepository clientRepository;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Client client;

    public void setUp() throws Exception {
        client = new Client();
        client.setName("John Doe");
        client.setAddress("123 Main St");
        client.setPhone("123456789");
        client.setProfessional(true);
        clientRepository.save(client);
    }

    public void testSave() {
    }

    public void testFindById() {
    }

    public void testFindAll() {
    }

    public void testUpdate() {
    }

    public void testDelete() {
    }

    public void testFindByName() {
    }
}