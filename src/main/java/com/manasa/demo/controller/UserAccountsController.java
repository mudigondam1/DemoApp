package com.manasa.demo.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.manasa.demo.constants.Constants;
import com.manasa.demo.dao.PncRepo;
import com.manasa.demo.model.PncModel;
import com.manasa.demo.service.TransactionService;
import com.manasa.demo.service.UserService;


@RestController
@RequestMapping("/" + Constants.USER_DETAILS)
public class UserAccountsController 
{	
	private static final Logger logger = LoggerFactory.getLogger(UserAccountsController.class);

	@Autowired
	TransactionService tservice;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PncRepo pncRepo;
	
	@GetMapping(value = "/" + Constants.PNC_USERS, produces= "application/json")
	public ResponseEntity<List<PncModel>> getPncUsers() throws Exception
	{
		logger.info("Entering into the method getPncUsers");
		ResponseEntity response = new ResponseEntity("{\"Users Retrieval Status\":\""+Constants.USERS_NOT_FOUND+"\"}", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		List<PncModel> list = null;
		list = userService.getUserDetails();
		
		if(!(list.isEmpty()))
		{
			response = new ResponseEntity(list, new HttpHeaders(), HttpStatus.OK);
		}
		else
		{
			response = new ResponseEntity("{\"Users Retrieval Status\":\""+Constants.DISPLAY_FAILED+"\"}", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.info("Exiting the method getPncUsers");
		return response;
	}
	
	@GetMapping(value = "/" + Constants.GET_USER, produces= "application/json")
	public ResponseEntity<Optional<PncModel>> getPncUser(HttpServletRequest request, @RequestParam(value = "acc_no", required = true) Long acc_no) throws Exception
	{
		logger.info("Entering into the method getPncUser");
		ResponseEntity response = new ResponseEntity("{\"User Retrieval Status\":\""+Constants.INVALID_DETAILS+"\"}", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		
		if(acc_no != null)
		{
			if(userService.getUser(acc_no) != null)
			{
				response = new ResponseEntity( userService.getUser(acc_no), new HttpHeaders(), HttpStatus.OK);
			}
			else
			{
				response = new ResponseEntity("{\"User Retrieval Status\":\""+Constants.USER_SEARCH_FAILED+"\"}", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.info("Exiting the method getPncUser");
		return response;
	}
	
	@PostMapping(value = "/" + Constants.ADD_PNC_USER, produces= "application/json")
	public ResponseEntity<PncModel> addPncUser(HttpServletRequest request, @RequestBody PncModel user) throws Exception
	{
		logger.info("Entering into the method addPncUser");
		ResponseEntity response = new ResponseEntity("{\"Adding User Status\":\""+Constants.INVALID_DETAILS+"\"}", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		
		if((user.getAcc_no() != null) && (user.getAcc_balance() != null) && (user.getFname() != null)) 
		{
			if(!(pncRepo.existsById(user.getAcc_no())))
			{
				if(userService.addUser(user) != null) 
				{
					response = new ResponseEntity(user, new HttpHeaders(), HttpStatus.CREATED);
				}
				else 
				{
					response = new ResponseEntity("{\"Adding User Status\":\""+Constants.ADD_FAILED+"\"}", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			else
			{
				response = new ResponseEntity("{\"Adding User Status\":\""+Constants.USER_EXISTS+"\"}", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.info("Exiting out of the method addPncUser");
		return response;
	}
	
	
	@PutMapping(value = "/" + Constants.UPDATE_PNC_USER, produces= "application/json")
	public ResponseEntity<PncModel> updatePncUser(HttpServletRequest request, @RequestBody PncModel user) throws Exception
	{
		logger.info("Entering into the method updatePncUser");
		ResponseEntity response = new ResponseEntity("{\"Updating User Status\":\""+Constants.INVALID_DETAILS+"\"}", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		
		if((user.getAcc_balance() != null) || (user.getFname() != null)) {
			
			if(userService.addUser(user) != null) 
			{
				response = new ResponseEntity(user, new HttpHeaders(), HttpStatus.OK);
			}
			else 
			{
				response = new ResponseEntity("{\"Updating User Status\":\""+Constants.UPDATING_USER_FAILED+"\"}", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		logger.info("Exiting the method updatePncUser");
		return response;
	}
	

	@DeleteMapping(value = "/" + Constants.DELETE_USER, produces= "application/json")
	public ResponseEntity<String> deletePncUser(HttpServletRequest request, @RequestParam(value = "acc_no", required = true) Long acc_no) throws Exception
	{
		logger.info("Entering into the method deletePncUser");
		boolean userDeleteStatus = false;
		ResponseEntity response = new ResponseEntity("{\"User Removal Status\":\""+Constants.INVALID_DETAILS+"\"}", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		
		if(acc_no != null)
		{
			userDeleteStatus = userService.deleteUser(acc_no);
			if(userDeleteStatus == true)
			{
				response = new ResponseEntity("{\"User Removal Status\":\""+Constants.USER_REMOVED+"\"}", new HttpHeaders(), HttpStatus.OK);
			}
			else
			{
				response = new ResponseEntity("{\"User Removal Status\":\""+Constants.DELETE_FAILED+"\"}", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.info("Exiting the method deletePncUser");
		return response;
	}
	

}