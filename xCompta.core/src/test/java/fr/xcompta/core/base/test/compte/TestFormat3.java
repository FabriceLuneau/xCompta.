package fr.xcompta.core.base.test.compte;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import fr.xcompta.core.base.Compte;

/**
 * Teste le wrapper d'instance {@link Compte#getNumeroFormate(int)}.
 *
 * <p>
 * Les tests vérifient le formatage sur six caractères de comptes de longueurs
 * différentes ainsi que l'absence de troncature lorsque le numéro dépasse la
 * longueur demandée.
 * </p>
 *
 * @author Fabrice Luneau
 */
class TestFormat3 {
	@ParameterizedTest
	@CsvSource({
            "101, Capital, 101000",
            "164, Emprunts, 164000",
            "218, Autres immobilisations, 218000",
            "370, Stocks de marchandises, 370000",
            "401, Fournisseurs, 401000",
            "411, Clients, 411000",
            "44566, TVA déductible, 445660",
            "512, Banque, 512000",
            "530, Caisse, 530000",
            "606, Achats non stockés, 606000",
            "607, Achats de marchandises, 607000",
            "60700, Achats de marchandises détaillé, 607000",
            "706, Prestations de services, 706000",
            "707, Ventes de marchandises, 707000",
            "70700, Ventes de marchandises détaillé, 707000",
            "707001, Ventes magasin 1, 707001",
            "7070012, Ventes magasin 12, 7070012"
    })
	@DisplayName("Vérifie le formatage du numéro de plusieurs comptes")
	void getNumeroFormate(final String numero, final String libelle, final String numeroAttendu) {
		final Compte compte = new Compte(numero, libelle);
		assertEquals(numeroAttendu, compte.getNumeroFormate(6));
	}
}