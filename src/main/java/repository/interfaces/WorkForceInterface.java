package main.java.repository.interfaces;

import main.java.domain.entities.WorkForce;

import java.util.List;
import java.util.Optional;

public interface WorkForceInterface<T extends WorkForce> extends CrudInterface<WorkForce> {
    @Override
    public WorkForce save(WorkForce workForce);

    @Override
    public Optional<WorkForce> findById(Long id);

    @Override
    public List<WorkForce> findAll();

    @Override
    public WorkForce update(WorkForce entity);

    @Override
    public boolean delete(Long id);
}
