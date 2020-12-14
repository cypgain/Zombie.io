package zombie.entities;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import simbad.sim.RangeSensorBelt;
import simbad.sim.RobotFactory;
import simbad.sim.SimpleAgent;
import zombie.ZombieEnvironment;
import zombie.ZombieGame;


public class Zombie extends LivingEntity 
{
	private RangeSensorBelt sonars;
	private RangeSensorBelt bumpers;
	
	private final float ZOMBIE_WALKING_SPEED = 0.2f;
	private final float ZOMBIE_RUNNING_SPEED = 1f;
	
	private Player target;
	
	private Point3d playerPos;
	private Point3d zombiePos;
	
	private float redColor = 0.0f;
	private float greenColor = 0.25f;
	private float blueColor = 1f;
	
	
	private ZombieGame game;

	public Zombie(Vector3d pos, Player target) 
	{
		super(100, 5, pos, "zombie", new Color3f(0.0f, 0.25f, 1f), 0.25f);
		sonars = RobotFactory.addSonarBeltSensor(this, 12);
		bumpers = RobotFactory.addBumperBeltSensor(this, 12);
		this.target = target;
		
		this.playerPos = new Point3d();
		this.zombiePos = new Point3d();
		
		this.setColor(color);
		
		game = ZombieGame.getInstance();
		
		this.setCanBeTraversed(false);
	}
	
	
	public void performBehavior() 
	{
		setRotationalVelocity(0);
		target.getCoords(playerPos);
		this.getCoords(zombiePos);
		
		setTranslationalVelocity(ZOMBIE_WALKING_SPEED);
		
		if (sonars.oneHasHit())
		{
			double left = sonars.getFrontLeftQuadrantMeasurement();
			double right = sonars.getFrontRightQuadrantMeasurement();
			double front = sonars.getFrontQuadrantMeasurement();
			
			if ((front > 0.1)||(left > 0.1)||(right > 0.1)) 
			{
				if (left < right)
					setRotationalVelocity(-1);
				else
					setRotationalVelocity(1) ;
			}
		}
		
		if(anOtherAgentIsVeryNear())
		{
			SimpleAgent sa = getVeryNearAgent();
			if(sa instanceof Bullet)
			{
				if(this.health <= 20)
				{
					ZombieGame.getInstance().getSimulator().removeAgent(this);
				}
				else
				{
					this.health -= 20;
					this.redColor += 0.1f;
					this.greenColor -= 0.05f;
					this.blueColor -= 0.2f;
					
					this.color = new Color3f(redColor, greenColor, blueColor);
					this.setColor(color);
					
				}
				
				ZombieGame.getInstance().getSimulator().removeAgent(sa);
				ZombieEnvironment.getInstance().removeZombie(this);
			}
		}
	}
	

	
}
