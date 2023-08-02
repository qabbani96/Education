package com.education.serviceImp;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.education.entity.Course;
import com.education.entity.ResponseBody;
import com.education.repository.CourseRepository;
import com.education.service.CourseService;

@Component

public class CourseServiceImp implements CourseService{

	@Autowired
	CourseRepository courseRepo;
	
	@Override
	public ResponseEntity<ResponseBody>  createCourse(Course course) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(course.getCourseName() == null) {
			result.put("message","Course Name is Empty");
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.BAD_REQUEST);
		}
		Course isCourseExist = courseRepo.findByCourseNameIgnoreCase(course.getCourseName());
		if(isCourseExist != null) {
			result.put("massage","Course Name Already Exist");
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.NOT_ACCEPTABLE);
		}
		if(course.getId() != null) {
			Course isCourseExist2 = courseRepo.findById(course.getId()).orElse(null);
			if(isCourseExist2 != null) {
				result.put("massage","id already exist please use update API to update course");
				return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.NOT_ACCEPTABLE);
			}
		}
		courseRepo.save(course);
		result.put("data", course);
		return new ResponseEntity<ResponseBody>(new ResponseBody("success",result),HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseBody> updateCourse(Long id,Course data) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(id == null) {
			result.put("massage", "id is null");
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.BAD_REQUEST);
		}
		Course course = courseRepo.findById(id).orElse(null);
		if(course == null) {
			result.put("massage", "Course Not Found "  );
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.NOT_FOUND);
		}
		if(data == null) {
			result.put("massage", "No Data sent"  );
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.BAD_REQUEST);	
		}
		if(data.getCourseName() == null) {
			result.put("massage", "Course Name is Empty"  );
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.NOT_ACCEPTABLE);
		}
		Course isCourseExist =  courseRepo.findByCourseNameIgnoreCase(data.getCourseName());
		if(isCourseExist != null) {
			result.put("massage", "Already We have course with same Name"  );
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.NOT_MODIFIED);
		}
		course.setCourseName(data.getCourseName());
		courseRepo.save(course);
		result.put("massage", "course Updated successfully"  );
		return new ResponseEntity<ResponseBody>(new ResponseBody("success",result),HttpStatus.OK);
	}

	@Override
	public ResponseBody deleteCourse(Long courseId) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(courseId == null) {
				result.put("massage", "course id is null");
				return new ResponseBody("failed",result);
			}
			Course course = courseRepo.findById(courseId).orElse(null);
			if(course == null) {
				result.put("massage", "Course Not Found");
				return new ResponseBody("failed",result);
			}
			if(course.getStudents().size() > 0) {
				result.put("massage", "Can`t delete course delete contain student should delete user before ");
				return new ResponseBody("failed",result);
			}
			courseRepo.delete(course);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("massage", "Something wrong");
			return new ResponseBody("failed",result);
			
		}
		result.put("massage", "course deleted succesfully" );
		return new ResponseBody("success",result);
	}

	@Override
	public ResponseBody getAllCourses(int page , int size) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(size == 0) {
				result.put("massage", "size should not be zero");
				return new ResponseBody("failed",result);
			}
			org.springframework.data.domain.Pageable  pageable =  PageRequest.of(page, size);
			List<Course> courses = (List<Course>) courseRepo.findAll(pageable);
			result.put("data", courses);
			return new ResponseBody("success",result);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("massage", "Something wrong");
			return new ResponseBody("failed",result);
		}
	}

	@Override
	public ResponseEntity<ResponseBody> getCourse(Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Course course = courseRepo.findById(id).orElse(null);
		if(course == null) {
			result.put("message", "Not Course found");
			return new ResponseEntity<ResponseBody>(new ResponseBody("success",result),HttpStatus.OK);
		}
		result.put("data", course);
		return new ResponseEntity<ResponseBody>(new ResponseBody("success",result),HttpStatus.OK);
	}

}
