package fr.xcompta.core.base;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Représente le solde d'un compte comptable à une date donnée.
 *
 * <p>Le montant est algébrique : positif au débit, négatif au crédit,
 * et éventuellement nul. L'identité métier repose exclusivement sur
 * le couple (compte, date), indépendamment du montant.</p>
 *
 * <h2>Calcul et conservation</h2>
 * <p>Il n'est pas nécessaire de produire un solde pour chaque journée
 * sans mouvement. Un solde demandé peut être obtenu à partir du dernier
 * solde fiable antérieur ou égal à la date recherchée, en y ajoutant
 * les mouvements des écritures validées de l'intervalle restant.</p>
 *
 * <p>À la clôture définitive d'une période, les soldes arrêtés servent
 * de références persistantes non modifiables. Avant cette clôture,
 * des soldes intermédiaires peuvent être calculés à la demande et
 * conservés en cache ; ils doivent être invalidés si des écritures
 * rétroactives changent les montants concernés.</p>
 *
 * <p>Le caractère définitif ou mis en cache relève de la persistance,
 * et non de l'identité ou des propriétés de cette classe. La recherche
 * du dernier solde, le calcul, la clôture et l'invalidation du cache
 * sont des responsabilités de services distincts, à développer.</p>
 *
 * <p>La clôture définitive exige l'absence d'écritures de brouillard
 * dans la période concernée ; une période de préparation peut encore
 * en contenir. Ces règles ne sont pas appliquées par cette classe.</p>
 *
 * @author Fabrice Luneau
 * @since 1.0
 */
public class Solde {


    /**
     * Date à laquelle le solde est arrêté.
     */
    private final LocalDate date;

    /**
     * Compte auquel appartient le solde.
     */
    private final Compte compte;

    /**
     * Montant algébrique du solde.
     *
     * <p>
     * Une valeur positive représente un solde débiteur et une valeur négative
     * représente un solde créditeur.
     * </p>
     */
    private final BigDecimal montant;

    /**
     * Construit le solde d'un compte à une date donnée.
     *
     * @param compte  compte concerné
     * @param date    date à laquelle le solde est arrêté
     * @param montant montant algébrique du solde
     * @throws NullPointerException si un argument est {@code null}
     */
    public Solde(
            final Compte compte,
            final LocalDate date,
            final BigDecimal montant) {

        this.compte = Objects.requireNonNull(
                compte,
                "Le compte du solde ne peut pas être null.");

        this.date = Objects.requireNonNull(
                date,
                "La date du solde ne peut pas être null.");

        this.montant = Objects.requireNonNull(
                montant,
                "Le montant du solde ne peut pas être null.");
    }

    public LocalDate getDate() {
        return date;
    }

    public Compte getCompte() {
        return compte;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    /**
     * Indique si le solde est nul.
     *
     * @return {@code true} si le montant est égal à zéro
     */
    public boolean isAZero() {
        return montant.signum() == 0;
    }

    /**
     * Indique si le solde est débiteur.
     *
     * @return {@code true} si le montant est strictement positif
     */
    public boolean isDebiteur() {
        return montant.signum() > 0;
    }

    /**
     * Indique si le solde est créditeur.
     *
     * @return {@code true} si le montant est strictement négatif
     */
    public boolean isCrediteur() {
        return montant.signum() < 0;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(compte, date);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Solde autre = (Solde) obj;
        return Objects.equals(compte, autre.compte)
                && Objects.equals(date, autre.date);
    }
}