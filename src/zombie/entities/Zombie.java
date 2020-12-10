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
	
	private ZombieGame game;

	public Zombie(Vector3d pos, Player target) 
	{
		super(100, 5, pos, "zombie", new Color3f(0.3f, 0.2f, 0.7f));
		sonars = RobotFactory.addSonarBeltSensor(this, 12);
		bumpers = RobotFactory.addBumperBeltSensor(this, 12);
		this.target = target;
		
		this.playerPos = new Point3d();
		this.zombiePos = new Point3d();
		
		this.setCanBeTraversed(true);
		this.setColor(color);
		
		this.game = ZombieGame.getInstance();
	}
	
	public Point getNextPoint()
	{		
		boolean[][] map = this.game.getEnv().getMap().getBoolMap();
		
		Vector3f zombiePos = this.getPositionInGrid();
		Vector3f playerPos = this.target.getPositionInGrid();
		
		Grid grid = new Grid(map.length, map[0].length, map);
		
		Point start  = new Point((int)zombiePos.x, (int)zombiePos.z);
		Point target = new Point((int)playerPos.x, (int)playerPos.z);
		
		//System.out.println("Width: " + map.length + " Height: " + map[0].length);
		//System.out.println("Start: " + start);
		//System.out.println("Dest: " + target);
		
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
			//System.out.println(point);
			testPath[point.y][point.x] = 5;
		}
		
		testPath[start.y][start.x] = 8;
		testPath[target.y][target.x] = 9;
		/*
		for (int y = 0; y < testPath.length; y++)
		{
			for (int x = 0; x < testPath[y].length; x++)
			{
				System.out.print(testPath[y][x]);
			}
			
			System.out.println();
		}*/
		
		if (path.size() == 0)
			return null;
		else
			return path.get(0);
	}
	
	public void performBehavior() 
	{
		
		//si x de point est plus grand faut aller à droite
		Point p = getNextPoint();
		if(p != null)
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
		
		/*
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
			if(sa instanceof Player)
			{
				LivingEntity le = (LivingEntity)sa;
				le.takeDamage(10);
			}
		}
		*/
	}

	
}
