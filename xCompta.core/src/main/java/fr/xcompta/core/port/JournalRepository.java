
package fr.xcompta.core.port;

import java.util.List;
import java.util.Optional;

import fr.xcompta.core.base.Journal;

/**
 * Définit le contrat de persistance des journaux comptables.
 *
 * <p>
 * Cette interface expose les opérations de création, de consultation, de
 * suppression et de renommage des journaux.
 * </p>
 *
 * <p>
 * Elle est indépendante de toute technologie de persistance. Les adaptateurs
 * doivent respecter les règles définies ici, notamment les contraintes de
 * validation et de suppression.
 * </p>
 *
 * <p>
 * Seule la modification du libellé d'un journal est autorisée. Son code
 * constitue un identifiant immuable.
 * </p>
 *
 * @author Fabrice Luneau
 */
public interface JournalRepository {

	/**
	 * Recherche un journal à partir de son code.
	 *
	 * @param code code du journal recherché
	 * @return un Optional contenant le journal trouvé, ou un Optional vide si aucun
	 *         journal ne correspond
	 */
	Optional<Journal> get(String code);

	/**
	 * Retourne l'ensemble des journaux comptables.
	 *
	 * <p>
	 * Aucun critère de filtrage ni de pagination n'est nécessaire. Le nombre de
	 * journaux est généralement inférieur à dix.
	 * </p>
	 *
	 * @return la liste des journaux, éventuellement vide, mais jamais null
	 */
	List<Journal> getAll();

	/**
	 * Crée et persiste un nouveau journal comptable.
	 *
	 * <p>
	 * Le journal doit satisfaire les règles de validation définies dans le noyau
	 * métier avant sa persistance.
	 * </p>
	 *
	 * <p>
	 * L'implémentation doit refuser la création si un journal possédant le même
	 * code existe déjà.
	 * </p>
	 *
	 * @param journal journal à créer
	 * @return le journal créé et persisté
	 * @throws IllegalArgumentException si journal est null
	 */
	Journal creer(Journal journal);

	/**
	 * Supprime un journal identifié par son code.
	 *
	 * <p>
	 * La suppression est autorisée uniquement si le journal ne contient aucune
	 * écriture comptable.
	 * </p>
	 *
	 * <p>
	 * Un journal contenant au moins une écriture validée ne peut pas être supprimé.
	 * </p>
	 *
	 * <p>
	 * Si le journal contient des écritures de brouillard, celles-ci doivent être
	 * supprimées explicitement avant la suppression du journal.
	 * </p>
	 *
	 * <p>
	 * Aucune suppression en cascade des écritures n'est autorisée.
	 * </p>
	 *
	 * @param code code du journal à supprimer
	 */
	void supprimer(String code);

	/**
	 * Supprime un journal comptable.
	 *
	 * <p>
	 * Cette méthode respecte exactement les mêmes contraintes que
	 * {@link #supprimer(String)}.
	 * </p>
	 *
	 * <p>
	 * Le journal ne doit contenir aucune écriture comptable. Aucune suppression en
	 * cascade n'est autorisée.
	 * </p>
	 *
	 * @param journal journal à supprimer
	 * @throws IllegalArgumentException si journal est null
	 */
	void supprimer(Journal journal);

    /**
     * Renomme durablement un journal identifié par son code immuable.
     * Après succès, get(code) doit restituer le nouveau libellé.
     * L'opération doit être atomique.
     *
     * <p>La synchronisation des références déjà en mémoire est souhaitée,
     * mais n'est pas garantie par le modèle actuel : Journal est un record
     * immuable. Elle nécessitera une stratégie de remplacement des références.</p>
     *
     * @param journal journal à renommer
     * @param nouveauLibelle nouveau libellé
     * @return true si le renommage est effectué
     * @throws IllegalArgumentException si un argument est invalide
     */
    boolean renommer(Journal journal, String nouveauLibelle);
}
