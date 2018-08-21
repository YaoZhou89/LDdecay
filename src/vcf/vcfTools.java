/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcf;

import java.util.List;
import java.util.Map;
import java.util.Set;
import ld.Correlation;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

/**
 *
 * @author yaozhou
 */
public class vcfTools {
    public vcfTools(){
        
    }
    public double[][] calCor(List<String> ID, Map<String,String[]> genotype,int windowSize){
        int block = windowSize/100;
        Correlation cc = new Correlation();
        double[][] res =  new double[block+1][3];
        
        double[][] geno = new double[ID.size()][genotype.get(ID.get(1)).length];
        Integer[] pos = new Integer[ID.size()];
        for (int i = 0; i < ID.size() ;i++ ){
            geno[i] = asNumeric(genotype.get(ID.get(i)));
            pos[i] = Integer.parseInt(ID.get(i).split("_")[1]);
        }
        res = calCorall(ID, pos, windowSize, geno);
        return res;
    }
    public double[][] calCor(List<String> ID, Map<String,String[]> genotype,int windowSize,Set sampleList, String[] samples){
        int block = windowSize/100;
//        Correlation cc = new Correlation();
        double[][] res =  new double[block][3];
        double[][] geno = new double[ID.size()][genotype.get(ID.get(1)).length];
        Integer[] pos = new Integer[ID.size()];
        for (int i = 0; i < ID.size() ;i++ ){
            geno[i] = asNumeric(genotype.get(ID.get(i)),sampleList,samples);
            pos[i] = Integer.parseInt(ID.get(i).split("_")[1]);
        }
        res = calCorall(ID, pos, windowSize, geno);
        return res;
    }
    
    public double[] asNumeric(String[] geno){
        double[] num = new double[geno.length];
        for (int i =0; i < geno.length;i++){
            if(geno[i].startsWith("0/0")|geno[i].startsWith("0|0")){
                num[i] = 0.0;
            }else if (geno[i].startsWith("1/1")|geno[i].startsWith("1|1")){
                num[i] = 2.0;
            }else if (geno[i].startsWith("0/1")|geno[i].startsWith("0|1")|geno[i].startsWith("1|0")){
                num[i] = 1.0;
            }else{
                num[i] = Double.NaN;
            }
        }
        return num;
    }
    public double[] asNumeric(String[] geno ,Set sampleIndex, String[] samples){
        double[] num = new double[geno.length];
        for (int i =0; i < geno.length;i++){
            if(sampleIndex.add(samples[i])){
                sampleIndex.remove(samples[i]);
                num[i] = Double.NaN;
                continue;
            };
            if(geno[i].startsWith("0/0")|geno[i].startsWith("0|0")){
                num[i] = 0.0;
            }else if (geno[i].startsWith("1/1")|geno[i].startsWith("1|1")){
                num[i] = 2.0;
            }else if (geno[i].startsWith("0/1")|geno[i].startsWith("0|1")|geno[i].startsWith("1|0")){
                num[i] = 1.0;
            }else{
                num[i] = Double.NaN;
            }
        }
        return num;
    }
    private double[][] calCorall(List<String> ID,Integer[] pos,int windowSize,double[][] geno){
        int block = windowSize/100;
        Correlation cc = new Correlation();
        double[][] res =  new double[block][3];
        
        for(int i = 0; i< block;i++){
            res[i][0] = (i+1)*0.1;
            res[i][1] = 0;
            res[i][2] = 0;
        }
        for (int i = 0; i < ID.size() - 1;i++ ){
            for (int j = i+1; j < ID.size();j++){
                int size = (pos[j]-pos[i])/100;
                if(size > block){
//                    double c = cc.calCor(geno[i],geno[j]);
//                    res[block][1] = c*c;
//                    res[block][2]++;
                    break;
                }
                double c = cc.calCor(geno[i],geno[j]);
                if(Double.isNaN(c)){
                   continue;
                }
                res[size][1]  += c*c;
                
                res[size][2]++;
            }
        }
        for(int i =0; i< block;i++){
            res[i][1] = res[i][1]/res[i][2];
        }
        return res;
    }
}
