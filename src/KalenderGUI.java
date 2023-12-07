import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
Skriven av Carl Sundberg
 */
public class KalenderGUI extends JFrame {

    private final JTextArea[] dayTexts = new JTextArea[7];
    JLabel[] dayLabels = new JLabel[7];
    JButton[] addButtons = new JButton[7];
    JButton[] clearButtons = new JButton[7];

    KalenderGUI() {
        // Det är fortfarande svårt för mig att förstå hur de diverse layout manager funkar ihop och hur man effektivt
        // kan skapa flera JPanel i samma frame.

        //By making the days in columns instead of rows it's easier for the user to skim through the days.
        JPanel mainPanel = new JPanel(new GridLayout(0, 7)); // Column

        // Deklarerar dagens datum och början av veckan
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);

        // Formaterar LocalDate till string
        // Jag har försökt att implementer "DateTimeFormatter" i själva veckokalendern, men jag kunde inte få det att
        // funka ihop med "TemporalAccessor". Jag använder detta formatering i "setTitle"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL");
        String formattedToday = today.format(formatter);

        for (int i = 0; i < 7; i++) {
            //Adding every day in their own JPanel and adding a border to better accentuate the days.
            JPanel dayPanel = new JPanel(new BorderLayout());
            dayPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            //Adding a second panel to the bottom of the day and adding my buttons to it to further make the calendar more readable.
            JPanel bottomPanel = new JPanel(new GridLayout(2, 0));

            LocalDate day = startOfWeek.plusDays(i); // Incrementer en dag
            dayLabels[i] = new JLabel(String.valueOf(day)); // Lägger in datum på elementen
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
            byte finalI = (byte) i;
            addButtons[i].addActionListener(e -> dayTexts[finalI].setText(JOptionPane.showInputDialog("Skriv in din event :) ")));
            //Buttons to clear events form your dayTexts
            clearButtons[i] = new JButton("Ta bort event");
            clearButtons[i].addActionListener(e -> dayTexts[finalI].setText(""));

            //Adding all the elements to their corresponding dayPanels and bottomPanels then adding them to mainPanel
            dayPanel.add(dayLabels[i], BorderLayout.NORTH);
            dayPanel.add(dayTexts[i]);
            bottomPanel.add(addButtons[i]);
            bottomPanel.add(clearButtons[i]);
            dayPanel.add(bottomPanel, BorderLayout.SOUTH);
            mainPanel.add(dayPanel);
        }
        //Adding all the new dayPanels to the frame and setting some values for the frame
        add(mainPanel);
        setTitle("Veckans Kalender. Idag är det " + formattedToday);
        setSize(1000, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}