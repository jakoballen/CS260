import com.github.lgooddatepicker.components.DateTimePicker;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

/**
 * Class RuleGUI - GUI driver for Rule Generation
 *
 * Created by: Jakob Allen (allenjd4061), 6 December 2019
 */

public class RuleGUI extends JFrame{
    //JFrame settings and properties
    private static final int WIDTH = 770;      //Width of JFrame
    private static final int HEIGHT = 770;     //Height of JFrame
    private DefaultListCellRenderer dlcr;      //Center align JComboBox
    private NumberFormatter nf;                //Number formatter for data fields that only contain numbers
    private Listener l;                        //Listener to react to window events

    //Components for user input
    private JComboBox<String> option;          //ComboBox for query options
    private JComboBox<String> tableSelect;     //ComboBox for table options
    private JFormattedTextField[] idRange;     //TextFields to enter ID range
    private DateTimePicker start;              //Allows user to pick the lower bound of query
    private DateTimePicker end;                //Allows user to pick the upper bound of query
    private JButton submit;                    //Button to process the input

    //Static components
    private JLabel prompt;                     //Label for query options prompt
    private JLabel to;                         //Label in middle of query options
    private String[] options;                  //Strings for query options
    private String[] tables;                   //Strings for both tables
    private JPanel userInput;                  //Contains elements gathering user input
    private JPanel input;                      //Contains other elements gathering user input
    private JTextArea output;                  //TextArea to display output
    private JScrollPane scroll;                //Allows the output to scroll
    private JButton done;                      //Allows the user to finish the action and start again

    public RuleGUI() {
        setTitle("Rule Miner");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createContents();
        setVisible(true);
    }

    /**
     * Create the contents of the JFrame
     */
    private void createContents(){
        //Create arrays for JComboBox options
        options = new String[]{"Select All","Select by Rule ID", "Select by Date"};
        tables = new String[]{"Select Table", "Rules", "RulesFull"};

        //Instantiate components required
        l = new Listener();
        nf = new NumberFormatter(NumberFormat.getIntegerInstance(Locale.US));
        tableSelect = new JComboBox<>(tables);
        option = new JComboBox<>(options);
        input = new JPanel(new FlowLayout());
        userInput = new JPanel(new BorderLayout());
        prompt = new JLabel("From ");
        to = new JLabel(" to ");
        submit = new JButton("Submit");
        start = new DateTimePicker();
        end = new DateTimePicker();
        done = new JButton("Done");
        idRange = new JFormattedTextField[2];
        for(int i =0; i< idRange.length; i++){
            idRange[i] = new JFormattedTextField(nf);
            idRange[i].setPreferredSize(new Dimension(300,20));
        }
        output = new JTextArea(38,10);
        scroll = new JScrollPane (output);

        dlcr = new DefaultListCellRenderer(); //center align JComboBoxes
        dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);

        //Set properties for components
        tableSelect.setRenderer(dlcr);
        tableSelect.addActionListener(l);

        option.setRenderer(dlcr);
        option.setVisible(false);
        option.addActionListener(l);

        prompt.setHorizontalAlignment(JTextField.CENTER);

        to.setHorizontalAlignment(JTextField.CENTER);
        to.setMaximumSize(new Dimension(5,5));


        submit.setPreferredSize(new Dimension(80, 20));
        submit.addActionListener(l);

        output.setEditable(false);

        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVisible(false);
        start.setPreferredSize(new Dimension(300,25));

        done.addActionListener(l);
        done.setVisible(false);

        end.setPreferredSize(new Dimension(300,20));

        //Add contents to panels
        userInput.add(option, BorderLayout.CENTER);
        userInput.add(tableSelect, BorderLayout.NORTH);

