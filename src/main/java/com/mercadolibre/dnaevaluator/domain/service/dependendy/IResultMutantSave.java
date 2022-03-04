package com.mercadolibre.dnaevaluator.domain.service.dependendy;


import com.mercadolibre.dnaevaluator.domain.model.Mutant;

/**
 * interfaz que se encarga de comunicar a la capa de dominio con la capa de infraestructura, la cual provee el acceso a datos
 */
public interface IResultMutantSave {

    void saveResultMutant(Mutant mutant);
    long countResult(boolean isMutant);
    Mutant validateExistDna(String dna);
}
