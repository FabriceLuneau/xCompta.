package fr.xcompta.core.base;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.xcompta.core.base.exception.XComptaCoreException;
import fr.xcompta.core.base.exception.XComptaCoreIllegalArgumentException;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;
import fr.xcompta.core.base.exception.ecriture.EcritureException;
import fr.xcompta.core.base.exception.ecriture.EcritureMontantTotalAZeroException;
import fr.xcompta.core.base.exception.ecriture.EcritureMouvementViolationContrainteReferentielleException;
import fr.xcompta.core.base.exception.ecriture.EcritureNbMouvementsInferieurADeuxException;
import fr.xcompta.core.base.exception.ecriture.EcritureNonEquilibreeException;
import fr.xcompta.core.base.exception.ecriture.EcritureViolationContrainteReferentielleException;
import fr.xcompta.core.date.exception.DateInvalideException;

/**
 * Représente une écriture comptable de brouillard.
 *
 * <p>
 * Une écriture de brouillard est une écriture provisoire. Elle peut être
 * modifiée, complétée ou supprimée tant qu'elle n'a pas été validée.
 * </p>
 *
 * <p>
 * Lors de sa validation, elle est transformée en écriture validée. La gestion des numéros de préenregistrement et d'enregistrement définitif
 * est prévue ultérieurement et n'est pas implémentée ici.
 * </p>
 *
 * <p>
 * Cette classe porte les opérations de modification de l'en-tête et des mouvements.
 * La classe mère conserve les données et les opérations de consultation communes.
 * </p>
 *
 * <h2>Saisie des mouvements</h2>
 *
 * <p>
 * Une écriture de brouillard autorise la saisie progressive de ses mouvements.
 * Un mouvement vide peut ainsi être ajouté à la fin de l'écriture ou inséré à
 * une position donnée. Le mouvement créé est automatiquement rattaché à
 * l'écriture.
 * </p>
 *
 * <h2>Contrôles métier</h2>
 *
 * <p>
 * Le contrôle post-construction reste volontairement minimal afin d'autoriser
 * la saisie progressive d'un brouillard. Il vérifie uniquement les invariants
 * techniques qui doivent être vrais dès la création de l'objet.
 * </p>
 *
 * <p>
 * Le contrôle préalable à la validation applique les mêmes règles métier que
 * pour une {@link EcritureValidee}. L'écriture doit posséder une date, un
 * journal, un libellé, au moins deux mouvements, être équilibrée et présenter
 * un montant total strictement positif. Chaque mouvement doit également
 * satisfaire ses propres règles de pré-validation.
 * </p>
 *
 * @author Fabrice Luneau
 * @since 1.0
 */
public final class EcritureDeBrouillard extends Ecriture<MouvementDeBrouillard> {
	/**
	 * Construit une nouvelle écriture de brouillard vide.
	 *
	 * <p>
	 * L'identifiant vaut {@code 0}, la date correspond à la date du jour et le
	 * libellé par défaut est « nouvelle écriture ». Le journal reste à renseigner
	 * avant validation.
	 * </p>
	 *
	 * @throws XComptaCoreException si l'état initial de l'objet est incohérent
	 */
	public EcritureDeBrouillard() throws XComptaCoreException {
		super(0, "nouvelle écriture", LocalDate.now(), null, null);
		
		// Aucun mouvement initial : aucun rattachement nécessaire.
		testPostConstruction();
	}

	/**
	 * Reconstruit l'en-tête d'une écriture de brouillard à partir de ses valeurs.
	 * Une collection vide de mouvements est initialisée.
	 *
	 * @param id      identifiant technique de l'écriture
	 * @param date    date comptable
	 * @param journal journal comptable
	 * @param libelle libellé de l'écriture
	 *
	 * @throws XComptaCoreException si l'état reconstruit est incohérent
	 */
	public EcritureDeBrouillard(final int id, final String libelle, final LocalDate date, final Journal journal)
			throws XComptaCoreException {

		/*
		 * FUTUR : ajouter le numéro de préenregistrement à ce constructeur et à l'appel
		 * de super(...), après réactivation de cette propriété dans Ecriture. Le type
		 * long reste à confirmer.
		 *
		 * Exemple de signature envisagée (non compilée) : EcritureDeBrouillard(int id,
		 * long numeroPreEnregistrement, LocalDate date, String libelle, Journal
		 * journal)
		 */
		super(id, libelle, date, journal, List.of());

		testPostConstruction();
	}

