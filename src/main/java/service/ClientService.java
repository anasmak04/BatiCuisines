package main.java.service;

import main.java.domain.entities.Client;
import main.java.repository.impl.ClientRepository;
import main.java.utils.Validations;

import java.util.List;
import java.util.Optional;

public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client save(Client Client) {
        return this.clientRepository.save(Client);
    }

    public Optional<Client> findById(Long id) {
        return this.clientRepository.findById(id);
    }

    public Client update(Client Client) {
        return this.clientRepository.update(Client);
    }

    public boolean delete(Long id) {
        return this.clientRepository.delete(id);
    }

    public List<Client> findAll() {
        return this.clientRepository.findAll();
    }

    public Optional<Client> findByName(String name) {
        Validations.ClientByNameValidation(name);
        return this.clientRepository.findByName(name);
    }
}
