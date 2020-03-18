import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Perceptron {
    List<Iris> train_set;
    List<Double> wS;
    double tS;
    List<Double>wV;
    double tV;
    double a;
    public Perceptron(double a,String trainSetFname){
        this.a=a;
        this.train_set=loadSetFromFile(trainSetFname);
        wS=study("Iris-setosa");
        wV=study("Iris-versicolor");
    }
    private List<Iris> loadSetFromFile(String setFname){
        List<Iris> set=new ArrayList<>();
        try{
            BufferedReader bf=new BufferedReader(new FileReader(setFname));
            String line="";
            while((line=bf.readLine())!=null){
                StringTokenizer tokenizer=new StringTokenizer(line,",");
                String token="";
                List<Double>attr=new ArrayList<>();
                while(tokenizer.hasMoreTokens()){
                    token=tokenizer.nextToken();
                    if(!token.startsWith("Iris")) {
                        attr.add(Double.parseDouble(token));
                    }
                    else
                        set.add(new Iris(attr,token));
                }
            }
            bf.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return set;
    }
    public void checkTestFile(String testFileName){
        List<Iris>testSet=loadSetFromFile(testFileName);
        for(Iris iris:testSet){
            System.out.println(iris+" "+checkIris(iris));
        }
    }
    public List<Double> study(String name){
        List<Double>w=new ArrayList<>();
        double t=0;
        Iris tmp=null;
        for(Iris iris:train_set){
            if(iris.name.equals(name)&&tmp==null){
                w=iris.attributes;
                tmp=iris;
            }else if(iris.name.equals(name)){
                for (int i = 0; i < iris.attributes.size(); i++) {
                    t+=iris.attributes.get(i)*tmp.attributes.get(i);
                }
            }
        }
        for(Iris iris:train_set){
            if(iris.name==name){
                w=calc(w,iris.attributes,1);
            }
            else {
                w = calc(w, iris.attributes, 0);
                t=t+a*-1;
            }
        }
        if(name=="Iris-setosa")
            tS=t;
        else
            tV=t;
        return w;
    }
    public List<Double>calc(List<Double>w,List<Double>x,int y){
        List<Double>retw=new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            retw.add(w.get(i)+(1-y)*a*x.get(i));
        }
        return retw;
    }
    public boolean checkIris(Iris iris){
        double t;
        List<Double>w;
        if(iris.name=="Iris-setosa") {
            t = tS;
            w = wS;
        }
        else {
            t = tV;
            w = wV;
        }
        double tmp=0;
        if(w.size()!=iris.attributes.size())
            System.out.println("podano zla ilosc argumentow");
        else
        for (int i = 0; i < iris.attributes.size(); i++) {
            tmp+=iris.attributes.get(i)*wV.get(i);
        }
        System.out.println(tmp+"   "+t);
        if(tmp>t)
            return true;
        return false;
    }

}
