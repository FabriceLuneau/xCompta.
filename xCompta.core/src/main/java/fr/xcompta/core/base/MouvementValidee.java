package fr.xcompta.core.base;

import java.math.BigDecimal;
import java.util.Objects;

import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;
import fr.xcompta.core.base.exception.mouvement.MouvementAZeroException;
import fr.xcompta.core.base.exception.mouvement.MouvementException;
import fr.xcompta.core.base.exception.mouvement.MouvementIdViolationContrainteException;

/**
 * Représente un mouvement appartenant à une écriture validée.
 *
 * <p>
 * Un mouvement validé possède des informations comptables définitives :
 * son compte et son montant ne peuvent plus être modifiés après sa
 * construction.
 * </p>
 *
 * <h2>États détaché et attaché</h2>
 *
 * <p>
 * Un mouvement validé peut exister temporairement sous forme détachée.
 * Dans cet état, sa référence vers l'écriture vaut {@code null}. Cet état
 * transitoire est notamment utilisé lors de la reconstruction des objets
 * métier à partir des données fournies par la couche de persistance.
 * </p>
 *
 * <p>
 * Lors de la construction d'une {@link EcritureValidee}, les mouvements
 * détachés fournis par l'appelant sont utilisés comme sources. L'écriture
 * construit par copie ses propres mouvements attachés en conservant leur
 * identifiant, leur compte et leur montant.
 * </p>
 *
 * <p>
 * Cette copie permet de rompre le lien avec les objets fournis par
 * l'appelant et garantit que chaque mouvement contenu dans une écriture
 * validée référence effectivement cette écriture.
 * </p>
 *
 * <p>
 * Les mouvements sources détachés ne sont pas modifiés lors de cette
 * opération. Ils peuvent devenir éligibles au ramasse-miettes lorsqu'ils
 * ne sont plus référencés par l'appelant.
 * </p>
 *
 * <h2>Identifiant technique</h2>
 *
 * <p>
 * L'identifiant est géré par la couche de persistance. Une valeur
 * {@code 0} indique que l'identifiant technique n'a pas encore été
 * attribué ou répercuté dans l'objet métier. Une valeur strictement
 * positive représente un identifiant connu. Une valeur négative est
 * interdite.
 * </p>
 *
 * <p>
 * L'état de persistance est distinct de la validité métier. Un mouvement
 * validé peut donc être cohérent du point de vue métier tout en étant
 * temporairement détaché ou en attente de son identifiant technique.
 * </p>
 *
 * <h2>Lettrage</h2>
 *
 * <p>
 * Dans le modèle actuel, seuls les mouvements validés peuvent être lettrés.
 * Un mouvement peut être
 * non lettré ou appartenir à un seul {@link Lettrage}. La référence portée par
 * cette classe représente le côté mouvement de cette relation bidirectionnelle.
 * </p>
 *
 * <p>
 * Le rattachement et le délettrage sont volontairement limités au package
 * métier : ils sont pilotés par {@link Lettrage}, qui maintient simultanément
 * sa liste de mouvements et la référence inverse de chaque mouvement.
 * </p>
 *
 * @author Fabrice LUNEAU
 */
public final class MouvementValidee extends Mouvement {

    /**
     * Lettrage auquel appartient le mouvement.
     *
     * <p>
     * La valeur {@code null} indique que le mouvement n'est pas lettré.
     * </p>
     */
    private Lettrage lettrage;

    /**
     * Construit un mouvement validé détaché.
     *
     * <p>
     * Ce constructeur est notamment destiné à la reconstruction d'un mouvement
     * avant son rattachement à une {@link EcritureValidee}. La référence vers
     * l'écriture vaut donc initialement {@code null}.
     * </p>
     *
     * <p>
     * L'identifiant peut être égal à {@code 0} si l'objet est encore en attente
     * de l'identifiant attribué par la persistance, ou être strictement positif
     * si cet identifiant est déjà connu.
     * </p>
     *
     * @param id identifiant technique, égal à {@code 0} ou strictement positif
     * @param montant montant signé du mouvement
     * @param compte compte comptable du mouvement
     *
     * @throws MouvementException si le mouvement ne respecte pas les règles
     *         métier
     * @throws XComptaCoreNullPointerException 
     */
    public MouvementValidee(
            final int id,
            final BigDecimal montant,
            final Compte compte)
            throws MouvementException, XComptaCoreNullPointerException {

        super(montant, null, compte);

        if (id < 0) {
            throw new MouvementIdViolationContrainteException(
                    "L'identifiant d'un mouvement validé ne peut pas être négatif.");
        }

        // L'identifiant 0 désigne un mouvement non encore persisté.
        // Un identifiant positif est conservé lors de la reconstruction.
        if (id > 0) {
            setId(id);
        }

		testPostConstruction();
    }

    /**
     * Retourne le lettrage associé au mouvement.
     *
     * @return le lettrage du mouvement, ou {@code null} s'il n'est pas lettré
     */
    public Lettrage getLettrage() {
        return lettrage;
    }

    /**
     * Indique si le mouvement est lettré.
     *
     * @return {@code true} si le mouvement appartient à un lettrage,
     *         {@code false} sinon
     */
    public boolean estLettre() {
        return lettrage != null;
    }

