package com.education.security;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.education.entity.Student;
import com.education.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.education.config.MainConfig;
import com.education.entity.ResponseBody;

@Component
public class ServiceInterceptor  implements HandlerInterceptor{

	private static final Logger log = Logger.getLogger(ServiceInterceptor.class.getName());

	@Autowired
	private JWTHandler jwtHandler;
	@Autowired
	private MainConfig mainConfig;	
	
	@Autowired
	StudentRepository studentRepo;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		response.reset();
		
		String token = request.getHeader("token");
		
		
		ResponseBody checkTokenExisting2 = checkTokenExisting(token);
		
		System.out.println("checkTokenExisting : "+checkTokenExisting2);
		if(checkTokenExisting2.getStatus().equalsIgnoreCase("-1")) {
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write(mapper.writeValueAsString(checkTokenExisting2));
			return false;
		}
		com.education.entity.ResponseBody responseLoadByToken = loadByAccessToken(token);
		System.out.println("------loadByToken--- :" + responseLoadByToken);
		if(!responseLoadByToken.getStatus().equalsIgnoreCase("0")) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write(mapper.writeValueAsString(responseLoadByToken));
			return false;
		}
		
		
		
		request.getSession().setAttribute("responseBody", responseLoadByToken.getResult());
		return true;
	}
	
	
	public ResponseBody loadByAccessToken(String token) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> std = studentRepo.findByAccessToken(token);
		
		if(std == null) {
			result.put("massage", "user not found");
			return new ResponseBody("-1",result);
		}
		 result.put("data",std);
		 return new ResponseBody("0",result);
	}
	

	public com.education.entity.ResponseBody checkTokenExisting(String token) {
		Map<String, Object> res = new HashMap<>();
		if (null == token || token.trim().isEmpty() || token.trim().equals("")) {
			return new ResponseBody("-1", res);
		} else {
			ResponseBody responseInvakidData = new ResponseBody("0", res);
			return responseInvakidData;
		}

	}
}