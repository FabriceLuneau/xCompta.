package fr.xcompta.core.base.test.compte;

import java.util.stream.Stream;

/**
 * Jeu de données complet utilisé par les tests paramétrés de {@code Compte}.
 *
 * <p>
 * Le jeu reprend l'ensemble des comptes présents dans le fichier XML du plan
 * comptable fourni à XCompta, auquel sont ajoutés les comptes auxiliaires de
 * fournisseurs, fournisseurs d'immobilisations, clients et caisses utilisés
 * par les tests.
 * </p>
 *
 * <p>
 * Les comptes sont répartis par classe afin de conserver un fichier source
 * lisible et d'éviter une méthode de construction unique trop volumineuse.
 * </p>
 *
 * @author Fabrice Luneau
 */
final class JeuDonneesCompte {

    private JeuDonneesCompte() {
        // Classe utilitaire : pas d'instanciation.
    }

    /**
     * Décrit les valeurs attendues pour un compte.
     *
     * @param numero numéro du compte tel qu’il doit être stocké
     * @param libelle libellé du compte
     * @param numeroClasse numéro de classe comptable attendu
     * @param numeroFormate6 numéro attendu sur six caractères
         */
    record DonneesCompte(
            String numero,
            String libelle,
            byte numeroClasse,
            String numeroFormate6) {
    }

    /**
     * Fournit l'ensemble du plan de comptes et les comptes auxiliaires de test.
     *
     * @return flux complet des comptes de test
     */
    static Stream<DonneesCompte> fournirComptes() {
        return Stream.of(
                fournirClasse1(),
                fournirClasse2(),
                fournirClasse3(),
                fournirClasse4(),
                fournirClasse5(),
                fournirClasse6(),
                fournirClasse7(),
                fournirClasse8(),
                fournirClasse9())
                .flatMap(stream -> stream);
    }

    /**
     * Fournit les comptes de classe 1.
     *
     * @return flux des comptes de classe 1
     */
    private static Stream<DonneesCompte> fournirClasse1() {
        return Stream.of(
                new DonneesCompte(
                        "110",
                        "Report à nouveau (solde créditeur)",
                        (byte) 1,
                        "110000"),

                new DonneesCompte(
                        "120",
                        "Résultat de l'exercice (bénéfice)",
                        (byte) 1,
                        "120000"),

                new DonneesCompte(
                        "130",
                        "Subventions d'investissement",
                        (byte) 1,
                        "130000"),

                new DonneesCompte(
                        "140",
                        "Provisions réglementées",
                        (byte) 1,
                        "140000"),

                new DonneesCompte(
                        "150",
                        "Provisions pour risques et charges",
                        (byte) 1,
                        "150000"),

                new DonneesCompte(
                        "160",
                        "Emprunts et dettes assimilées",
                        (byte) 1,
                        "160000"),

                new DonneesCompte(
                        "170",
                        "Dettes rattachées à des participations",
                        (byte) 1,
                        "170000"),

                new DonneesCompte(
                        "180",
                        "Comptes de liaison des établissements et sociétés en participation",
                        (byte) 1,
                        "180000"),

                new DonneesCompte(
                        "101",
                        "Capital",
                        (byte) 1,
                        "101000"),

                new DonneesCompte(
                        "104",
                        "Primes liées au capital social",
                        (byte) 1,
                        "104000"),

                new DonneesCompte(
                        "105",
                        "Ecarts de réévaluation",
                        (byte) 1,
                        "105000"),

                new DonneesCompte(
                        "106",
                        "Réserves",
                        (byte) 1,
                        "106000"),

                new DonneesCompte(
                        "107",
                        "Ecart d'équivalence",
                        (byte) 1,
                        "107000"),

                new DonneesCompte(
                        "108",
                        "Compte de l'exploitant",
                        (byte) 1,
                        "108000"),

                new DonneesCompte(
                        "109",
                        "Actionnaires : capital souscrit - non appelé",
                        (byte) 1,
                        "109000"),

                new DonneesCompte(
                        "119",
                        "Report à nouveau (solde débiteur)",
                        (byte) 1,
                        "119000"),

                new DonneesCompte(
                        "129",
                        "Résultat de l'exercice (perte)",
                        (byte) 1,
                        "129000"),

                new DonneesCompte(
                        "131",
                        "Subventions d'équipement",
                        (byte) 1,
                        "131000"),

                new DonneesCompte(
                        "138",
                        "Autres subventions d'investissement",
                        (byte) 1,
                        "138000"),

                new DonneesCompte(
                        "139",
                        "Subventions d'investissement inscrites au compte de résultat",
                        (byte) 1,
                        "139000"),

                new DonneesCompte(
                        "142",
                        "Provisions réglementées relatives aux immobilisations",
                        (byte) 1,
                        "142000"),

                new DonneesCompte(
                        "143",
                        "Provisions réglementées relatives aux stocks",
                        (byte) 1,
                        "143000"),

                new DonneesCompte(
                        "144",
                        "Provisions réglementées relatives aux autres élémentse l'actif",
                        (byte) 1,
                        "144000"),

                new DonneesCompte(
                        "145",
                        "Amortissements dérogatoires",
                        (byte) 1,
                        "145000"),

                new DonneesCompte(
                        "146",
                        "Provision spéciale de réévaluation",
                        (byte) 1,
                        "146000"),

                new DonneesCompte(
                        "147",
                        "Plus-values réinvesties",
                        (byte) 1,
                        "147000"),

                new DonneesCompte(
                        "148",
                        "Autres provisions réglementées",
                        (byte) 1,
                        "148000"),

                new DonneesCompte(
                        "151",
                        "Provisions pour risques",
                        (byte) 1,
                        "151000"),

                new DonneesCompte(
                        "153",
                        "Provisions pour pensions et obligations similaires",
                        (byte) 1,
                        "153000"),

                new DonneesCompte(
                        "154",
                        "Provisions pour restructurations",
                        (byte) 1,
                        "154000"),

                new DonneesCompte(
                        "155",
                        "Provisions pour impôts",
                        (byte) 1,
                        "155000"),

                new DonneesCompte(
                        "156",
                        "Provisions pour renouvellementes d'mmobilisations (entreprises concessionnaires)",
                        (byte) 1,
                        "156000"),

                new DonneesCompte(
                        "157",
                        "Provisions pour charges à répartir sur plusieursxercices",
                        (byte) 1,
                        "157000"),

                new DonneesCompte(
                        "158",
                        "Autres provisions pour charges",
                        (byte) 1,
                        "158000"),

                new DonneesCompte(
                        "161",
                        "Emprunts obligataires convertibles",
                        (byte) 1,
                        "161000"),

                new DonneesCompte(
                        "163",
                        "Autres emprunts obligataires",
                        (byte) 1,
                        "163000"),

                new DonneesCompte(
                        "164",
                        "Emprunts auprès des établissements de crédit",
                        (byte) 1,
                        "164000"),

                new DonneesCompte(
                        "165",
                        "Dépôts et cautionnements reçus",
                        (byte) 1,
                        "165000"),

                new DonneesCompte(
                        "166",
                        "Participation des salariés aux résultats",
                        (byte) 1,
                        "166000"),

                new DonneesCompte(
                        "167",
                        "Emprunts et dettes assortis de conditions particulières",
                        (byte) 1,
                        "167000"),

                new DonneesCompte(
                        "168",
                        "Autres emprunts et dettes assimilées",
                        (byte) 1,
                        "168000"),

                new DonneesCompte(
                        "169",
                        "Primes de remboursement des obligations",
                        (byte) 1,
                        "169000"),

                new DonneesCompte(
                        "171",
                        "Dettes rattachées à des participations (groupe)",
                        (byte) 1,
                        "171000"),

                new DonneesCompte(
                        "174",
                        "Dettes rattachées à des participations (hors groupe)",
                        (byte) 1,
                        "174000"),

                new DonneesCompte(
                        "178",
                        "Dettes rattachées à des sociétésn participation",
                        (byte) 1,
                        "178000"),

                new DonneesCompte(
                        "181",
                        "Comptes de liaison des établissements",
                        (byte) 1,
                        "181000"),

                new DonneesCompte(
                        "186",
                        "Biens et prestations de services échangés entre établissements (charges)",
                        (byte) 1,
                        "186000"),

                new DonneesCompte(
                        "187",
                        "Biens et prestations de services échangés entre établissements (produits)",
                        (byte) 1,
                        "187000"),

                new DonneesCompte(
                        "188",
                        "Comptes de liaison des sociétés en participation",
                        (byte) 1,
                        "188000"),

                new DonneesCompte(
                        "1011",
                        "Capital souscrit - non appelé",
                        (byte) 1,
                        "101100"),

                new DonneesCompte(
                        "1012",
                        "Capital souscrit - appelé, non versé",
                        (byte) 1,
                        "101200"),

                new DonneesCompte(
                        "1013",
                        "Capital souscrit - appelé, versé",
                        (byte) 1,
                        "101300"),

                new DonneesCompte(
                        "1018",
                        "Capital souscrit soumis à des réglementations particulières",
                        (byte) 1,
                        "101800"),

                new DonneesCompte(
                        "1041",
                        "Primes d'émission",
                        (byte) 1,
                        "104100"),

                new DonneesCompte(
                        "1042",
                        "Primes de fusion",
                        (byte) 1,
                        "104200"),

                new DonneesCompte(
                        "1043",
                        "Primes d'apport",
                        (byte) 1,
                        "104300"),

                new DonneesCompte(
                        "1044",
                        "Primes de conversion d'obligations en actions",
                        (byte) 1,
                        "104400"),

                new DonneesCompte(
                        "1045",
                        "Bons de souscription d'actions",
                        (byte) 1,
                        "104500"),

                new DonneesCompte(
                        "1051",
                        "Réserve spéciale de réévaluation",
                        (byte) 1,
                        "105100"),

                new DonneesCompte(
                        "1052",
                        "Ecart de réévaluation libre",
                        (byte) 1,
                        "105200"),

                new DonneesCompte(
                        "1053",
                        "Réserve de réévaluation",
                        (byte) 1,
                        "105300"),

                new DonneesCompte(
                        "1055",
                        "Ecarts de réévaluation (autres opérations légales)",
                        (byte) 1,
                        "105500"),

                new DonneesCompte(
                        "1057",
                        "Autres écarts de réévaluation en France",
                        (byte) 1,
                        "105700"),

                new DonneesCompte(
                        "1058",
                        "Autres écarts de réévaluation à l'étranger",
                        (byte) 1,
                        "105800"),

                new DonneesCompte(
                        "1061",
                        "Réserve légale",
                        (byte) 1,
                        "106100"),

                new DonneesCompte(
                        "1062",
                        "Réserves indisponibles",
                        (byte) 1,
                        "106200"),

                new DonneesCompte(
                        "1063",
                        "Réserves statutaires ou contractuelles",
                        (byte) 1,
                        "106300"),

                new DonneesCompte(
                        "1064",
                        "Réserves réglementées",
                        (byte) 1,
                        "106400"),

                new DonneesCompte(
                        "1068",
                        "Autres réserves",
                        (byte) 1,
                        "106800"),

                new DonneesCompte(
                        "1311",
                        "Subventions d'équipement - Etat",
                        (byte) 1,
                        "131100"),

                new DonneesCompte(
                        "1312",
                        "Subventions d'équipement - Régions",
                        (byte) 1,
                        "131200"),

                new DonneesCompte(
                        "1313",
                        "Subventions d'équipement - Départements",
                        (byte) 1,
                        "131300"),

                new DonneesCompte(
                        "1314",
                        "Subventions d'équipement - Communes",
                        (byte) 1,
                        "131400"),

                new DonneesCompte(
                        "1315",
                        "Subventions d'équipement - Collectivités publiques",
                        (byte) 1,
                        "131500"),

                new DonneesCompte(
                        "1316",
                        "Subventions d'équipement - Entreprises publiques",
                        (byte) 1,
                        "131600"),

                new DonneesCompte(
                        "1317",
                        "Subventions d'équipement - Entreprises et organismes privés",
                        (byte) 1,
                        "131700"),

                new DonneesCompte(
                        "1318",
                        "Subventions d'équipement - Autres",
                        (byte) 1,
                        "131800"),

                new DonneesCompte(
                        "1381",
                        "Subventions d'investissement - Etat",
                        (byte) 1,
                        "138100"),

                new DonneesCompte(
                        "1382",
                        "Subventions d'investissement - Régions",
                        (byte) 1,
                        "138200"),

                new DonneesCompte(
                        "1383",
                        "Subventions d'investissement - Départements",
                        (byte) 1,
                        "138300"),

                new DonneesCompte(
                        "1384",
                        "Subventions d'investissement - Communes",
                        (byte) 1,
                        "138400"),

                new DonneesCompte(
                        "1385",
                        "Subventions d'investissement - Collectivités publiques",
                        (byte) 1,
                        "138500"),

                new DonneesCompte(
                        "1386",
                        "Subventions d'investissement - Entreprises publiques",
                        (byte) 1,
                        "138600"),

                new DonneesCompte(
                        "1387",
                        "Subventions d'investissement - Entreprises et organismes privés",
                        (byte) 1,
                        "138700"),

                new DonneesCompte(
                        "1388",
                        "Subventions d'investissement - Autres",
                        (byte) 1,
                        "138800"),

                new DonneesCompte(
                        "1391",
                        "Subventions d'équipement",
                        (byte) 1,
                        "139100"),

                new DonneesCompte(
                        "1398",
                        "Autres subventions d'investissement",
                        (byte) 1,
                        "139800"),

                new DonneesCompte(
                        "1423",
                        "Provisions pour reconstitutiones gisement miners et pétroliers",
                        (byte) 1,
                        "142300"),

                new DonneesCompte(
                        "1424",
                        "Provisions pour investissement (participationes salariés)",
                        (byte) 1,
                        "142400"),

                new DonneesCompte(
                        "1431",
                        "Hausse des prix",
                        (byte) 1,
                        "143100"),

                new DonneesCompte(
                        "1432",
                        "Fluctuation des cours",
                        (byte) 1,
                        "143200"),

                new DonneesCompte(
                        "1511",
                        "Provisions pour litiges",
                        (byte) 1,
                        "151100"),

                new DonneesCompte(
                        "1512",
                        "Provisions pour garanties données aux clients",
                        (byte) 1,
                        "151200"),

                new DonneesCompte(
                        "1513",
                        "Provisions pour pertes sur marchés à terme",
                        (byte) 1,
                        "151300"),

                new DonneesCompte(
                        "1514",
                        "Provisions pour amendes et pénalités",
                        (byte) 1,
                        "151400"),

                new DonneesCompte(
                        "1515",
                        "Provisions pour pertes de change",
                        (byte) 1,
                        "151500"),

                new DonneesCompte(
                        "1516",
                        "Provisions pour pertes sur contrats",
                        (byte) 1,
                        "151600"),

                new DonneesCompte(
                        "1518",
                        "Autres provisions pour risques",
                        (byte) 1,
                        "151800"),

                new DonneesCompte(
                        "1572",
                        "Provisions pour gros entretien ou grandes révisions",
                        (byte) 1,
                        "157200"),

                new DonneesCompte(
                        "1581",
                        "Provisions pour remises en état",
                        (byte) 1,
                        "158100"),

                new DonneesCompte(
                        "1651",
                        "Dépôts reçus",
                        (byte) 1,
                        "165100"),

                new DonneesCompte(
                        "1655",
                        "Cautionnements reçus",
                        (byte) 1,
                        "165500"),

                new DonneesCompte(
                        "1661",
                        "Comptes bloqués",
                        (byte) 1,
                        "166100"),

                new DonneesCompte(
                        "1662",
                        "Fonds de participation",
                        (byte) 1,
                        "166200"),

                new DonneesCompte(
                        "1671",
                        "Emissionse titres participatifs",
                        (byte) 1,
                        "167100"),

                new DonneesCompte(
                        "1674",
                        "Avances conditionnées de l'Etat",
                        (byte) 1,
                        "167400"),

                new DonneesCompte(
                        "1675",
                        "Emprunts participatifs",
                        (byte) 1,
                        "167500"),

                new DonneesCompte(
                        "1681",
                        "Autres emprunts",
                        (byte) 1,
                        "168100"),

                new DonneesCompte(
                        "1685",
                        "Rentes viagères capitalisées",
                        (byte) 1,
                        "168500"),

                new DonneesCompte(
                        "1687",
                        "Autres dettes",
                        (byte) 1,
                        "168700"),

                new DonneesCompte(
                        "1688",
                        "Intérêts courus",
                        (byte) 1,
                        "168800"),

                new DonneesCompte(
                        "1781",
                        "Principal",
                        (byte) 1,
                        "178100"),

                new DonneesCompte(
                        "1788",
                        "Intérêts courus",
                        (byte) 1,
                        "178800"),

                new DonneesCompte(
                        "10131",
                        "Capital non amorti",
                        (byte) 1,
                        "101310"),

                new DonneesCompte(
                        "10132",
                        "Capital amorti",
                        (byte) 1,
                        "101320"),

                new DonneesCompte(
                        "10611",
                        "Réserve légale proprement dite",
                        (byte) 1,
                        "106110"),

                new DonneesCompte(
                        "10612",
                        "Plus-values nettes à long terme",
                        (byte) 1,
                        "106120"),

                new DonneesCompte(
                        "10641",
                        "Plus-values nette à long terme",
                        (byte) 1,
                        "106410"),

                new DonneesCompte(
                        "10643",
                        "Réserves consécutives à l'octroi de subventions d'investissemnt",
                        (byte) 1,
                        "106430"),

                new DonneesCompte(
                        "10648",
                        "Autres réserves réglementées",
                        (byte) 1,
                        "106480"),

                new DonneesCompte(
                        "10681",
                        "Réserve de propre assureur",
                        (byte) 1,
                        "106810"),

                new DonneesCompte(
                        "10688",
                        "Réserve diverses",
                        (byte) 1,
                        "106880"),

                new DonneesCompte(
                        "13911",
                        "Subventions d'équipement - Etat",
                        (byte) 1,
                        "139110"),

                new DonneesCompte(
                        "13912",
                        "Subventions d'équipement - Région",
                        (byte) 1,
                        "139120"),

                new DonneesCompte(
                        "13913",
                        "Subventions d'équipement - Département",
                        (byte) 1,
                        "139130"),

                new DonneesCompte(
                        "13914",
                        "Subventions d'équipement - Commune",
                        (byte) 1,
                        "139140"),

                new DonneesCompte(
                        "13915",
                        "Subventions d'équipement - Collectivités publiques",
                        (byte) 1,
                        "139150"),

                new DonneesCompte(
                        "13916",
                        "Subventions d'équipement - Entreprises publiques",
                        (byte) 1,
                        "139160"),

                new DonneesCompte(
                        "13917",
                        "Subventions d'équipement - Entreprises et organismes privés",
                        (byte) 1,
                        "139170"),

                new DonneesCompte(
                        "13918",
                        "Subventions d'équipement - Autres",
                        (byte) 1,
                        "139180"),

                new DonneesCompte(
                        "13981",
                        "Autres subventions d'investissement - Etat",
                        (byte) 1,
                        "139810"),

                new DonneesCompte(
                        "13982",
                        "Autres subventions d'investissement - Région",
                        (byte) 1,
                        "139820"),

                new DonneesCompte(
                        "13983",
                        "Autres subventions d'investissement - Département",
                        (byte) 1,
                        "139830"),

                new DonneesCompte(
                        "13984",
                        "Autres subventions d'investissement - Commune",
                        (byte) 1,
                        "139840"),

                new DonneesCompte(
                        "13985",
                        "Autres subventions d'investissement - Collectivités publiques",
                        (byte) 1,
                        "139850"),

                new DonneesCompte(
                        "13986",
                        "Autres subventions d'investissement - Entreprises publiques",
                        (byte) 1,
                        "139860"),

                new DonneesCompte(
                        "13987",
                        "Autres subventions d'investissement - Entreprises et organismes privés",
                        (byte) 1,
                        "139870"),

                new DonneesCompte(
                        "13988",
                        "Autres subventions d'investissement - Autres",
                        (byte) 1,
                        "139880"),

                new DonneesCompte(
                        "16881",
                        "Intérêts courus - Sur emprunts obligataires convertibles",
                        (byte) 1,
                        "168810"),

                new DonneesCompte(
                        "16883",
                        "Intérêts courus - Sur autres emprunts obligataires",
                        (byte) 1,
                        "168830"),

                new DonneesCompte(
                        "16884",
                        "Intérêts courus - Sur emprunts auprès des établissements de crédits",
                        (byte) 1,
                        "168840"),

                new DonneesCompte(
                        "16885",
                        "Intérêts courus - Sur dépôt et cautionnements reçus",
                        (byte) 1,
                        "168850"),

                new DonneesCompte(
                        "16886",
                        "Intérêts courus - Sur participations des salariés aux résultat",
                        (byte) 1,
                        "168860"),

                new DonneesCompte(
                        "16887",
                        "Intérêts courus - Sur emprunts et dettes assortis de conditions particulières",
                        (byte) 1,
                        "168870"),

                new DonneesCompte(
                        "16888",
                        "Intérêts courus - Sur autres emprunts et dettes assimilées",
                        (byte) 1,
                        "168880"));
    }

