package com.projects.salon.api.version.one;

import com.projects.salon.entity.Client;
import com.projects.salon.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0.1/clients")
@Slf4j
public class ClientControllerAPI {

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/phone")
    public ResponseEntity<Client> getByTelephone(@RequestParam String telephone) {
        log.info("Checks client phone number: {}", telephone);
        Client byTelephone = clientRepository.getByTelephone(telephone);
        if (byTelephone != null) {
            return new ResponseEntity<>(byTelephone, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
