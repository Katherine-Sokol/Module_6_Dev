package com.goit.client;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTests {
    private ClientService clientService;
    private Connection connection;

    @BeforeEach
    void beforeEach() throws SQLException {
        String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        Flyway flyway = Flyway.configure().dataSource(url, null, null).load();
        flyway.migrate();
        connection = DriverManager.getConnection(url);
        clientService = new ClientService(connection);
    }

    @AfterEach
    void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    void TestThatClientIsCreated() throws SQLException {
        List<Client> oldList = clientService.listAll();
        String name = "client1";
        clientService.create(name);
        List<Client> listAfterCreate = clientService.listAll();
        assertEquals(listAfterCreate.size(), oldList.size()+1);
    }

    @Test
    void testThatGetByIdWorksCorrectly() throws SQLException {
        String name = "client1";
        long id = clientService.create(name);
        String clientName = clientService.getById(id);
        assertEquals(name, clientName);
    }

    @Test
    void testThatClientsNameChanged() throws SQLException {
        int id = 1;
        String newName = "newName";
        clientService.setName(id, newName);
        assertEquals(newName, clientService.getById(id));
    }

    @Test
    void testThatClientIsDeleted() throws SQLException {
        List<Client> oldList = clientService.listAll();
        clientService.deleteById(1);
        List<Client> listAfterDelete = clientService.listAll();
        assertEquals(listAfterDelete.size(), oldList.size()-1);
    }

    @Test
    void testThatClientsAreAddedToList() {
        List<Client> clients = clientService.listAll();
        assertFalse(clients.isEmpty());
    }
}