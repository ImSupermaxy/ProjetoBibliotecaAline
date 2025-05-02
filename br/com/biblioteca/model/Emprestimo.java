package br.com.biblioteca.model;

import br.com.biblioteca.base.model.Entity;
import br.com.biblioteca.enums.ERetirada;
import br.com.biblioteca.enums.EStatus;
import br.com.biblioteca.shared.BibliotecaUtils;

import java.util.*;

public class Emprestimo extends Entity {
    private final Date dataEmprestimo;
    private final Date dataADevolver;
    private final Usuario usuario;
    private final List<Multa> multas;
    private final Livro livro;
    private final Retirada retirada;
    private Date dataDevolvolucao;
    private EStatus status;

    public Emprestimo(Usuario usuario, Livro livro, Retirada retirada){
        this.dataEmprestimo = new Date();
        this.dataADevolver = BibliotecaUtils.addMonthsToDate(this.getDataEmprestimo(), 1);
        this.usuario = usuario;
        this.multas = new ArrayList<Multa>();
        this.livro = livro;
        this.retirada = retirada;
        this.status = EStatus.Ativo;
    }

    public Date getDataEmprestimo(){
        return dataEmprestimo;
    }

    public Date getDataADevolver() {
        return this.dataADevolver;
    }

    public Usuario getUsuario(){
        return this.usuario;
    }

    public List<Multa> getMultas(){
        return multas;
    }

    public void addMulta(Multa multa){
        this.multas.add(multa);
    }

    public Livro getLivro(){
        return this.livro;
    }

    public Retirada getRetirada(){
        return this.retirada;
    }

    public Date getDataDevolvolucao(){
        return this.dataDevolvolucao;
    }

    public void updateDataDevolvolucao(Date dataDevolvolucao) {
        this.dataDevolvolucao = dataDevolvolucao;
        updateStatus(EStatus.Inativo);
        updateAllMultaStatus(EStatus.Inativo);
    }

    public EStatus getStatus(){
        return this.status;
    }

    public void updateStatus(EStatus status){
        this.status = status;
    }

    public void updateAllMultaStatus(EStatus status)
    {
        this.multas.forEach(m -> m.atualizaStatus(status));
    }

    @Override
    public String toString(){
        var devolvido = dataDevolvolucao != null;

        StringBuilder emprestimo = new StringBuilder("Empréstimo: \nEm: "
                + String.format("%s\n", BibliotecaUtils.getDateFormated(dataEmprestimo))
                + String.format("Data limite para devolver: %s\n\n", BibliotecaUtils.getDateFormated(dataADevolver))
                + String.format("Data devolvida: %s\n\n", devolvido ? BibliotecaUtils.getDateFormated(dataDevolvolucao) : "")
                + String.format("%s\n\n", usuario.toString())
                + String.format("%s\n", livro.toString())
                + String.format("Forma Retirada: %s\n", retirada.getRetirada()));

        if(retirada.getRetirada() == ERetirada.Entrega)
        {
            emprestimo.append(String.format("Entrega em: %s\n", retirada.getEndereco().toString()));
        }

        emprestimo.append(String.format("Status: %s\n", status));

        emprestimo.append("\n");

        if(!multas.isEmpty())
        {
            var multasArr = multas.toArray();
            for (int i = 0; i < multasArr.length; i++){
                emprestimo.append(String.format("Multa %s:\n", i + 1))
                        .append(String.format("%s\n", multasArr[i].toString()));
            }
            emprestimo.append("\n");
        }

        return emprestimo.toString();
    }
}
