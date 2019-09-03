package com.gerson.project.dao.projectjdbc.dao;

import java.util.List;

import com.gerson.project.dao.projectjdbc.model.Pessoa;

public interface JdbcDao<T>{
	
	public void remove(Integer id);
	public Pessoa findById(Integer id);
	public List<T> findAll();
	public void save(T obj);

}
