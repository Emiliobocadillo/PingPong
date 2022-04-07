package com.example.pingpong;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BouncingBalls extends Application {

    private static final int APP_W = 800;
    private static final int APP_H = 600;
    private static final int BALL_RADIUS = 10;
    private Circle ball1 = new Circle(BALL_RADIUS);
    private Circle ball2 = new Circle(BALL_RADIUS);


    // When true ball 1 is going up
    private boolean ball1Up = true;
    // When true ball 1 is going left
    private boolean ball1Left = false;


    // When true ball 1 is going up
    private boolean ball2Up = true;
    // When true ball 1 is going left
    private boolean ball2Left = false;

    // takes care of animating the game
    private Timeline timeline = new Timeline();
    // Whether the game is running or not
    private boolean running = true;


    // create all game object and initialize them to default state
    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(APP_W, APP_H);

        // runs every 0.016 or roughly 60fps
        KeyFrame frame = new KeyFrame(Duration.seconds(0.016), event -> {
            if (!running){
                return;
            }



            // BALL 1 MOVEMENT AND COLLISION DETECTION

            // if the left boolean is true then add -5 to the x coordinate of the ball, else add 5
            ball1.setTranslateX(ball1.getTranslateX() + (ball1Left ? -5 : 5));
            // if the up boolean is true then add -5 to the y coordinate of the ball, else add 5
            ball1.setTranslateY(ball1.getTranslateY() + (ball1Up ? -5 : 5));


            // collision detection sides
            // ball.getTranslateX() gets the center of the ball
            if (ball1.getTranslateX() - BALL_RADIUS == 0)
                ball1Left = false;
            else if (ball1.getTranslateX() + BALL_RADIUS == APP_W)
                ball1Left = true;


            // collision detection with top and bottom
            if (ball1.getTranslateY() - BALL_RADIUS == 0)
                ball1Up = false;
            else if (ball1.getTranslateY() + BALL_RADIUS == APP_H)
                ball1Up = true;



            // BALL 2 MOVEMENT AND COLLISON DETECTION

            // if the left boolean is true then add -5 to the x coordinate of the ball, else add 5
            ball2.setTranslateX(ball2.getTranslateX() + (ball2Left ? -5 : 5));
            // if the up boolean is true then add -5 to the y coordinate of the ball, else add 5
            ball2.setTranslateY(ball2.getTranslateY() + (ball2Up ? -5 : 5));


            // collision detection sides
            // ball.getTranslateX() gets the center of the ball
            if (ball2.getTranslateX() - BALL_RADIUS == 0)
                ball2Left = false;
            else if (ball2.getTranslateX() + BALL_RADIUS == APP_W)
                ball2Left = true;


            // collision detection with top and bottom
            if (ball2.getTranslateY() - BALL_RADIUS == 0)
                ball2Up = false;
            else if (ball2.getTranslateY() + BALL_RADIUS == APP_H)
                ball2Up = true;



        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().addAll(ball1, ball2);
        return root;
    }

    /*private void restartGame(){
        stopGame();
        startGame();
    }*/

    private void stopGame(){
        running = false;
        timeline.stop();
    }

    private void startGame(){
        ball1Up = true;
        ball2Up = false;
        ball1.setTranslateX(APP_W / 2);
        ball1.setTranslateY(APP_H / 2);
        ball2.setTranslateX(APP_W / 4);
        ball2.setTranslateY(APP_H / 4);
        ball1.setFill(Color.BLUE);
        ball2.setFill(Color.RED);
        timeline.play();
        running = true;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Bouncing Balls");
        primaryStage.setScene(scene);
        primaryStage.show();
        startGame();
    }

    public static void main(String[] args){
        launch(args);
    }

}
