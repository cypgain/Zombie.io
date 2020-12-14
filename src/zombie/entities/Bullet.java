package zombie.entities;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import simbad.sim.RangeSensorBelt;
import simbad.sim.RobotFactory;
import simbad.sim.SimpleAgent;
import zombie.ZombieGame;

public class Bullet extends Entity{

	private static int BULLET_DAMAGE = 20;
	
	private float initialRotation;
	
	private RangeSensorBelt bumpers;
	
	public Bullet(Vector3d pos, String name, Color3f color, float radius) 
	{
		super(pos, name, color, radius);
		this.setColor(this.color);
		this.radius = radius;
		
		this.bumpers = RobotFactory.addBumperBeltSensor(this, 12);
		
		this.initialRotation = (float) Player.getInstance().getRadians();
		
	}
	
	public void performBehavior()
	{
		this.setTranslationalVelocity(15);
		this.setRotation(initialRotation);
		
		if(this.collisionDetected())
		{
			ZombieGame.getInstance().getSimulator().removeAgent(this);
		}
		
		
	}
}