    /**
     * Fournit les comptes de classe 2.
     *
     * @return flux des comptes de classe 2
     */
    private static Stream<DonneesCompte> fournirClasse2() {
        return Stream.of(
                new DonneesCompte(
                        "210",
                        "Immobilisations corporelles",
                        (byte) 2,
                        "210000"),

                new DonneesCompte(
                        "220",
                        "Immobilisations mise en concession",
                        (byte) 2,
                        "220000"),

                new DonneesCompte(
                        "230",
                        "Immobilisations en cours",
                        (byte) 2,
                        "230000"),

                new DonneesCompte(
                        "250",
                        "Parts dans des entreprises liées et créances sur des entreprises liées",
                        (byte) 2,
                        "250000"),

                new DonneesCompte(
                        "260",
                        "Participations et créances rattachées à des participations",
                        (byte) 2,
                        "260000"),

                new DonneesCompte(
                        "270",
                        "Autres immobilisations financières",
                        (byte) 2,
                        "270000"),

                new DonneesCompte(
                        "280",
                        "Amortissements immobilisations incorporelles",
                        (byte) 2,
                        "280000"),

                new DonneesCompte(
                        "290",
                        "Dépréciations des immobilisations incorporelles",
                        (byte) 2,
                        "290000"),

                new DonneesCompte(
                        "201",
                        "Frais d'établissement",
                        (byte) 2,
                        "201000"),

                new DonneesCompte(
                        "203",
                        "Fraise de recherche et de développement",
                        (byte) 2,
                        "203000"),

                new DonneesCompte(
                        "205",
                        "Concessions et droits similaires, brevets, licences,arques, procédés, logiciels,roitst valeurs similaires",
                        (byte) 2,
                        "205000"),

                new DonneesCompte(
                        "206",
                        "Droit au bail",
                        (byte) 2,
                        "206000"),

                new DonneesCompte(
                        "207",
                        "Fonds commercial",
                        (byte) 2,
                        "207000"),

                new DonneesCompte(
                        "208",
                        "Autres immobilisations incorporelles",
                        (byte) 2,
                        "208000"),

                new DonneesCompte(
                        "211",
                        "Terrains",
                        (byte) 2,
                        "211000"),

                new DonneesCompte(
                        "212",
                        "Agencement et aménagements de terrains",
                        (byte) 2,
                        "212000"),

                new DonneesCompte(
                        "213",
                        "Constructions",
                        (byte) 2,
                        "213000"),

                new DonneesCompte(
                        "214",
                        "Constructions sur sol d'autrui",
                        (byte) 2,
                        "214000"),

                new DonneesCompte(
                        "215",
                        "Installations techniques, matériels et outillages industriels",
                        (byte) 2,
                        "215000"),

                new DonneesCompte(
                        "218",
                        "Autres immobilisations corporelles",
                        (byte) 2,
                        "218000"),

                new DonneesCompte(
                        "231",
                        "Immobilisations corporelles en cours",
                        (byte) 2,
                        "231000"),

                new DonneesCompte(
                        "232",
                        "Immobilisations incorporelles en cours",
                        (byte) 2,
                        "232000"),

                new DonneesCompte(
                        "237",
                        "Avances et acomptes versés sur immobilisations incorporelles",
                        (byte) 2,
                        "237000"),

                new DonneesCompte(
                        "238",
                        "Avances et acomptes versés sur commandes d'immobilisations corporelles",
                        (byte) 2,
                        "238000"),

                new DonneesCompte(
                        "261",
                        "Titres de participation",
                        (byte) 2,
                        "261000"),

                new DonneesCompte(
                        "266",
                        "Autres formes de participation",
                        (byte) 2,
                        "266000"),

                new DonneesCompte(
                        "267",
                        "Créances rattachées à des participations",
                        (byte) 2,
                        "267000"),

                new DonneesCompte(
                        "268",
                        "Créances rattachées à des sociétés en participation",
                        (byte) 2,
                        "268000"),

                new DonneesCompte(
                        "269",
                        "Versements restant à effectuer sur titres de participation non libérés",
                        (byte) 2,
                        "269000"),

                new DonneesCompte(
                        "271",
                        "Titres mmobilisés autres que les titres immobilisése l'activité portefeuille (droit de propriété)",
                        (byte) 2,
                        "271000"),

                new DonneesCompte(
                        "272",
                        "Titres immobilisés (droits de créance)",
                        (byte) 2,
                        "272000"),

                new DonneesCompte(
                        "273",
                        "Titres immobilisése l'activité portefeuille",
                        (byte) 2,
                        "273000"),

                new DonneesCompte(
                        "274",
                        "Prêts",
                        (byte) 2,
                        "274000"),

                new DonneesCompte(
                        "275",
                        "Dépôts et cautionnements versés",
                        (byte) 2,
                        "275000"),

                new DonneesCompte(
                        "276",
                        "Autres créances immobilisées",
                        (byte) 2,
                        "276000"),

                new DonneesCompte(
                        "277",
                        "(Actions propres ou parts propres)",
                        (byte) 2,
                        "277000"),

                new DonneesCompte(
                        "279",
                        "Versements restant àffectuer sur titresmmobilisés non libérés",
                        (byte) 2,
                        "279000"),

                new DonneesCompte(
                        "281",
                        "Amortissement des immobilisations corporelles",
                        (byte) 2,
                        "281000"),

                new DonneesCompte(
                        "282",
                        "Amortissements des immobilisations mises en concession",
                        (byte) 2,
                        "282000"),

                new DonneesCompte(
                        "291",
                        "Dépréciations des immobilisations corporelles",
                        (byte) 2,
                        "291000"),

                new DonneesCompte(
                        "292",
                        "Dépréciations des immobilisations mises en concession",
                        (byte) 2,
                        "292000"),

                new DonneesCompte(
                        "293",
                        "Dépréciations des immobilisations en cours",
                        (byte) 2,
                        "293000"),

                new DonneesCompte(
                        "296",
                        "Provisions pour dépréciation des participations et créances rattachées à des participations",
                        (byte) 2,
                        "296000"),

                new DonneesCompte(
                        "297",
                        "Provisions pour dépréciationes autres immobilisations financières",
                        (byte) 2,
                        "297000"),

                new DonneesCompte(
                        "2011",
                        "Frais de constitution",
                        (byte) 2,
                        "201100"),

                new DonneesCompte(
                        "2012",
                        "Frais de première établissement",
                        (byte) 2,
                        "201200"),

                new DonneesCompte(
                        "2013",
                        "Frais d'auguementaion de capital et d'opérations diverses(fusions, scissions, transformations)",
                        (byte) 2,
                        "201300"),

                new DonneesCompte(
                        "2111",
                        "Terrains nus",
                        (byte) 2,
                        "211100"),

                new DonneesCompte(
                        "2112",
                        "Terrains aménagés",
                        (byte) 2,
                        "211200"),

                new DonneesCompte(
                        "2113",
                        "Sous-sols et sur-sols",
                        (byte) 2,
                        "211300"),

                new DonneesCompte(
                        "2114",
                        "Terrains de gisement",
                        (byte) 2,
                        "211400"),

                new DonneesCompte(
                        "2115",
                        "Terrains bâtis",
                        (byte) 2,
                        "211500"),

                new DonneesCompte(
                        "2116",
                        "Compte d'ordre sur immobilisation",
                        (byte) 2,
                        "211600"),

                new DonneesCompte(
                        "2121",
                        "Agencement et aménagements de terrains nus",
                        (byte) 2,
                        "212100"),

                new DonneesCompte(
                        "2122",
                        "Agencement et aménagements de terrains aménagés",
                        (byte) 2,
                        "212200"),

                new DonneesCompte(
                        "2123",
                        "Agencement et aménagements sous-sols et sur-sols",
                        (byte) 2,
                        "212300"),

                new DonneesCompte(
                        "2124",
                        "Agencement et aménagements de terrains de gisement",
                        (byte) 2,
                        "212400"),

                new DonneesCompte(
                        "2125",
                        "Agencement et aménagements de terrains bâtis",
                        (byte) 2,
                        "212500"),

                new DonneesCompte(
                        "2126",
                        "Agencement et aménagements - compte d'ordre sur immobilisations",
                        (byte) 2,
                        "212600"),

                new DonneesCompte(
                        "2131",
                        "Bâtiments",
                        (byte) 2,
                        "213100"),

                new DonneesCompte(
                        "2135",
                        "Installations générales - agencements - aménagements des constructions",
                        (byte) 2,
                        "213500"),

                new DonneesCompte(
                        "2138",
                        "Ouvrages d'infrastructure",
                        (byte) 2,
                        "213800"),

                new DonneesCompte(
                        "2141",
                        "Constructions sur sol d'autrui - Bâtiment",
                        (byte) 2,
                        "214100"),

                new DonneesCompte(
                        "2145",
                        "Constructions sur sol d'autrui - Installations générales - agencements - aménagementses cnstructions",
                        (byte) 2,
                        "214500"),

                new DonneesCompte(
                        "2148",
                        "Constructions sur sol d'autrui - Ouvrages d'infrastructure",
                        (byte) 2,
                        "214800"),

                new DonneesCompte(
                        "2151",
                        "Installations complexes spécialisées",
                        (byte) 2,
                        "215100"),

                new DonneesCompte(
                        "2153",
                        "Installations à caractère spécifique",
                        (byte) 2,
                        "215300"),

                new DonneesCompte(
                        "2154",
                        "Matériels industriels",
                        (byte) 2,
                        "215400"),

                new DonneesCompte(
                        "2155",
                        "Outillage industriel",
                        (byte) 2,
                        "215500"),

                new DonneesCompte(
                        "2157",
                        "Agencements et aménagementsesatérielstutillagesndustriels",
                        (byte) 2,
                        "215700"),

                new DonneesCompte(
                        "2181",
                        "Installations générales, agencements, aménagementsivers",
                        (byte) 2,
                        "218100"),

                new DonneesCompte(
                        "2182",
                        "Matériel de transport",
                        (byte) 2,
                        "218200"),

                new DonneesCompte(
                        "2183",
                        "Matériel de bureaut et matérie lnformatique",
                        (byte) 2,
                        "218300"),

                new DonneesCompte(
                        "2184",
                        "Mobilier",
                        (byte) 2,
                        "218400"),

                new DonneesCompte(
                        "2185",
                        "Cheptel",
                        (byte) 2,
                        "218500"),

                new DonneesCompte(
                        "2186",
                        "Emballages récupérables",
                        (byte) 2,
                        "218600"),

                new DonneesCompte(
                        "2312",
                        "Immobilisations corporelles en cours - Terrain",
                        (byte) 2,
                        "231200"),

                new DonneesCompte(
                        "2313",
                        "Immobilisations corporelles en cours - Constructions",
                        (byte) 2,
                        "231300"),

                new DonneesCompte(
                        "2315",
                        "Immobilisations corporelles en cours - Intallation techniques matériel et outillage industirels",
                        (byte) 2,
                        "231500"),

                new DonneesCompte(
                        "2318",
                        "Immobilisations corporelles en cours - Autres immobilisationq corporellles",
                        (byte) 2,
                        "231800"),

                new DonneesCompte(
                        "2382",
                        "Avances et acomptes versés sur commandes d'immobilisations corporelles - Terrains",
                        (byte) 2,
                        "238200"),

                new DonneesCompte(
                        "2383",
                        "Avances et acomptes versés sur commandes d'immobilisations corporelles - Constructions",
                        (byte) 2,
                        "238300"),

                new DonneesCompte(
                        "2385",
                        "Avances et acomptes versés sur commandes d'immobilisations corporelles - Intallation techniques matériel et outillage",
                        (byte) 2,
                        "238500"),

                new DonneesCompte(
                        "2388",
                        "Avances et acomptes versés sur commandes d'immobilisations corporelles - Autres immobilisations corporelles",
                        (byte) 2,
                        "238800"),

                new DonneesCompte(
                        "2611",
                        "Titres de participation - Actions",
                        (byte) 2,
                        "261100"),

                new DonneesCompte(
                        "2618",
                        "Titres de participation - Autres Titres",
                        (byte) 2,
                        "261800"),

                new DonneesCompte(
                        "2671",
                        "Créances rattachées à des participations (groupe)",
                        (byte) 2,
                        "267100"),

                new DonneesCompte(
                        "2674",
                        "Créances rattachées à des participations (hors groupe)",
                        (byte) 2,
                        "267400"),

                new DonneesCompte(
                        "2675",
                        "Versements representatifs d'apport non capitalisé (appels de fonds)",
                        (byte) 2,
                        "267500"),

                new DonneesCompte(
                        "2676",
                        "Avances consolidables",
                        (byte) 2,
                        "267600"),

                new DonneesCompte(
                        "2677",
                        "Autres créances rattachées à des participations",
                        (byte) 2,
                        "267700"),

                new DonneesCompte(
                        "2678",
                        "Intérêts courus",
                        (byte) 2,
                        "267800"),

                new DonneesCompte(
                        "2681",
                        "Créances rattachées à des sociétés en participation - principal",
                        (byte) 2,
                        "268100"),

                new DonneesCompte(
                        "2688",
                        "Créances rattachées à des sociétés en participation - Intérêts courus",
                        (byte) 2,
                        "268800"),

                new DonneesCompte(
                        "2711",
                        "Titres mmobilisés autres que les titres immobilisése l'activité portefeuille (droit de propriété) - Actions",
                        (byte) 2,
                        "271100"),

                new DonneesCompte(
                        "2718",
                        "Titres mmobilisés autres que les titres immobilisése l'activité portefeuille (droit de propriété) - Autres titres",
                        (byte) 2,
                        "271800"),

                new DonneesCompte(
                        "2721",
                        "Titres immobilisés (droits de créance) - Obligations",
                        (byte) 2,
                        "272100"),

                new DonneesCompte(
                        "2722",
                        "Titres immobilisés (droits de créance) - Bons",
                        (byte) 2,
                        "272200"),

                new DonneesCompte(
                        "2741",
                        "Prêts participatifs",
                        (byte) 2,
                        "274100"),

                new DonneesCompte(
                        "2742",
                        "Prêts aux associés",
                        (byte) 2,
                        "274200"),

                new DonneesCompte(
                        "2743",
                        "Prêts au personnel",
                        (byte) 2,
                        "274300"),

                new DonneesCompte(
                        "2748",
                        "Autres prêts",
                        (byte) 2,
                        "274800"),

                new DonneesCompte(
                        "2751",
                        "Dépôts versés",
                        (byte) 2,
                        "275100"),

                new DonneesCompte(
                        "2755",
                        "Cautionnements versés",
                        (byte) 2,
                        "275500"),

                new DonneesCompte(
                        "2761",
                        "Créances diverses",
                        (byte) 2,
                        "276100"),

                new DonneesCompte(
                        "2768",
                        "Intérêts courus",
                        (byte) 2,
                        "276800"),

                new DonneesCompte(
                        "2771",
                        "Actions propres ou parts propres",
                        (byte) 2,
                        "277100"),

                new DonneesCompte(
                        "2772",
                        "Actions propres ou parts propres en voie d'annulation",
                        (byte) 2,
                        "277200"),

                new DonneesCompte(
                        "2801",
                        "Amortissements - Frais d'établissement",
                        (byte) 2,
                        "280100"),

                new DonneesCompte(
                        "2803",
                        "Amortissement - Frais de recherche et développement",
                        (byte) 2,
                        "280300"),

                new DonneesCompte(
                        "2805",
                        "Amortissement - Concessions et droits similaires, brevets, licences, logiciels, droits et valeurs similaires",
                        (byte) 2,
                        "280500"),

                new DonneesCompte(
                        "2807",
                        "Amortissement - Fonds commercial",
                        (byte) 2,
                        "280700"),

                new DonneesCompte(
                        "2808",
                        "Amortissement - Autres immobilisations incorporelles",
                        (byte) 2,
                        "280800"),

                new DonneesCompte(
                        "2811",
                        "Amortissement - Terrains de gisement",
                        (byte) 2,
                        "281100"),

                new DonneesCompte(
                        "2812",
                        "Amortissement - Agencements, aménagementse terrains",
                        (byte) 2,
                        "281200"),

                new DonneesCompte(
                        "2813",
                        "Amortissement - Constructions",
                        (byte) 2,
                        "281300"),

                new DonneesCompte(
                        "2814",
                        "Amortissement - Constructions sur sol d'autrui",
                        (byte) 2,
                        "281400"),

                new DonneesCompte(
                        "2815",
                        "Amortissement - Installations matériel et outillage industriels",
                        (byte) 2,
                        "281500"),

                new DonneesCompte(
                        "2818",
                        "Amortissement - Autres immobilisations corporelles",
                        (byte) 2,
                        "281800"),

                new DonneesCompte(
                        "2905",
                        "Dépréciations des marques, procédés, droits et valeurs similaires",
                        (byte) 2,
                        "290500"),

                new DonneesCompte(
                        "2906",
                        "Dépréciations des droit au bail",
                        (byte) 2,
                        "290600"),

                new DonneesCompte(
                        "2907",
                        "Dépréciations des fonds commercial",
                        (byte) 2,
                        "290700"),

                new DonneesCompte(
                        "2908",
                        "Dépréciations des autres immobilisations incorporelles",
                        (byte) 2,
                        "290800"),

                new DonneesCompte(
                        "2911",
                        "Terrains (autres que terrains de gisement)",
                        (byte) 2,
                        "291100"),

                new DonneesCompte(
                        "2931",
                        "Immobilisations corporelles en cours",
                        (byte) 2,
                        "293100"),

                new DonneesCompte(
                        "2932",
                        "Immobilisations incorporelles en cours",
                        (byte) 2,
                        "293200"),

                new DonneesCompte(
                        "2961",
                        "Titres de participation",
                        (byte) 2,
                        "296100"),

                new DonneesCompte(
                        "2966",
                        "Autres formes de participation",
                        (byte) 2,
                        "296600"),

                new DonneesCompte(
                        "2967",
                        "Créances rattachées à des participations",
                        (byte) 2,
                        "296700"),

                new DonneesCompte(
                        "2968",
                        "Créances rattachées à des sociétés en participation",
                        (byte) 2,
                        "296800"),

                new DonneesCompte(
                        "2971",
                        "Titres immobilisés autres que les titres immobilisés de l'activité portefeuille - droit de propriété",
                        (byte) 2,
                        "297100"),

                new DonneesCompte(
                        "2972",
                        "Titres immobilisés, droit de créance",
                        (byte) 2,
                        "297200"),

                new DonneesCompte(
                        "2973",
                        "Titres immobilisés de l'activité portefeuille",
                        (byte) 2,
                        "297300"),

                new DonneesCompte(
                        "2974",
                        "Prêts",
                        (byte) 2,
                        "297400"),

                new DonneesCompte(
                        "2975",
                        "Dépôts et cautionnement versés",
                        (byte) 2,
                        "297500"),

                new DonneesCompte(
                        "2976",
                        "Autres créances immobilisées",
                        (byte) 2,
                        "297600"),

                new DonneesCompte(
                        "20121",
                        "Frais de première établissement - Frais de prospection",
                        (byte) 2,
                        "201210"),

                new DonneesCompte(
                        "20122",
                        "Frais de première établissement - Frais de publicité",
                        (byte) 2,
                        "201220"),

                new DonneesCompte(
                        "21141",
                        "Carrières",
                        (byte) 2,
                        "211410"),

                new DonneesCompte(
                        "21381",
                        "Voies de terre",
                        (byte) 2,
                        "213810"),

                new DonneesCompte(
                        "21382",
                        "Voies de fer",
                        (byte) 2,
                        "213820"),

                new DonneesCompte(
                        "21383",
                        "Voie d'eau",
                        (byte) 2,
                        "213830"),

                new DonneesCompte(
                        "21384",
                        "Barrages",
                        (byte) 2,
                        "213840"),

                new DonneesCompte(
                        "21385",
                        "Pistes d'aérodromes",
                        (byte) 2,
                        "213850"),

                new DonneesCompte(
                        "21511",
                        "Installations complexes spécialisées - sur sol propre",
                        (byte) 2,
                        "215110"),

                new DonneesCompte(
                        "21514",
                        "Installations complexes spécialisées - sur saol d'autrui",
                        (byte) 2,
                        "215140"),

                new DonneesCompte(
                        "21531",
                        "Installations à caractère spécifique - sur sol propre",
                        (byte) 2,
                        "215310"),

                new DonneesCompte(
                        "21534",
                        "Installations à caractère spécifique - sur sol d'autrui",
                        (byte) 2,
                        "215340"),

                new DonneesCompte(
                        "27682",
                        "Intérêts courus - Sur titres immobilisés (droits de créances",
                        (byte) 2,
                        "276820"),

                new DonneesCompte(
                        "27684",
                        "Intérêts courus - Sur prêts",
                        (byte) 2,
                        "276840"),

                new DonneesCompte(
                        "27685",
                        "Intérêts courus - Sur dépôts et cautionnemets",
                        (byte) 2,
                        "276850"),

                new DonneesCompte(
                        "27688",
                        "Intérêts courus - Sur créances diverses",
                        (byte) 2,
                        "276880"),

                new DonneesCompte(
                        "28011",
                        "Amortissement - Frais de constitution",
                        (byte) 2,
                        "280110"),

                new DonneesCompte(
                        "28012",
                        "Amortissement - Frais de première établissement",
                        (byte) 2,
                        "280120"),

                new DonneesCompte(
                        "28013",
                        "Amortissement - Frais de première établissement",
                        (byte) 2,
                        "280130"),

                new DonneesCompte(
                        "28181",
                        "Ammortissements des installations générales, agencements, aménagements divers",
                        (byte) 2,
                        "281810"),

                new DonneesCompte(
                        "28182",
                        "Ammortissements des matériel de transport",
                        (byte) 2,
                        "281820"),

                new DonneesCompte(
                        "28183",
                        "Ammortissements des matériel de bureau et matériel informatique",
                        (byte) 2,
                        "281830"),

                new DonneesCompte(
                        "28184",
                        "Ammortissements des mobilier",
                        (byte) 2,
                        "281840"),

                new DonneesCompte(
                        "28185",
                        "Ammortissements cheptel",
                        (byte) 2,
                        "281850"),

                new DonneesCompte(
                        "28186",
                        "Ammortissements des emballages récupérables",
                        (byte) 2,
                        "281860"),

                new DonneesCompte(
                        "29751",
                        "Dépôts versés",
                        (byte) 2,
                        "297510"),

                new DonneesCompte(
                        "29755",
                        "Cautionnements versés",
                        (byte) 2,
                        "297550"));
    }

