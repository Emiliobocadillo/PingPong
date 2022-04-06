package com.example.pingpong;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PingPong extends Application {

    //Variables
    private static final int width = 800;
    private static final int height = 600;
    private static final int player_width = 15;
    private static final int player_height = 100;
    private static final double ball_radius = 15;
    private int ballXSpeed = 1;
    private int ballYSpeed = 1;

    private double playerOneYPos = height/2;
    private double playerTwoYPos = height/2;

    private int playerOneXPos = 0;
    private double playerTwoXPos = width-player_width;

    private double ballXPos = width/2;
    private double ballYPos = height/2;
    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private boolean gameStarted;

    public void start (Stage stage) throws Exception{
        stage.setTitle("P I N G   P O N G");
        //background size
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //JavaFX Timeline = free form animation defined by KeyFrames and their duration
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e ->run(gc)));
        //number of cycles in animation INDEFINITE = repeat indefinitely
        tl.setCycleCount(Timeline.INDEFINITE);
        // Mouse Control
        canvas.setOnMouseMoved(e -> playerOneYPos = e.getY());
        canvas.setOnMouseClicked(e-> gameStarted = true);
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();
    }

    private void run (GraphicsContext gc){
        // Set graphics
        // Set background color
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0, width, height);

        // set text color
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));

        if(gameStarted){
            // set ball movement
            ballXPos+=ballXSpeed;
            ballYPos+=ballYSpeed;

            // simple computer opponent who is following the ball
            if (ballXPos < width - width/4){
                playerTwoYPos = ballYPos-(player_height/2);
            }else{
                playerTwoYPos = ballYPos > playerTwoYPos + player_height / 2 ?playerTwoYPos += 1: playerTwoYPos -1;
            }

            // Draw the ball
            gc.fillOval(ballXPos,ballYPos, ball_radius, ball_radius);

        } else {
            // set the start
            gc.setStroke(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("On Click", width/2, height/2);

            // reset the ball start position
            ballXPos = width/2;
            ballYPos = height/2;

            // reset ball speed and direction
            ballXSpeed = new Random().nextInt(2) == 0 ? 1: -1;
            ballYSpeed = new Random().nextInt(2) == 0 ? 1: -1;
        }

        // ball stays in canvas
        if (ballYPos > (height-5) || ballYPos < 5) ballYSpeed *=-1;

        // computer/player two gets a point
        if (ballXPos < playerOneXPos - player_width){
            playerTwoScore++;
            gameStarted = false;
        }

        // player one gets a point
        if (ballXPos > playerTwoXPos + player_width){
            playerOneScore++;
            gameStarted = false;
        }

        // increase the ball speed
        if ((ballXPos + ball_radius > playerTwoXPos) && (ballXPos >= playerTwoYPos && ballYPos <= playerTwoYPos + player_height)
                || (ballXPos < playerOneXPos + player_width) && (ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + player_height)){
            ballXSpeed+=1 * Math.signum(ballXSpeed);
            ballYSpeed+=1 * Math.signum(ballYSpeed);
            ballXSpeed*=-1;
            ballYSpeed*=-1;
        }

        // draw score
        gc.fillText(playerOneScore + "\t\t\t\t\t\t\t\t" + playerTwoScore, width/2, 100);


        // draw player one and two
        gc.fillRect(playerOneXPos,playerOneYPos, player_width, player_height);
        gc.fillRect(playerTwoXPos,playerTwoYPos, player_width, player_height);
    }

    public static void main(String[] args) {
        launch();
    }
}
