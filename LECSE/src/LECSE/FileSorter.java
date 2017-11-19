package LECSE;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.GridLayout;

public class FileSorter extends JPanel implements TreeSelectionListener {
	private JTree tree;

	public FileSorter(Database db) throws FileNotFoundException, IOException {
		super(new GridLayout(1,0));

		//Create the root node
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Lectures");
		//Pass the database and the root node to the createNodes method
		createNodes(top, db);

		//Create a tree that allows one selection at a time.
		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode
		(TreeSelectionModel.SINGLE_TREE_SELECTION);

		//Listen for when the selection changes.
		tree.addTreeSelectionListener(this);

		//Create the scroll pane and add the tree to it. 
		JScrollPane treeView = new JScrollPane(tree);
		
		//Add the scroll pane to the JPanel
		add(treeView);
	}

	/** Required by TreeSelectionListener interface. */
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				tree.getLastSelectedPathComponent();

		if (node == null) return;

		if (node.isLeaf()) {
			//Get filename
			String fileName = node + ".txt";
			System.out.println(fileName);
			//Display file in mediaGUI
		} 
	}

	private void createNodes(DefaultMutableTreeNode top, Database db) throws FileNotFoundException, IOException {

		String[][] ci = db.getCourseAndINstructor();

		if(ci[0][0] != null) {
			for(int i = 0; i < ci.length; i++) {
				if(ci[i][1] != null) {
					//Sort by course
					if(db.getSortSelection().equals("Course")) {
						DefaultMutableTreeNode course = new DefaultMutableTreeNode(ci[i][0]);
						top.add(course);

						ArrayList<Lecture> lectures = db.getCourseArrayList(ci[i][0]);
						for(int k = 0; k < lectures.size(); k++) {
							DefaultMutableTreeNode lecture = new DefaultMutableTreeNode(lectures.get(k).getFileName());
							course.add(lecture);			
						}
						//Sort by instructor
					}else if(db.getSortSelection().equals("Instructor")) {
						DefaultMutableTreeNode instructor = new DefaultMutableTreeNode(ci[i][1]);
						top.add(instructor);

						ArrayList<Lecture> lectures = db.getInstructorArrayList(ci[i][1]);
						for(int k = 0; k < lectures.size(); k++) {
							DefaultMutableTreeNode lecture = new DefaultMutableTreeNode(lectures.get(k).getFileName());
							instructor.add(lecture);			
						}
					}
				}
			}
		}
	}
}