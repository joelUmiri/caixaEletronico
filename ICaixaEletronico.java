package caixaeletronico;

/**
* Interface (contrato) para utilizacao da interface grafica.
* Nesse contrato e definido as operacoes de entrada e saida de dinheiro do caixa eletronico
*/
public interface ICaixaEletronico{

    public String pegaValorTotalDisponivel();

    public String sacar(Integer valor);

    public String pegaRelatorioCedulas();

    public String reposicaoCedulas(Integer cedula, Integer quantidade);

    public String armazenaCotaMinima(Integer minimo);
}