package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoForSpringJunitTestingApplicationTests {

	@Test
	void contextLoads() {
	}

	@BeforeEach
	void Setup(){
		System.out.println("Run before every tests");
	}

	@Test
	void Test1(){
		System.out.println("Running test 1");
	}

	@Test
	void Test2(){
		System.out.println("Running test 2");
	}

}
