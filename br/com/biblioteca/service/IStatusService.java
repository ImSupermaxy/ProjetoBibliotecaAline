package br.com.biblioteca.service;

import br.com.biblioteca.base.service.IEnumService;
import br.com.biblioteca.enums.EStatus;

import java.util.Scanner;

public interface IStatusService extends IEnumService<EStatus> {
    EStatus criarStatus(Scanner scanner);
}
