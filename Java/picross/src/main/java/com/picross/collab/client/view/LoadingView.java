package com.picross.collab.client.view;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class LoadingView {
    private final VBox layout;

    public LoadingView() {
        this.layout = new VBox();
        this.layout.setAlignment(Pos.CENTER);
        this.layout.setSpacing(30);
        Label label = new Label("Attente de réponse du serveur...");
        getLayout().getChildren().add(label);


        ProgressIndicator p = new ProgressIndicator();

        p.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

        p.setScaleX(1.6); // Change this value to adjust the scale on the X axis
        p.setScaleY(1.6); // Change this value to adjust the scale on the Y axis

        // Add the ProgressIndicator to the Pane
        getLayout().getChildren().add(p);

    }

    public VBox getLayout() {
        return this.layout;
    }
}