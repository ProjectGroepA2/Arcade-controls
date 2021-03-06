package control.button;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import main.Window;
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
		buttons.add(new Button(1, 2, led));
		buttons.add(new Button(2, 1, led));
		buttons.add(new Button(3, 0, led));
		buttons.add(new Button(4, 3, led));
		buttons.add(new Button(5, 4, led));
		buttons.add(new Button(6, 5, led));
		
		for(Button b : buttons)
		{
			b.setColor(new Color((int)(Math.random()*254+1),(int)(Math.random()*254+1),(int)(Math.random()*254+1)));
		}
		System.out.println(Window.ON_RASP);
		if (Window.ON_RASP)
			addGpioListeners();
		
	}
	
	private void addGpioListeners(){
		ArrayList<GpioPinDigitalInput> inputpins = new ArrayList<GpioPinDigitalInput>();
        final GpioController gpio = GpioFactory.getInstance();
        
        inputpins.add(gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, "1")); //button 1 to 6 + start button
        inputpins.add(gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, "2"));
        inputpins.add(gpio.provisionDigitalInputPin(RaspiPin.GPIO_13, "3"));
        inputpins.add(gpio.provisionDigitalInputPin(RaspiPin.GPIO_14, "4"));
        inputpins.add(gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, "5"));
        inputpins.add(gpio.provisionDigitalInputPin(RaspiPin.GPIO_12, "6"));
        inputpins.add(gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, "0"));

        
        for(GpioPinDigitalInput p:inputpins){
        	  p.addListener(new GpioPinListenerDigital() {
                  @Override
                  public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent e) {
                	  if(e.getState() == PinState.HIGH){
                		  buttonRelease(buttons.get(Integer.parseInt(e.getPin().getName())));
                		  System.out.println(e.getPin().getName() + " Released");
                	  }else{
                		  buttonPress(buttons.get(Integer.parseInt(e.getPin().getName())));
                		  System.out.println(e.getPin().getName() + " Pressed");
                	  }
                  }                  
              });
        }
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
		case KeyEvent.VK_ESCAPE:
			if(!Window.ON_RASP)
				System.exit(0);
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
