package com.petshop.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.petshop.dao.DonoDAO;
import com.petshop.model.Dono;

public class TelaCadastroDono extends JFrame {
    private JFrame frameTela;
    private JPanel painelGeral;
    private JPanel painelTitulo;
    private JPanel painelCorpo;

    private JButton btnSalvar;
    private JButton btnCancelar;
    private JButton btnListar;
    private JButton btnAtualizar;
    private JButton btnExcluir;

    private JLabel labelTitulo;

    private JLabel labelNome;
    private JLabel labelTelefone;

    private JTextField fieldNome;
    private JTextField fieldTelefone;
    private JTextField fieldId; // campo para ID para atualizar/excluir

    private JPanel painelComponentes;

    private DonoDAO donoDao;

    public TelaCadastroDono() {
        frameTela = new JFrame("Tela de Cadastro de Dono");
        frameTela.setSize(520, 420);

        painelGeral = new JPanel();
        painelGeral.setPreferredSize(new Dimension(520, 420));

        painelTitulo = new JPanel();
        painelTitulo.setPreferredSize(new Dimension(510, 50));
        painelTitulo.setBorder(new LineBorder(Color.gray, 2, true));

        labelTitulo = new JLabel("Cadastro de Dono PET");
        labelTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        labelTitulo.setForeground(Color.BLUE);
        painelTitulo.add(labelTitulo);
        painelGeral.add(painelTitulo);

        painelCorpo = new JPanel();
        painelCorpo.setPreferredSize(new Dimension(510, 360));
        painelCorpo.setBorder(new LineBorder(Color.gray, 2, true));

        labelNome = new JLabel("Nome:");
        fieldNome = new JTextField();
        fieldNome.setPreferredSize(new Dimension(310, 25));

        labelTelefone = new JLabel("Telefone:");
        fieldTelefone = new JTextField();
        fieldTelefone.setPreferredSize(new Dimension(310, 25));

        // Campo ID para atualizar e excluir
        JLabel labelId = new JLabel("ID (para atualizar/excluir):");
        fieldId = new JTextField();
        fieldId.setPreferredSize(new Dimension(310, 25));

        painelComponentes = new JPanel();
        painelComponentes.setPreferredSize(new Dimension(480, 200));
        painelComponentes.setBorder(new LineBorder(Color.gray, 2, true));

        painelComponentes.add(labelId);
        painelComponentes.add(fieldId);

        painelComponentes.add(labelNome);
        painelComponentes.add(fieldNome);

        painelComponentes.add(labelTelefone);
        painelComponentes.add(fieldTelefone);

        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        btnListar = new JButton("Listar Donos");
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");

        painelCorpo.add(painelComponentes);

        painelCorpo.add(btnSalvar);
        painelCorpo.add(btnCancelar);
        painelCorpo.add(btnListar);
        painelCorpo.add(btnAtualizar);
        painelCorpo.add(btnExcluir);

        painelGeral.add(painelCorpo);

        frameTela.setContentPane(painelGeral);
        frameTela.setLocationRelativeTo(null);
        frameTela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameTela.setVisible(true);
        frameTela.setResizable(false);

        donoDao = new DonoDAO();

        // Ações dos botões
        btnSalvar.addActionListener((ActionEvent e) -> {
            try {
                Dono dono = new Dono();
                dono.setNome(fieldNome.getText());
                dono.setTelefone(fieldTelefone.getText());

                donoDao.create(dono);
                JOptionPane.showMessageDialog(frameTela, "Dono cadastrado com sucesso!");
                limparCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frameTela, "Erro ao cadastrar dono: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> limparCampos());

        btnListar.addActionListener(e -> {
            List<Dono> donos = donoDao.read();
            if (donos.isEmpty()) {
                JOptionPane.showMessageDialog(frameTela, "Nenhum dono cadastrado.");
                return;
            }
            StringBuilder sb = new StringBuilder("Donos cadastrados:\n");
            for (Dono d : donos) {
                sb.append("ID: ").append(d.getId())
                  .append(" | Nome: ").append(d.getNome())
                  .append(" | Telefone: ").append(d.getTelefone())
                  .append("\n");
            }
            JOptionPane.showMessageDialog(frameTela, sb.toString());
        });

        btnAtualizar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(fieldId.getText());
                Dono dono = new Dono();
                dono.setId(id);
                dono.setNome(fieldNome.getText());
                dono.setTelefone(fieldTelefone.getText());

                donoDao.update(dono);
                JOptionPane.showMessageDialog(frameTela, "Dono atualizado com sucesso!");
                limparCampos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameTela, "ID inválido! Use um número inteiro.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frameTela, "Erro ao atualizar dono: " + ex.getMessage());
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(fieldId.getText());
                int confirm = JOptionPane.showConfirmDialog(frameTela, "Tem certeza que deseja excluir o dono com ID " + id + "?",
                        "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    donoDao.delete(id);
                    JOptionPane.showMessageDialog(frameTela, "Dono excluído com sucesso!");
                    limparCampos();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameTela, "ID inválido! Use um número inteiro.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frameTela, "Erro ao excluir dono: " + ex.getMessage());
            }
        });
    }

    private void limparCampos() {
        fieldId.setText("");
        fieldNome.setText("");
        fieldTelefone.setText("");
    }
}