    /**
     * Fournit les comptes de classe 3.
     *
     * @return flux des comptes de classe 3
     */
    private static Stream<DonneesCompte> fournirClasse3() {
        return Stream.of(
                new DonneesCompte(
                        "310",
                        "Matières premièrestournitures",
                        (byte) 3,
                        "310000"),

                new DonneesCompte(
                        "320",
                        "Autres approvisionnements",
                        (byte) 3,
                        "320000"),

                new DonneesCompte(
                        "330",
                        "En-cours de Productione biens",
                        (byte) 3,
                        "330000"),

                new DonneesCompte(
                        "340",
                        "En-cours de Production de services",
                        (byte) 3,
                        "340000"),

                new DonneesCompte(
                        "350",
                        "Stocks de produits",
                        (byte) 3,
                        "350000"),

                new DonneesCompte(
                        "360",
                        "Stocks provenant d'immobilisations",
                        (byte) 3,
                        "360000"),

                new DonneesCompte(
                        "370",
                        "Stocks de marchandises",
                        (byte) 3,
                        "370000"),

                new DonneesCompte(
                        "390",
                        "Provisions pour dépréciation des stocks et en-cours",
                        (byte) 3,
                        "390000"),

                new DonneesCompte(
                        "311",
                        "Matière première A",
                        (byte) 3,
                        "311000"),

                new DonneesCompte(
                        "312",
                        "Matière première B",
                        (byte) 3,
                        "312000"),

                new DonneesCompte(
                        "317",
                        "Fournitures A,",
                        (byte) 3,
                        "317000"),

                new DonneesCompte(
                        "321",
                        "Matières consommables",
                        (byte) 3,
                        "321000"),

                new DonneesCompte(
                        "322",
                        "Fournitures consommables",
                        (byte) 3,
                        "322000"),

                new DonneesCompte(
                        "326",
                        "Emballages",
                        (byte) 3,
                        "326000"),

                new DonneesCompte(
                        "331",
                        "Produits en cours",
                        (byte) 3,
                        "331000"),

                new DonneesCompte(
                        "335",
                        "Travaux en cours",
                        (byte) 3,
                        "335000"),

                new DonneesCompte(
                        "341",
                        "Études en cours",
                        (byte) 3,
                        "341000"),

                new DonneesCompte(
                        "345",
                        "Prestations de servicen cours",
                        (byte) 3,
                        "345000"),

                new DonneesCompte(
                        "351",
                        "Produits intermédiaires",
                        (byte) 3,
                        "351000"),

                new DonneesCompte(
                        "355",
                        "Produits finis",
                        (byte) 3,
                        "355000"),

                new DonneesCompte(
                        "358",
                        "Produits résiduels (ou matière de récupération)",
                        (byte) 3,
                        "358000"),

                new DonneesCompte(
                        "371",
                        "Marchandise A",
                        (byte) 3,
                        "371000"),

                new DonneesCompte(
                        "372",
                        "Marchandise B",
                        (byte) 3,
                        "372000"),

                new DonneesCompte(
                        "391",
                        "Provisions pour dépréciation des matières premières",
                        (byte) 3,
                        "391000"),

                new DonneesCompte(
                        "392",
                        "Provisions pour dépréciation des autres approvisionnements",
                        (byte) 3,
                        "392000"),

                new DonneesCompte(
                        "393",
                        "Provisions pour dépréciations des en-cours de production de biens",
                        (byte) 3,
                        "393000"),

                new DonneesCompte(
                        "394",
                        "Provisions pour dépréciations des en-cours de production services",
                        (byte) 3,
                        "394000"),

                new DonneesCompte(
                        "395",
                        "Provisions pour dépréciations des stocks de produits",
                        (byte) 3,
                        "395000"),

                new DonneesCompte(
                        "397",
                        "Provisions pour dépréciations des stockse de marchandises",
                        (byte) 3,
                        "397000"),

                new DonneesCompte(
                        "3581",
                        "Déchets",
                        (byte) 3,
                        "358100"),

                new DonneesCompte(
                        "3585",
                        "Rebuts",
                        (byte) 3,
                        "358500"),

                new DonneesCompte(
                        "3586",
                        "Matières de récupération",
                        (byte) 3,
                        "358600"));
    }