    /**
     * Rattache le mouvement à un lettrage.
     *
     * <p>
     * Cette méthode est volontairement limitée au package métier.
     * Le rattachement doit normalement être piloté par {@link Lettrage}.
     * </p>
     *
     * @param lettrage lettrage à associer au mouvement
     *
     * @throws NullPointerException si le lettrage est {@code null}
     * @throws IllegalStateException si le mouvement est déjà lettré
     */
    void lettrer(final Lettrage lettrage) {

        Objects.requireNonNull(
                lettrage,
                "Le lettrage ne peut pas être null.");

        if (this.lettrage != null) {
            throw new IllegalStateException(
                    "Le mouvement est déjà lettré.");
        }

        this.lettrage = lettrage;
    }

    /**
     * Retire le mouvement de son lettrage.
     *
     * <p>
     * Cette méthode est volontairement limitée au package métier.
     * Le délettrage doit normalement être piloté par {@link Lettrage}.
     * </p>
     *
     * @param lettrage lettrage auquel le mouvement doit actuellement appartenir
     *
     * @throws NullPointerException si le lettrage fourni est {@code null}
     * @throws IllegalStateException si le mouvement n'appartient pas au
     *         lettrage fourni
     */
    void delettrer(final Lettrage lettrage) {

        Objects.requireNonNull(
                lettrage,
                "Le lettrage ne peut pas être null.");

        if (this.lettrage != lettrage) {
            throw new IllegalStateException(
                    "Le mouvement n'appartient pas à ce lettrage.");
        }

        this.lettrage = null;
    }

    /**
     * Vérifie la cohérence propre du mouvement après sa construction.
     *
     * <p>
     * Ce contrôle porte uniquement sur les informations qui doivent être
     * valides indépendamment du rattachement à une écriture.
     * </p>
     *
     * <p>
     * Une référence d'écriture à {@code null} est acceptée : elle représente
     * un mouvement validé détaché en attente de reconstruction dans une
     * {@link EcritureValidee}.
     * </p>
     *
     * <p>
     * Un montant {@code null} est interdit pour un mouvement validé. Il est
     * contrôlé séparément d'un montant égal à zéro afin de distinguer les deux
     * états invalides.
     * </p>
     * @throws XComptaCoreNullPointerException si le compte est absent
     * @throws MouvementAZeroException si le montant est {@code null}
     *         ou égal à zéro
     */
    @Override
	protected void testPostConstruction() throws MouvementAZeroException, XComptaCoreNullPointerException {

        if (getCompte() == null) {
        	throw new XComptaCoreNullPointerException(contexteErreur() + " : le compte ne peut pas être null.");
        }

        if (getMontant() == null) {
            throw new MouvementAZeroException(
                    "Le montant du mouvement validé ne peut pas être null.");
        }

        if (getMontant().signum() == 0) {
            throw new MouvementAZeroException(
                    "Le montant du mouvement validé ne peut pas être égal à zéro.");
        }
    }

    /**
     * Vérifie la cohérence métier propre au mouvement validé.
     *
     * <p>
     * Un mouvement validé est comptablement définitif dès sa construction.
     * Les contrôles portent donc sur son compte et son montant.
     * </p>
     *
     * <p>
     * Le rattachement à une écriture n'est volontairement pas vérifié ici.
     * Un mouvement validé détaché constitue un état transitoire autorisé.
     * La cohérence entre une écriture et les mouvements qu'elle contient
     * relève du contrôle d'intégrité de {@link EcritureValidee}.
     * </p>
     *
     * @throws MouvementException si une règle métier du mouvement n'est pas
     *         respectée
     * @throws XComptaCoreNullPointerException 
     */
    @Override
    public void testPreValidation() throws MouvementException, XComptaCoreNullPointerException {
        testPostConstruction();
    }
    /**
     * Compare l'état complet de deux mouvements validés.
     *
     * <p>
     * La comparaison prend en compte l'identifiant, le compte, le montant,
     * l'écriture de rattachement et le lettrage. Le montant est comparé
     * numériquement, sans tenir compte de son échelle.
     * </p>
     *
     * <p>
     * Les références vers l'écriture et le lettrage sont comparées par
     * identité d'instance afin d'éviter les comparaisons récursives et le
     * chargement de leurs collections. Deux objets distincts représentant
     * la même ligne persistée ne sont donc pas nécessairement égaux.
     * </p>
     *
     * @param objet objet à comparer
     * @return {@code true} si tous les champs comparés sont égaux
     */
    @Override
    public boolean equals(final Object objet) {
        if (this == objet) {
            return true;
        }
        if (!(objet instanceof MouvementValidee autre)) {
            return false;
        }

        return id == autre.id
                && Objects.equals(compte, autre.compte)
                && (montant == autre.montant
                        || (montant != null && autre.montant != null
                                && montant.compareTo(autre.montant) == 0))
                && ecriture == autre.ecriture
                && lettrage == autre.lettrage;
    }

    /**
     * Calcule un code de hachage cohérent avec {@link #equals(Object)}.
     *
     * <p>
     * Le montant est normalisé pour ignorer son échelle. L'écriture et le
     * lettrage sont hachés par identité d'instance. Le résultat peut changer
     * après attribution d'un identifiant, rattachement ou délettrage : ne
     * pas modifier ces propriétés lorsque l'objet est dans un {@code HashSet}
     * ou utilisé comme clé d'une {@code HashMap}.
     * </p>
     *
     * @return code de hachage du mouvement validé
     */
    @Override
    public int hashCode() {
        final BigDecimal montantNormalise = montant == null
                ? null : montant.stripTrailingZeros();
        return Objects.hash(id, compte, montantNormalise,
                System.identityHashCode(ecriture),
                System.identityHashCode(lettrage));
    }

}
