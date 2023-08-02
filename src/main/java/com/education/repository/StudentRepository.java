package com.education.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.education.entity.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long>{

	Student findByUserName(String username);

	List<Student> findAll(Pageable pageable);
	
	Student findByAccessToken(String accessToken);
	
}
