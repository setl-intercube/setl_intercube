package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.category.DefaultCategoryDataset;

import controller.Definition;
import helper.AccessVariables;
import helper.CheckListRenderer;
import helper.EnabledComboBoxRenderer;
import helper.Methods;
import helper.MyDisabledTreeRenderer;
import helper.MyListCellRenderer;
import model.CheckListItem;
import model.SelectedLevel;
import model.SelectedLevelInstance;
import model.TwinValue;
import net.miginfocom.swing.MigLayout;
import queries.Extraction;
//import java.awt.Component;

public class PanelOlap2<JFreeChart> extends JPanel {
	private static final long serialVersionUID = 1L;
	private Methods methods;
	private Definition definition;
	private Extraction extraction;
	private JDialog dialog;
	private JLabel lblTboxPath;
	private JLabel lblAboxPath;
	private JLabel lblCurrentLevelValueCube1;
	private JLabel lblCurrentLevelValueCube2;
	private JList<String> listSchemaIRI;
	private JList<String> listSchemaIRI_1;
	private JList<String> listSchemaGraph;
	private JList<String> listInstanceGraph;
	private JList<CheckListItem> listInstanceCube1;
	private JList<CheckListItem> listInstanceCube2;
	private JLabel lblDatasetIriValue;
	private JLabel lblDatasetIriValue_1;
	private JLabel lblObservationValue;
	private JLabel lblQbolapVersionValue;
	private JTree treeDimensionCube1;
	private JTree treeDimensionCube2;
	private JTree treeMeasureCube1;
	private JTree treeMeasureCube2;
	private JComboBox<String> comboBoxPropertyCube1;
	private JComboBox<String> comboBoxPropertyCube2;
	private JComboBox<String> comboBoxFilterConditionCube1;
	private JComboBox<String> comboBoxFilterConditionCube2;
	private JComboBox<String> comboBoxDataset;
	private JComboBox<String> comboBoxDataset_1;
	private JTextArea textAreaQuery;
	private boolean isSingle;
	private boolean isEnabled;
	private JPanel panelMeasureHolderCube1;
	private JPanel panelLevelHolderCube1;
	private JPanel panelMeasureHolderCube2;
	private JPanel panelLevelHolderCube2;
	private JList<String> listSelectionPropertyCube1;
	private JList<String> listSelectionPropertyCube2;
	private JTable tableResult = new JTable();
	private static final int[] SELECTION_INTERVAL = { 0, 1 };
	protected static int num = 0;
    private DefaultListSelectionModel model = new DefaultListSelectionModel();
    private EnabledComboBoxRenderer enableRenderer = new EnabledComboBoxRenderer();

    private EnabledListener enabledListener = new EnabledListener();
    private DisabledListener disabledListener = new DisabledListener();
    private ArrayList<String> bannedLevelsFromCube1 = new ArrayList<>();
    private ArrayList<String> bannedLevelsFromCube2 = new ArrayList<>();
    
//    public JFreeChart barChart;