	/**
	 * Reconstruit une écriture de brouillard avec ses mouvements. Les mouvements
	 * sont rattachés à cette écriture sans valider son contenu comptable.
	 *
	 * @param id         identifiant technique (0 si non persisté)
	 * @param libelle    libellé comptable
	 * @param date       date comptable
	 * @param journal    journal comptable
	 * @param mouvements mouvements initiaux, non null (liste vide autorisée)
	 * @throws XComptaCoreException si les données ou les rattachements sont
	 *                              invalides
	 */
	public EcritureDeBrouillard(final int id, final String libelle, final LocalDate date, final Journal journal,
			final List<MouvementDeBrouillard> mouvements) throws XComptaCoreException {
		super(id, libelle, date, journal, mouvements);
		
		testPostConstruction();
	}

	/**
	 * Attribue l'identifiant technique du brouillard.
	 *
	 * <p>
	 * La classe mère applique la règle commune : {@code 0} signifie non persisté,
	 * un identifiant positif est définitif, un négatif est interdit.
	 * </p>
	 *
	 * @param id identifiant à attribuer
	 * @throws IllegalArgumentException                          si l'identifiant
	 *                                                           est négatif
	 * @throws EcritureViolationContrainteReferentielleException si un identifiant
	 *                                                           positif a déjà été
	 *                                                           attribué
	 * @throws XComptaCoreIllegalArgumentException 
	 */
	public void setId(final int id) throws EcritureViolationContrainteReferentielleException, XComptaCoreIllegalArgumentException {
		if (id < 0) {
			throw new XComptaCoreIllegalArgumentException("L'identifiant d'une écriture ne peut pas être négatif.");
		}
		if (this.id > 0) {
			throw new EcritureViolationContrainteReferentielleException(
					"L'identifiant de l'écriture a déjà été attribué.");
		}
		this.id = id;
	}

	/*
	 * FUTUR : numéro de préenregistrement (désactivé).
	 *
	 * L'attribut et le getter appartiendront à Ecriture pour conserver ce numéro
	 * lors de la validation. Zéro signifie « non attribué » ; une fois attribué, le
	 * numéro sera immuable. Son unicité par comptabilité et son attribution
	 * transactionnelle seront garanties par le service de persistance. TODO :
	 * définir le point d'accès contrôlé pour son attribution.
	 */

	/**
	 * Modifie le libellé de cette écriture de brouillard.
	 *
	 * @param libelle nouveau libellé
	 * @throws XComptaCoreNullPointerException si le libellé est {@code null}
	 * @throws XComptaCoreIllegalArgumentException 
		 */
	public void setLibelle(final String libelle) throws XComptaCoreNullPointerException, XComptaCoreIllegalArgumentException {
		if (libelle == null) {
			throw new XComptaCoreNullPointerException(contexteErreur() + " : le libellé ne peut pas être null.");
		}
		
		if (libelle.isBlank() || libelle.trim().isEmpty()) {
			throw new XComptaCoreIllegalArgumentException(contexteErreur() + " : le libellé ne peut pas être vide ou blanc.");
		}

		this.libelle = libelle.trim();
	}

	/**
	 * Modifie la date comptable de cette écriture de brouillard.
	 *
	 * @param date nouvelle date comptable
	 * @throws XComptaCoreException 
	 * @throws EcritureDateNullException si la date est {@code null}
	 */
	public void setDate(final LocalDate date) throws XComptaCoreException {
		if (date == null) {
			throw new XComptaCoreNullPointerException(contexteErreur() + " : la date ne peut pas être null.");
		}

		this.date = date;
	}

	/**
	 * Modifie le journal de cette écriture de brouillard.
	 *
	 * @param journal nouveau journal
	 * @throws XComptaCoreNullPointerException si le journal est {@code null}
		 */
	public void setJournal(final Journal journal)
			throws XComptaCoreNullPointerException {
		if (journal == null) {
			throw new XComptaCoreNullPointerException(contexteErreur() + " : le journal ne peut pas être null.");
		}

		this.journal = journal;
	}

