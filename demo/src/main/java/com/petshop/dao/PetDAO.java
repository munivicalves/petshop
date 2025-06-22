package com.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.petshop.model.Pet;

public class PetDAO {
    public void create(Pet pet) {
        String sql = "INSERT INTO Pet (nome, tipo, idade, dono_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, pet.getNome());
            stmt.setString(2, pet.getTipo());
            stmt.setInt(3, pet.getIdade());
            stmt.setInt(4, pet.getDonoId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                pet.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar Pet", e);
        }
    }
}
