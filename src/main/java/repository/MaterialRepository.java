package main.java.repository;

import main.java.config.DatabaseConnection;
import main.java.domain.entities.Component;
import main.java.domain.entities.Material;
import main.java.exception.MaterialNotFoundException;
import main.java.repository.interfaces.MaterialInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MaterialRepository implements MaterialInterface<Material> {
    private Connection connection;
    private final ComponentRepository componentRepository;

    public MaterialRepository(ComponentRepository componentRepository) {
        this.connection = DatabaseConnection.getConnection();
        this.componentRepository = componentRepository;

    }


    @Override
    public Material save(Material material) {
        String sql = "INSERT INTO materials (component_id, unitCost, quantity, transportCost, qualityCoefficient) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, material.getComponent().getId());
            preparedStatement.setDouble(2, material.getUnitCost());
            preparedStatement.setDouble(3, material.getQuantity());
            preparedStatement.setDouble(4, material.getTransportCost());
            preparedStatement.setDouble(5, material.getCoefficientQuality());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long generatedId = resultSet.getLong(1);
                material.setId(generatedId);
                System.out.println("Material saved successfully with ID: " + generatedId);
            } else {
                throw new SQLException("Failed to save material, no ID obtained.");
            }
        } catch (SQLException e) {
            System.out.println("Error saving material: " + e.getMessage());
        }

        return material;
    }


    @Override
    public Optional<Material> findById(Long id) {
        String sql = "SELECT \n" +
                "    m.unitCost AS unitCost, \n" +
                "    m.quantity AS quantity,\n" +
                "    m.transportCost AS transportCost, \n" +
                "    m.coefficientQuality AS coefficientQuality,\n" +
                "    c.id AS componentId,\n" +
                "    c.name AS componentName,\n" +
                "    c.vatRate AS vatRate\n" +
                "FROM \n" +
                "    materials m\n" +
                "LEFT JOIN \n" +
                "    components c ON m.component_id = c.id\n" +
                "WHERE \n" +
                "    m.id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Material foundMaterial = new Material();
                foundMaterial.setId(resultSet.getLong("materialId"));
                foundMaterial.setUnitCost(resultSet.getDouble("unitCost"));
                foundMaterial.setQuantity(resultSet.getDouble("quantity"));
                foundMaterial.setTransportCost(resultSet.getDouble("transportCost"));
                foundMaterial.setCoefficientQuality(resultSet.getDouble("coefficientQuality"));

                Component component = new Component();
                component.setId(resultSet.getLong("componentId"));
                component.setName(resultSet.getString("componentName"));
                component.setVatRate(resultSet.getDouble("vatRate"));

                foundMaterial.setComponent(component);
                return Optional.of(foundMaterial);
            }
        } catch (SQLException sqlException) {
            System.out.println("Error finding material: " + sqlException.getMessage());
        }


        return Optional.empty();
    }


    @Override
    public List<Material> findAll() {
        String sql = "SELECT\n" +
                "    m.id AS materialId,\n" +
                "    m.transportcost AS transportcost,\n" +
                "    m.qualitycoefficient AS qualitycoefficient,\n" +
                "    m.unitcost AS unitcost,\n" +
                "    c.id AS componentId,\n" +
                "    c.name AS componentName,\n" +
                "    c.vatrate AS VatRate\n" +
                "FROM materials m\n" +
                "    LEFT JOIN components c ON m.component_id = c.id";

        List<Material> materials = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Material material = new Material();
                material.setId(resultSet.getLong("materialId"));
                material.setTransportCost(resultSet.getDouble("transportcost"));
                material.setCoefficientQuality(resultSet.getDouble("qualitycoefficient"));
                material.setUnitCost(resultSet.getDouble("unitcost"));

                Component component = new Component();
                component.setId(resultSet.getLong("componentId"));
                component.setName(resultSet.getString("componentName"));
                component.setVatRate(resultSet.getDouble("VatRate"));

                material.setComponent(component);
                materials.add(material);
            }
        } catch (SQLException e) {
            System.out.println("Error finding materials: " + e.getMessage());
        }

        return materials;
    }


    @Override
    public Material update(Material material) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM materials WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Material deleted successfully with ID: " + id);
            } else {
                throw new MaterialNotFoundException("material delete issue");
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return false;
    }
}
