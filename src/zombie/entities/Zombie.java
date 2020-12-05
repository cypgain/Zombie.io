package zombie.entities;

import javax.media.j3d.Transform3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import simbad.sim.RangeSensorBelt;
import simbad.sim.RobotFactory;

public class Zombie extends LivingEntity
{	
    Point3d coords = new Point3d();
    Point3d prev = new Point3d();
    Transform3D t3d = new Transform3D();

    RangeSensorBelt sonars, bumpers;

    boolean stop = false;

	
	public Zombie(Vector3d pos) 
	{
		super(100, 20, pos, "zombie");
		
		this.setCanBeTraversed(true);
		
        this.bumpers = RobotFactory.addBumperBeltSensor(this, 12);
        this.sonars = RobotFactory.addSonarBeltSensor(this, 12);
        
        this.setColor(new Color3f(0.2f, 0.2f, 0.7f));
	}
	
	@Override
	public void performBehavior()
	{		
		this.setTranslationalVelocity(this.speed);
		
        if (this.bumpers.oneHasHit()) 
        {
        	this.setTranslationalVelocity(-0.1);
        	this.setRotationalVelocity(0.5 - (0.1 * Math.random()));
        } 
        else if (this.sonars.oneHasHit()) 
        {
            // reads the three front quadrants
            double left = this.sonars.getFrontLeftQuadrantMeasurement();
            double right = this.sonars.getFrontRightQuadrantMeasurement();
            double front = this.sonars.getFrontQuadrantMeasurement();
            
            // if obstacle near
            if ((front < 0.7) || (left < 0.7) || (right < 0.7)) 
            {
                if (left < right)
                	this.setRotationalVelocity(-1 - (0.1 * Math.random()));
                else
                	this.setRotationalVelocity(1 - (0.1 * Math.random()));
                this.setTranslationalVelocity(0);
            } 
            else 
            {
            	this.setRotationalVelocity(0);
            	this.setTranslationalVelocity(0.6);
            }
        } 
        else 
        {
        	this.setTranslationalVelocity(0.8);
        	this.setRotationalVelocity(0);
        }
	}

}
