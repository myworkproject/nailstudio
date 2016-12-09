package com.projects.salon.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

}