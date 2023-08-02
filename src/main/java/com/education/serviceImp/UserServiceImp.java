package com.education.serviceImp;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.education.entity.ResponseBody;
import com.education.entity.Student;
import com.education.repository.StudentRepository;
import com.education.security.JWTHandler;
import com.education.service.UserService;

@Component
public class UserServiceImp implements UserService{

	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	private BCrypt bCrypt;
	
	@Autowired
	JWTHandler jwtHandler;
	
	@Override
	public ResponseEntity<ResponseBody> login(String username, String password) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(username == null || password == null) {
			result.put("massage", "username or password is empty");
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.BAD_REQUEST);
		}
		Student student = studentRepo.findByUserName(username);
		if(student == null) {
			result.put("massage", "username is not found");
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed",result),HttpStatus.OK);
		}
		if (!bCrypt.checkpw(password.toString(), student.getPassword().toString())) {
			result.put("massage", "Password is wrong");
			return new ResponseEntity<ResponseBody>(new ResponseBody("failed", result),HttpStatus.UNAUTHORIZED);
		}
		
		String token = null;
		token = jwtHandler.generateToken(student.getUserName());
		student.setAccessToken(token);
		studentRepo.save(student);
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("username", username);
		data.put("token", token);
		result.put("data", data);
		return new ResponseEntity<ResponseBody>(new ResponseBody("success", result),HttpStatus.OK);
	}

}
