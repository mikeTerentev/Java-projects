import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends Circle {

	double centerX;
	double centerY;
	double radius;
	double speedX;
	double speedY;
	int sgnSpeedX;
	int sgnSpeedY;
	Color color;
	Random rng;
	boolean invisible;

	public Ball(double cX, double cY, double rad, double sX, double sY, int sgnSpX, int sgnSpY, Color color) {
		super();
		centerX = cX;
		centerY = cY;
		radius = rad;
		speedX = sX;
		speedY = sY;
		sgnSpeedX = sgnSpX;
		sgnSpeedY = sgnSpY;
		this.color = color;
		rng = new Random();
		invisible = false;
		super.setFill(color);
	}

	public void move() {
		centerX += speedX * sgnSpeedX;
		centerY += speedY * sgnSpeedY;
	}

	void genSpeed(int width, int height) {
		speedX = rng.nextInt(5) + 5;
		speedY = rng.nextInt(5) + 5;
		if (centerX + radius > width) {
			sgnSpeedX *= -1;
			centerX = width - radius;
		}
		if (centerX - radius < 0) {
			sgnSpeedX *= -1;
			centerX = radius;
		}
		if (centerY - radius < 0) {
			sgnSpeedY *= -1;
			centerY = radius;
		}
		if (centerY + radius > height) {
			sgnSpeedY *= -1;
			centerY = height - radius;
		}
	}

	void render() {
		super.setCenterX(centerX);
		super.setCenterY(centerY);
		super.setRadius(radius);
	}

	public static boolean collision(Ball a, Ball b) {
		if (a.invisible||b.invisible) return false;
		double dist = Math.sqrt(Math.pow((a.centerX - b.centerX), 2) + Math.pow((a.centerY - b.centerY), 2));
		if (dist < a.radius + b.radius)
			return true;
		else
			return false;
	}

	public static void triggerCollision(Ball a, Ball b) {
		int chance = new Random().nextInt(2);
		if (chance == 0) {
			a.invisible = true;
			a.setFill(Color.TRANSPARENT);
		} else {
			b.invisible = true;
			b.setFill(Color.TRANSPARENT);
		}
		a.sgnSpeedX *= -1;
		a.sgnSpeedY *= -1;
		b.sgnSpeedX *= -1;
		b.sgnSpeedY *= -1;
	}

	public void away(double x, double y) {
		double dist = Math.sqrt(Math.pow(x-centerX, 2)+Math.pow(x-centerX, 2));
		sgnSpeedX = (int) Math.signum(centerX-x);
		sgnSpeedY = (int) Math.signum(centerY-y);
		speedX = 1/(dist*dist);
		speedY = 1/(dist*dist);
		
	}


}
