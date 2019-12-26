package hkrFX;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class HomeStage extends Stage {

//    private AnchorPane _anchorpane;
//    private Pane _pane;
    private Button[] _buttons;

    public HomeStage(){

        this.setResizable(false);
//        _anchorpane = new AnchorPane();
//        _anchorpane.prefHeight(MainFX.SCENE_HEIGHT);
//        _anchorpane.prefWidth(MainFX.SCENE_WIDTH);
//        _anchorpane.setStyle("-fx-background-color: white");
//
//        _pane = new Pane();
//        _pane.prefHeight(MainFX.SCENE_HEIGHT);
//        _pane.prefWidth(MainFX.SCENE_WIDTH);

        _buttons = createButtons(
                new String[] { "Employee", "Customer", "Admin" },
                new String[] {
                        "-fx-background-color: #3de377; -fx-background-radius: 111;",
                        "-fx-background-color: #ffb053; -fx-background-radius: 111;",
                        "-fx-background-color: #3b68ff; -fx-background-radius: 111;"
                },
                new double[] { 14, 225, 436 }
        );

        createScene();
    }

    private void createScene(){

        AnchorPane anchorPane = new AnchorPane();
        anchorPane = new AnchorPane();
        anchorPane.prefHeight(MainFX.SCENE_HEIGHT);
        anchorPane.prefWidth(MainFX.SCENE_WIDTH);
        anchorPane.setStyle("-fx-background-color: white");

        Pane pane = new Pane();
        pane.prefHeight(MainFX.SCENE_HEIGHT);
        pane.prefWidth(MainFX.SCENE_WIDTH);

        ObservableList<Node> pChilds = pane.getChildren();

        pChilds.addAll(_buttons);
        anchorPane.getChildren().add(pane);

        Scene scene = new Scene(anchorPane, MainFX.SCENE_WIDTH, MainFX.SCENE_HEIGHT);
        scene.getStylesheets().add("hkrFx/General.css");

        this.setScene(scene);
    }

    private Button[] createButtons(String[] texts, String[] styles, double[] layoutXs){

        if(texts.length != layoutXs.length || layoutXs.length != styles.length){
            System.out.println("Incorrect arguments lengths in HomeStage.createButtons");
            return null;
        }

        Button[] buttons = new Button[layoutXs.length];
        for (byte i = 0; i < buttons.length; i++){
            buttons[i] = new Button();
            buttons[i].setText(texts[i]);
            buttons[i].setLayoutX(layoutXs[i]);
            buttons[i].setLayoutY(125);
            buttons[i].setMnemonicParsing(false);
            buttons[i].setPrefHeight(150);
            buttons[i].setPrefWidth(150);
            buttons[i].setTextFill(Paint.valueOf("WHITE"));
            buttons[i].setStyle(styles[i]);
            buttons[i].setFont(new Font("Avenir Book", 25));
        }

        return buttons;
    }

    public Button getButton(String name){
        for (Button b : _buttons){
            if(MainFX.equals(b.getText().toLowerCase().toCharArray(), name.toLowerCase().toCharArray()))
                return b;
        }


        System.out.println("Unable to find button: "+ name +"");
        return null;
    }

}