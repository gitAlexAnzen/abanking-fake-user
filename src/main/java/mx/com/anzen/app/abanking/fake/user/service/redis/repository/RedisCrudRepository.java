/*
 * Copyright (c) 2018 Anzen Soluciones S.A. de C.V.
 * Mexico D.F.
 * All rights reserved.
 *
 * THIS SOFTWARE IS  CONFIDENTIAL INFORMATION PROPIETARY OF ANZEN SOLUCIONES.
 * THIS INFORMATION SHOULD NOT BE DISCLOSED AND MAY ONLY BE USED IN ACCORDANCE THE TERMS DETERMINED BY THE COMPANY ITSELF.
 */
package mx.com.anzen.app.abanking.fake.user.service.redis.repository;

import java.io.Serializable;

/**
 * <p>TODO [Add comments of the class]</p>
 * 
 * @author alexander
 * @version abanking-fake-user
 * @since abanking-fake-user
 */
public interface RedisCrudRepository <T, KEY extends Serializable, HASHKEY extends Serializable> extends
	RedisRepository<T, KEY, HASHKEY> {	

}
