import java.awt.BorderLayout;
import java.lang.Math;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
public class ToDoList extends JFrame implements ActionListener {

    protected static JList list;
    protected static DefaultListModel listModel;
    private static JScrollPane scrollPane;

    private static JPanel rightPanel;
    private static JPanel lowerPanel;
    private static JPanel middlePanel;

    //Used for task info display
    private static JLabel taskName;
    private static JLabel rank;
    private static JLabel subject;
    private static JLabel note;
    private static JLabel User;
    private static JLabel UserRank;
    private static JLabel Xp;
    private static JTextField taskNameDisplay;
    private static JTextField rankDisplay;
    private static JTextField subjectDisplay;
    private static JTextField noteDisplay;
    private static JTextField UserDisplay;
    private static JTextField UserRankDisplay;
    private static JTextField XpDisplay;

    //Buttons
    private static JButton add;
    private static JButton edit;

    //Menu bar
    private static JMenuBar menubar1;
    private static JMenu fileMenu;
    private static JMenuItem importItem;
    private static JMenuItem exportItem;
    private static JMenu editMenu;
    private static JMenuItem addItem;
    private static JMenuItem delItem;
    private static JMenuItem editSelItem;
    private static JMenuItem Complete;

    //used for the management of exporting tasks
    private static String exportText;
    protected static boolean exported = true;				//true = data has been saved (tasks do not to be saved on program close)
    protected static int currentEditIndex = 0;
    protected static boolean listenerOn = true;				//used to disable the JListListener when modifying it to prevent errors

    protected static AddWindow add1 = new AddWindow();
    protected static EditWindow edit1 = new EditWindow();

    protected static ArrayList<Task> myTasks = new ArrayList<Task>();	//array used to store task Objects from the Task class

    public ToDoList() throws IOException {
        this.setLayout(new BorderLayout());
        this.setTitle("League of Homework");
        this.setSize(640,260);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);	//Makes the program terminate properly when the close button is pressed

        //adding the Menu bar to the window and to the action listener
        menubar1 = new JMenuBar();						// Makes a new menu bar (now you can add items to this menu...)
        this.setJMenuBar(menubar1); 					// Attaches the named menubar (menubar1) to the window

        fileMenu = new JMenu("File"); 					//Makes a menubar button
        menubar1.add(fileMenu); 						//add the menubar button to the menu bar
        importItem = new JMenuItem("Import Quests");			//creates drop down buttons for the menubar button
        exportItem = new JMenuItem("Export Quests");
        fileMenu.add(importItem);
        fileMenu.add(exportItem);
        importItem.addActionListener(this);
        exportItem.addActionListener(this);

        editMenu = new JMenu("Edit");
        menubar1.add(editMenu);
        addItem = new JMenuItem("Add");
        delItem = new JMenuItem("Delete");
        editSelItem = new JMenuItem("Edit Selected");
        Complete = new JMenuItem("Complete Selected");

        editMenu.add(addItem);
        editMenu.add(delItem);
        editMenu.add(editSelItem);
        editMenu.add(Complete);
        addItem.addActionListener(this);
        delItem.addActionListener(this);
        editSelItem.addActionListener(this);
        Complete.addActionListener(this);

        //adding the buttons and labels to the window and the ActionListener
        taskName = new JLabel("Quest Name: ");
        rank = new JLabel("rank: ");
        subject = new JLabel("subject: ");
        note = new JLabel("Note: ");
        User = new JLabel("User: "+ get_user());
        UserRank = new JLabel("User Rank: " + get_rank());
        Xp = new JLabel("Xp: " + get_xp());
        User.setHorizontalAlignment(JTextField.CENTER);
        UserRank.setHorizontalAlignment(JTextField.CENTER);
        Xp.setHorizontalAlignment(JTextField.CENTER);
        taskName.setHorizontalAlignment(JTextField.CENTER);
        rank.setHorizontalAlignment(JTextField.CENTER);
        subject.setHorizontalAlignment(JTextField.CENTER);
        note.setHorizontalAlignment(JTextField.CENTER);

