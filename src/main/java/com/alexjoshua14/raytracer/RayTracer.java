package com.alexjoshua14.raytracer;

import com.alexjoshua14.raytracer.image.Image;
import com.alexjoshua14.raytracer.image.ImageColor;
import com.alexjoshua14.raytracer.scene.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.*;
// import java.awt.Color;
import javafx.stage.Stage;
// import javafx.scene.image.Image;
import javafx.util.Duration;

import java.io.IOException;
// import java.io.IOException;
// import java.nio.file.Paths;
// import java.nio.IntBuffer;
import java.util.Arrays;

public class RayTracer extends Application {
    private static final int W = 480;//1920;
    private static final int H = 270;//1080;
    private static final int REFRESH_RATE = 200;

    private static final SceneProperties SCENE = new SceneProperties(
            new Vector3(0, 0, 2),
            new ImagePlane(
                    new Vector3(-1.92f,  1.08f, -0.5f),
                    new Vector3( 1.92f,  1.08f, -0.5f),
                    new Vector3(-1.92f, -1.08f, -0.5f),
                    new Vector3( 1.92f, -1.08f, -0.5f)
            ),
            new ScenePixelColor(0.5f, 0.5f, 0.5f),
            Arrays.asList(
                    new Light(
                            new Vector3(-5, 1, 0.5f),
                            new ScenePixelColor(0.8f, 0.8f, 0.8f),
                            new ScenePixelColor(0.8f, 0.8f, 0.8f)
                    ),
                    new Light(
                            new Vector3(5, -1, 0.5f),
                            new ScenePixelColor(0.7f, 0.7f, 0.7f),
                            new ScenePixelColor(0.8f, 0.8f, 0.8f)
                    )
            ),
            Arrays.asList(
                    new Sphere(
                            new Vector3(0, 0, -1.2f),
                            0.4f,
                            new Material(
                                    new ScenePixelColor(0.2f, 0.1f, 0.1f),
                                    new ScenePixelColor(0.4f, 0.1f, 0.1f),
                                    new ScenePixelColor(0.7f, 0.7f, 0.7f),
                                    new ScenePixelColor(0.9f, 0.5f, 0.5f),
                                    100
                            ),
                            new Vector3(0.00005f, 0, 0.000005f)
                    ),
                    new Sphere(
                            new Vector3(-1, 0, -0.8f),
                            0.2f,
                            new Material(
                                    new ScenePixelColor(0.1f, 0.2f, 0.1f),
                                    new ScenePixelColor(0.5f, 0.9f, 0.5f),
                                    new ScenePixelColor(0.7f, 0.7f, 0.7f), 
                                    new ScenePixelColor(0.3f, 0.5f, 0.2f),
                                    25
                            ),
                            new Vector3(0.00005f, 0, -0.000005f)
                    ),
                    new Sphere(
                            new Vector3(1, 0f, -0.8f),
                            0.2f,
                            new Material(
                                    new ScenePixelColor(0.1f, 0.1f, 0.2f),
                                    new ScenePixelColor(0.5f, 0.5f, 0.9f),
                                    new ScenePixelColor(0.7f, 0.7f, 0.7f),
                                    new ScenePixelColor(0.2f, 0.3f, 0.5f),
                                    50
                            ),
                            new Vector3(-0.0005f, 0.0001f, -0.000005f)
                    ),
                    new Sphere(
                            new Vector3(1.45f, -0.3f, -0.4f),
                            0.4f,
                            new Material(
                                    new ScenePixelColor(0.1f, 0.1f, 0.2f),
                                    new ScenePixelColor(0.5f, 0.5f, 0.9f),
                                    new ScenePixelColor(0.7f, 0.7f, 0.7f),
                                    new ScenePixelColor(0.2f, 0.3f, 0.5f),
                                    50
                            ),
                            new Vector3(-0.00005f, 0.00001f, -0.000005f)
                    ),
                    new Sphere(
                            new Vector3(0, 0.7f, -1.2f),
                            0.2f,
                            new Material(
                                    new ScenePixelColor(0.1f, 0.1f, 0.2f),
                                    new ScenePixelColor(0.9f, 0.5f, 0.5f),
                                    new ScenePixelColor(0.7f, 0.7f, 0.7f),
                                    new ScenePixelColor(0.5f, 0.2f, 0.3f),
                                    50
                            ),
                            new Vector3(0f, 0.00005f, -0.000005f)
                    ),
                    new Sphere(
                            new Vector3(0, -0.7f, -0.8f),
                            0.2f,
                            new Material(
                                    new ScenePixelColor(0.1f, 0.1f, 0.2f),
                                    new ScenePixelColor(0.9f, 0.5f, 0.9f),
                                    new ScenePixelColor(0.7f, 0.7f, 0.7f),
                                    new ScenePixelColor(0.5f, 0.2f, 0.5f),
                                    50
                            ),
                            new Vector3(0.00005f, 0.00001f, -0.00005f)
                    ),
                    new Sphere(
                            new Vector3(-2.4f, -.6f, -4f),
                            1f,
                            new Material(
                                    new ScenePixelColor(0.1f, 0.1f, 0.2f),
                                    new ScenePixelColor(0.9f, 0.5f, 0.9f),
                                    new ScenePixelColor(0.7f, 0.7f, 0.7f),
                                    new ScenePixelColor(0.5f, 0.2f, 0.5f),
                                    50
                            ),
                            new Vector3(-0.00005f, 0.000009f, 0.00005f)
                    )
            )
    );


