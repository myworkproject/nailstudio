package com.projects.salon.repository;

import com.projects.salon.entity.Client;

import java.util.List;

public interface ClientRepository {
    List<Client> getAll();

    Client getById(int id);

    Client getByTelephone(String telephone);

    int save(Client client);

    void update(Client client);

    void delete(int id);
}
