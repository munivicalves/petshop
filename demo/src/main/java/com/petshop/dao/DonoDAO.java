package com.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.petshop.model.Dono;

public class DonoDAO {
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
}