    /**
     * Fournit les comptes de classe 4.
     *
     * @return flux des comptes de classe 4
     */
    private static Stream<DonneesCompte> fournirClasse4() {
        return Stream.of(
                new DonneesCompte(
                        "410",
                        "Clients et comptes rattachés",
                        (byte) 4,
                        "410000"),

                new DonneesCompte(
                        "420",
                        "Personnel et comptes rattachés",
                        (byte) 4,
                        "420000"),

                new DonneesCompte(
                        "430",
                        "Sécurité Sociale et autres organismes sociaux",
                        (byte) 4,
                        "430000"),

                new DonneesCompte(
                        "440",
                        "Etat et autres collectivitées publique",
                        (byte) 4,
                        "440000"),

                new DonneesCompte(
                        "450",
                        "Groupe et associés",
                        (byte) 4,
                        "450000"),

                new DonneesCompte(
                        "460",
                        "Débiteurs divers et créditeurs divers",
                        (byte) 4,
                        "460000"),

                new DonneesCompte(
                        "470",
                        "Comptes transitoires ou d'attente",
                        (byte) 4,
                        "470000"),

                new DonneesCompte(
                        "480",
                        "Comptes de régularisation",
                        (byte) 4,
                        "480000"),

                new DonneesCompte(
                        "490",
                        "Provisions pour dépréciation des comptes de tiers",
                        (byte) 4,
                        "490000"),

                new DonneesCompte(
                        "403",
                        "Fournisseurs - Effets à payer",
                        (byte) 4,
                        "403000"),

                new DonneesCompte(
                        "404",
                        "Fournisseurs'immobilisation",
                        (byte) 4,
                        "404000"),

                new DonneesCompte(
                        "405",
                        "Fournisseurs d'immobilisations - Effets à payer",
                        (byte) 4,
                        "405000"),

                new DonneesCompte(
                        "408",
                        "Fournisseurs Factures non parvenues",
                        (byte) 4,
                        "408000"),

                new DonneesCompte(
                        "409",
                        "Fournisseurs Débiteurs",
                        (byte) 4,
                        "409000"),

                new DonneesCompte(
                        "411",
                        "Clients",
                        (byte) 4,
                        "411000"),

                new DonneesCompte(
                        "413",
                        "Clients -effets à recevoir",
                        (byte) 4,
                        "413000"),

                new DonneesCompte(
                        "416",
                        "Clients douteux ou litigieux",
                        (byte) 4,
                        "416000"),

                new DonneesCompte(
                        "418",
                        "Clients Produits non encore facturés",
                        (byte) 4,
                        "418000"),

                new DonneesCompte(
                        "419",
                        "Clients Créditeurs",
                        (byte) 4,
                        "419000"),

                new DonneesCompte(
                        "421",
                        "Personnel - Rénumérations duesNotes de frais",
                        (byte) 4,
                        "421000"),

                new DonneesCompte(
                        "422",
                        "Comités d'entreprise, d'établissement",
                        (byte) 4,
                        "422000"),

                new DonneesCompte(
                        "423",
                        "Notes de frais",
                        (byte) 4,
                        "423000"),

                new DonneesCompte(
                        "424",
                        "Participation des salariés aux résultats",
                        (byte) 4,
                        "424000"),

                new DonneesCompte(
                        "425",
                        "Personnel - Avance et acompte",
                        (byte) 4,
                        "425000"),

                new DonneesCompte(
                        "426",
                        "Personnel - Dépôts",
                        (byte) 4,
                        "426000"),

                new DonneesCompte(
                        "427",
                        "Personnel - Oppositions",
                        (byte) 4,
                        "427000"),

                new DonneesCompte(
                        "428",
                        "Personnel - Charges à payer et produits à recevoir",
                        (byte) 4,
                        "428000"),

                new DonneesCompte(
                        "431",
                        "Sécurité Sociale",
                        (byte) 4,
                        "431000"),

                new DonneesCompte(
                        "437",
                        "Autres Organismes Sociaux",
                        (byte) 4,
                        "437000"),

                new DonneesCompte(
                        "438",
                        "Organismes Sociaux Charges à payer et Produits à recevoir",
                        (byte) 4,
                        "438000"),

                new DonneesCompte(
                        "441",
                        "Etat subventions à recevoir",
                        (byte) 4,
                        "441000"),

                new DonneesCompte(
                        "442",
                        "Etat - Impôts et taxes recouvrables sur des tiers",
                        (byte) 4,
                        "442000"),

                new DonneesCompte(
                        "443",
                        "Opérations particulières avec l'Etat, les collectivités publiques, les organismes internationaux",
                        (byte) 4,
                        "443000"),

                new DonneesCompte(
                        "444",
                        "Etat - Impôts sur les bénéfices",
                        (byte) 4,
                        "444000"),

                new DonneesCompte(
                        "445",
                        "Etat - Taxes sur le chiffre d'affaires",
                        (byte) 4,
                        "445000"),

                new DonneesCompte(
                        "446",
                        "Obligations cautionnées",
                        (byte) 4,
                        "446000"),

                new DonneesCompte(
                        "447",
                        "Autres impôts, taxes et versements assimilés",
                        (byte) 4,
                        "447000"),

                new DonneesCompte(
                        "448",
                        "Etat - Charges à payer et produits à recevoir",
                        (byte) 4,
                        "448000"),

                new DonneesCompte(
                        "449",
                        "Quotas d'émission à restituer à l'Etat",
                        (byte) 4,
                        "449000"),

                new DonneesCompte(
                        "451",
                        "Dette Groupe",
                        (byte) 4,
                        "451000"),

                new DonneesCompte(
                        "455",
                        "Associés - Comptes courants",
                        (byte) 4,
                        "455000"),

                new DonneesCompte(
                        "456",
                        "Associés - Opérations sur le capital",
                        (byte) 4,
                        "456000"),

                new DonneesCompte(
                        "457",
                        "Associés - Dividendes à payer",
                        (byte) 4,
                        "457000"),

                new DonneesCompte(
                        "458",
                        "Associés - Opérations faites en communt et en G.I.E.",
                        (byte) 4,
                        "458000"),

                new DonneesCompte(
                        "462",
                        "Créance sur cessions d'immobilisations",
                        (byte) 4,
                        "462000"),

                new DonneesCompte(
                        "464",
                        "Dettes sur acquisitions de valuers mobilières de placements",
                        (byte) 4,
                        "464000"),

                new DonneesCompte(
                        "465",
                        "Dettes sur cessions de valuers mobilières de placements",
                        (byte) 4,
                        "465000"),

                new DonneesCompte(
                        "467",
                        "Autre comptes debiteurs ou créditeurs",
                        (byte) 4,
                        "467000"),

                new DonneesCompte(
                        "468",
                        "Divers - Charges à payer et produits à recevoir",
                        (byte) 4,
                        "468000"),

                new DonneesCompte(
                        "471",
                        "Compte d'attente",
                        (byte) 4,
                        "471000"),

                new DonneesCompte(
                        "472",
                        "Compte d'attente",
                        (byte) 4,
                        "472000"),

                new DonneesCompte(
                        "473",
                        "Compte d'attente",
                        (byte) 4,
                        "473000"),

                new DonneesCompte(
                        "474",
                        "Compte d'attente",
                        (byte) 4,
                        "474000"),

                new DonneesCompte(
                        "475",
                        "Compte d'attente",
                        (byte) 4,
                        "475000"),

                new DonneesCompte(
                        "476",
                        "Différence de conversion - Actif",
                        (byte) 4,
                        "476000"),

                new DonneesCompte(
                        "477",
                        "Différences de conversion - Passif",
                        (byte) 4,
                        "477000"),

                new DonneesCompte(
                        "478",
                        "Autres comptes transitoires",
                        (byte) 4,
                        "478000"),

                new DonneesCompte(
                        "481",
                        "Charges à répartir sur plusieurs exercices",
                        (byte) 4,
                        "481000"),

                new DonneesCompte(
                        "486",
                        "Charges constatées d'avance",
                        (byte) 4,
                        "486000"),

                new DonneesCompte(
                        "487",
                        "Produits constatés d'avance",
                        (byte) 4,
                        "487000"),

                new DonneesCompte(
                        "488",
                        "Comptes de répartition périodique des charges et des produits",
                        (byte) 4,
                        "488000"),

                new DonneesCompte(
                        "489",
                        "Quotas d'émission alloués par l'Etat",
                        (byte) 4,
                        "489000"),

                new DonneesCompte(
                        "491",
                        "Provisions pour dépréciation des comptes de clients",
                        (byte) 4,
                        "491000"),

                new DonneesCompte(
                        "495",
                        "Provisions pourépréciationes comptesu groupetes associés",
                        (byte) 4,
                        "495000"),

                new DonneesCompte(
                        "496",
                        "Provisions pour fépréciation des comptes de débiteurs divers",
                        (byte) 4,
                        "496000"),

                new DonneesCompte(
                        "4041",
                        "Fournisseurs - Achats d'immobilisations",
                        (byte) 4,
                        "404100"),

                new DonneesCompte(
                        "4047",
                        "Fournisseurs - Achats d'immobilisations - retenues de garantie",
                        (byte) 4,
                        "404700"),

                new DonneesCompte(
                        "4081",
                        "Fournisseurs Factures non parvenues",
                        (byte) 4,
                        "408100"),

                new DonneesCompte(
                        "4084",
                        "Fournisseurs d'immobilisations - Factures non parvenues",
                        (byte) 4,
                        "408400"),

                new DonneesCompte(
                        "4088",
                        "Fournisseurs intérêts courus",
                        (byte) 4,
                        "408800"),

                new DonneesCompte(
                        "4091",
                        "Fournisseurs - Avance et acomptes versés sur commandes",
                        (byte) 4,
                        "409100"),

                new DonneesCompte(
                        "4096",
                        "Fournisseurs - Créance pour emballages et matériels à rendreDébiteurs",
                        (byte) 4,
                        "409600"),

                new DonneesCompte(
                        "4097",
                        "Fournisseurs - Autres avoirs",
                        (byte) 4,
                        "409700"),

                new DonneesCompte(
                        "4098",
                        "Rabais, remises, ristournes à obtenir et autres avoirs non encore reçu",
                        (byte) 4,
                        "409800"),

                new DonneesCompte(
                        "4181",
                        "Clients - Factures à établir",
                        (byte) 4,
                        "418100"),

                new DonneesCompte(
                        "4188",
                        "Clients - Intérêts courus",
                        (byte) 4,
                        "418800"),

                new DonneesCompte(
                        "4191",
                        "Clients - Avances et acomptes reçus sur commandes",
                        (byte) 4,
                        "419100"),

                new DonneesCompte(
                        "4196",
                        "Clients dette sur emballage à rendre",
                        (byte) 4,
                        "419600"),

                new DonneesCompte(
                        "4197",
                        "Clients autres avoirs",
                        (byte) 4,
                        "419700"),

                new DonneesCompte(
                        "4198",
                        "RRR à accorder et autres avoirs à établir",
                        (byte) 4,
                        "419800"),

                new DonneesCompte(
                        "4246",
                        "Réserve spéciale (art. L. 442-2 du code du travail)",
                        (byte) 4,
                        "424600"),

                new DonneesCompte(
                        "4248",
                        "Comptes courants",
                        (byte) 4,
                        "424800"),

                new DonneesCompte(
                        "4282",
                        "Dettes provisionnées pour congés à payer",
                        (byte) 4,
                        "428200"),

                new DonneesCompte(
                        "4284",
                        "Dettes provisionnées pour participation des salariés aux résultats",
                        (byte) 4,
                        "428400"),

                new DonneesCompte(
                        "4286",
                        "Autres charges à payer",
                        (byte) 4,
                        "428600"),

                new DonneesCompte(
                        "4287",
                        "Produit à recevoir",
                        (byte) 4,
                        "428700"),

                new DonneesCompte(
                        "4371",
                        "Caisse A",
                        (byte) 4,
                        "437100"),

                new DonneesCompte(
                        "4372",
                        "Caisse B",
                        (byte) 4,
                        "437200"),

                new DonneesCompte(
                        "4382",
                        "Charges sociales sur congés à payer",
                        (byte) 4,
                        "438200"),

                new DonneesCompte(
                        "4386",
                        "Autres charges à payer",
                        (byte) 4,
                        "438600"),

                new DonneesCompte(
                        "4387",
                        "Produits à recevoir",
                        (byte) 4,
                        "438700"),

                new DonneesCompte(
                        "4411",
                        "Subvention d'investissement",
                        (byte) 4,
                        "441100"),

                new DonneesCompte(
                        "4417",
                        "Subvention d'exploitation",
                        (byte) 4,
                        "441700"),

                new DonneesCompte(
                        "4418",
                        "Subvention d'équilibre",
                        (byte) 4,
                        "441800"),

                new DonneesCompte(
                        "4419",
                        "Avances sur subventions",
                        (byte) 4,
                        "441900"),

                new DonneesCompte(
                        "4424",
                        "Etat - Impôts et taxes recouvrables sur des tiers - Obligataires",
                        (byte) 4,
                        "442400"),

                new DonneesCompte(
                        "4425",
                        "Etat - Impôts et taxes recouvrables sur des tiers - Associés",
                        (byte) 4,
                        "442500"),

                new DonneesCompte(
                        "4431",
                        "Créances dur l'état résultant de la supression de la règle du déalage d'un mois en matière de TVA",
                        (byte) 4,
                        "443100"),

                new DonneesCompte(
                        "4438",
                        "Intérêts courus sur créances figurant au compte 4431",
                        (byte) 4,
                        "443800"),

                new DonneesCompte(
                        "4452",
                        "TVA due intracommunautaire",
                        (byte) 4,
                        "445200"),

                new DonneesCompte(
                        "4455",
                        "Taxe sur le CA à décaisser",
                        (byte) 4,
                        "445500"),

                new DonneesCompte(
                        "4456",
                        "Taxe sur le CA déductible",
                        (byte) 4,
                        "445600"),

                new DonneesCompte(
                        "4457",
                        "Taxe sur le CA collecté par l'entreprise",
                        (byte) 4,
                        "445700"),

                new DonneesCompte(
                        "4458",
                        "Taxe sur le CA à régulariser ou en attente",
                        (byte) 4,
                        "445800"),

                new DonneesCompte(
                        "4482",
                        "Charges fiscales sur congés à payer",
                        (byte) 4,
                        "448200"),

                new DonneesCompte(
                        "4486",
                        "Charges à payer",
                        (byte) 4,
                        "448600"),

                new DonneesCompte(
                        "4487",
                        "Produit à recevoir",
                        (byte) 4,
                        "448700"),

                new DonneesCompte(
                        "4551",
                        "Associés - Comptes courants -Principal",
                        (byte) 4,
                        "455100"),

                new DonneesCompte(
                        "4558",
                        "Associés - Comptes courants - Intérêts courus",
                        (byte) 4,
                        "455800"),

                new DonneesCompte(
                        "4561",
                        "Associés - Compte d'apport en société",
                        (byte) 4,
                        "456100"),

                new DonneesCompte(
                        "4562",
                        "Aporteurs - Capital appelé nin versé",
                        (byte) 4,
                        "456200"),

                new DonneesCompte(
                        "4563",
                        "Associés - Versements reçus sur auguementation de capital",
                        (byte) 4,
                        "456300"),

                new DonneesCompte(
                        "4564",
                        "Associés - Versements anticipés",
                        (byte) 4,
                        "456400"),

                new DonneesCompte(
                        "4566",
                        "Actionnaires défaillants",
                        (byte) 4,
                        "456600"),

                new DonneesCompte(
                        "4567",
                        "Associés - Capital à rembourser",
                        (byte) 4,
                        "456700"),

                new DonneesCompte(
                        "4581",
                        "Associés - Opérations faites en communt et en G.I.E.- opération courante",
                        (byte) 4,
                        "458100"),

                new DonneesCompte(
                        "4588",
                        "Associés - Opérations faites en communt et en G.I.E.- Intérêts courus",
                        (byte) 4,
                        "458800"),

                new DonneesCompte(
                        "4686",
                        "Charges à payer",
                        (byte) 4,
                        "468600"),

                new DonneesCompte(
                        "4687",
                        "Produits à recevoir",
                        (byte) 4,
                        "468700"),

                new DonneesCompte(
                        "4761",
                        "Diminutuin des créances",
                        (byte) 4,
                        "476100"),

                new DonneesCompte(
                        "4762",
                        "Auguementatin des dettes",
                        (byte) 4,
                        "476200"),

                new DonneesCompte(
                        "4768",
                        "Différences compensées par couverture de change",
                        (byte) 4,
                        "476800"),

                new DonneesCompte(
                        "4771",
                        "Auguementation de créances",
                        (byte) 4,
                        "477100"),

                new DonneesCompte(
                        "4772",
                        "Diminutution des dettes",
                        (byte) 4,
                        "477200"),

                new DonneesCompte(
                        "4778",
                        "Différences compensées par couveture de change",
                        (byte) 4,
                        "477800"),

                new DonneesCompte(
                        "4816",
                        "Frais d'emission des emprunts",
                        (byte) 4,
                        "481600"),

                new DonneesCompte(
                        "4886",
                        "Comptes de répartition périodique des charges",
                        (byte) 4,
                        "488600"),

                new DonneesCompte(
                        "4887",
                        "Comptes de répartition périodique des produits",
                        (byte) 4,
                        "488700"),

                new DonneesCompte(
                        "4951",
                        "Provisions pourépréciationes comptes du groupe",
                        (byte) 4,
                        "495100"),

                new DonneesCompte(
                        "4955",
                        "Provisions pourépréciationes comptes courants des associés",
                        (byte) 4,
                        "495500"),

                new DonneesCompte(
                        "4958",
                        "Provisions pour dépréciation des opérations faites en commun et en GIE",
                        (byte) 4,
                        "495800"),

                new DonneesCompte(
                        "4962",
                        "Provisions pour dépréciation des créances sur cessions d'immobilisations",
                        (byte) 4,
                        "496200"),

                new DonneesCompte(
                        "4965",
                        "Provisions pour dépréciation des créances sur cessions de valeurs mobilères de placement",
                        (byte) 4,
                        "496500"),

                new DonneesCompte(
                        "4967",
                        "Provisions pour dépréciation des autres créances",
                        (byte) 4,
                        "496700"),

                new DonneesCompte(
                        "40971",
                        "Fournisseurs d'exploitation - Autres avoirs",
                        (byte) 4,
                        "409710"),

                new DonneesCompte(
                        "40974",
                        "Fournisseurs d'immobilisations - Autres avoirs",
                        (byte) 4,
                        "409740"),

                new DonneesCompte(
                        "44551",
                        "TVA à décaisser",
                        (byte) 4,
                        "445510"),

                new DonneesCompte(
                        "44558",
                        "Taxes assimilées à la TVA",
                        (byte) 4,
                        "445580"),

                new DonneesCompte(
                        "44562",
                        "TVA sur immobilisations",
                        (byte) 4,
                        "445620"),

                new DonneesCompte(
                        "44563",
                        "TVA transféré par d'autres entreprises",
                        (byte) 4,
                        "445630"),

                new DonneesCompte(
                        "44566",
                        "TVA déductibles sur autres biens et services",
                        (byte) 4,
                        "445660"),

                new DonneesCompte(
                        "44567",
                        "Crédit de TVA à reporter",
                        (byte) 4,
                        "445670"),

                new DonneesCompte(
                        "44568",
                        "Taxes assimilé à la TVA",
                        (byte) 4,
                        "445680"),

                new DonneesCompte(
                        "44571",
                        "TVA collectée",
                        (byte) 4,
                        "445710"),

                new DonneesCompte(
                        "44578",
                        "Taxes assimilé à la TVA",
                        (byte) 4,
                        "445780"),

                new DonneesCompte(
                        "44581",
                        "Acomptes – Régime simplifié d’imposition",
                        (byte) 4,
                        "445810"),

                new DonneesCompte(
                        "44582",
                        "Acomptes – Régime du forfait",
                        (byte) 4,
                        "445820"),

                new DonneesCompte(
                        "44583",
                        "Remboursement e taxes sur le ciffre d'affaires demandé",
                        (byte) 4,
                        "445830"),

                new DonneesCompte(
                        "44584",
                        "TVA récupérée d'avance",
                        (byte) 4,
                        "445840"),

                new DonneesCompte(
                        "44586",
                        "Taxes sur le chiffre d'affaires sur facture non parvenues",
                        (byte) 4,
                        "445860"),

                new DonneesCompte(
                        "44587",
                        "Taxes sur le chiffre d'affaires sur factures à établir",
                        (byte) 4,
                        "445870"),

                new DonneesCompte(
                        "45611",
                        "Associés - Apports en nature",
                        (byte) 4,
                        "456110"),

                new DonneesCompte(
                        "45615",
                        "Apportt en numéraire",
                        (byte) 4,
                        "456150"),

                new DonneesCompte(
                        "45621",
                        "Actionnaires - Capital souscrit et appelé non versé",
                        (byte) 4,
                        "456210"),

                new DonneesCompte(
                        "45625",
                        "Associés - Capital appelé non versé",
                        (byte) 4,
                        "456250"),

                new DonneesCompte(
                        "401001",
                        "Fournisseur A",
                        (byte) 4,
                        "401001"),

                new DonneesCompte(
                        "401002",
                        "Fournisseur B",
                        (byte) 4,
                        "401002"),

                new DonneesCompte(
                        "401003",
                        "Fournisseur C",
                        (byte) 4,
                        "401003"),

                new DonneesCompte(
                        "401004",
                        "Fournisseur D",
                        (byte) 4,
                        "401004"),

                new DonneesCompte(
                        "401005",
                        "Fournisseur E",
                        (byte) 4,
                        "401005"),

                new DonneesCompte(
                        "404001",
                        "Fournisseur d'immobilisation A",
                        (byte) 4,
                        "404001"),

                new DonneesCompte(
                        "404002",
                        "Fournisseur d'immobilisation B",
                        (byte) 4,
                        "404002"),

                new DonneesCompte(
                        "404003",
                        "Fournisseur d'immobilisation C",
                        (byte) 4,
                        "404003"),

                new DonneesCompte(
                        "404004",
                        "Fournisseur d'immobilisation D",
                        (byte) 4,
                        "404004"),

                new DonneesCompte(
                        "404005",
                        "Fournisseur d'immobilisation E",
                        (byte) 4,
                        "404005"),

                new DonneesCompte(
                        "411001",
                        "Client A",
                        (byte) 4,
                        "411001"),

                new DonneesCompte(
                        "411002",
                        "Client B",
                        (byte) 4,
                        "411002"),

                new DonneesCompte(
                        "411003",
                        "Client C",
                        (byte) 4,
                        "411003"),

                new DonneesCompte(
                        "411004",
                        "Client D",
                        (byte) 4,
                        "411004"),

                new DonneesCompte(
                        "411005",
                        "Client E",
                        (byte) 4,
                        "411005"));
    }

