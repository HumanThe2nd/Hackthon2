import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class used to add Task data to the ToDoList
 */
public class AddWindow extends JFrame implements ActionListener{

    private static JTextField taskNameDisplay;
    private static JComboBox rankCombo;
    private static JComboBox subjectCombo;
    private static JTextField noteDisplay;
    private static JButton add;
    private static JButton cancel;

    private static final String[] rankList = {"Iron", "Bronze", "Silver", "Gold", "Platium", "Emerald", "Diamond", "Master", "Grandmaster", "Challenger"};
    private static final String[] subjectList = {"Math", "English", "Science", "Music", "Tech", "Other"};
    AddWindow()
    {
        this.setLayout(new BorderLayout());
        this.setTitle("Add Task");
        this.setSize(350,220);
        this.setResizable(false);

        JLabel taskName = new JLabel("Task Name: ");
        JLabel rank = new JLabel("Rank: ");
        JLabel subject = new JLabel("Subject: ");
        JLabel note = new JLabel("Note: ");
        taskNameDisplay  = new JTextField("");
        rankCombo 	 = new JComboBox(rankList);
        subjectCombo  	 = new JComboBox(subjectList);
        noteDisplay  	 = new JTextField("");

        JPanel mainPanel = new JPanel(new GridLayout(7, 2));                //Create a panel with a grid layout
        mainPanel.add(taskName);
        mainPanel.add(taskNameDisplay);
        mainPanel.add(rank);
        mainPanel.add(rankCombo);
        mainPanel.add(subject);
        mainPanel.add(subjectCombo);
        mainPanel.add(note);
        mainPanel.add(noteDisplay);
        this.getContentPane().add(BorderLayout.CENTER, mainPanel);	//Add the panel to the CENTER of the BorderLayout


        add = new JButton("Add");
        cancel = new JButton("Cancel");
        add.addActionListener(this);
        cancel.addActionListener(this);

        JPanel lowerPanel = new JPanel(new FlowLayout());
        lowerPanel.add(add);
        lowerPanel.add(cancel);
        this.getContentPane().add(BorderLayout.SOUTH, lowerPanel);
        /*
         * Method used to clear the text fields when the window is closed
         */
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                clearText();
            }
        });

        clearText();
        validate();
    }
    /**
     * Method used to reset the window to its default state, empty text fields etc
     */
    public void clearText()
    {
        taskNameDisplay.setText("");
        rankCombo.setSelectedItem("Other");
        subjectCombo.setSelectedItem("Other");
        noteDisplay.setText("");
    }
    /**
     * Method used to track actions performed
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == add)
        {
            if(taskNameDisplay.getText().isEmpty() || noteDisplay.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please fill in all of the fields.");
            }
            else
            {
                ToDoList.myTasks.add(new Task(taskNameDisplay.getText(), (String) rankCombo.getSelectedItem(), (String) subjectCombo.getSelectedItem(), noteDisplay.getText()));
                ToDoList.listModel.addElement(ToDoList.myTasks.get(ToDoList.myTasks.size()-1).getName());
                clearText();
                ToDoList.add1.setVisible(false);
                ToDoList.exported = false;
                JOptionPane.showMessageDialog(null, "Quest Added.");
            }
        }
        if(e.getSource() == cancel)
        {
            clearText();
            ToDoList.add1.setVisible(false);
        }
    }

}
