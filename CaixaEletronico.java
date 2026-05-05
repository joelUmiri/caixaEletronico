package caixaeletronico;

public class CaixaEletronico implements ICaixaEletronico {

	private int[][] notas = { { 100, 0 }, { 50, 0 }, { 20, 0 }, { 10, 0 }, { 5, 0 }, { 2, 0 } };

	private int cotaMinima = 0;

	@Override
	public String pegaRelatorioCedulas() {
		String resposta = "Relatório de Notas:\n";
		for (int i = 0; i < notas.length; i++) {
			// [i][0] é o valor da nota, [i][1] é a quantidade
			resposta += "R$ " + notas[i][0] + ": " + notas[i][1] + " unidade(s)\n";
		}
		return resposta;
	}

	@Override
	public String pegaValorTotalDisponivel() {
		int total = 0;
		for (int i = 0; i < notas.length; i++) {
			total += notas[i][0] * notas[i][1];
		}
		return "R$ " + total;
	}

	@Override
	public String reposicaoCedulas(Integer cedula, Integer quantidade) {
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
		// Cálculo do saldo total numérico para verificar a cota mínima
		int totalNoCaixa = 0;
		for (int i = 0; i < notas.length; i++) {
			totalNoCaixa += notas[i][0] * notas[i][1];
		}

		// Se o saldo total for menor que a cota, para o atendimento
		if (totalNoCaixa <= cotaMinima) {
			return "Caixa Vazio: Chame o Operador";
		}

		if (valor == 1 || valor == 3 || valor < 0) {
			return "Não Temos Notas Para Este Saque";
		}

		int restante = valor;
		int[] notasParaDar = new int[6];

		// Passo de Segurança: Se for ímpar, obriga o uso de uma nota de 5 para ficar
		// par
		if (restante % 2 != 0) {
			if (notas[4][1] > 0) { // estoque[4] é a linha da nota de 5[cite: 1]
				notasParaDar[4] = 1;
				restante -= 5;
			} else {
				return "Não Temos Notas Para Este Saque";
			}
		}

		// Loop de distribuição priorizando as maiores notas[cite: 1]
		for (int i = 0; i < notas.length; i++) {
			int valorNota = notas[i][0];
			int qtdNecessaria = restante / valorNota;
			int entregaReal = Math.min(qtdNecessaria, notas[i][1]);

			// Lógica para não deixar o restante ímpar (1 ou 3) ao usar notas de 5
			if (valorNota == 5 && entregaReal > 0) {
				if ((restante - (entregaReal * 5)) % 2 != 0) {
					entregaReal--;
				}
			}

			notasParaDar[i] += entregaReal;
			restante -= (entregaReal * valorNota);
		}

		// Verificação do limite de 30 cédulas por saque[cite: 1]
		int totalNotasSaque = 0;
		for (int qtd : notasParaDar)
			totalNotasSaque += qtd;

		if (restante == 0 && totalNotasSaque <= 30) {
			String resposta = "Saque de R$ " + valor + " realizado:\n";
			for (int i = 0; i < notas.length; i++) {
				if (notasParaDar[i] > 0) {
					notas[i][1] -= notasParaDar[i]; // Atualiza a matriz de quantidades[cite: 1]
					resposta += notasParaDar[i] + " nota(s) de R$ " + notas[i][0] + "\n";
				}
			}
			return resposta;
		} else if (totalNotasSaque > 30) {
			return "Limite de 30 cédulas excedido."; // Regra obrigatória[cite: 1]
		} else {
			return "Não Temos Notas Para Este Saque";
		}
	}

	@Override
	public String armazenaCotaMinima(Integer minimo) {
		this.cotaMinima = minimo;
		return "Cota mínima definida para: R$ " + minimo;
	}

	public static void main(String arg[]) {
		CaixaEletronico logica = new CaixaEletronico(); // cria uma nova instancia da classe
		new GUI(logica); // cria uma GUI utilizando a estrutura da classe CaixaEletronico
	}
}