    String tboxPath = "C:\\Users\\A\\OneDrive\\Documents\\New Query\\updated_tbox.ttl";
    String aboxPath = "C:\\Users\\A\\OneDrive\\Documents\\New Query";
    String sparqlEndPoint = "";
    private JLabel lblSchemaIri;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "serial" })
	public PanelOlap2() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		initializeAll();

		JPanel panelOlapButtons = new JPanel();
		panelOlapButtons.setBackground(Color.WHITE);
		add(panelOlapButtons, BorderLayout.NORTH);

		JButton btnLocalFile = new JButton("Local File");
		btnLocalFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isSingle = false;

				JPanel panelHolder = new JPanel();
				panelHolder.setBackground(Color.WHITE);
				panelHolder.setLayout(new BorderLayout(0, 0));

				JPanel panelBoth = new JPanel();
				JPanel panelSingle = new JPanel();
				panelSingle.setBackground(Color.WHITE);
				panelHolder.add(panelSingle, BorderLayout.CENTER);
				panelSingle.setLayout(new MigLayout("", "[][800px, grow][]", "[][]"));

				JLabel lblLocalFile = new JLabel("Local RDF File:");
				lblLocalFile.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelSingle.add(lblLocalFile, "cell 0 0,alignx trailing");

				JTextField textFieldLocalFile = new JTextField();
				textFieldLocalFile.setMargin(new Insets(5, 5, 5, 5));
				textFieldLocalFile.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelSingle.add(textFieldLocalFile, "cell 1 0,growx");
				textFieldLocalFile.setColumns(10);

				JButton btnOpenLocal = new JButton("Open");
				btnOpenLocal.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String filePath = methods.chooseFile("Select Local File");

						if (filePath != null) {
							textFieldLocalFile.setText(filePath);
						}
					}
				});
				btnOpenLocal.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelSingle.add(btnOpenLocal, "cell 2 0");

				JCheckBox chckbxUseSameFile = new JCheckBox("Use same file for both");
				JCheckBox chckbxUseTwoSeperate = new JCheckBox("Use two seperate Files");
				chckbxUseTwoSeperate.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (chckbxUseTwoSeperate.isSelected()) {
							panelHolder.removeAll();
							panelHolder.add(panelBoth, BorderLayout.CENTER);
							panelHolder.repaint();
							panelHolder.revalidate();
							chckbxUseSameFile.setSelected(false);

							isSingle = false;
						}
					}
				});
				chckbxUseTwoSeperate.setBackground(Color.WHITE);
				chckbxUseTwoSeperate.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelSingle.add(chckbxUseTwoSeperate, "cell 1 1");

				panelBoth.setBackground(Color.WHITE);
				panelHolder.add(panelBoth, BorderLayout.CENTER);
				panelBoth.setLayout(new MigLayout("", "[][800px, grow]", "[][][]"));

				JLabel lblTbox = new JLabel("TBox File:");
				lblTbox.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelBoth.add(lblTbox, "cell 0 0,alignx trailing");

				JTextField textFieldTBox = new JTextField(tboxPath);
				textFieldTBox.setMargin(new Insets(5, 5, 5, 5));
				textFieldTBox.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelBoth.add(textFieldTBox, "flowx,cell 1 0,growx");
				textFieldTBox.setColumns(10);

				JButton btnTBoxOpen = new JButton("Open");
				btnTBoxOpen.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String filePath = methods.chooseFile("Select TBox File");

						if (filePath != null) {
							textFieldTBox.setText(filePath);
						}
					}
				});
				btnTBoxOpen.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelBoth.add(btnTBoxOpen, "cell 1 0");

				JLabel lblAbox = new JLabel("ABox  File:");
				lblAbox.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelBoth.add(lblAbox, "cell 0 1,alignx trailing");

				JTextField textFieldABox = new JTextField(aboxPath);
				textFieldABox.setMargin(new Insets(5, 5, 5, 5));
				textFieldABox.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelBoth.add(textFieldABox, "flowx,cell 1 1,growx");
				textFieldABox.setColumns(10);

				JButton btnABoxOpen = new JButton("Open");
				btnABoxOpen.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String filePath = methods.chooseFile("Select ABox File");

						if (filePath != null) {
							textFieldABox.setText(filePath);
						}
					}
				});
				btnABoxOpen.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelBoth.add(btnABoxOpen, "cell 1 1");

				chckbxUseSameFile.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						if (chckbxUseSameFile.isSelected()) {
							panelHolder.removeAll();
							panelHolder.add(panelSingle, BorderLayout.CENTER);
							panelHolder.repaint();
							panelHolder.revalidate();
							chckbxUseTwoSeperate.setSelected(false);

							isSingle = true;
						}
					}
				});
				chckbxUseSameFile.setBackground(Color.WHITE);
				chckbxUseSameFile.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelBoth.add(chckbxUseSameFile, "cell 1 2");

				int confirmation = JOptionPane.showConfirmDialog(null, panelHolder, "Please Select Files",
						JOptionPane.OK_CANCEL_OPTION);

				if (confirmation == JOptionPane.OK_OPTION) {
					if (isSingle) {
						String filepath = textFieldLocalFile.getText().toString().trim();

						if (filepath != null) {
							lblTboxPath.setText("TBox File: " + filepath);
							lblAboxPath.setText("ABox File: " + filepath);

							dialog = methods.getProgressDialog(getParent());

							EventQueue.invokeLater(new Runnable() {
								@Override
								public void run() {
									if (definition.readTBoxModel(filepath) && definition.readABoxModel(filepath)) {
										definition.initializeOlapEndPoint(null);
									}

									comboBoxDataset.removeAllItems();
									comboBoxDataset_1.removeAllItems();
									for (String dataset : definition.getDatasetProperties().keySet()) {
										comboBoxDataset.addItem(dataset.trim());
										comboBoxDataset_1.addItem(dataset.trim());
									}

									dialog.dispose();
								}
							});
						}
					} else {
						String tboxPath = textFieldTBox.getText().toString().trim();
						String aboxPath = textFieldABox.getText().toString().trim();

						if (tboxPath != null && aboxPath != null) {
							lblTboxPath.setText("TBox File: " + tboxPath);
							lblAboxPath.setText("ABox File: " + aboxPath);

							dialog = methods.getProgressDialog(getParent());

							EventQueue.invokeLater(new Runnable() {
								@Override
								public void run() {
									if (definition.readTBoxModel(tboxPath) && definition.readABoxModel(aboxPath)) {
										definition.initializeOlapEndPoint(null);
									}

									comboBoxDataset.removeAllItems();
									comboBoxDataset_1.removeAllItems();
									for (String dataset : definition.getDatasetProperties().keySet()) {
										comboBoxDataset.addItem(dataset.trim());
										comboBoxDataset_1.addItem(dataset.trim());
									}

									dialog.dispose();
								}
							});
						}
					}
				}
			}
		});
		btnLocalFile.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelOlapButtons.add(btnLocalFile);

		JButton btnEndpoint = new JButton("EndPoint");
		btnEndpoint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel panelInput = new JPanel();
				panelInput.setBackground(Color.WHITE);
				panelInput.setLayout(new MigLayout("", "[][600px, grow]", "[]"));

				JLabel lblEndpoint = new JLabel("EndPoint:");
				lblEndpoint.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelInput.add(lblEndpoint, "cell 0 0,alignx trailing");

				JTextField textFieldEndPoint = new JTextField(AccessVariables.ENDPOINT_ONE);
				textFieldEndPoint.setMargin(new Insets(5, 5, 5, 5));
				textFieldEndPoint.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelInput.add(textFieldEndPoint, "cell 1 0,growx");
				textFieldEndPoint.setColumns(10);

				int confirmation = JOptionPane.showConfirmDialog(getParent(), panelInput, "EndPoint",
						JOptionPane.OK_CANCEL_OPTION);

				if (confirmation == JOptionPane.OK_OPTION) {
					String endPoint = textFieldEndPoint.getText().toString().trim();

					if (methods.checkString(endPoint)) {
						lblTboxPath.setText("TBox EndPoint: " + endPoint);
						lblAboxPath.setText("ABox EndPoint: " + endPoint);

						dialog = methods.getProgressDialog(getParent());

						EventQueue.invokeLater(new Runnable() {
							@Override
							public void run() {
								
								definition.initializeOlapEndPoint(endPoint);

								comboBoxDataset.removeAllItems();
								comboBoxDataset_1.removeAllItems();
								for (String dataset : definition.getDatasetProperties().keySet()) {
									comboBoxDataset.addItem(dataset.trim());
									comboBoxDataset_1.addItem(dataset.trim());
								}

								dialog.dispose();
							}
						});
					} else {
						methods.showDialog("Check File Path");
					}
				}
			}
		});
		btnEndpoint.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelOlapButtons.add(btnEndpoint);

		JPanel panelContainer = new JPanel();
		panelContainer.setBackground(Color.WHITE);
		add(panelContainer, BorderLayout.CENTER);
		panelContainer.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JSplitPane splitPaneFirst = new JSplitPane();
		splitPaneFirst.setResizeWeight(0.4);
		panelContainer.add(splitPaneFirst, "cell 0 0,grow");

		JPanel panelTreeHolder = new JPanel();
		splitPaneFirst.setLeftComponent(panelTreeHolder);
		panelTreeHolder.setBackground(Color.WHITE);
		panelTreeHolder.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JSplitPane splitPaneOne = new JSplitPane();
		splitPaneOne.setResizeWeight(0.4);
		splitPaneOne.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPaneOne.setOneTouchExpandable(true);
		panelTreeHolder.add(splitPaneOne, "cell 0 0,grow");

		JScrollPane scrollPaneDataset = new JScrollPane();
		splitPaneOne.setLeftComponent(scrollPaneDataset);

		JPanel panelDataset = new JPanel();
		scrollPaneDataset.setViewportView(panelDataset);
		panelDataset.setBackground(Color.WHITE);
		panelDataset.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][grow][][][][][][]"));

		lblTboxPath = new JLabel("TBox Path:");
		lblTboxPath.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		panelDataset.add(lblTboxPath, "cell 0 0 2 1,grow");

		lblAboxPath = new JLabel("ABox Path:");
		lblAboxPath.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		panelDataset.add(lblAboxPath, "cell 0 1 2 1,grow");

		JLabel lblDataset = new JLabel("Dataset 1:");
		lblDataset.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelDataset.add(lblDataset, "cell 0 2");

		comboBoxDataset = new JComboBox<>();
		comboBoxDataset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBoxDataset.getSelectedItem() != null) {
					String datasetName = comboBoxDataset.getSelectedItem().toString();

					LinkedHashMap<String, ArrayList<String>> hashMap = definition.getDatasetProperties()
							.get(datasetName);
					if (hashMap.containsKey("numobs")) {
						String numobs = methods.convertArrayListToString(hashMap.get("numobs"));
						lblObservationValue.setText(numobs);
						num = Integer.valueOf(numobs);
					}

					if (hashMap.containsKey("version")) {
						String version = methods.convertArrayListToString(hashMap.get("version"));
						lblQbolapVersionValue.setText(version);
					}

					DefaultListModel<String> iriModel = new DefaultListModel<>();
					for (String string : hashMap.get("cubeuriString")) {
						iriModel.addElement(string);
					}
					listSchemaIRI.setModel(iriModel);

					DefaultListModel<String> schemaModel = new DefaultListModel<>();
					if (hashMap.containsKey("schemagraphString")) {
						for (String string : hashMap.get("schemagraphString")) {
							schemaModel.addElement(string);
						}
					}
					listSchemaGraph.setModel(schemaModel);

					DefaultListModel<String> instanceModel = new DefaultListModel<>();
					if (hashMap.containsKey("instancegraphString")) {
						for (String string : hashMap.get("instancegraphString")) {
							instanceModel.addElement(string);
						}
					}

					listInstanceGraph.setModel(instanceModel);
					lblDatasetIriValue.setText(datasetName);
				}
			}
		});
		comboBoxDataset.setBackground(Color.WHITE);
		comboBoxDataset.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelDataset.add(comboBoxDataset, "cell 1 2,growx");

				JButton btnExtractCube = new JButton("Extract Cube");
				btnExtractCube.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (comboBoxDataset.getSelectedItem() != null) {
							clearAllCube1Selection();

							String dataset = comboBoxDataset.getSelectedItem().toString().trim();

							int schemaGraphSize = listSchemaGraph.getModel().getSize();
							int instanceGraphSize = listInstanceGraph.getModel().getSize();

							List<String> selectedGraphs = listSchemaGraph.getSelectedValuesList();
							List<String> selectedInstances = listInstanceGraph.getSelectedValuesList();

							if (schemaGraphSize == 0 || instanceGraphSize == 0) {
								dialog = methods.getProgressDialog(getParent());

								EventQueue.invokeLater(new Runnable() {
									@Override
									public void run() {
										
										definition.extractDatasetCube(dataset, null, null);
										refreshDimensionTreeForCube1();
										refreshMeasureTreeForCube1();
										dialog.dispose();
									}
								});
							} else {
								if (selectedGraphs.size() == 0 || selectedInstances.size() == 0) {
									methods.showDialog("Select both schema graphs and instances");
								} else {
									dialog = methods.getProgressDialog(getParent());

									EventQueue.invokeLater(new Runnable() {
										@Override
										public void run() {
											
											definition.extractDatasetCube(null, selectedGraphs, selectedInstances);
											refreshDimensionTreeForCube1();
											refreshMeasureTreeForCube1();
											dialog.dispose();
										}
									});
								}
							}
						}
					}
				});
				btnExtractCube.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelDataset.add(btnExtractCube, "cell 1 3,alignx right");

				lblSchemaIri = new JLabel("Schema 1 IRI:");
				lblSchemaIri.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelDataset.add(lblSchemaIri, "cell 0 4,aligny top");

				JLabel lblSchemaIri_1 = new JLabel("Schema 2 IRI:");
				lblSchemaIri_1.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelDataset.add(lblSchemaIri_1, "cell 0 8,aligny top");

				listSchemaIRI = new JList<>();
				listSchemaIRI.setValueIsAdjusting(true);
				listSchemaIRI.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
				listSchemaIRI.setBackground(Color.WHITE);
				MyListCellRenderer cellRenderer = new MyListCellRenderer();
				listSchemaIRI.setCellRenderer(cellRenderer);
				listSchemaIRI.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelDataset.add(listSchemaIRI, "cell 1 4,grow");

				listSchemaIRI_1 = new JList<>();
				listSchemaIRI_1.setValueIsAdjusting(true);
				listSchemaIRI_1.setFont(new Font("Tahoma", Font.BOLD, 12));
				listSchemaIRI_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
				listSchemaIRI_1.setBackground(Color.WHITE);
				MyListCellRenderer cellRenderer_1 = new MyListCellRenderer();
				listSchemaIRI.setCellRenderer(cellRenderer_1);
				panelDataset.add(listSchemaIRI_1, "cell 1 8,grow");

				JLabel lblDatasetIri = new JLabel("Dataset 1 IRI:");
				lblDatasetIri.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelDataset.add(lblDatasetIri, "cell 0 5");

				JLabel lblDatasetIri_1 = new JLabel("Dataset 2 IRI:");
				lblDatasetIri_1.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelDataset.add(lblDatasetIri_1, "cell 0 9");

				lblDatasetIriValue = new JLabel("");
				lblDatasetIriValue.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelDataset.add(lblDatasetIriValue, "cell 1 5");

				lblDatasetIriValue_1 = new JLabel("");
				lblDatasetIriValue_1.setFont(new Font("Tahoma", Font.BOLD, 12));
				panelDataset.add(lblDatasetIriValue_1, "cell 1 9");

		JLabel lblDataset_1 = new JLabel("Dataset 2:");
		lblDataset_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelDataset.add(lblDataset_1, "cell 0 6");

		comboBoxDataset_1 = new JComboBox<>();
		comboBoxDataset_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBoxDataset_1.getSelectedItem() != null) {
					String datasetName = comboBoxDataset_1.getSelectedItem().toString();

					LinkedHashMap<String, ArrayList<String>> hashMap = definition.getDatasetProperties().get(datasetName);
					if (hashMap.containsKey("numobs")) {
						String numobs = methods.convertArrayListToString(hashMap.get("numobs"));
						int n = Integer.valueOf(numobs);
						num += n;
						String numberOfObservation = Integer.toString(num);
						lblObservationValue.setText(numberOfObservation);
					}

					if (hashMap.containsKey("version")) {
						String version = methods.convertArrayListToString(hashMap.get("version"));
						lblQbolapVersionValue.setText(version);
					}

					DefaultListModel<String> iriModel = new DefaultListModel<>();
					for (String string : hashMap.get("cubeuriString")) {
						iriModel.addElement(string);
					}
					listSchemaIRI_1.setModel(iriModel);

					DefaultListModel<String> schemaModel = new DefaultListModel<>();
					if (hashMap.containsKey("schemagraphString")) {
						for (String string : hashMap.get("schemagraphString")) {
							schemaModel.addElement(string);
						}
					}
					listSchemaGraph.setModel(schemaModel);

					DefaultListModel<String> instanceModel = new DefaultListModel<>();
					if (hashMap.containsKey("instancegraphString")) {
						for (String string : hashMap.get("instancegraphString")) {
							instanceModel.addElement(string);
						}
					}

					listInstanceGraph.setModel(instanceModel);
					lblDatasetIriValue_1.setText(datasetName);
				}
			}
		});
		comboBoxDataset_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		comboBoxDataset_1.setBackground(Color.WHITE);
		panelDataset.add(comboBoxDataset_1, "cell 1 6,growx");

		JButton btnExtractCube_1 = new JButton("Extract Cube");
		btnExtractCube_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (comboBoxDataset_1.getSelectedItem() != null) {
					clearAllCube2Selection();

					String dataset = comboBoxDataset_1.getSelectedItem().toString().trim();

					int schemaGraphSize = listSchemaGraph.getModel().getSize();
					int instanceGraphSize = listInstanceGraph.getModel().getSize();

					List<String> selectedGraphs = listSchemaGraph.getSelectedValuesList();
					List<String> selectedInstances = listInstanceGraph.getSelectedValuesList();

					if (schemaGraphSize == 0 || instanceGraphSize == 0) {
						dialog = methods.getProgressDialog(getParent());

						EventQueue.invokeLater(new Runnable() {
							@Override
							public void run() {
								
								definition.extractDatasetCube(dataset, null, null);
								refreshDimensionTreeForCube2();
								refreshMeasureTreeForCube2();
								dialog.dispose();
							}
						});
					} else {
						if (selectedGraphs.size() == 0 || selectedInstances.size() == 0) {
							methods.showDialog("Select both schema graphs and instances");
						} else {
							dialog = methods.getProgressDialog(getParent());

							EventQueue.invokeLater(new Runnable() {
								@Override
								public void run() {
									
									definition.extractDatasetCube(null, selectedGraphs, selectedInstances);
									refreshDimensionTreeForCube2();
									refreshMeasureTreeForCube2();
									dialog.dispose();
								}
							});
						}
					}
				}
			}
		});
		btnExtractCube_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelDataset.add(btnExtractCube_1, "cell 1 7,alignx right");
		cellRenderer = new MyListCellRenderer();

		JLabel lblSchemaGraph = new JLabel("Schema Graph:");
		lblSchemaGraph.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelDataset.add(lblSchemaGraph, "cell 0 11,aligny top");

		listSchemaGraph = new JList<>();
		listSchemaGraph.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSchemaGraph.setValueIsAdjusting(true);
		listSchemaGraph.setFont(new Font("Tahoma", Font.BOLD, 12));
		listSchemaGraph.setBackground(Color.WHITE);
		listSchemaGraph.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		listSchemaGraph.setCellRenderer(cellRenderer);
		panelDataset.add(listSchemaGraph, "cell 1 11,grow");

		JLabel lblInstanceGraph = new JLabel("Instance Graph:");
		lblInstanceGraph.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelDataset.add(lblInstanceGraph, "cell 0 12,aligny top");

		listInstanceGraph = new JList<>();
		listInstanceGraph.setValueIsAdjusting(true);
		listInstanceGraph.setFont(new Font("Tahoma", Font.BOLD, 12));
		listInstanceGraph.setBackground(Color.WHITE);
		listInstanceGraph.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		listInstanceGraph.setCellRenderer(cellRenderer);
		panelDataset.add(listInstanceGraph, "cell 1 12,grow");

		JLabel lblQbolapVersion = new JLabel("QB4OLAP version:");
		lblQbolapVersion.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelDataset.add(lblQbolapVersion, "cell 0 13");

		lblQbolapVersionValue = new JLabel("");
		lblQbolapVersionValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelDataset.add(lblQbolapVersionValue, "cell 1 13");

		JLabel lblNoOfObservation = new JLabel("No. of observation:");
		lblNoOfObservation.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelDataset.add(lblNoOfObservation, "cell 0 14");

		lblObservationValue = new JLabel("");
		lblObservationValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelDataset.add(lblObservationValue, "cell 1 14");

		//------------------------------------------------------------------------------------------------------------------
		// Panel Cube Structure

		
		JPanel panelCubeStructure = new JPanel();
		splitPaneOne.setRightComponent(panelCubeStructure);
		panelCubeStructure.setBackground(Color.WHITE);
		panelCubeStructure.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JTabbedPane tabbedPaneForCube = new JTabbedPane(SwingConstants.TOP);
		panelCubeStructure.add(tabbedPaneForCube, "cell 0 0,grow");

		JPanel panelCubeHolder1 = new JPanel();
		tabbedPaneForCube.addTab("Cube 1", null, panelCubeHolder1, null);
		panelCubeHolder1.setBackground(Color.WHITE);
		panelCubeHolder1.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JSplitPane splitPaneTwoCube1 = new JSplitPane();
		splitPaneTwoCube1.setResizeWeight(0.5);
		splitPaneTwoCube1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panelCubeHolder1.add(splitPaneTwoCube1, "cell 0 0,grow");
		
		JScrollPane scrollPaneDimForCube1 = new JScrollPane();
		splitPaneTwoCube1.setLeftComponent(scrollPaneDimForCube1);

		JPanel panelDimensionForCube1 = new JPanel();
		scrollPaneDimForCube1.setViewportView(panelDimensionForCube1);
		panelDimensionForCube1.setBackground(Color.WHITE);
		panelDimensionForCube1.setLayout(new MigLayout("", "[grow]", "[grow]"));

		treeDimensionCube1 = new JTree();
		treeDimensionCube1.setCellRenderer(new MyDisabledTreeRenderer(bannedLevelsFromCube1));
		treeDimensionCube1.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeDimensionCube1
						.getLastSelectedPathComponent();
				if (selectedNode != null) {
					if (selectedNode.isLeaf()) {
						if (bannedLevelsFromCube1.contains(selectedNode.toString())) {
							JOptionPane.showMessageDialog(null,
			                        "You can't Select that Level", "ERROR",
			                        JOptionPane.ERROR_MESSAGE);
						} else {
							String selectedPath = treeDimensionCube1.getSelectionPath().toString();
							String selectedLevelName = treeDimensionCube1.getLastSelectedPathComponent().toString();
							String DatasetName = lblDatasetIriValue.getText() + ":";

							SelectedLevel selectedLevelDt1 = new SelectedLevel(DatasetName, selectedLevelName, selectedPath);
							lblCurrentLevelValueCube1.setText(selectedLevelName);
							if (!definition.getSelectedLevelList().contains(selectedLevelDt1)) {
								definition.getSelectedLevelList().add(selectedLevelDt1);

								refreshSelectedLevelsCube1();
							}

							dialog = methods.getProgressDialog(getParent());

							EventQueue.invokeLater(new Runnable() {
								@Override
								public void run() {
									
									definition.extractLevelProperties(selectedLevelName);
									comboBoxPropertyCube1.removeAllItems();

									DefaultListModel<String> model = new DefaultListModel<>();
									for (String property : definition.getLevelProperties()) {
										comboBoxPropertyCube1.addItem(property);
										model.addElement(property);
									}
									listSelectionPropertyCube1.setModel(model);

									comboBoxFilterConditionCube1.setSelectedIndex(0);
									dialog.dispose();
								}
							});
						}
					}
				}
			}
		});
		treeDimensionCube1.setBackground(Color.WHITE);
		treeDimensionCube1.setFont(new Font("Tahoma", Font.BOLD, 12));
		treeDimensionCube1.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Dimensions") {
			{
			}
		}));
		panelDimensionForCube1.add(treeDimensionCube1, "cell 0 0,grow");

		JScrollPane scrollPaneMeasureCube1 = new JScrollPane();
		splitPaneTwoCube1.setRightComponent(scrollPaneMeasureCube1);

		JPanel panelMeasureForCube1 = new JPanel();
		scrollPaneMeasureCube1.setViewportView(panelMeasureForCube1);
		panelMeasureForCube1.setBackground(Color.WHITE);
		panelMeasureForCube1.setLayout(new MigLayout("", "[grow]", "[grow]"));

		treeMeasureCube1 = new JTree();
		treeMeasureCube1.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				if (treeMeasureCube1.getSelectionPath() != null) {
					String measurePath = treeMeasureCube1.getSelectionPath().toString();
					String[] parts = measurePath.split(",");

					if (parts.length == 2) {
						String measureString = parts[1].trim();
						measureString = measureString.substring(0, parts[1].length() - 1);

						methods.showDialog("Select aggregate functions only");
					} else if (parts.length == 4) {
						String measureString = parts[1].trim();
						measureString = measureString.substring(0, parts[1].length() - 1);

						String functionString = parts[3].trim();
						functionString = functionString.substring(0, parts[3].length() - 2);

						methods.addToComplexHashMap(definition.getSelectedMeasureFunctionMap(), measureString, functionString);
						refreshSelectedMeasureCube1();
					}
				}
			}
		});
		treeMeasureCube1.setFont(new Font("Tahoma", Font.BOLD, 12));
		treeMeasureCube1.setBackground(Color.WHITE);
		treeMeasureCube1.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Measures") {
			{
			}
		}));
		panelMeasureForCube1.add(treeMeasureCube1, "cell 0 0,grow");
		
		//		-------------------------------------------------------------------------
		
		
		JPanel panelCubeHolder2 = new JPanel();
		tabbedPaneForCube.addTab("Cube 2", null, panelCubeHolder2, null);
		panelCubeHolder2.setBackground(Color.WHITE);
		panelCubeHolder2.setLayout(new MigLayout("", "[grow]", "[grow][]"));
		
		JSplitPane splitPaneTwoCube2 = new JSplitPane();
		splitPaneTwoCube2.setResizeWeight(0.5);
		splitPaneTwoCube2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panelCubeHolder2.add(splitPaneTwoCube2, "cell 0 0,grow");
		
		JScrollPane scrollPaneDimForCube2 = new JScrollPane();
		splitPaneTwoCube2.setLeftComponent(scrollPaneDimForCube2);

		JPanel panelDimensionForCube2 = new JPanel();
		scrollPaneDimForCube2.setViewportView(panelDimensionForCube2);
		panelDimensionForCube2.setBackground(Color.WHITE);
		panelDimensionForCube2.setLayout(new MigLayout("", "[grow]", "[grow]"));

		treeDimensionCube2 = new JTree();
		treeDimensionCube2.setCellRenderer(new MyDisabledTreeRenderer(bannedLevelsFromCube2));
		treeDimensionCube2.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeDimensionCube2
						.getLastSelectedPathComponent();
				if (selectedNode != null) {
					if (selectedNode.isLeaf()) {
						if (bannedLevelsFromCube2.contains(selectedNode.toString())) {
							JOptionPane.showMessageDialog(null,
			                        "You can't Select that Level", "ERROR",
			                        JOptionPane.ERROR_MESSAGE);
						} else {
							String selectedPath = treeDimensionCube2.getSelectionPath().toString();
							String selectedLevelName = treeDimensionCube2.getLastSelectedPathComponent().toString();
							String DatasetName = lblDatasetIriValue_1.getText() + ":";

							SelectedLevel selectedLevelDt2 = new SelectedLevel(DatasetName, selectedLevelName, selectedPath);
							lblCurrentLevelValueCube2.setText(selectedLevelName);
							if (!definition.getSelectedLevelList1().contains(selectedLevelDt2)) {
								definition.getSelectedLevelList1().add(selectedLevelDt2);

								refreshSelectedLevelsCube2();
							}

							dialog = methods.getProgressDialog(getParent());

							EventQueue.invokeLater(new Runnable() {
								@Override
								public void run() {
									
									definition.extractLevelProperties(selectedLevelName);
									comboBoxPropertyCube2.removeAllItems();

									DefaultListModel<String> model = new DefaultListModel<>();
									for (String property : definition.getLevelProperties()) {
										comboBoxPropertyCube2.addItem(property);
										model.addElement(property);
									}
									listSelectionPropertyCube2.setModel(model);

									comboBoxFilterConditionCube2.setSelectedIndex(0);
									dialog.dispose();
								}
							});
						}
					}
				}
			}
		});
		treeDimensionCube2.setBackground(Color.WHITE);
		treeDimensionCube2.setFont(new Font("Tahoma", Font.BOLD, 12));
		treeDimensionCube2.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Dimensions") {
			{
			}
		}));
		panelDimensionForCube2.add(treeDimensionCube2, "cell 0 0,grow");

		JScrollPane scrollPaneMeasureForCube2 = new JScrollPane();
		splitPaneTwoCube2.setRightComponent(scrollPaneMeasureForCube2);

		JPanel panelMeasureCube2 = new JPanel();
		scrollPaneMeasureForCube2.setViewportView(panelMeasureCube2);
		panelMeasureCube2.setBackground(Color.WHITE);
		panelMeasureCube2.setLayout(new MigLayout("", "[grow]", "[grow]"));

		treeMeasureCube2 = new JTree();
		treeMeasureCube2.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				if (treeMeasureCube2.getSelectionPath() != null) {
					String measurePath = treeMeasureCube2.getSelectionPath().toString();
					String[] parts = measurePath.split(",");

					if (parts.length == 2) {
						String measureString = parts[1].trim();
						measureString = measureString.substring(0, parts[1].length() - 1);

						methods.showDialog("Select aggregate functions only");
					} else if (parts.length == 4) {
						String measureString = parts[1].trim();
						measureString = measureString.substring(0, parts[1].length() - 1); 

						String functionString = parts[3].trim();
						functionString = functionString.substring(0, parts[3].length() - 2);

						methods.addToComplexHashMap(definition.getSelectedMeasureFunctionMap1(), measureString, functionString);
						refreshSelectedMeasureCube2();
					}
				}
			}
		});
		treeMeasureCube2.setFont(new Font("Tahoma", Font.BOLD, 12));
		treeMeasureCube2.setBackground(Color.WHITE);
		treeMeasureCube2.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Measures") {
			{
			}
		}));
		panelMeasureCube2.add(treeMeasureCube2, "cell 0 0,grow");
		
		//-------------------------------------------------------------------------------------------------------------------------		
		// Panel Component Holder 
		
		JPanel panelComponentHolder = new JPanel();
		splitPaneFirst.setRightComponent(panelComponentHolder);
		panelComponentHolder.setBackground(Color.WHITE);
		panelComponentHolder.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		panelComponentHolder.add(tabbedPane, "cell 0 0,grow");

		JPanel panelDataHolder = new JPanel();
		tabbedPane.addTab("Olap Query Generation by MD Construct Selection", null, panelDataHolder, null);
		panelDataHolder.setBackground(Color.WHITE);
		panelDataHolder.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JSplitPane splitPaneThree = new JSplitPane();
		splitPaneThree.setResizeWeight(0.4);
		panelDataHolder.add(splitPaneThree, "cell 0 0,grow");

		JPanel panelSelectionHolder = new JPanel();
		splitPaneThree.setLeftComponent(panelSelectionHolder);
		panelSelectionHolder.setBackground(Color.WHITE);
		panelSelectionHolder.setLayout(new MigLayout("", "[][grow]", "[][][][][grow]"));
		
		JTabbedPane tabbedPaneForSelectionHolder = new JTabbedPane(SwingConstants.TOP);
		panelSelectionHolder.add(tabbedPaneForSelectionHolder, "cell 0 0 2 1,grow");

		JPanel panelSelectionHolderForCube1 = new JPanel();
		tabbedPaneForSelectionHolder.addTab("Cube 1", null, panelSelectionHolderForCube1, null);
		panelSelectionHolderForCube1.setBackground(Color.WHITE);
		panelSelectionHolderForCube1.setLayout(new MigLayout("", "[][grow]", "[][][][][grow]"));

		JLabel lblCurrentLabelCube1 = new JLabel("Current Level:");
		lblCurrentLabelCube1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSelectionHolderForCube1.add(lblCurrentLabelCube1, "cell 0 0");

		lblCurrentLevelValueCube1 = new JLabel("");
		lblCurrentLevelValueCube1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSelectionHolderForCube1.add(lblCurrentLevelValueCube1, "cell 1 0,grow");

		JLabel lblPropertyCube1 = new JLabel("Property:");
		lblPropertyCube1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSelectionHolderForCube1.add(lblPropertyCube1, "cell 0 1,alignx left");

		comboBoxPropertyCube1 = new JComboBox<>();
		comboBoxPropertyCube1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBoxPropertyCube1.getSelectedItem() != null) {
					
					String selectedProperty = comboBoxPropertyCube1.getSelectedItem().toString();
					String selectedLevelDt1 = lblCurrentLevelValueCube1.getText().toString().trim();

					if (methods.checkString(selectedProperty) && methods.checkString(selectedLevelDt1)) {
						definition.extractLevelInstances(selectedLevelDt1, selectedProperty);
					}

					SelectedLevelInstance selectedLevelInstance = new SelectedLevelInstance();
					if (definition.getInstancesMap().containsKey(selectedLevelDt1)) {
						if (definition.getInstancesMap().get(selectedLevelDt1).containsKey(selectedProperty)) {
							selectedLevelInstance = definition.getInstancesMap().get(selectedLevelDt1)
									.get(selectedProperty);
						}
					}

					DefaultListModel<CheckListItem> defaultListModel = new DefaultListModel<>();

					if (definition.getLevelInstanceObjects().size() > 0) {
						if (definition.getLevelInstanceObjects().get(0) instanceof String) {
							// System.out.println("String");
							disableItemsInComboBox();
						} else {
							// System.out.println(definition.getLevelInstanceObjects().get(0));
							// System.out.println("Something else");
							enableItemsInComboBox();
						}
					}


					for (Object object : definition.getLevelInstanceObjects()) {
						CheckListItem checkListItem = new CheckListItem(object);
						if (selectedLevelInstance.containInstance(object)) {
							checkListItem.setSelected(true);
						}
						defaultListModel.addElement(checkListItem);
					}
					listInstanceCube1.setModel(defaultListModel);
				}
			}
		});
		comboBoxPropertyCube1.setBackground(Color.WHITE);
		comboBoxPropertyCube1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSelectionHolderForCube1.add(comboBoxPropertyCube1, "cell 1 1,growx");

		JLabel lblFilterConditionCube1 = new JLabel("Filter Condition:");
		lblFilterConditionCube1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSelectionHolderForCube1.add(lblFilterConditionCube1, "cell 0 2,alignx leading");

		comboBoxFilterConditionCube1 = new JComboBox<>();
		model.addSelectionInterval(SELECTION_INTERVAL[0], SELECTION_INTERVAL[0]);
        enableRenderer.setEnabledItems(model);
        comboBoxFilterConditionCube1.setRenderer(enableRenderer);
		comboBoxFilterConditionCube1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (isEnabled) {
					if (listInstanceCube1 != null) {
						ListModel<CheckListItem> listModel = listInstanceCube1.getModel();

						for (int i = 0; i < listModel.getSize(); i++) {
							CheckListItem checkListItem = listModel.getElementAt(i);
							checkListItem.setSelected(false);
							listInstanceCube1.repaint(listInstanceCube1.getCellBounds(i, i));
						}

						if (comboBoxPropertyCube1.getSelectedItem() != null) {
							copyInstancesForCube1();
						}
					}
				}
			}
		});
		comboBoxFilterConditionCube1.setBackground(Color.WHITE);
		comboBoxFilterConditionCube1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSelectionHolderForCube1.add(comboBoxFilterConditionCube1, "cell 1 2,growx");

		int countCube1 = 0;
		for (String conditionString : definition.getOlapConditionalHashMap().keySet()) {
			if (countCube1 > 0) {
				comboBoxFilterConditionCube1.addItem(conditionString);
			}
			countCube1++;
		}

		JLabel lblPropertiesForSelectionCube1 = new JLabel("Properties to be Viewed:");
		lblPropertiesForSelectionCube1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSelectionHolderForCube1.add(lblPropertiesForSelectionCube1, "cell 0 3");

		listSelectionPropertyCube1 = new JList<>();
		listSelectionPropertyCube1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String levelString = lblCurrentLevelValueCube1.getText().toString().trim();
				List<String> strings = listSelectionPropertyCube1.getSelectedValuesList();

				for (int i = 0; i < definition.getSelectedLevelList().size(); i++) {
					SelectedLevel level = definition.getSelectedLevelList().get(i);

					if (level.match(levelString)) {
						level.setViewProperties(strings);
						definition.getSelectedLevelList().set(i, level);
						break;
					}
				}

				refreshSelectedLevelsCube1();
			}
		});
		listSelectionPropertyCube1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		listSelectionPropertyCube1.setFont(new Font("Tahoma", Font.BOLD, 12));
		listSelectionPropertyCube1.setBackground(Color.WHITE);
		panelSelectionHolderForCube1.add(listSelectionPropertyCube1, "cell 1 3,grow");

		JPanel panelInstanceSelectionCube1 = new JPanel();
		panelInstanceSelectionCube1.setBackground(Color.WHITE);
		panelSelectionHolderForCube1.add(panelInstanceSelectionCube1, "cell 0 4 2 1,grow");
		panelInstanceSelectionCube1.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JScrollPane scrollPaneInstance = new JScrollPane();
		panelInstanceSelectionCube1.add(scrollPaneInstance, "cell 0 0,grow");

		listInstanceCube1 = new JList<>();
		listInstanceCube1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JList<?> list = (JList<?>) arg0.getSource();
				int index = list.locationToIndex(arg0.getPoint());

				String selectedCondition = comboBoxFilterConditionCube1.getSelectedItem().toString();
				TwinValue twinValue = definition.getOlapConditionalHashMap().get(selectedCondition);

				ListModel<CheckListItem> listModel = listInstanceCube1.getModel();

				int selected = 0;
				for (int i = 0; i < listModel.getSize(); i++) {
					CheckListItem checkListItem = listModel.getElementAt(i);
					if (checkListItem.isSelected()) {
						selected++;
					}
				}

				if (twinValue.getSecondValue().equals("single")) {
					if (selected < 1) {
						CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
						item.setSelected(!item.isSelected());
						list.repaint(list.getCellBounds(index, index));
					} else {
						methods.showDialog("This filter condition doesn't allow multiple selection");

						for (int i = 0; i < listModel.getSize(); i++) {
							CheckListItem checkListItem = listModel.getElementAt(i);
							checkListItem.setSelected(false);
							list.repaint(list.getCellBounds(i, i));
						}

						copyInstancesForCube1();
						return;
					}
				} else {
					CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
					item.setSelected(!item.isSelected());
					list.repaint(list.getCellBounds(index, index));
				}

				copyInstancesForCube1();
			}
		});
		listInstanceCube1.setCellRenderer(new CheckListRenderer());
		scrollPaneInstance.setViewportView(listInstanceCube1);
		listInstanceCube1.setBackground(Color.WHITE);
		listInstanceCube1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JPanel panelSelectionHolderForCube2 = new JPanel();
		tabbedPaneForSelectionHolder.addTab("Cube 2", null, panelSelectionHolderForCube2, null);
		panelSelectionHolderForCube2.setBackground(Color.WHITE);
		panelSelectionHolderForCube2.setLayout(new MigLayout("", "[][grow]", "[][][][][grow]"));

		JLabel lblCurrentLabelCube2 = new JLabel("Current Level:");
		lblCurrentLabelCube2.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSelectionHolderForCube2.add(lblCurrentLabelCube2, "cell 0 0");

		lblCurrentLevelValueCube2 = new JLabel("");
		lblCurrentLevelValueCube2.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSelectionHolderForCube2.add(lblCurrentLevelValueCube2, "cell 1 0,grow");

		JLabel lblPropertyCube2 = new JLabel("Property:");
		lblPropertyCube2.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSelectionHolderForCube2.add(lblPropertyCube2, "cell 0 1,alignx left");

		comboBoxPropertyCube2 = new JComboBox<>();
		comboBoxPropertyCube2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBoxPropertyCube2.getSelectedItem() != null) {
					
					String selectedProperty = comboBoxPropertyCube2.getSelectedItem().toString();
					String selectedLevelDt2 = lblCurrentLevelValueCube2.getText().toString().trim();

					if (methods.checkString(selectedProperty) && methods.checkString(selectedLevelDt2)) {
						definition.extractLevelInstances(selectedLevelDt2, selectedProperty);
					}

					SelectedLevelInstance selectedLevelInstance = new SelectedLevelInstance();
					if (definition.getInstancesMap().containsKey(selectedLevelDt2)) {
						if (definition.getInstancesMap().get(selectedLevelDt2).containsKey(selectedProperty)) {
							selectedLevelInstance = definition.getInstancesMap().get(selectedLevelDt2)
									.get(selectedProperty);
						}
					}

					DefaultListModel<CheckListItem> defaultListModel = new DefaultListModel<>();

					if (definition.getLevelInstanceObjects().size() > 0) {
						if (definition.getLevelInstanceObjects().get(0) instanceof String) {
							// System.out.println("String");
							disableItemsInComboBox();
						} else {
							// System.out.println(definition.getLevelInstanceObjects().get(0));
							// System.out.println("Something else");
							enableItemsInComboBox();
						}
					}


					for (Object object : definition.getLevelInstanceObjects()) {
						CheckListItem checkListItem = new CheckListItem(object);
						if (selectedLevelInstance.containInstance(object)) {
							checkListItem.setSelected(true);
						}
						defaultListModel.addElement(checkListItem);
					}
					listInstanceCube2.setModel(defaultListModel);
				}
			}
		});
		comboBoxPropertyCube2.setBackground(Color.WHITE);
		comboBoxPropertyCube2.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSelectionHolderForCube2.add(comboBoxPropertyCube2, "cell 1 1,growx");

		JLabel lblFilterConditionCube2 = new JLabel("Filter Condition:");
		lblFilterConditionCube2.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSelectionHolderForCube2.add(lblFilterConditionCube2, "cell 0 2,alignx leading");

		comboBoxFilterConditionCube2 = new JComboBox<>();
		model.addSelectionInterval(SELECTION_INTERVAL[0], SELECTION_INTERVAL[0]);
        enableRenderer.setEnabledItems(model);
        comboBoxFilterConditionCube2.setRenderer(enableRenderer);
		comboBoxFilterConditionCube2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (isEnabled) {
					if (listInstanceCube2 != null) {
						ListModel<CheckListItem> listModel = listInstanceCube2.getModel();

						for (int i = 0; i < listModel.getSize(); i++) {
							CheckListItem checkListItem = listModel.getElementAt(i);
							checkListItem.setSelected(false);
							listInstanceCube2.repaint(listInstanceCube2.getCellBounds(i, i));
						}

						if (comboBoxPropertyCube2.getSelectedItem() != null) {
							copyInstancesForCube2();
						}
					}
				}
			}
		});
		comboBoxFilterConditionCube2.setBackground(Color.WHITE);
		comboBoxFilterConditionCube2.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSelectionHolderForCube2.add(comboBoxFilterConditionCube2, "cell 1 2,growx");

		int countCube2 = 0;
		for (String conditionString : definition.getOlapConditionalHashMap().keySet()) {
			if (countCube2 > 0) {
				comboBoxFilterConditionCube2.addItem(conditionString);
			}
			countCube2++;
		}

		JLabel lblPropertiesForSelectionCube2 = new JLabel("Properties to be Viewed:");
		lblPropertiesForSelectionCube2.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSelectionHolderForCube2.add(lblPropertiesForSelectionCube2, "cell 0 3");

		listSelectionPropertyCube2 = new JList<>();
		listSelectionPropertyCube2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String levelString = lblCurrentLevelValueCube2.getText().toString().trim();
				List<String> strings = listSelectionPropertyCube2.getSelectedValuesList();

				for (int i = 0; i < definition.getSelectedLevelList1().size(); i++) {
					SelectedLevel level = definition.getSelectedLevelList1().get(i);

					if (level.match(levelString)) {
						level.setViewProperties(strings);
						definition.getSelectedLevelList1().set(i, level);
						break;
					}
				}
				

				refreshSelectedLevelsCube2();
			}
		});
		listSelectionPropertyCube2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		listSelectionPropertyCube2.setFont(new Font("Tahoma", Font.BOLD, 12));
		listSelectionPropertyCube2.setBackground(Color.WHITE);
		panelSelectionHolderForCube2.add(listSelectionPropertyCube2, "cell 1 3,grow");

		JPanel panelInstanceSelectionCube2 = new JPanel();
		panelInstanceSelectionCube2.setBackground(Color.WHITE);
		panelSelectionHolderForCube2.add(panelInstanceSelectionCube2, "cell 0 4 2 1,grow");
		panelInstanceSelectionCube2.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JScrollPane scrollPaneInstanceCube2 = new JScrollPane();
		panelInstanceSelectionCube2.add(scrollPaneInstanceCube2, "cell 0 0,grow");

		listInstanceCube2 = new JList<>();
		listInstanceCube2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JList<?> list = (JList<?>) arg0.getSource();
				int index = list.locationToIndex(arg0.getPoint());

				String selectedCondition = comboBoxFilterConditionCube2.getSelectedItem().toString();
				TwinValue twinValue = definition.getOlapConditionalHashMap().get(selectedCondition);

				ListModel<CheckListItem> listModel = listInstanceCube2.getModel();

				int selected = 0;
				for (int i = 0; i < listModel.getSize(); i++) {
					CheckListItem checkListItem = listModel.getElementAt(i);
					if (checkListItem.isSelected()) {
						selected++;
					}
				}

				if (twinValue.getSecondValue().equals("single")) {
					if (selected < 1) {
						CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
						item.setSelected(!item.isSelected());
						list.repaint(list.getCellBounds(index, index));
					} else {
						methods.showDialog("This filter condition doesn't allow multiple selection");

						for (int i = 0; i < listModel.getSize(); i++) {
							CheckListItem checkListItem = listModel.getElementAt(i);
							checkListItem.setSelected(false);
							list.repaint(list.getCellBounds(i, i));
						}

						copyInstancesForCube2();
						return;
					}
				} else {
					CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
					item.setSelected(!item.isSelected());
					list.repaint(list.getCellBounds(index, index));
				}

				copyInstancesForCube2();
			}
		});
		listInstanceCube2.setCellRenderer(new CheckListRenderer());
		scrollPaneInstanceCube2.setViewportView(listInstanceCube2);
		listInstanceCube2.setBackground(Color.WHITE);
		listInstanceCube2.setFont(new Font("Tahoma", Font.BOLD, 12));

		JPanel panelSummaryHolder = new JPanel();
		panelSummaryHolder.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Selection Summary",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		splitPaneThree.setRightComponent(panelSummaryHolder);
		panelSummaryHolder.setBackground(Color.WHITE);
		panelSummaryHolder.setLayout(new MigLayout("", "[grow]", "[grow][][]"));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panelSummaryHolder.add(splitPane, "cell 0 0,grow");

		JScrollPane scrollPaneMeasureContainer = new JScrollPane();
		splitPane.setLeftComponent(scrollPaneMeasureContainer);

		JPanel panelMeasureContainer = new JPanel();
		panelMeasureContainer.setBackground(Color.WHITE);
		scrollPaneMeasureContainer.setViewportView(panelMeasureContainer);
		panelMeasureContainer.setLayout(new MigLayout("", "[grow]", "[]"));

		panelMeasureHolderCube1 = new JPanel();
		panelMeasureHolderCube1.setBackground(Color.WHITE);
		panelMeasureContainer.add(panelMeasureHolderCube1, "cell 0 0,grow");
		panelMeasureHolderCube1.setLayout(new BoxLayout(panelMeasureHolderCube1, BoxLayout.Y_AXIS));
		
		panelMeasureHolderCube2 = new JPanel();
		panelMeasureHolderCube2.setBackground(Color.WHITE);
		panelMeasureContainer.add(panelMeasureHolderCube2, "cell 0 1,grow");
		panelMeasureHolderCube2.setLayout(new BoxLayout(panelMeasureHolderCube2, BoxLayout.Y_AXIS));

		JScrollPane scrollPaneLevelContainer = new JScrollPane();
		splitPane.setRightComponent(scrollPaneLevelContainer);

		JPanel panelLevelContainer = new JPanel();
		panelLevelContainer.setBackground(Color.WHITE);
		scrollPaneLevelContainer.setViewportView(panelLevelContainer);
		panelLevelContainer.setLayout(new MigLayout("", "[grow]", "[]"));

		panelLevelHolderCube1 = new JPanel();
		panelLevelHolderCube1.setBackground(Color.WHITE);
		panelLevelContainer.add(panelLevelHolderCube1, "cell 0 0,grow");
		panelLevelHolderCube1.setLayout(new BoxLayout(panelLevelHolderCube1, BoxLayout.Y_AXIS));
		
		panelLevelHolderCube2 = new JPanel();
		panelLevelHolderCube2.setBackground(Color.WHITE);
		panelLevelContainer.add(panelLevelHolderCube2, "cell 0 1,grow");
		panelLevelHolderCube2.setLayout(new BoxLayout(panelLevelHolderCube2, BoxLayout.Y_AXIS));

		JButton btnGenerateOlapQuery = new JButton("Generate OLAP Query");
		btnGenerateOlapQuery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String dataset1Name = comboBoxDataset.getSelectedItem().toString().trim();
				String dataset2Name = comboBoxDataset_1.getSelectedItem().toString().trim();

				if ((methods.checkString(dataset1Name) || methods.checkString(dataset2Name)) && (definition.getSelectedMeasureFunctionMap().size() > 0 || definition.getSelectedMeasureFunctionMap1().size() > 0)) {
					dialog = methods.getProgressDialog(getParent());

					EventQueue.invokeLater(new Runnable() {
						@Override
						public void run() {
							if((methods.checkString(dataset1Name) && methods.checkString(dataset2Name))) {
								String queryString = definition.generateOlapQueryForTwoCube(dataset1Name, dataset2Name, bannedLevelsFromCube1, bannedLevelsFromCube2);
								textAreaQuery.setText(queryString);
								// definition.runSparqlQuery(queryString);
								tabbedPane.setSelectedIndex(1);
								dialog.dispose();
							} else if (methods.checkString(dataset1Name)) {
								String queryString = definition.generateOlapQuery(dataset1Name, bannedLevelsFromCube1);
								textAreaQuery.setText(queryString);
								// definition.runSparqlQuery(queryString);
								tabbedPane.setSelectedIndex(1);
								dialog.dispose();
							} else {
								String queryString = definition.generateOlapQuery(dataset2Name, bannedLevelsFromCube2);
								textAreaQuery.setText(queryString);
								// definition.runSparqlQuery(queryString);
								tabbedPane.setSelectedIndex(1);
								dialog.dispose();
							}
						}
					});
				} else {
					methods.showDialog("You must select a dataset, measure and functions");
				}
			}
		});
		btnGenerateOlapQuery.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSummaryHolder.add(btnGenerateOlapQuery, "cell 0 1,grow");
		
		JButton btnRunQuery = new JButton("Run Query");
		btnRunQuery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				String datasetName = comboBoxDataset.getSelectedItem().toString().trim();
