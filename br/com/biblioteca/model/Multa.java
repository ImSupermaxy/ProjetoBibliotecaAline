package br.com.biblioteca.model;

import br.com.biblioteca.enums.EStatus;
import br.com.biblioteca.shared.BibliotecaUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Multa {
    private final double valor;  // por dia de atraso na devolução...
    private final Date dataMulta;
    private Date dataPagamento;
    private EStatus status;

    public Multa(Date dataADevolver, double taxaVencimento, Date dataDevolucao) {
        this.dataMulta = dataDevolucao;
        this.valor = calculaValorPelaData(dataADevolver, taxaVencimento);
        this.status = EStatus.Ativo;
    }

    private double calculaValorPelaData(Date dataADevolver, double taxaVencimento) {
        long diffEmMillis = Math.abs(dataADevolver.getTime() - this.getDataMulta().getTime());
        long diffEmDias = TimeUnit.MILLISECONDS.toDays(diffEmMillis);

        return diffEmDias * taxaVencimento;
    }

    public double getValor(){
        return this.valor;
    }

    public Date getDataMulta() {
        return dataMulta;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public EStatus getStatus(){
        return this.status;
    }

    public void atualizaDataPagamento(){
        this.dataPagamento = new Date();
    }

    public void atualizaStatus(EStatus status){
        this.status = status;
    }

    @Override
    public String toString(){
        var paga = dataPagamento != null;
        return String.format("Valor: %s\n", valor)
                + String.format("Data Multa: %s \n", BibliotecaUtils.getDateFormated(dataMulta))
                + String.format("Multa Paga?: %s\n", paga)
                + String.format("Data Pagamento: %s\n", paga ? BibliotecaUtils.getDateFormated(dataPagamento) : "")
                + String.format("Status: %s\n", status);
    }
}
