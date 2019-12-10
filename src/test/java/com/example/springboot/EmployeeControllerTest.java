package com.example.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springboot.repository.EmployeeRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {
	
	@Mock
	EmployeeRepository repository;
	
	public void setUp(){
		
	}

	@Test
	public void test() {
		repository = Mockito.mock(EmployeeRepository.class);
	}

}
