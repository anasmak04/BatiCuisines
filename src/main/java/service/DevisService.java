package main.java.service;

import main.java.domain.entities.Devis;
import main.java.repository.impl.DevisRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DevisService {
    private final DevisRepository devisRepository;

    public DevisService(DevisRepository devisRepository) {
        this.devisRepository = devisRepository;
    }

    public Devis save(Devis devis) {
        return this.devisRepository.save(devis);
    }

    public Optional<Devis> findById(Long id) {
        return this.devisRepository.findById(id);
    }

    public Devis update(Devis devis) {
        return this.devisRepository.update(devis);
    }

    public boolean delete(Long id) {
        return this.devisRepository.delete(id);
    }

    public List<Devis> findAll() {
        return this.devisRepository.findAll();
    }

    public void updateAmountDevis(Long devisId, double amount) {
        this.devisRepository.updateAmount(devisId, amount);
    }

    public Optional<Devis> findDevisByproject(Long projectId){
      return   this.devisRepository.findDevisByProjectId(projectId);
    }

    public boolean updateDevisStatus(Long devisId) {
        return this.devisRepository.updateDevisStatus(devisId);
    }


    private boolean checkIfDevisAccepted(Long devisId , LocalDate validatedDate) {
        Optional<Devis> fetchedDevs = findById(devisId);
        LocalDate currentDate = LocalDate.now();
        if(fetchedDevs.isPresent()){
           Devis devis = fetchedDevs.get();
            return  currentDate.isAfter(devis.getValidatedDate());
        }
        return false;
    }

    public void cancelDevisAndProjectIfNotAccepted(Long devisId , LocalDate validatedDate) {
        if(checkIfDevisAccepted(devisId , validatedDate)){
            updateDevisStatus(devisId);
        }
    }

}
