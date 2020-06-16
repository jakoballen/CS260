import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

/**
 * Class HumResourceGUI - GUI driver for Humanitarian Resource Database Access
 *
 * Created by: Jakob Allen (allenjd4061), 20 November 2019
 */


public class HumResourceGUI extends JFrame{
    //static settings for window
    private static final int WIDTH = 500; //width of JFrame
    private static final int HEIGHT = 700; //height of JFrame
    private DefaultListCellRenderer dlcr; //center align JComboBox

    //initial user input
    private JPanel selects; //JPanel to hold components for user to select action, table, and HRID
    private JComboBox<String> tableSelect; //display available tables to the user
    private String[] tables; //Strings representing available tables
    private JComboBox<String> actionSelect; //display available actions to user
    private String[] actions; //Strings representing available actions
    private JPanel HRIDIn; //JPanel to hold HRID input
    private JComboBox HRIDInput; //display available HRIDs
    private JLabel HRIDPrompt; //label the HRID input box

    //static buttons
    private JButton sub; //submit button
    private JButton finish; //finish/confirm buttom
    private JButton cancel; //cancel button

    //data fields for all tables
    private JPanel window; //JPanel that holds data fields
    private JLabel[] commonFields; //labels for common fields
    private JTextField[] commonFieldData; //data fields for common fields
    private JLabel[] mcFields; //labels for MedicalCenter fields
    private JFormattedTextField[] mcFieldsData; //data fields for MedicalCenter fields
    private JLabel[] waterFields; //labels for Water fields
    private JFormattedTextField[] waterFieldsData; //data fields for Water fields
    private JLabel[] foodFields; //labels for Food fields
    private JTextField[] foodFieldsData; //data for Food fields
    private JComboBox fTypeFieldData; //Contains options for Food Type

    public HumResourceGUI() {
        setTitle("Humanitarian Resource Database");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createContents();
        setVisible(true);
    }

    /**
     * Instantiate the more static contents of the JFrame
     */
    private void createContents(){
        Listener n = new Listener(); //listener to respond to swing events
        dlcr = new DefaultListCellRenderer(); //center align JComboBoxes
        dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);

        //create buttons
        finish = new JButton("Finish");
        finish.addActionListener(n);
        cancel = new JButton("Cancel");
        cancel.addActionListener(n);
        sub = new JButton("Submit");
        sub.addActionListener(n);

        createFieldElements();

        selects = new JPanel(new GridLayout(3,1));
        HRIDInput = new JComboBox();

        //create object to allow a table to be selected
        tables = new String[]{"Select Resource Type","Medical Center", "Water", "Food"};
        tableSelect = new JComboBox<>(tables);
        tableSelect.setRenderer(dlcr);
        tableSelect.addActionListener(n);
        tableSelect.setVisible(false);

        //create object to allow an action to be selected
        actions = new String[]{"Select Action","Insert", "Update", "Delete"};
        actionSelect = new JComboBox<>(actions);
        actionSelect.setRenderer(dlcr);
        actionSelect.addActionListener(n);

        //create object to allow an action to be selected
        HRIDIn = new JPanel(new GridLayout(1,3));
        HRIDPrompt = new JLabel("HRID: ");
        HRIDPrompt.setHorizontalAlignment(JTextField.CENTER);
        HRIDIn.add(HRIDPrompt);
        HRIDIn.setVisible(false);

        selects.add(actionSelect);
        selects.add(tableSelect);
        selects.add(HRIDIn);

