package zombie;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.w3c.dom.events.MouseEvent;

import simbad.gui.WorldWindow;
import simbad.sim.Simulator;
import simbad.sim.World;
import zombie.entities.Player;

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
    
    /**
     * Permet de commencer la partie
     */
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
    
    /**
     * Permet d'enregistrer les touches du jeu
     */
    private void registerKeys()
    {
    	InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    	
		// Register pressed keys
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0), Actions.FORWARD);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), Actions.BACKWARD);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), Actions.LEFT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0), Actions.RIGHT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), Actions.FORWARD);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), Actions.BACKWARD);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), Actions.LEFT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), Actions.RIGHT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), Actions.SPACE);

		// Register released keys
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, true), Actions.R_FORWARD);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), Actions.R_BACKWARD);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), Actions.R_LEFT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, true), Actions.R_RIGHT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), Actions.R_FORWARD);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), Actions.R_BACKWARD);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), Actions.R_LEFT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), Actions.R_RIGHT);
		
		this.actionMap = getRootPane().getActionMap();
    }
    
    public ActionMap getActionMap()
    {
    	return this.actionMap;
    }
    
    public Simulator getSimulator()
    {
    	return this.simulator;
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
