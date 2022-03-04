package com.mercadolibre.dnaevaluator.domain.service;

import com.mercadolibre.dnaevaluator.domain.model.Mutant;
import com.mercadolibre.dnaevaluator.domain.model.Stats;
import com.mercadolibre.dnaevaluator.domain.service.dependendy.IResultMutantSave;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassName: MutantService
 * Clase service que pertenece a la capa de dominio donde se maneja exlusivamente la lógica de negocio
 * Clase que se encarga de manejar toda la lógica para evaluar si un AND ingresado pertenece a una humano o mutante
 */
@Service
public class MutantService {

    private static final String dnaHorizontalPattern = "(.*)(A{4,}|T{4,}|C{4,}|G{4,})(.*)";
    private int countMatches;

    IResultMutantSave resultMutantSave;

    /**
     * Param: IResultMutantSave dnaRepository
     * Inyección de dependencia por constructor, no se usa anotación @AutoWired para evitar dependencias con  el framework en la capa de dominio(lógica de negocio)
     * la interface IResultMutantSave nos conecta con la capa de infraestructura la cual provee el acceso a datos, o integración con servicios externos si fuese necesario
     */
    public MutantService(IResultMutantSave dnaRepository){
        this.resultMutantSave = dnaRepository;
    }

    /**
     * param : String[]
     * return: boolean
     * Método de acceso público donde se valida si el ADN  ingresado pertenece a un humano o  mutante,
     */
    public boolean isMutant(String[] dna) {

        //Se realiza busqueda en base de datos para validar si ya fue evaluado el dna, si es así retorna la información que se encuentra en BD
        Mutant mutant = resultMutantSave.validateExistDna(Arrays.toString(dna));

        if ( mutant == null) {
            countMatches = 0;
            //se evalua si existe coincidencias horizontales, si existen más de 1 entonces guarda en base de datos y hace el retorno de la respuesta al controller
            if (evaluateHorizontalPattern(dna) > 1) {
                resultMutantSave.saveResultMutant(new Mutant(Arrays.toString(dna), true));
                return true;
            }
            //Evalua coincidencias Verticales
            else if (evaluateVerticalPattern(dna) > 1) {
                resultMutantSave.saveResultMutant(new Mutant(Arrays.toString(dna), true));
                return true;
             //Evalua coincidencias diagonales
            } else if (evaluateDiagonal(dna) > 1) {
                resultMutantSave.saveResultMutant(new Mutant(Arrays.toString(dna), true));
                return true;
            }

            resultMutantSave.saveResultMutant(new Mutant(Arrays.toString(dna), false));
            return false;

        }else{
            //Si ya existe el dna recibido, retorna boolean consultado en base de datos
            return mutant.isMutant();
        }
    }


    /**
     * return: Stats
     * Método de acceso público donde se calcula las estadisticas sacando el promedio de mutantes,
     */
    public Stats getStats(){

        DecimalFormat decimalFormat = new DecimalFormat("###.#");
        long dnaHuman = resultMutantSave.countResult(false);
        long dnaMutant = resultMutantSave.countResult(true);
        double ratio = (dnaHuman == 0.0) ? 0.0 : (double)dnaMutant / dnaHuman;

        /* Retorna model stats. Se multiplica ratio * 100 y se divide por 100 para redondear decimales */
        return new Stats(dnaMutant, dnaHuman,Math.round(ratio *100.0)/100.0);
    }

    /**
     * param : String[]
     * return: int
     * Método de acceso privado, se encarga de evaluar si existen coincidencias horizontales revisando los matches del DNA con la expresion regular (.*)(A{4,}|T{4,}|C{4,}|G{4,})(.*)
     */
    private int evaluateHorizontalPattern(String[] dna){
        long countStreamDna = Arrays.stream(dna)
        .filter(x -> x.matches(dnaHorizontalPattern)).count();

        countMatches += countStreamDna;
        return countMatches;
    }

    /**
     * param : String[]
     * return: int
     * Método de acceso privado,  convierte el array dna en una matriz y esta se invierte para posteriormente evaluar coincidencias con el método evaluateHorizontalPattern()
     */
    private int evaluateVerticalPattern( String[] dna){
        //Se invierte las posiciones de cada string para buscar coincicencias de forma horizontal
        List<String> list = new ArrayList<>();
        for (int j = 0; j < dna.length; j++) {
            int i = j;
            String strings = Arrays.stream(dna).map(s -> String.valueOf(s.charAt(i)))
                    .collect(Collectors.joining());
            list.add(strings);
        }
        return  evaluateHorizontalPattern(list.toArray(new String[0]));

    }

    /**
     * param : String[]
     * return: int
     * Método de acceso privado, Recorre el array dna y busca las coincidencias en dirección diagonal izquierda y diagonal derecha
     */
    private int  evaluateDiagonal( String[] dna) {
        for (int i = 0; i < dna.length; i++) {
            for (int j = 0; j < dna[i].length(); j++) {
                String base = String.valueOf(dna[i].charAt(j));

                //Se verifica coincidencias de izquierda a derecha
                if (i < dna.length - 3 && j < dna[i].length() - 3) {
                    if (base.equals(String.valueOf(dna[i + 1].charAt(j + 1))) && base.equals(String.valueOf(dna[i + 2].charAt(j + 2))) && base.equals(String.valueOf(dna[i + 3].charAt(j + 3)))) {
                        countMatches++;
                    }
                }
                //Se verifica coincidencias de derecha a izquierda
                if (i > dna.length - 3 && j < dna[i].length() - 3) {
                    if (base.equals(String.valueOf(dna[i - 1].charAt(j + 1))) && base.equals(String.valueOf(dna[i - 2].charAt(j + 2))) && base.equals(String.valueOf(dna[i - 3].charAt(j + 3)))) {
                        countMatches++;
                    }
                }
            }
        }
        return countMatches;
    }
}
