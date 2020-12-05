package zombie;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import simbad.sim.EnvironmentDescription;
import zombie.entities.Player;
import zombie.entities.Zombie;

public class ZombieEnvironment extends EnvironmentDescription
{
	
	private ZombieMap currentMap;
	private Player player;
	private Zombie zombie1;
	private Zombie zombie2;
	
	public ZombieEnvironment()
	{
		this.showAxis(false);
		
		this.currentMap = new ZombieMap("map");
		this.currentMap.render(this);
		
		this.player = new Player(new Vector3d(0, 0, 0), new Color3f(255,255,255));
		this.add(this.player);
		
		this.zombie1 = new Zombie(100, new Vector3d(2,0,2), "zombie", new Color3f(0,255,0), player);
		this.add(this.zombie1);
		
		this.zombie2 = new Zombie(100, new Vector3d(5,0,2), "zombie",  new Color3f(0,255,0), player);
		this.add(this.zombie2);
	}
	
	public ZombieMap getMap(){return this.currentMap;}

}
