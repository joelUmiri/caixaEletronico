package caixaeletronico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// A classe GUI cria a janela do sistema e "escuta" os cliques nos botões (ActionListener)
public class GUI extends JFrame implements ActionListener {

	// Criar versão serial para que não dê um warning no JFrame
	private static final long serialVersionUID = 1L; 
	
	private CaixaEletronico logica;

	// Declaração dos botões da interface
	private JButton btnEfetuarSaque;
	private JButton btnRelatorioCedulas;
	private JButton btnValorTotal;
	private JButton btnReposicaoCedulas;
	private JButton btnCotaMinima;
	private JButton btnSair;

	// Variáveis para guardar a posição do mouse e permitir arrastar a janela
	private int mouseX, mouseY;

	// Construtor: Configura como a janela deve aparecer ao ser criada
	public GUI(CaixaEletronico logica) {
		this.logica = logica;

		// Configurações básicas da janela (tamanho, remover bordas padrão e centralizar)
		setSize(310, 380);
		setUndecorated(true); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setLocationRelativeTo(null);
		setResizable(false);

		// Cria o painel principal cinza onde os botões serão colocados
		JPanel painel = new JPanel(null); 
		painel.setBackground(new Color(212, 212, 212));
		add(painel);

		// Cria a barra azul no topo (já que a padrão foi removida)
		JPanel barra = new JPanel(null);
		barra.setBounds(0, 0, 320, 30);
		barra.setBackground(new Color(10, 36, 106)); 
		painel.add(barra);

		// Adiciona o título branco na barra azul
		JLabel titulo = new JLabel("Caixa eletronico");
		titulo.setForeground(Color.WHITE);
		titulo.setFont(new Font("Arial", Font.BOLD, 13));
		titulo.setBounds(10, 5, 200, 20);
		barra.add(titulo);

		// Botão "X" para fechar o programa
		JButton btnFechar = new JButton("X");
		btnFechar.setBounds(285, 5, 25, 20);
		btnFechar.setMargin(new Insets(0, 0, 0, 0));
		barra.add(btnFechar);
		btnFechar.addActionListener(e -> System.exit(0));

		// Permite clicar e arrastar a janela pela barra azul
		barra.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		barra.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				setLocation(e.getXOnScreen() - mouseX, e.getYOnScreen() - mouseY);
			}
		});

		// --- Módulo do Cliente ---
		JLabel lblModuloCliente = new JLabel("Modulo do Cliente:");
		lblModuloCliente.setFont(new Font("Arial", Font.BOLD, 16));
		lblModuloCliente.setBounds(20, 40, 200, 25);
		painel.add(lblModuloCliente); 

		btnEfetuarSaque = new JButton("Efetuar Saque");
		btnEfetuarSaque.setBounds(20, 70, 265, 30);
		btnEfetuarSaque.addActionListener(this); // Diz que o clique será tratado no actionPerformed
		painel.add(btnEfetuarSaque);

		// --- Módulo do Administrador ---
		JLabel lblModuloAdministrador = new JLabel("Modulo do Administrador:");
		lblModuloAdministrador.setFont(new Font("Arial", Font.BOLD, 16));
		lblModuloAdministrador.setBounds(20, 115, 240, 25);
		painel.add(lblModuloAdministrador);

		btnRelatorioCedulas = new JButton("Relatorio de Cedulas");
		btnRelatorioCedulas.setBounds(20, 145, 265, 30);
		btnRelatorioCedulas.addActionListener(this);
		painel.add(btnRelatorioCedulas);

		btnValorTotal = new JButton("Valor total disponivel");
		btnValorTotal.setBounds(20, 180, 265, 30);
		btnValorTotal.addActionListener(this);
		painel.add(btnValorTotal);

		btnReposicaoCedulas = new JButton("Reposicao de Cedulas");
		btnReposicaoCedulas.setBounds(20, 215, 265, 30);
		btnReposicaoCedulas.addActionListener(this);
		painel.add(btnReposicaoCedulas);

		btnCotaMinima = new JButton("Cota Minima");
		btnCotaMinima.setBounds(20, 250, 265, 30);
		btnCotaMinima.addActionListener(this);
		painel.add(btnCotaMinima);

		// --- Módulo Geral ---
		JLabel lblModuloAmbos = new JLabel("Modulo de Ambos:");
		lblModuloAmbos.setFont(new Font("Arial", Font.BOLD, 16));
		lblModuloAmbos.setBounds(20, 290, 200, 25);
		painel.add(lblModuloAmbos);

		btnSair = new JButton("Sair");
		btnSair.setBounds(20, 320, 265, 30);
		btnSair.addActionListener(this);
		painel.add(btnSair);

		setVisible(true); // Faz a janela aparecer na tela
	}

	// Método que decide o que fazer quando qualquer botão é clicado
	public void actionPerformed(ActionEvent e) {
        
		if (e.getSource() == btnRelatorioCedulas) {
			// Mostra o relatório de notas vindo da lógica
			JOptionPane.showMessageDialog(this, logica.pegaRelatorioCedulas());
            
		} else if (e.getSource() == btnValorTotal) {
			// Mostra o saldo total vindo da lógica
			JOptionPane.showMessageDialog(this, "Saldo total: " + logica.pegaValorTotalDisponivel());
            
		} else if (e.getSource() == btnSair) {
			System.exit(0);
            
		} else if (e.getSource() == btnCotaMinima) {
			String input = JOptionPane.showInputDialog(this, "Informe o valor da cota mínima:");
			if (input != null && !input.isEmpty()) {
				int valor = Integer.parseInt(input);
				String mensagem = logica.armazenaCotaMinima(valor);
				JOptionPane.showMessageDialog(this, mensagem);
			}
            
		} else if (e.getSource() == btnReposicaoCedulas) {
			String inputCedula = JOptionPane.showInputDialog(this, "Qual o valor da nota para reposição?");
			if (inputCedula != null) {
				String inputQtd = JOptionPane.showInputDialog(this, "Quantas notas deseja adicionar?");
				if (inputQtd != null) {
					int cedula = Integer.parseInt(inputCedula);
					int quantidade = Integer.parseInt(inputQtd);
					String mensagem = logica.reposicaoCedulas(cedula, quantidade);
					JOptionPane.showMessageDialog(this, mensagem);
				}
			}
            
		} else if (e.getSource() == btnEfetuarSaque) {
			String input = JOptionPane.showInputDialog(this, "Digite o valor do saque:");
			if (input != null && !input.isEmpty()) {
				int valorSaque = Integer.parseInt(input);
				String resultado = logica.sacar(valorSaque);
				JOptionPane.showMessageDialog(this, resultado);
			}
		}
	}
}
