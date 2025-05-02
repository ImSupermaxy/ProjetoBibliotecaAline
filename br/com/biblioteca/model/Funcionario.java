package br.com.biblioteca.model;

import br.com.biblioteca.base.model.Entity;
import br.com.biblioteca.enums.EPerfil;
import br.com.biblioteca.enums.EStatus;
import br.com.biblioteca.shared.BibliotecaUtils;

import java.util.List;


public class Funcionario extends Entity {
    private final String nome;
    private final String cpf;
    private final String email;
    private final String senha;
    private final List<EPerfil> perfil;
    public EStatus status;

    public Funcionario(String nome, String cpf, String email, String senha, List<EPerfil> perfis){
        if(nome == null || nome.isEmpty())
            throw new IllegalArgumentException("Nome inválido.");
        if (cpf == null || cpf.isBlank())
            throw new IllegalArgumentException("Documento inválido.");
        if(!BibliotecaUtils.ValidateCPF(cpf))
            throw new IllegalArgumentException("O CPF deve ser no formato: 000.000.000-00");
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("E-mail inválido.");

        this.nome = nome.trim();
        this.cpf = cpf;
        this.email = email;
        this.senha = senha.trim();
        this.perfil = perfis;
        this.status = EStatus.Ativo;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public List<EPerfil> getPerfil() {
        return perfil;
    }

    public void inativaUsuario() { this.status = EStatus.Inativo; }

    public void ativaUsuario() { this.status = EStatus.Ativo; }


    @Override
    public String toString(){
        return String.format("Usuário: %s\n", nome)
                + String.format("Email: %s\n", email)
                + String.format("Cpf: %s\n", cpf)
                + String.format("Senha: %s\n", senha)
                + String.format("Status: %s\n", status.name())
                + String.format("Perfís: %s", BibliotecaUtils.getPerfilFormated(perfil));
    }
}
