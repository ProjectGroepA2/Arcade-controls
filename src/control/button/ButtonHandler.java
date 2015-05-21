package control.button;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import control.LedHandler;

public class ButtonHandler implements KeyListener{

	List<ButtonListener> listeners;
	static List<Button> buttons;
	LedHandler led;
	
	public ButtonHandler(LedHandler led)
	{
		this.led = led;
		
		listeners = new ArrayList<ButtonListener>();
		buttons = new ArrayList<Button>();
		
		buttons.add(new Button(0, -1, led));
		buttons.add(new Button(1, 5, led)); //TODO change to real ID's;
		buttons.add(new Button(2, 4, led));
		buttons.add(new Button(3, 3, led));
		buttons.add(new Button(4, 2, led));
		buttons.add(new Button(5, 1, led));
		buttons.add(new Button(6, 0, led));
	}

	public void addButtonListener(ButtonListener toAdd) {
	    listeners.add(toAdd);
	}
	
	public void buttonPress(Button b) {
		ButtonEvent e = new ButtonEvent(b, System.currentTimeMillis());
	    for (ButtonListener bt : listeners)
	        bt.buttonPressed(e);
	}
	 
	public void buttonRelease(Button b) {
		ButtonEvent e = new ButtonEvent(b, System.currentTimeMillis());
	    for (ButtonListener bt : listeners)
	        bt.buttonReleased(e);
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_0:
			buttonPress(buttons.get(0));
			break;
		case KeyEvent.VK_1:
			buttonPress(buttons.get(1));
			break;
		case KeyEvent.VK_2:
			buttonPress(buttons.get(2));
			break;
		case KeyEvent.VK_3:
			buttonPress(buttons.get(3));
			break;
		case KeyEvent.VK_4:
			buttonPress(buttons.get(4));
			break;
		case KeyEvent.VK_5:
			buttonPress(buttons.get(5));
			break;
		case KeyEvent.VK_6:
			buttonPress(buttons.get(6));
			break;
		}
	}

	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
	
	public static List<Button> getButtons()
	{
		return buttons;
	}
}