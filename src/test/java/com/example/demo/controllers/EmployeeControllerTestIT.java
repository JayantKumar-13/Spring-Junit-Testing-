package com.example.demo.controllers;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entities.Employee;
import com.example.demo.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class EmployeeControllerTestIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee TestEmployee;

    private EmployeeDto testEmployeeDto;

    @BeforeEach
    void setUp(){
        TestEmployee = Employee.builder()
                .id(1L)
                .email("jay@gmail.com")
                .name("Jayant")
                .salary(200L)
                .build();
        testEmployeeDto = EmployeeDto.builder()
                .id(1L)
                .email("jay@gmail.com")
                .name("Jayant")
                .salary(200L)
                .build();
        employeeRepository.deleteAll();         // Used to clear the database
    }
    @Test
    void testGetEmployeeBtId_success(){
        Employee savedEmployee = employeeRepository.save(TestEmployee);
        webTestClient.get()
                .uri("/employees/{id}" , savedEmployee.getId())// here we don't have to specify the whole path because we are running on the localHost
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())         // this is used here because if we pass testEmployee here then the saved and testEmployee will have differnet id
                .jsonPath("$.email").isEqualTo(savedEmployee.getEmail());
//                .isEqualTo(testEmployeeDto);     // either we can do this or this (.value)
//        .value(employeeDto -> {
//            assertThat(employeeDto.getEmail()).isEqualTo(savedEmployee.getEmail());
//            assertThat(employeeDto.getId()).isEqualTo(savedEmployee.getId());
//        })
    }

    @Test
    void testGetEmployeeById_Failure() {
        webTestClient.get()
                .uri("/employees/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateNewEmployee_whenEmployeeAlreadyExists_thenThrowException() {
        Employee savedEmployee = employeeRepository.save(TestEmployee);        // saving the employee

        webTestClient.post()           // saving the employee with same id as saved earlier
                .uri("/employees")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testCreateNewEmployee_whenEmployeeDoesNotExists_thenCreateEmployee() {
        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(testEmployeeDto.getEmail())
                .jsonPath("$.name").isEqualTo(testEmployeeDto.getName());
    }

    @Test
    void testUpdateEmployee_whenEmployeeDoesNotExists_thenThrowException() {
        webTestClient.put()
                .uri("/employees/999")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateEmployee_whenAttemptingToUpdateTheEmail_thenThrowException() {
        Employee savedEmployee = employeeRepository.save(TestEmployee);
        testEmployeeDto.setName("Random Name");
        testEmployeeDto.setEmail("random@gmail.com");

        webTestClient.put()
                .uri("/employees/{id}", savedEmployee.getId())
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

}