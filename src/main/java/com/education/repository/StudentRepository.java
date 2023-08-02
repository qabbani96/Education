package com.education.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.education.entity.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long>{

	Student findByUserName(String username);

	
	List<Student> findAll(Pageable pageable);
	
	@Query(value = "SELECT * from student where access_token = :accessToken", nativeQuery = true)
	Map<String, Object> findByAccessToken(String accessToken);
	
	
}
