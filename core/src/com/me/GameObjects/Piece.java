package com.me.GameObjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Piece {
	
	private Vector2 position;
	private Vector2 velocity;

	private float rotation;
	private int width;
	private int height;
	private double theta;
	private double spinSpeed;
	private double r;
	private double newCirc;
	private int colorState, greenToBlack;
	
	private Circle boundingCirc;

	public Piece(float x, float y, int width, int height) {
		
		this.width = width;
	    this.height = height;
	    position = new Vector2(x, y);
	    velocity = new Vector2(0, 0);
	    boundingCirc = new Circle();
	    
	    theta = 0;
	    r = 420/3;
	    spinSpeed = 0;
	    newCirc = 0;
	    colorState = 0;
	    greenToBlack = 0;
	    
	}
	
	public void update(float delta) {
		
		boundingCirc.set(position.x + 10/3, position.y + 10/3, 11/3);
		
		theta = theta + Math.toRadians(spinSpeed);
		position.x = (float)(960/3 + r*Math.cos(theta + Math.toRadians(newCirc)));
		position.y = (float)(527/3 + r*Math.sin(theta + Math.toRadians(newCirc)));


    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
    
    public void setWidth(int w) {
        width = w;
    }

    public void setHeight(int h) {
        height = h;
    }

    public float getRotation() {
        return rotation;
    }
    
    public Circle getBoundingCirc() {
		return boundingCirc;
	}
    
    public float getVelocity() {
    	return velocity.y;
    }

	public void setVelocity(float x) {
		velocity.y = x;
	}
	
	public void setY(float y) {
		position.y = y;
	}

	public void setX(float x) {
		position.x = x;
	}
	
	public void setNewCirc(float y) {
		newCirc = y;
	}
	
	public void setRadius(float newR) {
		r = newR;
	}
	
	public double getSpinSpeed() {
		return spinSpeed;
	}
	
	public void setSpinSpeed(double s) {
		spinSpeed = s;
	}
	
	public int getColorState() {
		return colorState;
	}
	
	public void setColorState(int i) {
		colorState = i;
	}
	
	public int getGreenToBlack() {
		return greenToBlack;
	}
	
	public void setGreenToBlack(int i) {
		greenToBlack = i;
	}
	
	public double getRadius() {
		return r;
	}
}