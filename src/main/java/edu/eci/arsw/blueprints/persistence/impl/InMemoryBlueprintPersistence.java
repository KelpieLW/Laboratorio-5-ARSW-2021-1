/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author hcadavid
 */
@Component
@Qualifier("inMemoryBlueprintPersistence")

public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final ConcurrentHashMap<Tuple<String,String>,Blueprint> blueprints=new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {

        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("Pedro_el_Escamoso", "Coral_Bueno",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);

        pts=new Point[]{new Point(10, 240),new Point(800, 250)};
        bp=new Blueprint("Arkantos", "Grecia",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);

        pts=new Point[]{new Point(99, 100),new Point(100, 200)};
        bp=new Blueprint("Pedro_el_Escamoso", "Papaya_magica",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);

        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint resultBlueprint = blueprints.get(new Tuple<>(author, bprintname));
        if (resultBlueprint== null){
            throw new BlueprintNotFoundException("Plano no encontrado");
        }
        return blueprints.get(new Tuple<>(author, bprintname));
    }
    @Override
    public ArrayList<Blueprint> getBluePrintsByAuthor(String author)throws BlueprintNotFoundException {
        ArrayList<Blueprint> blueprintsAuthor = new ArrayList<Blueprint>();
        boolean flagForNoAuthor = true;
        for (Map.Entry<Tuple<String, String>, Blueprint> bluePrintKey : blueprints.entrySet()){
            if(bluePrintKey.getKey().o1.equals(author)){
                blueprintsAuthor.add(bluePrintKey.getValue());
                flagForNoAuthor=false;
            }
        }
        if (flagForNoAuthor){
            throw new BlueprintNotFoundException("Autor no encontrado");
        }

        return blueprintsAuthor;
    }


    @Override
    public Set <Blueprint> getBluePrints(){
        Set<Blueprint> setOfBlueprints = new HashSet<Blueprint>();
        for (Map.Entry<Tuple<String, String>, Blueprint> bluePrintKey : blueprints.entrySet()){
            setOfBlueprints.add(bluePrintKey.getValue());
        }
        return setOfBlueprints;
    }
    @Override
    public void updateBlueprint (String author, String name, Blueprint bp) throws BlueprintPersistenceException{
        boolean flagForNotFound = true;

        if (blueprints.containsKey(new Tuple<>(author,name))){

            blueprints.remove(new Tuple<>(author,name));
            saveBlueprint(bp);
            flagForNotFound=false;
        }
        if (flagForNotFound){
            saveBlueprint(bp);
        }


    }
    
    
}
