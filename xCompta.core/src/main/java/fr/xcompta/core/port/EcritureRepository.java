package fr.xcompta.core.port;

import java.util.List;
import java.util.Optional;

import fr.xcompta.core.base.Ecriture;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Définit le contrat de persistance des écritures comptables.
 *
 * <p>
 * Ce port est indépendant de toute technologie de persistance.
 * Il permet de gérer les écritures de brouillard et les écritures validées.
 * </p>
 *
 * <p>
 * Chaque écriture persistée possède un identifiant technique interne,
 * distinct de ses numéros métier.
 * </p>
 *
 * <p>
 * Une écriture reçoit un numéro de préenregistrement lors de sa première
 * persistance. Ce numéro est conservé après validation.
 * Une écriture validée reçoit également un numéro d'enregistrement définitif.
 * </p>
 *
 * <p>
 * Seules les écritures de brouillard peuvent être modifiées ou supprimées.
 * Une écriture validée est définitive et immuable.
 * </p>
 *
 * <p>
 * Les recherches portant sur plusieurs écritures relèvent du port
 * {@link Extractable}.
 * </p>
 *
 * @author Fabrice Luneau
 */
public interface EcritureRepository {

    /**
     * Recherche une écriture par son numéro de préenregistrement.
     *
     * <p>
     * Ce numéro est commun aux écritures de brouillard et aux écritures
     * validées. Il est distinct de l'identifiant technique de persistance.
     * </p>
     *
     * @param numeroPreEnregistrement numéro de préenregistrement recherché
     * @return l'écriture correspondante, ou Optional.empty()
     *         si aucune écriture ne correspond
     */
    Optional<Ecriture<?>> get(int numeroPreEnregistrement);

    /**
     * Recherche une écriture validée par son numéro définitif.
     *
     * @param numeroValidation numéro d'enregistrement définitif
     * @return l'écriture validée correspondante, ou Optional.empty()
     *         si aucune écriture ne correspond
     */
    Optional<EcritureValidee> getEcritureValidee(int numeroValidation);

    /**
     * Préenregistre une nouvelle écriture de brouillard.
     *
     * <p>
     * Le numéro de préenregistrement doit être égal à zéro.
     * Dans le cas contraire, l'opération est rejetée et l'appelant
     * doit utiliser {@link #modifierEcriture(EcritureDeBrouillard)}.
     * </p>
     *
     * <p>
     * L'opération attribue un identifiant technique interne et un numéro
     * de préenregistrement. Seul le numéro de préenregistrement est retourné.
     * </p>
     *
     * <p>
     * TODO : définir les contrôles applicables au préenregistrement
     * d'un brouillard incomplet.
     * </p>
     *
     * <p>
     * TODO : définir les modalités d'attribution des numéros,
     * notamment en cas de concurrence ou de rollback.
     * </p>
     *
     * @param ecriture nouvelle écriture de brouillard
     * @return numéro de préenregistrement attribué
     */
    int preenregistrerNouvelleEcriture(EcritureDeBrouillard ecriture);

    /**
     * Modifie une écriture de brouillard préenregistrée.
     *
     * <p>
     * L'identifiant technique et le numéro de préenregistrement
     * doivent être différents de zéro.
     * Une écriture validée ne peut jamais être modifiée.
     * </p>
     *
     * <p>
     * TODO : définir le comportement si le brouillard n'existe plus
     * ou a déjà été validé.
     * </p>
     *
     * <p>
     * TODO : préciser les contrôles d'identité entre l'objet fourni
     * et l'écriture persistée.
     * </p>
     *
     * @param ecriture écriture de brouillard modifiée
     * @return true si la modification a été effectuée, false sinon
     */
    boolean modifierEcriture(EcritureDeBrouillard ecriture);

    /**
     * Crée directement une nouvelle écriture validée.
     *
     * <p>
     * L'écriture source peut être :
     * </p>
     *
     * <ul>
     * <li>une écriture de brouillard non préenregistrée ;</li>
     * <li>une écriture validée utilisée comme modèle.</li>
     * </ul>
     *
     * <p>
     * L'écriture source doit être conforme aux règles métier
     * permettant la création d'une écriture validée.
     * Elle n'est jamais modifiée par cette opération.
     * </p>
     *
     * <p>
     * La nouvelle écriture reçoit un identifiant technique,
     * un numéro de préenregistrement et un numéro définitif
     * qui lui sont propres.
     * </p>
     *
     * <p>
     * La création et la persistance doivent être atomiques.
     * En cas d'échec, aucune nouvelle écriture ne doit être conservée.
     * </p>
     *
     * <p>
     * TODO : refuser une écriture de brouillard déjà préenregistrée.
     * </p>
     *
     * <p>
     * TODO : préciser les modalités d'attribution des identifiants
     * et des numéros métier.
     * </p>
     *
     * <p>
     * TODO : définir les exceptions en cas d'échec de validation
     * ou de persistance.
     * </p>
     *
     * @param ecriture écriture source à utiliser pour la création
     * @return nouvelle écriture validée et persistée
     */
    EcritureValidee creerEcritureValidee(Ecriture<?> ecriture);

