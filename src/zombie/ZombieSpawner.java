package zombie;

import java.util.List;

import javax.vecmath.Vector3d;

import simbad.sim.Box;
import zombie.entities.Zombie;
import zombie.entities.pathfinding.Point;

public class ZombieSpawner implements Runnable
{
		
	private ZombieEnvironment env;
	private List<Box> spawners;
	private int totalZombiesSpawned;
	
	private int startX;
	private int startY;
	
	public ZombieSpawner(ZombieEnvironment env, List<Box> spawners)
	{
		this.env = env;
		this.spawners = spawners;
		this.totalZombiesSpawned = 0;
		
		this.startX = this.env.getMap().getArrayMap().length;
		this.startY = this.env.getMap().getArrayMap()[0].length;
	}
	
	@Override
	public void run() 
	{				
		try { Thread.sleep(1000); } catch(Exception e) { e.printStackTrace(); }
		
		while (true)
		{				
			// Si tous les zombies de la vague sont morts
			if (this.env.getZombies().size() == 0 && this.totalZombiesSpawned >= this.env.getZombiesThisRound())
			{
				this.totalZombiesSpawned = 0;
				this.env.nextRound(); // Passage à la vague suivante
				try { Thread.sleep(3000); } catch(Exception e) { e.printStackTrace(); } // Attente de 3 secondes avant le prochain spawn de zombie
			}
			// Si tous les zombies de la vague ne sont pas encore spawn
			else if (this.totalZombiesSpawned < this.env.getZombiesThisRound())
			{
				// On fait spawn un nouveau zombie sur un spawner aléatoire
				Vector3d pos = this.spawners.get((int)(Math.random() * this.spawners.size())).getPosition();
				Vector3d newPos = new Vector3d(pos.x, 0.2f, pos.z);
				this.env.spawnZombie(newPos);
				this.totalZombiesSpawned++;
			}
			
			try { Thread.sleep(1000); } catch(Exception e) { e.printStackTrace(); }
		}
	}
	
}
