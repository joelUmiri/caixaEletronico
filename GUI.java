package caixaeletronico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame implements ActionListener {

    //JButton, botoes de cada função.
    private JButton btnEfetuarSaque;
    private JButton btnRelatorioCedulas;
    private JButton btnValorTotal;
    private JButton btnReposicaoCedulas;
    private JButton btnCotaMinima;
    private JButton btnSair;

    private int mouseX, mouseY;

    //Nome do sistema.
    public GUI() {
        
        //setSize: para definir manualmente a largura e a altura de um componente de interface gráfica
        setSize(310, 380);
        setUndecorated(true); // remove barra padrão
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Closed(Fecha)
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel(null); //Cria um painel
        painel.setBackground(new Color(212, 212, 212));
        add(painel);

        // Barra azul
        JPanel barra = new JPanel(null);
        barra.setBounds(0, 0, 320, 30);
        barra.setBackground(new Color(10, 36, 106)); // azul escuro estilo XP
        painel.add(barra);

        //Titulo
        JLabel titulo = new JLabel("Caixa eletronico");
        titulo.setForeground(Color.WHITE);//Colo branco
        titulo.setFont(new Font("Arial", Font.BOLD, 13));
        titulo.setBounds(10, 5, 200, 20);
        barra.add(titulo);

        //Botão para fechar
        JButton btnFechar = new JButton("X");
        btnFechar.setBounds(285, 5, 25, 20);
        btnFechar.setMargin(new Insets(0,0,0,0));
        barra.add(btnFechar);

        btnFechar.addActionListener(e -> System.exit(0));

        // mover janela
        barra.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        //serve para detectar e processar movimentos do mouse (mover ou arrastar) sobre um componente específico, neste caso, chamado
        barra.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen() - mouseX, e.getYOnScreen() - mouseY);
            }
        });

        JLabel lblModuloCliente = new JLabel("Modulo do Cliente:");
        lblModuloCliente.setFont(new Font("Arial", Font.BOLD, 16));//Fonte para o texto(Arial)
        lblModuloCliente.setBounds(20, 40, 200, 25);
        painel.add(lblModuloCliente); // add: serve para inserir componentes gráficos dentro desse painel

        btnEfetuarSaque = new JButton("Efetuar Saque");
        btnEfetuarSaque.setBounds(20, 70, 265, 30);
        painel.add(btnEfetuarSaque);

        //JLabel: Ele atua como um "rótulo", servindo para identificar outros componentes, exibir títulos ou mostrar resultados de processos na tela.
        JLabel lblModuloAdministrador = new JLabel("Modulo do Administrador:");
        lblModuloAdministrador.setFont(new Font("Arial", Font.BOLD, 16));
        lblModuloAdministrador.setBounds(20, 115, 240, 25);
        painel.add(lblModuloAdministrador);

        btnRelatorioCedulas = new JButton("Relatorio de Cedulas");
        btnRelatorioCedulas.setBounds(20, 145, 265, 30);
        painel.add(btnRelatorioCedulas);

        btnValorTotal = new JButton("Valor total disponivel");
        btnValorTotal.setBounds(20, 180, 265, 30);
        painel.add(btnValorTotal);

        btnReposicaoCedulas = new JButton("Reposicao de Cedulas");
        btnReposicaoCedulas.setBounds(20, 215, 265, 30);
        painel.add(btnReposicaoCedulas);

        btnCotaMinima = new JButton("Cota Minima");
        btnCotaMinima.setBounds(20, 250, 265, 30);
        painel.add(btnCotaMinima);

        JLabel lblModuloAmbos = new JLabel("Modulo de Ambos:");
        lblModuloAmbos.setFont(new Font("Arial", Font.BOLD, 16));
        lblModuloAmbos.setBounds(20, 290, 200, 25);
        painel.add(lblModuloAmbos);

        btnSair = new JButton("Sair");
        btnSair.setBounds(20, 320, 265, 30);
        btnSair.addActionListener(this);
        painel.add(btnSair);

        setVisible(true);
    }    
    
    //serve para definir a ação que será executada quando um evento de interface gráfica
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSair) {
            System.exit(0);
        }
    }
}