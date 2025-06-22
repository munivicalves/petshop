package com.petshop.model;

import java.time.LocalDateTime;

public class Consulta {
    private int id;
    private LocalDateTime data;
    private String descricao;
    private int petId;

    public Consulta() {}

    public Consulta(LocalDateTime data, String descricao, int petId) {
        this.data = data;
        this.descricao = descricao;
        this.petId = petId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }
}