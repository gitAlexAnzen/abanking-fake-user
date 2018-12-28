/*
 * Copyright (c) 2018 Anzen Soluciones S.A. de C.V.
 * Mexico D.F.
 * All rights reserved.
 *
 * THIS SOFTWARE IS  CONFIDENTIAL INFORMATION PROPIETARY OF ANZEN SOLUCIONES.
 * THIS INFORMATION SHOULD NOT BE DISCLOSED AND MAY ONLY BE USED IN ACCORDANCE THE TERMS DETERMINED BY THE COMPANY ITSELF.
 */
package mx.com.anzen.app.abanking.fake.user.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//import mx.com.anzen.app.abanking.coredata.service.impl.nosql.redis.repositories.generics.RedisSimpleCrudRepository;

import mx.com.anzen.app.abanking.fake.user.service.FakeUserService;
import mx.com.anzen.app.abanking.fake.user.service.redis.model.FakeUser;
import mx.com.anzen.app.abanking.fake.user.service.redis.repository.RedisSimpleCrudRepository;
import mx.com.anzen.app.abanking.fake.user.util.constants.FakeUserConstants;

/**
 * <p>TODO [Add comments of the class]</p>
 *
 * @version abanking-fake-user
 * @since abanking-fake-user
 */
@Service("fakeUserServiceImpl")
public class FakeUserServiceImpl implements FakeUserService {

	private static final Logger logger = LoggerFactory.getLogger(FakeUserServiceImpl.class);
   
	@Value("${maxim.attempts}")
	protected String maximAttemptsMessage;
	
	@Value("${attempt.failed}")
	protected String attemptFailed;
	
	@Value("${first.attempt}")
	protected String firstAttempt;

	@Autowired
	@Qualifier("fakeUserRepository")
	private RedisSimpleCrudRepository<FakeUser, String> fakeUserRepository;

	/**
	 * Time of expiration of objects in seconds
	 */
	private Long EXPIRATION_TIME_IN_SECONDS = new Long("400");

	/* 
	 * @see mx.com.anzen.app.abanking.fake.user.service.SessionService#existFakeUser(java.lang.String)
	 */
	@Override
	public FakeUser existFakeUser(String identifier) {

		logger.debug("Validating if a user exist: {} ", identifier);

		FakeUser user = fakeUserRepository.findOne(identifier);

		logger.debug("User exist executed");
		return user;
	}

	/* 
	 * @see mx.com.anzen.app.abanking.fake.user.service.FakeUserService#saveFakeUser(mx.com.anzen.app.abanking.fake.user.service.redis.model.FakeUser)
	 */
	@Override
	public FakeUser saveFakeUser(String userID) {

		logger.info("Creatin fake user : {}", userID);

		String fakeUserId = nextSessionId();
		FakeUser userToCreate = new FakeUser();
		userToCreate.setId(fakeUserId);
		userToCreate.setUser(userID);
		userToCreate.setMessage(firstAttempt);
		userToCreate.setAttempts(FakeUserConstants.INIT_ATTEMPTS);

		userToCreate = fakeUserRepository.save(userToCreate, EXPIRATION_TIME_IN_SECONDS);
		logger.debug("Identifier user generated is: {}", fakeUserId);

		return userToCreate;
	}

	/* 
	 * @see mx.com.anzen.app.abanking.fake.user.service.SessionService#updateAttempts(mx.com.anzen.app.abanking.fake.user.service.redis.model.FakeUser)
	 */
	@Override
	public FakeUser updateAttempts(FakeUser fakeUser) {

		logger.info("Updating user: {}", fakeUser.getUser());
		FakeUser updateUser = new FakeUser();
		updateUser.setId(fakeUser.getId());
		updateUser.setUser(fakeUser.getUser());

		int attempts = fakeUser.getAttempts() + FakeUserConstants.ADD_ATTEMPTS;
		updateUser.setAttempts(attempts);

		updateUser = fakeUserRepository.save(updateUser, EXPIRATION_TIME_IN_SECONDS);

		if(updateUser.getAttempts() == FakeUserConstants.MAX_ATTEMPTS) {
			updateUser.setMessage(maximAttemptsMessage);
		} else {
			updateUser.setMessage(attemptFailed);
		}

		logger.info("Success update, attempt: {} ", updateUser.getAttempts());
		return updateUser;
	}

	/**
	 * <p>TODO [Add comments of method]</p>
	 *
	 * @return
	 */
	public String nextSessionId() {
		//the UUID carry out both criteria: uniqueness and secure random
		UUID uuid = UUID.randomUUID();
		String result = Long.toHexString(uuid.getMostSignificantBits())+Long.toHexString(uuid.getLeastSignificantBits());
		return result;
	}

}