        add(selects, BorderLayout.PAGE_START);

    }

    /**
     * Reset the GUI window to its original state
     */
    private void reset(){
        createFieldElements();
        actionSelect.setEnabled(true);
        tableSelect.setEnabled(true);
        HRIDIn.setVisible(false);
        HRIDIn.remove(HRIDInput);
        HRIDInput.setEnabled(true);
        window.setVisible(false);
        tableSelect.setSelectedIndex(0);
        actionSelect.setSelectedIndex(0);
        sub.setEnabled(true);
        tableSelect.setVisible(false);
        //commonFieldData[5].setEnabled(true);
    }

    /**
     * Add the the text fields to the GUI window
     * @param index Determine which table's elements get added to the GUI
     * @param action Determines if the fields should be enabled or not
     */
    private void addTextFields(int index, String action){
        int rows = index == 0 ? 14 : 13; //medical center has one more field than other tables
        window = new JPanel(new GridLayout(rows, 2));

        //add common fields to the window
        window.add(commonFields[8]);
        window.add(commonFieldData[8]);
        for(int i = 0; i < 8; i++){
            window.add(commonFields[i]);
            window.add(commonFieldData[i]);
            if (action.equals("Delete")) {
                commonFieldData[i].setEnabled(false);
            }
        }
        commonFieldData[8].setEnabled(false); //don't allow hrid to be updated

        //add subclass fields to the window based on selected table
        for (int i = 0; i<4; i++){
            if(index == 0) {
                window.add(mcFields[i]);
                window.add(mcFieldsData[i]);
                if (action.equals("Delete")) {
                    mcFieldsData[i].setEnabled(false);
                }
            }else if(index == 1 && i != 3){
                window.add(waterFields[i]);
                window.add(waterFieldsData[i]);
                if (action.equals("Delete")) {
                    waterFieldsData[i].setEnabled(false);
                }
            }else if(index == 2 && i !=3){
                window.add(foodFields[i]);
                if(i == 0){
                    window.add(fTypeFieldData);
                }else {
                    window.add(foodFieldsData[i]);
                }
                if (action.equals("Delete")) {
                    foodFieldsData[i].setEnabled(false);
                    fTypeFieldData.setEnabled(false);
                }
            }
        }

        //add the last row of items
        window.add(finish);
        window.add(cancel);

        //add the window to the JFrame
        add(window, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Instantiate the fields required for each table
     */
    private void createFieldElements(){
        NumberFormatter nf = new NumberFormatter(NumberFormat.getIntegerInstance(Locale.US)); //number formatter for data fields that only contain numbers

        //create common field labels
        commonFields = new JLabel[9];
        commonFields[8]= new JLabel("Resource ID");
        commonFields[0] = new JLabel("Resource Name");
        commonFields[1] = new JLabel("Address");
        commonFields[2] = new JLabel("PhoneNumber");
        commonFields[3] = new JLabel("Latitude");
        commonFields[4] = new JLabel("Longitude");
        commonFields[5] = new JLabel("Type");
        commonFields[6] = new JLabel("Description");
        commonFields[7] = new JLabel("Operational Hours");

        //create common field data fields
        commonFieldData = new JTextField[9];
        for(int i = 0; i<9; i++){
            commonFields[i].setHorizontalAlignment(JTextField.CENTER);
            commonFieldData[i] = new JTextField();
        }
        commonFieldData[5].setEnabled(false);

        //create medical center data labels
        mcFields = new JLabel[4];
        mcFields[0] = new JLabel("Number of Beds Available");
        mcFields[1] = new JLabel("Emergency Room Capacity");
        mcFields[2] = new JLabel("Number of Doctors");
        mcFields[3] = new JLabel("Number of Nurses");

        //create medical center data fields
        mcFieldsData = new JFormattedTextField[4];
        for(int i=0; i<4; i++){
            mcFields[i].setHorizontalAlignment(JTextField.CENTER);
            mcFieldsData[i] = new JFormattedTextField(nf);
        }

        //create water field labels
        waterFields = new JLabel[3];
        waterFields[0] = new JLabel("Number of 10oz Bottle Available");
        waterFields[1] = new JLabel("Number of Half Liter Bottle Available");
        waterFields[2] = new JLabel("Number of 5 Gallon Jugs Available");

        //create water data fields
        waterFieldsData = new JFormattedTextField[3];
        for(int i=0; i<3; i++){
            waterFields[i].setHorizontalAlignment(JTextField.CENTER);
            waterFieldsData[i] = new JFormattedTextField(nf);
        }

        //create food field labels
        foodFields = new JLabel[4];
        foodFields[0] = new JLabel("Food Type");
        foodFields[1] = new JLabel("Number of Meals Available");
        foodFields[2] = new JLabel("Food Description");

        //create food data fields
        foodFieldsData = new JTextField[4];
        for(int i=0; i<3; i++){
            foodFields[i].setHorizontalAlignment(JTextField.CENTER);
            if(i!=1) {
                foodFieldsData[i] = new JTextField();
            }else{
                foodFieldsData[i] = new JFormattedTextField(nf);
            }
        }
        String[] options = {"Select Option","Grocery", "Distributor", "Restaurant"}; //options for food type field
        fTypeFieldData = new JComboBox<>(options);
        fTypeFieldData.setRenderer(dlcr);

    }

    public static void main(String[] args){
        //set properties specific to macOS
        if(System.getProperty("os.name").toLowerCase().contains("mac os x")) {
            System.setProperty("apple.awt.brushMetalLook", "true");
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("apple.awt.application.name", "Humanitarian Resource Database");
            System.setProperty("apple.awt.textantialiasing", "true");
        }

        //set look and feel regardless of OS
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("Source for theme not found, falling back to default");
        }

        new HumResourceGUI();
    }

    /**
     * Class Listener - Responds to actions that occur on the GUI
     *
     * Created by: Jakob Allen (allenjd4061), 20 November 2019
     */
    private class Listener implements ActionListener {
        //make connection early to speed up the rest of the program
        private DataAccessObject dao = new DataAccessObject(); //connection to database
        private Connection conn = dao.connect(); //connection to database

        private HumResource hr; //represents row in table
        private ArrayList<Integer> availHRID; //list of HRIDs in database
        int flag = 0; //to determine if window listener has been added


        public void actionPerformed(ActionEvent e){
            //add window listener to close database connection after window is closed
            if(flag == 0) {
                addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        dao.disconnect();
                        System.exit(0);
                    }
                });
                flag = 1; //signal that the listener has been added
            }

           String table = (String) tableSelect.getSelectedItem(); //selected table
           String action = (String) actionSelect.getSelectedItem(); //selected action

            //behavior if a table is selected
            if(e.getSource() == tableSelect && tableSelect.getSelectedIndex() != 0){
                tableSelect.setEnabled(false); //don't allow table to be changed in the middle of transaction
                //instantiate object based on selected table
                if(table.equals("Medical Center")){
                    hr = new MedicalCenter();
                }else if(table.equals("Water")){
                    hr = new Water();
                }else{
                    hr = new Food();
                }

                availHRID = dao.getUsedHRID(hr); //retrieve IDs that are currently in the database

                if(action.equals("Insert")){
                    commonFieldData[8].setText(Integer.toString(dao.getNextHRID()));
                    addTextFields(tableSelect.getSelectedIndex()-1, action); //add the fields of the table to the window
                    commonFieldData[5].setText(table); //set hr type field to table selected
                }else if(!availHRID.isEmpty()){ //action is not insert and the table is not empty
                    HRIDInput = new JComboBox<>(availHRID.toArray());
                    HRIDIn.add(HRIDInput);
                    HRIDIn.add(sub);
                    HRIDIn.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(HumResourceGUI.this, "The selected table is empty.\nProgram will return to initial state.");
                    reset();
                }
            //behavior for if an action is selected
            }else if(e.getSource() == actionSelect && actionSelect.getSelectedIndex() != 0) {
                actionSelect.setEnabled(false); //don't allow to change action in middle of transaction
                tableSelect.setVisible(true); //ask for type of humanitarian resource
            //behavior for if submit button is pressed
            }else if(e.getSource() == sub) {
                hr.setHRID((int) HRIDInput.getSelectedItem());
                HRIDInput.setEnabled(false); //don't allow user to change HRID in the middle of transaction

                if (!action.equals("Insert")) {
                    sub.setEnabled(false);
                    dao.populateData(hr); //retrieve data from table and set variables
                    addTextFields(tableSelect.getSelectedIndex() - 1, action); //add the fields of the table to the window

                    //set the boxes to their current values
                    populateCommonTextFields();
                    populateSubFieldData(tableSelect.getSelectedIndex() - 1);
                }

                //desired action determines button text
                if (action.equals("Delete")) {
                    finish.setText("Confirm");
                } else {
                    finish.setText("Finish");
                }

                //behavior for if finish/submit button is pressed
            }else if(e.getSource() == finish) { //source is finish/confirm button
                if (!action.equals("Delete") && table.equals("Food") && fTypeFieldData.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(HumResourceGUI.this, "You must select a food type before submitting.");
                } else {
                    setHRCommonData();
                    setSubFieldData(tableSelect.getSelectedIndex() - 1);

                    int rows = 0; //number of rows affected
                    if (action.equals("Update")) rows = dao.update(hr);
                    if (action.equals("Insert")) rows = dao.insert(hr);
                    if (action.equals("Delete")) rows = dao.delete(hr);

                    if (rows == 2) { //rows affected should be 2 if successful
                        JOptionPane.showMessageDialog(HumResourceGUI.this, action + " successful.\nProgram will return to initial state.");
                    } else {
                        JOptionPane.showMessageDialog(HumResourceGUI.this, action + " failed.\nProgram will return to initial state.");
                    }
                    reset();
                }
                //behavior for if cancel button is pressed
            } else if(e.getSource() == cancel){ //source is cancel button
                JOptionPane.showMessageDialog(HumResourceGUI.this, "Transaction cancelled.\nProgram will return to initial state.");
                reset();
            }
        } // end - method ActionPerformed

        /**
         * Set the GUI text boxes to their actual values from the HumResource object
         */
        private void populateCommonTextFields(){
            commonFieldData[0].setText(hr.getHRName());
            commonFieldData[1].setText(hr.getHRAddress());
            commonFieldData[2].setText(hr.getHRPhoneNumber());
            commonFieldData[3].setText(Double.toString(hr.getLatitude()));
            commonFieldData[4].setText(Double.toString(hr.getLongitude()));
            commonFieldData[5].setText(hr.getHRType());
            commonFieldData[6].setText(hr.getHRDesc());
            commonFieldData[7].setText(hr.getHROpenHours());
            commonFieldData[8].setText(Integer.toString(hr.getHRID()));
        }

        /**
         * Set the values in the HumResource object to the values in the GUI data fields
         */
        private void setHRCommonData() {
            hr.setHRName(commonFieldData[0].getText());
            hr.setHRAddress(commonFieldData[1].getText());
            hr.setHRPhoneNumber(commonFieldData[2].getText());
            try {
                hr.setLatitude(Double.parseDouble(commonFieldData[3].getText()));
            } catch (NumberFormatException nfe) {
                System.out.println("not a number, ignoring input in latitude field.");
            }
            try {
                hr.setLongitude(Double.parseDouble(commonFieldData[4].getText()));
            } catch (NumberFormatException nfe) {
                System.out.println("not a number, ignoring input in longitude field.");
            }
            hr.setHRType(commonFieldData[5].getText());
            hr.setHRDesc(commonFieldData[6].getText());
            hr.setHROpenHours(commonFieldData[7].getText());
            try {
                int id = Integer.parseInt(commonFieldData[8].getText());
                hr.setHRID(id);

            }catch(NumberFormatException nfe) {
                System.out.println("not a number, ignoring input in hrid field");
            }
        }

        /**
         * Set the GUI text boxes to their actual values from the Food object
         */
        private void populateFoodFields(){
            String ftype = ((Food)hr).getfType(); //type of food
            if(ftype.equals("Grocery")){
                fTypeFieldData.setSelectedIndex(1);
            }else if(ftype.equals("Distributor")){
                fTypeFieldData.setSelectedIndex(2);
            }else{
                fTypeFieldData.setSelectedIndex(3);
            }
            foodFieldsData[1].setText(Integer.toString(((Food)hr).getfMealsAvail()));
            foodFieldsData[2].setText(((Food)hr).getfSpecificDesc());
        }

        /**
         * Set the values in the Food object to the values in the GUI data fields
         */
        private void setFoodData(){
            ((Food)hr).setfType((String) fTypeFieldData.getSelectedItem());
            try {
                ((Food) hr).setfMealsAvail(NumberFormat.getNumberInstance(Locale.US).parse(foodFieldsData[1].getText()).intValue());
            }catch(ParseException pe){
                System.out.println("parse exception encountered in method setFoodData");
            }
            ((Food)hr).setfSpecificDesc(foodFieldsData[2].getText());
        }

        /**
         * Set the GUI text boxes to their actual values from the MedicalCenter object
         */
        private void populateMCFields(){
            mcFieldsData[0].setText(Integer.toString(((MedicalCenter)hr).getNumBeds()));
            mcFieldsData[1].setText(Integer.toString(((MedicalCenter)hr).getEmergencyRoomCap()));
            mcFieldsData[2].setText(Integer.toString(((MedicalCenter)hr).getNumDoctors()));
            mcFieldsData[3].setText(Integer.toString(((MedicalCenter)hr).getNumNurses()));
        }

        /**
         * Set the values in the MedicalCenter object to the values in the GUI data fields
         */
        private void setMCData() {
            try {
                if (mcFieldsData[0].getValue() != null) {
                    ((MedicalCenter) hr).setNumBeds(NumberFormat.getNumberInstance(Locale.US).parse(mcFieldsData[0].getText()).intValue());
                }
                if (mcFieldsData[1].getValue() != null) {
                    ((MedicalCenter) hr).setEmergencyRoomCap(NumberFormat.getNumberInstance(Locale.US).parse(mcFieldsData[1].getText()).intValue());
                }
                if (mcFieldsData[2].getValue() != null) {
                    ((MedicalCenter) hr).setNumDoctors(NumberFormat.getNumberInstance(Locale.US).parse(mcFieldsData[2].getText()).intValue());
                }
                if (mcFieldsData[3].getValue() != null) {
                    ((MedicalCenter) hr).setNumNurses(NumberFormat.getNumberInstance(Locale.US).parse(mcFieldsData[3].getText()).intValue());
                }

            }catch(ParseException pe) {
                System.out.println("parse exception encountered in method setMCData");
            }
        }

        /**
         * Set the GUI text boxes to their actual values from the Water object
         */
        private void populateWaterFields(){
            waterFieldsData[0].setText(Integer.toString(((Water)hr).getNum10ozBottle()));
            waterFieldsData[1].setText(Integer.toString(((Water)hr).getNumHalfLiterBottle()));
            waterFieldsData[2].setText(Integer.toString(((Water)hr).getNum5GalJug()));
        }

        /**
         * Set the values in the Water object to the values in the GUI data fields
         */
        private void setWaterData(){
            try {
                if(waterFieldsData[0].getValue() != null) {
                    ((Water) hr).setNum10ozBottle(NumberFormat.getNumberInstance().parse(waterFieldsData[0].getText()).intValue());
                }
                if(waterFieldsData[1].getValue() != null) {
                    ((Water) hr).setNumHalfLiterBottle(NumberFormat.getNumberInstance().parse(waterFieldsData[1].getText()).intValue());
                }
                if(waterFieldsData[2].getValue() != null) {
                    ((Water) hr).setNum5GalJug(NumberFormat.getNumberInstance().parse(waterFieldsData[2].getText()).intValue());
                }
            }catch (ParseException pe){
                System.out.println("parse exception encountered in method setWaterData");;
            }
        }

        /**
         * Set the values in the respective object to the values in the GUI data fields.
         * @param index Determines which object to set the values of.
         */
        private void setSubFieldData(int index){
            if(index == 0){
                setMCData();
            }else if(index == 1){
                setWaterData();
            }else{
                setFoodData();
            }
        }

        /**
         * Set the GUI text boxes to their actual values from the respective object.
         * @param index Determines from which object to populate GUI fields.
         */
        private void populateSubFieldData(int index){
            if (index == 0) {
                populateMCFields();
            } else if (index == 1) {
                populateWaterFields();
            } else {
                populateFoodFields();
            }
        }
    } //end - class Listener
} //end - class HumResourceGUI