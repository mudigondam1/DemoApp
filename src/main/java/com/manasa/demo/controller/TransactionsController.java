package com.manasa.demo.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.manasa.demo.constants.Constants;
import com.manasa.demo.dao.PncRepo;
import com.manasa.demo.model.TransferModel;
import com.manasa.demo.service.TransactionService;
import com.manasa.demo.service.UserService;

@RestController
@RequestMapping("/" + Constants.TRANSACTIONS)
public class TransactionsController 
{	
	private static final Logger logger = LoggerFactory.getLogger(UserAccountsController.class);

	@Autowired
	PncRepo repo1;
	
	@Autowired
	TransactionService tservice;
	
	@Autowired
	UserService userService;
	
	@PostMapping(value = "/" + Constants.SEND_AMOUNT, produces= "application/json")
	public ResponseEntity<String> processMoneyTransfer(HttpServletRequest request, @RequestBody TransferModel tmodel) throws Exception
	{
		logger.info("Entering into the method: processMoneyTransfer");
		boolean moneyTransferStatus = false;
		ResponseEntity response = new ResponseEntity("{\"Transaction Status\":\""+Constants.INVALID_DETAILS+"\"}", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		if(tmodel.getAmount() != null || tmodel.getAmount() != 0 ) 
		{
			try
			{
				moneyTransferStatus= tservice.sendMoney(tmodel.getFromAccount(), tmodel.getToAccount(), tmodel.getAmount());
				
			}catch(Exception e)
			{
				logger.info("Exception occured while funds transfer");
			}
			
			if(moneyTransferStatus == true) 
			{
				response = new ResponseEntity("{\"Transaction Status\":\""+Constants.TRANSFER_DONE+"\"}" , new HttpHeaders(), HttpStatus.OK);
			}
			else 
			{
				response = new ResponseEntity("{\"Transaction Status\":\""+Constants.TRANSFER_FAILED+"\"}", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.info("Exiting out of the method: processMoneyTransfer");
		return response;
	}
}
