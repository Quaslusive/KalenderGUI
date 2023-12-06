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

        //By making the days in columns instead of rows its easier for the user to skim through the days.
        panalMain = new JPanel(new GridLayout(0, 7)); // Column

        // Deklarerar dagens datum och början av veckan
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);

        // Formatterar LocalDate till string
        // Jag har försökt att implementer "DateTimeFormatter" i själva vecko kalenden, men jag kunde inte få det att
        // funka ihop med "TemporalAccessor". Jag använder detta formatering i "setTitle"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL");
        String formattedToday = today.format(formatter);

        for (int i = 0; i < 7; i++) {
            //Adding every day in their own JPanel and adding a border to better accentuate the days.
            JPanel dayPanel = new JPanel();
            dayPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            dayPanel.setLayout(new GridLayout(4, 0));//4 rows evenly distribute the elements on dayPanel.

            LocalDate day = startOfWeek.plusDays(i); // Incrementer en dag
            dayLabels[i] = new JLabel(String.valueOf(day)); // Populärar elementen med datum
            if (day.equals(today)) { // Om en dag == med dagens datum, blir texten röd
                Font fontCurrentDag = new Font(Font.SANS_SERIF, Font.BOLD, 20);
                //dayLabels displays the corresponding days date
                dayLabels[i].setFont(fontCurrentDag);
                dayLabels[i].setForeground(Color.RED);

            }
            //Maybe some more comment here about what does what?
            //dayTexts displays your activity
            dayTexts[i] = new JTextArea();
            dayTexts[i].setEditable(false);
            //Button which opens a JOptionPane that lets you insert an activity
            addButtons[i] = new JButton("Lägg till event");
            int finalI = i; //  Förslag från IntelliJ
            addButtons[i].addActionListener(e -> dayTexts[finalI].setText(JOptionPane.showInputDialog("Skriv in din event :) ")));
            //Buttons to clear events form your dayTexts
            clearButtons[i] = new JButton("Ta bort event");
            clearButtons[i].addActionListener(e -> dayTexts[finalI].setText(""));

            //Adding all the elements to their corresponding dayPanel
            dayPanel.add(dayLabels[i]);
            dayPanel.add(dayTexts[i]);
            dayPanel.add(addButtons[i]);
            dayPanel.add(clearButtons[i]);
            panalMain.add(dayPanel);
        }
        //Adding all the new dayPanels to the frame and setting some values for the frame
        add(panalMain);
        setTitle("Veckans Kalender. Idag är det " + formattedToday);
        setSize(1000, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
