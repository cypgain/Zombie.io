package zombie.entities;

import javax.media.j3d.Transform3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import zombie.ZombieGame;
import zombie.ZombieMap;


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
		this.setHealth(health-damage); // on diminue la vie de la LivingEntity
		this.setColor(color);
	}
	

	//Methode qui retourne la position en suivant le format d'un tableau 
	//Ex : si le joueur est en haut à gauche cette méthode retourne x = 0 et z = 0
	public Vector3f getPositionInGrid()
	{
		Vector3f gridPos = new Vector3f(0f, 0f, 0f);
		Vector3f realPos = this.getTranslation();
		
		ZombieMap map = ZombieGame.getInstance().getEnv().getMap();
		
		int startX = map.getArrayMap().length / 2;
		int startY = map.getArrayMap()[0].length / 2;
		
		gridPos.x = (float) (Math.floor(realPos.x) + startX);
		gridPos.y = realPos.y;
		gridPos.z = (float) (Math.floor(realPos.z) + startY - 1);
		
		return gridPos;
	}

	
}
