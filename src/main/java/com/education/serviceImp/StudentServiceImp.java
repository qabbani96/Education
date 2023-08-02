package com.education.serviceImp;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.education.entity.Course;
import com.education.entity.ResponseBody;
import com.education.entity.Student;
import com.education.repository.CourseRepository;
import com.education.repository.StudentRepository;
import com.education.security.JWTHandler;
import com.education.service.StudentService;

@Component

public class StudentServiceImp implements StudentService{

	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	CourseRepository courseRepo;
	
	@Autowired
	JWTHandler jwtHandler;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public ResponseEntity<ResponseBody> createStudent(Student student) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(student.getUserName() == null) {
			result.put("massage", "username is empty");
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.BAD_REQUEST);
		}
		Student dtaStudent =  studentRepo.findByUserName(student.getUserName());
		if(dtaStudent != null) {
			result.put("massage", "student with this username "+ student.getUserName() + " already exist");
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.NOT_ACCEPTABLE);
		}
		ResponseBody checkComxPass = checkPasswordComplexity(student.getPassword(), student.getUserName());
		if(checkComxPass.getStatus().equalsIgnoreCase("failed")) {
			result.put("massage", checkComxPass.getResult().get("massage"));
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.NOT_ACCEPTABLE);
		}
		try {
			student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
			
			studentRepo.save(student);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("massage","Something wrong");
			return new ResponseEntity<ResponseBody>(new ResponseBody("success",result),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		result.put("massage","Student Created Successfully ");
		return new ResponseEntity<ResponseBody>(new ResponseBody("success",result),HttpStatus.CREATED);
	
	}

	
	
	
	public ResponseBody checkPasswordComplexity(String password, String username) {
		Map<String, Object> passwordResult = new LinkedHashMap<String, Object>();
		if (password.contains(username)
				|| !password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$")) {
			passwordResult.put("massage", "password does not match the complexity");
			return new ResponseBody("failed",
					passwordResult);
		}
		return new ResponseBody("success", passwordResult);
	}




	@Override
	public ResponseEntity<ResponseBody> registrationOnCours(Long studentId, Set<Course> courses) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		Student student = studentRepo.findById(studentId).orElse(null);
		if(student == null) {
			result.put("massage","Student Not Found");
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.NOT_FOUND);	
		}
		student.setCourses(courses);
		try {
			studentRepo.save(student);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("massage","Something wrong");
			return new ResponseEntity<ResponseBody>(new ResponseBody("success",result),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		result.put("massage","Courses added succesfully for student");
		return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.OK);	
	}




	@Override
	public ResponseEntity<ResponseBody> getStudentCourse(Long studentId) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		if(studentId == null) {
			result.put("massage","student id is null");
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.BAD_REQUEST);
		}
		Student student = studentRepo.findById(studentId).orElse(null);
		if(student ==  null) {
			result.put("massage","student Not found");
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.NOT_FOUND);
		}
		result.put("data",student);
		return new ResponseEntity<ResponseBody>(new ResponseBody("success",result),HttpStatus.OK);
		
		
	}




	@Override
	public ResponseEntity<ResponseBody> deleteStudent(Long studentId) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(studentId == null) {
			result.put("massage","id not correct");
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.BAD_REQUEST);
		}
		Student student = studentRepo.findById(studentId).orElse(null);
		if(student == null) {
			result.put("massage","Studnet not found");
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.NOT_FOUND);
		}
		studentRepo.delete(student);
		result.put("massage","Studnet delete successfully");
		return new ResponseEntity<ResponseBody>(new ResponseBody("success",result),HttpStatus.OK);
	}




	@Override
	public ResponseEntity<ResponseBody> getAllStudent(int page , int size) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Student> listStd;
		try {
			if(size == 0) {
				result.put("massage", "size should not be zero");
				return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.BAD_REQUEST);
			}
			Pageable pageable = PageRequest.of(page, size);
			 listStd = (List<Student>) studentRepo.findAll(pageable);
			
		}catch(Exception e) {
			e.printStackTrace();
			result.put("massage", "something wrong");
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.BAD_REQUEST);
		}
		result.put("data", listStd);
		return new ResponseEntity<ResponseBody>(new ResponseBody("success",result),HttpStatus.OK);
	}
}
