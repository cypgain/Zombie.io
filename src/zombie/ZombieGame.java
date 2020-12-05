package zombie;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import simbad.gui.WorldWindow;
import simbad.sim.Simulator;
import simbad.sim.World;

public class ZombieGame extends JFrame
{
	
	private static ZombieGame instance;
	
	private ZombieEnvironment env;
    private World world;
    private Simulator simulator;
    private WorldWindow worldWindow;
    private ActionMap actionMap;
	
    public ZombieGame() 
    {
        super("Zombie.io");
     
        ZombieGame.instance = this;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setResizable(false);
        
        this.registerKeys();
        this.start();

        this.setVisible(true);
    }
    
    private void start()
    {
        this.env = new ZombieEnvironment();
        
        this.world = new World(this.env);
        this.world.changeViewPoint(World.VIEW_FROM_TOP, null);
        
        this.simulator = new Simulator(new JDesktopPane(), this.world, this.env);
        this.simulator.startSimulation();
        
        JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add("Center", this.world.getCanvas3D());

		this.add(panel);
    }
    
    private void registerKeys()
    {
    	InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    	
		// Register pressed keys
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0), Actions.FORWARD);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), Actions.BACKWARD);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), Actions.LEFT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0), Actions.RIGHT);

		// Register released keys
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, true), Actions.R_FORWARD);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), Actions.R_BACKWARD);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), Actions.R_LEFT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, true), Actions.R_RIGHT);
		
		this.actionMap = getRootPane().getActionMap();
    }
    
    public ActionMap getActionMap()
    {
    	return this.actionMap;
    }
    
    public static ZombieGame getInstance()
    {
    	return ZombieGame.instance;
    }
    
    public ZombieEnvironment getEnv()
    {
    	return this.env;
    }
    
    public static void main(String[] args)
    {
    	new ZombieGame();
    }

}
