package agh.ics.oop.view;

import agh.ics.oop.model.WorldElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class WorldElementBox extends VBox {

    private static final Map<String, Image> imageCache = new HashMap<>();
    private final VBox container;

    public WorldElementBox(WorldElement element, String positionLabel) {

        String resourceName = element.getResourceName();
        Image image = imageCache.computeIfAbsent(resourceName, res ->
                new Image(getClass().getResourceAsStream("/" + res))
        );
        //Image image = new Image(getClass().getResourceAsStream("/" + element.getResourceName()));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        Label label = new Label(positionLabel);

        container = new VBox();
        container.getChildren().addAll(imageView, label);
        container.setAlignment(Pos.CENTER);
    }

    public VBox getContainer() {
        return container;
    }
}
