package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Qualifier("filtroSubMuestreo")
public class FiltroSubMuestreo implements Filtro {
    List<Point> points;
    @Override
    public Blueprint eliminarPlano(Blueprint plano) {
        points=plano.getPoints();
        ArrayList<Point> evaluados=new ArrayList<Point>();
        boolean bandera=true;
        if(points.size()!=0){
            for (int i=0;i<points.size();i++){
                if(bandera){
                    evaluados.add(points.get(i));

                }
                bandera=!bandera;
            }
        }
        Point [] convertir=cambiarTipo(evaluados);
        plano.setPoints(Arrays.asList(convertir));
        return plano;
    }

    public Point [] cambiarTipo(ArrayList<Point> evaluados){
        Point[] envio=new Point[evaluados.size()];
        for(int i=0;i<evaluados.size();i++){
            envio[i]=evaluados.get(i);
        }
        return envio;
    }

}
