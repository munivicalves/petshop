package com.petshop.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
    public static JFrame frameTela;
    public static JPanel painelGeral;
    public static JPanel painelTitulo;
    public static JPanel painelCorpo;

    public static JButton btnSalvar;
    public static JButton btnCancelar;
    public static JLabel labelTitulo;

    public static JLabel labelData;
    public static JLabel labelDescricao;
    public static JLabel labelPetId;

    public static JTextField fieldData;
    public static JTextField fieldDescricao;
    public static JTextField fieldPetId;

    

    public static JPanel painelComponentes;
    public static ConsultaDAO ConsultaDao;



    public TelaAgendamentos() {

        frameTela = new JFrame("Tela de Agendamentos");
        frameTela.setSize(500, 400);
        painelGeral = new JPanel();
        painelGeral.setPreferredSize(new Dimension(500, 400));
        painelTitulo = new JPanel();
        painelTitulo.setPreferredSize(new Dimension(490, 50));
        painelTitulo.setBorder(new LineBorder(Color.gray, 2,true));

        labelTitulo = new JLabel("Agendamento de Consultas");
        labelTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        labelTitulo.setForeground(Color.BLUE);
        painelTitulo.add(labelTitulo);
        painelGeral.add(painelTitulo);

        painelCorpo = new JPanel();
        painelCorpo.setPreferredSize(new Dimension(490, 340));
        painelCorpo.setBorder(new LineBorder(Color.gray, 2,true));


        labelData = new JLabel("Data:");
        fieldData = new JTextField();
        fieldData.setPreferredSize(new Dimension(310, 20 ));

        labelDescricao = new JLabel("Descrição:");
        fieldDescricao = new JTextField();
        fieldDescricao.setPreferredSize(new Dimension(310, 20 ));

        labelPetId = new JLabel("ID do Pet:");
        fieldPetId = new JTextField();
        fieldPetId.setPreferredSize(new Dimension(310, 20 ));

        painelComponentes = new JPanel();
        painelComponentes.setPreferredSize(new Dimension(410, 180));
        painelComponentes.setBorder(new LineBorder(Color.gray, 2,true));

        painelComponentes.add(labelData);
        painelComponentes.add(fieldData);

        painelComponentes.add(labelDescricao);
        painelComponentes.add(fieldDescricao);

        painelComponentes.add(labelPetId);
        painelComponentes.add(fieldPetId);


        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");

        painelCorpo.add(painelComponentes);

        painelCorpo.add(btnSalvar);
        painelCorpo.add(btnCancelar);


        painelGeral.add(painelCorpo);

        frameTela.setContentPane(painelGeral);
        frameTela.setLocationRelativeTo(null);
        frameTela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameTela.setVisible(true);
        frameTela.setResizable(false);

        ConsultaDao = new ConsultaDAO();

        btnSalvar.addActionListener((ActionEvent e) -> {
        try {
            String textoData = fieldData.getText(); // Ex: 22/06/2025 14:00
            String textoPetId = fieldPetId.getText();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime data = LocalDateTime.parse(textoData, formatter);

            int petId = Integer.parseInt(textoPetId);

            Consulta consulta = new Consulta();
            consulta.setData(data);
            consulta.setDescricao(fieldDescricao.getText());
            consulta.setPetId(petId); 

            ConsultaDao.create(consulta);

            limparCamposJTextField(fieldData, fieldDescricao, fieldPetId);
            fieldPetId.setText(""); // Limpa o campo de ID do Pet
            JOptionPane.showMessageDialog(null, "Consulta salva com sucesso!");
            } catch (NumberFormatException | DateTimeParseException ex) {
                if (ex instanceof NumberFormatException) {
                    JOptionPane.showMessageDialog(null, "ID do Pet deve ser um número inteiro.");
                } else {
                    JOptionPane.showMessageDialog(null, "Data inválida! Use o formato: dd/MM/yyyy HH:mm");
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar a consulta.");
            }
        });



        btnCancelar.addActionListener((ActionEvent e) -> {
            limparCamposJTextField(fieldData, fieldDescricao, fieldPetId);
        });

    }

    public static void limparCamposJTextField(JTextField fieldData, JTextField fieldDescricao, JTextField fieldPetId) {
        fieldData.setText("");
        fieldDescricao.setText("");
    }
}

