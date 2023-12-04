import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
/*

Skriven av Carl Sundberg

 */

public class KalenderGUI extends JFrame {

    private JPanel panalMain;
    private JLabel[] dayLabels = new JLabel[7];
    private JTextArea[] dayTexts = new JTextArea[7];
    private JButton[] addButtons = new JButton[7];
    private JButton[] clearButtons = new JButton[7];

    KalenderGUI() {
        // Det är fortfarande svårt för mig att förstå hur de diverse layout manager funkar ihop och hur man effektivt
        // kan skapa flera Jpanel i samma frame.
        panalMain = new JPanel(new GridLayout(0, 2)); // Column

        // Deklarerar dagens datum och början av veckan
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);

        // Formatterar LocalDate till string
        // Jag har försökt att implementer "DateTimeFormatter" i själva vecko kalenden, men jag kunde inte få det att
        // funka ihop med "TemporalAccessor". Jag använder detta formatering i "setTitle"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL");
        String formattedToday = today.format(formatter);

        for (int i = 0; i < 7; i++) {
            LocalDate day = startOfWeek.plusDays(i); // Incrementer en dag
            dayLabels[i] = new JLabel(String.valueOf(day)); // Populärar elementen med datum
            if (day.equals(today)) { // Om en dag == med dagens datum, blir texten röd
                Font fontCurrentDag = new Font(Font.SANS_SERIF, Font.BOLD, 20);
                dayLabels[i].setFont(fontCurrentDag);
                dayLabels[i].setForeground(Color.RED);

            }
            dayTexts[i] = new JTextArea();
            dayTexts[i].setEditable(false);
            addButtons[i] = new JButton("Lägg till event");
            int finalI = i; //  Förslag från IntelliJ
            addButtons[i].addActionListener(e -> dayTexts[finalI].setText(JOptionPane.showInputDialog("Skriv in din event :) ")));
            clearButtons[i] = new JButton("Ta bort event");
            clearButtons[i].addActionListener(e -> dayTexts[finalI].setText(""));

            panalMain.add(dayLabels[i]);
            panalMain.add(dayTexts[i]);
            panalMain.add(addButtons[i]);
            panalMain.add(clearButtons[i]);
        }

        add(panalMain);
        setTitle("Veckans Kalender. Idag är det " + formattedToday);
        setSize(1000, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
