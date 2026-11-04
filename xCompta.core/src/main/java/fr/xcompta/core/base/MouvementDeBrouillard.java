package fr.xcompta.core.base;

import java.math.BigDecimal;
import java.util.Objects;

import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;
import fr.xcompta.core.base.exception.ecriture.EcritureMouvementViolationContrainteReferentielleException;
import fr.xcompta.core.base.exception.mouvement.MouvementAZeroException;
import fr.xcompta.core.base.exception.mouvement.MouvementException;
import fr.xcompta.core.base.exception.mouvement.MouvementIdViolationContrainteException;

/**
 * Représente un mouvement appartenant à une écriture de brouillard.
 *
 * <p>
 * Un mouvement de brouillard est modifiable et peut être créé de manière
 * incomplète afin de permettre une saisie progressive. Il peut notamment être
 * créé sans compte, avec un montant nul et sans écriture de rattachement.
 * </p>
 *
 * <p>
 * Un mouvement nouvellement créé n'est pas persisté : son identifiant
 * technique vaut donc {@code 0}. L'identifiant est attribué ultérieurement par
 * la couche de persistance. Un constructeur distinct permet de reconstruire
 * un mouvement déjà enregistré avec son identifiant.
 * </p>
 *
 * <p>
 * Un mouvement peut être créé en mode détaché, puis rattaché à une écriture
 * lorsque celle-ci l'ajoute à sa collection. Il peut également être construit
 * directement en mode attaché lorsqu'un compte, un montant et une écriture
 * sont déjà connus.
 * </p>
 *
 * @author Fabrice LUNEAU
 */
public final class MouvementDeBrouillard extends Mouvement {

    /**
     * Construit un mouvement de brouillard vide et détaché.
     */
    public MouvementDeBrouillard() {
        super(BigDecimal.ZERO, null, null);
    }

    /**
     * Construit un mouvement de brouillard renseigné mais détaché.
     *
     * @param compte compte comptable, éventuellement {@code null}
     * @param montant montant signé, éventuellement {@code null} dans un état transitoire
     */
    public MouvementDeBrouillard(final Compte compte, final BigDecimal montant) {
        super(montant, null, compte);
    }

    /**
     * Construit un mouvement de brouillard renseigné et directement attaché à
     * une écriture de brouillard.
     *
     * @param montant montant signé, éventuellement {@code null} dans un état transitoire
     * @param compte compte comptable, éventuellement {@code null}
     * @param ecriture écriture de brouillard, éventuellement {@code null}
     */
    public MouvementDeBrouillard(
            final BigDecimal montant,
            final Compte compte,
            final EcritureDeBrouillard ecriture) {

        super(montant, ecriture, compte);
    }

    /**
     * Reconstruit un mouvement de brouillard déjà enregistré.
     *
     * @param id identifiant technique strictement positif
     * @param montant montant signé, éventuellement {@code null}
     * @param compte compte comptable, éventuellement {@code null}
     * @param ecriture écriture de brouillard, éventuellement {@code null}
     * @throws MouvementIdViolationContrainteException si l'identifiant est invalide
     */
    public MouvementDeBrouillard(
            final int id,
            final BigDecimal montant,
            final Compte compte,
            final EcritureDeBrouillard ecriture)
            throws MouvementIdViolationContrainteException {

        super(id, montant, ecriture, compte);
    }

    /**
     * Modifie le montant du mouvement.
     *
     * @param montant nouveau montant ; {@code null} est normalisé à zéro
     */
    public void setMontant(final BigDecimal montant) {
        this.montant = montant == null ? BigDecimal.ZERO : montant;
    }

    /**
     * Modifie le compte du mouvement.
     *
     * @param compte nouveau compte, éventuellement {@code null}
     */
    public void setCompte(final Compte compte) {
        this.compte = compte;
    }

    /**
     * Inverse le sens du mouvement.
     *
     * <p>
     * Un débit devient un crédit et inversement. Un montant nul reste nul.
     * </p>
     */
    public void inverserSens() {
        montant = montant.negate();
    }

