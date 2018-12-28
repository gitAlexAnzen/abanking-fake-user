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

import org.springframework.data.repository.CrudRepository;


/**
 * <p>TODO [Add comments of the class]</p>
 *
 * @version abanking-fake-user
 * @since abanking-fake-user
 */
public interface RedisSimpleCrudRepository<T, KEY extends Serializable> extends RedisCrudRepository<T, KEY, String>,
	CrudRepository<T, KEY> {
	

	/**
	 * Save the entity {@link S} given and set the timeout to remove that entity.
	 * @param entity {@link S} must no be null.
	 * @param timeout {@link Long} in seconds.
	 * @return The entity stored in Redis.
	 */
	<S extends T> S save(S entity, Long timeout);

	/**
	 * Set the timeout for given key.
	 * @param key {@link KEY} must no be null.
	 * @param timeout {@link Long} in seconds.
	 * @return {@code true} if the KEY is in Redis and the timeout was set successful.
	 *         Otherwise returns {@code false}.
	 */
	Boolean timeout(KEY key, Long timeout);

	/**
	 * Find a entity set where its ID matches with String pattern.
	 * @param pattern {@link String}
	 * @return A Entity Iterable.
	 */
	Iterable<T> find(String pattern);

	/**
	 * Update the timeout of a entity in the repository.
	 * @param <T> entity
	 * @param timeout {@link Long} in seconds.
	 * @return true if the entity is in the repository, otherwise false.
	 */
	Boolean timeout(T t, Long timeout);

}
