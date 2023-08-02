package com.education.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.education.entity.Course;
import com.education.entity.ResponseBody;

@Service
public interface CourseService {

	public ResponseEntity<ResponseBody> createCourse(Course course);
	public ResponseEntity<ResponseBody> updateCourse(Long id , Course data);
	public ResponseBody deleteCourse(Long course);
	public ResponseBody getAllCourses(int page , int size);
	public ResponseEntity<ResponseBody>  getCourse(Long id);
	
}
