package main.java.repository.interfaces;

import main.java.domain.entities.Devis;

import java.util.Optional;

public interface DevisInterface extends CrudInterface<Devis> {
    public void updateAmount(Long devisId , double amount);
    public Optional<Devis> findDevisByProjectId(Long projectId);
}
