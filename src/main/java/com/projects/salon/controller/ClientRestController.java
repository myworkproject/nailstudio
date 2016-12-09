package com.projects.salon.controller;

import com.projects.salon.entity.Client;
import com.projects.salon.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientRestController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/all")
    public List<Client> getAll() {
        return clientRepository.getAll();
    }

    @GetMapping("/{id}")
    public Client getById(@PathVariable int id) {
        return clientRepository.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        clientRepository.delete(id);
    }

    @PostMapping
    public int save(Client client) {
        client.setId(null);
        return clientRepository.save(client);
    }

    @PutMapping
    public void update(Client client) {
        clientRepository.update(client);
    }
}
