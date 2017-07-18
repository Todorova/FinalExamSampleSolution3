package edu.pragmatic.exam.model;


public class Dog {
	
	public static final int FIELDS_COUNT = 7;
	
	public enum State {
		LOST,
		FOUND
	}
	private int id;
	private String date;
	private String color;
	private String breed;
	private boolean isMale;
	private State state;
	private String name;
	private String dateCreated;
	
//	public Dog() {}
//	public Dog (String date, String color,String breed, String isMale, String state, String name, String dateCreated ) {
//	       this.date = date;
//	        this.name = name;
//	        this.breed = breed;
//	        this.color = color;
//	        this.isMale = isMale.equals("M") ? true : false;
//	        this.state = state.equals("Lost") ? State.LOST: State.FOUND;
//	        this.dateCreated = dateCreated;
//
//	    }
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return this.id;
	}
	public boolean setFields(String[] elements) {
		
		if(elements.length != FIELDS_COUNT) {
			return false;
		}
		
		setDate(elements[0]);
		setColor(elements[1]);
		setBreed(elements[2]);
		setMale(elements[3].equals("M"));
		setState(elements[4].equals("Lost") ? State.LOST : State.FOUND);
		setName(elements[5]);
		setDateCreated(elements[6]);
		
		return true;
		
	}
	
	public String[] getFields() {
		String[] fields = new String[FIELDS_COUNT];
		
		fields[0] = getDate();
		fields[1] = getColor();
		fields[2] = getBreed();
		fields[3] = isMale() ? "M" : "F";
		fields[4] = (getState() == State.LOST ? "Lost" : "Found");
		fields[5] = getName();
		fields[6] = getDateCreated();
		
		return fields;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	public boolean isMale() {
		return isMale;
	}
	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	
	
}
