package fr.xcompta.core.base.test;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.MouvementDeBrouillard;

	/**
	 * Outils utilitaires communs aux tests.
	 */
	public final class TestTool {

	        // Classe utilitaire : aucune instance nécessaire.

	    /**
	     * Tire aléatoirement un entier compris entre les bornes indiquées,
	     * bornes incluses.
	     *
	     * @param min valeur minimale
	     * @param max valeur maximale
	     * @return un entier compris entre min et max inclus
	     * @throws IllegalArgumentException si min est supérieur à max
	     */
	    public static int entierAleatoire(final int min, final int max) {

	        if (min > max) {
	            throw new IllegalArgumentException(
	                    "La borne minimale ne peut pas être supérieure à la borne maximale.");
	        }

	        return ThreadLocalRandom.current().nextInt(min, max + 1);
	    }

	    /**
	     * Tire aléatoirement un montant BigDecimal non nul compris entre
	     * les bornes indiquées, avec deux décimales.
	     *
	     * <p>
	     * Les bornes sont exprimées en unités monétaires et sont incluses.
	     * La plage doit contenir au moins une valeur différente de zéro.
	     * </p>
	     *
	     * @param min montant minimal
	     * @param max montant maximal
	     * @return un montant aléatoire différent de zéro
	     * @throws IllegalArgumentException si la plage est invalide ou ne
	     *         contient aucune valeur différente de zéro
	     */
	    public static BigDecimal montantAleatoireNonNul(
	            final int min,
	            final int max) {

	        if (min > max) {
	            throw new IllegalArgumentException(
	                    "La borne minimale ne peut pas être supérieure à la borne maximale.");
	        }

	        if (min == 0 && max == 0) {
	            throw new IllegalArgumentException(
	                    "La plage doit contenir une valeur différente de zéro.");
	        }

	        int montant;

	        do {
	            montant = ThreadLocalRandom.current()
	                    .nextInt(min * 100, (max * 100) + 1);
	        } while (montant == 0);

	        return BigDecimal.valueOf(montant, 2);
	    }
	    
	    public static MouvementDeBrouillard mouvementAuHasard(EcritureDeBrouillard ecriture) {
	    	int index = entierAleatoire(0, ecriture.size()-1);
	    	return ecriture.getMouvement(index);
	    }
	}
	