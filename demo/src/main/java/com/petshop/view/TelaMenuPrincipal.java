package com.petshop.view;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TelaMenuPrincipal extends JFrame {

    private final JButton btnAgendarConsulta;
    private final JButton btnCadastroDono;
    private final JButton btnCadastroPet;
    private final JButton btnSair;

    public TelaMenuPrincipal() {
        setTitle("Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(5, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titulo = new JLabel("🐾 PetShop - Menu Principal", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));

        btnAgendarConsulta = new JButton("Agendar Consulta");
        btnCadastroDono = new JButton("Cadastro de Donos");
        btnCadastroPet = new JButton("Cadastro de Pets");
        btnSair = new JButton("Sair");

        painel.add(titulo);
        painel.add(btnAgendarConsulta);
        painel.add(btnCadastroDono);
        painel.add(btnCadastroPet);
        painel.add(btnSair);

        add(painel);
        configurarAcoes();
    }

    private void configurarAcoes() {
        btnAgendarConsulta.addActionListener((ActionEvent e) -> {
            dispose();
            new TelaAgendamentos();
        });

        btnCadastroDono.addActionListener((ActionEvent e) -> {
            dispose();
            new TelaCadastroDono().setVisible(true);
        });

        btnCadastroPet.addActionListener((ActionEvent e) -> {
            dispose();
            new TelaCadastroPet().setVisible(true);
        });

        btnSair.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        new TelaMenuPrincipal().setVisible(true);
    }
}
