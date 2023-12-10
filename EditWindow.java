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
 * Class used to edit Task data to the ToDoList
 */
public class EditWindow extends JFrame implements ActionListener{

    private static JTextField taskNameDisplay;
    private static JComboBox rankCombo;
    private static JComboBox subjectCombo;
    private static JTextField noteDisplay;
    private static JButton save;
    private static JButton cancel;



    private static final String[] rankList = {"Iron", "Bronze", "Silver", "Gold", "Platium", "Emerald", "Diamond", "Master", "Grandmaster", "Challenger"};
    private static final String[] subjectList = {"Math", "English", "Science", "Music", "Tech", "Other"};


    EditWindow()
    {
        this.setLayout(new BorderLayout());
        this.setTitle("Edit Task");
        this.setSize(350,220);
        this.setResizable(false);

        JLabel taskName = new JLabel("Task Name: ");
        JLabel rank = new JLabel("rank: ");
        JLabel subject = new JLabel("subject: ");
        JLabel note = new JLabel("Note: ");

        taskNameDisplay  = new JTextField();
        rankCombo 	 = new JComboBox(rankList);
        subjectCombo  	 = new JComboBox(subjectList);
        noteDisplay  	 = new JTextField();

        JPanel mainPanel = new JPanel(new GridLayout(7, 2));                //Create a panel with a grid layout
        mainPanel.add(taskName);	mainPanel.add(taskNameDisplay);
        mainPanel.add(rank);	mainPanel.add(rankCombo);
        mainPanel.add(subject);	mainPanel.add(subjectCombo);
        mainPanel.add(note);		mainPanel.add(noteDisplay);
        this.getContentPane().add(BorderLayout.CENTER, mainPanel);	//Add the panel to the CENTER of the BorderLayout


        save = new JButton("Save");
        cancel = new JButton("Cancel");
        save.addActionListener(this);
        cancel.addActionListener(this);

        JPanel lowerPanel = new JPanel(new FlowLayout());
        lowerPanel.add(save);
        lowerPanel.add(cancel);
        this.getContentPane().add(BorderLayout.SOUTH, lowerPanel);	//Add the panel to the SOUTH of the BorderLayout

        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                ToDoList.listenerOn = true;
            }
        });


        validate();
    }
    public static void populate()
    {
        taskNameDisplay.setText(ToDoList.myTasks.get(ToDoList.currentEditIndex).getName());
        rankCombo.setSelectedItem(ToDoList.myTasks.get(ToDoList.currentEditIndex).getrank());
        subjectCombo.setSelectedItem(ToDoList.myTasks.get(ToDoList.currentEditIndex).getsubject());
        noteDisplay.setText(ToDoList.myTasks.get(ToDoList.currentEditIndex).getNote());
    }
    /**
     * Method to track actions performed
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == save)
        {
            if(taskNameDisplay.getText().isEmpty() || noteDisplay.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Please fill in all of the fields.");
            }
            else if(taskNameDisplay.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please fill in a name in the name field.");
            }
            else
            {
                ToDoList.myTasks.get(ToDoList.currentEditIndex).setName(taskNameDisplay.getText());
                ToDoList.myTasks.get(ToDoList.currentEditIndex).setrank((String) rankCombo.getSelectedItem());
                ToDoList.myTasks.get(ToDoList.currentEditIndex).setsubject((String) subjectCombo.getSelectedItem());
                ToDoList.myTasks.get(ToDoList.currentEditIndex).setNote(noteDisplay.getText());

                ToDoList.listModel.remove(ToDoList.currentEditIndex);
                ToDoList.listModel.add(ToDoList.currentEditIndex, ToDoList.myTasks.get(ToDoList.currentEditIndex).getName());
                ToDoList.listenerOn = true;
                ToDoList.edit1.setVisible(false);
                ToDoList.changeOutputDetails(ToDoList.currentEditIndex);
                ToDoList.exported = false;
                JOptionPane.showMessageDialog(null, "Edit task Successful.");
            }
        }
        if(e.getSource() == cancel)
        {
            ToDoList.listenerOn = true;
            ToDoList.edit1.setVisible(false);
        }
    }
}
