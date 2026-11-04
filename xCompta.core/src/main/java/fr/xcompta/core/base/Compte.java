package fr.xcompta.core.base;

import java.util.Objects;

/**
 * Représente un compte du Plan Comptable Général (PCG).
 *
 * <p>
 * Le compte est une entité de référence du domaine métier. Il est utilisé par
 * les mouvements comptables qui composent les écritures.
 * </p>
 *
 * <h2>Règles métier</h2>
 * <ul>
 * <li>Le numéro constitue la clé métier du compte.</li>
 * <li>Le numéro est immuable après la création de l'objet.</li>
 * <li>Un numéro de compte comporte au minimum trois caractères.</li>
 * <li>Les trois premiers caractères sont obligatoirement numériques.</li>
 * <li>Le premier chiffre est compris entre 1 et 9.</li>
 * <li>Les deuxième et troisième chiffres ne peuvent pas être tous les deux
 * nuls.</li>
 * <li>À partir du quatrième caractère, le numéro peut contenir des chiffres
 * ou des lettres. La restriction aux seuls caractères ASCII est prévue,
 * mais pas encore appliquée par la validation actuelle.</li>
 * <li>Les lettres sont conservées en majuscules.</li>
 * <li>Les zéros terminaux sont conservés dans le numéro stocké.
 * Pour la comparaison des numéros exclusivement numériques, seuls les zéros
 * terminaux au-delà des trois premiers caractères sont ignorés :
 * {@code 530} reste {@code 530}, jamais {@code 53}.</li>
 * <li>Deux numéros numériques qui ne diffèrent que par des zéros terminaux
 * sont considérés comme représentant le même compte. Ainsi {@code 607} et
 * {@code 607000}, ou {@code 530} et {@code 530000}, sont équivalents.</li>
 * <li>Les comptes de tiers appartiennent à la classe 4.</li>
 * <li>Le libellé ne peut être ni {@code null}, ni vide ou composé
 * uniquement d’espaces après suppression des espaces périphériques.</li>
 * </ul>
 *
 * <h2>Représentation et formatage</h2>
 *
 * <p>
 * Le numéro stocké n'est pas modifié pour imposer une longueur d'affichage.
 * Le formatage est une opération distincte qui ajoute, si nécessaire, des
 * zéros à droite d'un numéro numérique.
 * </p>
 *
 * <p>
 * La méthode statique {@link #formatNumero(String, int)} permet notamment aux
 * interfaces utilisateur de formater un numéro sans disposer d'une instance
 * de {@code Compte}. La méthode {@link #getNumeroFormate(int)} constitue le
 * wrapper d'instance de cette méthode.
 * </p>
 *
 * <p>
 * Le solde d'un compte n'est volontairement pas porté par cette classe. Il est
 * déterminé à partir des mouvements comptables.
 * </p>
 *
 * <h2>Architecture</h2>
 *
 * <p>
 * Cette classe appartient au noyau métier de XCompta et ne dépend d'aucune
 * technologie de persistance.
 * </p>
 *
 * @author Fabrice Luneau
 */
public class Compte {

    /**
     * Longueur minimale d'un numéro de compte.
     */
    private static final int LONGUEUR_MINIMALE = 3;

    /**
     * Numéro du compte.
     *
     * <p>
     * Les espaces périphériques sont supprimés et les lettres sont stockées en
     * majuscules. Les zéros terminaux sont conservés.
     * </p>
     */
    private final String numero;

    /**
     * Libellé du compte.
     */
    private final String libelle;

    /**
     * Construit un compte en validant et normalisant son numéro et son libellé.
     *
     * @param numero numéro du compte
     * @param libelle libellé du compte
     * @throws NullPointerException si un argument est {@code null}
     * @throws IllegalArgumentException si le numéro ou le libellé est invalide
     */
    public Compte(final String numero, final String libelle) {
        this.numero = trimNumero(numero);
        this.libelle = normaliseLibelle(libelle);
    }

