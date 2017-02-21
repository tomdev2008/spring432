package com.mvw.cpm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvw.cpm.dao.JpaDao;
import com.mvw.cpm.model.JpaBean;

@Service
public class JpaService {

	@Autowired
	private JpaDao jpaDao;

	@Transactional
	public void save() {
		JpaBean jb = new JpaBean();

		jb.setName("name");
		jb.setContent("1");
		jb.setScore(1);

		jpaDao.save(jb);

	}

}
