package zombie.entities;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import simbad.sim.Agent;

public abstract class Entity extends Agent
{
	
	protected Color3f color;
	
	public Entity(Vector3d pos, String name, Color3f color, float radius) 
	{
		super(pos, name);
		this.color = color;
		this.radius = radius;
	}

}
