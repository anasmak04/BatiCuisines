package main.java.repository.impl;

import main.java.config.DatabaseConnection;
import main.java.domain.entities.Client;
import main.java.domain.entities.Component;
import main.java.domain.entities.Project;
import main.java.exception.ComponentNotFoundException;
import main.java.repository.interfaces.ComponentInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComponentRepository implements ComponentInterface {
    private Connection connection;

    public ComponentRepository() {
        this.connection = DatabaseConnection.getConnection();
    }


    @Override
    public Component save(Component component) {
        String sql = "INSERT INTO components (name, componentType, vatRate, project_id) VALUES (?, ?, ?, ?) RETURNING id;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, component.getName());
            preparedStatement.setString(2, component.getComponentType());
            preparedStatement.setDouble(3, component.getVatRate());
            preparedStatement.setLong(4, component.getProject().getId()); // Ensure project is not null

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                component.setId(resultSet.getLong("id"));
                return component;
            } else {
                throw new SQLException("Creating component failed, no ID obtained.");
            }
        } catch (SQLException e) {
            System.err.println("Error saving component: " + e.getMessage());
            throw new RuntimeException("Error saving component", e);
        }
    }



    @Override
    public Optional<Component> findById(Long id) {
        String query = "SELECT * FROM components WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    Client client = new Client();
                    client.setId(resultSet.getLong("id"));
                    client.setName(resultSet.getString("name"));
                    client.setAddress(resultSet.getString("address"));
                    client.setPhone(resultSet.getString("phone"));
                    client.setProfessional(resultSet.getBoolean("isProfessional"));
                    Project project = new Project(
                            resultSet.getLong("id"),
                            resultSet.getString("projectName"),
                            resultSet.getDouble("profitMargin"),
                            resultSet.getDouble("totalCost"),
                            resultSet.getString("status"),
                            resultSet.getDouble("surface"),
                            client
                    );

                    Component foundComponent = new Component(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("componentType"),
                            resultSet.getDouble("vatRate"),
                            project
                    );
                    return Optional.of(foundComponent);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public List<Component> findAll() {
        String query = "select\n" +
                "    p.*,\n" +
                "    c.*,\n" +
                "    cl.*\n" +
                "FROM components c\n" +
                "LEFT JOIN projects p\n" +
                "ON p.id = c.project_id\n" +
                "LEFT JOIN clients cl ON cl.id = p.client_id";
        List<Component> componentList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {

                Client client = new Client();
                client.setId(resultSet.getLong("id"));
                client.setName(resultSet.getString("name"));
                client.setAddress(resultSet.getString("address"));
                client.setPhone(resultSet.getString("phone"));
                client.setProfessional(resultSet.getBoolean("isProfessional"));
                Project project = new Project(
                        resultSet.getLong("id"),
                        resultSet.getString("projectName"),
                        resultSet.getDouble("profitMargin"),
                        resultSet.getDouble("totalCost"),
                        resultSet.getString("status"),
                        resultSet.getDouble("surface"),
                        client
                );

                Component component = new Component(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("componentType"),
                        resultSet.getDouble("vatRate"),
                        project
                );
                componentList.add(component);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return componentList;
    }


    @Override
    public Component update(Component component) {
        String sql = "UPDATE components SET name = ?, componentType = ?, vatRate = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, component.getName());
            preparedStatement.setString(2, component.getComponentType());
            preparedStatement.setDouble(3, component.getVatRate());
            preparedStatement.setLong(4, component.getId());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Component saved successfully");
            } else {
                throw new ComponentNotFoundException("component saved not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return component;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM components WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Component deleted successfully");
            } else {
                throw new ComponentNotFoundException("component deleted not found");
            }
        } catch (SQLException sqlException) {
            throw new ComponentNotFoundException(sqlException.getMessage());
        }
        return false;
    }

    @Override
    public double findVatRateForComponent(Long id) {
        String sql = "SELECT vatRate FROM components WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getDouble("vatRate");
            }
        }catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return 0.0;
    }
}
