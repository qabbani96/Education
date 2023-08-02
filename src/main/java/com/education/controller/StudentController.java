package com.education.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.education.entity.Course;
import com.education.entity.ResponseBody;
import com.education.entity.Student;
import com.education.service.StudentService;

@RequestMapping("/student")
@RestController
public class StudentController {

	@Autowired
	StudentService studentService;
	
	@PostMapping("/create")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<ResponseBody> createStudent(HttpServletRequest request ,@Valid @RequestBody  Student student) {
		return this.studentService.createStudent(student);
	}
	
	@PostMapping("/{studentId}/register/course")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<ResponseBody> registerOnCourse(HttpServletRequest request, @PathVariable Long studentId , @RequestBody Set<Course> courses){
		 return this.studentService.registrationOnCours(studentId , courses);
	}
	
	@GetMapping("/{studentId}/courses")
	public ResponseEntity<ResponseBody> allCourseForStudent(HttpServletRequest request, @PathVariable Long studentId){
		return this.studentService.getStudentCourse(studentId);
	}
	
	@DeleteMapping("/{studentId}")
	public ResponseEntity<ResponseBody> deleteStudent(HttpServletRequest request, @PathVariable Long studentId){
		return this.studentService.deleteStudent(studentId);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ResponseBody> getAllStudent(HttpServletRequest request, @RequestParam int page , @RequestParam int size){
		return this.studentService.getAllStudent(page , size);
	}
	
	
}
