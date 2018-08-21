/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lddecay;

import io.IOUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import ld.LD;
import vcf.vcf;
import vcf.vcfTools;

/**
 *
 * @author yaozhou
 */
public class main {

    /**
     *
     * This project was written for calculating the pair-wise correlation from vcf files,
     * Using R to plot the LD decay
     * @param args the command line arguments
     * 
     * --file: input file of vcf, 
     * default, compressed vcf file with bgzip;and indexed with tabix
     * support vcf: uncompressed vcf file;
     * support plink: plink binary format (pending)
     * support bcf: bcf file format (peeding)
     * --out: outfile
     * --dist: distance for estimating LD;(kb)
     * --samples: sub-population, sample names (A001;A002)
     * --subpop: file; different subpopulations with #subpop_name and one same per line
     * --startPos: startPosition
     * --endPos: endPosition
     * --byChr: output the results by chromosome;
     * --chr: output selected chromosome;
     * 
     */
    public static void main(String[] args) {
        String inFile =  "", outFile = "", chr = "";
        String subpop = "";
        int len = args.length, windowSize = 100000, startPos = 0, endPos = 1000000000;
        boolean byChr = false;
        for (int i = 0; i < len; i++){
            if (null != args[i])switch (args[i]) {
                case "--windowSize":
                    windowSize = Integer.parseInt(args[i+1])*1000;   
                    i++;
                    break;
                case "--subPop":
                    subpop = args[i+1];
                    i++;
                    break;
                case "--file":
                    inFile = args[i+1];
                    i++;
                    break;
                case "--out":
                    outFile = args[i+1];
                    i++;
                    break;
                case "--startPos":
                    startPos = Integer.parseInt(args[i+1]);
                    i++;
                    break;
                case "--endPos":
                    endPos = Integer.parseInt(args[i+1]);
                    i++;
                    break;
                case "--chr":
                    chr = args[i+1];
                    i++;
                    break;
                case "--byChr":
                    byChr = true;
                    break;
                default:
                    break;
            }
        }
        
        if(subpop.equals("")){
            new LD().LDdecay(inFile, outFile, windowSize);
        }else if(!subpop.equals("") ){
            try {
                Set samples = new HashSet();
                String temp= "";
                BufferedReader bs = IOUtils.getTextReader(subpop);
                while ((temp = bs.readLine())!=null){
                    samples.add(temp);
                }
                System.out.println("Subpopulation size: "+samples.size());
                new LD().LDdecay(inFile, outFile, windowSize, samples);
            } catch (IOException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
//        }else if(subpop.equals("") && !subsample.equals("")){
//            String[] names = subsample.split(",");
//            Set samples = new HashSet();
//            for (int i = 0; i< names.length;i++){
//                samples.add(names[i]);
//            }
//            new LD().LDdecay(inFile, outFile, windowSize, samples);
//        }else if(!subpop.equals("") && !subsample.equals("")){
//            
        }else{
            
        }
    }
    
}
