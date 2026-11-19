
package fr.xcompta.core.port;

import java.util.List;
import java.util.Optional;

import fr.xcompta.core.base.Compte;

/**
 * Définit le contrat de persistance des comptes comptables.
 *
 * <p>
 * Cette interface expose les opérations de création, de consultation, de
 * suppression et de renommage des comptes.
 * </p>
 *
 * <p>
 * Elle est indépendante de toute technologie de persistance. Les adaptateurs
 * doivent respecter les règles définies ici, notamment les contraintes de
 * validation et de suppression.
 * </p>
 *
 * <p>
 * Seule la modification du libellé d'un compte est autorisée. Son numéro
 * constitue un identifiant immuable.
 * </p>
 *
 * @author Fabrice Luneau
 */
public interface CompteRepository
{

	/**
	 * Recherche un compte à partir de son numéro.
	 *
	 * @param numéro du compte recherché
	 * @return un Optional contenant le compt trouvé, ou un Optional vide si aucun
	 *         compte ne correspond
	 */
	Optional<Compte> get(String numero);

	/**
	 * Retourne l'ensemble des comptes comptables.
	 * 
	 * @return la liste des comptes, éventuellement vide, mais jamais null
	 */
	List<Compte> getAll();

	/**
	 * Retourne la liste des compte commençant par le numéro en argument tous les
	 * compte 6* tous les achats 60*
	 * 
	 * @return la liste des comptes commençant par le numéro en argument,
	 *         éventuellement vide, mais jamais null
	 */
	List<Compte> getAll(String startPattern);

	/**
	 * Crée et persiste un nouveau compte comptable.
	 *
	 * <p>
	 * Le compte doit satisfaire les règles de validation définies dans le noyau
	 * métier avant sa persistance.
	 * </p>
	 *
	 * <p>
	 * L'implémentation doit refuser la création si un compte possédant le même
	 * numero existe déjà.
	 * </p>
	 *
	 * @param compte journal compte à créer
	 * @return le compte créé et persisté
	 * @throws IllegalArgumentException si compte est null
	 */
	Compte creer(Compte compte);

	/**
	 * Supprime un compte identifié par son numero.
	 *
	 * <p>
	 * La suppression est autorisée uniquement si le compte n'est utilisé par aucune
	 * écriture comptable.
	 * </p>
	 *
	 * <p>
	 * Un Compte utilisé au moins par une écriture validée ne peut pas être
	 * supprimé.
	 * </p>
	 *
	 * <p>
	 * Si le compte est utilisé par au moins une écritures de brouillard, celles-ci
	 * doivent être supprimées explicitement avant la suppression du compte.
	 * </p>
	 *
	 * <p>
	 * Aucune suppression en cascade des écritures n'est autorisée.
	 * </p>
	 *
	 * @param numéro numero du compte à supprimer
	 */
	void supprimer(String numero);

	/**
	 * Supprime un compte comptable.
	 *
	 * <p>
	 * Cette méthode respecte exactement les mêmes contraintes que
	 * {@link #supprimer(String)}.
	 * </p>
	 *
	 * <p>
	 * Le compte ne doit référencer aucune écriture comptable. Aucune suppression en
	 * cascade n'est autorisée.
	 * </p>
	 *
	 * @param Compte Compte à supprimer
	 * @throws IllegalArgumentException si Compte est null
	 */
	void supprimer(Compte compte);

    /**
     * Persiste le nouveau libellé porté par le compte fourni, identifié par
     * son numéro immuable. Après succès, un nouvel appel à get(numero)
     * doit restituer ce libellé. Le renommage doit être atomique.
     *
     * <p>La mise à jour des références déjà chargées en mémoire est souhaitée,
     * mais n'est pas garantie par le modèle actuel : Compte possède un libellé
     * final. Elle nécessitera une stratégie de synchronisation des références.</p>
     *
     * @param compte compte avec son numéro et le nouveau libellé
     * @return true si le renommage est effectué
     * @throws IllegalArgumentException si le compte est invalide
     */
    boolean renommer(Compte compte);
}