    /**
     * Fournit les comptes de classe 5.
     *
     * @return flux des comptes de classe 5
     */
    private static Stream<DonneesCompte> fournirClasse5() {
        return Stream.of(
                new DonneesCompte(
                        "510",
                        "Banques, établissements financiers et assimilés",
                        (byte) 5,
                        "510000"),

                new DonneesCompte(
                        "520",
                        "Instrument de tésorerie",
                        (byte) 5,
                        "520000"),

                new DonneesCompte(
                        "530",
                        "Caisse",
                        (byte) 5,
                        "530000"),

                new DonneesCompte(
                        "540",
                        "Régies d'avance et accéditifs",
                        (byte) 5,
                        "540000"),

                new DonneesCompte(
                        "580",
                        "Virementsnternes",
                        (byte) 5,
                        "580000"),

                new DonneesCompte(
                        "590",
                        "Provisions pour dépréciation des comptes financiers",
                        (byte) 5,
                        "590000"),

                new DonneesCompte(
                        "501",
                        "VMP - Parts dans des entreprises liées",
                        (byte) 5,
                        "501000"),

                new DonneesCompte(
                        "502",
                        "VMP - Actions propres",
                        (byte) 5,
                        "502000"),

                new DonneesCompte(
                        "503",
                        "VMP - Actions",
                        (byte) 5,
                        "503000"),

                new DonneesCompte(
                        "504",
                        "VMP - Autres titres conférant un droit de propriété",
                        (byte) 5,
                        "504000"),

                new DonneesCompte(
                        "505",
                        "VMP - Obligations et bons émis par la société et achetés par elle",
                        (byte) 5,
                        "505000"),

                new DonneesCompte(
                        "506",
                        "VMP - Obligations",
                        (byte) 5,
                        "506000"),

                new DonneesCompte(
                        "507",
                        "Bons du Trésor et bons de caisse à court terme",
                        (byte) 5,
                        "507000"),

                new DonneesCompte(
                        "508",
                        "VMP - Autres valeurs mobilières de placement et autres créances assimilées",
                        (byte) 5,
                        "508000"),

                new DonneesCompte(
                        "509",
                        "Versements restant à effectuer sur valeurs mobilières de placement non libérées",
                        (byte) 5,
                        "509000"),

                new DonneesCompte(
                        "511",
                        "Valeurs à l'encaissement",
                        (byte) 5,
                        "511000"),

                new DonneesCompte(
                        "512",
                        "Banques",
                        (byte) 5,
                        "512000"),

                new DonneesCompte(
                        "514",
                        "Chèques postaux",
                        (byte) 5,
                        "514000"),

                new DonneesCompte(
                        "515",
                        "Caisses du Trésor et des établissements publics",
                        (byte) 5,
                        "515000"),

                new DonneesCompte(
                        "516",
                        "Sociétés de bourse",
                        (byte) 5,
                        "516000"),

                new DonneesCompte(
                        "517",
                        "Autres organismes financiers",
                        (byte) 5,
                        "517000"),

                new DonneesCompte(
                        "518",
                        "Intérêts courus",
                        (byte) 5,
                        "518000"),

                new DonneesCompte(
                        "519",
                        "Concours bancaires courants",
                        (byte) 5,
                        "519000"),

                new DonneesCompte(
                        "531",
                        "Caisse siège social",
                        (byte) 5,
                        "531000"),

                new DonneesCompte(
                        "532",
                        "Caisse succursale (ou usine) A",
                        (byte) 5,
                        "532000"),

                new DonneesCompte(
                        "533",
                        "Caisse succursale (ou usine) B",
                        (byte) 5,
                        "533000"),

                new DonneesCompte(
                        "5031",
                        "Actions - Titres cotés",
                        (byte) 5,
                        "503100"),

                new DonneesCompte(
                        "5035",
                        "Actions - Titres non cotés",
                        (byte) 5,
                        "503500"),

                new DonneesCompte(
                        "5061",
                        "Obligations - Titres cotés",
                        (byte) 5,
                        "506100"),

                new DonneesCompte(
                        "5065",
                        "Obligations - Titres non cotés",
                        (byte) 5,
                        "506500"),

                new DonneesCompte(
                        "5081",
                        "Autres valeurs mobilières",
                        (byte) 5,
                        "508100"),

                new DonneesCompte(
                        "5082",
                        "Bons de souscription",
                        (byte) 5,
                        "508200"),

                new DonneesCompte(
                        "5088",
                        "Intérêts courus sur obligations bon valeurs assimilées",
                        (byte) 5,
                        "508800"),

                new DonneesCompte(
                        "5111",
                        "Coupons échus à l'encaissement",
                        (byte) 5,
                        "511100"),

                new DonneesCompte(
                        "5112",
                        "Chèques à encaissser",
                        (byte) 5,
                        "511200"),

                new DonneesCompte(
                        "5113",
                        "Effet à l'encaissement",
                        (byte) 5,
                        "511300"),

                new DonneesCompte(
                        "5114",
                        "Effet à l'escompte",
                        (byte) 5,
                        "511400"),

                new DonneesCompte(
                        "5121",
                        "Comptes en monnaie nationale",
                        (byte) 5,
                        "512100"),

                new DonneesCompte(
                        "5124",
                        "Comptes en devises",
                        (byte) 5,
                        "512400"),

                new DonneesCompte(
                        "5181",
                        "Intérêts courus à payer",
                        (byte) 5,
                        "518100"),

                new DonneesCompte(
                        "5188",
                        "Intérêts courus à recevoir",
                        (byte) 5,
                        "518800"),

                new DonneesCompte(
                        "5191",
                        "Crédit de mobilisation de créances commerciales (CMCC)",
                        (byte) 5,
                        "519100"),

                new DonneesCompte(
                        "5193",
                        "Mobilisation de créances nées à l'étranger",
                        (byte) 5,
                        "519300"),

                new DonneesCompte(
                        "5198",
                        "Inérêts courus sur concours bancaires courants",
                        (byte) 5,
                        "519800"),

                new DonneesCompte(
                        "5311",
                        "Caisse en monnaie nationale",
                        (byte) 5,
                        "531100"),

                new DonneesCompte(
                        "5314",
                        "Caisse en devises",
                        (byte) 5,
                        "531400"),

                new DonneesCompte(
                        "5903",
                        "Provisions pour dépréciation des actions",
                        (byte) 5,
                        "590300"),

                new DonneesCompte(
                        "5904",
                        "Provisions pour dépréciation autres titres conférant un droit de propriété",
                        (byte) 5,
                        "590400"),

                new DonneesCompte(
                        "5906",
                        "Provisions pour dépréciation des obligations",
                        (byte) 5,
                        "590600"),

                new DonneesCompte(
                        "5908",
                        "Provisions pour dépréciation des autres VMP",
                        (byte) 5,
                        "590800"));
    }

