import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AkinatorGUI extends Application {

    private simpleneuralnetwork neuralNetwork;
    private int currentQuestionIndex = 0;

    // Las preguntas que definimos anteriormente
    private final String[] questions = {
        "¿Es un ser humano?", 
        "¿Es un personaje famoso?", 
        "¿Está vivo?", 
        "¿Es un actor o actriz?", 
        "¿Es un personaje de película?"
    };

    // Los personajes que estamos adivinando
    private final String[] characters = {"Pikachu", "Goku", "Elsa"};
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Crear la red neuronal
        neuralNetwork = new simpleneuralnetwork(5, 5, 3); // 5 entradas, 5 neuronas ocultas, 3 salidas (Pikachu, Goku, Elsa)

        // Crear los controles de la interfaz
        Label questionLabel = new Label("Pregunta: " + questions[currentQuestionIndex]);
        Button yesButton = new Button("Sí");
        Button noButton = new Button("No");
        TextArea resultTextArea = new TextArea(); // Mostrar las predicciones
        resultTextArea.setEditable(false); // Hacer que no se pueda editar directamente
        Button finishButton = new Button("Terminar");
        finishButton.setVisible(false); // Inicialmente, el botón "Terminar" está oculto

        // Configurar el layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(questionLabel, yesButton, noButton, resultTextArea, finishButton);

        // Crear la escena y asignarla a la ventana
        Scene scene = new Scene(layout, 300, 350);
        primaryStage.setTitle("Akinator Simple");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Lógica de los botones "Sí" y "No"
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleAnswer(1.0, questionLabel, resultTextArea, yesButton, noButton, finishButton); // 1 para sí
            }
        });

        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleAnswer(0.0, questionLabel, resultTextArea, yesButton, noButton, finishButton); // 0 para no
            }
        });

        finishButton.setOnAction(event -> primaryStage.close()); // Cerrar la ventana al presionar "Terminar"
    }

    // Manejar las respuestas del usuario
    private void handleAnswer(double userAnswer, Label questionLabel, TextArea resultTextArea, Button yesButton, Button noButton, Button finishButton) {
        // Crear una entrada de 5 valores
        double[] input = new double[5];
        for (int i = 0; i < currentQuestionIndex; i++) {
            input[i] = 0.0;  // Rellenar con ceros
        }
        input[currentQuestionIndex] = userAnswer;  // Establecer la respuesta de la pregunta actual

        // Determinar la salida esperada (target) basado en el personaje
        double[] target = new double[3];  // Tres personajes
        if (currentQuestionIndex == 0) {
            // Redefinir las salidas dinámicamente según las respuestas
            if (userAnswer == 1.0) {
                target = new double[]{1.0, 0.0, 0.0}; // Si la respuesta es sí, Pikachu
            } else {
                target = new double[]{0.0, 1.0, 0.0}; // Si la respuesta es no, Goku
            }
        } else {
            target = new double[]{0.0, 1.0, 0.0}; // Se puede añadir más lógica según las respuestas anteriores
        }

        // Entrenar la red neuronal
        neuralNetwork.train(new double[][]{input}, new double[][]{target}, 10000, 0.1); // Aumentar las iteraciones

        // Hacer una predicción con la misma entrada
        double[] prediction = neuralNetwork.predict(input);

        // Borrar el contenido previo del cuadro de texto
        resultTextArea.clear();

        // Mostrar solo la predicción con 5 decimales
        String resultText = "Predicción:\n";
        resultText += characters[0] + ": " + String.format("%.5f", prediction[0]) + "\n";
        resultText += characters[1] + ": " + String.format("%.5f", prediction[1]) + "\n";
        resultText += characters[2] + ": " + String.format("%.5f", prediction[2]) + "\n";

        // Mostrar el resultado en el TextArea
        resultTextArea.setText(resultText);

        // Avanzar a la siguiente pregunta
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.length) {
            questionLabel.setText("Pregunta: " + questions[currentQuestionIndex]);
        } else {
            questionLabel.setText("¡Juego terminado!");
            resultTextArea.appendText("Gracias por jugar.\n");

            // Mostrar el botón "Terminar" y ocultar los botones "Sí" y "No"
            yesButton.setVisible(false);
            noButton.setVisible(false);
            finishButton.setVisible(true);
        }
    }
}
