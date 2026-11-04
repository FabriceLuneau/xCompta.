package fr.xcompta.core.base;

import java.math.BigDecimal;
import java.util.Objects;

import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;
import fr.xcompta.core.base.exception.mouvement.MouvementException;
import fr.xcompta.core.base.exception.mouvement.MouvementIdViolationContrainteException;

/**
 * Classe abstraite représentant un mouvement comptable.
 *
 * <p>
 * Un mouvement correspond à une ligne d'une écriture comptable. Il associe
 * principalement un {@link Compte} à un montant.
 * </p>
 *
 * <p>
 * Le montant est exprimé sous forme signée :
 * </p>
 *
 * <ul>
 * <li>un montant strictement positif représente un débit ;</li>
 * <li>un montant strictement négatif représente un crédit ;</li>
 * <li>un montant égal à zéro représente un mouvement encore incomplet ou
 * invalide selon son état dans le cycle de vie.</li>
 * </ul>
 *
 * <p>
 * Cette représentation permet de manipuler débit et crédit dans une seule
 * propriété et simplifie notamment le calcul des soldes, l'équilibrage des
 * écritures et les opérations de contrepassation.
 * </p>
 *
 * <h2>Hiérarchie</h2>
 *
 * <p>
 * Cette classe factorise les propriétés et les comportements communs aux
 * différents états d'un mouvement :
 * </p>
 *
 * <ul>
 * <li>{@link MouvementDeBrouillard} représente un mouvement en cours de saisie,
 * qui peut encore être modifié ;</li>
 * <li>{@link MouvementValidee} représente un mouvement appartenant à une
 * écriture validée et dont l'état métier doit être cohérent dès sa
 * construction.</li>
 * </ul>
 *
 * <h2>Cycle de vie</h2>
 *
 * <p>
 * Un mouvement peut être créé en mode détaché, sans référence à une écriture.
 * Dans ce cas, sa propriété {@code ecriture} vaut {@code null}. Il peut ensuite
 * être rattaché à une écriture avant sa validation et sa persistance.
 * </p>
 *
 * <p>
 * Un mouvement peut également être construit directement en mode attaché en
 * recevant son écriture lors de sa création.
 * </p>
 *
 * <p>
 * Un mouvement ne peut être rattaché qu'une seule fois à une écriture. Une
 * fois rattaché, il ne peut pas être transféré vers une autre écriture.
 * </p>
 *
 * <h2>Identifiant technique et persistance</h2>
 *
 * <p>
 * L'identifiant {@code id} est un identifiant technique attribué par la couche
 * de persistance :
 * </p>
 *
 * <ul>
 * <li>{@code id == 0} indique que le mouvement n'a pas encore été persisté ;</li>
 * <li>{@code id > 0} indique que le mouvement a déjà été persisté et possède
 * un identifiant technique.</li>
 * </ul>
 *
 * <p>
 * L'identifiant technique participe à l'égalité complète de l'objet, mais
 * n'intervient pas dans son équivalence comptable.
 * </p>
 *
 * <h2>Égalité et équivalence comptable</h2>
 *
 * <p>
 * Chaque classe concrète définit elle-même {@code equals(Object)} et
 * {@code hashCode()} en fonction de l'ensemble de ses propriétés.
 * La classe abstraite ne fournit pas d'implémentation de ces méthodes.
 *
 * <p>
 * {@link #estEquivalent(Mouvement)} compare exclusivement le compte et le
 * montant signé, indépendamment du type concret (brouillard ou validé), de
 * l'identifiant, de l'écriture de rattachement et du lettrage.
 * </p>
 *
 * <p>
 * Les objets de brouillard sont modifiables : leur code de hachage peut
 * changer après une modification. Ils ne doivent pas être modifiés lorsqu'ils
 * sont utilisés comme clés de table de hachage ou éléments d'un ensemble
 * fondé sur le hachage.
 * </p>
 *
 * <h2>Lettrage</h2>
 *
 * <p>
 * Dans le modèle actuel, seuls les mouvements validés peuvent être lettrés.
 * Une évolution future pourra autoriser le lettrage de mouvements de brouillard,
 * éventuellement avec des mouvements validés. La validation devra préserver
 * les associations de lettrage. La suppression d'un mouvement lettré devra
 * provoquer le délettrage complet, de manière transactionnelle.
 * </p>
 *
 * <h2>Persistance et chargement différé</h2>
 *
 * <p>
 * Les adaptateurs de persistance doivent privilégier un chargement paresseux
 * (lazy) de l'écriture liée au mouvement, afin de permettre notamment la
 * consultation d'extraits de compte sans charger l'écriture complète.
 * Cette configuration ne relève pas du coeur métier.
 * </p>
 *
 * <h2>Duplication</h2>
 *
 * <p>
 * Un mouvement peut être dupliqué afin de participer à une nouvelle écriture,
 * par exemple lors d'une duplication ou d'une contrepassation.
 * </p>
 *
 * <p>
 * La copie conserve le compte et le montant mais constitue un nouvel objet
 * destiné à être persisté indépendamment du mouvement d'origine. Son
 * identifiant est donc réinitialisé à {@code 0}.
 * </p>
 *
 * <h2>Choix d'implémentation</h2>
 *
 * <p>
 * Le montant est représenté par un {@link BigDecimal}. Ce type garantit une
 * représentation décimale exacte des valeurs monétaires et évite les erreurs
 * d'arrondi propres aux types flottants.
 * </p>
 *
 * <p>
 * La classe abstraite conserve telle quelle la valeur du montant reçue par son
 * constructeur. Les sous-classes définissent ensuite les états autorisés :
 * un mouvement de brouillard peut représenter un état de saisie incomplet,
 * tandis qu'un mouvement validé doit posséder un montant non {@code null} et
 * différent de zéro.
 * </p>
 *
 * @author Fabrice LUNEAU
 */
