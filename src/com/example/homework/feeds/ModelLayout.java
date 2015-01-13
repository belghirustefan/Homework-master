package com.example.homework.feeds;

import com.example.homework.base.BaseModel;

public class ModelLayout extends BaseModel {

	private String interest;
	private String type;
	private String action;
	private String description; 
	private String label;
	private String color;

	public ModelLayout() {

	}

	public ModelLayout(String interest, String type, String action,
			String description, String label, String color) {
		super();
		this.interest = interest;
		this.type = type;
		this.action = action;
		this.description = description;
		this.label = label;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
