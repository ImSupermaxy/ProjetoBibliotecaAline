package br.com.biblioteca.service.impl;

import br.com.biblioteca.enums.EStatus;
import br.com.biblioteca.service.IStatusService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static br.com.biblioteca.shared.ConsoleUtils.lerInt;

public class StatusServiceImpl implements IStatusService {
    @Override
    public List<EStatus> getAll() {
        return Arrays.stream(EStatus.values())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllNome() {
        return Arrays.stream(EStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public EStatus criarStatus(Scanner scanner) {
        var allStatus = this.getAllNome().toArray(new String[0]);

        System.out.println("Opções de status do sistema: ");
        for (int i = 0; i < allStatus.length; i++)
        {
            System.out.println((i + 1) + " - " + allStatus[i]);
        }

        var status = lerInt(scanner, "Status: ");

        if(status < 1 || status > allStatus.length)
            throw new IllegalArgumentException("Status inválido.");

        return EStatus.valueOf(allStatus[status - 1]);
    }
}
