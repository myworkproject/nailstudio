package com.projects.salon.service;

import com.projects.salon.entity.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAll();

    Client getById(int id);

    Client getByTelephone(String telephone);

    void save(Client client);

    void update(Client client);

    void delete(int id);
}