        //make display fields noneditable and white in colour
        taskNameDisplay  = new JTextField();
        taskNameDisplay.setEditable(false);
        taskNameDisplay.setBackground(Color.white);
        rankDisplay  = new JTextField();
        rankDisplay.setEditable(false);
        rankDisplay.setBackground(Color.white);
        subjectDisplay  = new JTextField();
        subjectDisplay.setEditable(false);
        subjectDisplay.setBackground(Color.white);
        noteDisplay  	 = new JTextField();
        noteDisplay.setEditable(false);
        noteDisplay.setBackground(Color.white);
        UserDisplay  = new JTextField();
        UserDisplay.setEditable(false);
        UserDisplay.setBackground(Color.white);
        UserRankDisplay  = new JTextField();
        UserRankDisplay.setEditable(false);
        UserRankDisplay.setBackground(Color.white);
        XpDisplay  = new JTextField();
        XpDisplay.setEditable(false);
        XpDisplay.setBackground(Color.white);


        //Create a panel with a grid layout and add Labels etc
        rightPanel = new JPanel(new GridLayout(7, 2));
        rightPanel.add(taskName);	rightPanel.add(taskNameDisplay);
        rightPanel.add(rank);	rightPanel.add(rankDisplay);
        rightPanel.add(subject);	rightPanel.add(subjectDisplay);
        rightPanel.add(note);		rightPanel.add(noteDisplay);
        rightPanel.add(User);		rightPanel.add(UserDisplay);
        rightPanel.add(UserRank);		rightPanel.add(UserRankDisplay);
        rightPanel.add(Xp);		rightPanel.add(XpDisplay);
        UserDisplay.setText(get_user());
        UserRankDisplay.setText(get_rank());
        Xp.setText(get_xp());

        //set up JList and add it to a scrollPane
        list = new JList();
        listModel = new DefaultListModel();
        list.setModel(listModel);
        list.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 			//Make sure only one item can be selected on the tree
        scrollPane = new JScrollPane(list);
        middlePanel = new JPanel(new GridLayout(1, 2));				//Create a panel with a grid layout
        middlePanel.add(scrollPane);
        middlePanel.add(rightPanel);
        this.getContentPane().add(BorderLayout.CENTER, middlePanel);	//Add the panel to the EAST of the BorderLayout

        //import Tasks on startup
        try
        {
            importTasks();
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Error, task file could not be exported.");
        }

        listListener myListListener = new listListener();
        list.addListSelectionListener(myListListener);
        list.setSelectedIndex(0);

        //export tasks on close
        this.addWindowListener(new WindowAdapter()
        {
            /**
             * Method used to export tasks when the program is closed
             *
             * @ param exported boolean used to show whether the tasks have been saved of not
             */
            public void windowClosing(WindowEvent we)
            {
                if(!exported)	//if tasks are not saved already
                {
                    exportTasks();
                }
            }
        });

