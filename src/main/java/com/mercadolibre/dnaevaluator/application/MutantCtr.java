package com.mercadolibre.dnaevaluator.application;

import com.mercadolibre.dnaevaluator.domain.service.MutantService;
import com.mercadolibre.dnaevaluator.infrastructure.shared.dto.DNASequenceDto;
import com.mercadolibre.dnaevaluator.infrastructure.shared.dto.StatsDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller qye se encuentra en la paca de aplicación y funciona como adaptador para acceder a la capa de dominio
 */
@RestController
@RequestMapping(value="api")
public class MutantCtr {

    //Inyección de dependencia para comunicarse con la capa de dominio
    @Autowired
    MutantService mutantService;

    @Autowired
    ModelMapper mapper;

    /**
     *
     * param dna
     * return status 403 si el adn es humano y 200 si el adn es mutante
     * Servicio rest
     **/
    @PostMapping(value = "/mutant")
    public ResponseEntity<String> isMutant( @RequestBody DNASequenceDto dna) {
        String[] dnaArray;
        try {
             dnaArray = dna.getDna();
        }catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        if (mutantService.isMutant(dnaArray)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**

     * param
     * return StatsDto
     * Servicio rest que retorna  las estadisticas de los ADN analizados
     **/
    @GetMapping(value = "/stats", produces =MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<StatsDto> getStats (){
        // Se realiza mapper del model Stats a StatsDTO, esto debido a que es la capa de aplicacion(adaptador) no  deben usar elementos de la casa de dominio
        return  ResponseEntity.ok(mapper.map(mutantService.getStats(), StatsDto.class));
    }
}
