package DigitClassifier;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;

public class modelEvaluate {
	static public String evaluate(AbstractClassifier cls,Instances test){
		String summary=null;
		try {
			Evaluation eval=new Evaluation(test);
			eval.evaluateModel(cls, test);
			summary=eval.toSummaryString();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return summary;
	}
	static public float accuracy(AbstractClassifier cls,Instances test){
		float acc=-1;
		
		try {
			Evaluation eval=new Evaluation(test);
			eval.evaluateModel(cls, test);
			System.out.println(eval.toSummaryString());
			acc=(float)eval.correct()/test.size();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return acc;
	}
	
	static public String getLabel(AbstractClassifier cls,Instance instance){
		String label=null;
		double pred=-1;
		try {
			pred = cls.classifyInstance(instance);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String predict=instance.classAttribute().value((int) pred);
		label=predict;
		return label;
	}
	static public void savePredict(Vector<String>predictions){
		try {
			BufferedWriter bfw=new BufferedWriter(new FileWriter("data/predict.txt"));
			for(int i=0;i<predictions.size();i++){
				bfw.write(predictions.get(i));
				bfw.newLine();
			}
			bfw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
