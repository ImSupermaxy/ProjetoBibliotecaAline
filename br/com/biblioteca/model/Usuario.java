package br.com.biblioteca.model;
import br.com.biblioteca.base.model.Entity;
import br.com.biblioteca.enums.EPerfil;
import br.com.biblioteca.enums.EStatus;
import br.com.biblioteca.shared.BibliotecaUtils;

import java.util.Date;
import java.util.List;

public class Usuario extends Entity {
    private final String nome;
    private final String cpf;
    private final String email;
    private final String senha;
    private final Date dataNascimento;
    private final String telefone;
    public final Endereco endereco;
    private final List<EPerfil> perfil;
    private EStatus status;

    public Usuario(String nome, String cpf, String email, String senha, Date dataNascimento, String telefone, Endereco endereco, List<EPerfil> perfis){
        if(nome == null || nome.isEmpty())
            throw new IllegalArgumentException("Nome inválido.");
        if (cpf == null || cpf.isBlank())
            throw new IllegalArgumentException("Documento inválido.");
        if(!BibliotecaUtils.ValidateCPF(cpf))
            throw new IllegalArgumentException("O CPF deve ser no formato: 000.000.000-00");
        if (telefone == null || telefone.isBlank())
            throw new IllegalArgumentException("Telefone inválido.");
        if (!BibliotecaUtils.ValidateTelefone(telefone))
            throw new IllegalArgumentException("O telefone deve ser no formato: (00) 90000-0000");
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("E-mail inválido.");
        if (dataNascimento == null || dataNascimento.after(new Date()))
            throw new IllegalArgumentException("Data de nascimento inválida.");

        this.nome = nome.trim();
        this.cpf = cpf;
        this.email = email;
        this.senha = senha.trim();
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public Endereco getEndereco(){
        return endereco;
    }

    public List<EPerfil> getPerfil() {
        return perfil;
    }

    public void inativaUsuario() { this.status = EStatus.Inativo; }

    public void ativaUsuario() { this.status = EStatus.Ativo; }

    @Override
    public String toString() {
        return String.format("Usuário: %s\n", nome)
                + String.format("Email: %s\n", email)
                + String.format("Cpf: %s\n", cpf)
                + String.format("Senha: %s\n", senha)
                + String.format("Data Nascimento: %s\n", BibliotecaUtils.getDateFormated(dataNascimento))
                + String.format("Telfone: %s\n", telefone)
                + String.format("Endereco: \n%s\n", endereco.toString())
                + String.format("Status: %s\n", status.name())
                + String.format("Perfís: %s", BibliotecaUtils.getPerfilFormated(perfil));
        //return super.toString();
    }
}
