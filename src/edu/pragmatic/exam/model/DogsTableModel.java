package edu.pragmatic.exam.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
public class DogsTableModel extends AbstractTableModel {

	private List<Dog> dogs;
	
	@Override
	public int getRowCount() {		
		return (dogs != null ? dogs.size() : 0);
	}

	@Override
	public int getColumnCount() {
		return Dog.FIELDS_COUNT;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Dog dog = dogs.get(rowIndex);
		switch(columnIndex) {
		case 0: return dog.getDate();
		case 1: return dog.getName();
		case 2: return dog.getBreed();
		case 3: return dog.getColor();
		case 4: return (dog.isMale() ? "Male" : "Female");
		case 5: return dog.getState();
		case 6: return dog.getDateCreated();
		}
		
		return null;
	}

	@Override
	public String getColumnName(int column) {
		switch(column) {
		case 0: return "Date";
		case 1: return "Name";
		case 2: return "Breed";
		case 3: return "Color";
		case 4: return "Sex";
		case 5: return "State";
		case 6: return "Date Created";
		default: return "";
		}
	}
	
	public void setDogs(List<Dog> dogs) {
		this.dogs = dogs;
		
		fireTableDataChanged();
	}
	
	
	
	//------
	
	
}
