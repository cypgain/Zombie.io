package zombie.entities;

import javax.media.j3d.Transform3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class LivingEntity extends Entity
{
	protected int health;
	protected int speed;
	
	
	public LivingEntity(int defaultHealth, int speed, Vector3d pos, String name, Color3f color, float radius)
	{
		super(pos, name, color, radius);
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
	
	public void takeDamage(int damage)
	{
		this.setColor(new Color3f(1f, 0f, 0f));
		this.setHealth(health-damage);
		this.setColor(color);
	}
	
	
}
