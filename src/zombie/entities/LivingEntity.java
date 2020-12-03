package zombie.entities;

import javax.vecmath.Vector3d;

public class LivingEntity extends Entity
{

	private int health;
	
	public LivingEntity(int defaultHealth, Vector3d pos, String name)
	{
		super(pos, name);
		this.health = defaultHealth;
	}
	
	public int getHealth()
	{
		return this.health;
	}
	
	public void setHealth(int health)
	{
		this.health = health;
	}
	
}
