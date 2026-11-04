package fr.xcompta.core.base.test.compte;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import fr.xcompta.core.base.Compte;

/**
 * Teste la détermination du numéro de classe associé à un compte.

 *
 * @author Fabrice Luneau
 */
class NumeroClasse {

	@ParameterizedTest
	@CsvSource({ "101, 1", "207, 2", "370, 3", "401, 4", "512, 5", "607, 6", "707, 7" })
	@DisplayName("Vérifie le numéro de classe d'un compte")
	void getNumeroClasse(String numero, byte numeroClasseAttendu) {

		Compte compte = new Compte(numero, "Compte de test");

		assertEquals(numeroClasseAttendu, compte.getNumeroClasse());
	}
}