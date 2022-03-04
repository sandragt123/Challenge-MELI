package com.mercadolibre.dnaevaluator.domain.service;

import com.mercadolibre.dnaevaluator.domain.model.Mutant;
import com.mercadolibre.dnaevaluator.domain.model.Stats;
import com.mercadolibre.dnaevaluator.domain.service.dependendy.IResultMutantSave;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.when;

/**
 * ClassName
 **/
class MutantServiceTest {

    @Mock
    private IResultMutantSave resultMutantSave;

    @InjectMocks
    private MutantService mutantService;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void isMutantWhenDnaHasThreeMatches() {
        //Arrange
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTC"};
        when(resultMutantSave.validateExistDna(Arrays.toString(dna))).thenReturn(null);
        //Act
        boolean isMutant = mutantService.isMutant(dna);
        //Assert
        Assertions.assertTrue(isMutant);

    }

    @Test
    void isMutantWhenDnaHasTwoMatches() {
        //Arrange
        String[] dna = {"TTTTGA","CAGTTC","TTATGT","AGAAGG","CTGCAA","TCACTA"};
        when(resultMutantSave.validateExistDna(Arrays.toString(dna))).thenReturn(null);
        //Act
        boolean isMutant = mutantService.isMutant(dna);
        //Assert
        Assertions.assertTrue(isMutant);

    }
    @Test
    void isMutantWhenDnaHasDiangonalMatches() {
        //Arrange
        String[] dna = {"TGCAG","CAGGC","CCGCA","CGCTA","CCTTA"};
        when(resultMutantSave.validateExistDna(Arrays.toString(dna))).thenReturn(null);
        //Act
        boolean isMutant = mutantService.isMutant(dna);
        //Assert
        Assertions.assertTrue(isMutant);

    }

    @Test
    void isMutantTrueAndExistInBD(){
        //Arrange
        String[] dna = {"TTTTGA","CAGTTC","TTATGT","AGAAGG","CTGCAA","TCACTA"};
        Mutant mutant = new Mutant("", true);
        when(resultMutantSave.validateExistDna(Arrays.toString(dna))).thenReturn(mutant);
        //Act
        boolean isMutant = mutantService.isMutant(dna);
        //Assert
        Assertions.assertTrue(isMutant);

    }

    @Test
    void isMutantFalseAndExistInBD(){
        //Arrange
        String[] dna = {"TTGAGA","CAGTTC","TAATGT","TGCCGG","CAGCAT","TCACAT"};
        Mutant mutant = new Mutant("", false);
        when(resultMutantSave.validateExistDna(Arrays.toString(dna))).thenReturn(mutant);
        //Act
        boolean isMutant = mutantService.isMutant(dna);
        //Assert
        Assertions.assertFalse(isMutant);

    }

    @Test
    void isMutantFalseAndNotExistInBD(){
        //Arrange
        String[] dna = {"TTGAGA","CAGTTC","TAATGT","TGCCGG","CAGCAT","TCACAT"};
        when(resultMutantSave.validateExistDna(Arrays.toString(dna))).thenReturn(null);
        //Act
        boolean isMutant = mutantService.isMutant(dna);
        //Assert
        Assertions.assertFalse(isMutant);


    }

    @Test
    void getStats() {
        //Arrange
        when(resultMutantSave.countResult(false)).thenReturn(5L);
        when(resultMutantSave.countResult(true)).thenReturn(10L);
        //Act
        Stats stats = mutantService.getStats();
        //Assert
        Assertions.assertEquals(stats.getRatio(), 2.0);
        Assertions.assertEquals(stats.getCountHumanDna(), 5L);
        Assertions.assertEquals(stats.getCountMutantDna(), 10L);

    }

    @Test
    void getStatswithDecimals() {
        //Arrange
        when(resultMutantSave.countResult(false)).thenReturn(6L);
        when(resultMutantSave.countResult(true)).thenReturn(4L);
        //Act
        Stats stats = mutantService.getStats();
        //Assert
        Assertions.assertEquals(stats.getRatio(), 0.67);
        Assertions.assertEquals(stats.getCountHumanDna(), 6L);
        Assertions.assertEquals(stats.getCountMutantDna(), 4L);

    }
}