/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ld;

import htsjdk.tribble.readers.TabixReader;
import io.IOUtils;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import lddecay.main;
import vcf.vcf;
import vcf.vcfTools;

/**
 *
 * @author yaozhou
 */
public class LD {
    public LD(){
        
    }
    
    /* 1.   read vcf;
    * 2. select the candidate region
    * 3. calculate LD
    * 4. output result
    *c
    */
    public void LDdecay(String inFile,String outFile,int windowSize){
        try {
            vcf a = new vcf();
            a.initialVCFread(inFile);
            BufferedWriter bw = IOUtils.getTextWriter(outFile);
            int block = windowSize/100;
            double[][] res = new double[block][2];
            for(int i = 0; i< block;i++){
                res[i][0] = (i+1)*0.1;
                res[i][1] = 0;
            }
            int chrnum = 0;
            while(!a.checkEnd()){
                a.readVCFByChr();
                System.out.println("chromosome\t"+a.getChr()+"\t Readed!");
//                if(a.getChr().equals("0")) continue;
                chrnum++;
                double[][] cor = new vcfTools().calCor(a.getPos(), a.getGeno(), windowSize);
                for(int i = 0; i < res.length;i++){
                    res[i][1] += cor[i][1];
                }
            }
            bw.write("Distance(kb)\trsquare\n");
            for(int i = 0; i < res.length;i++){
                    bw.write((String.format("%.2f", res[i][0]))+"\t" + res[i][1]/chrnum);
                    bw.newLine();
                }
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(LD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void LDdecay(String inFile,String outFile,int windowSize, Set sampleList){
        try {
            vcf a = new vcf();
            a.initialVCFread(inFile);
            BufferedWriter bw = IOUtils.getTextWriter(outFile);
            String[] samples = a.getSample();
            while(!a.checkEnd()){
                a.readVCFByChr();
                double[][] cor = new vcfTools().calCor(a.getPos(), a.getGeno(), windowSize,sampleList,samples);
                bw.write("Distance(kb)\trsquare\n");
                for(int i = 0; i < cor.length;i++){
                    bw.write((String.format("%.2f", cor[i][0]))+"\t" + cor[i][1]);
                    bw.newLine();
                }
                bw.flush();
                bw.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(LD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
