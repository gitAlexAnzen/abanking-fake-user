/*
 * Copyright (c) 2018 Anzen Soluciones S.A. de C.V.
 * Mexico D.F.
 * All rights reserved.
 *
 * THIS SOFTWARE IS  CONFIDENTIAL INFORMATION PROPIETARY OF ANZEN SOLUCIONES.
 * THIS INFORMATION SHOULD NOT BE DISCLOSED AND MAY ONLY BE USED IN ACCORDANCE THE TERMS DETERMINED BY THE COMPANY ITSELF.
 */
package mx.com.anzen.app.abanking.fake.user.service;



import mx.com.anzen.app.abanking.fake.user.service.redis.model.FakeUser;

/**
 * <p>TODO [Add comments of the class]</p>
 *
 * @version abanking-fake-user
 * @since abanking-fake-user
 */
public interface FakeUserService {
	
	/**
	 * Validate user from its identifier
	 * @param sessionID the session identifier
	 * @return true if the session is valid, false otherwise
	 * @throws CoreDataTechnicalException an technical error occurred
	 */
	FakeUser existFakeUser(String identifier);

	/**
	 * Updates attempts, this is needed in case some data is shared beok vatween operations
	 * @param userSession is session object
	 * @return the session
	 * @throws InvalidSessionException no session has been found for the user
	 * @throws CoreDataTechnicalException a technical error occurred
	 */
	FakeUser updateAttempts(FakeUser fakeUser);

	/**
	 * <p>TODO [Add comments of method]</p>
	 * 
	 * @param userSession
	 * @return
	 */
	FakeUser saveFakeUser(String userSession);

	
}
