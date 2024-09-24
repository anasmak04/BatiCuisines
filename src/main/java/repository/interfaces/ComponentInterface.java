package main.java.repository.interfaces;

import main.java.domain.entities.Component;

public interface ComponentInterface extends CrudInterface<Component> {
     double findVatRateForComponent(Long id);
}
