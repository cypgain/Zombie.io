package zombie;

import javax.vecmath.Color3f;
import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Vector3d;

import simbad.sim.EnvironmentDescription;
import zombie.entities.Player;
import zombie.entities.Zombie;

public class ZombieEnvironment extends EnvironmentDescription
{
	
	private ZombieMap currentMap;
	private Player player;
	private List<Zombie> zombies;
	private int currentRound;
	private int zombiesThisRound;
	private static ZombieEnvironment instance;
	
	
	public ZombieEnvironment()
	{
        this.setUsePhysics(false);
		this.showAxis(false);
		
		this.currentMap = new ZombieMap("map");
		this.currentMap.render(this);
		
		this.player = new Player(new Vector3d(0, 0.2f, 0), new Color3f(0.9f,0.9f,0.9f));
		this.add(this.player);
		
		this.zombies = new ArrayList<>();
		this.currentRound = 1;
		this.zombiesThisRound = 1;
		
		ZombieSpawner spawner = new ZombieSpawner(this, this.currentMap.getSpawners());
		
		instance = this;
		new Thread(spawner).start();
		
	}
	public static ZombieEnvironment getInstance()
	{
		return instance;
	}
	public void spawnZombie(Vector3d position)
	{
		Zombie zombie = new Zombie(position, this.player);
		this.zombies.add(zombie);
		ZombieGame.getInstance().getSimulator().addAgent(zombie);
	}
	
	public void nextRound()
	{
		currentRound++;
		zombiesThisRound += 2;
	}
	
	public int getZombiesThisRound()
	{
		return this.zombiesThisRound;
	}
	
	public ZombieMap getMap()
	{
		return this.currentMap;
	}

	public List<Zombie> getZombies()
	{
		return this.zombies;
	}
	
	public void removeZombie(Zombie z)
	{
		this.zombies.remove(z);
	}
	
}
