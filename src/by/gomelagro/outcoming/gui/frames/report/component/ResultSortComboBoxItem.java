package by.gomelagro.outcoming.gui.frames.report.component;

import java.util.Comparator;

import by.gomelagro.outcoming.gui.db.files.data.UnloadedInvoice;

public class ResultSortComboBoxItem {
	private String text;
	private Comparator<UnloadedInvoice> comparator;
	
	public String getText(){return this.text;}
	public Comparator<UnloadedInvoice> getComparator(){return this.comparator;}
	
	public ResultSortComboBoxItem(String text, Comparator<UnloadedInvoice> comparator){
		this.text = text;
		this.comparator = comparator;
	}
	
	public String toString(){return this.getText();}
	
}