    /**
     * Retourne le numéro du compte tel qu'il est stocké.
     *
     * @return numéro du compte
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Retourne le libellé du compte.
     *
     * @return libellé du compte
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Normalise et valide un libellé de compte.
     *
     * <p>
     * Les espaces périphériques sont supprimés. Cette méthode est statique
     * afin que la même règle puisse être utilisée sans disposer d'une instance
     * de {@code Compte}, notamment par une interface utilisateur.
     * </p>
     *
     * @param libelle libellé à normaliser
     * @return libellé normalisé
     * @throws NullPointerException si le libellé est {@code null}
     * @throws IllegalArgumentException si le libellé est vide
     */
    public static String normaliseLibelle(final String libelle) {
        Objects.requireNonNull(
                libelle,
                "Le libellé du compte ne peut pas être null.");

        String valeur = libelle.trim();

        if (valeur.isEmpty()) {
            throw new IllegalArgumentException(
                    "Le libellé du compte ne peut pas être vide.");
        }

        return valeur;
    }

    /**
     * Normalise et valide un numéro de compte.
     *
     * <p>
     * La normalisation supprime les espaces périphériques et convertit les
     * lettres en majuscules. Elle ne supprime pas les zéros du numéro.
     * </p>
     *
     * <p>
     * Les trois premiers caractères doivent être numériques. Le premier doit
     * être compris entre 1 et 9. Les deuxième et troisième chiffres ne peuvent
     * pas être tous les deux nuls.
     * </p>
     *
     * <p>
     * À partir du quatrième caractère, les chiffres et les lettres sont
     * autorisés.
     * </p>
     *
     * <p>
     * Exemples valides :
     * </p>
     *
     * <ul>
     * <li>{@code 401}</li>
     * <li>{@code 401001}</li>
     * <li>{@code 401DUPONT}</li>
     * <li>{@code 530}</li>
     * <li>{@code 530000}</li>
     * <li>{@code 607}</li>
     * <li>{@code 607000}</li>
     * </ul>
     *
     * @param numeroToTrim numéro à normaliser
     * @return numéro normalisé
     * @throws NullPointerException si le numéro est {@code null}
     * @throws IllegalArgumentException si le numéro ne respecte pas les règles
     *         métier
     */
    public static String trimNumero(final String numeroToTrim) {
        Objects.requireNonNull(
                numeroToTrim,
                "Le numéro de compte ne peut pas être null.");

        String numero = numeroToTrim.trim().toUpperCase();

        if (numero.length() < LONGUEUR_MINIMALE) {
            throw new IllegalArgumentException(
                    "Un numéro de compte doit comporter au minimum "
                            + LONGUEUR_MINIMALE + " caractères.");
        }

        if (numero.charAt(0) < '1' || numero.charAt(0) > '9') {
            throw new IllegalArgumentException(
                    "Un numéro de compte doit commencer par un chiffre de 1 à 9.");
        }

        if (!Character.isDigit(numero.charAt(1))
                || !Character.isDigit(numero.charAt(2))) {
            throw new IllegalArgumentException(
                    "Les trois premiers caractères d'un numéro de compte "
                            + "doivent être numériques.");
        }

        if (numero.charAt(1) == '0' && numero.charAt(2) == '0') {
            throw new IllegalArgumentException(
                    "Les deuxième et troisième chiffres d'un numéro de compte "
                            + "ne peuvent pas être tous les deux nuls.");
        }

        for (int i = LONGUEUR_MINIMALE; i < numero.length(); i++) {
            if (!Character.isLetterOrDigit(numero.charAt(i))) {
                throw new IllegalArgumentException(
                        "Un numéro de compte ne peut contenir que des chiffres "
                                + "et des lettres.");
            }
        }

        return numero;
    }

    /**
     * Formate un numéro de compte pour son affichage.
     *
     * <p>
     * Cette méthode est statique afin de pouvoir être utilisée lorsqu'aucune
     * instance de {@code Compte} n'est disponible, notamment depuis une
     * interface utilisateur.
     * </p>
     *
     * <p>
     * Un numéro exclusivement numérique est complété à droite par des zéros
     * jusqu'à atteindre la longueur demandée.
     * </p>
     *
     * <p>
     * Un numéro alphanumérique, par exemple {@code 401DUPONT}, est retourné
     * tel quel. Un numéro plus long que la longueur demandée n'est jamais
     * tronqué.
     * </p>
     *
     * @param numeroToFormat numéro à formater
     * @param longueur longueur d'affichage souhaitée
     * @return numéro formaté
     * @throws NullPointerException si le numéro est {@code null}
     * @throws IllegalArgumentException si le numéro ou la longueur est invalide
     */
    public static String formatNumero(
            final String numeroToFormat,
            final int longueur) {

        if (longueur < LONGUEUR_MINIMALE) {
            throw new IllegalArgumentException(
                    "La longueur doit être au minimum de "
                            + LONGUEUR_MINIMALE + " caractères.");
        }

        String numero = trimNumero(numeroToFormat);

        if (numero.chars().anyMatch(Character::isLetter)) {
            return numero;
        }

        if (numero.length() >= longueur) {
            return numero;
        }

        return numero + "0".repeat(longueur - numero.length());
    }

