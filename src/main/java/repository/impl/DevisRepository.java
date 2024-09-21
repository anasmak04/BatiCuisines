package main.java.repository.impl;

import main.java.config.DatabaseConnection;
import main.java.domain.entities.Client;
import main.java.domain.entities.Devis;
import main.java.domain.entities.Project;
import main.java.repository.interfaces.DevisInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DevisRepository implements DevisInterface {
    private final Connection connection;

    public DevisRepository() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public Devis save(Devis devis) {
        String query = "INSERT INTO quotes (estimatedAmount, issueDate,validatedDate, isAccepted, project_id) VALUES (?, ?,?, ?, ?) RETURNING id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, devis.getEstimatedAmount());
            preparedStatement.setDate(2, java.sql.Date.valueOf(devis.getIssueDate()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(devis.getValidatedDate()));
            preparedStatement.setBoolean(4, devis.isAccepted());
            preparedStatement.setLong(5, devis.getProject().getId());

            try (ResultSet generatedKeys = preparedStatement.executeQuery()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    devis.setId(id);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return devis;
    }

    @Override
    public Optional<Devis> findById(Long id) {
        String query = "SELECT q.id, q.estimatedAmount, q.issueDate,q.validatedDate ,q.isAccepted, q.project_id, " +
                "p.projectName, p.profitMargin, p.surface ,p.totalCost, p.status, " +
                "c.id AS client_id, c.name, c.address, c.phone, c.isProfessional " +
                "FROM quotes q " +
                "JOIN projects p ON q.project_id = p.id " +
                "JOIN clients c ON p.client_id = c.id " +
                "WHERE q.id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Client client = new Client(
                            resultSet.getLong("client_id"),
                            resultSet.getString("name"),
                            resultSet.getString("address"),
                            resultSet.getString("phone"),
                            resultSet.getBoolean("isProfessional")
                    );

                    Project project = new Project(
                            resultSet.getLong("project_id"),
                            resultSet.getString("projectName"),
                            resultSet.getDouble("profitMargin"),
                            resultSet.getDouble("totalCost"),
                            resultSet.getString("status"),
                            resultSet.getDouble("surface"),
                            client
                    );

                    Devis foundDevis = new Devis(
                            resultSet.getLong("id"),
                            resultSet.getDouble("estimatedAmount"),
                            resultSet.getDate("issueDate").toLocalDate(),
                            resultSet.getDate("validatedDate") != null ? resultSet.getDate("validatedDate").toLocalDate() : null,
                            resultSet.getBoolean("isAccepted"),
                            project
                    );

                    return Optional.of(foundDevis);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Devis> findAll() {
        String query = "SELECT q.id, q.estimatedAmount, q.issueDate, q.validatedDate ,q.isAccepted, q.project_id, " +
                "p.projectName, p.profitMargin,p.surface ,p.totalCost, p.status," +
                "c.id AS client_id, c.name, c.address, c.phone, c.isProfessional " +
                "FROM quotes q " +
                "JOIN projects p ON q.project_id = p.id " +
                "JOIN clients c ON p.client_id = c.id";

        List<Devis> devisList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Client client = new Client(
                        resultSet.getLong("client_id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getBoolean("isProfessional")
                );

                Project project = new Project(
                        resultSet.getLong("project_id"),
                        resultSet.getString("projectName"),
                        resultSet.getDouble("profitMargin"),
                        resultSet.getDouble("totalCost"),
                        resultSet.getString("status"),
                        resultSet.getDouble("surface"),
                        client
                );

                Devis devis = new Devis(
                        resultSet.getLong("id"),
                        resultSet.getDouble("estimatedAmount"),
                        resultSet.getDate("issueDate").toLocalDate(),
                        resultSet.getDate("validatedDate") != null ? resultSet.getDate("validatedDate").toLocalDate() : null,
                        resultSet.getBoolean("isAccepted"),
                        project
                );

                devisList.add(devis);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return devisList;
    }


    @Override
    public Devis update(Devis devis) {
        String query = "UPDATE quotes SET estimatedAmount = ?, issueDate = ?, validatedDate = ? ,isAccepted = ?, project_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, devis.getEstimatedAmount());
            preparedStatement.setDate(2, java.sql.Date.valueOf(devis.getIssueDate()));
            preparedStatement.setBoolean(3, devis.isAccepted());
            preparedStatement.setLong(4, devis.getProject().getId());
            preparedStatement.setLong(5, devis.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                System.out.println("Quote updated successfully");
            } else {
                throw new SQLException("Update failed, no rows affected.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return devis;
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM quotes WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public void updateAmount(Long projectId, double amount) {
        String sql = "UPDATE quotes SET estimatedAmount = ? WHERE project_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setLong(2, projectId);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Devis updated successfully");
            } else {
                System.out.println("Update failed, no rows affected.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<Devis> findDevisByProjectId(Long projectId) {
        String sql = "SELECT q.id,\n" +
                "       q.estimatedamount,\n" +
                "       q.issuedate,\n" +
                "       q.validateddate,\n" +
                "       q.isaccepted,\n" +
                "       q.project_id AS project_id,\n" +
                "       p.id AS prId,\n" +
                "       p.projectname,\n" +
                "       p.client_id AS client_id,\n" +
                "       c.name AS clientName\n" +
                "FROM quotes q\n" +
                "JOIN projects p ON p.id = q.project_id\n" +
                "JOIN clients c ON c.id = p.client_id\n" +
                "WHERE q.project_id = ?\n";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Devis devis = new Devis();
                Project project = new Project();
                Client client = new Client();

                project.setProjectName(resultSet.getString("projectname"));
                client.setName(resultSet.getString("clientName"));
                project.setClient(client);

                devis.setId(resultSet.getLong("id"));
                devis.setEstimatedAmount(resultSet.getDouble("estimatedamount"));
                devis.setIssueDate(resultSet.getDate("issuedate").toLocalDate());

                Date validatedDate = resultSet.getDate("validateddate");
                devis.setValidatedDate(validatedDate != null ? validatedDate.toLocalDate() : null);

                devis.setAccepted(resultSet.getBoolean("isaccepted"));
                devis.setProject(project);

                return Optional.of(devis);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public boolean updateDevisStatus(Long devisId) {
        String sql = "UPDATE quotes SET isAccepted = true WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, devisId);
            int result = preparedStatement.executeUpdate();
            if(result == 1) {
                return true;
            }
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return false;
    }


}