	/**
	 * Retourne le mouvement situé à la position indiquée afin de permettre sa
	 * modification pendant la saisie du brouillard.
	 *
	 * <p>
	 * Cette méthode expose l'instance contenue dans la collection interne et doit
	 * donc être utilisée uniquement pour les opérations propres à une écriture de
	 * brouillard modifiable.
	 * </p>
	 *
	 * @param index position du mouvement, comprise entre {@code 0} et
	 *              {@code size() - 1}
	 * @return mouvement de brouillard situé à la position indiquée
	 * @throws IndexOutOfBoundsException si l'index est hors des limites de la
	 *                                   collection
	 */
	public MouvementDeBrouillard getMouvement(final int index) {
		return getMouvementsModifiables().get(index);
	}

	/**
	 * Ajoute un mouvement au début de l'écriture.
	 *
	 * @param mouvement mouvement à ajouter
	 *
	 * @throws EcritureMouvementViolationContrainteReferentielleException si le
	 *                                                                    mouvement
	 *                                                                    ne
	 *                                                                    respecte
	 *                                                                    pas la
	 *                                                                    relation
	 *                                                                    avec
	 *                                                                    l'écriture
	 */
	public void ajouterMouvementAuDebut(final MouvementDeBrouillard nouveauMouvement)
			throws EcritureMouvementViolationContrainteReferentielleException {

		Objects.requireNonNull(nouveauMouvement, "Le mouvement est null.");
		
		if (mouvementEstPresent(nouveauMouvement)) {
			throw new IllegalArgumentException(
					"Ce mouvement est déjà présent dans l'écriture.");
		}
		
		nouveauMouvement.rattacherEcriture(this);
		getMouvementsModifiables().add(0, nouveauMouvement);
	}

	/**
	 * Ajoute un mouvement de brouillard à la fin de l'écriture.
	 *
	 * @param nouveauMouvement mouvement à ajouter
	 *
	 * @throws EcritureMouvementViolationContrainteReferentielleException si le
	 *                                                                    mouvement
	 *                                                                    ne
	 *                                                                    respecte
	 *                                                                    pas la
	 *                                                                    relation
	 *                                                                    avec
	 *                                                                    l'écriture
	 */
	public void ajouterMouvementALaFin(final MouvementDeBrouillard nouveauMouvement)
			throws EcritureMouvementViolationContrainteReferentielleException {

		Objects.requireNonNull(nouveauMouvement, "Le mouvement est null.");
		
		if (mouvementEstPresent(nouveauMouvement)) {
			throw new IllegalArgumentException(
					"Ce mouvement est déjà présent dans l'écriture.");
		}
		
		nouveauMouvement.rattacherEcriture(this);
		getMouvementsModifiables().add(nouveauMouvement);
	}

	/**
	 * Insère un mouvement existant à la position indiquée.
	 *
	 * @param index     position d'insertion
	 * @param nouveauMouvement mouvement à insérer
	 *
	 * @throws IndexOutOfBoundsException                                  si l'index
	 *                                                                    est
	 *                                                                    invalide
	 * @throws EcritureMouvementViolationContrainteReferentielleException si le
	 *                                                                    mouvement
	 *                                                                    ne
	 *                                                                    respecte
	 *                                                                    pas la
	 *                                                                    relation
	 *                                                                    avec
	 *                                                                    l'écriture
	 */
	public void insererMouvementA(final int index, final MouvementDeBrouillard nouveauMouvement)
			throws EcritureMouvementViolationContrainteReferentielleException {

		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException("Index d'insertion invalide : " + index);
		}
		Objects.requireNonNull(nouveauMouvement, "Le mouvement est null.");
		