    /**
     * Ajoute une valeur au montant courant.
     *
     * @param valeur valeur à ajouter
     * @throws NullPointerException si la valeur est {@code null}
     */
    public void ajouter(final BigDecimal valeur) {
        Objects.requireNonNull(valeur, "La valeur à ajouter ne peut pas être null.");
        montant = montant.add(valeur);
    }

    /**
     * Soustrait une valeur du montant courant.
     *
     * @param valeur valeur à soustraire
     * @throws NullPointerException si la valeur est {@code null}
     */
    public void soustraire(final BigDecimal valeur) {
        Objects.requireNonNull(valeur, "La valeur à soustraire ne peut pas être null.");
        montant = montant.subtract(valeur);
    }
    
    /**
     * Détache ce mouvement de son écriture de brouillard.
     *
     * @throws EcritureMouvementViolationContrainteReferentielleException
     *         si le mouvement n'est rattaché à aucune écriture
     */
    void detacher()
            throws EcritureMouvementViolationContrainteReferentielleException {

        if (ecriture == null) {
            throw new EcritureMouvementViolationContrainteReferentielleException(
                    "Le mouvement n'est pas attaché à une écriture.");
    }
    
    ecriture = null;
    }

    /**
     * Vérifie les invariants immédiatement après construction.
     *
     * <p>
     * Aucun contrôle supplémentaire n'est actuellement nécessaire : toutes les
     * informations métier peuvent être temporairement absentes dans un
     * brouillard.
     * </p>
     */
    @Override
    protected void testPostConstruction() throws MouvementException {
        // Aucun invariant supplémentaire pour l'état de brouillard.
    }

    /**
     * Vérifie que le mouvement est prêt à participer à la validation de son
     * écriture.
     * @throws XComptaCoreNullPointerException 
     *
     * @throws XComptaCoreNullPointerException si le mouvement est détaché
     * @throws XComptaCoreNullPointerException si aucun compte n'est renseigné
     * @throws MouvementAZeroException si le montant est égal à zéro
     */
    @Override
    public void testPreValidation() throws MouvementException, XComptaCoreNullPointerException {
        if (ecriture == null) {
        	throw new XComptaCoreNullPointerException(contexteErreur() + " : l'écriture ne peut pas être null.");
        }

        if (getCompte() == null) {
        	
        	throw new XComptaCoreNullPointerException(contexteErreur() + " : le compte ne peut pas être null.");
        }

        if (getMontant().signum() == 0) {
            throw new MouvementAZeroException(
                    "Le montant du mouvement ne peut pas être égal à zéro.");
        }
    }
    /**
     * Compare l'état complet de deux mouvements de brouillard.
     *
     * <p>
     * Sont comparés l'identifiant, le compte, le montant signé et la référence
     * de l'écriture de rattachement. Cette dernière est comparée par identité
     * d'instance pour éviter une comparaison récursive des écritures.
     * Les montants sont comparés numériquement, sans tenir compte de l'échelle.
     * </p>
     *
     * @param objet objet à comparer
     * @return {@code true} si tous les champs sont équivalents
     */
    @Override
    public boolean equals(final Object objet) {
        if (this == objet) {
            return true;
        }
        if (!(objet instanceof MouvementDeBrouillard autre)) {
            return false;
        }
        return id == autre.id
                && Objects.equals(compte, autre.compte)
                && (montant == autre.montant
                        || (montant != null && autre.montant != null
                                && montant.compareTo(autre.montant) == 0))
                && ecriture == autre.ecriture;
    }

    /**
     * Calcule un code de hachage cohérent avec {@link #equals(Object)}.
     *
     * <p>
     * Le montant est normalisé pour ignorer son échelle et l'écriture est
     * hachée par identité d'instance. Le résultat peut changer si l'état
     * mutable du brouillard est modifié.
     * </p>
     *
     * @return code de hachage du mouvement de brouillard
     */
    @Override
    public int hashCode() {
        final BigDecimal montantNormalise = montant == null
                ? null : montant.stripTrailingZeros();
        return Objects.hash(id, compte, montantNormalise,
                System.identityHashCode(ecriture));
    }

}
