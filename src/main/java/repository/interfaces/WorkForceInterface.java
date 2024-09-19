package main.java.repository.interfaces;

import main.java.domain.entities.WorkForce;

import java.util.List;

public interface WorkForceInterface extends CrudInterface<WorkForce> {
     List<WorkForce> findAllByProjectId(Long projectId);

}
