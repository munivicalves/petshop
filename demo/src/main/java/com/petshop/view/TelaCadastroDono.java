package com.petshop.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.petshop.dao.DonoDAO;
import com.petshop.model.Dono;


public class TelaCadastroDono extends JFrame {
    public static JFrame frameTela;
    public static JPanel painelGeral;
    public static JPanel painelTitulo;
    public static JPanel painelCorpo;

    public static JButton btnSalvar;
    public static JButton btnCancelar;
    public static JLabel labelTitulo;

    public static JLabel labelNome;
    public static JLabel labelTelefone;

    public static JTextField fieldNome;
    public static JTextField fieldTelefone;

    public static JPanel painelComponentes;
    public static DonoDAO DonoDao;



    public TelaCadastroDono(String[] args) {

        frameTela = new JFrame("Tela de Cadastro de Dono");
        frameTela.setSize(500, 400);
        painelGeral = new JPanel();
        painelGeral.setPreferredSize(new Dimension(500, 400));
        painelTitulo = new JPanel();
        painelTitulo.setPreferredSize(new Dimension(490, 50));
        painelTitulo.setBorder(new LineBorder(Color.gray, 2,true));

        labelTitulo = new JLabel("Cadastro de Dono PET");
        labelTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        labelTitulo.setForeground(Color.BLUE);
        painelTitulo.add(labelTitulo);
        painelGeral.add(painelTitulo);

        painelCorpo = new JPanel();
        painelCorpo.setPreferredSize(new Dimension(490, 340));
        painelCorpo.setBorder(new LineBorder(Color.gray, 2,true));


        labelNome = new JLabel("Nome:");
        fieldNome = new JTextField();
        fieldNome.setPreferredSize(new Dimension(310, 20 ));

        labelTelefone = new JLabel("Telefone:");
        fieldTelefone = new JTextField();
        fieldTelefone.setPreferredSize(new Dimension(310, 20 ));


        painelComponentes = new JPanel();
        painelComponentes.setPreferredSize(new Dimension(410, 180));
        painelComponentes.setBorder(new LineBorder(Color.gray, 2,true));

        painelComponentes.add(labelNome);
        painelComponentes.add(fieldNome);

        painelComponentes.add(labelTelefone);
        painelComponentes.add(fieldTelefone);



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

        DonoDao = new DonoDAO();
        btnSalvar.addActionListener((ActionEvent e) -> {
            Dono dono = new Dono();
            dono.setNome(fieldNome.getText());
            dono.setTelefone(fieldTelefone.getText());
            
            DonoDao.create(dono);
            limparCamposJTextField(fieldNome, fieldTelefone);
        });


        btnCancelar.addActionListener((ActionEvent e) -> {
            limparCamposJTextField(fieldNome, fieldTelefone);
        });

    }

    public static void limparCamposJTextField(JTextField fieldNome, JTextField fieldTelefone) {
        fieldNome.setText("");
        fieldTelefone.setText("");
    }
}


