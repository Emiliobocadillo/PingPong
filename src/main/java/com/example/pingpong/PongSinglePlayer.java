package com.example.pingpong;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class PongSinglePlayer extends Application {

    private enum UserAction{
        NONE, LEFT, RIGHT
    }

    // use this for multiplayer????
    private enum User2Action{
        NONE, LEFT, RIGHT
    }

    private static final int APP_W = 800;
    private static final int APP_H = 600;

    private static final int BALL_RADIUS = 10;
    private static final int BAT_W = 100;
    private static final int BAT_H = 20;

    private Circle ball = new Circle(BALL_RADIUS);
    private Rectangle bat = new Rectangle(BAT_W, BAT_H);

    // When true ball is going up
    private boolean ballUp = true;
    // When true ball is going left
    private boolean ballLeft = false;
    // default user action
    private UserAction action = UserAction.NONE;

    // takes care of animating the game
    private Timeline timeline = new Timeline();
    // Whether the game is running or not
    private boolean running = true;


    // create all game object and initialize them to default state
    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(APP_W, APP_H);

        bat.setTranslateX(APP_W / 2);
        bat.setTranslateY(APP_H - BAT_H);
        bat.setFill(Color.BLUE);

        // runs every 0.016 or roughly 60fps
        KeyFrame frame = new KeyFrame(Duration.seconds(0.016), event -> {
            if (!running){
                return;
            }

            switch(action){
                case LEFT:
                    if(bat.getTranslateX() - 5 >= 0)
                        bat.setTranslateX(bat.getTranslateX() - 5);
                    break;
                case RIGHT:
                    if(bat.getTranslateX() + BAT_W + 5 <= APP_W)
                        bat.setTranslateX(bat.getTranslateX() + 5);
                    break;
                case NONE:
                     break;
            }

            // if the left boolean is true then add -5 to the x coordinate of the ball, else add 5
            ball.setTranslateX(ball.getTranslateX() + (ballLeft ? -5 : 5));
            // if the up boolean is true then add -5 to the y coordinate of the ball, else add 5
            ball.setTranslateY(ball.getTranslateY() + (ballUp ? -5 : 5));


            // collision detection sides
            // ball.getTranslateX() gets the center of the ball
            if (ball.getTranslateX() - BALL_RADIUS == 0)
                ballLeft = false;
            else if (ball.getTranslateX() + BALL_RADIUS == APP_W)
                ballLeft = true;


            // collision detection with top
            if (ball.getTranslateY() - BALL_RADIUS == 0)
                ballUp = false;

            // collision detection with bat
            else if (ball.getTranslateY() + BALL_RADIUS == APP_H - BAT_H
                    && ball.getTranslateX() + BALL_RADIUS >= bat.getTranslateX()
                    && ball.getTranslateX() - BALL_RADIUS <= bat.getTranslateX() + BAT_W)
                ballUp = true;

            if (ball.getTranslateY() + BALL_RADIUS == APP_H)
                restartGame();
        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().addAll(ball,bat);
        return root;
    }

    private void restartGame(){
        stopGame();
        startGame();
    }

    private void stopGame(){
        running = false;
        timeline.stop();
    }

    private void startGame(){
        ballUp = true;
        ball.setTranslateX(APP_W / 2);
        ball.setTranslateY(APP_H / 2);
        timeline.play();
        running = true;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Scene scene = new Scene(createContent());




        // What happens when keys are pressed
        scene.setOnKeyPressed(event ->{
            switch (event.getCode()){
                case A:
                    action = UserAction.LEFT;
                    break;
                case D:
                    action = UserAction.RIGHT;
                    break;
            }
                });
        // What happens when keys are released
        scene.setOnKeyReleased(event ->{
            switch (event.getCode()){
                case A:
                    action = UserAction.NONE;
                    break;
                case D:
                    action = UserAction.NONE;
                    break;
            }
        });

        primaryStage.setTitle("Pong Single Player");
        primaryStage.setScene(scene);
        primaryStage.show();
        startGame();
    }

    public static void main(String[] args){
        launch(args);
    }

}
