package no.nith.pg6100.swing;

import no.nith.pg6100.Person;
import no.nith.pg6100.PersonDataAksess;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class TabellModellen extends DefaultTableModel implements PersonForm.EndringsLytter {
    private final PersonDataAksess pda;

    public TabellModellen(PersonDataAksess pda) {
        this.pda = pda;
        addColumn("personid");
        addColumn("fornavn");
        addColumn("etternavn");
        addColumn("epost");
        populerPersoner();
    }

    private void populerPersoner() {
        List allePersoner = pda.hentAllePersoner();
        for (Object anAllePersoner : allePersoner) {
            leggTilPerson((Person) anAllePersoner);
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    private void leggTilPerson(Person person) {
        Vector egenskaper = new Vector(Arrays.asList(new Object[]{person.getPersonId(), person.getFornavn(), person.getEtternavn(), person.getEpost()}));
        addRow(egenskaper);
    }

    public void personLagret(Person person) {
        fjernAlle();
        populerPersoner();
    }

    private void fjernAlle() {
        while(getRowCount()>0)
            removeRow(0);
    }

    public void populerMedData(List personer) {
        fjernAlle();
        for(Object person:personer)
            leggTilPerson((Person) person);
    }
}