    /**
     * Retourne le numéro de la classe comptable à laquelle appartient le
     * compte.
     *
     * @return numéro de la classe comptable
     */
    public byte getNumeroClasse() {
        return (byte) Character.digit(numero.charAt(0), 10);
    }

    /**
     * Indique si le compte est un compte de tiers.
     *
     * <p>
     * Les comptes de tiers appartiennent à la classe 4. Cette règle s'applique
     * aussi bien aux comptes numériques qu'aux comptes individualisés
     * alphanumériques.
     * </p>
     *
     * <p>
     * Par exemple {@code 401} (fournisseurs), {@code 404} (fournisseurs
     * d’immobilisations) et {@code 411} (clients) sont des comptes de tiers.
     * Un compte individualisé tel que {@code 401DUPONT} appartient également
     * à cette classe.
     * </p>
     *
     * @return {@code true} si le compte appartient à la classe 4
     */
    public boolean estCompteDeTiers() {
        return getNumeroClasse() == 4;
    }

    /**
     * Retourne le numéro du compte formaté pour l'affichage.
     *
     * <p>
     * Cette méthode constitue le wrapper d'instance de
     * {@link #formatNumero(String, int)}.
     * </p>
     *
     * @param longueur longueur d'affichage souhaitée
     * @return numéro formaté
     * @throws IllegalArgumentException si la longueur est inférieure à trois
     */
    public String getNumeroFormate(final int longueur) {
        return formatNumero(numero, longueur);
    }

    /**
     * Retourne la forme canonique du numéro utilisée pour l'égalité métier.
     *
     * <p>
     * Pour un numéro exclusivement numérique, les zéros terminaux situés
     * au-delà des trois premiers caractères sont ignorés. Ainsi {@code 607}
     * et {@code 607000}, ou {@code 530} et {@code 530000}, possèdent la même
     * forme canonique.
     * </p>
     *
     * <p>
     * Les numéros alphanumériques sont retournés tels quels.
     * </p>
     *
     * @param numeroToCanonicalize numéro à convertir
     * @return forme canonique du numéro
     */
    private static String numeroCanonique(
            final String numeroToCanonicalize) {

        if (numeroToCanonicalize.chars().anyMatch(Character::isLetter)) {
            return numeroToCanonicalize;
        }

        int fin = numeroToCanonicalize.length();

        while (fin > LONGUEUR_MINIMALE
                && numeroToCanonicalize.charAt(fin - 1) == '0') {
            fin--;
        }

        return numeroToCanonicalize.substring(0, fin);
    }

    /**
     * Retourne une représentation textuelle du compte.
     *
     * @return numéro formaté sur au moins six caractères, sans troncature,
     *         suivi du libellé
     */
    @Override
    public String toString() {
        return getNumeroFormate(6) + " - " + libelle;
    }

    /**
     * Calcule le code de hachage à partir de la forme canonique du numéro.
     * Le libellé n’intervient pas.
     *
     * @return code de hachage
     */
    @Override
    public int hashCode() {
        return numeroCanonique(numero).hashCode();
    }

    /**
     * Compare deux comptes selon leur numéro comptable.
     *
     * <p>
     * Pour les comptes numériques, les zéros terminaux situés au-delà des
     * trois premiers caractères ne participent pas à l'égalité métier.
     * Ainsi un compte {@code 607} est égal à un compte {@code 607000}.
     * Les trois premiers caractères sont toujours conservés dans la forme
     * canonique. Le libellé ne participe
     * pas à la comparaison.
     * </p>
     *
     * @param obj objet à comparer
     * @return {@code true} si les deux comptes représentent le même compte
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Compte other)) {
            return false;
        }

        return numeroCanonique(numero)
                .equals(numeroCanonique(other.numero));
    }
}