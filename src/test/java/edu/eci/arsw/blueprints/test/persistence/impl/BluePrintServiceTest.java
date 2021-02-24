package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.FiltroRedundancias;
import edu.eci.arsw.blueprints.persistence.impl.FiltroSubMuestreo;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BluePrintServiceTest {

    private ApplicationContext classPaht;
    private BlueprintsServices bluePrintServices;

    private BlueprintsServices rara;

    @Before
    public void setUp() {
        classPaht = new ClassPathXmlApplicationContext("applicationContext.xml");
        rara = classPaht.getBean(BlueprintsServices.class);
    }
    @Test
    public void registrarPlano() throws BlueprintNotFoundException {
        List<Blueprint>bluePrints=new ArrayList<Blueprint>();
        Point[] pts0=new Point[]{new Point(10, 10),new Point(10,10),new Point(10,1)};
        Blueprint nuevo=new Blueprint("Allan","La raja en el espejo",pts0);
        Blueprint nuevo1=new Blueprint("Santiago","hola",pts0);
        Blueprint nuevo2=new Blueprint("jose","chao",pts0);
        Blueprint nuevo3=new Blueprint("nico","lll",pts0);
        bluePrints.add(nuevo);
        bluePrints.add(nuevo1);
        bluePrints.add(nuevo2);
        bluePrints.add(nuevo3);
        FiltroSubMuestreo filtroSubMuestreo=new FiltroSubMuestreo();
        filtroSubMuestreo.eliminarPlano(nuevo);
        System.out.println(nuevo.getPoints().size());
        try{
            rara.addNewBlueprint(nuevo);
            rara.addNewBlueprint(nuevo1);
            rara.addNewBlueprint(nuevo2);
            rara.addNewBlueprint(nuevo3);
        } catch (BlueprintPersistenceException e) {
            e.printStackTrace();
        }
        assertEquals("["+nuevo.toString()+"]", rara.getBlueprintsByAuthor("Allan").toString());
        assertEquals("["+nuevo1.toString()+"]", rara.getBlueprintsByAuthor("Santiago").toString());
        assertEquals("["+nuevo2.toString()+"]", rara.getBlueprintsByAuthor("jose").toString());
        assertEquals("["+nuevo3.toString()+"]", rara.getBlueprintsByAuthor("nico").toString());
    }
}
