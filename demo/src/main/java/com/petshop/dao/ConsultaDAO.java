package com.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.petshop.model.Consulta;

public class ConsultaDAO {
    @SuppressWarnings("CallToPrintStackTrace")
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
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ConsultaDAO []";
    }
}