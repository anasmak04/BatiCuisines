package main.java.repository.interfaces;

import main.java.domain.entities.Material;

import java.util.List;
import java.util.Optional;

public interface MaterialInterface  <T extends Material> extends CrudInterface<Material>{
    @Override
    public Material save(Material entity);

    @Override
    public Optional<Material> findById(Long id);

    @Override
    public List<Material> findAll();

    @Override
    public Material update(Material entity);

    @Override
    public boolean delete(Long id);
}
