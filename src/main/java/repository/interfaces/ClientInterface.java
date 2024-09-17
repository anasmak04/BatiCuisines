package main.java.repository.interfaces;

import main.java.domain.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientInterface extends CrudInterface<Client> {

    @Override
    public Client save(Client client);

    @Override
    public Optional<Client> findById(int id);

    @Override
    public List<Client> findAll();

    @Override
    public Client update(Client client);

    @Override
    public boolean delete(int id);

    public Optional<Client> findByName(String name);
}
