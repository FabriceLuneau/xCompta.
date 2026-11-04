package fr.xcompta.core.base;

import java.time.LocalDate;

import fr.xcompta.core.base.exception.XComptaCoreIllegalArgumentException;
import fr.xcompta.core.base.exception.XComptaCoreIllegalStateException;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;

/**
 * Représente un exercice comptable défini par une période et un état d'ouverture.
 *
 * <p>Les dates de début et de fin sont explicites et incluses dans l'exercice.
 * Un exercice dure généralement un an, mais sa durée n'est pas imposée.
 * L'appartenance d'une écriture à un exercice se détermine par sa date :
 * aucune relation de composition avec les écritures ou les mouvements n'est nécessaire.</p>
 *
 * <p>Les exercices sont numérotés chronologiquement à partir de zéro :
 * le premier porte l'identifiant {@code 0}, le suivant {@code 1}, etc.
 * La cohérence de cette numérotation et l'absence de chevauchement entre exercices
 * relèvent du composant chargé de les gérer.</p>
 *
 * <p>Un exercice peut être ouvert ou fermé temporairement. Une clôture définitive
 * enregistre une date de clôture et interdit toute réouverture.</p>
 *
 * <p>Plusieurs exercices peuvent être ouverts simultanément. L’exercice sélectionné
 * par l’application sert uniquement de contexte pour proposer les dates des saisies,
 * recherches et états comptables. Cette sélection n’est pas une propriété de
 * l’exercice et ne limite pas les opérations aux dates de celui-ci.</p>
 *
 * <p>L’enregistrement d’une écriture est autorisé si sa date appartient à un
 * exercice ouvert. La recherche de l’exercice correspondant et le contrôle
 * effectif lors de l’enregistrement relèvent du service applicatif.</p>
 *
 * @author Fabrice LUNEAU
 */
public class Exercice {

    private final int id;
    private final LocalDate debut;
    private final LocalDate fin;
    private LocalDate dateCloture;
    private boolean ouvert = true;

    /**
     * Construit le premier exercice, d'identifiant {@code 0}.
     *
     * @param debut date de début incluse
     * @param fin date de fin incluse
     * @throws XComptaCoreNullPointerException si l'une des dates est {@code null}
     * @throws XComptaCoreIllegalArgumentException si la fin précède le début
     */
    public Exercice(final LocalDate debut, final LocalDate fin)
            throws XComptaCoreIllegalArgumentException, XComptaCoreNullPointerException {
        this(0, debut, fin);
    }

    /**
     * Construit un exercice avec son identifiant chronologique.
     *
     * @param id identifiant de l'exercice, supérieur ou égal à zéro
     * @param debut date de début incluse
     * @param fin date de fin incluse
     * @throws XComptaCoreIllegalArgumentException si l'identifiant est négatif ou si la fin précède le début
     * @throws XComptaCoreNullPointerException si l'une des dates est {@code null}
     */
    public Exercice(final int id, final LocalDate debut, final LocalDate fin)
            throws XComptaCoreIllegalArgumentException, XComptaCoreNullPointerException {
        if (id < 0) {
            throw new XComptaCoreIllegalArgumentException("L'identifiant de l'exercice ne peut pas être négatif.");
        }
        if (debut == null) {
            throw new XComptaCoreNullPointerException("Exercice : la date de début ne peut pas être null.");
        }
        this.debut = debut;
        if (fin == null) {
            throw new XComptaCoreNullPointerException("Exercice : la date de fin ne peut pas être null.");
        }
        this.fin = fin;
        if (fin.isBefore(debut)) {
            throw new XComptaCoreIllegalArgumentException("La date de fin ne peut pas précéder la date de début.");
        }
        this.id = id;
    }

    /**
     * @return identifiant chronologique de l'exercice (à partir de zéro)
     */
    public int getId() {
        return id;
    }

    /**
     * @return date de début incluse de l'exercice
     */
    public LocalDate getDateDebut() {
        return debut;
    }

    /**
     * @return date de fin incluse de l'exercice, fournie lors de la construction
     */
    public LocalDate getDateFin() {
        return fin;
    }

    /**
     * @return date de clôture définitive, ou {@code null} si l'exercice n'est pas clôturé
     */
    public LocalDate getDateCloture() {
        return dateCloture;
    }

    /**
     * Indique si l'exercice est actuellement ouvert.
     * Un exercice clôturé définitivement est toujours fermé.
     *
     * @return {@code true} si l'exercice est ouvert
     */
    public boolean isOuvert() {
        return ouvert;
    }

    /**
     * Alias de {@link #isOuvert()}, conservé pour compatibilité.
     *
     * @return {@code true} si l'exercice est ouvert
     */
    public boolean estOuvert() {
        return isOuvert();
    }

    /**
     * Ouvre ou ferme temporairement l'exercice.
     * La fermeture temporaire ne renseigne pas la date de clôture.
     * Un exercice définitivement clôturé ne peut pas être rouvert.
     *
     * @param ouvert {@code true} pour ouvrir, {@code false} pour fermer
     * @throws XComptaCoreIllegalStateException si une réouverture est demandée après clôture définitive
     */
    public void setOuvert(final boolean ouvert) throws XComptaCoreIllegalStateException {
        if (ouvert && isCloture()) {
            throw new XComptaCoreIllegalStateException("Un exercice définitivement clôturé ne peut pas être rouvert.");
        }
        this.ouvert = ouvert;
    }

    /**
     * Indique si l'exercice est définitivement clôturé.
     *
     * @return {@code true} si une date de clôture définitive est enregistrée
     */
    public boolean isCloture() {
        return dateCloture != null;
    }

    /**
     * Clôture définitivement l'exercice à la date indiquée.
     * La date de clôture correspond à l'opération de clôture, et non
     * nécessairement à la date de fin de l'exercice.
     * Cette opération est irréversible.
     *
     * @param dateCloture date de la clôture définitive
     * @throws XComptaCoreNullPointerException si la date est {@code null}
     * @throws XComptaCoreIllegalStateException si l'exercice est déjà clôturé
     */
    public void cloturer(final LocalDate dateCloture)
            throws XComptaCoreIllegalStateException, XComptaCoreNullPointerException {
        if (dateCloture == null) {
            throw new XComptaCoreNullPointerException("Exercice : la date de clôture ne peut pas être null.");
        }
        if (isCloture()) {
            throw new XComptaCoreIllegalStateException("L'exercice est déjà définitivement clôturé.");
        }
        this.dateCloture = dateCloture;
        this.ouvert = false;
    }

    /**
     * Détermine si une date appartient à la période de l'exercice,
     * bornes de début et de fin incluses, indépendamment de son état d'ouverture.
     *
     * @param date date à examiner, éventuellement {@code null}
     * @return {@code true} si la date est comprise entre le début et la fin ;
     *         {@code false} si elle est absente ou hors période
     */
    public boolean appartient(final LocalDate date) {
        return date != null && !date.isBefore(debut) && !date.isAfter(fin);
    }

    /**
     * Indique si une écriture datée peut être enregistrée dans cet exercice.
     * L'exercice doit être ouvert et la date doit appartenir à sa période.
     * Cette vérification est indépendante de l'exercice sélectionné dans l'interface.
     *
     * @param dateEcriture date de l'écriture, éventuellement {@code null}
     * @return {@code true} si l'exercice est ouvert et contient cette date ;
     *         {@code false} dans tous les autres cas
     */
    public boolean autoriseEnregistrement(final LocalDate dateEcriture) {
        return isOuvert() && appartient(dateEcriture);
    }
}