    private Scene renderCurrentScene(com.alexjoshua14.raytracer.tracer.RayTracer tracer) {
        try (com.alexjoshua14.raytracer.image.Image bufferedImage = new Image(W, H)) {
                for (int x = 0; x < W; x++) {
                        for (int y = 0; y < H; y++) {
                                ScenePixelColor scenePixelColor = tracer.tracedValueAtPixel(x, y);
                                bufferedImage.plotPixel(x, y, colorToImageColor(scenePixelColor));
                        }
                }

                javafx.scene.image.Image imageToDisplay = SwingFXUtils.toFXImage(bufferedImage.getImage(), null);
                ImageView imageView = new ImageView(imageToDisplay);
                StackPane root = new StackPane();
                root.getChildren().add(imageView);
                
                Scene s = new Scene(root, W, H);
                return s;
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return null;
    }

    private void updateScene(com.alexjoshua14.raytracer.tracer.RayTracer tracer, long timejump) {
        tracer.move(timejump);
    }

    private Scene getNextScene(com.alexjoshua14.raytracer.tracer.RayTracer tracer) {
        updateScene(tracer, REFRESH_RATE);
        Scene nextScene = renderCurrentScene(tracer);
        return nextScene;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        com.alexjoshua14.raytracer.tracer.RayTracer tracer =
        new com.alexjoshua14.raytracer.tracer.RayTracer(SCENE, W, H);

        primaryStage.show();
        Scene intialScene = renderCurrentScene(tracer);
        primaryStage.setScene(intialScene);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(REFRESH_RATE), event -> {
                Scene nextScene = getNextScene(tracer);
                if (nextScene != null) primaryStage.setScene(nextScene);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
                
                // updateScene(tracer, 1);
                // Thread.sleep(3000);
                //break;
        //}
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static ImageColor colorToImageColor(ScenePixelColor scenePixelColor) {
        // Color color = new Color(
        //         scenePixelColor.getR(),
        //         scenePixelColor.getG(),
        //         scenePixelColor.getB()
        // );
        

        // return color.getRGB();
        return new ImageColor(
                (int) (scenePixelColor.getR() * ImageColor.MAX),
                (int) (scenePixelColor.getG() * ImageColor.MAX),
                (int) (scenePixelColor.getB() * ImageColor.MAX)
        );
    }
}
