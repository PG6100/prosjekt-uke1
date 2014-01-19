package no.nith.pg6100.swing;

import no.nith.pg6100.Person;
import no.nith.pg6100.PersonDataAksess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class PersonForm extends JPanel {

    private final JButton lagre = new JButton("Lagre");
    private final PersonDataAksess pda;
    private final Vector<EndringsLytter> lyttere=new Vector<EndringsLytter>();

    private final JButton søk = new JButton("Søk");
    private final JTextField fornavn = new JTextField(10), etternavn = new JTextField(10), epost = new JTextField(10);
    private final JTextField[] tekstFelter = new JTextField[]{fornavn, etternavn, epost};

    public PersonForm(PersonDataAksess pda) {
        super(new SpringLayout());
        this.pda = pda;
        String[] labels = {"Fornavn: ", "Etternavn: ", "epost: "};
        int numPairs = labels.length;

        for (int i = 0; i < labels.length; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            add(l);
            l.setLabelFor(tekstFelter[i]);
            add(tekstFelter[i]);
        }
        lagre.addActionListener(getLagreActionListener());
        søk.addActionListener(getSøkListener());
        final JLabel info = new JLabel("");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(lagre);
        buttonPanel.add(søk);
        info.setLabelFor(buttonPanel);
        add(info);
        add(buttonPanel);
        numPairs++;

        lagKompaktGrid(this,
                numPairs, 2, //rad, kol
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad


    }

    private ActionListener getSøkListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final JTextField fornavnSøkeText = new JTextField();
                EventQueue.invokeLater(new Runnable() {public void run() {fornavnSøkeText.grabFocus();fornavnSøkeText.requestFocus();}});
                final JComponent[] komponenter = new JComponent[] {new JLabel("Søk fornavn:"),fornavnSøkeText};
                JOptionPane.showMessageDialog(PersonForm.this, komponenter, "Søk fornavn: ", JOptionPane.PLAIN_MESSAGE);
                if (fornavnSøkeText.getText() != null && fornavnSøkeText.getText().trim().length() > 0) {
                    System.out.println("søker: "+fornavnSøkeText.getText());
                    List l=pda.hentPersonMedFornavn(fornavnSøkeText.getText());
                    System.out.println(l);
                    fireEndringsHendelse(l);
                }
            }
        };
    }
    private ActionListener getLagreActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lagre.setEnabled(false);
                lagre.setText(".....");
                new SwingWorker<Object, Object>() {
                    protected Object doInBackground() throws Exception {
                        try {
                            Person person = new Person();
                            person.setFornavn(fornavn.getText());
                            person.setEtternavn(etternavn.getText());
                            person.setEpost(epost.getText());
                            pda.lagrePerson(person);
                            fireEndringsHendelse(person);
                        } finally {
                            lagre.setEnabled(true);
                            lagre.setText("Lagre");
                        }
                        return null;
                    }
                }.execute();
            }
        };
    }


    public void leggTilEndringsLytter(TabellModellen tabellModellen) {
        this.lyttere.add(tabellModellen);
    }

    private void fireEndringsHendelse(List personer) {
        for (EndringsLytter el : lyttere) {
            el.populerMedData(personer);
        }
    }

    private void fireEndringsHendelse(Person person) {
        for (EndringsLytter el : lyttere) {
            el.personLagret(person);
        }
    }

    public interface EndringsLytter{

        public void personLagret(Person person);

        public void populerMedData(List personer);

    }

    public static void lagKompaktGrid(Container parent, int rows, int cols, int initialX, int initialY, int xPad, int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout) parent.getLayout();
        } catch (ClassCastException exc) {
            System.err.println("The first argument to lagKompaktGrid must use SpringLayout.");
            return;
        }

        //Align all cells in each column and make them the same width.
        Spring x = Spring.constant(initialX);
        for (int c = 0; c < cols; c++) {
            Spring width = Spring.constant(0);
            for (int r = 0; r < rows; r++) {
                width = Spring.max(width, getConstraintsForCell(r, c, parent, cols).getWidth());
            }
            for (int r = 0; r < rows; r++) {
                SpringLayout.Constraints constraints = getConstraintsForCell(r, c, parent, cols);
                constraints.setX(x);
                constraints.setWidth(width);
            }
            x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
        }

        //Align all cells in each row and make them the same height.
        Spring y = Spring.constant(initialY);
        for (int r = 0; r < rows; r++) {
            Spring height = Spring.constant(0);
            for (int c = 0; c < cols; c++) {
                height = Spring.max(height, getConstraintsForCell(r, c, parent, cols).getHeight());
            }
            for (int c = 0; c < cols; c++) {
                SpringLayout.Constraints constraints = getConstraintsForCell(r, c, parent, cols);
                constraints.setY(y);
                constraints.setHeight(height);
            }
            y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
        }

        SpringLayout.Constraints pCons = layout.getConstraints(parent);
        pCons.setConstraint(SpringLayout.SOUTH, y);
        pCons.setConstraint(SpringLayout.EAST, x);
    }


    private static SpringLayout.Constraints getConstraintsForCell(int row, int col, Container parent, int cols) {
        SpringLayout layout = (SpringLayout) parent.getLayout();
        Component c = parent.getComponent(row * cols + col);
        return layout.getConstraints(c);
    }
}
