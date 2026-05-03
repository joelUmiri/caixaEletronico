package caixaEletronico;

import java.util.Scanner;

public class CaixaEletronico implements ICaixaEletronico {
	
	
    private int[] notas = {100, 50, 20, 10, 5, 2};
    private int[] quantidades = {0, 0, 0, 0, 0, 0};
    
    private int cotaMinima = 0;
	

	public String pegaRelatorioCedulas() {
		
		String resposta = "Relatório de Notas:\n";

        for (int i = 0; i < notas.length; i++) {
            resposta += "R$ " + notas[i] + ": " + quantidades[i] + " unidade(s)\n";
        }
		
		return resposta;
	}

	public String pegaValorTotalDisponivel() {
		
		int total = 0;
        for (int i = 0; i < notas.length; i++) {
            total += notas[i] * quantidades[i];
        }

        String resposta = "R$ " + total;
        return resposta;
		
		
		
	}

	public String reposicaoCedulas(Integer cedula, Integer quantidade) {
		String resposta = "Nota de R$ " + cedula + " não reconhecida.";
	    
	    for (int i = 0; i < notas.length; i++) {
	        if (notas[i] == cedula) { 
	            quantidades[i] += quantidade; 
	            resposta = "Reposição de " + quantidade + " notas de R$ " + cedula + " realizada!";
	            break; 
	        }
	    }
		
		return resposta;
	}

	public String sacar(Integer valor) {
	    int totalAtual = calcularSomaNumerica();
	    if (totalAtual < cotaMinima) {
	        return "Caixa Vazio: Chame o Operador";
	    }

	    int restante = valor;
	    int[] notasParaDar = new int[6]; 
	    for (int i = 0; i < notas.length; i++) {
	        int qtdNecessaria = restante / notas[i];
	        int qtdDisponivel = quantidades[i];

	        if (qtdNecessaria > 0) {
	            int entregaReal = Math.min(qtdNecessaria, qtdDisponivel);
	            notasParaDar[i] = entregaReal;
	            restante -= (entregaReal * notas[i]);
	        }
	    }

	   
	    if (restante == 0) {
	        String resposta = "Saque de R$ " + valor + " realizado. Notas entregues:\n";
	        for (int i = 0; i < notas.length; i++) {
	            if (notasParaDar[i] > 0) {
	                quantidades[i] -= notasParaDar[i];
	                resposta += notasParaDar[i] + " nota(s) de R$ " + notas[i] + "\n";
	            }
	        }
	        return resposta;
	    } else {
	        return "Não Temos Notas Para Este Saque";
	    }
	}

	private int calcularSomaNumerica() {
	    int soma = 0;
	    for (int i = 0; i < notas.length; i++) {
	        soma += notas[i] * quantidades[i];
	    }
	    return soma;
	}

	public String armazenaCotaMinima(Integer minimo) {
	    this.cotaMinima = minimo; 
	
	    String resposta = "Cota mínima definida para: R$ " + minimo;
		return resposta;
	}

	public static void main(String arg[]) {
		//GUI janela = new GUI(CaixaEletronico.class);
		//janela.show();
		
		CaixaEletronico caixa = new CaixaEletronico();
		Scanner teclado = new Scanner(System.in);
		int opcao = -1;

		System.out.println("=== SISTEMA DE CAIXA ELETRÔNICO (MODO CONSOLE) ===");

		while (opcao != 0) {
			System.out.println("\n1- Reposição | 2- Sacar | 3- Saldo | 4- Relatório | 5- Cota Mínima | 0- Sair");
			System.out.print("Escolha uma opção: ");
			opcao = teclado.nextInt();

			switch (opcao) {
				case 1:
					System.out.print("Valor da cédula (2, 5, 10, 20, 50, 100): ");
					int ced = teclado.nextInt();
					System.out.print("Quantidade: ");
					int qtd = teclado.nextInt();
					System.out.println(caixa.reposicaoCedulas(ced, qtd));
					break;
				case 2:
					System.out.print("Valor do saque: ");
					int valor = teclado.nextInt();
					System.out.println(caixa.sacar(valor));
					break;
				case 3:
					System.out.println("Saldo Total: " + caixa.pegaValorTotalDisponivel());
					break;
				case 4:
					System.out.println(caixa.pegaRelatorioCedulas());
					break;
				case 5:
					System.out.print("Novo valor de cota mínima: ");
					int min = teclado.nextInt();
					System.out.println(caixa.armazenaCotaMinima(min));
					break;
				case 0:
					System.out.println("Encerrando...");
					break;
				default:
					System.out.println("Opção inválida!");
			}
		}
		teclado.close();
	}
}
