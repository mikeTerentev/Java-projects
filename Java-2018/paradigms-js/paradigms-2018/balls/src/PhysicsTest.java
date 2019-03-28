import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class PhysicsTest extends Application {

	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;
	Random rand = new Random();

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Group root = new Group();
		Scene scene = new Scene(root, WIDTH, HEIGHT);

		Ball circle = new Ball(200, 100, 20, 5, 4, 1, 1, Color.BLUE);
		root.getChildren().add(circle);

		primaryStage.setScene(scene);
		primaryStage.show();

		AnimationTimer animator = new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				circle.genSpeed(WIDTH, HEIGHT);
				circle.move();
				circle.render();
			}

		};

		animator.start();
	}

}