    /**
     * Fournit les comptes de classe 6.
     *
     * @return flux des comptes de classe 6
     */
    private static Stream<DonneesCompte> fournirClasse6() {
        return Stream.of(
                new DonneesCompte(
                        "610",
                        "Services extérieurs",
                        (byte) 6,
                        "610000"),

                new DonneesCompte(
                        "620",
                        "Autres services extérieurs",
                        (byte) 6,
                        "620000"),

                new DonneesCompte(
                        "630",
                        "Impôts, taxes et versements assimilés",
                        (byte) 6,
                        "630000"),

                new DonneesCompte(
                        "640",
                        "Charges de personnel",
                        (byte) 6,
                        "640000"),

                new DonneesCompte(
                        "650",
                        "Autres charges de gestion courante",
                        (byte) 6,
                        "650000"),

                new DonneesCompte(
                        "660",
                        "Charges financières",
                        (byte) 6,
                        "660000"),

                new DonneesCompte(
                        "670",
                        "Charges exceptionnelles",
                        (byte) 6,
                        "670000"),

                new DonneesCompte(
                        "680",
                        "Dotations aux amortissements et aux provisions",
                        (byte) 6,
                        "680000"),

                new DonneesCompte(
                        "690",
                        "Participation des salariés - impôts sur les bénéfices et assimilés",
                        (byte) 6,
                        "690000"),

                new DonneesCompte(
                        "601",
                        "Achats stockés - Matières premières et fournitures",
                        (byte) 6,
                        "601000"),

                new DonneesCompte(
                        "602",
                        "Achats stockés - Autres approvisionnements",
                        (byte) 6,
                        "602000"),

                new DonneesCompte(
                        "603",
                        "Variations des stocks approvisionnements et marchandises",
                        (byte) 6,
                        "603000"),

                new DonneesCompte(
                        "604",
                        "Achats d'études et prestations de services",
                        (byte) 6,
                        "604000"),

                new DonneesCompte(
                        "605",
                        "Achats de matériel, équipements et travaux",
                        (byte) 6,
                        "605000"),

                new DonneesCompte(
                        "606",
                        "Achats non stockés de matière et fournitures",
                        (byte) 6,
                        "606000"),

                new DonneesCompte(
                        "607",
                        "Achats de marchandises",
                        (byte) 6,
                        "607000"),

                new DonneesCompte(
                        "608",
                        "(Compte réservé, le cas échéant, à la récapitulation des frais accessoires incorporés aux achats)",
                        (byte) 6,
                        "608000"),

                new DonneesCompte(
                        "609",
                        "Rabais, remises et ristournes obtenus sur achats",
                        (byte) 6,
                        "609000"),

                new DonneesCompte(
                        "611",
                        "Sous-traitance générale",
                        (byte) 6,
                        "611000"),

                new DonneesCompte(
                        "612",
                        "Redevances de crédit-bail",
                        (byte) 6,
                        "612000"),

                new DonneesCompte(
                        "613",
                        "Locations",
                        (byte) 6,
                        "613000"),

                new DonneesCompte(
                        "614",
                        "Charges locatives et de copropriété",
                        (byte) 6,
                        "614000"),

                new DonneesCompte(
                        "615",
                        "Entretien et réparations",
                        (byte) 6,
                        "615000"),

                new DonneesCompte(
                        "616",
                        "Primes d'assurances",
                        (byte) 6,
                        "616000"),

                new DonneesCompte(
                        "617",
                        "Etudes et recherches",
                        (byte) 6,
                        "617000"),

                new DonneesCompte(
                        "618",
                        "Divers",
                        (byte) 6,
                        "618000"),

                new DonneesCompte(
                        "619",
                        "Rabais, remises et ristournes obtenus sur services extérieurs",
                        (byte) 6,
                        "619000"),

                new DonneesCompte(
                        "621",
                        "Personnel extérieur à l'entreprise",
                        (byte) 6,
                        "621000"),

                new DonneesCompte(
                        "622",
                        "Rémunérations d'intermédiaires et honoraires",
                        (byte) 6,
                        "622000"),

                new DonneesCompte(
                        "623",
                        "Publicité, publications, relations publiques",
                        (byte) 6,
                        "623000"),

                new DonneesCompte(
                        "624",
                        "Transports de biens et transports collectifs du personnel",
                        (byte) 6,
                        "624000"),

                new DonneesCompte(
                        "625",
                        "Déplacements, missions et réceptions",
                        (byte) 6,
                        "625000"),

                new DonneesCompte(
                        "626",
                        "Frais postaux et de télécommunications",
                        (byte) 6,
                        "626000"),

                new DonneesCompte(
                        "627",
                        "Services bancaires et assimilés",
                        (byte) 6,
                        "627000"),

                new DonneesCompte(
                        "628",
                        "Divers",
                        (byte) 6,
                        "628000"),

                new DonneesCompte(
                        "629",
                        "Rabais, remises et ristournes obtenus sur autres services extérieurs",
                        (byte) 6,
                        "629000"),

                new DonneesCompte(
                        "631",
                        "Impôts, taxes et versements assimilés sur rémunérations (administrations des impôts)",
                        (byte) 6,
                        "631000"),

                new DonneesCompte(
                        "633",
                        "Impôts, taxes et versements assimilés sur rémunérations (autres organismes)",
                        (byte) 6,
                        "633000"),

                new DonneesCompte(
                        "635",
                        "Autres impôts, taxes et versements assimilés (administrations des impôts)",
                        (byte) 6,
                        "635000"),

                new DonneesCompte(
                        "637",
                        "Autres impôts, taxes et versements assimilés (autres organismes)",
                        (byte) 6,
                        "637000"),

                new DonneesCompte(
                        "641",
                        "Rémunérations du personnel",
                        (byte) 6,
                        "641000"),

                new DonneesCompte(
                        "644",
                        "Rémunération du travail de l'exploitant",
                        (byte) 6,
                        "644000"),

                new DonneesCompte(
                        "645",
                        "Charges de sécurité sociale et de prévoyance",
                        (byte) 6,
                        "645000"),

                new DonneesCompte(
                        "646",
                        "Cotisations sociales personnelles de l'exploitant",
                        (byte) 6,
                        "646000"),

                new DonneesCompte(
                        "647",
                        "Autres charges sociales",
                        (byte) 6,
                        "647000"),

                new DonneesCompte(
                        "648",
                        "Autres charges de personnel",
                        (byte) 6,
                        "648000"),

                new DonneesCompte(
                        "651",
                        "Redevances pour concessions, brevets, licences, marques, procédés, logiciels, droits et valeurs similaires",
                        (byte) 6,
                        "651000"),

                new DonneesCompte(
                        "653",
                        "Jetons de présence",
                        (byte) 6,
                        "653000"),

                new DonneesCompte(
                        "654",
                        "Pertes sur créances irrécouvrables",
                        (byte) 6,
                        "654000"),

                new DonneesCompte(
                        "655",
                        "Quote-part de résultat sur opérations faites en commun",
                        (byte) 6,
                        "655000"),

                new DonneesCompte(
                        "658",
                        "Charges diverses de gestion courante",
                        (byte) 6,
                        "658000"),

                new DonneesCompte(
                        "661",
                        "Charges d'intérêts",
                        (byte) 6,
                        "661000"),

                new DonneesCompte(
                        "664",
                        "Pertes sur créances liées à des participations",
                        (byte) 6,
                        "664000"),

                new DonneesCompte(
                        "665",
                        "Escomptes accordés",
                        (byte) 6,
                        "665000"),

                new DonneesCompte(
                        "666",
                        "Pertes de change",
                        (byte) 6,
                        "666000"),

                new DonneesCompte(
                        "667",
                        "Charges nettes sur cessions de valeurs mobilières de placement",
                        (byte) 6,
                        "667000"),

                new DonneesCompte(
                        "668",
                        "Autres charges financières",
                        (byte) 6,
                        "668000"),

                new DonneesCompte(
                        "671",
                        "Charges exceptionnelles sur opérations de gestion",
                        (byte) 6,
                        "671000"),

                new DonneesCompte(
                        "672",
                        "(Compte à la disposition des entités pour enregistrer, en cours d'exercice, les charges sur exercices antérieurs)",
                        (byte) 6,
                        "672000"),

                new DonneesCompte(
                        "675",
                        "Valeurs comptables des éléments d'actif cédés",
                        (byte) 6,
                        "675000"),

                new DonneesCompte(
                        "678",
                        "Autres charges exceptionnelles",
                        (byte) 6,
                        "678000"),

                new DonneesCompte(
                        "681",
                        "Dotations aux amortissements et aux provisions - Charges d'exploitation",
                        (byte) 6,
                        "681000"),

                new DonneesCompte(
                        "686",
                        "Dotations aux amortissements et aux provisions - Charges financières",
                        (byte) 6,
                        "686000"),

                new DonneesCompte(
                        "687",
                        "Dotations aux amortissements et aux provisions - Charges exceptionnelles",
                        (byte) 6,
                        "687000"),

                new DonneesCompte(
                        "691",
                        "Participation des salariés aux résultats",
                        (byte) 6,
                        "691000"),

                new DonneesCompte(
                        "695",
                        "Impôts sur les bénéfices",
                        (byte) 6,
                        "695000"),

                new DonneesCompte(
                        "696",
                        "Suppléments d'impôt sur les sociétés liés aux distributions",
                        (byte) 6,
                        "696000"),

                new DonneesCompte(
                        "697",
                        "Imposition forfaitaire annuelle des sociétés",
                        (byte) 6,
                        "697000"),

                new DonneesCompte(
                        "698",
                        "Intégration fiscale",
                        (byte) 6,
                        "698000"),

                new DonneesCompte(
                        "699",
                        "Produits - Reports en arrière des déficits",
                        (byte) 6,
                        "699000"),

                new DonneesCompte(
                        "6021",
                        "Achats stockés - Matières et consommables",
                        (byte) 6,
                        "602100"),

                new DonneesCompte(
                        "6022",
                        "Achats stockés - Fournitures et consommables",
                        (byte) 6,
                        "602200"),

                new DonneesCompte(
                        "6026",
                        "Achats stockés - Emballages",
                        (byte) 6,
                        "602600"),

                new DonneesCompte(
                        "6031",
                        "Variations des stocks matières premières et fournitures",
                        (byte) 6,
                        "603100"),

                new DonneesCompte(
                        "6032",
                        "Variations des stocks des autres approvisionnements",
                        (byte) 6,
                        "603200"),

                new DonneesCompte(
                        "6037",
                        "Variations des stocks de marchandises",
                        (byte) 6,
                        "603700"),

                new DonneesCompte(
                        "6061",
                        "Achats de carburant",
                        (byte) 6,
                        "606100"),

                new DonneesCompte(
                        "6063",
                        "Fournitures d'entretien et de petit équipement",
                        (byte) 6,
                        "606300"),

                new DonneesCompte(
                        "6064",
                        "Fournitures administratives",
                        (byte) 6,
                        "606400"),

                new DonneesCompte(
                        "6068",
                        "Autres matières et fournitures",
                        (byte) 6,
                        "606800"),

                new DonneesCompte(
                        "6091",
                        "Rabais, remises et ristournes obtenus sur achats de matière première et fournitures",
                        (byte) 6,
                        "609100"),

                new DonneesCompte(
                        "6092",
                        "Rabais, remises et ristournes obtenus sur achats d'autres approviseonnements stockés",
                        (byte) 6,
                        "609200"),

                new DonneesCompte(
                        "6094",
                        "Rabais, remises et ristournes obtenus sur achats d'études et de prestations de services",
                        (byte) 6,
                        "609400"),

                new DonneesCompte(
                        "6095",
                        "Rabais, remises et ristournes obtenus sur achats matériel équipement et travaux",
                        (byte) 6,
                        "609500"),

                new DonneesCompte(
                        "6096",
                        "Rabais, remises et ristournes obtenus sur approvsionnements non stockés",
                        (byte) 6,
                        "609600"),

                new DonneesCompte(
                        "6097",
                        "Rabais, remises et ristournes de marchandisee",
                        (byte) 6,
                        "609700"),

                new DonneesCompte(
                        "6098",
                        "Rabais, remises et ristournes non affectés",
                        (byte) 6,
                        "609800"),

                new DonneesCompte(
                        "6122",
                        "Redevances de crédit-bail mobilier",
                        (byte) 6,
                        "612200"),

                new DonneesCompte(
                        "6125",
                        "Redevances de crédit-bail immobilier",
                        (byte) 6,
                        "612500"),

                new DonneesCompte(
                        "6132",
                        "Locations immobilière",
                        (byte) 6,
                        "613200"),

                new DonneesCompte(
                        "6135",
                        "Locations immobilière mobilière",
                        (byte) 6,
                        "613500"),

                new DonneesCompte(
                        "6136",
                        "Malis sur emballage",
                        (byte) 6,
                        "613600"),

                new DonneesCompte(
                        "6152",
                        "Entretien et réparations sur biens immobiliers",
                        (byte) 6,
                        "615200"),

                new DonneesCompte(
                        "6155",
                        "Entretien et réparations sur biens mobiliers",
                        (byte) 6,
                        "615500"),

                new DonneesCompte(
                        "6156",
                        "Maintenance",
                        (byte) 6,
                        "615600"),

                new DonneesCompte(
                        "6161",
                        "Primes d'assurances - multirisques",
                        (byte) 6,
                        "616100"),

                new DonneesCompte(
                        "6162",
                        "Primes d'assurances - assurances obligatoires dommage construction",
                        (byte) 6,
                        "616200"),

                new DonneesCompte(
                        "6163",
                        "Primes d'assurances - transport",
                        (byte) 6,
                        "616300"),

                new DonneesCompte(
                        "6164",
                        "Primes d'assurances - risques d'exploitations",
                        (byte) 6,
                        "616400"),

                new DonneesCompte(
                        "6165",
                        "Primes d'assurances - insolvabilité clients",
                        (byte) 6,
                        "616500"),

                new DonneesCompte(
                        "6181",
                        "Documentaion générale",
                        (byte) 6,
                        "618100"),

                new DonneesCompte(
                        "6183",
                        "Documentation technique",
                        (byte) 6,
                        "618300"),

                new DonneesCompte(
                        "6185",
                        "Frais de colloques, séminaires, conférences",
                        (byte) 6,
                        "618500"),

                new DonneesCompte(
                        "6211",
                        "Personnel intérimaire",
                        (byte) 6,
                        "621100"),

                new DonneesCompte(
                        "6214",
                        "Personnel détaché ou prêtée à l'entreprise",
                        (byte) 6,
                        "621400"),

                new DonneesCompte(
                        "6221",
                        "Commission et courtage sur achats",
                        (byte) 6,
                        "622100"),

                new DonneesCompte(
                        "6222",
                        "Commission et courtage sur ventes",
                        (byte) 6,
                        "622200"),

                new DonneesCompte(
                        "6224",
                        "Rénumeration des transitaires",
                        (byte) 6,
                        "622400"),

                new DonneesCompte(
                        "6225",
                        "Rémunérations d'affactirage",
                        (byte) 6,
                        "622500"),

                new DonneesCompte(
                        "6226",
                        "Honoraires",
                        (byte) 6,
                        "622600"),

                new DonneesCompte(
                        "6227",
                        "Frais d'actes et de contentieux",
                        (byte) 6,
                        "622700"),

                new DonneesCompte(
                        "6228",
                        "Divers",
                        (byte) 6,
                        "622800"),

                new DonneesCompte(
                        "6231",
                        "Annonces et insertions",
                        (byte) 6,
                        "623100"),

                new DonneesCompte(
                        "6232",
                        "Echantillons",
                        (byte) 6,
                        "623200"),

                new DonneesCompte(
                        "6233",
                        "Foire et expositions",
                        (byte) 6,
                        "623300"),

                new DonneesCompte(
                        "6234",
                        "Cadeeaux à la clientèle",
                        (byte) 6,
                        "623400"),

                new DonneesCompte(
                        "6235",
                        "Primes",
                        (byte) 6,
                        "623500"),

                new DonneesCompte(
                        "6236",
                        "Catalogues et imprimés",
                        (byte) 6,
                        "623600"),

                new DonneesCompte(
                        "6237",
                        "Publications",
                        (byte) 6,
                        "623700"),

                new DonneesCompte(
                        "6238",
                        "Divers (pourboires, dont courant)",
                        (byte) 6,
                        "623800"),

                new DonneesCompte(
                        "6241",
                        "Transports sur achats",
                        (byte) 6,
                        "624100"),

                new DonneesCompte(
                        "6242",
                        "Transports sur ventes",
                        (byte) 6,
                        "624200"),

                new DonneesCompte(
                        "6243",
                        "Transports entre établissements ou chantiers",
                        (byte) 6,
                        "624300"),

                new DonneesCompte(
                        "6244",
                        "Transports administratifs",
                        (byte) 6,
                        "624400"),

                new DonneesCompte(
                        "6247",
                        "Transports collectifs du personnel",
                        (byte) 6,
                        "624700"),

                new DonneesCompte(
                        "6248",
                        "Divers",
                        (byte) 6,
                        "624800"),

                new DonneesCompte(
                        "6251",
                        "Voyages et déplacements",
                        (byte) 6,
                        "625100"),

                new DonneesCompte(
                        "6255",
                        "Frais de démenagement",
                        (byte) 6,
                        "625500"),

                new DonneesCompte(
                        "6256",
                        "Missions",
                        (byte) 6,
                        "625600"),

                new DonneesCompte(
                        "6257",
                        "Réceptions",
                        (byte) 6,
                        "625700"),

                new DonneesCompte(
                        "6271",
                        "Frais sur titres (achats, ventes, gardes)",
                        (byte) 6,
                        "627100"),

                new DonneesCompte(
                        "6272",
                        "Commission et frais sur emission d'emprunts",
                        (byte) 6,
                        "627200"),

                new DonneesCompte(
                        "6275",
                        "Frais sur effets",
                        (byte) 6,
                        "627500"),

                new DonneesCompte(
                        "6276",
                        "Location de coffres",
                        (byte) 6,
                        "627600"),

                new DonneesCompte(
                        "6278",
                        "Autres frais et commissions sur prestations de services",
                        (byte) 6,
                        "627800"),

                new DonneesCompte(
                        "6281",
                        "Concours divers (cotisations)",
                        (byte) 6,
                        "628100"),

                new DonneesCompte(
                        "6284",
                        "Frais de recrutement de personnel",
                        (byte) 6,
                        "628400"),

                new DonneesCompte(
                        "6311",
                        "Taxes sur les salaires",
                        (byte) 6,
                        "631100"),

                new DonneesCompte(
                        "6312",
                        "Taxe d'apprentissage",
                        (byte) 6,
                        "631200"),

                new DonneesCompte(
                        "6313",
                        "Participations des employeurs à la formation professionnelle continue",
                        (byte) 6,
                        "631300"),

                new DonneesCompte(
                        "6314",
                        "Cotisation pour défaut d'investissement obligatoire dans la construction",
                        (byte) 6,
                        "631400"),

                new DonneesCompte(
                        "6318",
                        "Autres",
                        (byte) 6,
                        "631800"),

                new DonneesCompte(
                        "6331",
                        "Versement de transport",
                        (byte) 6,
                        "633100"),

                new DonneesCompte(
                        "6332",
                        "Allocation logement",
                        (byte) 6,
                        "633200"),

                new DonneesCompte(
                        "6333",
                        "Participations des employeurs à la formations professionnelle continue",
                        (byte) 6,
                        "633300"),

                new DonneesCompte(
                        "6334",
                        "Participation des employeurs à l'effort de construction",
                        (byte) 6,
                        "633400"),

                new DonneesCompte(
                        "6335",
                        "Versements libératoire ouvrant droit à l'exonération de la taxe d'apprentissage",
                        (byte) 6,
                        "633500"),

                new DonneesCompte(
                        "6338",
                        "Autres",
                        (byte) 6,
                        "633800"),

                new DonneesCompte(
                        "6351",
                        "Impôts directs (sauf impôts dur les bénéfices)",
                        (byte) 6,
                        "635100"),

                new DonneesCompte(
                        "6352",
                        "Taxe sur le chiffre d'affaire non récupérables",
                        (byte) 6,
                        "635200"),

                new DonneesCompte(
                        "6353",
                        "Impôts indirects",
                        (byte) 6,
                        "635300"),

                new DonneesCompte(
                        "6354",
                        "Droit d'enregistrement et de timbre",
                        (byte) 6,
                        "635400"),

                new DonneesCompte(
                        "6358",
                        "Autres droits",
                        (byte) 6,
                        "635800"),

                new DonneesCompte(
                        "6371",
                        "Contribution sociale de solidarité à la charge des osciétés",
                        (byte) 6,
                        "637100"),

                new DonneesCompte(
                        "6372",
                        "Taxes perçues par les organismes publics internationaux",
                        (byte) 6,
                        "637200"),

                new DonneesCompte(
                        "6374",
                        "Impôts et taxes exigibles à l'étranger",
                        (byte) 6,
                        "637400"),

                new DonneesCompte(
                        "6378",
                        "Taxex diverses",
                        (byte) 6,
                        "637800"),

                new DonneesCompte(
                        "6411",
                        "Salaires, appointements",
                        (byte) 6,
                        "641100"),

                new DonneesCompte(
                        "6412",
                        "Congés payés",
                        (byte) 6,
                        "641200"),

                new DonneesCompte(
                        "6413",
                        "Primes et gratifications",
                        (byte) 6,
                        "641300"),

                new DonneesCompte(
                        "6414",
                        "Indemnités et avantages divers",
                        (byte) 6,
                        "641400"),

                new DonneesCompte(
                        "6415",
                        "Supplément famillial",
                        (byte) 6,
                        "641500"),

                new DonneesCompte(
                        "6451",
                        "Cotisations à l'URSSAF",
                        (byte) 6,
                        "645100"),

                new DonneesCompte(
                        "6452",
                        "Cotisations aux mutuelles",
                        (byte) 6,
                        "645200"),

                new DonneesCompte(
                        "6453",
                        "Cotisations aux caisses de retraites",
                        (byte) 6,
                        "645300"),

                new DonneesCompte(
                        "6454",
                        "Cotisations aux ASSEDIC",
                        (byte) 6,
                        "645400"),

                new DonneesCompte(
                        "6458",
                        "Cotisations aux autres organismes sociaux",
                        (byte) 6,
                        "645800"),

                new DonneesCompte(
                        "6471",
                        "Prestations directes",
                        (byte) 6,
                        "647100"),

                new DonneesCompte(
                        "6472",
                        "Versements aux comités d'entreprises et d'établissements",
                        (byte) 6,
                        "647200"),

                new DonneesCompte(
                        "6473",
                        "Versements aux comités d'hygiène et de sécurité",
                        (byte) 6,
                        "647300"),

                new DonneesCompte(
                        "6474",
                        "Versements aux autres œuvres sociales",
                        (byte) 6,
                        "647400"),

                new DonneesCompte(
                        "6475",
                        "Médecine du travail, pharmacie",
                        (byte) 6,
                        "647500"),

                new DonneesCompte(
                        "6511",
                        "Redevances pour concessions, brevets, licences, marques, procédés, logiciels",
                        (byte) 6,
                        "651100"),

                new DonneesCompte(
                        "6516",
                        "Droits d'auteur et de reproduction",
                        (byte) 6,
                        "651600"),

                new DonneesCompte(
                        "6518",
                        "Autres droits et valeurs similaires",
                        (byte) 6,
                        "651800"),

                new DonneesCompte(
                        "6541",
                        "Pertes sur créances irrécouvrables de l'exercice",
                        (byte) 6,
                        "654100"),

                new DonneesCompte(
                        "6544",
                        "Pertes sur créances irrécouvrables des exercice antérieurs",
                        (byte) 6,
                        "654400"),

                new DonneesCompte(
                        "6551",
                        "Quote-part de bénéfice transférée (comptabilité du gérant)",
                        (byte) 6,
                        "655100"),

                new DonneesCompte(
                        "6555",
                        "Quote-part de perte supportée (comptabilité des associés non gérants)",
                        (byte) 6,
                        "655500"),

                new DonneesCompte(
                        "6611",
                        "Intérêts des emprunts et dettes",
                        (byte) 6,
                        "661100"),

                new DonneesCompte(
                        "6615",
                        "Intérêts des comptes courants et des dépôts créditeurs",
                        (byte) 6,
                        "661500"),

                new DonneesCompte(
                        "6616",
                        "Intérêts bancaires et sur opérations de financement (escompte,...)",
                        (byte) 6,
                        "661600"),

                new DonneesCompte(
                        "6617",
                        "Intérêts des obligations cautionnées",
                        (byte) 6,
                        "661700"),

                new DonneesCompte(
                        "6618",
                        "Intérêts des autres dettes",
                        (byte) 6,
                        "661800"),

                new DonneesCompte(
                        "6711",
                        "Pénalités sur marchés (et dédits payés sur achats et ventes)",
                        (byte) 6,
                        "671100"),

                new DonneesCompte(
                        "6712",
                        "Pénalités, amendes fiscales et pénales",
                        (byte) 6,
                        "671200"),

                new DonneesCompte(
                        "6713",
                        "Dons, libéralités",
                        (byte) 6,
                        "671300"),

                new DonneesCompte(
                        "6714",
                        "Créances devenues irrécouvrables dans l'exercice",
                        (byte) 6,
                        "671400"),

                new DonneesCompte(
                        "6715",
                        "Subventions accordées",
                        (byte) 6,
                        "671500"),

                new DonneesCompte(
                        "6717",
                        "Rappel d'impôts (autres qu'impôts sur les bénéfices)",
                        (byte) 6,
                        "671700"),

                new DonneesCompte(
                        "6718",
                        "Autres charges exceptionnelles sur opérations de gestion",
                        (byte) 6,
                        "671800"),

                new DonneesCompte(
                        "6751",
                        "Valeurs comptables des éléments d'actif cédés - Immobilisations incorporelles",
                        (byte) 6,
                        "675100"),

                new DonneesCompte(
                        "6752",
                        "Valeurs comptables des éléments d'actif cédés - immobilisations corporelles",
                        (byte) 6,
                        "675200"),

                new DonneesCompte(
                        "6756",
                        "Valeurs comptables des éléments d'actif cédés - Immobilissation financières",
                        (byte) 6,
                        "675600"),

                new DonneesCompte(
                        "6781",
                        "Malis provenant de clauses d'indexation",
                        (byte) 6,
                        "678100"),

                new DonneesCompte(
                        "6782",
                        "Lots",
                        (byte) 6,
                        "678200"),

                new DonneesCompte(
                        "6783",
                        "Malis provenant du rachat par l'entreprise d'actions et obligations émises par elle-même",
                        (byte) 6,
                        "678300"),

                new DonneesCompte(
                        "6788",
                        "Charges exceptionnelles diverses",
                        (byte) 6,
                        "678800"),

                new DonneesCompte(
                        "6811",
                        "Dotations aux amortissements sur immobilisations incorporelles et corporelles",
                        (byte) 6,
                        "681100"),

                new DonneesCompte(
                        "6812",
                        "Dotations aux amortissements des charges d'exploitation à répartir",
                        (byte) 6,
                        "681200"),

                new DonneesCompte(
                        "6815",
                        "Dotations aux provisions pour risques et charges d'exploitation",
                        (byte) 6,
                        "681500"),

                new DonneesCompte(
                        "6816",
                        "Dotations aux dépréciations des immobilisations incorporelles et corporelles",
                        (byte) 6,
                        "681600"),

                new DonneesCompte(
                        "6817",
                        "Dotations aux provisions pour dépréciation des actifs circulants",
                        (byte) 6,
                        "681700"),

                new DonneesCompte(
                        "6861",
                        "Dotations aux amortissements des primes de remboursements des obligations",
                        (byte) 6,
                        "686100"),

                new DonneesCompte(
                        "6865",
                        "Dotations aux provisions pour risques et charges financiers",
                        (byte) 6,
                        "686500"),

                new DonneesCompte(
                        "6866",
                        "Dotations aux provisions pour dépréciation des éléments financiers",
                        (byte) 6,
                        "686600"),

                new DonneesCompte(
                        "6868",
                        "Auters dotations",
                        (byte) 6,
                        "686800"),

                new DonneesCompte(
                        "6871",
                        "Dotations aux amortissements des immobilisations exceptionnels des immobilisations",
                        (byte) 6,
                        "687100"),

                new DonneesCompte(
                        "6872",
                        "Dotations aux provisions réglementées (immobilisations)",
                        (byte) 6,
                        "687200"),

                new DonneesCompte(
                        "6873",
                        "Dotations aux provisions réglementées (stocks)",
                        (byte) 6,
                        "687300"),

                new DonneesCompte(
                        "6874",
                        "Dotations aux provisions réglementées",
                        (byte) 6,
                        "687400"),

                new DonneesCompte(
                        "6875",
                        "Dotations aux provisions pour risques et charges exceptionnels",
                        (byte) 6,
                        "687500"),

                new DonneesCompte(
                        "6876",
                        "Dotations aux provisions pour dépréciations exceptionnelles",
                        (byte) 6,
                        "687600"),

                new DonneesCompte(
                        "6951",
                        "Impôts dus en France",
                        (byte) 6,
                        "695100"),

                new DonneesCompte(
                        "6952",
                        "Contribution additionnelle à l'impôt sur les bénéfices",
                        (byte) 6,
                        "695200"),

                new DonneesCompte(
                        "6954",
                        "Impôts dus à l'étranger",
                        (byte) 6,
                        "695400"),

                new DonneesCompte(
                        "6981",
                        "Intégration fiscale - Charges",
                        (byte) 6,
                        "698100"),

                new DonneesCompte(
                        "6989",
                        "Intégration fiscale - Produits",
                        (byte) 6,
                        "698900"),

                new DonneesCompte(
                        "60221",
                        "Achats stockés - Combustibles",
                        (byte) 6,
                        "602210"),

                new DonneesCompte(
                        "60222",
                        "Achats stockés - Produits d'entretien",
                        (byte) 6,
                        "602220"),

                new DonneesCompte(
                        "60223",
                        "Achats stockés - Fournitures d'atelier et d'usine",
                        (byte) 6,
                        "602230"),

                new DonneesCompte(
                        "60224",
                        "Achats stockés - Fournitures de magasin",
                        (byte) 6,
                        "602240"),

                new DonneesCompte(
                        "60225",
                        "Achats stockés - Fournitures de bureau",
                        (byte) 6,
                        "602250"),

                new DonneesCompte(
                        "60261",
                        "Achats stockés - Emballages perdus",
                        (byte) 6,
                        "602610"),

                new DonneesCompte(
                        "60265",
                        "Achats stockés - Emballages récupérables non identifiables",
                        (byte) 6,
                        "602650"),

                new DonneesCompte(
                        "60267",
                        "Achats stockés - Emballages à usage mixte",
                        (byte) 6,
                        "602670"),

                new DonneesCompte(
                        "61636",
                        "Primes d'assurances - transport sur achats",
                        (byte) 6,
                        "616360"),

                new DonneesCompte(
                        "61637",
                        "Primes d'assurances - transport sur ventes",
                        (byte) 6,
                        "616370"),

                new DonneesCompte(
                        "61638",
                        "Primes d'assurances - sur autres biens",
                        (byte) 6,
                        "616380"),

                new DonneesCompte(
                        "63511",
                        "Taxe professionnelle",
                        (byte) 6,
                        "635110"),

                new DonneesCompte(
                        "63512",
                        "Taxe foncière",
                        (byte) 6,
                        "635120"),

                new DonneesCompte(
                        "63513",
                        "Autres impôts locaux",
                        (byte) 6,
                        "635130"),

                new DonneesCompte(
                        "63514",
                        "Taxe sur les véhicules de sociétés",
                        (byte) 6,
                        "635140"),

                new DonneesCompte(
                        "63541",
                        "Droits de mutation",
                        (byte) 6,
                        "635410"),

                new DonneesCompte(
                        "66116",
                        "Intérêts des emprunts et dettes assimilées",
                        (byte) 6,
                        "661160"),

                new DonneesCompte(
                        "66117",
                        "Intérêts des dettes rattachées à des participations",
                        (byte) 6,
                        "661170"),

                new DonneesCompte(
                        "66181",
                        "Intérêts des autres dettes commerciales",
                        (byte) 6,
                        "661810"),

                new DonneesCompte(
                        "66188",
                        "Intérêts des dettes diverses",
                        (byte) 6,
                        "661880"),

                new DonneesCompte(
                        "68111",
                        "Immobilisations incorporelles",
                        (byte) 6,
                        "681110"),

                new DonneesCompte(
                        "68112",
                        "Immobilisations corporelles",
                        (byte) 6,
                        "681120"),

                new DonneesCompte(
                        "68161",
                        "Immobilisations incorporelles",
                        (byte) 6,
                        "681610"),

                new DonneesCompte(
                        "68162",
                        "Immobilisations corporelles",
                        (byte) 6,
                        "681620"),

                new DonneesCompte(
                        "68173",
                        "Stocks et en-cours",
                        (byte) 6,
                        "681730"),

                new DonneesCompte(
                        "68174",
                        "Créances",
                        (byte) 6,
                        "681740"),

                new DonneesCompte(
                        "68662",
                        "Immobilisations financières",
                        (byte) 6,
                        "686620"),

                new DonneesCompte(
                        "68665",
                        "Valeurs mobilières de placement",
                        (byte) 6,
                        "686650"));
    }

