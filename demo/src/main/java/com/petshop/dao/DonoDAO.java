package com.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.petshop.model.Dono;

public class DonoDAO {

    // CREATE
    public void create(Dono dono) {
        String sql = "INSERT INTO Dono (nome, telefone) VALUES (?, ?)";
        try (Connection conn = ConexaoJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, dono.getNome());
            stmt.setString(2, dono.getTelefone());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                dono.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar Dono", e);
        }
    }

    // READ (listar todos os donos)
    public List<Dono> read() {
        List<Dono> donos = new ArrayList<>();
        String sql = "SELECT * FROM Dono";
        try (Connection conn = ConexaoJDBC.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Dono dono = new Dono();
                dono.setId(rs.getInt("id"));
                dono.setNome(rs.getString("nome"));
                dono.setTelefone(rs.getString("telefone"));
                donos.add(dono);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Donos", e);
        }
        return donos;
    }

    // UPDATE
    public void update(Dono dono) {
        String sql = "UPDATE Dono SET nome = ?, telefone = ? WHERE id = ?";
        try (Connection conn = ConexaoJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dono.getNome());
            stmt.setString(2, dono.getTelefone());
            stmt.setInt(3, dono.getId());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Nenhum dono encontrado com o ID: " + dono.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar Dono", e);
        }
    }

    // DELETE
    public void delete(int id) {
        String sql = "DELETE FROM Dono WHERE id = ?";
        try (Connection conn = ConexaoJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Nenhum dono encontrado com o ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar Dono", e);
        }
    }

    // FIND BY ID
    public Dono findById(int id) {
        String sql = "SELECT * FROM Dono WHERE id = ?";
        try (Connection conn = ConexaoJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Dono dono = new Dono();
                dono.setId(rs.getInt("id"));
                dono.setNome(rs.getString("nome"));
                dono.setTelefone(rs.getString("telefone"));
                return dono;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Dono por ID", e);
        }
        return null; 
    }
}
