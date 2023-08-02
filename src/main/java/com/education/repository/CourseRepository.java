package com.education.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.education.entity.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long>{

	Course findByCourseNameIgnoreCase(String courseName);

	List<Course> findAll(Pageable pageable);
}
