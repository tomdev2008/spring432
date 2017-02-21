package com.mvw.mybatis.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvw.mybatis.model.MyBean;


@Service
public class MyService {

	public  List<MyBean> findAll(){
		return null;
	}
	
	@Transactional
	public  void del(){

	}
}
