import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Perceptron {

    private int quantIn, quantOut;
    private double [][] w;
    private double ni;

    public Perceptron(int quantIn, int quantOut, double ni){
        this.quantIn = quantIn;
        this.quantOut = quantOut;
        this.w = new double[quantIn+1][quantOut];
        matrizAleatoria();//inicializar pesos c/ valores aleatorios [-0.03,0.03]
        this.ni = ni;
    }

    public double[] treinar(double[] vetEntrada, double[] y){// passar as entradas, passar a saida desejada
        double[] vetEntradas = concVet(vetEntrada, new double[]{1});
        double[] o = new double[y.length];
        for (int j = 0; j < y.length; j++){// J para saida, i para entrada
            double u = 0;
            for (int i =0; i < vetEntradas.length; i++){
                u += (vetEntradas[i] * w[i][j]);
            }
            o[j] = (1 / (1 + Math.exp(-u)));

        }

        for (int j = 0; j< y.length; j++){
            for (int i = 0; i < vetEntradas.length; i++){
                this.w[i][j] += ni * (y[j] - o[j]) * vetEntradas[i];
            }
        }
        return o;
    }

    private double[] concVet(double[] vetUm, double[] vetDois){
        int tamanho = vetUm.length + vetDois.length;
        double[] vetAux = new double[tamanho];

        System.arraycopy(vetUm, 0, vetAux, 0, vetUm.length);
        System.arraycopy(vetDois,0, vetAux, vetUm.length, vetDois.length);

        return  vetAux;
    };

    private void matrizAleatoria(){
        //Random r = new Random();
        Random r = ThreadLocalRandom.current();
        double min = -0.03;
        double max = 0.03;

        for (int i = 0; i < quantIn + 1 ; i++){
            for (int j = 0; j < quantOut; j++){
                this.w[i][j] = min + (max - min) * r.nextDouble();
            }
        }
    };
}