//
//				if (methods.checkString(datasetName) && definition.getSelectedMeasureFunctionMap().size() > 0) {
//					dialog = methods.getProgressDialog(getParent());
				String dataset1Name = comboBoxDataset.getSelectedItem().toString().trim();
				String dataset2Name = comboBoxDataset_1.getSelectedItem().toString().trim();

				if ((methods.checkString(dataset1Name) || methods.checkString(dataset2Name)) && (definition.getSelectedMeasureFunctionMap().size() > 0 || definition.getSelectedMeasureFunctionMap1().size() > 0)) {
					dialog = methods.getProgressDialog(getParent());

					EventQueue.invokeLater(new Runnable() {
						@Override
						public void run() {
							Object[][] valueArray;
							if((methods.checkString(dataset1Name) && methods.checkString(dataset2Name))) {
								String queryString = definition.generateOlapQueryForTwoCube(dataset1Name, dataset2Name, bannedLevelsFromCube1, bannedLevelsFromCube2);
								textAreaQuery.setText(queryString);
								valueArray = definition.runSparqlQuery(textAreaQuery.getText().toString().trim());
								// definition.runSparqlQuery(queryString);
								dialog.dispose();
							} else if (methods.checkString(dataset1Name)) {
								String queryString = definition.generateOlapQuery(dataset1Name, bannedLevelsFromCube1);
								textAreaQuery.setText(queryString);
								valueArray = definition.runSparqlQuery(textAreaQuery.getText().toString().trim());
								// definition.runSparqlQuery(queryString);
								dialog.dispose();
							} else {
								String queryString = definition.generateOlapQuery(dataset2Name, bannedLevelsFromCube2);
								textAreaQuery.setText(queryString);
								valueArray = definition.runSparqlQuery(textAreaQuery.getText().toString().trim());
								// definition.runSparqlQuery(queryString);
								dialog.dispose();
							}
//							String queryString = definition.generateOlapQuery(datasetName, bannedLevelsFromCube1);
//							textAreaQuery.setText(queryString);
//							// tabbedPane.setSelectedIndex(1);
//
//							Object[][] valueArray = definition.runSparqlQuery(queryString);
//
//							dialog.dispose();

//							JScrollPane scrollPaneTable = new JScrollPane();
//							scrollPaneTable.setPreferredSize(new Dimension(1200, 600));
//
//							JTable tableResult = new JTable(valueArray, definition.getSelectedColumns().toArray());
//							tableResult.setFont(new Font("Tahoma", Font.PLAIN, 14));
//							tableResult.setBackground(Color.WHITE);
//							scrollPaneTable.setViewportView(tableResult);
//
//							JOptionPane.showMessageDialog(null, scrollPaneTable, "OLAP Result",
//									JOptionPane.INFORMATION_MESSAGE);
							
							// Printing valueArray
//							for (int i = 0; i < valueArray.length; i++) {
//					            // Iterate through each column in the current row
//					            for (int j = 0; j < valueArray[i].length; j++) {
//					                // Print the value
//					                System.out.print(valueArray[i][j] + "\t");
//					            }
//					            // Move to the next line after printing each row
//					            System.out.println();
//					        }
							showResultTable(valueArray);
						}
					});
				} else {
					methods.showDialog("You must select a dataset, measure and functions");
				}
			}
		});
		btnRunQuery.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSummaryHolder.add(btnRunQuery, "flowx,cell 0 2,grow");
		
