package com.application.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.application.model.Professor;
import com.application.model.User;
import com.application.services.ProfessorService;
import com.application.services.UserService;
import javax.validation.Valid;


@RestController
public class RegistrationController 
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfessorService professorService;
	
	@PostMapping("/registeruser")
	@CrossOrigin(origins = "http://localhost:4200")
	public User registerUser(@Valid @RequestBody User user) throws Exception
	{
		String currEmail = user.getEmail();
		String newID = getNewID();
		user.setUserid(newID);
		
		if(currEmail != null && !"".equals(currEmail))
		{
			User userObj = userService.fetchUserByEmail(currEmail);
			if(userObj != null)
			{
				throw new Exception("User with "+currEmail+" already exists !!!");
			}
		}
		User userObj = null;
		userObj = userService.saveUser(user);
		return userObj;
	}
	
	@PostMapping("/registerprofessor")
	@CrossOrigin(origins = "http://localhost:4200")
	public Professor registerDoctor(@Valid @RequestBody Professor professor) throws Exception
	{
		String currEmail = professor.getEmail();
		String newID = getNewID();
		professor.setProfessorid(newID);
		professor.setStatus("false");  
		
		if(currEmail != null && !"".equals(currEmail))
		{
			Professor professorObj = professorService.fetchProfessorByEmail(currEmail);
			if(professorObj != null)
			{
				throw new Exception("Professor with "+currEmail+" already exists !!!");
			}
		}
		Professor professorObj = null;
		professorObj = professorService.saveProfessor(professor);
		return professorObj;
	}
	
	public String getNewID()
	{
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+"0123456789"+"abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) 
        {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
	}
}