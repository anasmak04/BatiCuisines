package main.java.service;

import main.java.domain.entities.WorkForce;
import main.java.repository.WorkForceRepository;
import main.java.repository.interfaces.WorkForceInterface;

import java.util.List;
import java.util.Optional;

public class WorkForceService  {

    private final WorkForceRepository workForceRepository;

    public WorkForceService(WorkForceRepository workForceRepository) {
        this.workForceRepository = workForceRepository;
    }

    public WorkForce save(WorkForce workForce) {
        return workForceRepository.save(workForce);
    }


    public Optional<WorkForce> findById(Long id) {
        return this.workForceRepository.findById(id);
    }


    public List<WorkForce> findAll() {
        return this.workForceRepository.findAll();
    }


    public WorkForce update(WorkForce workForce) {
        return this.workForceRepository.update(workForce);
    }


    public boolean delete(Long id) {
        return this.workForceRepository.delete(id);
    }
}
