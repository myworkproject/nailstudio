package com.projects.salon.service;

import com.projects.salon.entity.Client;
import com.projects.salon.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAll() {
        return clientRepository.getAll();
    }

    @Override
    public Client getById(int id) {
        return clientRepository.getById(id);
    }

    @Override
    public Client getByTelephone(String telephone) {
        return clientRepository.getByTelephone(telephone);
    }

    @Override
    public int save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void update(Client client) {
        clientRepository.update(client);
    }

    @Override
    public void delete(int id) {
        clientRepository.delete(id);
    }
}
