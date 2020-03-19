public class Main {
    public static void main(String[] args) {
        double a=0.1;
        String trainSet="train_set.csv";
        String testSet="test_set.csv";
        Perceptron perceptron=new Perceptron(a,trainSet);
        perceptron.checkTestFile(testSet);

    }
}
