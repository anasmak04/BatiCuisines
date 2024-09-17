package main.java.repository.interfaces;

import main.java.domain.entities.Devis;

import java.util.List;
import java.util.Optional;

public interface DevisInterface extends CrudInterface<Devis> {

    @Override
    public Devis save(Devis devis);

    @Override
    public Optional<Devis> findById(int id);

    @Override
    public List<Devis> findAll();

    @Override
    public Devis update(Devis entity);

    @Override
    public boolean delete(int id);
}
