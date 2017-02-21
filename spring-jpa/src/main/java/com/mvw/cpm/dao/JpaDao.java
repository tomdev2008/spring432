package com.mvw.cpm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mvw.cpm.model.JpaBean;

@Repository
public interface JpaDao extends JpaRepository<JpaBean,Long>,JpaSpecificationExecutor<JpaBean>{
	
}
