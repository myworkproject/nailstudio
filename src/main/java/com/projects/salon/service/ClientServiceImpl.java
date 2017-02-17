package com.projects.salon.service;

import com.projects.salon.entity.Client;
import com.projects.salon.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Secured("ADMIN")
    public List<Client> getAll() {
        return clientRepository.getAll();
    }

    @Override
    @Secured("ADMIN")
    public Client getById(int id) {
        return clientRepository.getById(id);
    }

    @Override
    @Secured("ADMIN")
    public Client getByTelephone(String telephone) {
        return clientRepository.getByTelephone(telephone);
    }

    @Override
    @Secured("ADMIN")
    public int save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    @Secured("ADMIN")
    public void update(Client client) {
        clientRepository.update(client);
    }

    @Override
    @Secured("ADMIN")
    public void delete(int id) {
        clientRepository.delete(id);
    }
}
