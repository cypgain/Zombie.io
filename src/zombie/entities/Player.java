package zombie.entities;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import simbad.sim.SimpleAgent;
import zombie.Actions;
import zombie.ZombieGame;

public class Player extends LivingEntity
{
	private static Player instance;
	
	protected boolean isInvincible = false;
	private final int INVINCIBILITY_TIME = 3000;
	
	public Player(Vector3d pos, Color3f color) 
	{
		super(100, 5, pos, "player", new Color3f(0f, 0.5f, 0.1f));
		
		this.setColor(this.color);
		this.setCanBeTraversed(true);

		this.registerActions();
		
		if(instance != null)
		{
			System.out.println("erreur : il existe deja une instance de Player");
		}else {
			instance = 	this;
		}
	}
	
	public static Player getInstance()
	{
		return Player.instance;
	}
	
	public void performBehavior() {
		if(anOtherAgentIsVeryNear())
		{
			
			SimpleAgent sa = getVeryNearAgent();
			if(sa instanceof Zombie)
			{
				if(! this.isInvincible)
				{
					this.takeDamage(10);
					invincibility();
				}

				System.out.println("POINT DE VIE : " + this.health);
			}
		}
	}
	
	private void invincibility()
	{
		Thread tInvincibility = new Thread(new Runnable() {
			public void run() {
				
				System.out.println("START INvincible");
				Player instance = Player.getInstance();
				instance.isInvincible = true;
				try {
					Thread.sleep(instance.INVINCIBILITY_TIME);
				} catch (InterruptedException e) {}
				
				instance.isInvincible = false;
				
			}
		});
		tInvincibility.start();
	}
	
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
	}
	
	
	
	
}