        add(scroll, BorderLayout.CENTER);
        add(done, BorderLayout.SOUTH);
        add(userInput, BorderLayout.NORTH);
    }//end - method createContents

    public static void main(String[] args) {
        //set properties specific to macOS
        if(System.getProperty("os.name").toLowerCase().contains("mac os x")) {
            System.setProperty("apple.awt.brushMetalLook", "true");
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("apple.awt.application.name", "Rule Miner");
            System.setProperty("apple.awt.textantialiasing", "true");
        }
        //set look and feel regardless of OS
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("Source for theme not found, falling back to default");
        }
        new RuleGUI();
    }//end - method main

    /**
     * Reset the window to the initial state
     */
    private void reset(){
        scroll.setVisible(false);
        done.setVisible(false);
        input.removeAll();
        output.setText("");
        idRange[0].setEnabled(true);
        idRange[1].setEnabled(true);
        done.setVisible(false);
        for(JFormattedTextField jftf: idRange){
            jftf.setText("");
        }
        submit.setEnabled(true);
        submit.setVisible(false);
        option.setEnabled(true);
        option.setVisible(false);
        tableSelect.setEnabled(true);
        option.setSelectedIndex(0);
        tableSelect.setSelectedIndex(0);
        input.remove(submit);
        setVisible(true);
    }//end - method reset

    private class Listener implements ActionListener{
        DataAccessObject dao;                               //Access to the database
        ExecutorService es;                                 //ExecutorService so the rule generation doesn't freeze the program
        RuleSet rs;                                         //List of all rules
        int flag = 0;                                       //to determine if window listener has been added

        public Listener() {
            this.dao = new DataAccessObject();
            dao.connect();
        }

        public void actionPerformed(ActionEvent e) {
            //add window listener to close database connection after window is closed
            if(flag == 0) {
                addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        dao.disconnect(); //disconnect from the database
                        es.shutdownNow(); //shut down the thread
                        System.exit(0);
                    }
                });
                flag = 1; //signal that the listener has been added
            }
            if(e.getSource() == tableSelect && tableSelect.getSelectedIndex() != 0){ //if a table has been selected
                //disable components from previous step and add components for the next
                tableSelect.setEnabled(false);
                option.setEnabled(true);
                option.setVisible(true);
                //Set the database to the one chosen by the user
                if(tableSelect.getSelectedIndex() == 1){
                    dao.setDatabase("CS260");
                    dao.setCollection("Rules");
                }else{
                    dao.setDatabase("CS260");
                    dao.setCollection("RulesFull");
                }
            }else if(e.getSource() == option) { //if a query option has been selected
                //disable components from previous step and add components for the next
                option.setEnabled(false);
                if (option.getSelectedIndex() != 0) {
                    input.add(prompt);
                    if (option.getSelectedIndex() == 1) {
                        input.add(idRange[0]);
                        input.add(to);
                        input.add(idRange[1]);
                    }else if(option.getSelectedIndex() == 2){
                        input.add(start);
                        input.add(to);
                        input.add(end);
                    }
                }
                input.add(submit);
                submit.setVisible(true);
                userInput.add(input, BorderLayout.SOUTH);
                setVisible(true);
            }else if (e.getSource() == submit) { //if the user wants to execute the query
                //disable components from the previous step
                submit.setEnabled(false);
                idRange[0].setEnabled(false);
                idRange[1].setEnabled(false);

                if(option.getSelectedIndex() == 0) { //user wants to find all from the collection
                    dao.executeQueryAll();
                }else if(option.getSelectedIndex() == 1){   //user has selected a range of IDs
                    try {
                        dao.executeQueryIDRange(NumberFormat.getNumberInstance().parse(idRange[0].getText()).intValue(), NumberFormat.getNumberInstance().parse(idRange[1].getText()).intValue());
                    }catch(ParseException pe){
                        System.out.println("parse exception encountered trying to query");
                    }
                }else{  //user has selected a date range
                    dao.executeQueryDateRange(Date.from(start.getDateTimePermissive().atZone(ZoneId.systemDefault()).toInstant()), Date.from(end.getDateTimePermissive().atZone(ZoneId.systemDefault()).toInstant()));
                }

                //Generate rules in a new thread to not freeze the GUI
                es = Executors.newSingleThreadExecutor();
                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        //Process the results and add the results to the output database
                        rs = dao.processResults();
                        int numRulesFromDB = rs.size();
                        dao.setDatabase("ALLENJD4061");
                        dao.setCollection("RulesOutput");
                        dao.deleteAll();
                        dao.addManyRules(rs);
                        generateRules(rs, rs.size());
                        output.append("\nNumber of new rules: " + (rs.size()-numRulesFromDB));
                    }
                });
                es.shutdown();

                //Add component to view output
                scroll.setVisible(true);
                done.setVisible(true);
                setVisible(true);
            }else if(e.getSource() == done){    //user has finished the action
                es.shutdownNow(); //stop the previous query if it is still going on
                reset();
            }
        }//end - method actionPerformed

        /**
         * Generates transitive rules from the given RuleSet
         * @param rules initial set of rules to be parsed
         * @param numAdded number of rules that haven't been checked
         */
        private void generateRules(RuleSet rules, int numAdded) {
            if (numAdded != 0) { //if there were rules added in the last iteration
                int offset = rules.size() - numAdded; //index of the first unchecked rule
                RuleSet newRules = new RuleSet(); //set of rules generated in this iteration
                int numNew = 0; //number of new rules in this iteration
                for (int i = 0; i < rules.size(); i++) {
                    for (int j = i + 1 + offset; j < rules.size(); j++) {
                        Rule r = null;
                        if (rules.get(i).getR_rhs().containsAll(rules.get(j).getR_lhs())) { //if the lhs element at j is a subset of the rhs of the element at i
                            r = new Rule(dao.nextIndex(), new Date(), rules.get(i).getR_lhs(), rules.get(j).getR_rhs());
                        }else if (rules.get(j).getR_rhs().containsAll(rules.get(i).getR_lhs())) {   //if the lhs element at i is a subset of the rhs of the element at j
                            r = new Rule(dao.nextIndex(), new Date(), rules.get(j).getR_lhs(), rules.get(i).getR_rhs());
                        }
                        if (r != null && !r.elementOnBothSides() && !newRules.contains(r) && !rules.contains(r)) {
                            numNew++;
                            newRules.add(r);
                            output.append(r.toString() + "\n");
                        }
                    }
                    rs.addOtherRuleSet(newRules);
                }
                //add new rules to the output database if there are any
                if(!newRules.isEmpty()) {
                    dao.addManyRules(newRules);
                }
                //try to generate more rules based off new rules
                generateRules(rules, numNew);
            }
        }//end - method generateRules

    }//end - class Listener

}//end - class RuleGUI



