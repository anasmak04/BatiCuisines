package main.java.repository.interfaces;

import main.java.domain.entities.Material;
import java.util.List;

public interface MaterialInterface extends CrudInterface<Material>{
    List<Material> findAllByProjectId(Long projectId);
}
