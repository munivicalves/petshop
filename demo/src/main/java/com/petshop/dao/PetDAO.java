package com.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.petshop.model.Pet;

public class PetDAO {

    // CREATE
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

    // READ (listar todos os pets)
    public List<Pet> read() {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM Pet";
        try (Connection conn = ConexaoJDBC.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setNome(rs.getString("nome"));
                pet.setTipo(rs.getString("tipo"));
                pet.setIdade(rs.getInt("idade"));
                pet.setDonoId(rs.getInt("dono_id"));
                pets.add(pet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Pets", e);
        }
        return pets;
    }

    // UPDATE
    public void update(Pet pet) {
        String sql = "UPDATE Pet SET nome = ?, tipo = ?, idade = ?, dono_id = ? WHERE id = ?";
        try (Connection conn = ConexaoJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pet.getNome());
            stmt.setString(2, pet.getTipo());
            stmt.setInt(3, pet.getIdade());
            stmt.setInt(4, pet.getDonoId());
            stmt.setInt(5, pet.getId());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Nenhum pet encontrado com o ID: " + pet.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar Pet", e);
        }
    }

    // DELETE
    public void delete(int id) {
        String sql = "DELETE FROM Pet WHERE id = ?";
        try (Connection conn = ConexaoJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Nenhum pet encontrado com o ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar Pet", e);
        }
    }

    // FIND BY ID
    public Pet findById(int id) {
        String sql = "SELECT * FROM Pet WHERE id = ?";
        try (Connection conn = ConexaoJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setNome(rs.getString("nome"));
                pet.setTipo(rs.getString("tipo"));
                pet.setIdade(rs.getInt("idade"));
                pet.setDonoId(rs.getInt("dono_id"));
                return pet;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Pet por ID", e);
        }
        return null; 
    }
}
