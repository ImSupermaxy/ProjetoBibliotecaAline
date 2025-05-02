package br.com.biblioteca.service;

import br.com.biblioteca.base.model.Entity;
import br.com.biblioteca.base.service.IService;
import br.com.biblioteca.enums.EStatus;
import br.com.biblioteca.model.Multa;

import java.util.Date;
import java.util.List;

public interface IMultaService extends IService {
    Multa criarMulta(Date dataADevolver, Date dataDevolucao);
}