public abstract class Mouvement implements ValidableMetier {

    /**
     * Identifiant technique du mouvement.
     *
     * <p>
     * La valeur {@code 0} indique que le mouvement n'a pas encore été
     * persisté. Un identifiant strictement positif correspond à un mouvement
     * déjà persisté.
     * </p>
     *
     * <p>
     * Cet identifiant participe à l'égalité complète de l'objet, mais pas
     * à son équivalence comptable.
     * </p>
     */
    protected int id = 0;

    /**
     * Montant signé du mouvement.
     *
     * <ul>
     * <li>positif : débit ;</li>
     * <li>négatif : crédit ;</li>
     * <li>zéro : mouvement incomplet ou invalide selon son état.</li>
     * </ul>
     */
    protected BigDecimal montant;

    /**
     * Écriture à laquelle le mouvement est rattaché.
     *
     * <p>
     * La valeur {@code null} indique que le mouvement est en mode détaché.
     * Lorsqu'une écriture est renseignée, le mouvement est en mode attaché.
     * </p>
     *
     * <p>
     * Un mouvement ne peut être rattaché qu'une seule fois à une écriture.
     * </p>
     */
    protected Ecriture<?> ecriture;

    /**
     * Compte comptable concerné par le mouvement.
     *
     * <p>
     * Cette propriété peut temporairement être {@code null} pour un mouvement
     * de brouillard en cours de saisie.
     * </p>
     */
    protected Compte compte;

    /**
     * Construit un mouvement.
     *
     * <p>
     * La cohérence de l'état obtenu est contrôlée par les sous-classes au moyen
     * de {@link #testPostConstruction()} lorsqu'elles sont complètement
     * initialisées.
     * </p>
     *
     * <p>
     * Un identifiant égal à {@code 0} représente un mouvement qui n'a pas
     * encore été persisté.
     * </p>
     *
     * <p>
     * L'écriture peut être {@code null}. Le mouvement est alors construit en
     * mode détaché et pourra être rattaché ultérieurement.
     * </p>
     *
     * @param montant  montant signé du mouvement ; les sous-classes déterminent
     *                 si {@code null} est autorisé dans leur état
     * @param ecriture écriture propriétaire, ou {@code null} pour un mouvement
     *                 détaché
     * @param compte   compte comptable, éventuellement {@code null} pour un
     *                 mouvement de brouillard incomplet
     */
    protected Mouvement(
            final BigDecimal montant,
            final Ecriture<?> ecriture,
            final Compte compte) {

        this.montant = montant;
        this.ecriture = ecriture;
        this.compte = compte;
    }

