package com.education;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.education.entity.Student;
import com.education.repository.StudentRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication

public class EducationApplication {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	StudentRepository studentRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(EducationApplication.class, args);
		
		
	}

	@PostConstruct
	public void creationFirst() {
		Student std =  studentRepository.findByUserName("UserForRegister");
		if(std != null) {
			Student s = new Student();
			
			s.setFullName("Mohammad");
			s.setUserName("UserForRegister");
			s.setMobile("0796945841");
			s.setEmail("qabba@gmail.com");
			s.setAddress("Amman");
			s.setPassword(bCryptPasswordEncoder.encode("Testing@123$"));
			studentRepository.save(s);
		}
		
	}
	
}
