package zombie.entities;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class LivingEntity extends Entity
{

	protected Color3f color;
	protected int health;
	protected int speed;
	
	
	
	public LivingEntity(int defaultHealth, int speed, Vector3d pos, String name, Color3f color)
	{
		super(pos, name);
		this.health = defaultHealth;
		this.speed = speed;
		this.color = color;
	}
	
	public int getHealth()
	{
		return this.health;
	}
	
	public void setHealth(int health)
	{
		this.health = health;
	}
	
	public void takeDamage(int damage)
	{
		this.setColor(new Color3f(1f, 0f, 0f));
		this.setHealth(health-damage);
		this.setColor(color);
	}
	
	
	
}
