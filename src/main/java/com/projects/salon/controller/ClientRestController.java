package com.projects.salon.controller;

import com.projects.salon.entity.Client;
import com.projects.salon.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/client")
@Slf4j
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Client> getAll() {
        log.debug("Returns all clients.");
        return clientService.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpEntity<Client> getById(@PathVariable int id) {
        log.debug("Returns client: {}.", id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Expires", LocalDateTime.now().plusDays(1).toString());
        return new HttpEntity<>(clientService.getById(id), httpHeaders);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        log.debug("Delete client: {}.", id);
        clientService.delete(id);
    }

    @PostMapping
    public void saveOrUpdate(Client client) {
        if (client.getId() == 0) {
            client.setId(null);
            log.debug("Save new client: {}", client);
            clientService.save(client);
        } else {
            log.debug("Update client: {} name={},phone={}.", client.getId(), client.getFirstName(), client.getPhone());
            clientService.update(client);
        }
    }
}
