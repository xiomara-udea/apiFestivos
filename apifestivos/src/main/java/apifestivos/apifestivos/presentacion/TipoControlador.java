package apifestivos.apifestivos.presentacion;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import apifestivos.apifestivos.aplicacion.TipoServicio;
import apifestivos.apifestivos.core.entidades.Tipo;


@RestController
@RequestMapping("/apifestivos/tipo")
public class TipoControlador {

    private TipoServicio servicio;

    public TipoControlador(TipoServicio servicio){
        this.servicio = servicio;
    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public List<Tipo> listar(){
        return servicio.listar();
    }
}
