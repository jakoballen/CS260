import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class JavaMap extends JFrame {
    private WebView mapBrowser;
    private WebEngine mapEngine;
    private JFXPanel browserPanel;
    private JavaScriptListener jsl;
    private JButton btnAddPoint;
    private JButton btnClearPoints;
    private JPanel btnPanel;
    private DataAccessObject dao;
    /* used to generate a new ID for each point added to map - returned to caller for user later. */
    private int newMapPointID;

    public JavaMap(int width, int height, String title){
        this.newMapPointID = 0;

        this.setSize(new Dimension(width, height));
        this.setTitle(title);
        /* close id handled below */
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseWindowListener());

        createContents();

        this.setVisible(true);

        JOptionPane.showConfirmDialog(this, String.format("Please wait while the map is loading.%nThis could take a few moments."), "Wait", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }

    private void createContents(){
        /* initialize DAO */
        dao = new DataAccessObject();
        dao.connect();

        /* create layout */
        this.setLayout(new BorderLayout());

        /* create panel and add it to the JFrame. */
        btnPanel = new JPanel();
        this.add(btnPanel, BorderLayout.SOUTH);

        /* initialize buttons */
        btnAddPoint = new JButton("Add Points from DB");
        btnClearPoints = new JButton("Remove Points");
        btnAddPoint.setEnabled(false);
        btnClearPoints.setEnabled(false);

        /* handle button clicks */
        btnAddPoint.addActionListener(new btnAddPointActionListener());
        btnClearPoints.addActionListener(new btnAddClearPointsListener());

        /* add buttons to the button panel */
        btnPanel.add(btnAddPoint);
        btnPanel.add(btnClearPoints);

        //create map/browser and panel - JFX panel required to run WebView in SWING */
        browserPanel = new JFXPanel();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mapBrowser = new WebView();
                browserPanel.setScene(new Scene(mapBrowser));

                mapEngine = mapBrowser.getEngine();
                /* get the path to the mapHTML file */
                String mapFile = getClass().getResource("mapHTML.html").toExternalForm();

                /* load the mapHTML page */
                mapEngine.load(mapFile);

                /* when the page finishes loading handler */
                mapEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                        if (newValue == Worker.State.SUCCEEDED) {
                            System.out.println("page loaded");
                            /* once the webpage loads, add the 'app' object to the window,
                              so we can call functions in java from JavaScript.
                             */

                            JSObject win = (JSObject) mapEngine.executeScript("window");
                            jsl = new JavaScriptListener();
                            win.setMember("app", jsl);

                            /* must run any SWING code on SWING thread */
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    /* put any code here to handle when the page finishes loading
                                    *  on the SWING thread.*/
                                    /* here, we enable the buttons when the page loads */
                                    btnAddPoint.setEnabled(true);
                                    btnClearPoints.setEnabled(true);
                                }
                            });
                        }
                    }
                });

            }
        });

        /* add browser panel to this frame */
        this.add(browserPanel, BorderLayout.CENTER);
    }

    private void closeWindow() {
        this.setVisible(false);
        this.dispose();
    }

    private class CloseWindowListener implements WindowListener {
        private boolean windowOpen;

        @Override
        public void windowOpened(WindowEvent e) {
            //window opened
            windowOpen = true;
        }

        @Override
        public void windowClosing(WindowEvent e) {
            System.out.println("Window closing.");
            if (windowOpen) {
                dao.disconnect();

                windowOpen = false;
                closeWindow();
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {
            System.out.println("Window closed.");
        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }


    /* handles when the add points button is pressed */
    private class btnAddPointActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // 1. TODO: add a line to use the existing variable dao to execute an SQL query that retrieves all fields in all rows of the HumResource table
            dao.executeSQLQuery("SELECT * FROM HUMRESOURCE");
            // 2. TODO: again using the dao variable, call the processResultSetHRObjects method and assign the return value to the variable declared below
            //        NOTE: since the return type of that method returns a list, you'll have to do a type cast of your return value to (ArrayList<HumResource>)

            ArrayList<HumResource> hrList =(ArrayList<HumResource>) dao.processResultSetHRObjects();

            // 3. TODO: write a for loop that adds a marker to the map for each HumResource object in the HRList variable
            //        NOTE: to add one point, you could use the HumResource object setters to do:
            //              addMapPoint(hr.getHrLatitude(), hr.getHrLongitude(), hr.getHrName() + ", " + hr.getHrDesc(), "marker.png");
            for(HumResource hr : hrList){
                addMapPoint(hr.getHrLatitude(), hr.getHrLongitude(), hr.getHrName() + ", " + hr.getHrDesc(), "marker.png");
            }
        }
    }

    /* handles when the clear points button is pressed */
    private class btnAddClearPointsListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            clearMapPoints();
        }
    }

    /* class must be public for this to work */
    public class JavaScriptListener {
        public void setSelectedItem(int id) {
            System.out.println("JavaScript selectedID: " + id);

            /* item on map was selected, place your code to handle that event here. */
        }
    }

    public void clearMapPoints(){
        newMapPointID = 0;
        eval("clearMapPoints();");

    }

    public int addMapPoint(double latitude, double longitude, String description, String pngIcon){
        eval("addMapPoint(" + newMapPointID + ", "
                                    + latitude + ", "
                                    + longitude + ", "
                                    + jsEncode(description) + ", "
                                    + jsEncode(pngIcon) + ");");
        /* return current ID and add 1 for next use */
        return newMapPointID++;
    }

    /* encodes a string for use in a javascript function */
    public String jsEncode(String jsString){
        jsString = jsString.replace("\'", "\"");
        return "'" + jsString + "'";
    }

    public void eval(String jsCode){
        /* javascript code must be run in JavaFX thread */
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mapEngine.executeScript(jsCode);
            }
        });
    }
}
