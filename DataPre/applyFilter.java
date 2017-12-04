package DataPre;

import java.util.Random;
import java.util.Vector;

import extSrc.MultiClassFLDA;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.*;

public class applyFilter {
	private Instances data=null;
	private void init(){
		data.setClassIndex(data.numAttributes()-1);
	}
	public applyFilter(Instances data){
		this.data=data;
		this.init();
	}
	public void LDA(){
		//weka.filters.supervised.attribute.MultiClassFLDA -R 1.0E-6
		MultiClassFLDA pca=new MultiClassFLDA();
		String[] para="-R 1.0E-6".split(" ");
		try {
			pca.setOptions(para);
			pca.setInputFormat(this.data);
			this.data=Filter.useFilter(this.data, pca);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void shrinkAttribute(){
		ASEvaluation evaluator = new CfsSubsetEval();
		ASSearch search = new BestFirst();
		AttributeSelection eval = null;
		eval = new AttributeSelection();
		eval.setEvaluator(evaluator);
		eval.setSearch(search);
		try {
			eval.SelectAttributes(this.data);
			int numOfNewAttributes = eval.numberAttributesSelected();
			System.out.println("selected num="+numOfNewAttributes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public Instances getData(){
		return data;
	}
	public void removeLabel(){
		Remove rm=new Remove();
		
		String para="last";
		try {
			rm.setAttributeIndices(para);
			rm.setInputFormat(this.data);
			this.data=Filter.useFilter(this.data, rm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void removeOutliers(){
		Instances mydata=new Instances(this.data);
		//try to remove those instances that prevent us to classify efficiently
		
	}
	public void replaceData(Instances data){
		this.data.clear();
		this.data.addAll(data);
		this.init();
	}
	public Vector<Instances> splitData(int k){//split (training)data into k parts 
		Vector<Instances> splitted=new Vector<Instances>();
		Instances instInit=new Instances(this.data);
		instInit.clear();
		Instances mydata=new Instances(this.data);
		Random rd=new Random(1237);
		int i=0,r=0,batch=(mydata.size()-1)/k+1;
		for(int j=0;j<k;j++){
			Instances inst=new Instances(instInit);
			for(i=0;i<batch&&mydata.isEmpty()==false;i++){
				r=rd.nextInt();
				if(r<0)
					r=-r;
				r=r%mydata.size();
				
				inst.add(mydata.get(r));
				mydata.remove(r);
			}
			splitted.add(inst);
		}
		return splitted;
	}
}
