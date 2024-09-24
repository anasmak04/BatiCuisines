package main.java.repository.interfaces;

import main.java.domain.entities.Client;

import java.util.Optional;

public interface ClientInterface extends CrudInterface<Client> {
     Optional<Client> findByName(String name);
}
