package no.nith.pg6100.swing;


import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class PersonTabell extends JPanel {

    public PersonTabell(TableModel tableModel) {
        super(new GridLayout(1,0));
        final JTable table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table));
    }

}
