package br.com.biblioteca.shared;

import br.com.biblioteca.enums.EPerfil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BibliotecaUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static SimpleDateFormat getDateFormat(){
        return sdf;
    }

    public static String getDateFormated(Date data){
        return sdf.format(data);
    }

    public static String getPerfilFormated(List<EPerfil> perfis){
        StringBuilder texto = new StringBuilder();
        var perfisArray = perfis.toArray();

        for (Object o : perfisArray) {
            texto.append(o.toString());
            texto.append(", ");
        }

        var tamanho = texto.toString().length();
        texto.delete(tamanho - 2, tamanho - 1);
        return texto.toString();
    }

    public static Date addYearsToDate(Date data, int countYears){
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);

        cal.add(Calendar.YEAR, countYears);

        return cal.getTime();
    }

    public static Date addMonthsToDate(Date data, int countMonths){
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);

        cal.add(Calendar.MONTH, countMonths);

        return cal.getTime();
    }

    public static Date addDaysToDate(Date data, int countDays){
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);

        cal.add(Calendar.DAY_OF_MONTH, countDays);

        return cal.getTime();
    }

    public static String getDateFormated(Date data, SimpleDateFormat format){
        return format.format(data);
    }

    public static boolean ValidateCPF(String cpf)
    {
        var mask = "000.000.000-00";
        String regex = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";

        if(cpf.length() != mask.length())
            return false;

        return cpf.matches(regex);
    }

    public static boolean ValidateTelefone(String telefone)
    {
        var mask = "(00) 00000-0000";
        String regex = "\\(\\d{2}\\)\\s9\\d{4}-\\d{4}";

        if(telefone.length() != mask.length())
            return false;

        return telefone.matches(regex);
    }

    public static boolean ValidateCEP(String cep) {
        var mask = "00000-000";
        String regex = "\\d{5}-\\d{3}";

        if(cep.length() != mask.length())
            return false;

        return cep.matches(regex);
    }

    public static boolean ValidateNome(String nome) {
        String regex = "^[\\p{L}\\p{N} ]+$";

        if(nome.length() >= 120)
            return false;

        return nome.matches(regex);

    }
}
