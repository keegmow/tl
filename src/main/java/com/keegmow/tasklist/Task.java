package com.keegmow.tasklist;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//
//import org.hibernate.annotations.Table;

//@Entity
//@Table(appliesTo = "tasks")
public class Task {

//	@Id @GeneratedValue @Column(name = "id")
	private int id;
	
//	@Column(name="active")
	private boolean active;
	
//	@Column(name="taskDesc")
	private String taskDesc;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}
	
	public String getStatus() {
		if (active)
			return "Active";
		else
			return "Complete";
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public void toggleActive() {
		if (active) {
			active = false;
		} else {
			active = true;
		}
	}
	
	
}
