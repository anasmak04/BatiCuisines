package main.java.repository.impl;

import main.java.config.DatabaseConnection;
import main.java.domain.entities.*;
import main.java.exception.ProjectNotFoundException;
import main.java.repository.interfaces.ProjectInterface;

import java.sql.*;
import java.util.*;

public class ProjectRepository implements ProjectInterface {
    private final Connection connection;

    public ProjectRepository() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public Project save(Project project) {
        String sql = "INSERT INTO projects (projectName, profitMargin, totalCost, status, surface, client_id) VALUES (?, ?, ?, ?::projectStatus, ?, ?) RETURNING id;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setDouble(2, project.getProfitMargin());
            preparedStatement.setDouble(3, project.getTotalCost());
            preparedStatement.setString(4, project.getStatus().name());
            preparedStatement.setDouble(5, project.getSurface());
            preparedStatement.setLong(6, project.getClient().getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                project.setId(resultSet.getLong("id"));
                System.out.println("Project saved with ID: " + project.getId());
                return project;
            } else {
                throw new SQLException("Creating project failed, no ID obtained.");
            }
        } catch (SQLException e) {
            System.out.println("Error saving project: " + e.getMessage());
            return null;
        }
    }


    @Override
    public Optional<Project> findById(Long id) {
        String sql = "SELECT\n" +
                "    p.projectname AS projectName,\n" +
                "    p.profitmargin AS profitMargin,\n" +
                "    p.status AS status,\n" +
                "    p.surface AS surface,\n" +
                "    p.totalcost AS totalCost,\n" +
                "    p.id AS id,\n" +
                "    c.id AS client_id,\n" +
                "    c.name AS client_name,\n" +
                "    c.address AS client_address\n" +
                "FROM projects p\n" +
                "         JOIN clients c ON c.id = p.client_id\n" +
                "WHERE p.id = ?;\n";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getLong("client_id"));
                client.setName(resultSet.getString("client_name"));
                client.setAddress(resultSet.getString("client_address"));
                Project foundProject = new Project(
                        resultSet.getLong("id"),
                        resultSet.getString("projectName"),
                        resultSet.getDouble("profitMargin"),
                        resultSet.getDouble("totalCost"),
                        resultSet.getString("status"),
                        resultSet.getDouble("surface"),
                        client
                );
                return Optional.of(foundProject);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public List<Project> findAll() {
        String sql = "SELECT\n" +
                "    p.id AS project_id,\n" +
                "    p.projectName,\n" +
                "    p.profitMargin,\n" +
                "    p.totalCost,\n" +
                "    p.status AS projectStatus,\n" +
                "    p.surface,\n" +
                "    cl.id AS client_id,\n" +
                "    cl.name AS clientName,\n" +
                "    cl.address AS clientAddress,\n" +
                "    cl.phone AS clientPhone,\n" +
                "    cl.isProfessional AS clientIsProfessional,\n" +
                "    comp.id AS component_id,\n" +
                "    comp.name AS componentName,\n" +
                "    comp.componentType AS componentType,\n" +
                "    comp.vatRate AS vatRate,\n" +
                "    ma.id AS materialId,\n" +
                "    ma.quantity AS quantity,\n" +
                "    ma.transportCost AS transportCost,\n" +
                "    ma.qualitycoefficient AS coefficientQuality,\n" +
                "    le.id AS laborId,\n" +
                "    le.hourlyrate AS hourlyCost,\n" +
                "    le.workhours AS workingHours,\n" +
                "    le.workerProductivity AS workerProductivity\n" +
                "FROM\n" +
                "    projects p\n" +
                "    LEFT JOIN clients cl ON p.client_id = cl.id\n" +
                "    LEFT JOIN components comp ON p.id = comp.project_id\n" +
                "    LEFT JOIN materials ma ON comp.id = ma.component_id\n" +
                "    LEFT JOIN labor le ON comp.id = le.component_id;";

        List<Project> projects = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Long projectId = resultSet.getLong("project_id");


                Client client = new Client();
                client.setId(resultSet.getLong("client_id"));
                client.setName(resultSet.getString("clientName"));
                client.setAddress(resultSet.getString("clientAddress"));
                client.setPhone(resultSet.getString("clientPhone"));
                client.setProfessional(resultSet.getBoolean("clientIsProfessional"));

                Project project = new Project(
                        projectId,
                        resultSet.getString("projectName"),
                        resultSet.getDouble("profitMargin"),
                        resultSet.getDouble("totalCost"),
                        resultSet.getString("projectStatus"),
                        resultSet.getDouble("surface"),
                        client
                );

                projects.add(project);


                Long componentId = resultSet.getLong("component_id");

                Component component = new Component();
                component.setId(componentId);
                component.setName(resultSet.getString("componentName"));
                component.setComponentType(resultSet.getString("componentType"));
                component.setVatRate(resultSet.getDouble("vatRate"));
                component.setProject(project);


                project.addComponent(component);


                Long materialId = resultSet.getLong("materialId");
                Material material = new Material();
                material.setId(materialId);
                material.setQuantity(resultSet.getDouble("quantity"));
                material.setTransportCost(resultSet.getDouble("transportCost"));
                material.setCoefficientQuality(resultSet.getDouble("coefficientQuality"));
//                material.setComponent(component);

                component.addMaterial(material);


                Long laborId = resultSet.getLong("laborId");
                WorkForce workForce = new WorkForce();
                workForce.setId(laborId);
                workForce.setHourlyCost(resultSet.getDouble("hourlyCost"));
                workForce.setWorkingHours(resultSet.getInt("workingHours"));
                workForce.setWorkerProductivity(resultSet.getDouble("workerProductivity"));
//                workForce.setComponent(component);

                component.addWorkForce(workForce);

            }
        } catch (SQLException e) {
            System.out.println("Error retrieving projects: " + e.getMessage());
        }

        return projects;
    }


    @Override
    public Project update(Project project) {
        String sql = "UPDATE projects SET projectName = ?, profitMargin = ?, totalCost = ?, status = ?::projectStatus , surface = ?, client_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setDouble(2, project.getProfitMargin());
            preparedStatement.setDouble(3, project.getTotalCost());
            preparedStatement.setString(4, project.getStatus().name());
            preparedStatement.setString(5, project.getStatus().name());
            preparedStatement.setDouble(6, project.getSurface());
            preparedStatement.setLong(7, project.getId());

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Project updated successfully");
            } else {
                throw new ProjectNotFoundException("Update failed, project not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return project;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM projects WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Project deleted successfully");
                return true;
            } else {
                throw new ProjectNotFoundException("Delete failed, project not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean updateFields(Long projectId, double marginProfit, double totalCost) {
        String sql = "UPDATE projects SET profitMargin ? , totalCost = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, marginProfit);
            preparedStatement.setDouble(2, totalCost);
            preparedStatement.setLong(3, projectId);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Project updated successfully");
            } else {
                System.out.println("Update failed, project not found");
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return false;
    }

    @Override
    public Project findProjectByName(String name) {
        String sql = "SELECT id , projectName FROM projects WHERE projectName = ?";
        Project project = new Project();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                project.setId(id);
                project.setProjectName(resultSet.getString("projectName"));
            } else {
                throw new ProjectNotFoundException("Project not found");
            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return project;
    }

    @Override
    public void updateProjectFields(Long projctId, double marginProfit, double totalCost) {
        String sql = "UPDATE projects SET profitMargin =? , totalCost = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, marginProfit);
            preparedStatement.setDouble(2, totalCost);
            preparedStatement.setLong(3, projctId);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Project updated successfully");
            } else {
                System.out.println("Update failed, project not found");
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @Override
    public boolean updateProjectStatus(Long projctId, String status) {
        String sql = "UPDATE projects SET status = ?::projectStatus  WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status);
            preparedStatement.setLong(2, projctId);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return false;
    }
}
