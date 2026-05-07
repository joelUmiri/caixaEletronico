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
	    // Calcula o valor total que tem no caixa pra saber se dá pra começar a brincadeira
	    int totalNoCaixa = 0;
	    for (int i = 0; i < notas.length; i++) {
	        totalNoCaixa += notas[i][0] * notas[i][1];
	    }

	    // Se o que tem no caixa for menor ou igual à reserva (cota mínima), o banco nem abre
	    if (totalNoCaixa <= cotaMinima) return "Caixa Vazio: Chame o Operador";
	    
	    // Trava para valores que nota de 2 e 5 não pagam (1 e 3 reais) ou valores zoados
	    if (valor == 1 || valor == 3 || valor <= 0) return "Não Temos Notas Para Este Saque";

	    int[] notasDefinitivas = new int[6]; 
	    boolean conseguiu = false;

	    // Pega o limite de notas de 100 e 50 que a gente tem no estoque para os testes
	    int max100 = Math.min(valor / 100, notas[0][1]);
	    int max50 = Math.min(valor / 50, notas[1][1]);

	    // Aqui é onde o sistema testa as combinações. Se ele não conseguir pagar 
	    // usando o máximo de 100 e 50, ele vai diminuindo uma por uma e tentando de novo.
	    for (int t100 = max100; t100 >= 0; t100--) {
	        for (int t50 = max50; t50 >= 0; t50--) {
	            
	            int restante = valor;
	            int[] tentativaLocal = new int[6];

	            // Usa a quantidade de notas de 100 definida por esse passo do loop
	            tentativaLocal[0] = t100;
	            restante -= (t100 * 100);
	            
	            if (restante < 0) continue; 

	            // Usa a quantidade de notas de 50 definida por esse passo do loop
	            int qtd50Efetiva = Math.min(t50, restante / 50);
	            tentativaLocal[1] = qtd50Efetiva;
	            restante -= (qtd50Efetiva * 50);

	            // Se o valor for ímpar, a gente PRECISA gastar uma nota de 5 pra ele ficar par.
	            // Se não tiver nota de 5 pra isso, essa combinação de 100 e 50 já não serve.
	            if (restante % 2 != 0) {
	                if (notas[4][1] > 0 && restante >= 5) {
	                    tentativaLocal[4] = 1; 
	                    restante -= 5;
	                } else {
	                    continue; 
	                }
	            }

	            // Depois de decidir os 100, 50 e o 5 do ímpar, preenche o resto com o que tiver
	            for (int i = 2; i < notas.length; i++) {
	                int vNota = notas[i][0];
	                if (vNota == 5 && tentativaLocal[4] == 1) continue; // Pula o 5 porque já usamos lá em cima

	                int qtdNecessaria = restante / vNota;
	                int entregaReal = Math.min(qtdNecessaria, notas[i][1]);
	                
	                tentativaLocal[i] += entregaReal;
	                restante -= (entregaReal * vNota);
	            }

	            // Se o restante zerou, achamos a combinação perfeita de notas
	            if (restante == 0) {
	                notasDefinitivas = tentativaLocal;
	                conseguiu = true;
	                break; 
	            }
	        }
	        if (conseguiu) break; 
	    }

	    // Se achou uma combinação, só falta ver se não deu papel demais e entregar
	    if (conseguiu) {
	        int totalNotasSaque = 0;
	        for (int qtd : notasDefinitivas) totalNotasSaque += qtd;

	        if (totalNotasSaque <= 30) {
	            String resposta = "Saque de R$ " + valor + " realizado:\n";
	            for (int i = 0; i < notas.length; i++) {
	                if (notasDefinitivas[i] > 0) {
	                    // Atualiza o estoque real tirando as notas que o cliente vai levar
	                    notas[i][1] -= notasDefinitivas[i]; 
	                    resposta += notasDefinitivas[i] + " nota(s) de R$ " + notas[i][0] + "\n";
	                }
	            }
	            return resposta;
	        } else {
	            return "Limite de 30 cédulas excedido.";
	        }
	    }

	    return "Não Temos Notas Para Este Saque";
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
