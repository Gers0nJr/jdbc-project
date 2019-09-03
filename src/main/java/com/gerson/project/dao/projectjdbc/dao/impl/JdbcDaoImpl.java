package com.gerson.project.dao.projectjdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gerson.project.dao.projectjdbc.config.DbConfig;
import com.gerson.project.dao.projectjdbc.dao.JdbcDao;
import com.gerson.project.dao.projectjdbc.exceptions.DbException;
import com.gerson.project.dao.projectjdbc.exceptions.DbIntegrityException;
import com.gerson.project.dao.projectjdbc.model.Pessoa;

public class JdbcDaoImpl implements JdbcDao<Pessoa> {

	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;

	@Override
	public void remove(Integer id) {

		try {

			conn = DbConfig.getConnection();
			st = conn.prepareStatement("DELETE FROM pessoa WHERE Id = ?");
			st.setInt(1, id);
			int rowsAffected = st.executeUpdate();
			System.out.println("Linhas afetadas: " + rowsAffected);

		} catch (SQLException e) {

			throw new DbIntegrityException(e.getMessage());

		} finally {

			DbConfig.closeStatement(st);
			DbConfig.closeConnection();

		}
	}

	@Override
	public Pessoa findById(Integer id) {
		
		try {

			conn = DbConfig.getConnection();
			st = conn.prepareStatement("SELECT * FROM pessoa WHERE id = ?");

			st.setInt(1, id);
			ResultSet rs = st.executeQuery();

			if(rs.next()) {
				
				Pessoa obj = new Pessoa();

				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));
				
				return obj;

			}
			
			return null;
			
		} catch (SQLException e) {

			throw new RuntimeException(e);

		} finally {

			DbConfig.closeStatement(st);
			DbConfig.closeConnection();

		}
	}

	@Override
	public List<Pessoa> findAll() {

		List<Pessoa> listObj = new ArrayList<>();

		try {

			conn = DbConfig.getConnection();
			st = conn.prepareStatement("SELECT * FROM pessoa");

			rs = st.executeQuery();

			while (rs.next()) {

				Pessoa obj = new Pessoa();

				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));

				listObj.add(obj);
			}
			
			return listObj;
			
		} catch (SQLException e) {

			throw new RuntimeException(e);

		} finally {

			DbConfig.closeStatement(st);
			DbConfig.closeConnection();

		}
	}
	
	@Override
	public void save(Pessoa obj) {

		try {

			conn = DbConfig.getConnection();
			
			conn.commit();
			
			if(obj.getId() == null) {
				st = conn.prepareStatement("INSERT INTO pessoa (nome) values (?)");
				st.setString(1, obj.getNome());
				int rowsAffected = st.executeUpdate();
				System.out.println("Linhas afetadas: " + rowsAffected);
			}
			
			else {
				st = conn.prepareStatement("UPDATE pessoa set nome = ? WHERE id = ?");
				st.setString(1, obj.getNome());
				st.setInt(2, obj.getId());
				int rowsAffected = st.executeUpdate();
				System.out.println("Linhas afetadas: " + rowsAffected);
			}

		} catch (SQLException e) {
			
			try {
				
				conn.rollback();
				
			} catch (SQLException e1) {
				
				throw new DbException("Houve um problema na transação - " + e1.getMessage());
				
			}

			throw new DbIntegrityException(e.getMessage());

		} finally {

			DbConfig.closeStatement(st);
			DbConfig.closeConnection();

		}

	}
}
