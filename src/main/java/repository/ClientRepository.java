package main.java.repository;

import main.java.domain.entities.Client;
import main.java.repository.interfaces.ClientInterface;

import java.util.List;
import java.util.Optional;

public class ClientRepository implements ClientInterface<Client> {

    @Override
    public Client save(Client client) {
        return null;
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