//		JButton btnVisualize = new JButton("Visualize Data");
//		btnVisualize.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//				List<String> selectedColumnsTable = new ArrayList<>(new LinkedHashSet<>(definition.getSelectedColumns()));
//				
//				String dataset1Name = comboBoxDataset.getSelectedItem().toString().trim();
//				String dataset2Name = comboBoxDataset_1.getSelectedItem().toString().trim();
//				
//				Object[][] valueArray;
//				String queryString = definition.generateOlapQueryForTwoCube(dataset1Name, dataset2Name, bannedLevelsFromCube1, bannedLevelsFromCube2);
//				textAreaQuery.setText(queryString);
//				valueArray = definition.runSparqlQuery(textAreaQuery.getText().toString().trim());
//				dialog.dispose();
//				
//				DefaultTableModel tableModel = new DefaultTableModel(
//	                    new Object[][]{valueArray},
//	                    new Object[]{selectedColumnsTable.toArray()}
//	            );
//
//		        int rowCount = extraction.valueListSize;
//				System.out.println(rowCount);
//		        for (int i = 0; i < rowCount; i++) {
//		            String category = tableModel.getValueAt(i, 0).toString();
//		            int value = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
//		            dataset.addValue(value, "Data Series", category);
//		            
//		        }
//
//		        barChart = ChartFactory.createBarChart(
//		                "Bar Chart Title",
//		                "Category Axis Label",
//		                "Value Axis Label",
//		                dataset,
//		                PlotOrientation.VERTICAL,
//		                true, true, false
//		        );
//			}
//		});
//		btnVisualize.setFont(new Font("Tahoma", Font.BOLD, 12));
//		panelSummaryHolder.add(btnVisualize, "cell 0 2,grow");

		JPanel panelResultHolder = new JPanel();
		tabbedPane.addTab("Result", null, panelResultHolder, null);
		panelResultHolder.setBackground(Color.WHITE);
		panelResultHolder.setLayout(new MigLayout("", "[grow]", "[grow][]"));

		JScrollPane scrollPaneQuery = new JScrollPane();
		panelResultHolder.add(scrollPaneQuery, "cell 0 0,grow");

		textAreaQuery = new JTextArea();
		textAreaQuery.setFont(new Font("Tahoma", Font.BOLD, 14));
		textAreaQuery.setBackground(Color.WHITE);
		scrollPaneQuery.setViewportView(textAreaQuery);

		JButton btnRunQuery_1 = new JButton("Run Query");
		btnRunQuery_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Long totalDifference = 0L;
				Long startTimeLong = Methods.getTime();

				Object[][] valueArray = definition.runSparqlQuery(textAreaQuery.getText().toString().trim());

				Long endTimeLong = Methods.getTime();
				totalDifference += endTimeLong - startTimeLong;

				JScrollPane scrollPaneTable = new JScrollPane();
				scrollPaneTable.setPreferredSize(new Dimension(1200, 600));

				showResultTable(valueArray);

