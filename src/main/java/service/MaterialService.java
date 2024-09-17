package main.java.service;

import main.java.domain.entities.Material;
import main.java.repository.MaterialRepository;
import main.java.repository.interfaces.MaterialInterface;

import java.util.List;
import java.util.Optional;

public class MaterialService  {

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public Material save(Material material) {
        return materialRepository.save(material);
    }


    public Optional<Material> findById(Long id) {
        return materialRepository.findById(id);
    }


    public List<Material> findAll() {
        return materialRepository.findAll();
    }


    public Material update(Material material) {
        return materialRepository.update(material);
    }


    public boolean delete(Long id) {
        return materialRepository.delete(id);
    }
}
