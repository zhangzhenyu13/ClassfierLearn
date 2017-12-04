package DigitClassifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import weka.classifiers.AbstractClassifier;
import weka.core.Instances;
class parseHelper{
	
	public  static String[] parseOptions(String str){//help parse options
		//System.out.println("parse->"+str);
		String []ops=str.split(" ");
		String line="";
		Vector<String> vops=new Vector<String>();
		if(ops.length<2){
			return ops;
		}
		for(int i=0;i<ops.length;i++){
			if(ops[i].contains("\"")){
				for(int j=i+1;j<ops.length;j++){
					if(ops[j].contains("\"")){
						for(int k=i;k<j;k++)
							line+=ops[k]+" ";
						line+=ops[j];
						vops.add(line);
						line="";
						i=j;
						break;
					}
				}
			}
			else{
				vops.add(ops[i]);
			}
		}
		ops=new String[vops.size()];
		for(int i=0;i<vops.size();i++)
			ops[i]=vops.get(i);
		
		
		return ops;
	}
	public static String[] parseClassifier(String str){//split name and options
		String []result=new String[2];
		int pos=str.indexOf(" ");
		if(pos<0){
			result[0]=str;
			result[1]=null;
		}
		else{
			result[0]=str.substring(0, pos);
			result[1]=str.substring(pos+1);
		}
		return result;
	}
}

public class digitClass{
	
	private AbstractClassifier myClassifier=null;
	public digitClass(AbstractClassifier cls){
		this.myClassifier=cls;
	}
	public void buildModel(Instances train){
		long t1=System.currentTimeMillis();
		System.out.println("building "+this.myClassifier.getClass().getSimpleName()+",trainSetSize="+train.size());
		try {
			this.myClassifier.buildClassifier(train);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long t2=System.currentTimeMillis();
		System.out.println("Model building completed in "+(t2-t1)/1000+"s !");
	}
	public AbstractClassifier getClassifier(){
		return this.myClassifier;
	}
	public static AbstractClassifier selectClassifier(){
		AbstractClassifier Mycls=null;
		//Init Classifier data
		System.out.println("Init Classifier SetUp...");
		BufferedReader bfr=null;
		AbstractClassifier[]clss=null;
		try {
			bfr=new BufferedReader(new FileReader("data/classifiers.txt"));
			String line=bfr.readLine();
			int n=Integer.parseInt(line);
			clss=new AbstractClassifier[n];
			String cls="",ops="";
			System.out.println("classifierNum="+n);
			for(int i=0;i<n;i++){
				try{
					line=bfr.readLine();
					//System.out.println("#"+(i+1)+": "+line);
					String[]result=parseHelper.parseClassifier(line);
					cls=result[0];
					ops=result[1];
					Class<?> mycls=Class.forName(cls);
					clss[i]=(AbstractClassifier) mycls.newInstance();
					if(result[1]!=null){
						clss[i].setOptions(parseHelper.parseOptions(ops));
						}
				}catch(Exception e){
					System.out.println("Error load class "+e.getMessage());
				}
			}
			bfr.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("finished");
		//print Classifiers
		for(int i=0;i<clss.length;i++)
			System.out.println("Alg #"+(i+1)+": "+clss[i].getClass().getSimpleName());
		System.out.println("choose a num:");
		Scanner kin=new Scanner(System.in);
		String sel=kin.nextLine();
		int selNum=Integer.parseInt(sel);
		Mycls=clss[selNum-1];
		//return selected one
		return Mycls;
	}
	
}
