import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PerceptronMain {

    public static void main(String[] args) {
        List<Data> ecoliData = readData();
        Perceptron perceptron = new Perceptron(ecoliData.size(),3,0.3);

        for (int e = 0; e < 10001; e++){

            double erroEpoca = 0;
            int erroClassificacaoEpoca = 0;
            for (Data data : ecoliData){
                double[] x = data.getInput();
                double[] y = data.getOutput();
                double[] o = perceptron.treinar(x,y);

                double erroAmostra = 0;//valor do somatorio
                int erroClassificacao = 0 ;
                for (int i = 0; i< y.length; i++){
                    erroAmostra += Math.abs(y[i] - o[i]);

                    if( o[i] >= 0.5){
                        erroClassificacao += Math.abs(y[i] - 1) ;
                    }else{
                        erroClassificacao +=  Math.abs(y[i] - 0);
                    }
                }
                erroClassificacaoEpoca += erroClassificacao;
                erroEpoca += erroAmostra;

            }
                System.out.println("A epoca: " + e + " - erro: " + erroEpoca + " - erro de classificação: " + erroClassificacaoEpoca);
        }
    }


    public static List<Data> readData(){
        List<Data> ecoliData = new LinkedList<>();
        String fileName = "ecoli.data";

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fileReader);

            String line;

            while((line = br.readLine()) != null){
                String[] split = line.split("  ");
                double[] input = new double[split.length - 1];
                double[] output = null;

                for (int i = 0; i < input.length; i++){

                    input[i] = Double.parseDouble(split[i]);
                    if (i == input.length - 1) {
                        output = handleOutput(split[i + 1]);
                    }
                }
                if (!Arrays.stream(input).allMatch(x -> x == 0.0) && output != null) {
                    ecoliData.add(new Data(input, output));
                }
            }

            br.close();

        } catch (IOException e){
            System.err.println("Arquivo " + fileName + " não encontrado!");
        }

        return ecoliData;
    }

    private static double[] handleOutput(String value) {
        if (value.equals(" cp"))
            return new double[]{0, 0, 0};
        else if (value.equals(" im"))
            return new double[]{0, 0, 1};

        return null;
    }

}
