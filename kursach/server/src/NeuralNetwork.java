import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class NeuralNetwork {

    private double learningRate;
    private Layer[] layers;
    private UnaryOperator<Double> activation;
    private UnaryOperator<Double> derivative;
    private double l2 = 0.0000001;

    public NeuralNetwork(double learningRate, double l2, ArrayList<Integer> sizes) {
        System.out.println("NN");
        UnaryOperator<Double> sigmoid = x -> 1 / (1 + Math.exp(-x));
        UnaryOperator<Double> dsigmoid = y -> y * (1 - y);
        this.learningRate = learningRate;
        this.l2 = l2;
        this.activation = sigmoid;
        this.derivative = dsigmoid;
        layers = new Layer[sizes.size()];//количество слоев
        for (int i = 0; i < sizes.size(); i++) {
            int nextSize = 0;
            if(i < sizes.size() - 1){//если не дошли до предпоследнего слоя
                nextSize = sizes.get(i + 1);//то nextSize = следующий слой
            }
            layers[i] = new Layer(sizes.get(i), nextSize);//как linear (например входных 2 выходных 5)
            for (int j = 0; j < sizes.get(i); j++) {
                layers[i].biases[j] = Math.random() * 2.0 - 1.0;//рандомно выбираем смещение
                for (int k = 0; k < nextSize; k++) {
                    layers[i].weights[j][k] = Math.random() * 2.0 - 1.0;//рандомно подбираем веса
                    //System.out.println("weights");
                }
            }
        }
    }

    public double[] feedForward(double[] inputs) {
        System.arraycopy(inputs, 0, layers[0].neurons, 0, inputs.length);
        for (int i = 1; i < layers.length; i++)  {
            Layer l = layers[i - 1];
            Layer l1 = layers[i];
            for (int j = 0; j < l1.size; j++) {
                l1.neurons[j] = 0;
                for (int k = 0; k < l.size; k++) {
                    l1.neurons[j] += l.neurons[k] * l.weights[k][j];// + l2*l.weights[k][j];//нейрон нового слоя получает значение нейрона предыдущего слоя * вес
                }
                l1.neurons[j] += l1.biases[j];//так же не забываем про смещение bias
                l1.neurons[j] = activation.apply(l1.neurons[j]);//получаем результат с сигмоиды
            }
        }
        return layers[layers.length - 1].neurons;//возвращаем нейроны последнего слоя
    }

    public void backpropagation(double[] targets) {
        double[] errors = new double[layers[layers.length - 1].size];
        for (int i = 0; i < layers[layers.length - 1].size; i++) {
            errors[i] = targets[i] - layers[layers.length - 1].neurons[i];//считаем ошибку на последнем слое
        }
        for (int k = layers.length - 2; k >= 0; k--) {
            Layer l = layers[k];
            Layer l1 = layers[k + 1];
            double[] errorsNext = new double[l.size];
            double[] gradients = new double[l1.size];
            for (int i = 0; i < l1.size; i++) {
                gradients[i] = errors[i] * derivative.apply(layers[k + 1].neurons[i]);
                gradients[i] *= learningRate;//считаем градиенты
            }
            double[][] deltas = new double[l1.size][l.size];
            for (int i = 0; i < l1.size; i++) {
                for (int j = 0; j < l.size; j++) {
                    deltas[i][j] = gradients[i] * l.neurons[j];//считаем новые значения нейронов
                }
            }
            for (int i = 0; i < l.size; i++) {
                errorsNext[i] = 0;
                for (int j = 0; j < l1.size; j++) {
                    errorsNext[i] += l.weights[i][j] * errors[j] + l2*l.weights[i][j];//считаем следующую ошибку
                }
            }
            errors = new double[l.size];
            System.arraycopy(errorsNext, 0, errors, 0, l.size);
            double[][] weightsNew = new double[l.weights.length][l.weights[0].length];
            for (int i = 0; i < l1.size; i++) {
                for (int j = 0; j < l.size; j++) {
                    weightsNew[j][i] = l.weights[j][i] + deltas[i][j];//считаем новые веса по весам которые у нас были + delta
                }
            }
            l.weights = weightsNew;
            for (int i = 0; i < l1.size; i++) {
                l1.biases[i] += gradients[i];// не забываем про смещение bias
            }
        }
    }

}