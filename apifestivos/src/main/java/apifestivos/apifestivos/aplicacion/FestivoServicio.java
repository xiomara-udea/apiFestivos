package apifestivos.apifestivos.aplicacion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import apifestivos.apifestivos.core.entidades.Festivo;
import apifestivos.apifestivos.core.interfaces.repositorios.IFestivoRepositorio;
import apifestivos.apifestivos.core.interfaces.servicios.IFestivoServicio;
import jakarta.persistence.Transient;

@Service
public class FestivoServicio implements IFestivoServicio {

    private IFestivoRepositorio repositorio;
    
    public FestivoServicio(IFestivoRepositorio repositorio){
        this.repositorio = repositorio;
    }
    
    
    private Date obtenerDomingoPascua(int año) {
        int mes, dia, A, B, C, D, E, M, N;
        M = 0;
        N = 0;
        if (año >= 1583 && año <= 1699) {
            M = 22;
            N = 2;
        } else if (año >= 1700 && año <= 1799) {
            M = 23;
            N = 3;
        } else if (año >= 1800 && año <= 1899) {
            M = 23;
            N = 4;
        } else if (año >= 1900 && año <= 2099) {
            M = 24;
            N = 5;
        } else if (año >= 2100 && año <= 2199) {
            M = 24;
            N = 6;
        } else if (año >= 2200 && año <= 2299) {
            M = 25;
            N = 0;
        }

        A = año % 19;
        B = año % 4;
        C = año % 7;
        D = ((19 * A) + M) % 30;
        E = ((2 * B) + (4 * C) + (6 * D) + N) % 7;

        // Decidir entre los 2 casos
        if (D + E < 10) {
            dia = D + E + 22;
            mes = 3; // Marzo
        } else {
            dia = D + E - 9;
            mes = 4; // Abril
        }

        // Excepciones especiales
        if (dia == 26 && mes == 4)
            dia = 19;
        if (dia == 25 && mes == 4 && D == 28 && E == 6 && A > 10)
            dia = 18;
        return new Date(año - 1900, mes - 1, dia);
    }

    private Date agregarDias(Date fecha, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.DATE, dias); // minus number would decrement the days
        return cal.getTime();
    }

    private Date siguienteLunes(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        if (c.get(Calendar.DAY_OF_WEEK) > Calendar.MONDAY)
            fecha = agregarDias(fecha, 8 - c.get(Calendar.DAY_OF_WEEK));
        else if (c.get(Calendar.DAY_OF_WEEK) < Calendar.MONDAY)
            fecha = agregarDias(fecha, 1);
        return fecha;
    }

    private List<Festivo> calcularFestivos(List<Festivo> festivos, int año) {
        if (festivos != null) {
            Date pascua = obtenerDomingoPascua(año);
            int i = 0;
            for (final Festivo festivo : festivos) {
                switch (festivo.getTipo().getId()) {
                    case 1:
                        festivo.setFecha(new Date(año - 1900, festivo.getMes() - 1, festivo.getDia()));
                        break;
                    case 2:
                        festivo.setFecha(siguienteLunes(new Date(año - 1900, festivo.getMes() - 1, festivo.getDia())));
                        break;
                    case 3:
                        festivo.setFecha(agregarDias(pascua, festivo.getDiasPascua()));
                        break;
                    case 4:
                        festivo.setFecha(siguienteLunes(agregarDias(pascua, festivo.getDiasPascua())));
                        break;
                }
                festivos.set(i, festivo);
                i++;
            }
        }
        return festivos;
    }

    public List<Date> obtenerFestivos(int año) {
        List<Festivo> festivos = repositorio.findAll();
        festivos = calcularFestivos(festivos, año);
        List<Date> fechas = new ArrayList<Date>();
        for (final Festivo festivo : festivos) {
            fechas.add(festivo.getFecha());
        }
        return fechas;
    }

    private boolean fechasIguales(Date fecha1, Date fecha2) {
        return fecha1.getYear()==fecha2.getYear() && fecha1.getMonth()==fecha2.getMonth() && fecha1.getDay()==fecha2.getDay();
    }

    private boolean esFestivo(List<Festivo> festivos, Date fecha) {
        if (festivos != null) {
            // if (festivos.get(0).getFecha() != null && fecha.getYear() !=
            // festivos.get(0).getFecha().getYear())
            festivos = calcularFestivos(festivos, fecha.getYear()+1900);

            //System.out.println(fecha.getYear());

            for (final Festivo festivo : festivos) {
                Calendar c = Calendar.getInstance();
                c.setTime(fecha);
                if (fechasIguales(festivo.getFecha(), fecha) || c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
                    return true;
            }
        }
        return false;
    }



    @Override
    public List<Festivo> listar() {
        return repositorio.findAll();
    }

    @Override
    public List<Festivo> obtener(int year) {
       
        throw new UnsupportedOperationException("Unimplemented method 'obtener'");
    }


    @Override
    public String verificar(int year, int month, int año) {
        
        throw new UnsupportedOperationException("Unimplemented method 'verificar'");
    }

    @Override
    public boolean esFestivo(Date fecha) {
        List<Festivo>festivos = repositorio.findAll();
        return esFestivo(festivos, fecha);
    }
    
}
