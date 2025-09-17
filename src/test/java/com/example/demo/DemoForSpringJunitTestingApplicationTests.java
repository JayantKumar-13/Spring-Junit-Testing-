package com.example.demo;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoForSpringJunitTestingApplicationTests {

	@Test
	void contextLoads() {
	}

//	@BeforeEach
//	void Setup(){
//		System.out.println("Run before every tests");
//	}

//	@Test
//	void Test1(){
//		System.out.println("Running test 1");
//	}
//
//
//	@Test
//	void Test2(){
//		System.out.println("Running test 2");
//	}


	@Test
	void testAdd(){
		int a = 5 , b = 3;

		int res = add(a , b);
		Assertions.assertThat(res)
				.isEqualTo(8)
				.isCloseTo(9 , Offset.offset(1));
	}

	int add(int a , int b){
		return a+b;
	}
}
