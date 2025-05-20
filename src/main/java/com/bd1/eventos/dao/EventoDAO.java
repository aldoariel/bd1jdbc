package com.bd1.eventos.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bd1.eventos.model.Evento;

public class EventoDAO {

    private final Connection connection;

    public EventoDAO(Connection connection) {
        this.connection = connection;
    }

    public Integer salvar(Evento evento) {
        String sql = "insert into evento (id, nombre, fecha) values (null, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, evento.getNombre());
            preparedStatement.setDate(2, new Date(evento.getFecha().getTime()));

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();

                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizar(Evento evento) {
        String sql = "update evento set nombre = ?, fecha = ? where id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, evento.getNombre());
            preparedStatement.setDate(2, new Date(evento.getFecha().getTime()));
            preparedStatement.setInt(3, evento.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Evento buscar(Integer id) {
        String sql = "select * from evento where id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(!resultSet.next()) {
                    return null;
                }

                return new Evento(id, resultSet.getString("nombre"),
                        resultSet.getDate("fecha"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Evento> listar() {
        String sql = "select * from evento";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Evento> eventos = new ArrayList<Evento>();

                while(resultSet.next()) {
                    eventos.add(new Evento(resultSet.getInt("id"),
                            resultSet.getString("nombre"), resultSet.getDate("fecha")));
                }

                return eventos;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(Integer id) {
        String sql = "delete from evento where id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
