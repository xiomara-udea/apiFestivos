package apifestivos.apifestivos.core.interfaces.servicios;

import java.util.Date;
import java.util.List;

import apifestivos.apifestivos.core.entidades.Festivo;

public interface IFestivoServicio {
    
    public List<Festivo> listar();

    public List<Festivo> obtener(Integer year);

    public String verificar(int year, int month, int dia);

    public boolean esFestivo(Date fecha);

}
