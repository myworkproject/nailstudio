package com.projects.salon.repository;

import com.projects.salon.entity.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientRepositoryImplTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void get_all() throws Exception {
        clientRepository.getAll()
                .forEach(System.out::println);

    }

    @Test
    public void get_by_id() throws Exception {
        assertThat(clientRepository.getById(1), notNullValue());
    }

    @Test
    public void save_new_client() {
        Client client = new Client(null, "Sara", "+5533212");
        int key = clientRepository.save(client);
        client.setId(key);
        assertThat(client, is(clientRepository.getById(key)));
    }

    @Test
    public void update_client() {
        Client fromDB = clientRepository.getById(1);
        fromDB.setName("John");
        fromDB.setPhone("56649");
        clientRepository.update(fromDB);
        assertThat(fromDB, is(clientRepository.getById(fromDB.getId())));
    }
}