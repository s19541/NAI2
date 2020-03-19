import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Perceptron {
    List<Iris> train_set;
    List<Double> w;
    double t;
    double a;
    public Perceptron(double a,String trainSetFname){
        this.a=a;
        this.train_set=loadSetFromFile(trainSetFname);
        w=study("Iris-setosa");
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
        int tmp=0;
        int accuracyCount1=0;
        int accuracyCount2=0;
        int setosaCount=0;
        for(Iris iris:testSet){
            tmp++;
            System.out.println(tmp+"."+checkIris(iris));
            if(iris.name.equals("Iris-setosa"))
                setosaCount++;
            if(checkIris(iris).equals(iris.name)&&iris.name.equals("Iris-setosa"))
                accuracyCount1++;
            else if(checkIris(iris).equals(iris.name))
                accuracyCount2++;
        }
        System.out.println("Accuracy for Iris-Setosa:"+accuracyCount1+"/"+setosaCount);
        System.out.println("Accuracy for Iris-Versicolor:"+accuracyCount2+"/"+(testSet.size()-setosaCount));
        System.out.println("Overall accuracy:"+(accuracyCount1+accuracyCount2)+"/"+testSet.size());
    }
    public List<Double> study(String name){
        List<Double>w=new ArrayList<>();
        for (int i = 0; i < train_set.get(0).attributes.size() ; i++) {
            w.add(Math.random()*1);
        }
        double t=0;
        int y=0;
        int d=0;
        Collections.shuffle( train_set ) ;
        for(Iris iris:train_set){
            if(iris.name.equals(name)&&calcY(w,iris.attributes)>=t){
                y=1;
                d=1;
            }
            else if(iris.name.equals(name)&&calcY(w,iris.attributes)<t){
                y=0;
                d=1;
            }
            else if(!iris.name.equals(name)&&calcY(w,iris.attributes)>=t){
                y=1;
                d=0;
            }
            else if(!iris.name.equals(name)&&calcY(w,iris.attributes)<t){
                y=0;
                d=0;
            }
            w=calc(w,iris.attributes,y,d);
            t+=(d-y)*a*-1;
        }
        this.t=t;
        return w;
    }
    public List<Double>calc(List<Double>w,List<Double>x,int y,int d){
        List<Double>retw=new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            retw.add(w.get(i)+(d-y)*a*x.get(i));
        }
        return retw;
    }
    public double calcY(List<Double>w,List<Double>x){
        double tmp=0;
        for (int i = 0; i < x.size(); i++) {
            tmp+=x.get(i)*w.get(i);
        }
        return tmp;
    }
    public String checkIris(Iris iris){
        double tmp=calcY(w,iris.attributes);
        if(tmp>t)
            return "Iris-setosa";
        else
            return "Iris-versicolor";
    }

}
