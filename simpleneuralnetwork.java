import java.io.*;
import java.util.Random;

public class simpleneuralnetwork implements Serializable {

    private double[][] weightsInputHidden;
    private double[][] weightsHiddenOutput;
    private double[] hiddenBias;
    private double[] outputBias;
    private transient Random random = new Random();

    public simpleneuralnetwork(int inputSize, int hiddenSize, int outputSize) {
        weightsInputHidden = new double[inputSize][hiddenSize];
        weightsHiddenOutput = new double[hiddenSize][outputSize];
        hiddenBias = new double[hiddenSize];
        outputBias = new double[outputSize];

        for (int i = 0; i < inputSize; i++)
            for (int j = 0; j < hiddenSize; j++)
                weightsInputHidden[i][j] = random.nextDouble() - 0.5;

        for (int i = 0; i < hiddenSize; i++) {
            hiddenBias[i] = random.nextDouble() - 0.5;
            for (int j = 0; j < outputSize; j++)
                weightsHiddenOutput[i][j] = random.nextDouble() - 0.5;
        }

        for (int i = 0; i < outputSize; i++)
            outputBias[i] = random.nextDouble() - 0.5;
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private double sigmoidDerivative(double x) {
        return x * (1 - x);
    }

    public double[] predict(double[] input) {
        double[] hidden = new double[hiddenBias.length];
        for (int i = 0; i < hidden.length; i++) {
            double sum = hiddenBias[i];
            for (int j = 0; j < input.length; j++)
                sum += input[j] * weightsInputHidden[j][i];
            hidden[i] = sigmoid(sum);
        }

        double[] output = new double[outputBias.length];
        for (int i = 0; i < output.length; i++) {
            double sum = outputBias[i];
            for (int j = 0; j < hidden.length; j++)
                sum += hidden[j] * weightsHiddenOutput[j][i];
            output[i] = sigmoid(sum);
        }

        return output;
    }

    public void train(double[][] inputs, double[][] targets, int epochs, double learningRate) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (int sample = 0; sample < inputs.length; sample++) {
                double[] input = inputs[sample];
                double[] target = targets[sample];

                double[] hidden = new double[hiddenBias.length];
                for (int i = 0; i < hidden.length; i++) {
                    double sum = hiddenBias[i];
                    for (int j = 0; j < input.length; j++)
                        sum += input[j] * weightsInputHidden[j][i];
                    hidden[i] = sigmoid(sum);
                }

                double[] output = new double[outputBias.length];
                for (int i = 0; i < output.length; i++) {
                    double sum = outputBias[i];
                    for (int j = 0; j < hidden.length; j++)
                        sum += hidden[j] * weightsHiddenOutput[j][i];
                    output[i] = sigmoid(sum);
                }

                double[] outputErrors = new double[output.length];
                double[] outputDeltas = new double[output.length];
                for (int i = 0; i < output.length; i++) {
                    outputErrors[i] = target[i] - output[i];
                    outputDeltas[i] = outputErrors[i] * sigmoidDerivative(output[i]);
                }

                double[] hiddenErrors = new double[hidden.length];
                double[] hiddenDeltas = new double[hidden.length];
                for (int i = 0; i < hidden.length; i++) {
                    for (int j = 0; j < output.length; j++)
                        hiddenErrors[i] += outputDeltas[j] * weightsHiddenOutput[i][j];
                    hiddenDeltas[i] = hiddenErrors[i] * sigmoidDerivative(hidden[i]);
                }

                for (int i = 0; i < hidden.length; i++)
                    for (int j = 0; j < output.length; j++)
                        weightsHiddenOutput[i][j] += learningRate * outputDeltas[j] * hidden[i];

                for (int i = 0; i < output.length; i++)
                    outputBias[i] += learningRate * outputDeltas[i];

                for (int i = 0; i < input.length; i++)
                    for (int j = 0; j < hidden.length; j++)
                        weightsInputHidden[i][j] += learningRate * hiddenDeltas[j] * input[i];

                for (int i = 0; i < hidden.length; i++)
                    hiddenBias[i] += learningRate * hiddenDeltas[i];
            }
        }
    }

    public void saveModel(String filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(weightsInputHidden);
            out.writeObject(weightsHiddenOutput);
            out.writeObject(hiddenBias);
            out.writeObject(outputBias);
            System.out.println("Modelo guardado en: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadModel(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            weightsInputHidden = (double[][]) in.readObject();
            weightsHiddenOutput = (double[][]) in.readObject();
            hiddenBias = (double[]) in.readObject();
            outputBias = (double[]) in.readObject();
            System.out.println("Modelo cargado desde: " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}