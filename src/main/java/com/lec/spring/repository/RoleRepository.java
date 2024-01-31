package com.lec.spring.repository;

import com.lec.spring.domain.Role;
import com.lec.spring.domain.RoleName;
import com.lec.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByRoleName(RoleName name);

    List<Role> findByUsers(User user);
}
