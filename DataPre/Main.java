package DataPre;

import java.util.Vector;

import DigitClassifier.digitClass;
import DigitClassifier.modelEvaluate;
import weka.core.Instances;

public class Main {
	public static void main(String[] args){
		String trainfile="trainGray",testfile="testGray";
		digitClass myClassifier=new digitClass(digitClass.selectClassifier());
		applyFilter train=new applyFilter(loadFile.loadArffFile(trainfile)),
				test=new applyFilter(loadFile.loadArffFile(testfile));
		System.out.println("data Preparing...");
		//train.LDA();
		train.shrinkAttribute();
		Instances trainData=train.getData();
		Instances testData=new Instances(trainData);
		testData.clear();
		testData.addAll(test.getData());
		myClassifier.buildModel(trainData);
		System.out.println(modelEvaluate.evaluate(myClassifier.getClassifier(), testData));
		Vector<String> predictions=new Vector<>();
		for(int i=0;i<testData.size();i++)
			predictions.add(modelEvaluate.getLabel(myClassifier.getClassifier(), testData.get(i)));
		modelEvaluate.savePredict(predictions);
	}
}
