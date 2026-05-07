package caixaeletronico;

public class CaixaEletronico implements ICaixaEletronico {

	// Matriz que guarda os valores das notas e a quantidade disponível de cada uma
	private int[][] notas = { { 100, 0 }, { 50, 0 }, { 20, 0 }, { 10, 0 }, { 5, 0 }, { 2, 0 } };

	// Valor limite de segurança do caixa
	private int cotaMinima = 0;

	@Override
	public String pegaRelatorioCedulas() {
		String resposta = "Relatório de Notas:\n";
		// Percorre a matriz para montar o texto com as quantidades atuais
		for (int i = 0; i < notas.length; i++) {
			resposta += "R$ " + notas[i][0] + ": " + notas[i][1] + " unidade(s)\n";
		}
		return resposta;
	}

	@Override
	public String pegaValorTotalDisponivel() {
		int total = 0;
		// Multiplica o valor da nota pela sua quantidade e soma tudo
		for (int i = 0; i < notas.length; i++) {
			total += notas[i][0] * notas[i][1];
		}
		return "R$ " + total;
	}

	@Override
	public String reposicaoCedulas(Integer cedula, Integer quantidade) {
		// Procura na matriz a nota informada e adiciona a nova quantidade
		for (int i = 0; i < notas.length; i++) {
			if (notas[i][0] == cedula) {
				notas[i][1] += quantidade;
				return "Reposição de " + quantidade + " notas de R$ " + cedula + " realizada!";
			}
		}
		return "Nota de R$ " + cedula + " não reconhecida.";
	}

	@Override
	public String sacar(Integer valor) {
		// Calcula quanto dinheiro tem no total antes de começar o saque
		int totalNoCaixa = 0;
		for (int i = 0; i < notas.length; i++) {
			totalNoCaixa += notas[i][0] * notas[i][1];
		}

		// Trava de segurança: impede o saque se o valor no caixa for menor ou igual à cota
		if (totalNoCaixa <= cotaMinima) {
			return "Caixa Vazio: Chame o Operador";
		}

		// Verifica valores que não podem ser formados com as notas disponíveis (2, 5, 10...)
		if (valor == 1 || valor == 3 || valor < 0) {
			return "Não Temos Notas Para Este Saque";
		}

		int restante = valor;
		int[] notasParaDar = new int[6]; // Array temporário para guardar o que será entregue

		// Se o valor for ímpar, retira uma nota de 5 primeiro para tornar o resto par
		if (restante % 2 != 0) {
			if (notas[4][1] > 0) { 
				notasParaDar[4] = 1;
				restante -= 5;
			} else {
				return "Não Temos Notas Para Este Saque";
			}
		}

		// Distribui as notas começando da maior (100) para a menor (2)
		for (int i = 0; i < notas.length; i++) {
			int valorNota = notas[i][0];
			int qtdNecessaria = restante / valorNota;
			
			// Entrega pro entregaReal o maior número de notas desse tipo que tem no estoque
			int entregaReal = Math.min(qtdNecessaria, notas[i][1]);

			// Regra para não deixar o restante ímpar (como 1 ou 3) ao usar notas de 5
			// Se for ímpar, usa uma nota 5 a menos.
			if (valorNota == 5 && entregaReal > 0) {
				if ((restante - (entregaReal * 5)) % 2 != 0) {
					entregaReal--;
				}
			}

			notasParaDar[i] += entregaReal;
			restante -= (entregaReal * valorNota);
		}

		// Soma o total de papéis (cédulas) que serão entregues
		int totalNotasSaque = 0;
		for (int qtd : notasParaDar)
			totalNotasSaque += qtd;

		// Se conseguiu zerar o valor e não passou de 30 notas, finaliza o saque
		if (restante == 0 && totalNotasSaque <= 30) {
			String resposta = "Saque de R$ " + valor + " realizado:\n";
			for (int i = 0; i < notas.length; i++) {
				if (notasParaDar[i] > 0) {
					notas[i][1] -= notasParaDar[i]; // Tira as notas do estoque real
					resposta += notasParaDar[i] + " nota(s) de R$ " + notas[i][0] + "\n";
				}
			}
			return resposta;
		} else if (totalNotasSaque > 30) {
			return "Limite de 30 cédulas excedido.";
		} else {
			return "Não Temos Notas Para Este Saque";
		}
	}

	@Override
	public String armazenaCotaMinima(Integer minimo) {
		// Define o novo valor de reserva do caixa
		this.cotaMinima = minimo;
		return "Cota mínima definida para: R$ " + minimo;
	}

	public static void main(String arg[]) {
		// Inicia o sistema criando a lógica e depois a interface gráfica
		CaixaEletronico logica = new CaixaEletronico(); 
		new GUI(logica); 
	}
}
