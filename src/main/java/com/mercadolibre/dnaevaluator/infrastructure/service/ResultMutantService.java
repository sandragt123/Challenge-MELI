package com.mercadolibre.dnaevaluator.infrastructure.service;
import com.mercadolibre.dnaevaluator.domain.model.Mutant;
import com.mercadolibre.dnaevaluator.domain.service.dependendy.IResultMutantSave;
import com.mercadolibre.dnaevaluator.infrastructure.persistence.MutantRepository;
import com.mercadolibre.dnaevaluator.infrastructure.shared.dto.ResultMutantDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.Date;

/**
 * ClassName: ResultMutantService
 * Clase repository que pertenece a la capa de infraestructura donde se orquesta toda la conexión tanto con  base de datos como con servicios REST o SOAP si fuese necesario
 */
@Repository
public class ResultMutantService implements IResultMutantSave {

    @Autowired
    private ModelMapper mapper;

    //Se inyecta dependencia con la interfaz MutantRepository que nos conecta con la base de datos
    @Autowired
    private MutantRepository mutantRepository;


    //
    /**
     * param : Mutant
     * Método que se encarga de guardar el resultado del ADN evaluado en la capa de servicio
     */
    @Override
    public void saveResultMutant(Mutant mutant) {
        //Se hace mapeo de la entidad de negocio Mutant a mutantDto, esto para evitar el uso de ésta por fuera de la capa de dominio
       ResultMutantDto mutantDto = mapper.map(mutant, ResultMutantDto.class);
       mutantDto.setDateCreate(new Date());
       mutantRepository.save(mutantDto);
    }

    /**
     * param : boolean
     * método que se encarga de contar en base de datos la cantidad de mutantes o humanos según el tipo de ADN que llegue como parámetro
     */
    @Override
    public long countResult(boolean isMutant) {
       return  mutantRepository.countByType(isMutant);
    }

    /**
     * param : String
     * método que se encarga de retornar el resultado de un ADN si se encuentra ya creado en base de datos
     */
    @Override
    public Mutant validateExistDna(String dna){
        ResultMutantDto mutantDto = mutantRepository.findByDna(dna);
        return mutantDto !=  null ? mapper.map(mutantRepository.findByDna(dna), Mutant.class) : null;
    }
}
