package fr.xcompta.core.base.test.integration.ecriturevalidee;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementValidee;
import fr.xcompta.core.base.exception.XComptaCoreException;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;
import fr.xcompta.core.base.exception.ecriture.EcritureCompenseException;
import fr.xcompta.core.base.exception.ecriture.EcritureNbMouvementsInferieurADeuxException;
import fr.xcompta.core.base.exception.ecriture.EcritureNonEquilibreeException;
import fr.xcompta.core.base.exception.mouvement.MouvementAZeroException;
import fr.xcompta.core.base.exception.mouvement.MouvementException;
import fr.xcompta.core.base.test.integration.ecriturevalidee.JeuDonneesEcritureValidee.DonneesEcriture;
import fr.xcompta.core.base.test.integration.ecriturevalidee.JeuDonneesEcritureValidee.DonneesMouvement;

/**
 * Tests d'intégration des constructions invalides d'une
 * {@link EcritureValidee}.
 *
 * <p>
 * Une écriture validée doit respecter toutes ses contraintes dès sa
 * construction. Cette classe couvre les erreurs d'en-tête ainsi que les
 * principaux états invalides de sa collection de mouvements.
 * </p>
 *
 * <p>
 * Les scénarios comptables déjà présents dans l'ancien test d'intégration sont
 * conservés : mouvement à zéro, compensation, écriture non équilibrée,
 * écriture vide et écriture avec un seul mouvement.
 * </p>
 *
 * @author Fabrice Luneau
 */
class ConstructInvalide {

    private static final String SOURCE_ECRITURES =
            "fr.xcompta.core.base.test.integration.ecriturevalidee."
            + "JeuDonneesEcritureValidee#fournirEcritures";

