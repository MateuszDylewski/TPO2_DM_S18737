package zad1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MyWindow extends Application {

    public static Client client;

    public void startWindow(Client client){
        MyWindow.client = client;
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        ComboBox<String> languagesCodes = new ComboBox<>();
        TextField input = new TextField();
        Label output = new Label();
        output.setWrapText(true);
        output.setMaxWidth(160);
        Label translation = new Label("Tlumaczenie:");
        Label label = new Label("Polskie slowo:");
        Button send = new Button("SEND");

        languagesCodes.getItems().addAll(FXCollections.observableArrayList("EN","ES","DE"));

        label.setLayoutX(20);
        label.setLayoutY(20);
        input.setLayoutX(20);
        input.setLayoutY(40);

        languagesCodes.setLayoutX(20);
        languagesCodes.setLayoutY(80);
        send.setPrefSize(60,20);
        send.setLayoutX(110);
        send.setLayoutY(80);

        translation.setLayoutX(20);
        translation.setLayoutY(130);
        output.setLayoutX(20);
        output.setLayoutY(150);

        pane.getChildren().addAll(label, send, input, languagesCodes, translation, output);
        Scene scene = new Scene(pane, 180, 200);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        send.setOnAction(event -> {
            if(input.getText() != null && !input.getText().equals("") && languagesCodes.getValue() != null) {
                client.word = input.getText().toLowerCase();
                client.languageCode = languagesCodes.getValue();

                while(client.translatedWord == null){
                    try {
                        Thread.sleep(150);
                    } catch (Exception ex){
                        System.out.println("Gui czeka na klienta: " + ex);
                    }
                }

                output.setText(client.translatedWord.equals("null") ? "Wyraz moze nie wystepowac w slowniku, sprawdz pisownie!" : client.translatedWord);
                client.translatedWord = null;
            }
        });
    }
}
