package com.Cardinal.LOTH.Gui.Panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.Cardinal.LOTH.Gui.Handle.HourCellRenderer;
import com.Cardinal.LOTH.Util.HourUtils;
import com.Cardinal.LOTH.Util.HourUtils.Hour;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.TimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;

public class TimePanel extends JPanel implements ActionListener, TimeChangeListener, ItemListener {

	private static final long serialVersionUID = 1L;

	private DatePickerSettings dateSettings;
	private TimePickerSettings timeSettings;
	private DateTimePicker dateTimePicker;
	private JComboBox<Hour> hours;
	private JButton now;
	private JLabel label;

	public TimePanel(Dimension windowSize) {
		dateSettings = new DatePickerSettings();
		timeSettings = new TimePickerSettings();
		dateSettings.setAllowEmptyDates(false);
		timeSettings.setAllowEmptyTimes(false);

		timeSettings.generatePotentialMenuTimes(
				new ArrayList<LocalTime>(IntStream.range(0, 24).mapToObj(i -> i >= 10 ? i + ":00" : "0" + i + ":00")
						.map(LocalTime::parse).collect(Collectors.toList())));

		dateTimePicker = new DateTimePicker(dateSettings, timeSettings);

		dateTimePicker.setDateTimeStrict(LocalDateTime.now());

		now = new JButton("Now");
		label = new JLabel("Please select the date and time for the hour you would like to retrieve:");
		hours = new JComboBox<Hour>(Hour.values());
		hours.setSelectedItem(HourUtils.getHour(dateTimePicker.getDateTimeStrict().toLocalTime()));

		hours.setRenderer(new HourCellRenderer());

		hours.addItemListener(this);

		dateTimePicker.getTimePicker().addTimeChangeListener(this);
		now.addActionListener(this);

		GridBagConstraints constraints = new GridBagConstraints();

		this.setLayout(new GridBagLayout());

		constraints.gridx = 0;
		constraints.gridy = 1;
		add(now, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;

		add(dateTimePicker, constraints);

		constraints.gridx = 2;
		constraints.gridy = 1;
		add(hours, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 0, (int) (windowSize.height * 0.0130208333333333), 0);
		add(label, constraints);
	}

	public LocalDateTime getTime() {
		return dateTimePicker.getDateTimeStrict();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dateTimePicker.setDateTimeStrict(LocalDateTime.now());
	}

	@Override
	public void timeChanged(TimeChangeEvent event) {
		hours.setSelectedItem(HourUtils.getHour(event.getNewTime()));
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		dateTimePicker.setDateTimeStrict(
				LocalDateTime.of(dateTimePicker.getDatePicker().getDate(), ((Hour) e.getItem()).time()));
	}
}
