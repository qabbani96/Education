package com.education.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.education.entity.ResponseBody;

@Service
public interface UserService {

	public ResponseEntity<ResponseBody> login(String username , String password);
}
