package no.nith.pg6100.swing;

import no.nith.pg6100.PersonDataAksess;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JPanel{
    public MainWindow() {
        super(new GridLayout(2,0));
        PersonDataAksess pda=new PersonDataAksess();
        TabellModellen tabellModellen=new TabellModellen(pda);
        PersonForm form = new PersonForm(pda);
        form.leggTilEndringsLytter(tabellModellen);
        PersonTabell tabell = new PersonTabell(tabellModellen);
        add(form);
        add(tabell);
    }
}