    private static final String SOURCE_ENTETES_INVALIDES =
            "fr.xcompta.core.base.test.integration.ecriturevalidee."
            + "JeuDonneesInvalidesEcritureValidee#fournirEntetesInvalides";

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.DisplayName("Une écriture validée non persistée accepte id égal à zéro")
    void identifiantTechniqueNonAttribue() throws XComptaCoreException {
        final Journal journal = new Journal("OD", "Opérations diverses");
        final EcritureValidee ecriture = new EcritureValidee(
                0, 0, 0, "Écriture avant persistance", LocalDate.of(2026, 10, 21),
                journal, creerMouvementsValidesMinimaux());
        org.junit.jupiter.api.Assertions.assertEquals(0, ecriture.getId());
        org.junit.jupiter.api.Assertions.assertEquals(0, ecriture.getNumeroDePreEnregistrement());
        org.junit.jupiter.api.Assertions.assertEquals(0, ecriture.getNumeroEnregistrement());
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource(SOURCE_ENTETES_INVALIDES)
    @DisplayName("Refuse les en-têtes invalides")
    void enteteInvalide(
            final String description,
            final int id,
            final LocalDate date,
            final String codeJournal,
            final String libelleJournal,
            final String libelle,
            final Class<? extends Throwable> exceptionAttendue)
            throws MouvementException, XComptaCoreNullPointerException {

        final Journal journal = codeJournal == null
                ? null
                : new Journal(codeJournal, libelleJournal);

        final List<MouvementValidee> mouvements =
                creerMouvementsValidesMinimaux();

        assertThrows(
                exceptionAttendue,
                () -> new EcritureValidee(id, (id == 0 ? 0 : id + 1000), (id <= 0 ? 0 : id + 2000), libelle, date, journal, mouvements),
                description);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource(SOURCE_ECRITURES)
    @DisplayName("Refuse une écriture contenant un mouvement à zéro")
    void ecritureMouvementAZero(final DonneesEcriture donnees)
            throws XComptaCoreException {

        final List<MouvementValidee> mouvements = creerMouvements(donnees);
        final MouvementValidee mouvement = mouvements.get(0);

        assertThrows(
                MouvementAZeroException.class,
                () -> new MouvementValidee(
                        mouvement.getId(),
                        BigDecimal.ZERO,
                        mouvement.getCompte()));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource(SOURCE_ECRITURES)
    @DisplayName("Refuse deux mouvements utilisant le même compte")
    void ecritureMouvementCompensation(final DonneesEcriture donnees)
            throws XComptaCoreException {

        final DonneesMouvement source = donnees.mouvements().get(0);
        final Compte compte =
                new Compte(source.numeroCompte(), source.libelleCompte());

        final List<MouvementValidee> mouvements = new ArrayList<>();

        mouvements.add(
                new MouvementValidee(
                        source.id(),
                        new BigDecimal("100.00"),
                        compte));

        mouvements.add(
                new MouvementValidee(
                        source.id() + 10000,
                        new BigDecimal("-100.00"),
                        compte));

        assertThrows(
                EcritureCompenseException.class,
                () -> new EcritureValidee(donnees.id(), (donnees.id() == 0 ? 0 : donnees.id() + 1000), (donnees.id() <= 0 ? 0 : donnees.id() + 2000), donnees.libelle(), donnees.date(), creerJournal(donnees), mouvements));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource(SOURCE_ECRITURES)
    @DisplayName("Refuse une écriture non équilibrée")
    void ecritureNonEquilibree(final DonneesEcriture donnees)
            throws XComptaCoreException {

        final List<MouvementValidee> mouvements = creerMouvements(donnees);
        final DonneesMouvement source = donnees.mouvements().get(0);

        mouvements.set(
                0,
                new MouvementValidee(
                        source.id(),
                        new BigDecimal(source.montant())
                                .add(new BigDecimal("0.01")),
                        new Compte(
                                source.numeroCompte(),
                                source.libelleCompte())));

        assertThrows(
                EcritureNonEquilibreeException.class,
                () -> new EcritureValidee(donnees.id(), (donnees.id() == 0 ? 0 : donnees.id() + 1000), (donnees.id() <= 0 ? 0 : donnees.id() + 2000), donnees.libelle(), donnees.date(), creerJournal(donnees), mouvements));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource(SOURCE_ECRITURES)
    @DisplayName("Refuse une écriture sans mouvement")
    void ecritureVide(final DonneesEcriture donnees) {

        assertThrows(
                EcritureNbMouvementsInferieurADeuxException.class,
                () -> new EcritureValidee(donnees.id(), (donnees.id() == 0 ? 0 : donnees.id() + 1000), (donnees.id() <= 0 ? 0 : donnees.id() + 2000), donnees.libelle(), donnees.date(), creerJournal(donnees), List.of()));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource(SOURCE_ECRITURES)
    @DisplayName("Refuse une écriture avec un seul mouvement")
    void ecritureUnSeulMouvement(final DonneesEcriture donnees)
            throws MouvementException, XComptaCoreNullPointerException {

        final DonneesMouvement source = donnees.mouvements().get(0);
        final MouvementValidee mouvement = creerMouvement(source);

        assertThrows(
                EcritureNbMouvementsInferieurADeuxException.class,
                () -> new EcritureValidee(donnees.id(), (donnees.id() == 0 ? 0 : donnees.id() + 1000), (donnees.id() <= 0 ? 0 : donnees.id() + 2000), donnees.libelle(), donnees.date(), creerJournal(donnees), List.of(mouvement)));
    }

    @Test
    @DisplayName("Refuse une collection de mouvements null")
    void collectionMouvementsNull() {

        assertThrows(
                EcritureNbMouvementsInferieurADeuxException.class,
                () -> new EcritureValidee(1, 1001, 2001, "Écriture avec collection null", LocalDate.of(2026, 8, 19), new Journal("OD", "Opérations diverses"), null));
    }

    @Test
    @DisplayName("Refuse une collection contenant un mouvement null")
    void collectionContenantMouvementNull()
            throws MouvementException, XComptaCoreNullPointerException {

        final List<MouvementValidee> mouvements =
                new ArrayList<>(creerMouvementsValidesMinimaux());

        mouvements.set(1, null);

        assertThrows(
                XComptaCoreNullPointerException.class,
                () -> new EcritureValidee(1, 1001, 2001, "Écriture contenant un mouvement null", LocalDate.of(2026, 8, 19), new Journal("OD", "Opérations diverses"), mouvements));
    }

    /**
     * Construit le journal associé au jeu de données.
     *
     * @param donnees données de l'écriture
     * @return journal construit
     */
    private static Journal creerJournal(final DonneesEcriture donnees) {
        return new Journal(
                donnees.codeJournal(),
                donnees.libelleJournal());
    }

    /**
     * Reconstruit les mouvements validés détachés associés au jeu de données.
     *
     * @param donnees données de l'écriture
     * @return liste modifiable des mouvements validés
     * @throws MouvementException si un mouvement est invalide
     * @throws XComptaCoreNullPointerException 
     */
    private static List<MouvementValidee> creerMouvements(
            final DonneesEcriture donnees)
            throws MouvementException, XComptaCoreNullPointerException {

        final List<MouvementValidee> mouvements = new ArrayList<>();

        for (DonneesMouvement donneesMouvement : donnees.mouvements()) {
            mouvements.add(creerMouvement(donneesMouvement));
        }

        return mouvements;
    }

    /**
     * Reconstruit un mouvement validé détaché.
     *
     * @param donnees données du mouvement
     * @return mouvement validé
     * @throws MouvementException si les données du mouvement sont invalides
     * @throws XComptaCoreNullPointerException 
     */
    private static MouvementValidee creerMouvement(
            final DonneesMouvement donnees)
            throws MouvementException, XComptaCoreNullPointerException {

        return new MouvementValidee(
                donnees.id(),
                new BigDecimal(donnees.montant()),
                new Compte(
                        donnees.numeroCompte(),
                        donnees.libelleCompte()));
    }

    /**
     * Construit deux mouvements détachés valides et équilibrés.
     *
     * @return deux mouvements valides utilisant des comptes différents
     * @throws MouvementException si la construction d'un mouvement échoue
     * @throws XComptaCoreNullPointerException 
     */
    private static List<MouvementValidee> creerMouvementsValidesMinimaux()
            throws MouvementException, XComptaCoreNullPointerException {

        return List.of(
                new MouvementValidee(
                        1,
                        new BigDecimal("100.00"),
                        new Compte("512", "Banque")),
                new MouvementValidee(
                        2,
                        new BigDecimal("-100.00"),
                        new Compte("401", "Fournisseurs")));
    }
}
