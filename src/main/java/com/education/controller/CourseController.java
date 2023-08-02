package com.education.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.education.entity.Course;
import com.education.entity.ResponseBody;
import com.education.service.CourseService;

@RequestMapping("/course")
@RestController
public class CourseController {

	@Autowired
	CourseService courseService;
	
	@PostMapping("/create")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<ResponseBody> createCourse(HttpServletRequest request,@RequestBody Course course) {
		return this.courseService.createCourse(course);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseBody> getCourseInfo(HttpServletRequest request,@PathVariable Long id) {
		return this.courseService.getCourse(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseBody> updateCourse(HttpServletRequest request,@PathVariable Long id ,@RequestBody Course data){
		return this.courseService.updateCourse(id,data);
	}
	
	@GetMapping
	public ResponseBody getAllCourses(HttpServletRequest request,@RequestParam int page , @RequestParam int size) {
		return this.courseService.getAllCourses(page , size);
	}
	
	@DeleteMapping("/{courseId}")
	public ResponseBody deleteCourse(HttpServletRequest request,@PathVariable Long courseId){
		return this.courseService.deleteCourse(courseId);
	}
	
}
