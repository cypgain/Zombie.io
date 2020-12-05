package zombie.entities;

import javax.vecmath.Vector3d;

public class LivingEntity extends Entity
{

	protected int health;
	protected int speed;
	
	public LivingEntity(int defaultHealth, int speed, Vector3d pos, String name)
	{
		super(pos, name);
		this.health = defaultHealth;
		this.speed = speed;
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
