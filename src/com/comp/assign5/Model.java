//Jagdeep Matharu - 300710666

package com.comp.assign5;

public class Model {

	private int icon;
	private int title;
	
	
	private boolean isGroupHeader = false;
	
	public Model(int title) {
		this(-1,title);
		isGroupHeader = true;
	}
	public Model(int icon, int title) {
		super();
		this.icon = icon;
		this.title = title;
		
	}

	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public int getTitle() {
		return title;
	}
	public void setTitle(int title) {
		this.title = title;
	}
	
	public boolean isGroupHeader() {
		return isGroupHeader;
	}
	public void setGroupHeader(boolean isGroupHeader) {
		this.isGroupHeader = isGroupHeader;
	}
	
	
	
}