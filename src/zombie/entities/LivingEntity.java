package zombie.entities;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class LivingEntity extends Entity
{

	private int health;
	private Color3f color;
	
	public LivingEntity(int defaultHealth, Vector3d pos, String name, Color3f color)
	{
		super(pos, name);
		this.health = defaultHealth;
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
		this.setColor(new Color3f(255,0,0));
		this.setHealth(damage);
		this.setColor(color);
	}
	
}
