package fr.xcompta.core.base.test.compte;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.test.compte.JeuDonneesCompte.DonneesCompte;

/**
 * Tests paramétrés des constructions nominales de {@link Compte}.
 *
 * <p>
 * Le même jeu de données, issu du plan de comptes XML, est utilisé pour
 * vérifier le constructeur de {@code Compte}.
 * </p>
 *
 * <p>
 * Les tests contrôlent le numéro stocké, le libellé, la classe comptable,
 * le formatage sur six caractères.
 * </p>
 *
 * @author Fabrice Luneau
 */
class Construct2Arg {

    private static final String SOURCE =
            "fr.xcompta.core.base.test.compte."
            + "JeuDonneesCompte#fournirComptes";

    /**
     * Vérifie le constructeur à deux arguments.
     *
     * <p>
     * La construction conserve les informations comptables attendues.
     * </p>
     *
     * @param donnees données du compte à construire
     */
    @ParameterizedTest(name = "[{index}] compte {0}")
    @MethodSource(SOURCE)
    @DisplayName("Constructeur à deux arguments : construit des comptes du plan comptable")
    void constructeurDeuxArguments(final DonneesCompte donnees) {

        Compte compte = new Compte(
                donnees.numero(),
                donnees.libelle());

        verifierDonneesCommunes(compte, donnees);

    }

    /**
     * Vérifie les propriétés du compte construit.
     *
     * @param compte compte construit
     * @param donnees valeurs attendues
     */
    private static void verifierDonneesCommunes(
            final Compte compte,
            final DonneesCompte donnees) {

        assertEquals(
                donnees.numero(),
                compte.getNumero());

        assertEquals(
                donnees.libelle(),
                compte.getLibelle());

        assertEquals(
                donnees.numeroClasse(),
                compte.getNumeroClasse());

        assertEquals(
                donnees.numeroFormate6(),
                compte.getNumeroFormate(6));
    }
}
