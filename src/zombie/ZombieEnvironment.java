package zombie;

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
	
	public ZombieEnvironment()
	{
        this.setUsePhysics(false);
		this.showAxis(false);
		
		this.currentMap = new ZombieMap("map");
		this.currentMap.render(this);
		
		this.player = new Player(new Vector3d(0, 0, 0));
		this.add(this.player);
		
		this.zombies = new ArrayList<>();
		this.currentRound = 1;
		this.zombiesThisRound = 5;
		
		ZombieSpawner spawner = new ZombieSpawner(this, this.currentMap.getSpawners());
		new Thread(spawner).start();
	}
	
	public void spawnZombie(Vector3d position)
	{
		Zombie zombie = new Zombie(position);
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

	public List<Zombie> getZombies()
	{
		return this.zombies;
	}
	
}
