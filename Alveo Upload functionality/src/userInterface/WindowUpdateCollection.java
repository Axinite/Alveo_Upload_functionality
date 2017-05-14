package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import net.sf.json.JSONObject;
import upload.UploadConstants;

/** Window for creating new collection
 * 
 *
 * @author Simon Lacis
 *
 */

public class WindowUpdateCollection {

	JFrame frame;
	HashMap<String, JSONObject> recItemMetadata = new HashMap<String,JSONObject>();
	HashMap<String, HashMap<String, JSONObject>> recDocMetadata = new HashMap<String,HashMap<String,JSONObject>>();
	private String path = null;
	private String absolupath;
	private String filename;
	private JTextField textField_1;
	private Boolean collectionMD, itemMD, newItem;


	/**
	 * Create the application.
	 * @param key 
	 */
	public WindowUpdateCollection(String key) {		
		initialize(key);
//		getItemMeta("uploadertest2", key);
	}

	/**
	 * Initialize the contents of the frame.
	 * @param key 
	 */
	private void initialize(String key) {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);

		JTextField Filechooser = new JTextField();
		Filechooser.setBounds(166, 199, 200, 30);
		frame.getContentPane().add(Filechooser);
		Filechooser.setColumns(10);
		JButton btnAdd = new JButton("Directory");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				final JFileChooser selection = new JFileChooser();
				// Allow for selection of individual file or directory
				selection.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				File file;

				int returnVal = selection.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = selection.getSelectedFile();
					if ((file.getName() != null) && (file.getName().length() > 0)) { 
						int dot = file.getName().lastIndexOf('.'); 
						if ((dot >-1) && (dot < (file.getName().length()))) { 
							filename = file.getName().substring(0, dot); 
						} 
					} 

					path = file.getPath();
					absolupath = file.getAbsolutePath();
					System.out.println(absolupath);
					absolupath = absolupath.substring(0,absolupath.lastIndexOf(File.separator));
					Filechooser.setText(path);
				} else {
					System.out.println("Open command cancelled by user.");
				}
			}
		});
		btnAdd.setBounds(40, 202, 114, 23);
		frame.getContentPane().add(btnAdd);

		JRadioButton addnewYes, addnewNo;
		addnewYes  = new JRadioButton("Yes");
		addnewYes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(addnewYes.isSelected()) {
					Filechooser.setVisible(true);
					btnAdd.setVisible(true);
					newItem = true;
				}

			}
		});

		addnewNo  = new JRadioButton("No");
		addnewNo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(addnewNo.isSelected()) {
					Filechooser.setVisible(false);
					btnAdd.setVisible(false);
					newItem = false;
				}

			}
		});
		addnewNo.setSelected(true);
		ButtonGroup operation = new ButtonGroup();
		operation.add(addnewNo);
		operation.add(addnewYes);

		JPanel operPanel = new JPanel();
		Border operBorder = BorderFactory.createTitledBorder("Add New Items/Documents");
		operPanel.setBorder(operBorder);
		operPanel.add(addnewNo);
		operPanel.add(addnewYes);
		operPanel.setBounds(102, 143, 223, 47);



		frame.getContentPane().add(operPanel);

		JButton btnCreateNewCollection = new JButton("Generate Metadata");
		btnCreateNewCollection.setBounds(64, 302, 262, 36);
		btnCreateNewCollection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (path == null && newItem == true){
				//Error message : Null Path 
				JOptionPane.showMessageDialog(null, "Please select path", "InfoBox: " + "Error Message", JOptionPane.INFORMATION_MESSAGE);
				} else {
				HashMap<String, String> collectionDetails = new HashMap<String,String>();
						collectionDetails.put("collectionName",textField_1.getText());
				MetadataBuilder builder = new MetadataBuilder(path, collectionDetails, key, newItem, itemMD, collectionMD, false);
				builder.frame.setVisible(true);

				}
	
			}
		});
		frame.getContentPane().add(btnCreateNewCollection);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(180, 24, 186, 29);
		frame.getContentPane().add(textField_1);

		JLabel lblCollectionName = new JLabel("Collection Name");
		lblCollectionName.setBounds(41, 38, 130, 15);
		frame.getContentPane().add(lblCollectionName);




		JPanel collectionMDPanel = new JPanel();
		Border collectionBorder = BorderFactory.createTitledBorder("Update Collection MD");
		collectionMDPanel.setBorder(collectionBorder);
		//		collectionMDPanel.setBorder(null);
		collectionMDPanel.setBounds(9, 70, 177, 38);
		frame.getContentPane().add(collectionMDPanel);

		JRadioButton radioButtonYes = new JRadioButton("Yes");
		radioButtonYes.setSelected(true);
		radioButtonYes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(radioButtonYes.isSelected()) {
					collectionMD = true;
				}

			}
		});
		collectionMDPanel.add(radioButtonYes);

		

		JRadioButton radioButtonNo = new JRadioButton("No");
		radioButtonNo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(radioButtonNo.isSelected()) {
					collectionMD = false;
				}

			}
		});
		collectionMDPanel.add(radioButtonNo);
		ButtonGroup collMDoper = new ButtonGroup();
		collMDoper.add(radioButtonYes);
		collMDoper.add(radioButtonNo);

		JPanel itemMDPanel = new JPanel();
		Border itemBorder = BorderFactory.createTitledBorder("Update Item MD");
		itemMDPanel.setBorder(itemBorder);
		itemMDPanel.setBounds(202, 70, 173, 38);
		frame.getContentPane().add(itemMDPanel);

		JRadioButton radioButtonYesItem = new JRadioButton("Yes");
		radioButtonYesItem.setSelected(true);
		radioButtonYesItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(radioButtonYesItem.isSelected()) {
					itemMD = true;
				}

			}
		});
		itemMDPanel.add(radioButtonYesItem);

		JRadioButton radioButtonNoItem = new JRadioButton("No");
		radioButtonNoItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(radioButtonNoItem.isSelected()) {
					itemMD = false;
				}

			}
		});
		itemMDPanel.add(radioButtonNoItem);
		ButtonGroup itemMDoper = new ButtonGroup();
		itemMDoper.add(radioButtonYesItem);
		itemMDoper.add(radioButtonNoItem);

	}


	}
