package main.java.service;

import main.java.domain.entities.Component;
import main.java.domain.entities.Material;
import main.java.repository.ComponentRepository;
import main.java.repository.interfaces.ComponentInterface;
import main.java.repository.interfaces.MaterialInterface;

import java.util.List;
import java.util.Optional;

public class ComponentService   {

    private final ComponentRepository componentRepository;
    public ComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    public Component save(Component component) {
        return this.componentRepository.save(component);
    }


    public Optional<Component> findById(int id) {
        return Optional.empty();
    }


    public List<Component> findAll() {
        return List.of();
    }


    public Component update(Component component) {
        return null;
    }


    public boolean delete(int id) {
        return false;
    }


}
