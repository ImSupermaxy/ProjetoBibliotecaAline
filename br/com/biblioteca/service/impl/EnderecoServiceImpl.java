package br.com.biblioteca.service.impl;

import br.com.biblioteca.model.Endereco;
import br.com.biblioteca.service.IEnderecoService;

import java.util.Scanner;

import static br.com.biblioteca.shared.ConsoleUtils.lerLinha;

public class EnderecoServiceImpl implements IEnderecoService {

    @Override
    public Endereco criarEndereco(Scanner scanner) {
        var logradouro = lerLinha(scanner, "Logradouro");
        var uf = lerLinha(scanner, "UF (Sígla)", u -> u.length() != 2);
        var bairro = lerLinha(scanner, "Bairro");
        var cidade = lerLinha(scanner, "Cidade");
        var cep = lerLinha(scanner, "CEP (formato 00000-000)");
        var complemento = lerLinha(scanner, "Complemento");
        var numero = lerLinha(scanner, "Número (caso não tenha digite s/n)");

        return new Endereco(cep, logradouro, bairro, complemento, numero, uf, cidade);
    }

}
