package com.petshop.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.petshop.dao.ConsultaDAO;
import com.petshop.model.Consulta;

public class TelaAgendamentos extends JFrame {
    private static JFrame frameTela;
    private static JPanel painelGeral;
    private static JPanel painelTitulo;
    private static JPanel painelCorpo;

    private static JLabel labelTitulo;
    private static JLabel labelData;
    private static JLabel labelDescricao;
    private static JLabel labelPetId;

    private static JTextField fieldData;
    private static JTextField fieldDescricao;
    private static JTextField fieldPetId;

    private static JButton btnSalvar;
    private static JButton btnCancelar;
    private static JButton btnListar;
    private static JButton btnAtualizar;
    private static JButton btnExcluir;
    private static JButton btnMenu;

    private static JPanel painelComponentes;
    private static ConsultaDAO ConsultaDao;

    public TelaAgendamentos() {
        frameTela = new JFrame("Tela de Agendamentos");
        frameTela.setSize(500, 500);
        painelGeral = new JPanel();
        painelGeral.setPreferredSize(new Dimension(500, 500));

        painelTitulo = new JPanel();
        painelTitulo.setPreferredSize(new Dimension(490, 50));
        painelTitulo.setBorder(new LineBorder(Color.gray, 2, true));
        labelTitulo = new JLabel("Agendamento de Consultas");
        labelTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        labelTitulo.setForeground(Color.BLUE);
        painelTitulo.add(labelTitulo);
        painelGeral.add(painelTitulo);

        painelCorpo = new JPanel();
        painelCorpo.setPreferredSize(new Dimension(490, 440));
        painelCorpo.setBorder(new LineBorder(Color.gray, 2, true));

        labelData = new JLabel("Data:");
        fieldData = new JTextField();
        fieldData.setPreferredSize(new Dimension(310, 20));

        labelDescricao = new JLabel("Descrição:");
        fieldDescricao = new JTextField();
        fieldDescricao.setPreferredSize(new Dimension(310, 20));

        labelPetId = new JLabel("ID do Pet:");
        fieldPetId = new JTextField();
        fieldPetId.setPreferredSize(new Dimension(310, 20));

        painelComponentes = new JPanel();
        painelComponentes.setPreferredSize(new Dimension(410, 180));
        painelComponentes.setBorder(new LineBorder(Color.gray, 2, true));

        painelComponentes.add(labelData);
        painelComponentes.add(fieldData);
        painelComponentes.add(labelDescricao);
        painelComponentes.add(fieldDescricao);
        painelComponentes.add(labelPetId);
        painelComponentes.add(fieldPetId);

        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        btnListar = new JButton("Listar Consultas");
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");
        btnMenu = new JButton("Menu Principal");

        painelCorpo.add(painelComponentes);
        painelCorpo.add(btnSalvar);
        painelCorpo.add(btnCancelar);
        painelCorpo.add(btnListar);
        painelCorpo.add(btnAtualizar);
        painelCorpo.add(btnExcluir);
        painelCorpo.add(btnMenu);

        painelGeral.add(painelCorpo);
        frameTela.setContentPane(painelGeral);
        frameTela.setLocationRelativeTo(null);
        frameTela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameTela.setVisible(true);
        frameTela.setResizable(false);

        ConsultaDao = new ConsultaDAO();

        // Ação Salvar
        btnSalvar.addActionListener((ActionEvent e) -> {
            try {
                String textoData = fieldData.getText().trim(); // Ex: 22/06/2025 14:00
                String textoPetId = fieldPetId.getText().trim();

                if (textoData.isEmpty() || textoPetId.isEmpty() || fieldDescricao.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
                    return;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime data = LocalDateTime.parse(textoData, formatter);

                int petId = Integer.parseInt(textoPetId);

                Consulta consulta = new Consulta();
                consulta.setData(data);
                consulta.setDescricao(fieldDescricao.getText().trim());
                consulta.setPetId(petId);

                ConsultaDao.create(consulta);

                limparCampos();
                JOptionPane.showMessageDialog(null, "Consulta salva com sucesso!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID do Pet deve ser um número inteiro.");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(null, "Data inválida! Use o formato: dd/MM/yyyy HH:mm");
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar a consulta: " + ex.getMessage());
            }
        });

        // Ação Cancelar
        btnCancelar.addActionListener(e -> limparCampos());

        // Ação Listar
        btnListar.addActionListener(e -> {
            List<Consulta> consultas = ConsultaDao.read();
            if (consultas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhuma consulta cadastrada.");
                return;
            }

            StringBuilder sb = new StringBuilder("Consultas:\n");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            for (Consulta c : consultas) {
                sb.append("ID: ").append(c.getId())
                        .append(" | Data: ").append(c.getData().format(formatter))
                        .append(" | PetID: ").append(c.getPetId())
                        .append(" | Descrição: ").append(c.getDescricao())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        });

        // Ação Atualizar
        btnAtualizar.addActionListener(e -> {
            try {
                String inputId = JOptionPane.showInputDialog("Digite o ID da consulta a atualizar:");
                if (inputId == null || inputId.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "ID não pode ser vazio.");
                    return;
                }

                int id = Integer.parseInt(inputId.trim());

                Consulta consultaExistente = ConsultaDao.findById(id);
                if (consultaExistente == null) {
                    JOptionPane.showMessageDialog(null, "Consulta com ID " + id + " não encontrada.");
                    return;
                }

                String textoData = fieldData.getText().trim();
                String descricao = fieldDescricao.getText().trim();
                String textoPetId = fieldPetId.getText().trim();

                if (textoData.isEmpty() || descricao.isEmpty() || textoPetId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos para atualizar.");
                    return;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime data = LocalDateTime.parse(textoData, formatter);

                int petId = Integer.parseInt(textoPetId);

                Consulta consultaAtualizada = new Consulta(id, data, descricao, petId);

                ConsultaDao.update(consultaAtualizada);

                JOptionPane.showMessageDialog(null, "Consulta atualizada com sucesso!");
                limparCampos();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID e Pet ID devem ser números inteiros.");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(null, "Data inválida! Use o formato: dd/MM/yyyy HH:mm");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar a consulta: " + ex.getMessage());
            }
        });

        // Ação Excluir
        btnExcluir.addActionListener(e -> {
            try {
                String inputId = JOptionPane.showInputDialog("Digite o ID da consulta a excluir:");
                if (inputId == null || inputId.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "ID não pode ser vazio.");
                    return;
                }
                int id = Integer.parseInt(inputId.trim());

                ConsultaDao.delete(id);

                JOptionPane.showMessageDialog(null, "Consulta excluída com sucesso!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID deve ser um número inteiro.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir a consulta: " + ex.getMessage());
            }
        });

        // Ação Voltar ao Menu
        btnMenu.addActionListener(e -> {
            frameTela.dispose();
            new TelaMenuPrincipal().setVisible(true);
        });
    }

    private static void limparCampos() {
        fieldData.setText("");
        fieldDescricao.setText("");
        fieldPetId.setText("");
    }
}