		if (mouvementEstPresent(nouveauMouvement)) {
			throw new IllegalArgumentException(
					"Ce mouvement est déjà présent dans l'écriture.");
		}
		nouveauMouvement.rattacherEcriture(this);
		getMouvementsModifiables().add(index, nouveauMouvement);
	}

	/**
	 * Crée et ajoute un mouvement de brouillard vide à la fin de l'écriture.
	 *
	 * <p>
	 * Le mouvement créé est neuf et non persisté. Son identifiant vaut donc
	 * {@code 0}, son compte n'est pas encore renseigné et son montant vaut zéro.
	 * </p>
	 *
	 * <p>
	 * Le mouvement est automatiquement rattaché à cette écriture lors de son ajout.
	 * </p>
	 *
	 * @return le mouvement de brouillard créé et ajouté
	 *
	 * @throws EcritureMouvementViolationContrainteReferentielleException si le
	 *                                                                    mouvement
	 *                                                                    ne peut
	 *                                                                    pas être
	 *                                                                    ajouté à
	 *                                                                    l'écriture
	 */
	public MouvementDeBrouillard ajouterMouvementVide()
			throws EcritureMouvementViolationContrainteReferentielleException {

		MouvementDeBrouillard mouvement = new MouvementDeBrouillard();
		Objects.requireNonNull(mouvement, "Le mouvement est null.");
		mouvement.rattacherEcriture(this);
		getMouvementsModifiables().add(mouvement);

		return mouvement;
	}

	/**
	 * Crée et insère un mouvement de brouillard vide à la position indiquée.
	 *
	 * <p>
	 * Le mouvement créé est neuf et non persisté. Son identifiant vaut donc
	 * {@code 0}, son compte n'est pas encore renseigné et son montant vaut zéro.
	 * </p>
	 *
	 * <p>
	 * Le mouvement est automatiquement rattaché à cette écriture lors de son
	 * insertion par la classe mère.
	 * </p>
	 *
	 * @param index position d'insertion
	 *
	 * @return le mouvement de brouillard créé et inséré
	 *
	 * @throws IndexOutOfBoundsException                                  si l'index
	 *                                                                    est
	 *                                                                    inférieur
	 *                                                                    à
	 *                                                                    {@code 0}
	 *                                                                    ou
	 *                                                                    supérieur
	 *                                                                    au nombre
	 *                                                                    actuel de
	 *                                                                    mouvements
	 * @throws EcritureMouvementViolationContrainteReferentielleException si le
	 *                                                                    mouvement
	 *                                                                    ne peut
	 *                                                                    pas être
	 *                                                                    inséré
	 *                                                                    dans
	 *                                                                    l'écriture
	 */
	public MouvementDeBrouillard insererMouvementA(final int index)
			throws EcritureMouvementViolationContrainteReferentielleException {

		/*
		 * On contrôle l'index avant de rattacher le mouvement. Ainsi, un index invalide
		 * ne laisse pas un mouvement rattaché à l'écriture sans appartenir à sa
		 * collection.
		 */
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException(
					"L'index d'insertion " + index + " doit être compris entre 0 et " + size() + ".");
		}

		MouvementDeBrouillard mouvement = new MouvementDeBrouillard();
		insererMouvementA(index, mouvement);

		return mouvement;
	}

	/**
	 * Supprime le mouvement situé à une position donnée.
	 *
	 * @param index position du mouvement à supprimer
	 * @throws XComptaCoreNullPointerException 
	 * @throws x 
	 */
	public boolean supprimerMouvement(final int index)
            throws EcritureMouvementViolationContrainteReferentielleException {
        MouvementDeBrouillard ancien = getMouvementsModifiables().get(index);
        ancien.detacher();
        getMouvementsModifiables().remove(index);
        return true;
    }

    /** Supprime uniquement l'instance fournie, sans utiliser equals(). */
    public boolean supprimerMouvement(final MouvementDeBrouillard mouvementASupprimer)
            throws EcritureMouvementViolationContrainteReferentielleException,
                   XComptaCoreNullPointerException {
        if (mouvementASupprimer == null) {
            throw new XComptaCoreNullPointerException("Le mouvement à supprimer est null.");
        }
        for (int i = 0; i < size(); i++) {
            if (getMouvementsModifiables().get(i) == mouvementASupprimer) {
                return supprimerMouvement(i);
            }
        }
        return false;
    }

    /** Remplace une instance de mouvement sans modifier la taille de la liste. */
    protected void remplacerUnMouvement(final MouvementDeBrouillard mouvement, final int index) {
        Objects.requireNonNull(mouvement, "Le mouvement de remplacement est null.");
        MouvementDeBrouillard ancien = getMouvementsModifiables().get(index);
        if (ancien == mouvement) {
            return;
        }
        for (MouvementDeBrouillard present : getMouvementsModifiables()) {
            if (present == mouvement) {
                throw new IllegalArgumentException("Ce mouvement est déjà présent dans l'écriture.");
            }
        }
        // Le nouveau mouvement est rattaché avant de toucher à l'ancien.
        mouvement.rattacherEcriture(this);
        try {
            ancien.detacher();
        } catch (EcritureMouvementViolationContrainteReferentielleException exception) {
            // Restaure le mouvement de remplacement si le détachement de l'ancien échoue.
            try {
                mouvement.detacher();
            } catch (EcritureMouvementViolationContrainteReferentielleException restauration) {
                exception.addSuppressed(restauration);
            }
            throw new IllegalStateException("Impossible de détacher l'ancien mouvement.", exception);
        }
        getMouvementsModifiables().set(index, mouvement);
    }

	/**
	 * Supprime les mouvements de brouillard encore vides.
	 *
	 * <p>
	 * Cette opération est propre au brouillard : elle permet de nettoyer les lignes
	 * de saisie restées vides avant un enregistrement ou une validation.
	 * </p>
	 * @throws EcritureMouvementViolationContrainteReferentielleException 
	 * @throws XComptaCoreNullPointerException 
	 */
	public void supprimerMouvementsVides() throws EcritureMouvementViolationContrainteReferentielleException, XComptaCoreNullPointerException {
	    Iterator<MouvementDeBrouillard> iterator =
	            getMouvementsModifiables().iterator();

	    while (iterator.hasNext()) {
	        MouvementDeBrouillard mouvement = iterator.next();

	        if (mouvement.estVide()) {
	            mouvement.detacher();
	            iterator.remove();
	        }
	    }
	}
		
	/**
	 * Construit une copie validée du brouillard après prévalidation.
	 *
	 * <p>
	 * La copie ne conserve pas les identifiants techniques de l'écriture ni ceux
	 * des mouvements. La substitution en base doit être réalisée par le service de
	 * persistance dans une transaction ; cette méthode ne réalise aucune opération
	 * SQL.
	 * </p>
	 *
	 * <p>
	 * FUTUR : conserver aussi le numéro de préenregistrement et attribuer le numéro
	 * définitif de manière transactionnelle.
	 * </p>
	 *
	 * @return nouvelle écriture validée
	 * @throws XComptaCoreException si les contrôles métier échouent
	 */
	public EcritureValidee copierPourValidation() throws XComptaCoreException {
// Construire la copie uniquement après une prévalidation réussie.
		testPreValidation();

		List<MouvementValidee> mouvementsValides = new ArrayList<>();

		for (MouvementDeBrouillard mouvement : getMouvements()) {
			mouvementsValides.add(new MouvementValidee(0, mouvement.getMontant(), mouvement.getCompte()));
		}

		return new EcritureValidee(0, getLibelle(), getDate(), getJournal(), mouvementsValides);
	}

	/**
	 * Compatibilité avec les appels existants : construit uniquement une copie
	 * métier. La validation définitive et la suppression du brouillard seront
	 * assurées ultérieurement par le service de persistance transactionnel.
	 *
	 * @return copie destinée à la validation
	 * @throws XComptaCoreException si le brouillard ne respecte pas les règles
	 *                              métier
	 */
	public EcritureValidee valider() throws XComptaCoreException {
		return copierPourValidation();
	}

	/**
	 * Prépare l'écriture avant l'application des règles de pré-validation.
	 *
	 * <p>
	 * Les mouvements vides utilisés pendant la saisie sont supprimés, puis tous les
	 * mouvements restants sont rattachés à cette écriture afin de restaurer
	 * l'intégrité référentielle avant les contrôles métier.
	 * </p>
	 * @throws EcritureMouvementViolationContrainteReferentielleException 
	 * @throws XComptaCoreNullPointerException 
	 */
	private void preparerValidation() throws EcritureMouvementViolationContrainteReferentielleException, XComptaCoreNullPointerException {

		supprimerMouvementsVides();

		for (MouvementDeBrouillard mouvement : getMouvements()) {
			mouvement.rattacherEcriture(this);
		}
	}

	/**
	 * Vérifie les invariants techniques immédiatement après la construction.
	 *
	 * <p>
	 * Ce contrôle ne vérifie pas que l'écriture est prête à être validée. Un
	 * brouillard nouvellement créé peut donc être vide et ne pas encore posséder de
	 * journal.
	 * </p>
	 *
	 * @throws EcritureException si l'objet est techniquement incohérent
	 * @throws XComptaCoreIllegalArgumentException 
	 * @throws XComptaCoreNullPointerException 
	 */
	@Override
	protected void testPostConstruction() throws EcritureException, XComptaCoreIllegalArgumentException, XComptaCoreNullPointerException {
//la seule contrainte pour une écriture de brouillard est d'avoir un id à 0
		// 0 : non persistée ; positif : identifiant déjà attribué.
		if (getId() < 0) {
			throw new XComptaCoreIllegalArgumentException(
					"L'identifiant d'une écriture de brouillard " + "ne peut pas être négatif.");
		}
	}

	/**
	 * Vérifie que l'écriture de brouillard peut être validée.
	 *
	 * <p>
	 * Ce contrôle vérifie les propriétés de l'écriture, l'intégrité référentielle
	 * de ses mouvements ainsi que les règles métier propres à chaque mouvement.
	 * </p>
	 *
	 * @throws XComptaCoreException si l'écriture ne peut pas être validée
	 */
	@Override
	public void testPreValidation() throws XComptaCoreException {
		preparerValidation();
		
		if (getDate() == null) {
			throw new DateInvalideException("La date de l'écriture de brouillard ne peut pas être null.");
		}

		if (getJournal() == null) {
			throw new XComptaCoreNullPointerException(contexteErreur() + " : le journal ne peut pas être null.");
		}

		if (getLibelle() == null || getLibelle().isBlank()) {
			throw new XComptaCoreNullPointerException(contexteErreur() + " : le libellé ne peut pas être null ou vide.");
		}

		if (size() < 2) {
			throw new EcritureNbMouvementsInferieurADeuxException(
					"Une écriture de brouillard doit comporter " + "au moins deux mouvements.");
		}

		testIntegriteDesMouvements();

		for (MouvementDeBrouillard mouvement : getMouvements()) {
			mouvement.testPreValidation();
		}

		if (!estEquilibree()) {
			throw new EcritureNonEquilibreeException("L'écriture de brouillard n'est pas équilibrée.");
		}

		if (getTotalDebit().signum() <= 0) {
			throw new EcritureMontantTotalAZeroException(
					"Le montant total d'une écriture doit être " + "strictement positif.");
		}

		testNonCompensation();

		// TODO métier : vérifier que la date appartient à l'exercice comptable.
	}

    /** Égalité complète, en évitant la récursion écriture ↔ mouvement. */
    @Override
    public boolean equals(final Object objet) {
        if (this == objet) return true;
        if (!(objet instanceof EcritureDeBrouillard autre)) return false;
        if (getId() != autre.getId()
                || !Objects.equals(getLibelle(), autre.getLibelle())
                || !Objects.equals(getDate(), autre.getDate())
                || !Objects.equals(getJournal(), autre.getJournal())
                || size() != autre.size()) return false;
        for (int i = 0; i < size(); i++) {
            MouvementDeBrouillard a = getMouvements().get(i);
            MouvementDeBrouillard c = autre.getMouvements().get(i);
            if (a.getId() != c.getId()
                    || !Objects.equals(a.getCompte(), c.getCompte())
                    || !memeMontant(a.getMontant(), c.getMontant())) return false;
        }
        return true;
    }

    private static boolean memeMontant(BigDecimal a, BigDecimal b) {
        return a == b || (a != null && b != null && a.compareTo(b) == 0);
    }

    @Override
    public int hashCode() {
        int resultat = Objects.hash(getId(), getLibelle(), getDate(), getJournal());
        for (MouvementDeBrouillard mouvement : getMouvements()) {
            BigDecimal montant = mouvement.getMontant();
            resultat = 31 * resultat + Objects.hash(mouvement.getId(), mouvement.getCompte(),
                    montant == null ? null : montant.stripTrailingZeros());
        }
        return resultat;
    }
}
