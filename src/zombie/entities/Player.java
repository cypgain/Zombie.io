package zombie.entities;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import zombie.Actions;
import zombie.ZombieGame;

public class Player extends LivingEntity
{

	private int speed;
	
	public Player(Vector3d pos, Color3f color) 
	{
		super(100, pos, "player", color);
		this.speed = 2;
		this.setColor(color);
		this.setCanBeTraversed(true);
		this.registerActions();
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