    /**
     * Valide une écriture de brouillard déjà préenregistrée.
     *
     * <p>
     * L'opération vérifie la conformité de l'écriture avec les règles
     * métier du noyau, puis construit une nouvelle écriture validée.
     * </p>
     *
     * <p>
     * La validation comprend les étapes suivantes :
     * </p>
     *
     * <ol>
     * <li>Vérifier la conformité de l'écriture de brouillard.</li>
     * <li>Créer une écriture validée à partir de son en-tête.</li>
     * <li>Copier les mouvements sous forme de mouvements validés.</li>
     * <li>Rattacher les mouvements à la nouvelle écriture validée.</li>
     * <li>Conserver le numéro de préenregistrement.</li>
     * <li>Attribuer un numéro d'enregistrement définitif.</li>
     * <li>Persister la nouvelle écriture validée.</li>
     * <li>Supprimer l'écriture de brouillard initiale.</li>
     * </ol>
     *
     * <p>
     * L'ensemble de ces opérations doit être transactionnel et atomique.
     * Tout échec doit provoquer l'annulation complète de la transaction.
     * </p>
     *
     * <p>
     * TODO : préciser le devenir de l'identifiant technique
     * lors du remplacement du brouillard.
     * </p>
     *
     * <p>
     * TODO : définir les exceptions en cas d'échec de validation
     * ou de persistance.
     * </p>
     *
     * @param ecriture écriture de brouillard préenregistrée à valider
     * @return écriture validée et persistée
     */
    EcritureValidee validerEcriture(EcritureDeBrouillard ecriture);

    /**
     * Valide plusieurs écritures de brouillard.
     *
     * <p>
     * La liste contient les écritures sélectionnées.
     * La sélection peut porter sur une période, une partie du brouillard
     * ou sa totalité. Les critères de sélection relèvent du port
     * {@link Extractable}.
     * </p>
     *
     * <p>
     * L'opération doit être transactionnelle.
     * </p>
     *
     * <p>
     * TODO : déterminer si la transaction est globale (tout ou rien)
     * ou si des validations partielles sont autorisées.
     * </p>
     *
     * <p>
     * TODO : remplacer le retour void par un résultat détaillé
     * permettant de confirmer les validations et de signaler
     * les éventuels échecs.
     * </p>
     *
     * @param brouillard écritures de brouillard à valider
     */
    void validerBrouillard(List<EcritureDeBrouillard> brouillard);

    /**
     * Supprime une écriture de brouillard préenregistrée.
     *
     * <p>
     * Une écriture validée ne peut jamais être supprimée.
     * L'adaptateur doit vérifier que l'écriture persistée
     * est toujours une écriture de brouillard.
     * </p>
     *
     * <p>
     * TODO : définir le comportement si l'écriture n'existe plus
     * ou a déjà été validée.
     * </p>
     *
     * <p>
     * TODO : préciser les contrôles d'identité entre l'objet fourni
     * et l'écriture persistée.
     * </p>
     *
     * @param ecritureDeBrouillard écriture de brouillard à supprimer
     * @return true si la suppression a été effectuée, false sinon
     */
    boolean supprimer(EcritureDeBrouillard ecritureDeBrouillard);
    

    /**
     * Crée et enregistre directement la contrepassation
     * d'une écriture validée.
     *
     * <p>
     * La contrepassation inverse les mouvements comptables :
     * les débits deviennent des crédits et inversement.
     * L'écriture d'origine reste inchangée.
     * </p>
     *
     * <p>
     * La nouvelle écriture possède sa propre date comptable,
     * correspondant à la date de contrepassation.
     * </p>
     *
     * <p>
     * La contrepassation est enregistrée directement comme
     * écriture validée, sans passage par le brouillard.
     * Elle reçoit un nouvel identifiant technique,
     * un nouveau numéro de préenregistrement et un nouveau
     * numéro d'enregistrement définitif.
     * </p>
     *
     * <p>
     * Cette opération ne concerne que les écritures validées.
     * La contrepassation d'un brouillard reste manuelle.
     * Aucun traitement par lot n'est prévu.
     * </p>
     *
     * <p>
     * TODO : permettre ultérieurement de relier une écriture
     * à une ou plusieurs écritures associées, notamment
     * pour retrouver ses contrepassations.
     * </p>
     *
     * @param ecritureValidee écriture validée à contrepasser
     * @return nouvelle écriture de contrepassation,
     *         validée et persistée
     * @throws XComptaCoreException 
     */
    default EcritureValidee contrepasser(
            EcritureValidee ecritureValidee) throws XComptaCoreException {

        EcritureDeBrouillard contrepassation =
                ecritureValidee.contrepasser();

        return creerEcritureValidee(contrepassation);
    }

    
}
