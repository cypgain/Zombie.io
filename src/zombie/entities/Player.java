package zombie.entities;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.JOptionPane;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import simbad.sim.SimpleAgent;
import simbad.sim.Simulator;
import zombie.Actions;
import zombie.ZombieEnvironment;
import zombie.ZombieGame;

public class Player extends LivingEntity
{
	private static Player instance;
	
	protected boolean isInvincible = false;
	private final int INVINCIBILITY_TIME = 3000;
	
	private double direction;
	
	
	public Player(Vector3d pos, Color3f color) 
	{
		super(100, 5, pos, "player", color, 0.4f);
		
		this.setColor(this.color);
		this.setCanBeTraversed(true);

		this.registerActions();
		
		if(instance != null)
		{
			System.out.println("erreur : il existe deja une instance de Player");
		}
		else 
		{
			instance = 	this;
		}
	}
	
	public static Player getInstance()
	{
		return Player.instance;
	}
	
	public void performBehavior() 
	{
		
		if(anOtherAgentIsVeryNear())
		{
			
			SimpleAgent sa = getVeryNearAgent();
			if(sa instanceof Zombie)//si le joueur se fait toucher par un Zombie
			{
				if(! this.isInvincible) //S'il n'est pas en mode invincible
				{
					
					this.takeDamage(10);
					if(this.getHealth() <= 0) // S'il n'a plus de vie
					{
						ZombieGame.getInstance().getSimulator().stopSimulation();
						String sMsg = "Partie terminée vous avez tenu : ";
						sMsg += ZombieGame.getInstance().getEnv().getRound()-1 + " " + (ZombieGame.getInstance().getEnv().getRound()-1 > 1 ? "manches" : "manche");
						JOptionPane.showMessageDialog(ZombieGame.getInstance(),  sMsg);
						ZombieGame.getInstance().dispatchEvent(new WindowEvent(ZombieGame.getInstance(), WindowEvent.WINDOW_CLOSING));
						
					}
					else {
						invincibility();
					}
					System.out.println("POINT(S) DE VIE : " + this.health);
				}
			}
		}
		this.direction = this.getRadians();
	}
	
	//Méthode qui crée un nouveau Thread permettant de gérer l'invincibilité du joueur
	private void invincibility()
	{
		Thread tInvincibility = new Thread(new Runnable() 
		{
			public void run() 
			{
				Player instance = Player.getInstance();
				instance.isInvincible = true;
				try 
				{
					Thread.sleep(instance.INVINCIBILITY_TIME);
				} catch (InterruptedException e) {}
				
				instance.isInvincible = false;
				
			}
		});
		tInvincibility.start();
	}
	
	public void shoot()
	{
		Point3d p = new Point3d();
		this.getCoords(p);
		
		//On crée une Bullet qui part depuis le joueur dans la direction ou regarde le joueur
		Bullet b = new Bullet(new Vector3d(p.getX(), p.getY(), p.getZ()), "Bullet", new Color3f(1f,1f,0f), 0.1f);	
		Simulator sim = ZombieGame.getInstance().getSimulator();
		sim.addAgent(b);
	}
	
	
	//Permet le déplacement du joueur
	@SuppressWarnings("serial")
	private void registerActions()
	{
		ZombieGame.getInstance();
		ActionMap actionMap = ZombieGame.getInstance().getActionMap();
		
		actionMap.put(Actions.FORWARD, new AbstractAction() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setTranslationalVelocity(speed);
			}
		});
		
		actionMap.put(Actions.R_FORWARD, new AbstractAction() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setTranslationalVelocity(0);
			}
		});
		
		actionMap.put(Actions.BACKWARD, new AbstractAction() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setTranslationalVelocity(-speed);
			}
		});
		
		actionMap.put(Actions.R_BACKWARD, new AbstractAction() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setTranslationalVelocity(0);
			}
		});
		
		actionMap.put(Actions.LEFT, new AbstractAction() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setRotationalVelocity(-speed);
			}
		});
		
		actionMap.put(Actions.R_LEFT, new AbstractAction() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setRotationalVelocity(0);
			}
		});
		
		actionMap.put(Actions.RIGHT, new AbstractAction() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setRotationalVelocity(speed);
			}
		});
		
		actionMap.put(Actions.R_RIGHT, new AbstractAction() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setRotationalVelocity(0);
			}
		});
		
		actionMap.put(Actions.SPACE, new AbstractAction() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				shoot();
			}
		});
	}
	
	
	
	
}
