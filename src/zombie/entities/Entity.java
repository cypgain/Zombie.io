package zombie.entities;

import javax.vecmath.Vector3d;

import simbad.sim.Agent;

public abstract class Entity extends Agent
{

	public Entity(Vector3d pos, String name) 
	{
		super(pos, name);
	}

}
