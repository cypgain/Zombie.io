package zombie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import simbad.sim.Box;
import zombie.entities.pathfinding.Node;

public class ZombieMap 
{

	private List<Box> walls;
	private List<Box> spawners;
	private int[][] map;
	
	public ZombieMap(String mapName)
	{
		 this.walls = new ArrayList<>();
		 this.spawners = new ArrayList<>();
		 this.readMap(mapName);
	}
	
	private void readMap(String mapName)
	{
		InputStream is = getClass().getClassLoader().getResourceAsStream(mapName + ".zombiemap");
		
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        String line;
        try 
        {
        	line = br.readLine();
        	String[] data = line.split(" ");
        	
        	this.map = new int[Integer.parseInt(data[0])][Integer.parseInt(data[1])];
        	
        	int row = 0;
			while ((line = br.readLine()) != null) 
			{
			    line = line.replaceAll(" ", "");
			    
			    for (int column = 0; column < line.length(); column++)
			    {
			    	this.map[row][column] = Integer.parseInt("" + line.charAt(column));
			    }
			    
			    row++;
			}
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}
	}
	
	public void printMap()
	{
		for(int i = 0; i < this.map.length; i++)
		{
			for (int y = 0; y < this.map[i].length; y++)
			{
				System.out.print(this.map[i][y]);
			}
			System.out.println();
		}
	}

	public void render(ZombieEnvironment env) 
	{
		System.out.println("Rendering map");
		
		Box box;
		
		int startX = this.map.length / 2;
		int startY = this.map[0].length / 2;
		
		for (int y = 0; y < this.map.length; y++)
		{
			for (int x = 0; x < this.map[y].length; x++)
			{
				if (this.map[y][x] == 1)
				{
					box = new Box(new Vector3d(x - startX - 1, 0, y - startY + 1), new Vector3f(1, 1, 1), env);
					this.walls.add(box);
					env.add(box);	
				}
				else if (this.map[y][x] == 2)
				{
					box = new Box(new Vector3d(x - startX - 1, -0.99f, y - startY + 1), new Vector3f(1, 1, 1), env);
					box.setColor(new Color3f(0.1f, 0.1f, 0.7f));
					box.setCanBeTraversed(true);
					this.spawners.add(box);
					env.add(box);
				}
			}
		}
	}
	
	public List<Box> getWalls()
	{
		return this.walls;
	}
	
	public List<Box> getSpawners()
	{
		return this.spawners;
	}
	
	public int[][] getArrayMap()
	{
		return this.map;
	}
	
	public boolean[][] getBoolMap()
	{
		boolean[][] boolMap = new boolean[this.map.length][this.map[0].length];
		
		for (int y = 0; y < boolMap.length; y++)
		{
			for (int x = 0; x < boolMap[0].length; x++)
			{
				boolean b = this.map[y][x] == 1;
				boolMap[y][x] = !b;
			}
		}
		
		return boolMap;
	}	
	
}

