package com.me.GameObjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

public class Ball {
	
	private Vector2 position;

	private int rotation;
	private int width;
	private int height;
	private double angle;
	private float speed;
	
	private Circle boundingCirc;
	
	public Ball(float x, float y, int width, int height) {
		
		this.width = width;
	    this.height = height;
	    
	    position = new Vector2(x, y);
	    
	    boundingCirc = new Circle();
	  
	    speed = 83;
	    angle = 2 * Math.PI * Math.random(); 
	     
	}
	
	public void update(float delta) {
		
		boundingCirc.set(position.x + 10/3, position.y + 10/3, 10/3);
		
		position.x += Math.cos(angle) * speed * delta;
		position.y += Math.sin(angle) * speed * delta;
		

    }
	
	public boolean collides(Piece piece) {
		
		if(Intersector.overlaps(piece.getBoundingCirc(), boundingCirc)) {
			piece.setRadius(9001);//over 9000!!!!
			angle = angle - 180;
			return true;
		}
		
		return false;
        	
    }
	
	public boolean collides2(Ball ball) {
		
		if(Intersector.overlaps(ball.getBoundingCirc(), boundingCirc)) {
				double tempAng;
				tempAng = angle;
				angle = ball.getAngle();
				ball.setAngle(tempAng);
			return true;
		}
		
		return false;
    	
}

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }
    
    public void setX(float x) {
        position.x = x;
    }

    public void setY(float y) {
        position.y = y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }
    
    public Circle getBoundingCirc() {
		return boundingCirc;
	}
	
	public void setAngle(double a) {
		angle = a;
	}
	
	public Double getAngle() {
		return angle;
	}
	
	public void setSpeed(float s) {
		speed = s;
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setWidth(int w) {
		width = w;
	}
	
	public void setHeight(int h) {
		height = h;
	}

}
