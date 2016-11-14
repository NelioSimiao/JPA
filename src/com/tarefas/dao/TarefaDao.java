package com.tarefas.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tarefas.model.Tarefa;


@Repository
public class TarefaDao {
	private final Connection connection;
	@Autowired
	public TarefaDao(DataSource dataSource) {
		try {
			this.connection = dataSource.getConnection();
			} catch (SQLException e) {
			throw new RuntimeException(e);
			}
			
		
	}

	public void adiciona(Tarefa tarefa) {
		String sql = "insert into tarefa (descricao, finalizado) values (?,?)";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, tarefa.getDescricao());
			stmt.setBoolean(2, tarefa.isFinalizado());
			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Tarefa> lista() {
		try {
			List<Tarefa> tarefas = new ArrayList<Tarefa>();
			PreparedStatement stmt = this.connection.prepareStatement("select * from tarefa");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// adiciona a tarefa na lista
				tarefas.add(populaTarefa(rs));
			}

			rs.close();
			stmt.close();

			return tarefas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Tarefa populaTarefa(ResultSet rs) throws SQLException {
		Tarefa tarefa = new Tarefa();

		// popula o objeto tarefa
		tarefa.setId(rs.getInt("id"));
		tarefa.setDescricao(rs.getString("descricao"));
		tarefa.setFinalizado(rs.getBoolean("finalizado"));

		// popula a data de finalizacao da tarefa, fazendo a conversao
		Date data = rs.getDate("dataFinalizacao");
		if (data != null) {
			LocalDate dataFinalizacao = LocalDate.now();
			dataFinalizacao = data.toLocalDate();
			tarefa.setDataFinalizacao(dataFinalizacao);
		}

		return tarefa;
	}

	public void remove(Tarefa tarefa) {

		// if (tarefa.getId() == null) {
		// throw new IllegalStateException("Id da tarefa não deve ser nula.");
		// }

		String sql = "delete from tarefa where id = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setLong(1, tarefa.getId());
			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Tarefa buscaPorId(Long id) {

		if (id == null) {
			throw new IllegalStateException("Id da tarefa não deve ser nula.");
		}

		try {
			PreparedStatement stmt = this.connection.prepareStatement("select * from tarefa where id = ?");
			stmt.setLong(1, id);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return populaTarefa(rs);
			}

			rs.close();
			stmt.close();

			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void altera(Tarefa tarefa) {
		String sql = "update tarefa set descricao = ?, finalizado = ?, dataFinalizacao = ? where id = ?";
	
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, tarefa.getDescricao());
			stmt.setBoolean(2, tarefa.isFinalizado());
			//stmt.setDate(3,  java.sql.Date.valueOf(tarefa.getDataFinalizacao()));
			stmt.setDate(3, tarefa.getDataFinalizacao() != null ?  java.sql.Date.valueOf(tarefa.getDataFinalizacao()): null);
			stmt.setLong(4, tarefa.getId());
			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
