package com.Cardinal.LOTH.Gui.Components.Buttons;

import javax.swing.JButton;

import com.github.lgooddatepicker.components.DateTimePicker;

public class FinishButton extends JButton {

	private static final long serialVersionUID = 1L;
	private DateTimePicker picker;

	public FinishButton(String string, DateTimePicker picker) {
		super(string);
		this.picker = picker;
	}

	public DateTimePicker getPicker() {
		return picker;
	}

}
