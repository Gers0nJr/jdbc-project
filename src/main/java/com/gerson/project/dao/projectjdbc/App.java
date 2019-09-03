package com.gerson.project.dao.projectjdbc;

import java.sql.SQLException;

import com.gerson.project.dao.projectjdbc.dao.JdbcDao;
import com.gerson.project.dao.projectjdbc.dao.impl.JdbcDaoImpl;
import com.gerson.project.dao.projectjdbc.model.Pessoa;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException
    {
    	JdbcDao dao = new JdbcDaoImpl();
    	
    	//Pessoa p1 = new Pessoa(9, "Junior a");
    	//dao.save(p1);
    	
    	Pessoa p = dao.findById(6);
    	System.out.println(p);
    	
        //dao.remove(1);
    	
    	/*for(Pessoa pessoa : dao.findAll()) {
    		System.out.println("Id: " + pessoa.getId() + " - Nome: " + pessoa.getNome());
    	}	*/
    }
}
