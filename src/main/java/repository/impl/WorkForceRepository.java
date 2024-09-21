package main.java.repository.impl;


import main.java.config.DatabaseConnection;
import main.java.domain.entities.Component;
import main.java.domain.entities.Project;
import main.java.domain.entities.WorkForce;
import main.java.exception.LaborNotFoundException;
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

        Component savedComponent = componentRepository.save(workForce);
        workForce.setId(savedComponent.getId());

        String sql = "INSERT INTO labor (id, name, hourlyRate, workHours, workerProductivity, project_id , componentType , vatRate) " +
                "VALUES (?, ?, ?, ?, ? , ? , ? , ?) RETURNING id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, workForce.getId());
            preparedStatement.setString(2, workForce.getName());
            preparedStatement.setDouble(3, workForce.getHourlyCost());
            preparedStatement.setDouble(4, workForce.getWorkingHours());
            preparedStatement.setDouble(5, workForce.getWorkerProductivity());
            preparedStatement.setLong(6, workForce.getProject().getId());
            preparedStatement.setString(7, workForce.getComponentType());
            preparedStatement.setDouble(8, workForce.getVatRate());

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
        String sql = "SELECT * FROM labor WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                WorkForce workForce = new WorkForce();
                workForce.setId(resultSet.getLong("id"));
                workForce.setHourlyCost(resultSet.getDouble("hourlyRate"));
                workForce.setWorkingHours(resultSet.getDouble("workHours"));
                workForce.setWorkerProductivity(resultSet.getDouble("workerProductivity"));
                workForce.setName(resultSet.getString("name"));
                workForce.setComponentType(resultSet.getString("componentType"));
                workForce.setVatRate(resultSet.getDouble("vatRate"));

                Project project = new Project();
                project.setId(resultSet.getLong("project_id"));
                workForce.setProject(project);

                return Optional.of(workForce);
            }
        } catch (SQLException e) {
            System.out.println("Error finding work force by ID: " + e.getMessage());
        }

        return Optional.empty();
    }


    @Override
    public List<WorkForce> findAll() {
        String sql = "SELECT * FROM labor";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<WorkForce> workForces = new ArrayList<>();

            while (resultSet.next()) {
                WorkForce workForce = new WorkForce();
                workForce.setId(resultSet.getLong("id"));
                workForce.setHourlyCost(resultSet.getDouble("hourlyRate"));
                workForce.setWorkingHours(resultSet.getDouble("workHours"));
                workForce.setWorkerProductivity(resultSet.getDouble("workerProductivity"));
                workForce.setName(resultSet.getString("name"));
                workForce.setComponentType(resultSet.getString("componentType"));
                workForce.setVatRate(resultSet.getDouble("vatRate"));
                Project project = new Project();
                project.setId(resultSet.getLong("project_id"));

                workForce.setProject(project);

                workForces.add(workForce);
            }

            return workForces;
        } catch (SQLException e) {
            System.out.println("Error finding all work forces: " + e.getMessage());
        }

        return List.of();
    }


    @Override
    public WorkForce update(WorkForce workForce) {
        Component updatedComponent = componentRepository.update(workForce);
        workForce.setId(updatedComponent.getId());

        String sql = "UPDATE labor SET name = ? ,vatrate = ? , hourlyRate = ?, workHours = ?, workerProductivity = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, workForce.getName());
            preparedStatement.setDouble(2, workForce.getVatRate());
            preparedStatement.setDouble(3, workForce.getHourlyCost());
            preparedStatement.setDouble(4, workForce.getWorkingHours());
            preparedStatement.setDouble(5, workForce.getWorkerProductivity());
            preparedStatement.setLong(6, workForce.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new LaborNotFoundException("No rows updated. Workforce with ID " + workForce.getId() + " not found.");
            }

            System.out.println("Workforce updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating workforce: " + e.getMessage());
        }

        return workForce;
    }


    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM labor WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (SQLException sqlException) {
            System.out.println("Error deleting labor: " + sqlException.getMessage());
        }

        return false;
    }

    @Override
    public List<WorkForce> findAllByProjectId(Long projectId) {
        List<WorkForce> workforces = new ArrayList<>();
        String sql = "SELECT l.id, l.hourlyRate, l.workHours, l.workerProductivity, " +
                "l.name AS component_name, l.vatRate, l.project_id " +
                "FROM labor l " +
                "WHERE l.project_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getLong("project_id"));

                WorkForce workForce = new WorkForce();
                workForce.setId(resultSet.getLong("id"));
                workForce.setHourlyCost(resultSet.getDouble("hourlyRate"));
                workForce.setWorkingHours(resultSet.getDouble("workHours"));
                workForce.setWorkerProductivity(resultSet.getDouble("workerProductivity"));
                workForce.setName(resultSet.getString("component_name"));
                workForce.setVatRate(resultSet.getDouble("vatRate"));

                workForce.setProject(project);

                workforces.add(workForce);
            }
        } catch (SQLException sqlException) {
            System.out.println("Error finding all work forces: " + sqlException.getMessage());
        }

        return workforces;
    }


}
