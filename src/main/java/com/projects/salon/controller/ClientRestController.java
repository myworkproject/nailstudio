package com.projects.salon.controller;

import com.projects.salon.entity.Client;
import com.projects.salon.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRestController.class);

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Client> getAll() {
        LOGGER.debug("Returns all clients.");
        return clientRepository.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Client getById(@PathVariable int id) {
        LOGGER.debug("Returns client: {}.", id);
        return clientRepository.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        LOGGER.debug("Delete client: {}.", id);
        clientRepository.delete(id);
    }

    @PostMapping
    public int save(Client client) {
        client.setId(null);
        LOGGER.debug("Save new client: {}", client);
        return clientRepository.save(client);
    }

    @PutMapping
    public void update(Client client) {
        LOGGER.debug("Update client: {}\nname={},phone={}.", client.getId(), client.getName(), client.getPhone());
        clientRepository.update(client);
    }
}
