/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ld;

import java.util.List;
import java.util.Map;
import java.util.Set;
import vcf.vcfTools;

/**
 *
 * @author yaozhou
 */
public class Dprime {
    public Dprime(){
        
    }
    // geno是两个SNP的值：0，1或者2，没有NA。0是vcf里面的0/0，或者0|0； 1是0/1或者是0|1，1|0； 2是1/1或者是1|1.
    //第一行是SNP1，第二行是SNP2
    public double calDprime(double[][] geno){
        double p1 = sum(geno[0])/(geno[0].length*2);
        double p2 = sum(geno[1])/(geno[1].length*2);
        double q1 = 1 - p1;
        double q2 = 1- q1;
        double p11 = p1 * q1;
        double p12 = p1 * q2;
        double p21 = p2 * q1;
        double p22 = p2 * q2;
        double D = p11*p22 - p12*p21;
        double Dmax = 0;
        if (D>0){
            Dmax = min(p12,p21);
        }else{
            Dmax = min(p11,p22);
        }
        double Dprime = D/Dmax;
        return Dprime;
    }
    
    private double sum(double[] ge){
        double sum = 0;
        for (int i = 0; i< ge.length;++i){
            sum += ge[i];
        }
        return sum;
    }
    private double min(double a,double b){
        if(a > b){
            return b;
        }else{
            return a;
        }
    }
}
