package com.curd.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curd.demo.Entity.EntityClass;

public interface RepositoryForCurd extends JpaRepository<EntityClass, Integer> {
	

}
