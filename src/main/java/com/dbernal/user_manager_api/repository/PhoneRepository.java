/*
 * Copyright (c) 2024 Recurso de ejemplo creado por David Bernal.
 */

package com.dbernal.user_manager_api.repository;

import com.dbernal.user_manager_api.model.PhoneEntity;
import com.dbernal.user_manager_api.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends CrudRepository<PhoneEntity, Long> {

}
