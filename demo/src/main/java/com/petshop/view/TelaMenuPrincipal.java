package com.petshop.view;

import java.awt.Font;
import java.awt.GridLayout;

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

    public TelaMenuPrincipal() {
        setTitle("Menu Principal");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(4, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titulo = new JLabel("PetShop - Menu Principal", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));

        btnAgendarConsulta = new JButton("Agendar Consulta");
        btnCadastroDono = new JButton("Cadastro de Donos");
        btnCadastroPet = new JButton("Cadastro de Pets");

        painel.add(titulo);
        painel.add(btnAgendarConsulta);
        painel.add(btnCadastroDono);
        painel.add(btnCadastroPet);

        add(painel);

        configurarAcoes();
    }

    private void configurarAcoes() {
        btnAgendarConsulta.addActionListener(e -> new TelaAgendamentos().setVisible(true));
        btnCadastroDono.addActionListener(e -> new TelaCadastroDono(null).setVisible(true));
        btnCadastroPet.addActionListener(e -> new TelaCadastroPet().setVisible(true));
    }

    public static void main(String[] args) {
        new TelaMenuPrincipal().setVisible(true);
    }
}