    /**
     * Fournit les comptes de classe 7.
     *
     * @return flux des comptes de classe 7
     */
    private static Stream<DonneesCompte> fournirClasse7() {
        return Stream.of(
                new DonneesCompte(
                        "710",
                        "Production stockée (ou déstockage)",
                        (byte) 7,
                        "710000"),

                new DonneesCompte(
                        "720",
                        "Production immobilisée",
                        (byte) 7,
                        "720000"),

                new DonneesCompte(
                        "740",
                        "Subventions d'exploitation",
                        (byte) 7,
                        "740000"),

                new DonneesCompte(
                        "750",
                        "Autres produits de gestion courante",
                        (byte) 7,
                        "750000"),

                new DonneesCompte(
                        "760",
                        "Produits financiers",
                        (byte) 7,
                        "760000"),

                new DonneesCompte(
                        "770",
                        "Produits exceptionnels",
                        (byte) 7,
                        "770000"),

                new DonneesCompte(
                        "780",
                        "Reprises sur amortissements et provisions",
                        (byte) 7,
                        "780000"),

                new DonneesCompte(
                        "790",
                        "Transferts de charges",
                        (byte) 7,
                        "790000"),

                new DonneesCompte(
                        "701",
                        "Ventes de produits finis",
                        (byte) 7,
                        "701000"),

                new DonneesCompte(
                        "702",
                        "Ventes de produits intermédiaires",
                        (byte) 7,
                        "702000"),

                new DonneesCompte(
                        "703",
                        "Ventes de produits résiduels",
                        (byte) 7,
                        "703000"),

                new DonneesCompte(
                        "704",
                        "Travaux",
                        (byte) 7,
                        "704000"),

                new DonneesCompte(
                        "705",
                        "Etudes",
                        (byte) 7,
                        "705000"),

                new DonneesCompte(
                        "706",
                        "Prestations de services",
                        (byte) 7,
                        "706000"),

                new DonneesCompte(
                        "707",
                        "Ventes de marchandises",
                        (byte) 7,
                        "707000"),

                new DonneesCompte(
                        "708",
                        "Produits des activités annexes",
                        (byte) 7,
                        "708000"),

                new DonneesCompte(
                        "709",
                        "Rabais, remises et ristournes accordés par l'entreprise",
                        (byte) 7,
                        "709000"),

                new DonneesCompte(
                        "713",
                        "Variation des stocks (en-cours de production, produits)",
                        (byte) 7,
                        "713000"),

                new DonneesCompte(
                        "721",
                        "Production immobilisations incorporelles",
                        (byte) 7,
                        "721000"),

                new DonneesCompte(
                        "722",
                        "Production immobilisations corporelles",
                        (byte) 7,
                        "722000"),

                new DonneesCompte(
                        "751",
                        "Redevances pour concessions, brevets, licences, marques, procédés, logiciels, droits et valeurs similaires",
                        (byte) 7,
                        "751000"),

                new DonneesCompte(
                        "752",
                        "Revenus des immeubles non affectés à des activités professionnelles",
                        (byte) 7,
                        "752000"),

                new DonneesCompte(
                        "753",
                        "Jetons de présence et rémunérations d'administrateurs, gérants,",
                        (byte) 7,
                        "753000"),

                new DonneesCompte(
                        "754",
                        "Ristournes perçues des coopératives (provenant des excédents)",
                        (byte) 7,
                        "754000"),

                new DonneesCompte(
                        "755",
                        "Quotes-parts de résultat sur opérations faites en commun",
                        (byte) 7,
                        "755000"),

                new DonneesCompte(
                        "758",
                        "Produits divers de gestion courante",
                        (byte) 7,
                        "758000"),

                new DonneesCompte(
                        "761",
                        "Produits de participations",
                        (byte) 7,
                        "761000"),

                new DonneesCompte(
                        "762",
                        "Produits des autres immobilisations financières",
                        (byte) 7,
                        "762000"),

                new DonneesCompte(
                        "763",
                        "Revenus des autres créances",
                        (byte) 7,
                        "763000"),

                new DonneesCompte(
                        "764",
                        "Revenus des valeurs mobilières de placement",
                        (byte) 7,
                        "764000"),

                new DonneesCompte(
                        "765",
                        "Escomptes obtenus",
                        (byte) 7,
                        "765000"),

                new DonneesCompte(
                        "766",
                        "Gains de change",
                        (byte) 7,
                        "766000"),

                new DonneesCompte(
                        "767",
                        "Produits nets sur cessions de valeurs mobilières de placement",
                        (byte) 7,
                        "767000"),

                new DonneesCompte(
                        "768",
                        "Autres produits financiers",
                        (byte) 7,
                        "768000"),

                new DonneesCompte(
                        "771",
                        "Produits exceptionnels sur opérations de gestion",
                        (byte) 7,
                        "771000"),

                new DonneesCompte(
                        "772",
                        "(Compte à la disposition des entités pour enregistrer, en cours d'exercice, les produits sur exercices antérieurs)",
                        (byte) 7,
                        "772000"),

                new DonneesCompte(
                        "775",
                        "Produits des cessions d'éléments d'actif",
                        (byte) 7,
                        "775000"),

                new DonneesCompte(
                        "777",
                        "Quote-part des subventions d'investissement virée au résultat de l'exercice",
                        (byte) 7,
                        "777000"),

                new DonneesCompte(
                        "778",
                        "Autres produits exceptionnels",
                        (byte) 7,
                        "778000"),

                new DonneesCompte(
                        "781",
                        "Reprises sur amortissements et provisions (à inscrire dans les produits d'exploitation)",
                        (byte) 7,
                        "781000"),

                new DonneesCompte(
                        "786",
                        "Reprises sur provisions pour risques (à inscrire dans les produits financiers)",
                        (byte) 7,
                        "786000"),

                new DonneesCompte(
                        "787",
                        "Reprises sur provisions (à inscrire dans les produits exceptionnels)",
                        (byte) 7,
                        "787000"),

                new DonneesCompte(
                        "791",
                        "Transferts de charges d'exploitation",
                        (byte) 7,
                        "791000"),

                new DonneesCompte(
                        "796",
                        "Transferts de charges financières",
                        (byte) 7,
                        "796000"),

                new DonneesCompte(
                        "797",
                        "Transferts de charges exceptionnelles",
                        (byte) 7,
                        "797000"),

                new DonneesCompte(
                        "7081",
                        "Produits des services exploités dans l'intérêts du personnel",
                        (byte) 7,
                        "708100"),

                new DonneesCompte(
                        "7082",
                        "Commissions et courtages",
                        (byte) 7,
                        "708200"),

                new DonneesCompte(
                        "7083",
                        "Locations diverses",
                        (byte) 7,
                        "708300"),

                new DonneesCompte(
                        "7084",
                        "Mise à disposition de personnel facturée",
                        (byte) 7,
                        "708400"),

                new DonneesCompte(
                        "7085",
                        "Port et frais accesoires facturés",
                        (byte) 7,
                        "708500"),

                new DonneesCompte(
                        "7086",
                        "Bonis sur reprises d'emballages consignés",
                        (byte) 7,
                        "708600"),

                new DonneesCompte(
                        "7087",
                        "Bonifications obtenues des clients et primes sur ventes",
                        (byte) 7,
                        "708700"),

                new DonneesCompte(
                        "7088",
                        "Autres produits des activités annexes",
                        (byte) 7,
                        "708800"),

                new DonneesCompte(
                        "7091",
                        "Rabais, remises et ristournes accordés sur ventes de produits finis",
                        (byte) 7,
                        "709100"),

                new DonneesCompte(
                        "7092",
                        "Rabais, remises et ristournes accordés sur ventes de produits intermediaires",
                        (byte) 7,
                        "709200"),

                new DonneesCompte(
                        "7094",
                        "Rabais, remises et ristournes accordés sur travaux",
                        (byte) 7,
                        "709400"),

                new DonneesCompte(
                        "7095",
                        "Rabais, remises et ristournes accordés sur études",
                        (byte) 7,
                        "709500"),

                new DonneesCompte(
                        "7096",
                        "Rabais, remises et ristournes accordés sur prestations de services",
                        (byte) 7,
                        "709600"),

                new DonneesCompte(
                        "7097",
                        "Rabais, remises et ristournes accordés sur ventes de marchandies",
                        (byte) 7,
                        "709700"),

                new DonneesCompte(
                        "7098",
                        "Rabais, remises et ristournes accordés sur produits des activités annexes",
                        (byte) 7,
                        "709800"),

                new DonneesCompte(
                        "7133",
                        "Variation des en-cours de production de biens",
                        (byte) 7,
                        "713300"),

                new DonneesCompte(
                        "7134",
                        "Variation des en-cours de production de services",
                        (byte) 7,
                        "713400"),

                new DonneesCompte(
                        "7135",
                        "Variation des stocks de produits",
                        (byte) 7,
                        "713500"),

                new DonneesCompte(
                        "7511",
                        "Redevances pour concessions, brevets, licences, marques, procédés, logiciels",
                        (byte) 7,
                        "751100"),

                new DonneesCompte(
                        "7516",
                        "Droits d'auteur et de reproduction",
                        (byte) 7,
                        "751600"),

                new DonneesCompte(
                        "7518",
                        "Autres droits et valeurs similaires",
                        (byte) 7,
                        "751800"),

                new DonneesCompte(
                        "7551",
                        "Quote-part de perte transférée (comptabilité du gérant)",
                        (byte) 7,
                        "755100"),

                new DonneesCompte(
                        "7555",
                        "Quote-part de bénéfice attribuée (comptabilité des associés non-gérants)",
                        (byte) 7,
                        "755500"),

                new DonneesCompte(
                        "7611",
                        "Revenus des titres de participation",
                        (byte) 7,
                        "761100"),

                new DonneesCompte(
                        "7616",
                        "Revenus sur autres formes de participation",
                        (byte) 7,
                        "761600"),

                new DonneesCompte(
                        "7617",
                        "Revenus des créances rattachées à des participations",
                        (byte) 7,
                        "761700"),

                new DonneesCompte(
                        "7621",
                        "Revenus des titres immobilisés",
                        (byte) 7,
                        "762100"),

                new DonneesCompte(
                        "7626",
                        "Revenus des prêts",
                        (byte) 7,
                        "762600"),

                new DonneesCompte(
                        "7627",
                        "Revenus des créances immobilisées",
                        (byte) 7,
                        "762700"),

                new DonneesCompte(
                        "7631",
                        "Revenus des créances commerciales",
                        (byte) 7,
                        "763100"),

                new DonneesCompte(
                        "7638",
                        "Revenus des créances diverses",
                        (byte) 7,
                        "763800"),

                new DonneesCompte(
                        "7711",
                        "Dédits et pénalités perçus sur achats et sur ventes",
                        (byte) 7,
                        "771100"),

                new DonneesCompte(
                        "7713",
                        "Libéralités reçues",
                        (byte) 7,
                        "771300"),

                new DonneesCompte(
                        "7714",
                        "Rentrées sur créances amorties",
                        (byte) 7,
                        "771400"),

                new DonneesCompte(
                        "7715",
                        "Subventions d'équilibre",
                        (byte) 7,
                        "771500"),

                new DonneesCompte(
                        "7717",
                        "Dégrèvements d'impôts autres qu'impôts sur les bénéfices",
                        (byte) 7,
                        "771700"),

                new DonneesCompte(
                        "7718",
                        "Autres produits exceptionnels sur opérations de gestion",
                        (byte) 7,
                        "771800"),

                new DonneesCompte(
                        "7751",
                        "Immobilisations incorporelles",
                        (byte) 7,
                        "775100"),

                new DonneesCompte(
                        "7752",
                        "Immobilisations corporelles",
                        (byte) 7,
                        "775200"),

                new DonneesCompte(
                        "7756",
                        "Immobilisations financières",
                        (byte) 7,
                        "775600"),

                new DonneesCompte(
                        "7758",
                        "Autres éléments d'actif",
                        (byte) 7,
                        "775800"),

                new DonneesCompte(
                        "7781",
                        "Bonis provenant de clauses d'indexation",
                        (byte) 7,
                        "778100"),

                new DonneesCompte(
                        "7782",
                        "Lots",
                        (byte) 7,
                        "778200"),

                new DonneesCompte(
                        "7783",
                        "Bonis provenant du rachat par l'entreprise d'actions et d'obligations émises par elle-même",
                        (byte) 7,
                        "778300"),

                new DonneesCompte(
                        "7788",
                        "Produits exceptionnels divers",
                        (byte) 7,
                        "778800"),

                new DonneesCompte(
                        "7811",
                        "Reprises sur amortissements des immobilisations incorporelles et corporelles",
                        (byte) 7,
                        "781100"),

                new DonneesCompte(
                        "7815",
                        "Reprises sur provisions pour risques et charges d'exploitation",
                        (byte) 7,
                        "781500"),

                new DonneesCompte(
                        "7816",
                        "Reprises sur dépréciations des immobilisations incorporelles et corporelles",
                        (byte) 7,
                        "781600"),

                new DonneesCompte(
                        "7817",
                        "Reprises sur provisions pour dépréciation des actifs circulants",
                        (byte) 7,
                        "781700"),

                new DonneesCompte(
                        "7865",
                        "Reprises sur provisions pour risques et charges financiers",
                        (byte) 7,
                        "786500"),

                new DonneesCompte(
                        "7866",
                        "Reprises sur provisions pour dépréciation des éléments financiers",
                        (byte) 7,
                        "786600"),

                new DonneesCompte(
                        "7872",
                        "Reprises sur provisions réglementées (immobilisations)",
                        (byte) 7,
                        "787200"),

                new DonneesCompte(
                        "7873",
                        "Reprises sur provision réglementées (stock)",
                        (byte) 7,
                        "787300"),

                new DonneesCompte(
                        "7874",
                        "Reprises sur autres provision réglementées",
                        (byte) 7,
                        "787400"),

                new DonneesCompte(
                        "7875",
                        "Reprises sur provisions exceptionnelles",
                        (byte) 7,
                        "787500"),

                new DonneesCompte(
                        "7876",
                        "Reprises sur dépréciations exceptionnelles",
                        (byte) 7,
                        "787600"),

                new DonneesCompte(
                        "71331",
                        "Produits en cours",
                        (byte) 7,
                        "713310"),

                new DonneesCompte(
                        "71335",
                        "Travaux en cours",
                        (byte) 7,
                        "713350"),

                new DonneesCompte(
                        "71341",
                        "Etudes en cours",
                        (byte) 7,
                        "713410"),

                new DonneesCompte(
                        "71345",
                        "Prestations de services en cours",
                        (byte) 7,
                        "713450"),

                new DonneesCompte(
                        "71351",
                        "Produits intermédiaires",
                        (byte) 7,
                        "713510"),

                new DonneesCompte(
                        "71355",
                        "Produits finis",
                        (byte) 7,
                        "713550"),

                new DonneesCompte(
                        "78111",
                        "Immobilisations incorporelles",
                        (byte) 7,
                        "781110"),

                new DonneesCompte(
                        "78112",
                        "Immobilisations corporelles",
                        (byte) 7,
                        "781120"),

                new DonneesCompte(
                        "78161",
                        "Immobilisations incorporelles",
                        (byte) 7,
                        "781610"),

                new DonneesCompte(
                        "78162",
                        "Immobilisations corporelles",
                        (byte) 7,
                        "781620"),

                new DonneesCompte(
                        "78173",
                        "Stocks et en-cours",
                        (byte) 7,
                        "781730"),

                new DonneesCompte(
                        "78174",
                        "Créances",
                        (byte) 7,
                        "781740"),

                new DonneesCompte(
                        "78662",
                        "Immobilisations financières",
                        (byte) 7,
                        "786620"),

                new DonneesCompte(
                        "78665",
                        "Valeurs mobilières de placements",
                        (byte) 7,
                        "786650"),

                new DonneesCompte(
                        "78725",
                        "Amortissement dérogatoires",
                        (byte) 7,
                        "787250"),

                new DonneesCompte(
                        "78726",
                        "Provision spéciale de réévaluation",
                        (byte) 7,
                        "787260"),

                new DonneesCompte(
                        "78727",
                        "Plus-values réinvesties",
                        (byte) 7,
                        "787270"));
    }

    /**
     * Fournit les comptes de classe 8.
     *
     * @return flux des comptes de classe 8
     */
    private static Stream<DonneesCompte> fournirClasse8() {
        return Stream.empty();
    }

    /**
     * Fournit les comptes de classe 9.
     *
     * @return flux des comptes de classe 9
     */
    private static Stream<DonneesCompte> fournirClasse9() {
        return Stream.empty();
    }
}
