package br.com.biblioteca.service;

import br.com.biblioteca.base.service.IService;
import br.com.biblioteca.model.Endereco;

import java.util.Scanner;

public interface IEnderecoService extends IService {
    Endereco criarEndereco(Scanner scanner);
}