//				definition.runSparqlQuery(textAreaQuery.getText().toString().trim(), true);

				String timeStringOne = "Required Time for processing: "
						+ Methods.getTimeInSeconds(totalDifference);
				System.out.println(timeStringOne);
			}
		});
		btnRunQuery_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelResultHolder.add(btnRunQuery_1, "cell 0 1,alignx center");
	}

	protected void clearAllCube1Selection() {
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Dimensions");
		treeDimensionCube1.setModel(new DefaultTreeModel(rootNode));

		DefaultMutableTreeNode rootNode2 = new DefaultMutableTreeNode("Measures");
		treeMeasureCube1.setModel(new DefaultTreeModel(rootNode2));

		treeDimensionCube1.clearSelection();
		treeMeasureCube1.clearSelection();
		lblCurrentLevelValueCube1.setText("");
		comboBoxPropertyCube1.removeAllItems();

		DefaultListModel<CheckListItem> model = new DefaultListModel<>();
		listInstanceCube1.setModel(model);

		definition.resetOlapSelection();
		refreshSelectedLevelsCube1();
		refreshSelectedMeasureCube1();

		textAreaQuery.setText("");
	}
	
	protected void clearAllCube2Selection() {
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Dimensions");
		treeDimensionCube2.setModel(new DefaultTreeModel(rootNode));

		DefaultMutableTreeNode rootNode2 = new DefaultMutableTreeNode("Measures");
		treeMeasureCube2.setModel(new DefaultTreeModel(rootNode2));

		treeDimensionCube2.clearSelection();
		treeMeasureCube2.clearSelection();
		lblCurrentLevelValueCube2.setText("");
		comboBoxPropertyCube2.removeAllItems();

		DefaultListModel<CheckListItem> model = new DefaultListModel<>();
		listInstanceCube2.setModel(model);

		definition.resetOlapSelection();
		refreshSelectedLevelsCube2();
		refreshSelectedMeasureCube2();

		textAreaQuery.setText("");
	}

	private void showResultTable(Object[][] valueArray) {
		List<String> selectedColumnsTable = new ArrayList<>(new LinkedHashSet<>(definition.getSelectedColumns()));
		
		tableResult = new JTable(valueArray, selectedColumnsTable.toArray());
        tableResult.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tableResult.setBackground(Color.WHITE);

        // Create a scroll pane and set its preferred size
        JScrollPane scrollPaneTable = new JScrollPane(tableResult);
        scrollPaneTable.setPreferredSize(new Dimension(1200, 600));

        // Add a table header
        JTableHeader tableHeader = tableResult.getTableHeader();
        tableHeader.setFont(new Font("Tahoma", Font.BOLD, 14));
        tableHeader.setBackground(Color.LIGHT_GRAY);

        // Create a panel for better layout control
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add the scroll pane to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPaneTable, gbc);

        // Display the table in a dialog
        JOptionPane.showMessageDialog(
                null,
                panel,
                "OLAP Result",
                JOptionPane.INFORMATION_MESSAGE
        );
	}
	private void refreshSelectedMeasureCube1() {
		
		panelMeasureHolderCube1.removeAll();

		for (String measureString : definition.getSelectedMeasureFunctionMap().keySet()) {
			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panelMeasureHolderCube1.add(panel);
			panel.setLayout(new MigLayout("", "[grow][]", "[][]"));

			String datasetName = comboBoxDataset.getSelectedItem().toString().trim();
			String measureStringContainsDatasetName = datasetName + ":" + measureString;
			JLabel lblSelectedLevel = new JLabel(measureStringContainsDatasetName);
			lblSelectedLevel.setFont(new Font("Tahoma", Font.BOLD, 12));
			panel.add(lblSelectedLevel, "cell 0 0,grow");

			JButton btnRemove = new JButton("Remove");
			btnRemove.setMargin(new Insets(10, 10, 10, 10));
			btnRemove.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					definition.getSelectedMeasureFunctionMap().remove(measureString);
					refreshSelectedMeasureCube1();

					DefaultListModel<CheckListItem> model = new DefaultListModel<>();
					listInstanceCube1.setModel(model);
				}
			});
			btnRemove.setFont(new Font("Tahoma", Font.BOLD, 12));
			panel.add(btnRemove, "cell 1 0 1 2,aligny center");

			String filterText = "<function>";

			ArrayList<String> functionList = definition.getSelectedMeasureFunctionMap().get(measureString);
			String functionString = methods.convertArrayListToString(functionList);
			if (methods.checkString(functionString)) {
				filterText = "Aggregated function(s): " + functionString;
			}
			JLabel lblFilterCondition = new JLabel(filterText);
			lblFilterCondition.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			panel.add(lblFilterCondition, "cell 0 1,grow");
		}

		panelMeasureHolderCube1.repaint();
		panelMeasureHolderCube1.revalidate();
	}
	
