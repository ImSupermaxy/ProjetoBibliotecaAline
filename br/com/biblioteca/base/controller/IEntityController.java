package br.com.biblioteca.base.controller;

import java.util.Scanner;

public interface IEntityController extends IBaseController{
    void cadastrar(Scanner scanner);
    void listar(Scanner scanner);
    void remover(Scanner scanner);
}
