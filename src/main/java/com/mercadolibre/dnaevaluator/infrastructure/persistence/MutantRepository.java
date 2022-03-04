package com.mercadolibre.dnaevaluator.infrastructure.persistence;

import com.mercadolibre.dnaevaluator.infrastructure.shared.dto.ResultMutantDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Interfaz que extiende de CrudRepository y nos permite realizar las diferentes operaciones en la BD
 **/
public interface MutantRepository extends CrudRepository<ResultMutantDto, Long> {

    @Query(value = "SELECT count(id) from ResultMutantDto where isMutant = :isMutant")
    int countByType(@Param("isMutant") boolean isMutant);

    ResultMutantDto findByDna(String dna);

}
