package com.projects.salon;

import com.projects.salon.repository.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientRepositoryTests {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void test_get_all() {
        clientRepository.getAll()
                .forEach(System.out::println);
    }

    @Test
    public void test_get_by_id() {
        assertThat(clientRepository.getById(1), notNullValue());
    }
}
