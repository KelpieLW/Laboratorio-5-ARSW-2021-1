package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.impl.FiltroRedundancias;
import edu.eci.arsw.blueprints.persistence.impl.FiltroSubMuestreo;
import edu.eci.arsw.blueprints.services.FiltroServices;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FiltroTest {
    private ApplicationContext classPaht;
    private FiltroSubMuestreo filtroSubMuestreo;
    private FiltroRedundancias filtroRedundancias;

    private FiltroServices rara;
    @Before
    public void setUp() {
        classPaht = new ClassPathXmlApplicationContext("applicationContext.xml");
        rara = classPaht.getBean(FiltroServices.class);
    }

    @Test
    public void TestFiltroRedundancias() throws BlueprintNotFoundException {
        List<Blueprint> bluePrints=new ArrayList<Blueprint>();
        Point[] pts0=new Point[]{new Point(10, 10),new Point(10,10),new Point(10,1)};
        Blueprint nuevo=new Blueprint("Allan","La raja en el espejo",pts0);
        bluePrints.add(nuevo);
        rara.filterByRedundancy(nuevo);
        Point[] evaluar=new Point[]{new Point(10, 10),new Point(10,1)};
        for (int i=0;i<evaluar.length;i++){
            assertEquals(evaluar[i].getX(),rara.filterByRedundancy(nuevo).getPoints().get(i).getX());
        }
    }
    @Test
    public void TestSubMuestreo(){
        List<Blueprint> bluePrints=new ArrayList<Blueprint>();
        Point[] pts1=new Point[]{new Point(5, 1),new Point(10,10),new Point(10,1)};
        Blueprint nuevo1=new Blueprint("Allan","La raja en el espejo",pts1);
        bluePrints.add(nuevo1);
        rara.filterBySubmuestre(nuevo1);
        Point[] evaluar=new Point[]{new Point(5, 1),new Point(10,1)};
        for (int i=0;i<evaluar.length;i++){
            assertEquals(evaluar[i].getX(),rara.filterByRedundancy(nuevo1).getPoints().get(i).getX());
        }
    }
}
