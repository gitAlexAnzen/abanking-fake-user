/*
 * Copyright (c) 2018 Anzen Soluciones S.A. de C.V.
 * Mexico D.F.
 * All rights reserved.
 *
 * THIS SOFTWARE IS  CONFIDENTIAL INFORMATION PROPIETARY OF ANZEN SOLUCIONES.
 * THIS INFORMATION SHOULD NOT BE DISCLOSED AND MAY ONLY BE USED IN ACCORDANCE THE TERMS DETERMINED BY THE COMPANY ITSELF.
 */
package mx.com.anzen.app.abanking.fake.user.service.redis.repository;


import mx.com.anzen.app.abanking.fake.user.service.redis.model.FakeUser;
import mx.com.anzen.app.abanking.fake.user.util.RedisUtils;

/**
 * <p>TODO [Add comments of the class]</p>
 * @version abanking-fake-user
 * @since abanking-fake-user
 */
public class FakeIDUserRepository extends AbstractRedisSimpleCrudRepository<FakeUser, String>  {
	
	@Override
	public String getKey(FakeUser t) {
		return RedisUtils.generateKeyForSessionByUser(t);
	}

	/* 
	 * @see org.springframework.data.repository.CrudRepository#count()
	 */
	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	

//	@Override
//	public String getNamespace() {
//		return RedisUtils.SESSION_BY_USER_PREFIX;
//	}

}
