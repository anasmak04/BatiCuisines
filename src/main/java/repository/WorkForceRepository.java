package main.java.repository;


import main.java.config.DatabaseConnection;
import main.java.domain.entities.Component;
import main.java.domain.entities.Project;
import main.java.domain.entities.WorkForce;
import main.java.repository.interfaces.WorkForceInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkForceRepository implements WorkForceInterface {

    private Connection connection;
    private ComponentRepository componentRepository;

    public WorkForceRepository(ComponentRepository componentRepository) {
        this.connection = DatabaseConnection.getConnection();
        this.componentRepository = componentRepository;
    }

    @Override
    public WorkForce save(WorkForce workForce) {
        String sql = "INSERT INTO labor (component_id, hourlyRate, workHours, workerProductivity) " +
                "VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, workForce.getComponent().getId());
            preparedStatement.setDouble(2, workForce.getHourlyCost());
            preparedStatement.setDouble(3, workForce.getWorkingHours());
            preparedStatement.setDouble(4, workForce.getWorkerProductivity());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long generatedId = resultSet.getLong(1);
                workForce.setId(generatedId);
                System.out.println("Work force saved successfully with ID: " + generatedId);
            } else {
                throw new SQLException("Failed to save labor, no ID obtained.");
            }
        } catch (SQLException e) {
            System.out.println("Error saving work force: " + e.getMessage());
        }

        return workForce;
    }


    @Override
    public Optional<WorkForce> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<WorkForce> findAll() {
        String sql = "SELECT * FROM labor";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<WorkForce> workForces = new ArrayList<>();
            while (resultSet.next()){
                WorkForce workForce = new WorkForce();
                workForce.setId(resultSet.getLong("id"));
                workForce.setHourlyCost(resultSet.getDouble("hourlyCost"));
                workForce.setWorkingHours(resultSet.getDouble("workingHours"));
                workForce.setWorkerProductivity(resultSet.getDouble("workerProductivity"));
                Component component = new Component();
                component.setId(resultSet.getLong("component_id"));
                workForce.setComponent(component);
                workForces.add(workForce);
            }
            return workForces;
        }catch(SQLException e){
            System.out.println("Error finding all work forces: " + e.getMessage());
        }
        return List.of();
    }

    @Override
    public WorkForce update(WorkForce entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<WorkForce> findAllByProjectId(Long projectId) {
        List<WorkForce> workforces = new ArrayList<>();
        String sql = "SELECT l.id, l.hourlyRate, l.workHours, l.workerProductivity, " +
                "c.id AS component_id, c.name AS component_name, c.vatRate, c.project_id " +
                "FROM labor l " +
                "JOIN components c ON l.component_id = c.id " +
                "WHERE c.project_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Component component = new Component();
                component.setId(resultSet.getLong("component_id"));
                component.setName(resultSet.getString("component_name"));
                component.setVatRate(resultSet.getDouble("vatRate"));

                Project project = new Project();
                project.setId(resultSet.getLong("project_id"));

                WorkForce workForce = new WorkForce();
                workForce.setId(resultSet.getLong("id"));

                workForce.setHourlyCost(resultSet.getDouble("hourlyRate"));
                workForce.setWorkingHours(resultSet.getDouble("workHours"));
                workForce.setWorkerProductivity(resultSet.getDouble("workerProductivity"));
                component.setProject(project);
                workForce.setComponent(component);

                workforces.add(workForce);
            }
        } catch (SQLException sqlException) {
            System.out.println("Error finding all work forces: " + sqlException.getMessage());
        }

        return workforces;
    }


}
