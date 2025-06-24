package com.petshop.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
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
        setSize(550, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

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
        painelForm.add(new JLabel("Tipo (Cachorro, Gato...):"), gbc);
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
        painelForm.add(new JLabel("ID do Dono:"), gbc);
        fieldDonoId = new JTextField(20);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        painelForm.add(fieldDonoId, gbc);

        painelGeral.add(painelForm, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        JButton btnListar = new JButton("Listar Pets");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnListar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);

        painelGeral.add(painelBotoes, BorderLayout.SOUTH);

        add(painelGeral);

        petDao = new PetDAO();

        // Ação Salvar
        btnSalvar.addActionListener((ActionEvent e) -> {
            try {
                if (camposValidos()) {
                    Pet pet = new Pet();
                    pet.setNome(fieldNome.getText().trim());
                    pet.setTipo(fieldTipo.getText().trim());
                    pet.setIdade(Integer.parseInt(fieldIdade.getText().trim()));
                    pet.setDonoId(Integer.parseInt(fieldDonoId.getText().trim()));

                    petDao.create(pet);
                    limparCampos();
                    JOptionPane.showMessageDialog(this, "Pet cadastrado com sucesso!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Idade e ID do Dono devem ser números inteiros.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar o pet: " + ex.getMessage());
            }
        });

        // Ação Cancelar
        btnCancelar.addActionListener((ActionEvent e) -> limparCampos());

        // Ação Listar
        btnListar.addActionListener(e -> {
            List<Pet> pets = petDao.read();
            if (pets.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum pet cadastrado.");
                return;
            }

            StringBuilder sb = new StringBuilder("Pets cadastrados:\n");
            for (Pet p : pets) {
                sb.append("ID: ").append(p.getId())
                  .append(" | Nome: ").append(p.getNome())
                  .append(" | Tipo: ").append(p.getTipo())
                  .append(" | Idade: ").append(p.getIdade())
                  .append(" | Dono ID: ").append(p.getDonoId())
                  .append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        // Ação Atualizar
        btnAtualizar.addActionListener((var e) -> {
            try {
                String inputId = JOptionPane.showInputDialog(this, "Digite o ID do pet a atualizar:");
                if (inputId == null || inputId.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "ID não pode ser vazio.");
                    return;
                }

                int id = Integer.parseInt(inputId.trim());

                Pet petExistente = petDao.findById(id);
                if (petExistente == null) {
                    JOptionPane.showMessageDialog(this, "Pet com ID " + id + " não encontrado.");
                    return;
                }

                if (camposValidos()) {
                    Pet petAtualizado = new Pet();
                    petAtualizado.setId(id);
                    petAtualizado.setNome(fieldNome.getText().trim());
                    petAtualizado.setTipo(fieldTipo.getText().trim());
                    petAtualizado.setIdade(Integer.parseInt(fieldIdade.getText().trim()));
                    petAtualizado.setDonoId(Integer.parseInt(fieldDonoId.getText().trim()));

                    petDao.update(petAtualizado);
                    JOptionPane.showMessageDialog(this, "Pet atualizado com sucesso!");
                    limparCampos();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID, Idade e ID do Dono devem ser números inteiros.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar o pet: " + ex.getMessage());
            }
        });

        // Ação Excluir
        btnExcluir.addActionListener(e -> {
            try {
                String inputId = JOptionPane.showInputDialog(this, "Digite o ID do pet a excluir:");
                if (inputId == null || inputId.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "ID não pode ser vazio.");
                    return;
                }

                int id = Integer.parseInt(inputId.trim());

                Pet petExistente = petDao.findById(id);
                if (petExistente == null) {
                    JOptionPane.showMessageDialog(this, "Pet com ID " + id + " não encontrado.");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Tem certeza que deseja excluir o pet com ID " + id + "?",
                        "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    petDao.delete(id);
                    JOptionPane.showMessageDialog(this, "Pet excluído com sucesso!");
                    limparCampos();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID deve ser um número inteiro.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir o pet: " + ex.getMessage());
            }
        });
    }

    private boolean camposValidos() {
        if (fieldNome.getText().trim().isEmpty() ||
            fieldTipo.getText().trim().isEmpty() ||
            fieldIdade.getText().trim().isEmpty() ||
            fieldDonoId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
            return false;
        }
        return true;
    }

    private void limparCampos() {
        fieldNome.setText("");
        fieldTipo.setText("");
        fieldIdade.setText("");
        fieldDonoId.setText("");
    }
}
