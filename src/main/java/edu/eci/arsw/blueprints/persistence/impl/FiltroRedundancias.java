package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Qualifier("filtroRedundancias")
public class FiltroRedundancias implements Filtro{
    private List<Point> points=null;


    @Override
    public Blueprint eliminarPlano(Blueprint plano) {
        points=plano.getPoints();
        ArrayList<Point> evaluados= new ArrayList<Point>() ;
        Point evaluando=null;
        if(points.size()!=0){
            evaluando=points.get(0);
            evaluados.add(points.get(0));
            for(int i=0;i<points.size()-1;i++){
                if(!(points.get(i+1).getX()==evaluando.getX()) || !(points.get(i+1).getY()==evaluando.getY())){
                    evaluados.add(points.get(i+1));
                }
                evaluando=points.get(i+1);
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
