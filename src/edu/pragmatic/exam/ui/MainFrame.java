package edu.pragmatic.exam.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.pragmatic.exam.model.Database;
import edu.pragmatic.exam.model.Dog;
import edu.pragmatic.exam.model.DogsTableModel;
import edu.pragmatic.exam.utils.Utils;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTable dogsTable;
	private DogsTableModel dogsTableModel;
	private JButton btnDelete;
	private List<Dog> dogs;
	private JButton btnSave;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 643, 458);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 27, 576, 178);
		contentPane.add(scrollPane);
		
		dogsTable = new JTable();
		dogsTableModel = new DogsTableModel();
		dogsTable.setModel(dogsTableModel);
		dogsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				updateDeleteButton();				
			}
		});
		scrollPane.setViewportView(dogsTable);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				loadData();				
			}
		});
		btnLoad.setBounds(30, 231, 117, 29);
		contentPane.add(btnLoad);
		
		JButton btnLoadfile = new JButton("Load file");
		btnLoadfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				JFileChooser chooser = new JFileChooser();
		        chooser.showOpenDialog(null);
		        File selectedFile = chooser.getSelectedFile();

		        try {
					List<Dog> dogs = Utils.loadFile(selectedFile.getAbsolutePath());

					dogsTableModel.setDogs(dogs);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLoadfile.setBounds(150, 231, 117, 29);
		contentPane.add(btnLoadfile);
		
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteSelectedDog();				
			}
		});
		btnDelete.setBounds(270, 231, 117, 29);
		contentPane.add(btnDelete);
		
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveDogs();
			}
		});
		btnSave.setBounds(390, 231, 117, 29);
		contentPane.add(btnSave);
		updateDeleteButton();
		
	}
	
	
	private void loadData() {
		try {
			dogs = Utils.load();
			dogsTableModel.setDogs(dogs);	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void deleteSelectedDog() {
		Connection conn;
		try {
			int selectedRowIndex = dogsTable.getSelectionModel().getMinSelectionIndex();
			
			if(selectedRowIndex != -1) {
				conn = Database.getConnection();		
				String query= "DELETE FROM Animals WHERE id = ? ";
				int idInt = dogs.get(selectedRowIndex).getId();
				System.out.println(idInt);
				PreparedStatement preparedStatement = conn.prepareStatement(query);
				preparedStatement.setInt(1,idInt);
				preparedStatement.executeUpdate();

				conn.close();
				dogs.remove(selectedRowIndex);
				dogsTableModel.setDogs(dogs);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	
	
	
	private void saveDogs() {
		try {
			Utils.save(dogs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void updateDeleteButton() {
		int selectedRowIndex = dogsTable.getSelectionModel().getMinSelectionIndex();
		btnDelete.setEnabled(selectedRowIndex != -1);
	}
}
