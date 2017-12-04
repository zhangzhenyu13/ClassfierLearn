package DataPre;
import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class loadFile {
	static public Instances loadArffFile(String fileName){
		Instances data=null;
		System.out.print("loading data from file...");
		ArffLoader loader=new ArffLoader();
		try {
			loader.setSource(new File("data/"+fileName+".arff"));
			data=loader.getDataSet();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("finished");
		return data;
	}
}
