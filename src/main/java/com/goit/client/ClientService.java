package com.goit.client;

import com.goit.exception.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private final PreparedStatement createSt;
    private final PreparedStatement selectMaxIdSt;
    private final PreparedStatement getByIdSt;
    private final PreparedStatement setNameSt;
    private final PreparedStatement deleteByIdSt;
    private final PreparedStatement listAllSt;

    public ClientService(Connection connection) throws SQLException {
        createSt = connection.prepareStatement("INSERT INTO client (name) VALUES (?)");
        selectMaxIdSt = connection.prepareStatement("SELECT max(id) as maxId FROM client");
        getByIdSt = connection.prepareStatement("SELECT name FROM client WHERE id=?");
        setNameSt = connection.prepareStatement("UPDATE client SET name=? WHERE id=?");
        deleteByIdSt = connection.prepareStatement("""
                DELETE project_worker
                WHERE project_id in (SELECT id FROM project WHERE client_id = ?);
                DELETE project WHERE client_id = ?;
                DELETE FROM client WHERE id=?
                """);
        listAllSt = connection.prepareStatement("SELECT * FROM client");
    }

    public long create(String name) throws SQLException {
        checkNameLength(name);
        createSt.setString(1, name);
        createSt.executeUpdate();
        long id;
        try (ResultSet rs = selectMaxIdSt.executeQuery()) {
            rs.next();
            id = rs.getLong("maxId");
        }
        return id;
    }

    private static void checkNameLength(String name) {
        if (name.length() < 2) {
            throw new DbException("Client's name is too short. Required length is more than 2 symbols and less than 1000 symbols.");
        } else if (name.length() > 1000) {
            throw new DbException("Client's name is too long. Required length is more than 2 symbols and less than 1000 symbols.");
        }
    }

    String getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        String name;
        try (ResultSet rs = getByIdSt.executeQuery()) {
            rs.next();
            name = rs.getString("name");
        }
        return name;
    }

    void setName(long id, String name) throws SQLException {
        checkNameLength(name);
        checkId(id);
        setNameSt.setString(1, name);
        setNameSt.setLong(2, id);
        setNameSt.executeUpdate();
    }

    private void checkId(long id) throws SQLException {
        long maxId;
        try (ResultSet rs = selectMaxIdSt.executeQuery()) {
            rs.next();
            maxId = rs.getLong("maxId");
        }
        if (id < 0 || id > maxId) {
            throw new DbException("Client with id " + id + " does not exist.");
        }
    }

    void deleteById(long id) throws SQLException {
        checkId(id);
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.setLong(2, id);
        deleteByIdSt.setLong(3, id);
        deleteByIdSt.executeUpdate();
    }

    List<Client> listAll() {
        List<Client> clients = new ArrayList<>();
        try (ResultSet rs = listAllSt.executeQuery()) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                Client client = new Client();
                client.setId(id);
                client.setName(name);
                clients.add(client);
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
        if (clients.isEmpty()) {
            throw new DbException("No clients in the table.");
        }
        return clients;
    }
}