    /**
     * Reconstruit un mouvement à partir de son identifiant technique.
     *
     * <p>
     * Ce constructeur est destiné à la reconstitution d'un mouvement déjà
     * enregistré. Il ne dispense pas la persistance de contrôler l'identité
     * et l'intégrité des objets chargés.
     * </p>
     *
     * @param id identifiant technique strictement positif
     * @param montant montant signé du mouvement
     * @param ecriture écriture de rattachement, éventuellement {@code null}
     * @param compte compte comptable, éventuellement {@code null} pour un brouillard
     * @throws MouvementIdViolationContrainteException si l'identifiant est invalide
     */
    protected Mouvement(
            final int id,
            final BigDecimal montant,
            final Ecriture<?> ecriture,
            final Compte compte) throws MouvementIdViolationContrainteException {

        this(montant, ecriture, compte);
        if (id <= 0) {
            throw new MouvementIdViolationContrainteException(
                    "L'identifiant d'un mouvement persisté doit être strictement positif.");
        }
        this.id = id;
    }

    /**
     * Retourne l'identifiant technique du mouvement.
     *
     * @return {@code 0} si le mouvement n'a pas encore été persisté,
     *         sinon son identifiant technique
     */
    public int getId() {
        return id;
    }
    
    /**
     * Attribue l'identifiant technique du mouvement.
     *
     * <p>
     * L'identifiant ne peut être attribué que si le mouvement n'a pas encore
     * été persisté, c'est-à-dire lorsque son identifiant courant vaut
     * {@code 0}. Une fois attribué, il ne peut plus être modifié.
     * </p>
     *
     * @param id identifiant technique à attribuer
     *
     * @throws MouvementIdViolationContrainteException si l'identifiant n'est
     *         pas strictement positif ou si un identifiant a déjà été attribué
     */
    public void setId(final int id) throws MouvementIdViolationContrainteException {

        if (this.id != 0) {
            throw new MouvementIdViolationContrainteException(
                    "L'identifiant du mouvement a déjà été attribué.");
        }

        if (id <= 0) {
            throw new MouvementIdViolationContrainteException(
                    "L'identifiant d'un mouvement doit être strictement positif.");
        }

        this.id = id;
    }


    /**
     * Retourne le montant signé du mouvement.
     *
     * @return montant du mouvement
     */
    public BigDecimal getMontant() {
        return montant;
    }

    /**
     * Retourne l'écriture à laquelle le mouvement est rattaché.
     *
     * @return l'écriture propriétaire ou {@code null} si le mouvement est
     *         détaché
     */
    public Ecriture<?> getEcriture() {
        return ecriture;
    }

    /**
     * Rattache le mouvement à une écriture.
     *
     * <p>
     * Cette opération permet de rattacher à une écriture un mouvement créé
     * initialement en mode détaché.
     * </p>
     *
     * <p>
     * Un mouvement ne peut être rattaché qu'une seule fois. Une nouvelle
     * tentative de rattachement à la même instance d'écriture est tolérée,
     * mais il est interdit de le rattacher ensuite à une autre écriture.
     * </p>
     *
     * @param ecriture écriture à laquelle rattacher le mouvement
     *
     * @throws NullPointerException si l'écriture fournie est {@code null}
     * @throws IllegalStateException si le mouvement est déjà rattaché à une
     *                               autre écriture
     */
    void rattacherEcriture(final Ecriture<?> ecriture) {

        Objects.requireNonNull(
                ecriture,
                "L'écriture du mouvement ne peut pas être null.");

        if (this.ecriture != null && this.ecriture != ecriture) {
            throw new IllegalStateException(
                    "Le mouvement est déjà rattaché à une autre écriture.");
        }

        this.ecriture = ecriture;
    }

    /**
     * Retourne le compte comptable du mouvement.
     *
     * @return le compte ou {@code null} si aucun compte n'a encore été
     *         renseigné
     */
    public Compte getCompte() {
        return compte;
    }
    
    /**
     * Retourne l'identifiant de l'écriture de rattachement.
     *
     * @return identifiant de l'écriture
     * @throws NullPointerException si le mouvement est détaché
     */
    public int getIdEcriture() {
        return ecriture.getId();
    }

    /**
     * Retourne le journal de l'écriture de rattachement.
     *
     * @return journal de l'écriture
     * @throws NullPointerException si le mouvement est détaché
     */
    public Journal getJournal() {
        return ecriture.getJournal();
    }