private void refreshSelectedMeasureCube2() {
		
		panelMeasureHolderCube2.removeAll();

		for (String measureString : definition.getSelectedMeasureFunctionMap().keySet()) {
			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panelMeasureHolderCube2.add(panel);
			panel.setLayout(new MigLayout("", "[grow][]", "[][]"));

			String datasetName = comboBoxDataset_1.getSelectedItem().toString().trim();
			String measureStringContainsDatasetName = datasetName + ":" + measureString;
			JLabel lblSelectedLevel = new JLabel(measureStringContainsDatasetName);
			lblSelectedLevel.setFont(new Font("Tahoma", Font.BOLD, 12));
			panel.add(lblSelectedLevel, "cell 0 0,grow");

			JButton btnRemove = new JButton("Remove");
			btnRemove.setMargin(new Insets(10, 10, 10, 10));
			btnRemove.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					definition.getSelectedMeasureFunctionMap().remove(measureString);
					refreshSelectedMeasureCube2();

					DefaultListModel<CheckListItem> model = new DefaultListModel<>();
					listInstanceCube1.setModel(model);
				}
			});
			btnRemove.setFont(new Font("Tahoma", Font.BOLD, 12));
			panel.add(btnRemove, "cell 1 0 1 2,aligny center");

			String filterText = "<function>";

			ArrayList<String> functionList = definition.getSelectedMeasureFunctionMap().get(measureString);
			String functionString = methods.convertArrayListToString(functionList);
			if (methods.checkString(functionString)) {
				filterText = "Aggregated function(s): " + functionString;
			}
			JLabel lblFilterCondition = new JLabel(filterText);
			lblFilterCondition.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			panel.add(lblFilterCondition, "cell 0 1,grow");
		}

		panelMeasureHolderCube2.repaint();
		panelMeasureHolderCube2.revalidate();
	}

	protected void copyInstancesForCube1() {
		
		if (isEnabled) {
			String selectedLevelDt1 = lblCurrentLevelValueCube1.getText().toString().trim();
			String selectedProperty = comboBoxPropertyCube1.getSelectedItem().toString();
			String selectedCondition = comboBoxFilterConditionCube1.getSelectedItem().toString();
			TwinValue twinValue = definition.getOlapConditionalHashMap().get(selectedCondition);
			selectedCondition = twinValue.getFirstValue();

			LinkedHashMap<String, SelectedLevelInstance> propertyMap = new LinkedHashMap<>();
			if (definition.getInstancesMap().containsKey(selectedLevelDt1)) {
				propertyMap = definition.getInstancesMap().get(selectedLevelDt1);
			}

			SelectedLevelInstance selectedLevelInstance = new SelectedLevelInstance(selectedCondition);

			ListModel<CheckListItem> listModel = listInstanceCube1.getModel();
			for (int i = 0; i < listModel.getSize(); i++) {
				CheckListItem checkListItem = listModel.getElementAt(i);

				if (checkListItem.isSelected()) {
					if (!selectedLevelInstance.containInstance(checkListItem.getValue())) {
						selectedLevelInstance.addInstance(checkListItem.getValue());
					}
				}
			}

			propertyMap.put(selectedProperty, selectedLevelInstance);
			definition.getInstancesMap().put(selectedLevelDt1, propertyMap);

			updateFilterConditionForCube1();
		}
	}
	
	protected void copyInstancesForCube2() {
		
		if (isEnabled) {
			String selectedLevelDt2 = lblCurrentLevelValueCube2.getText().toString().trim();
			String selectedProperty = comboBoxPropertyCube2.getSelectedItem().toString();
			String selectedCondition = comboBoxFilterConditionCube2.getSelectedItem().toString();
			TwinValue twinValue = definition.getOlapConditionalHashMap().get(selectedCondition);
			selectedCondition = twinValue.getFirstValue();

			LinkedHashMap<String, SelectedLevelInstance> propertyMap = new LinkedHashMap<>();
			if (definition.getInstancesMap().containsKey(selectedLevelDt2)) {
				propertyMap = definition.getInstancesMap().get(selectedLevelDt2);
			}

			SelectedLevelInstance selectedLevelInstance = new SelectedLevelInstance(selectedCondition);

			ListModel<CheckListItem> listModel = listInstanceCube1.getModel();
			for (int i = 0; i < listModel.getSize(); i++) {
				CheckListItem checkListItem = listModel.getElementAt(i);

				if (checkListItem.isSelected()) {
					if (!selectedLevelInstance.containInstance(checkListItem.getValue())) {
						selectedLevelInstance.addInstance(checkListItem.getValue());
					}
				}
			}

			propertyMap.put(selectedProperty, selectedLevelInstance);
			definition.getInstancesMap().put(selectedLevelDt2, propertyMap);

			updateFilterConditionForCube2();
		}
	}

	private void updateFilterConditionForCube1() {
		String selectedLevelDt1 = lblCurrentLevelValueCube1.getText().toString().trim();
		String filterConditionString = "";
		if (definition.getInstancesMap().containsKey(selectedLevelDt1)) {
			LinkedHashMap<String, SelectedLevelInstance> propertyMap = definition.getInstancesMap().get(selectedLevelDt1);

			for (String propertyString : propertyMap.keySet()) {
				SelectedLevelInstance selectedLevelInstance = propertyMap.get(propertyString);
				if (selectedLevelInstance.getInstances().size() > 0) {
					filterConditionString += propertyString + ": ";

					String instanceString = "";
					String condition = "";
					if (selectedLevelInstance.getFilterCondition() != "no") {
						condition = selectedLevelInstance.getFilterCondition();
					}

					for (int i = 0; i < selectedLevelInstance.getInstances().size(); i++) {
						String instance = selectedLevelInstance.getInstances().get(i).toString();

						instanceString += condition + " " + instance;
						instanceString = instanceString.trim();

						if (i < selectedLevelInstance.getInstances().size() - 1) {
							instanceString += ",";
						}
					}

					filterConditionString += instanceString + "\n";
				}
			}
		}

		for (int i = 0; i < definition.getSelectedLevelList().size(); i++) {
			SelectedLevel level = definition.getSelectedLevelList().get(i);

			if (level.match(selectedLevelDt1)) {
				level.setFilterCondition(filterConditionString);
				definition.getSelectedLevelList().set(i, level);
				break;
			}
		}

		refreshSelectedLevelsCube1();
	}
	
	private void updateFilterConditionForCube2() {
		

		String selectedLevelDt2 = lblCurrentLevelValueCube2.getText().toString().trim();
		String filterConditionString = "";
		if (definition.getInstancesMap().containsKey(selectedLevelDt2)) {
			LinkedHashMap<String, SelectedLevelInstance> propertyMap = definition.getInstancesMap().get(selectedLevelDt2);

			for (String propertyString : propertyMap.keySet()) {
				SelectedLevelInstance selectedLevelInstance = propertyMap.get(propertyString);
				if (selectedLevelInstance.getInstances().size() > 0) {
					filterConditionString += propertyString + ": ";

					String instanceString = "";
					String condition = "";
					if (selectedLevelInstance.getFilterCondition() != "no") {
						condition = selectedLevelInstance.getFilterCondition();
					}

					for (int i = 0; i < selectedLevelInstance.getInstances().size(); i++) {
						String instance = selectedLevelInstance.getInstances().get(i).toString();

						instanceString += condition + " " + instance;
						instanceString = instanceString.trim();

						if (i < selectedLevelInstance.getInstances().size() - 1) {
							instanceString += ",";
						}
					}

					filterConditionString += instanceString + "\n";
				}
			}
		}

		for (int i = 0; i < definition.getSelectedLevelList1().size(); i++) {
			SelectedLevel level = definition.getSelectedLevelList1().get(i);

			if (level.match(selectedLevelDt2)) {
				level.setFilterCondition(filterConditionString);
				definition.getSelectedLevelList1().set(i, level);
				break;
			}
		}

		refreshSelectedLevelsCube2();
	}

