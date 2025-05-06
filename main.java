import java.util.Scanner;
import java.io.File;

public class main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        simpleneuralnetwork nn = new simpleneuralnetwork(5, 4, 3);
        String modelFile = "modelo_akinator.dat";

        System.out.print("¿Quieres entrenar un nuevo modelo? (s/n): ");
        String opcion = scanner.nextLine();

        if (opcion.equalsIgnoreCase("s")) {
            System.out.println("Entrenando red neuronal...");
            nn.train(data.inputs, data.outputs, 5000, 0.1);
            nn.saveModel(modelFile);
        } else {
            File file = new File(modelFile);
            if (file.exists()) {
                nn.loadModel(modelFile);
            } else {
                System.out.println("No se encontró el modelo. Entrenando desde cero...");
                nn.train(data.inputs, data.outputs, 5000, 0.1);
                nn.saveModel(modelFile);
            }
        }

        double[] userInput = new double[5];
        String[] preguntas = {
            "¿Es un personaje de anime? (1 = sí, 0 = no): ",
            "¿Es un personaje femenino? (1 = sí, 0 = no): ",
            "¿Tiene poderes eléctricos? (1 = sí, 0 = no): ",
            "¿Vuela? (1 = sí, 0 = no): ",
            "¿Tiene cabello rubio? (1 = sí, 0 = no): "
        };

        System.out.println("\nResponde las preguntas:");
        for (int i = 0; i < preguntas.length; i++) {
            System.out.print(preguntas[i]);
            userInput[i] = scanner.nextInt();
        }

        double[] output = nn.predict(userInput);
        int indexMayor = 0;
        for (int i = 1; i < output.length; i++) {
            if (output[i] > output[indexMayor]) indexMayor = i;
        }

        System.out.println("\n🤖 Creo que estás pensando en: " + data.personajes[indexMayor]);
        for (int i = 0; i < output.length; i++) {
            System.out.printf("- %s: %.2f%%\n", data.personajes[i], output[i] * 100);
        }

        scanner.close();
    }
}
