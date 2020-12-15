package zombie.entities;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import simbad.sim.RangeSensorBelt;
import simbad.sim.RobotFactory;
import simbad.sim.SimpleAgent;
import zombie.ZombieEnvironment;
import zombie.ZombieGame;
import zombie.ZombieMap;
import zombie.entities.pathfinding.Grid;
import zombie.entities.pathfinding.Node;
import zombie.entities.pathfinding.PathFinding;
import zombie.entities.pathfinding.Point;


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
		this.game = ZombieGame.getInstance();
	}
	
	
	//Méthode qui retourne le prochain point où doit aller le Zombie (indiqué par l'algorithme de PathFinding)
	public Point getNextPoint()
	{		
		boolean[][] map = this.game.getEnv().getMap().getBoolMap();
		
		Vector3f zombiePos = this.getPositionInGrid();
		Vector3f playerPos = this.target.getPositionInGrid();
		
		Grid grid = new Grid(map.length, map[0].length, map);
		
		int pys = (int)zombiePos.z >= map.length ? map.length - 1 : (int)zombiePos.z < 0 ? 0 : (int)zombiePos.z;
		int pxs = (int)zombiePos.x >= map[0].length ? map[0].length - 1 : (int)zombiePos.x < 0 ? 0 : (int)zombiePos.x;
		
		int pyt = (int)playerPos.z >= map.length ? map.length - 1 : (int)playerPos.z < 0 ? 0 : (int)playerPos.z;
		int pxt = (int)playerPos.x >= map[0].length ? map[0].length - 1 : (int)playerPos.x < 0 ? 0 : (int)playerPos.x;
		
		
		Point start  = new Point(pxs, pys);
		Point target = new Point(pxt, pyt);
		
		//System.out.println("Width: " + map.length + " Height: " + map[0].length);

		
		List<Point> path = PathFinding.findPath(grid, start, target, false);
		
		int[][] initArray = this.game.getEnv().getMap().getArrayMap();
		int[][] testPath  = new int[initArray.length][initArray[0].length];
		
		for(int lig = 0; lig < initArray.length; lig++)
		{
			for(int col = 0; col < initArray[0].length; col++)
			{
				testPath[lig][col] = initArray[lig][col];
			}
		}
			
		for (Point point : path)
		{
			int py = point.y >= testPath.length ? testPath.length - 1 : point.y < 0 ? 0 : point.y;
			int px = point.x >= testPath[0].length ? testPath[0].length - 1 : point.x < 0 ? 0 : point.x;
			//System.out.println(point);
			testPath[py][px] = 5;
		}
		
		testPath[start.y][start.x] = 8;
		testPath[target.y][target.x] = 9;
		
		if (path.size() == 0)
			return null;
		else
			return path.get(0);
	}
	
	
	public void performBehavior() 
	{
		
		Point p = getNextPoint();
		if(p != null) //On déplace le zombie vers le point indiqué
		{
			//System.out.println("point : " + p);
			
			Vector3f zombiePos = this.getPositionInGrid();
			Point zombiePoint  = new Point((int)zombiePos.x, (int)zombiePos.z);
			//System.out.println("zombiePoint : " + zombiePoint +  "   |   point : " + p);
			
			if(p.x > zombiePoint.x)
				this.setRotation(0f);
			else if(p.x < zombiePoint.x)
				this.setRotation((float) Math.PI);
			else
			{
				if(p.y > zombiePoint.y)
					this.setRotation(-(float)(Math.PI/2.0f));	
				else
					this.setRotation((float)(Math.PI/2.0f));
			}
			
			setTranslationalVelocity(ZOMBIE_RUNNING_SPEED);
		}
		
		if(anOtherAgentIsVeryNear()) //Si le zombie est touché par une Bullet
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
