package br.com.biblioteca.service;

import br.com.biblioteca.base.service.IEnumService;
import br.com.biblioteca.enums.ERetirada;
import br.com.biblioteca.model.Retirada;

import java.util.Scanner;

public interface IRetiradaService extends IEnumService<ERetirada> {
    Retirada criarRetirada(Scanner scanner);
}
