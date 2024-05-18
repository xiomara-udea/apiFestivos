package apifestivos.apifestivos.presentacion;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import apifestivos.apifestivos.core.entidades.Festivo;
import apifestivos.apifestivos.core.interfaces.servicios.IFestivoServicio;

@RestController
@RequestMapping("/apifestivos/festivos")
public class FestivoControlador {
    
    private IFestivoServicio servicio; 
    
    public FestivoControlador(IFestivoServicio servicio){
        this.servicio = servicio;
    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public List<Festivo> listar(){
        return servicio.listar();
    } 

    @RequestMapping(value = "/verificar/{year}/{month}/{day}", method = RequestMethod.GET)
    public String verificar(@PathVariable int year, @PathVariable int month, @PathVariable int day){
        return this.servicio.verificar(year, month, day);
    } 
}
