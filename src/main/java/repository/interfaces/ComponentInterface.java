package main.java.repository.interfaces;

import main.java.domain.entities.Component;

public interface ComponentInterface extends CrudInterface<Component> {
    public double findVatRateForComponent(Long id);
}
