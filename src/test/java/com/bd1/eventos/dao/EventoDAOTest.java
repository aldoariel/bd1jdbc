package com.bd1.eventos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.bd1.eventos.model.Evento;

public class EventoDAOTest {

	private static Connection connection;
//antes obtenemos conexion 
	@BeforeAll
	public static void iniciarClasse() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registroevento" +
				"?useTimezone=true&serverTimezone=UTC", "root", "root");
	}
//cerramos conexion al salir
	@AfterAll
	public static void cerrarClasse() throws SQLException {
		connection.close();
	}

	@Test
	public void crud() {
		Evento evento = new Evento(null, "PC01", new Date());

		// obtenemos  unaa instancia del DAO.
		EventoDAO dao = new EventoDAO(connection);

		// insertamo y  se recupera el identificador.
		Integer id = dao.salvar(evento);
		Assertions.assertNotNull(id, "Identificador  retornado como NULL.");

		// El identificador recuperado sera nuestro id".
		evento.setId(id);

		// Verificando si el registro realmente esta persistido en la  base de dados.
		evento = dao.buscar(evento.getId());
		String nombreAlterado = evento.getNombre() + " alterado";
		Assertions.assertNotNull(evento, "Evento nulo.");

		// Atualizando o registro no banco de dados.
		System.out.println(" Nombre: guardado: " + evento.getNombre() + "  id : " + evento.getId()); 
		evento.setNombre(nombreAlterado);
		dao.actualizar(evento);

		// Verificando se atualização ocorreu com sucesso.
		evento = dao.buscar(evento.getId());
		Assertions.assertEquals(nombreAlterado, evento.getNombre(), "El nombre no fue  actualizado corretamente.");
		System.out.println(" Nombre: actualizado: " + evento.getNombre() + "  id : " + evento.getId()); 

		// borrando el  registro.
		System.out.println(" Se esta borrando..");
		dao.deletar(evento.getId());

		// Aqui el registro ya debe estar borrado
		evento = dao.buscar(evento.getId());
		Assertions.assertNull(evento, "Evento aun  existe y ya deberia esta borrado.");
	}
}
