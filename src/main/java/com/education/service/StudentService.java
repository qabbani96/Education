package com.education.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.education.entity.Course;
import com.education.entity.ResponseBody;
import com.education.entity.Student;

@Service
public interface StudentService {

	ResponseEntity<ResponseBody> createStudent(Student student);
	ResponseEntity<ResponseBody> registrationOnCours(Long studentId , Set<Course> courses);
	ResponseEntity<ResponseBody> getStudentCourse(Long studentId);
	ResponseEntity<ResponseBody> deleteStudent(Long studentId);
	ResponseEntity<ResponseBody> getAllStudent(int page , int size);
}
