/*
 * Copyright (c) 2018 Anzen Soluciones S.A. de C.V.
 * Mexico D.F.
 * All rights reserved.
 *
 * THIS SOFTWARE IS  CONFIDENTIAL INFORMATION PROPIETARY OF ANZEN SOLUCIONES.
 * THIS INFORMATION SHOULD NOT BE DISCLOSED AND MAY ONLY BE USED IN ACCORDANCE THE TERMS DETERMINED BY THE COMPANY ITSELF.
 */
package mx.com.anzen.app.abanking.fake.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.anzen.app.abanking.fake.user.service.FakeUserService;
import mx.com.anzen.app.abanking.fake.user.service.redis.model.FakeUser;

/**
 * <p>TODO [Add comments of the class]</p>
 *
 * @version abanking-fake-user
 * @since abanking-fake-user
 */
@RestController
@RequestMapping("/fake")
public class FakeUserController {
	
	@Autowired
	private FakeUserService fakeUserService;
	

	private static final Logger logger = LoggerFactory.getLogger(FakeUserController.class);


	@ApiOperation(value = "Get fake user when it no is a valid in app")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public FakeUser getFakeUser(
			@ApiParam (value = "Represent client to generate fake alias ")
			@RequestParam(name = "user", required = false) String user,
			@RequestParam(name = "identifier", required = true) String identifier) {

		logger.info("Starting logic to generate fake user of {}", user);

		FakeUser fakeUser = fakeUserService.existFakeUser(identifier);

		if(fakeUser == null) {
			logger.debug("Creatin user: {}", user);
			fakeUser = fakeUserService.saveFakeUser(user);

		} else {
			logger.info("Update attempts of user: {}", user);
			fakeUser = fakeUserService.updateAttempts(fakeUser);
		}

		logger.info("Fake user executed success: {}");
		return fakeUser;		
	}

}