    /**
     * Crée une copie de ce mouvement dans une nouvelle écriture de brouillard.
     *
     * <p>
     * La copie conserve le compte et le montant du mouvement d'origine.
     * Elle constitue cependant un nouvel objet métier destiné à être persisté
     * séparément : son identifiant technique est donc réinitialisé à
     * {@code 0}.
     * </p>
     *
     * <p>
     * La copie est directement rattachée à l'écriture cible.
     * </p>
     *
     * @param ecritureCible écriture de brouillard recevant la copie
     *
     * @return nouveau mouvement de brouillard non encore persisté
     *
     * @throws MouvementException si la construction du mouvement dupliqué
     *                            enfreint une règle métier
     */
    public MouvementDeBrouillard dupliquer(
            final EcritureDeBrouillard ecritureCible)
            throws MouvementException {

        return new MouvementDeBrouillard(
                montant,
                compte,
                ecritureCible);
    }

    /**
     * Indique si le montant du mouvement est égal à zéro.
     *
     * @return {@code true} si le montant est égal à zéro,
     *         {@code false} sinon
     */
    public boolean estAZero() {
        return montant.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * Indique si le mouvement est débiteur.
     *
     * @return {@code true} si le montant est strictement positif,
     *         {@code false} sinon
     */
    public boolean estDebiteur() {
        return montant.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Indique si le mouvement est créditeur.
     *
     * @return {@code true} si le montant est strictement négatif,
     *         {@code false} sinon
     */
    public boolean estCrediteur() {
        return montant.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * Indique si aucune information comptable n'a encore été renseignée.
     *
     * <p>
     * Un mouvement est considéré comme vide lorsque son compte est
     * {@code null} et que son montant est égal à zéro.
     * </p>
     *
     * <p>
     * Le rattachement éventuel du mouvement à une écriture n'intervient pas
     * dans cette définition.
     * </p>
     *
     * @return {@code true} si le mouvement ne possède ni compte ni montant
     *         significatif, {@code false} sinon
     */
    public boolean estVide() {
        return compte == null
                && montant.signum() == 0;
    }

    /**
     * Compare deux montants en ignorant les différences d'échelle.
     */
    private static boolean montantsEgaux(final BigDecimal montant1, final BigDecimal montant2) {
        if (montant1 == montant2) {
            return true;
        }
        if (montant1 == null || montant2 == null) {
            return false;
        }
        return montant1.compareTo(montant2) == 0;
    }

    /**
     * Indique si un autre mouvement possède le même contenu comptable.
     *
     * <p>
     * Seuls le compte et le montant sont pris en compte. Le type concret,
     * l'identifiant, le rattachement à une écriture et le lettrage sont ignorés. Un mouvement
     * de brouillard peut donc être comptablement équivalent à un mouvement
     * validé sans lui être égal.
     * </p>
     *
     * @param autre mouvement à comparer
     * @return {@code true} si les deux mouvements ont le même compte et le même
     *         montant signé
     */
    public boolean estEquivalent(final Mouvement autre) {
        return autre != null
                && Objects.equals(compte, autre.compte)
                && montantsEgaux(montant, autre.montant);
    }

    /**
     * Vérifie la cohérence du mouvement immédiatement après sa construction.
     *
     * <p>
     * Cette vérification ne signifie pas nécessairement que le mouvement est
     * prêt à être validé ou persisté. Une sous-classe représentant un objet
     * transitoire, comme {@link MouvementDeBrouillard}, peut accepter un état
     * temporairement incomplet.
     * </p>
     *
     * @throws MouvementException si l'état du mouvement ne respecte pas les
     *                            invariants imposés par la sous-classe
     * @throws XComptaCoreNullPointerException 
     */
    protected abstract void testPostConstruction()
            throws MouvementException, XComptaCoreNullPointerException;

    /**
     * Retourne une représentation textuelle du mouvement.
     *
     * @return identifiant de l'écriture (ou « détaché »), compte et montant
     */
    @Override
    public String toString() {
        return (ecriture == null ? "détaché" : ecriture.getId())
                + " " + compte + " : " + montant;
    }
    
protected String contexteErreur() {
	return getClass().getSimpleName() + (getId() > 0 ? " [id=" + getId() + "]" : "");
}
}