package apifestivos.apifestivos.core.interfaces.servicios;

import java.util.Date;
import java.util.List;

import apifestivos.apifestivos.core.entidades.Festivo;

public interface IFestivoServicio {
    
    public List<Festivo> listar();

    public List<Festivo> obtener(int year);

    public String verificar(int year, int month, int año);

}
