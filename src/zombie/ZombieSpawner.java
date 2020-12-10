package zombie;

import java.util.List;

import javax.vecmath.Vector3d;

import simbad.sim.Box;

public class ZombieSpawner implements Runnable
{
		
	private ZombieEnvironment env;
	private List<Box> spawners;
	private int totalZombiesSpawned;
	
	public ZombieSpawner(ZombieEnvironment env, List<Box> spawners)
	{
		this.env = env;
		this.spawners = spawners;
		this.totalZombiesSpawned = 0;
	}
	
	@Override
	public void run() 
	{				
		try { Thread.sleep(3000); } catch(Exception e) { e.printStackTrace(); }
		
		while (true)
		{						
			if (this.env.getZombies().size() == 0 && this.totalZombiesSpawned >= this.env.getZombiesThisRound())
			{
				this.totalZombiesSpawned = 0;
				this.env.nextRound();
				try { Thread.sleep(3000); } catch(Exception e) { e.printStackTrace(); }
			}
			else if (this.totalZombiesSpawned < this.env.getZombiesThisRound())
			{
				Vector3d pos = this.spawners.get((int)(Math.random() * this.spawners.size())).getPosition();
				Vector3d newPos = new Vector3d(pos.x, 0.2f, pos.z);
				this.env.spawnZombie(newPos);
				this.totalZombiesSpawned++;
			}
			
			try { Thread.sleep(1000); } catch(Exception e) { e.printStackTrace(); }
		}
	}
	
}
