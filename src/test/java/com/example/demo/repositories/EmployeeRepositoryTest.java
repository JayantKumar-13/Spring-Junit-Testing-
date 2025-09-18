package com.example.demo.repositories;

import com.example.demo.TestContainerConfiguration;
import com.example.demo.entities.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(TestContainerConfiguration.class)
@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;
    @BeforeEach
    void setup(){
        employee = Employee.builder()
                
                .name("Jayant")
                .email("Jay@123").salary(100L)
                .build();
    }
    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnEmployee() {
        employeeRepository.save(employee);

        List<Employee> employeeList = employeeRepository.findByEmail(employee.getEmail());

        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList).isNotEmpty();
        Assertions.assertThat(employeeList.get(0).getEmail()).isEqualTo(employee.getEmail());
    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList() {
        String email = "JayantKumar@123";
        List<Employee> employeeList = employeeRepository.findByEmail(email);

        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList).isEmpty();
    }
}
