package fr.xcompta.core.base.exception.ecriture;

import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Exception de base pour toutes les exceptions relatives à une écriture comptable.
 *
 * <p>Une écriture est considérée comme valide si elle respecte les règles suivantes :</p>
 *
 * <ul>
 *   <li>
 *     Elle est équilibrée :
 *     <ul>
 *       <li>le total des débits est égal au total des crédits ;</li>
 *       <li>les totaux des débits et des crédits sont différents de zéro.</li>
 *     </ul>
 *   </li>
 *   <li>Elle comporte au moins deux mouvements.</li>
 *   <li>
 *     Chaque compte n'est utilisé qu'une seule fois dans l'écriture.
 *     Les mouvements utilisant un même compte doivent être regroupés.
 *   </li>
 *   <li>Chaque mouvement possède un montant différent de zéro.</li>
 *   <li>
 *     La date de l'écriture est valide :
 *     <ul>
 *       <li>elle est postérieure à la date de clôture ;</li>
 *       <li>elle appartient à l'exercice comptable en cours ;</li>
 *       <li>l'exercice comptable est ouvert.</li>
 *     </ul>
 *   </li>
 *   <li>L'écriture référence un journal.</li>
 * </ul>
 *
 * @author Fabrice Luneau
 */
public abstract class EcritureException extends XComptaCoreException {

    private static final long serialVersionUID = 1L;

    protected EcritureException() {
        super();
    }

    protected EcritureException(String message) {
        super(message);
    }
}
