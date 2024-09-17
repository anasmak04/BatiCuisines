package main.java.repository;


import main.java.config.DatabaseConnection;
import main.java.domain.entities.Component;
import main.java.domain.entities.WorkForce;
import main.java.repository.interfaces.WorkForceInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkForceRepository implements WorkForceInterface<WorkForce> {

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
            preparedStatement.setInt(1, workForce.getComponent().getId());
            preparedStatement.setDouble(2, workForce.getHourlyCost());
            preparedStatement.setDouble(3, workForce.getWorkingHours());
            preparedStatement.setDouble(4, workForce.getWorkerProductivity());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int generatedId = resultSet.getInt(1);
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
    public Optional<WorkForce> findById(int id) {
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
                workForce.setId(resultSet.getInt("id"));
                workForce.setHourlyCost(resultSet.getDouble("hourlyCost"));
                workForce.setWorkingHours(resultSet.getDouble("workingHours"));
                workForce.setWorkerProductivity(resultSet.getDouble("workerProductivity"));
                Component component = new Component();
                component.setId(resultSet.getInt("component_id"));
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
    public boolean delete(int id) {
        return false;
    }
}
