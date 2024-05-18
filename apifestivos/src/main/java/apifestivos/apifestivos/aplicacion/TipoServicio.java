package apifestivos.apifestivos.aplicacion;

import java.util.List;

import org.springframework.stereotype.Service;

import apifestivos.apifestivos.core.entidades.Tipo;
import apifestivos.apifestivos.core.interfaces.repositorios.ITipoRepositorio;
import apifestivos.apifestivos.core.interfaces.servicios.ITipoServicio;

@Service
public class TipoServicio implements ITipoServicio{

    private ITipoRepositorio repositorio;

    public TipoServicio(ITipoRepositorio repositorio){
        this.repositorio = repositorio;
    }

    @Override
    public List<Tipo> listar() {
        return repositorio.findAll();
    }
    
}
