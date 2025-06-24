package com.petshop.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.petshop.model.Consulta;

public class ConsultaDAO {

    // CREATE
    public void create(Consulta consulta) {
        String sql = "INSERT INTO Consulta (data, descricao, pet_id) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(consulta.getData()));
            stmt.setString(2, consulta.getDescricao());
            stmt.setInt(3, consulta.getPetId());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                consulta.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar Consulta", e);
        }
    }

    // READ (listar todas as consultas)
    public List<Consulta> read() {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM Consulta";
        try (Connection conn = ConexaoJDBC.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Consulta consulta = new Consulta(
                    rs.getInt("id"),
                    rs.getTimestamp("data").toLocalDateTime(),
                    rs.getString("descricao"),
                    rs.getInt("pet_id")
                );
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Consultas", e);
        }
        return consultas;
    }

    // UPDATE
    public void update(Consulta consulta) {
        String sql = "UPDATE Consulta SET data = ?, descricao = ?, pet_id = ? WHERE id = ?";
        try (Connection conn = ConexaoJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(consulta.getData()));
            stmt.setString(2, consulta.getDescricao());
            stmt.setInt(3, consulta.getPetId());
            stmt.setInt(4, consulta.getId());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Nenhuma consulta encontrada com o ID: " + consulta.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar Consulta", e);
        }
    }

    // DELETE
    public void delete(int id) {
        String sql = "DELETE FROM Consulta WHERE id = ?";
        try (Connection conn = ConexaoJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Nenhuma consulta encontrada com o ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar Consulta", e);
        }
    }

    // FIND BY ID
    public Consulta findById(int id) {
        String sql = "SELECT * FROM Consulta WHERE id = ?";
        try (Connection conn = ConexaoJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Consulta(
                    rs.getInt("id"),
                    rs.getTimestamp("data").toLocalDateTime(),
                    rs.getString("descricao"),
                    rs.getInt("pet_id")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Consulta por ID", e);
        }
        return null; 
    }
}
