package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/")
public class BlueprintAPIController {
    @Autowired
    @Qualifier("inMemoryBlueprintPersistence")
    BlueprintsPersistence bps;

    @GetMapping("/blueprints")
    public ResponseEntity<?> getAllBluePrints(){

        return new ResponseEntity<>(bps.getBluePrints(), HttpStatus.OK);
    }

    @GetMapping("/blueprints/{author}")
    public ResponseEntity<?> getAuthorBlueprints(@PathVariable(value="author") String author)throws ResourceNotFoundException{

        try {
            return new ResponseEntity<>(bps.getBluePrintsByAuthor(author), HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping("/blueprints/{author}/{name}")
    public ResponseEntity<?> getAuthorBlueprints(@PathVariable(value="author") String author,@PathVariable(value="name") String name)throws ResourceNotFoundException{

        try {
            return new ResponseEntity<>(bps.getBlueprint(author, name), HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            System.out.println("ACA");
            throw new ResourceNotFoundException();

        }
    }

    @PostMapping("/blueprints")
    public ResponseEntity<?> addBlueprint (@RequestBody Blueprint bp) throws ResourceDuplicatedException{
        try{
            bps.saveBlueprint(bp);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }
        catch (BlueprintPersistenceException e) {
            throw new ResourceDuplicatedException();
        }
    }

    @PutMapping("/blueprints/{author}/{name}")
    public ResponseEntity<?>  putBlueprint (@PathVariable(value="author") String author,@PathVariable(value="name") String name, @RequestBody Blueprint bp) throws BlueprintPersistenceException{
        bps.updateBlueprint(author, name, bp);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}