protected void refreshSelectedLevelsCube1() {
		
		panelLevelHolderCube1.removeAll();

		for (SelectedLevel selectedLevelDt1 : definition.getSelectedLevelList()) {
			JPanel panel = new JPanel();
			panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel.setBackground(Color.WHITE);
			panelLevelHolderCube1.add(panel);
			panel.setLayout(new MigLayout("", "[grow][]", "[][][]"));

			String selectedLevelContainsDatasetName = selectedLevelDt1.getDatasetName() + selectedLevelDt1.getLevelName();
			JLabel lblSelectedLevel = new JLabel(selectedLevelContainsDatasetName);
			lblSelectedLevel.setFont(new Font("Tahoma", Font.BOLD, 12));
			panel.add(lblSelectedLevel, "cell 0 0,grow");

			JButton btnRemove = new JButton("Remove");
			btnRemove.setMargin(new Insets(10, 10, 10, 10));
			btnRemove.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					definition.getSelectedLevelList().remove(selectedLevelDt1);
					// definition.getFilterPropertyMap().remove(selectedLevelDt1);
					definition.getInstancesMap().remove(selectedLevelDt1.getLevelName());
					refreshSelectedLevelsCube1();


					DefaultListModel<CheckListItem> model = new DefaultListModel<>();
					listInstanceCube1.setModel(model);
				}
			});
			btnRemove.setFont(new Font("Tahoma", Font.BOLD, 12));
			panel.add(btnRemove, "cell 1 0 1 3,alignx center,aligny center");

			String filterText = "<filter condition>";

			if (methods.checkString(selectedLevelDt1.getFilterCondition())) {
				filterText = "Filter Condition: " + selectedLevelDt1.getFilterCondition();
			}

			JLabel lblFilterCondition = new JLabel(filterText);
			lblFilterCondition.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
			panel.add(lblFilterCondition, "cell 0 1,grow");

			JLabel lblPropertiesForSelection = new JLabel("Properties to be Viewed: ");
			lblPropertiesForSelection.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
			panel.add(lblPropertiesForSelection, "cell 0 2,grow");

			String value = "Properties to be Viewed: " + methods.convertArrayListToString(selectedLevelDt1.getViewProperties());
			lblPropertiesForSelection.setText(value);
		}

		panelLevelHolderCube1.repaint();
		panelLevelHolderCube1.revalidate();
	}

	protected void refreshSelectedLevelsCube2() {
		
		panelLevelHolderCube2.removeAll();
	
		for (SelectedLevel selectLevel : definition.getSelectedLevelList1()) {
			JPanel panel = new JPanel();
			panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel.setBackground(Color.WHITE);
			panelLevelHolderCube2.add(panel);
			panel.setLayout(new MigLayout("", "[grow][]", "[][][]"));
			
			String selectedLevelContainsDatasetName = selectLevel.getDatasetName() + selectLevel.getLevelName();
			JLabel lblSelectedLevel = new JLabel(selectedLevelContainsDatasetName);
			lblSelectedLevel.setFont(new Font("Tahoma", Font.BOLD, 12));
			panel.add(lblSelectedLevel, "cell 0 0,grow");
			
			JButton btnRemove = new JButton("Remove");
			btnRemove.setMargin(new Insets(10, 10, 10, 10));
			btnRemove.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					definition.getSelectedLevelList1().remove(selectLevel);
					// definition.getFilterPropertyMap().remove(selectedLevel);
					definition.getInstancesMap().remove(selectLevel.getLevelName());
					refreshSelectedLevelsCube2();
					
					DefaultListModel<CheckListItem> model = new DefaultListModel<>();
					listInstanceCube2.setModel(model);
				}
			});
			btnRemove.setFont(new Font("Tahoma", Font.BOLD, 12));
			panel.add(btnRemove, "cell 1 0 1 3,alignx center,aligny center");
			
			String filterText = "<filter condition>";

			if (methods.checkString(selectLevel.getFilterCondition())) {
				filterText = "Filter Condition: " + selectLevel.getFilterCondition();
			}
	
			JLabel lblFilterCondition = new JLabel(filterText);
			lblFilterCondition.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
			panel.add(lblFilterCondition, "cell 0 1,grow");
	
			JLabel lblPropertiesForSelection = new JLabel("Properties to be Viewed: ");
			lblPropertiesForSelection.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
			panel.add(lblPropertiesForSelection, "cell 0 2,grow");
	
			String value = "Properties to be Viewed: " + methods.convertArrayListToString(selectLevel.getViewProperties());
			lblPropertiesForSelection.setText(value);
		}
	
		panelLevelHolderCube2.repaint();
		panelLevelHolderCube2.revalidate();
	}

	protected void refreshMeasureTreeForCube1() {
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Measures");

		for (String measureString : definition.getMeasureMap().keySet()) {
			DefaultMutableTreeNode measureNode = new DefaultMutableTreeNode(measureString);

			ArrayList<String> functionList = definition.getMeasureMap().get(measureString);

			if (functionList.size() > 0) {
				DefaultMutableTreeNode functionRootNode = new DefaultMutableTreeNode("Aggregate Functions");

				for (String functionString : functionList) {
					DefaultMutableTreeNode functionNode = new DefaultMutableTreeNode(functionString);
					functionRootNode.add(functionNode);
				}

				measureNode.add(functionRootNode);
			}

			rootNode.add(measureNode);
		}

		treeMeasureCube1.setModel(new DefaultTreeModel(rootNode));
	}
	
	protected void refreshMeasureTreeForCube2() {
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Measures");

		for (String measureString : definition.getMeasureMap().keySet()) {
			DefaultMutableTreeNode measureNode = new DefaultMutableTreeNode(measureString);

			ArrayList<String> functionList = definition.getMeasureMap().get(measureString);

			if (functionList.size() > 0) {
				DefaultMutableTreeNode functionRootNode = new DefaultMutableTreeNode("Aggregate Functions");

				for (String functionString : functionList) {
					DefaultMutableTreeNode functionNode = new DefaultMutableTreeNode(functionString);
					functionRootNode.add(functionNode);
				}

				measureNode.add(functionRootNode);
			}

			rootNode.add(measureNode);
		}

		treeMeasureCube2.setModel(new DefaultTreeModel(rootNode));
	}

	protected void refreshDimensionTreeForCube1() {
		
		bannedLevelsFromCube1 = new ArrayList<>();
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Dimensions");

		for (String dimensionString : definition.getDimHierMap().keySet()) {
			DefaultMutableTreeNode dimensionNode = new DefaultMutableTreeNode(dimensionString);

			ArrayList<String> hierList = definition.getDimHierMap().get(dimensionString);
			for (String hierarchyString : hierList) {
				DefaultMutableTreeNode hierarchyNode = new DefaultMutableTreeNode(hierarchyString);

				ArrayList<String> levelList = definition.getHierLevelMap().get(hierarchyString);

				boolean isBanned = false;
				for (String levelString : levelList) {
					DefaultMutableTreeNode levelNode = new DefaultMutableTreeNode(levelString);
					hierarchyNode.add(levelNode);

					if (isBanned) {
						if (!bannedLevelsFromCube1.contains(levelString)) {
							bannedLevelsFromCube1.add(levelString);
						}
					}


					if (definition.getAllCubeLevels().contains(levelString)) {
						isBanned = true;
					}
				}

				dimensionNode.add(hierarchyNode);
			}

			rootNode.add(dimensionNode);
		}

		treeDimensionCube1.setCellRenderer(new MyDisabledTreeRenderer(bannedLevelsFromCube1));
		treeDimensionCube1.setModel(new DefaultTreeModel(rootNode));
	}
	
	protected void refreshDimensionTreeForCube2() {
		
		bannedLevelsFromCube2 = new ArrayList<>();
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Dimensions");

		for (String dimensionString : definition.getDimHierMap().keySet()) {
			DefaultMutableTreeNode dimensionNode = new DefaultMutableTreeNode(dimensionString);

			ArrayList<String> hierList = definition.getDimHierMap().get(dimensionString);
			for (String hierarchyString : hierList) {
				DefaultMutableTreeNode hierarchyNode = new DefaultMutableTreeNode(hierarchyString);

				ArrayList<String> levelList = definition.getHierLevelMap().get(hierarchyString);

				boolean isBanned = false;
				for (String levelString : levelList) {
					DefaultMutableTreeNode levelNode = new DefaultMutableTreeNode(levelString);
					hierarchyNode.add(levelNode);

					if (isBanned) {
						if (!bannedLevelsFromCube2.contains(levelString)) {
							bannedLevelsFromCube2.add(levelString);
						}
					}



					if (definition.getAllCubeLevels().contains(levelString)) {
						isBanned = true;
					}
				}

				dimensionNode.add(hierarchyNode);
			}

			rootNode.add(dimensionNode);
		}

		treeDimensionCube2.setCellRenderer(new MyDisabledTreeRenderer(bannedLevelsFromCube2));
		treeDimensionCube2.setModel(new DefaultTreeModel(rootNode));
	}

	private class EnabledListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isEnabled = true;
        }
    }

    private class DisabledListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (((JComboBox<?>) e.getSource()).getSelectedIndex() != SELECTION_INTERVAL[0]) {
                JOptionPane.showMessageDialog(null,
                        "You can't Select that Item", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                isEnabled = false;
                comboBoxFilterConditionCube1.setSelectedIndex(0);
                comboBoxFilterConditionCube2.setSelectedIndex(0);
            } else {
                isEnabled = true;
            }
        }
    }

    protected void disableItemsInComboBox() {
        comboBoxFilterConditionCube1.removeActionListener(enabledListener);
        comboBoxFilterConditionCube1.addActionListener(disabledListener);
        comboBoxFilterConditionCube2.removeActionListener(enabledListener);
        comboBoxFilterConditionCube2.addActionListener(disabledListener);
        model.setSelectionInterval(SELECTION_INTERVAL[0], SELECTION_INTERVAL[0]);
    }

    protected void enableItemsInComboBox() {
    	comboBoxFilterConditionCube1.removeActionListener(disabledListener);
    	comboBoxFilterConditionCube1.addActionListener(enabledListener);
        comboBoxFilterConditionCube2.removeActionListener(enabledListener);
        comboBoxFilterConditionCube2.addActionListener(disabledListener);
        model.setSelectionInterval(SELECTION_INTERVAL[0], comboBoxFilterConditionCube1.getModel()
                .getSize() - 1);
    }

	private void initializeAll() {
		
		methods = new Methods();
		definition = new Definition();
	}
}
