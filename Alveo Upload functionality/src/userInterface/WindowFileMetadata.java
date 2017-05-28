package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;



import net.sf.json.JSONObject;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;

/** A upload function GUI class 
 * 
 *
 * @author Kun He
 *
 */

public class WindowFileMetadata {

	JFrame frame;

	public JSONObject metadataMapping = new JSONObject();
	private JCheckBox chckbxFirst, chckbxSecond, chckbxThird, chckbxForth;
	private JTable table;
	private JTextField txtWavtxt;
	private JTextField textField;


	/**
	 * Create the application.
	 * @param key 
	 */
	public WindowFileMetadata() {		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @param key 
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 400);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);

		ButtonGroup items = new ButtonGroup();


		// Open Metadata Editor button
		JButton btnUpdateExistingCol = new JButton("Return");
		btnUpdateExistingCol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				metadataMapping.put("mapping", getTableData(table));
				metadataMapping.put("extensions", txtWavtxt.getText());
				metadataMapping.put("delim", textField.getText());
				if (chckbxFirst.isSelected()) {
					metadataMapping.put("useFirst", true);
				} else {
					metadataMapping.put("useFirst", false);
				}

				if (chckbxSecond.isSelected()) {
					metadataMapping.put("useSecond", true);
				} else {
					metadataMapping.put("useSecond", false);
				}

				if (chckbxThird.isSelected()) {
					metadataMapping.put("useThird", true);
				} else {
					metadataMapping.put("useThird", false);
				}
				if (chckbxForth.isSelected()) {
					metadataMapping.put("useForth", true);
				} else {
					metadataMapping.put("useForth", false);
				}

				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		btnUpdateExistingCol.setBounds(63, 327, 262, 36);
		frame.getContentPane().add(btnUpdateExistingCol);

		//		table.setBounds(72, 88, 1, 1);
		//		frame.getContentPane().add(table);
		String[] colNames = { "Portion", "Metadata tag", "Metadata type" };
		DefaultTableModel model = new DefaultTableModel(colNames, 0);
		String[] first = {"first", "", "item"};
		String[] second = {"second", "", "item"};
		String[] third = {"third", "", "item"};
		String[] forth = {"forth", "", "item"};
		model.addRow(first);
		model.addRow(second);
		model.addRow(third);
		model.addRow(forth);
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(35, 138, 320, 121);
		frame.getContentPane().add(scrollPane);

		JLabel lblFormatFirstsecondthirdext = new JLabel("Format: first.second.third.ext");
		lblFormatFirstsecondthirdext.setBounds(87, 118, 248, 22);
		frame.getContentPane().add(lblFormatFirstsecondthirdext);

		txtWavtxt = new JTextField();
		txtWavtxt.setText("wav,txt");
		txtWavtxt.setBounds(71, 74, 114, 19);
		frame.getContentPane().add(txtWavtxt);
		txtWavtxt.setColumns(10);

		JLabel lblReadFrom = new JLabel("Read from:");
		lblReadFrom.setBounds(71, 55, 114, 22);
		frame.getContentPane().add(lblReadFrom);

		chckbxFirst = new JCheckBox("first");
		chckbxFirst.setSelected(true);
		chckbxFirst.setBounds(48, 300, 66, 23);
		frame.getContentPane().add(chckbxFirst);

		chckbxSecond = new JCheckBox("second");
		chckbxSecond.setSelected(true);
		chckbxSecond.setBounds(118, 300, 91, 23);
		frame.getContentPane().add(chckbxSecond);

		chckbxThird = new JCheckBox("third");
		chckbxThird.setBounds(209, 300, 66, 23);
		frame.getContentPane().add(chckbxThird);

		JLabel lblPortionsToUse = new JLabel("Portions to use in Item title");
		lblPortionsToUse.setBounds(82, 271, 194, 15);
		frame.getContentPane().add(lblPortionsToUse);

		JLabel lblDelimeter = new JLabel("Delimeter");
		lblDelimeter.setBounds(220, 55, 70, 15);
		frame.getContentPane().add(lblDelimeter);

		textField = new JTextField();
		textField.setText(".");
		textField.setBounds(220, 74, 25, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		chckbxForth = new JCheckBox("forth");
		chckbxForth.setBounds(281, 300, 91, 23);
		frame.getContentPane().add(chckbxForth);



	}

	// Get data from table to JSONObject
	//REWRITE THIS CODE
	public static JSONObject getTableData (JTable table) {
		JSONObject dataFromTable = new JSONObject();
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
		int i = 0;
		//	    Object[][] tableData = new Object[nRow][nCol];
		for (; i < nRow ; i++){
			//For multi-layered json
			dataFromTable.element((String)dtm.getValueAt(i,0), (String)dtm.getValueAt(i,1) );

		}
		System.out.println(dataFromTable.toString());
		return dataFromTable;


	}
}