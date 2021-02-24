package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.persistence.impl.Filtro;
import edu.eci.arsw.blueprints.persistence.impl.FiltroRedundancias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service



public class FiltroServices {
    @Autowired
    @Qualifier("filtroRedundancias")
    Filtro redundancias;
    @Autowired
    @Qualifier("filtroSubMuestreo")
    Filtro subMuestreo;

    public Blueprint filterByRedundancy(Blueprint bp){
        return redundancias.eliminarPlano(bp);

    }
    public Blueprint filterBySubmuestre(Blueprint bp){
        return subMuestreo.eliminarPlano(bp);
    }

}
