package br.com.biblioteca.shared;

import br.com.biblioteca.enums.EPerfil;
import br.com.biblioteca.enums.ERetirada;
import br.com.biblioteca.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TesteConsole {

    public static void TesteBiblioteca(){
        System.out.println("Entender o pq a BibliotecaUtils não ta funcionando....");

        var usuario = CriaUsuario();
        var livro = new Livro("Duna 2", 5, "José de camargo");
        var empresitmo = new Emprestimo(usuario, livro, new Retirada(ERetirada.Biblioteca, null));

        var calendar = Calendar.getInstance();
        calendar.setTime(empresitmo.getDataADevolver());
        calendar.add(Calendar.DATE, 15);
        var dataVencimento = calendar.getTime();

        empresitmo.addMulta(new Multa(dataVencimento, 5.00, new Date()));

        var perfis = new ArrayList<EPerfil>();
        perfis.add(EPerfil.Master);
        var funcionario = new Funcionario("Aline dos Santos", "000.000.000-00", "aline@testeemail.com", "aline@senha", perfis);

        System.out.println(usuario.toString() + "\n");
        System.out.println("=========----------------==============----------------=========\n");
        System.out.println(empresitmo.toString());
        System.out.println("=========----------------==============----------------=========\n");
        System.out.println(funcionario.toString() + "\n");
    }

    public static Usuario CriaUsuario(){
        var perfis = new ArrayList<EPerfil>();
        perfis.add(EPerfil.Cliente);

        return new Usuario("Matheus", "497.431.228-69", "matheus@gmail.com", "testeSenha@", new Date(),
                "(11) 99999-9999", new Endereco("00000-000", "Rua teste alskdjal", "Vilda Teste", null, "000", "SP", "São Paulo"), perfis);
    }

    public static Funcionario CriaFuncionario(){
        var perfis = new ArrayList<EPerfil>();
        perfis.add(EPerfil.Master);
        return new Funcionario("Aline dos Santos", "000.000.000-00", "aline@testeemail.com", "aline@senha", perfis);
    }
}
