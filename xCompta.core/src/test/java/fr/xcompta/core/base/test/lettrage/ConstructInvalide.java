package fr.xcompta.core.base.test.lettrage;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import fr.xcompta.core.base.*;
import fr.xcompta.core.base.exception.XComptaCoreException;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;
import fr.xcompta.core.base.exception.lettrage.*;

/** Tests de rejet atomique des lettrages incohérents. */
class ConstructInvalide {
    private static final LocalDate DATE = LocalDate.of(2026, 10, 9);

    @Test
    void rejetteDatesEtListesInvalides() throws XComptaCoreException {
        List<MouvementValidee> mouvements = Construct1Arg.mouvements();
        assertThrows(IllegalArgumentException.class, () -> new Lettrage(-1, DATE, mouvements));
        assertThrows(XComptaCoreNullPointerException.class, () -> new Lettrage(0, null, mouvements));
        assertThrows(LettrageNbMouvementsInferieurADeuxException.class,
                () -> new Lettrage(0, DATE, null));
        assertThrows(LettrageNbMouvementsInferieurADeuxException.class,
                () -> new Lettrage(0, DATE, List.of(mouvements.get(0))));
        assertFalse(mouvements.get(0).estLettre());
    }

    @Test
    void rejetteNullDoublonEtDesequilibreSansRattachement() throws XComptaCoreException {
        List<MouvementValidee> mouvements = Construct1Arg.mouvements();
        List<MouvementValidee> avecNull = new ArrayList<>(mouvements);
        avecNull.set(1, null);
        assertThrows(XComptaCoreException.class, () -> new Lettrage(0, DATE, avecNull));
        assertThrows(LettrageMouvementDupliqueException.class,
                () -> new Lettrage(0, DATE, List.of(mouvements.get(0), mouvements.get(0))));
        Compte compte = mouvements.get(0).getCompte();
        MouvementValidee autre = new MouvementValidee(3, new BigDecimal("-119"), compte);
        assertThrows(LettrageNonEquilibreException.class,
                () -> new Lettrage(0, DATE, List.of(mouvements.get(0), autre)));
        assertFalse(mouvements.get(0).estLettre());
        assertFalse(autre.estLettre());
    }

    @Test
    void rejetteComptesDifferentsEtMouvementDejaLettre() throws XComptaCoreException {
        List<MouvementValidee> mouvements = Construct1Arg.mouvements();
        MouvementValidee autreCompte = new MouvementValidee(3, new BigDecimal("-120"),
                new Compte("411", "Clients"));
        assertThrows(LettrageCompteDifferentException.class,
                () -> new Lettrage(0, DATE, List.of(mouvements.get(0), autreCompte)));
        assertFalse(mouvements.get(0).estLettre());
        new Lettrage(1, DATE, mouvements);
        assertThrows(LettrageMouvementDejaLettreException.class,
                () -> new Lettrage(2, DATE, mouvements));
    }
}
