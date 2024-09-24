package main.java.repository.interfaces;

import main.java.domain.entities.Devis;

import java.util.Optional;

public interface DevisInterface extends CrudInterface<Devis> {
     void updateAmount(Long devisId , double amount);
     Optional<Devis> findDevisByProjectId(Long projectId);
     boolean updateDevisStatus(Long devisId);
}
