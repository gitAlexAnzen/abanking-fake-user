/*
 * Copyright (c) 2018 Anzen Soluciones S.A. de C.V.
 * Mexico D.F.
 * All rights reserved.
 *
 * THIS SOFTWARE IS  CONFIDENTIAL INFORMATION PROPIETARY OF ANZEN SOLUCIONES.
 * THIS INFORMATION SHOULD NOT BE DISCLOSED AND MAY ONLY BE USED IN ACCORDANCE THE TERMS DETERMINED BY THE COMPANY ITSELF.
 */
package mx.com.anzen.app.abanking.fake.user.util;

import mx.com.anzen.app.abanking.fake.user.service.redis.model.FakeUser;

/**
 * <p>TODO [Add comments of the class]</p>
 * 
 * @author alexander
 * @version abanking-fake-user
 * @since abanking-fake-user
 */
public class RedisUtils {
	public static final String WILDCARD = "*";

	/**
	 * the account entity prefix for the key linked to an account entity
	 */
	public static final String ACCOUNT_ENTITY_PREFIX = "ACC";

	/**
	 * the account's lock entity prefix
	 */
	public static final String ACCOUNT_LOCK_ENTITY_PREFIX = "LACC";

	/**
	 * the session entity prefix
	 */
	public static final String FAKE_USER_ENTITY_PREFIX = "FAKE";

	/**
	 * the session by user entity prefix
	 */
	public static final String SESSION_BY_USER_PREFIX = "SBU";

	/**
	 * the cross-request-data entity prefix
	 */
	public static final String CROSS_REQUEST_DATA_ENTITY_PREFIX = "CRD";

	/**
	 * the num-tran prefix
	 */
	public static final String NUM_TRAN = "NUMTRAN";
	
	/**
	 * the separator used to distinguish the key different fields
	 */
	public static final String FIELD_SEPARATOR = "$";

	/**
	 * the loading-status field name of an account object in Redis
	 */
	public static final String LOADING_STATUS_FIELD_NAME = "loadingStatus";

	/**
	 * the transactions field name of an account object in Redis
	 */
	public static final String TRANSACTIONS_FIELD_NAME = "transactions";

	/**
	 * the loading-status value for "no started" in Redis
	 */
	public static final String LOADING_STATUS_NOT_STARTED_VALUE = "0";

	/**
	 * the loading-status value for "started" in Redis
	 */
	public static final String LOADING_STATUS_STARTED_VALUE = "1";

	/**
	 * the loading-status value for "ended" in Redis
	 */
	public static final String LOADING_STATUS_END_VALUE = "2";

	/**
	 * the loading-status value for "error" in Redis
	 */
	public static final String LOADING_STATUS_ERROR_VALUE = "3";

	/**
	 * obtains the key value of an user's account
	 * @param userLogin the user's login to get the key for
	 * @param bankID the user's bank-identifier to get the key for
	 * @param accountID the account-identifier
	 * @return the key corresponding to the account in the redis database
	 */
	public static String generateKeyForAccount(String userLogin, Integer bankID, String accountID) {
		return ACCOUNT_ENTITY_PREFIX + FIELD_SEPARATOR + bankID + FIELD_SEPARATOR + userLogin + FIELD_SEPARATOR
			+ accountID;
	}

	/**
	 * obtains the value of an account's lock
	 * @param userLogin the user's login to get an account's lock for
	 * @param bankID the user's bank-identifier to get an account's lock for
	 * @param accountID the account-identifier
	 * @return the account's lock corresponding to the account in the Redis database
	 */
	public static String generateLockForAccount(String userLogin, Integer bankID, String accountID) {
		return ACCOUNT_LOCK_ENTITY_PREFIX + FIELD_SEPARATOR + bankID + FIELD_SEPARATOR + userLogin + FIELD_SEPARATOR
			+ accountID;
	}

	/**
	 * obtains the key value of an user's session
	 * @param sessionID the session to get the key for
	 * @return the key corresponding to the session in the Redis database
	 */
	public static String generateKeyForSession(Integer sessionID) {
		return generateKeyForSession(sessionID.toString());
	}

	/**
	 * obtains the key value of an user's session
	 * @param sessionID the session to get the key for
	 * @return the key corresponding to the session in the Redis database
	 */
	public static String generateKeyForSession(String sessionID) {
		return FAKE_USER_ENTITY_PREFIX + FIELD_SEPARATOR + sessionID;
	}

	/**
	 * obtains the key value of a session retrieved by user
	 * @param userID the user to get the session for
	 * @return the key corresponding to the sessionByUser entity in the Redis database
	 */
	public static String generateKeyForSessionByUser(FakeUser userID) {
		return SESSION_BY_USER_PREFIX + FIELD_SEPARATOR + userID.getUser(); 
	}
	
	/**
	 * obtains the key value of a num tran
	 * @return the key corresponding to the num-tran in the Redis database
	 */
	public static String generateNumTran() {
		return NUM_TRAN;
	}
}
