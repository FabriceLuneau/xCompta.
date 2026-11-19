package fr.xcompta.core.port;

import java.time.LocalDate;
import java.util.List;
import fr.xcompta.core.date.exception.DateInvalideException;

/**
 * Port d'extraction comptable avec critère de sélection obligatoire.
 *
 * <p>Le critère est un compte ou un journal selon le port spécialisé.
 * Il n'existe aucune extraction sans critère. Les bornes sont inclusives,
 * et null signifie seulement une borne temporelle absente.</p>
 * <p>Le brouillard est exclu par défaut.</p>
 *
 * @param <T> type des éléments retournés
 * @param <K> type du critère de sélection
 * @author Fabrice Luneau
 */
public interface Extractable<T, K> {

    /**
     * Extrait les éléments correspondant au critère et à la période.
     * L'adaptateur doit vérifier les deux dates et le critère.
     *
     * @param debut date de début inclusive, éventuellement null
     * @param fin date de fin inclusive, éventuellement null
     * @param critere critère obligatoire, jamais null
     * @param inclureBrouillard true pour inclure les brouillards
     * @return liste, éventuellement vide, jamais null
     * @throws NullPointerException si le critère est null
     * @throws DateInvalideException si fin est antérieure à debut
     */
    List<T> get(LocalDate debut, LocalDate fin, K critere,
            boolean inclureBrouillard) throws DateInvalideException;

    /** Extrait une période sans brouillard. */
    default List<T> get(LocalDate debut, LocalDate fin, K critere)
            throws DateInvalideException {
        return get(debut, fin, critere, false);
    }

    /** Extrait toutes les dates pour un critère donné. */
    default List<T> getTout(K critere, boolean inclureBrouillard)
            throws DateInvalideException {
        return get(null, null, critere, inclureBrouillard);
    }

    /** Extrait toutes les dates pour un critère donné, sans brouillard. */
    default List<T> getTout(K critere) throws DateInvalideException {
        return getTout(critere, false);
    }

    /** Extrait jusqu'à la date de fin incluse. */
    default List<T> getAvant(LocalDate fin, K critere, boolean inclureBrouillard)
            throws DateInvalideException {
        return get(null, fin, critere, inclureBrouillard);
    }

    /** Extrait jusqu'à la date de fin incluse, sans brouillard. */
    default List<T> getAvant(LocalDate fin, K critere)
            throws DateInvalideException {
        return getAvant(fin, critere, false);
    }

    /** Extrait depuis la date de début incluse. */
    default List<T> getApres(LocalDate debut, K critere, boolean inclureBrouillard)
            throws DateInvalideException {
        return get(debut, null, critere, inclureBrouillard);
    }

    /** Extrait depuis la date de début incluse, sans brouillard. */
    default List<T> getApres(LocalDate debut, K critere)
            throws DateInvalideException {
        return getApres(debut, critere, false);
    }
}
