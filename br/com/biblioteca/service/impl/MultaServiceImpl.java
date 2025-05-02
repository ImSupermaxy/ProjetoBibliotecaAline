package br.com.biblioteca.service.impl;

import br.com.biblioteca.model.Multa;
import br.com.biblioteca.service.IMultaService;

import java.util.Date;

public class MultaServiceImpl implements IMultaService {

    private final double taxaGeralMulta = 5.00; // R$ 5,00

    public MultaServiceImpl() {
    }

    @Override
    public Multa criarMulta(Date dataADevolver, Date dataDevolucao) {
        return new Multa(dataADevolver, taxaGeralMulta, dataDevolucao);
    }
}