        validate();

    }

    // for data
    // line 1: name
    // line 2: xp
    // line 3: rank
    /**
     * Posts a quest with a given number, points, and description.
     *
     * @param number the quest number
     * @param points the number of points for the quest
     * @param desc the description of the quest
     * @throws IOException if an I/O error occurs writing to the file
     */
    static void post_quest(int number, int points, String desc) throws IOException {
        FileWriter myWriter = new FileWriter("quests.txt");
        myWriter.write(number + " " + points + "\n" + desc + "\n");
        myWriter.close();
        System.out.println("quest posted");
    }
    /**
     * Reads the second line from a file named "data.txt" and returns it as an integer.
     *
     * @return the xp as an integer
     * @throws IOException if an I/O error occurs reading from the file
     */
    static String get_xp() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
        reader.readLine();
        String secondLine = reader.readLine();
        reader.close();
        int xp =  Integer.parseInt(secondLine);
        return Integer.toString(xp);
    }
    /**
     * Reads the first line from a file named "data.txt" and returns it as a string.
     *
     * @return the name as a string
     * @throws IOException if an I/O error occurs reading from the file
     */
    static String get_user() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
        String name = reader.readLine();
        reader.close();
        return name;
    }
    /**
     * Reads the third line from a file named "data.txt" and returns the corresponding rank as a string.
     *
     * @return the rank as a string
     * @throws IOException if an I/O error occurs reading from the file
     */
    static String get_rank() throws IOException {
        String[] Ranks= {"Iron","Bronze","Silver","Gold","Platinum","Emerald","Diamond","Master","Grandmaster","Challenger"};
        BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
        reader.readLine(); reader.readLine();
        String line3 = reader.readLine();
        reader.close();
        int rank =  Integer.parseInt(line3);
        return Ranks[rank];
    }

    /**
     * Adds a specified number of experience points to the current total in "data.txt".
     *
     * The file "data.txt" is expected to have the following format:
     * Line 1: name
     * Line 2: current xp
     * Line 3: rank
     *
     * After this method is called, the xp on line 2 will be increased by the specified number.
     *
     * @param num the number of experience points to add
     * @throws IOException if an I/O error occurs reading from or writing to the file
     */
    static void add_xp(int num) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
        String name = reader.readLine();
        int xp = num+Integer.parseInt(reader.readLine());
        int rank = (int) Math.sqrt(xp);
        reader.close();
        FileWriter myWriter = new FileWriter("data.txt");
        myWriter.write(name + "\n" + xp + "\n" + rank);
        myWriter.close();
    }
    /**
     * Reads the first three lines from a file named "data.txt" and returns them as a formatted string.
     *
     * The file "data.txt" is expected to have the following format:
     * Line 1: name
     * Line 2: xp
     * Line 3: rank
     *
     * @return a string in the format "User: [name]\nExperience: [xp]\nRank: [rank]"
     * @throws IOException if an I/O error occurs reading from the file
     */
    static String get_data() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
        String name = reader.readLine();
        String xp = reader.readLine();
        String rank = reader.readLine();
        reader.close();
        return "User: " + name + "\n" + "Experience: " + xp + "\n" + "Rank: "+ rank;
    }
    private class listListener implements ListSelectionListener{
        /**
         * Changes the task details display to display the details of the current selected task
         * @param listenerOn used to turn on and off the JList listener when modifiying the list
         */
        public void valueChanged(ListSelectionEvent e) {
            if(listenerOn)
            {
                changeOutputDetails(list.getSelectedIndex());
            }

        }
    }
    /**
     * Method used to update the display to show the task details of the
     * selected task in the JList display
     * @param index the index of the selected item on the JList (correlates with the same task index in the array)
     */
    public static void changeOutputDetails(int index)
    {
        //update task output details from array data
        taskNameDisplay.setText(myTasks.get(index).getName());
        rankDisplay.setText(""+myTasks.get(index).getrank());
        subjectDisplay.setText(myTasks.get(index).getsubject());
        noteDisplay.setText(myTasks.get(index).getNote());
    }
    public void edit()
    {
        if(list.getSelectedIndex() < 0)	//if there is no task selected, notify the user
        {
            JOptionPane.showMessageDialog(null, "Error, no task selected.");
        }
        else
        {
            currentEditIndex = list.getSelectedIndex();	//store the current selected task index
            EditWindow.populate();						//populate EditWindow with task data
            listenerOn = false;							//Disable the listListener while editing the list
            edit1.setVisible(true);
        }
    }

    public void importTasks()
    {
        try
        {
            File f = new File("C:\\ToDoList\\ToDoListTasks.txt");
            Scanner textFile;
            try {
                textFile = new Scanner(f);
                while(textFile.hasNextLine())
                {
                    String tString = textFile.nextLine();
                    String [] str = tString.split("�");		//use "�" to differentiate between each block of information and store it in a new array

                    Task t = new Task();					//create a new task
                    //Add task details from string
                    t.setName(str[0]);
                    t.setrank(str[1]);
                    t.setsubject(str[2]);
                    t.setNote(str[3]);

                    myTasks.add(t);			//add the task to the array list
                    listModel.addElement(myTasks.get(myTasks.size()-1).getName());	//add the task to the JList display
                }
            }
            catch (FileNotFoundException e)//if the tasks text file cannot be found, notify the user
            {
                JOptionPane.showMessageDialog(null, "Error, the following file could not be found or is corrupt:" + "\n" + "'C:\\ToDoList\\ToDoListTasks.txt'" + "\n" + "Please create this file to export task list data.");
            }
        }
        catch(NumberFormatException z)//if the task file is not formatted properly notify the user
        {
            JOptionPane.showMessageDialog(null, "Error, Tasks may not have been imported successfully due to file corruption");
        }
    }
    /**
     * Method used to export the tasks into a text file
     */
    public void exportTasks()
    {
        try
        {
            PrintWriter p = new PrintWriter("C:\\ToDoList\\ToDoListTasks.txt");		//create text file

            exportText = "";										//empty the string for new input
            int counter = 0;										//counter used to count how many tasks have been exported
            for(int i = 0; i < myTasks.size(); i++)					//loop through the array
            {
                Task t = new Task();								//create a new task
                t = myTasks.get(i);									//copy the task details from the array at index 'i' into the new array 't'
                exportText = exportText + t.toStringExport();		//extract the task data and store it in exportText
                p.println(exportText);								//add task data to the text file
                exportText = "";									//empty the string for new input
                counter++;											//increment count to show a task has been exported
            }

            p.close();												//close the printWriter
            exported = true;										//set exported to true to show tasks have been saved
            JOptionPane.showMessageDialog(null, counter + " tasks exported.");

        }
        catch (FileNotFoundException e)//if the file cannot be found or created, notify the user
        {
            JOptionPane.showMessageDialog(null, "Error, the following file could not be created:" + "\n" + "'C:\\ToDoList\\ToDoListTasks.txt'" + "\n" + "Please create this file to export task list data.");
        }
    }
    /**
     * Method used to track actions performed
     */
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == addItem)
        {
            add1.setVisible(true);	//open the add window
        }
        if(e.getSource() == add)
        {
            add1.setVisible(true);	//open the add window
        }
        if(e.getSource() == Complete)
        {
            if(list.getSelectedIndex() < 0)	//if there is no quests selected, notify the user)
            {
                JOptionPane.showMessageDialog(null, "Error, no quest selected.");
            }
            else
            {
                listenerOn = false;	//disable the listListener while editing the list
                myTasks.remove(list.getSelectedIndex());	//remove the selected task from the array
                listModel.remove(list.getSelectedIndex());	//remove the selected task from the JList display
                listenerOn = true;
                exported = false; //set exported to false as data has been changed
                JOptionPane.showMessageDialog(null, "Quest completed.");
                taskNameDisplay.setText("");
                rankDisplay.setText("");
                subjectDisplay.setText("");
                noteDisplay.setText("");
                String[] Ranks= {"Iron","Bronze","Silver","Gold","Platinum","Emerald","Diamond","Master","Grandmaster","Challenger"};
                for(int i=0;i<Ranks.length;i++){
                    if(rank.equals(Ranks[i])){
                        try {
                            add_xp(i+1);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
            if(e.getSource() == delItem)
            {
                if(list.getSelectedIndex() < 0)	//if not item has been selected on the JList, notify the user
                {
                    JOptionPane.showMessageDialog(null, "Error, no quest selected.");
                }
                else
                {
                    listenerOn = false;										//disable the JList Listener while editing
                    myTasks.remove(list.getSelectedIndex());				//remove the task from the array
                    listModel.remove(list.getSelectedIndex());				//remove the task from the JList
                    listenerOn = true;										//enable the JList listener
                    exported = false;										//set exported to false as data has been changed
                    JOptionPane.showMessageDialog(null, "Quest deleted.");
                    //clear Task display text
                    taskNameDisplay.setText("");
                    rankDisplay.setText("");
                    subjectDisplay.setText("");
                    noteDisplay.setText("");
                }
            }
            if(e.getSource() == edit||e.getSource() == editSelItem)
            {
                edit();
            }

            if(e.getSource() == importItem)
            {
                importTasks();
                exported = false; 	//set exported to false as data has been changed

            }
            if(e.getSource() == exportItem)
            {
                exportTasks();

            }
        }
    }
}
