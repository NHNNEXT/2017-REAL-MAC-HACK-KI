package com.amigotrip.repository;

import com.amigotrip.domain.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Naver on 2017. 11. 15..
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(String role);
}