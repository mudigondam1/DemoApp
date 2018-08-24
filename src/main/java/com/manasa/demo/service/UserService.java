package com.manasa.demo.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manasa.demo.dao.PncRepo;
import com.manasa.demo.model.PncModel;

@Service
public class UserService 
{	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	PncRepo pncRepo;
	
	public PncModel addUser(PncModel user) 
	{
		try 
		{
			pncRepo.save(user);
		}
		catch(Exception e) 
		{
			logger.error("Exception while adding a user: ", e);
			user = null;
		}
		return user;
	}

	public List<PncModel> getUserDetails() 
	{
		List<PncModel> list = null;
		try
		{
			list = pncRepo.findAll();
		}
		catch(Exception e)
		{
			logger.error("Exception while getting the users list: ", e);
		}
		return list;
	}
	
	public Optional<PncModel> getUser(Long acc_no) 
	{
		Optional<PncModel> user = null;
		try 
		{
			user = pncRepo.findById(acc_no);
		}
		catch(Exception e) 
		{
			logger.error("Exception while adding a user: ", e);
			user = null;
		}
		return user;
	}
	
	public boolean deleteUser(Long acc_no) 
	{
		boolean userDeleted = false;
		try 
		{
			if(true)
			{
				PncModel user = pncRepo.getOne(acc_no);
				pncRepo.delete(user);
				userDeleted = true;
			}
		}
		catch(Exception e)
		{
			logger.error("Exception while deleting the user: ", e);
		}
		return userDeleted;
	}
}
