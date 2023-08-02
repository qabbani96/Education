package com.education.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.education.entity.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long>{

	@Transactional
	Course findByCourseNameIgnoreCase(String courseName);

	@Transactional
	List<Course> findAll(Pageable pageable);
}
