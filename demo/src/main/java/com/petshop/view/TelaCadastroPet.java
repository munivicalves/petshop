package com.petshop.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;

import com.petshop.dao.PetDAO;
import com.petshop.model.Pet;

public class TelaCadastroPet extends JFrame {
    private JTextField fieldNome;
    private JTextField fieldTipo;
    private JTextField fieldIdade;
    private JTextField fieldDonoId;
    private PetDAO petDao;

    public TelaCadastroPet() {
        setTitle("Tela de Cadastro de Pet");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Só fecha essa janela

        JPanel painelGeral = new JPanel();
        painelGeral.setLayout(new BorderLayout(10, 10));
        painelGeral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelTitulo = new JLabel("Cadastro de Pet", SwingConstants.CENTER);
        labelTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        labelTitulo.setForeground(Color.BLUE);
        painelGeral.add(labelTitulo, BorderLayout.NORTH);

        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBorder(new LineBorder(Color.GRAY, 2, true));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.LINE_END;

        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        painelForm.add(new JLabel("Nome:"), gbc);
        fieldNome = new JTextField(20);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        painelForm.add(fieldNome, gbc);

        // Tipo
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_END;
        painelForm.add(new JLabel("Tipo (Cachorro, gato...):"), gbc);
        fieldTipo = new JTextField(20);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        painelForm.add(fieldTipo, gbc);

        // Idade
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_END;
        painelForm.add(new JLabel("Idade:"), gbc);
        fieldIdade = new JTextField(20);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        painelForm.add(fieldIdade, gbc);

        // Dono ID
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.LINE_END;
        painelForm.add(new JLabel("Id do Dono Pet:"), gbc);
        fieldDonoId = new JTextField(20);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        painelForm.add(fieldDonoId, gbc);

        painelGeral.add(painelForm, BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        painelGeral.add(painelBotoes, BorderLayout.SOUTH);

        add(painelGeral);

        petDao = new PetDAO();

        // Ações dos botões
        btnSalvar.addActionListener((ActionEvent e) -> {
            try {
                Pet pet = new Pet();
                pet.setNome(fieldNome.getText());
                pet.setTipo(fieldTipo.getText());
                pet.setIdade(Integer.parseInt(fieldIdade.getText()));
                pet.setDonoId(Integer.parseInt(fieldDonoId.getText()));

                petDao.create(pet);
                limparCampos();
                JOptionPane.showMessageDialog(this, "Pet cadastrado com sucesso!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Idade e ID do Dono devem ser números inteiros.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar o pet.");
            }
        });

        btnCancelar.addActionListener((ActionEvent e) -> limparCampos());
    }

    private void limparCampos() {
        fieldNome.setText("");
        fieldTipo.setText("");
        fieldIdade.setText("");
        fieldDonoId.setText("");
    }
}
