package zombie;

import javax.vecmath.Vector3d;

import simbad.sim.EnvironmentDescription;
import zombie.entities.Player;

public class ZombieEnvironment extends EnvironmentDescription
{
	
	private Player player;
	
	public ZombieEnvironment()
	{
		this.showAxis(false);
		
		this.player = new Player(new Vector3d(0, 0, 0));
		
		this.add(this.player);
	}

}
