package main.java.service;

import main.java.domain.entities.Component;
import main.java.repository.impl.ComponentRepository;

import java.util.List;
import java.util.Optional;

public class ComponentService {

    private final ComponentRepository componentRepository;

    public ComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    public Component save(Component component) {
        return this.componentRepository.save(component);
    }


    public Optional<Component> findById(Long id) {
        return componentRepository.findById(id);
    }


    public List<Component> findAll() {
        return componentRepository.findAll();
    }


    public Component update(Component component) {
        return componentRepository.update(component);
    }


    public boolean delete(Long id) {
        return componentRepository.delete(id);
    }


}
