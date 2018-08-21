# LDdecay
Calculate the r-square using our java program and plot the LD decay in R

### Usage:
##### 1. calculate the R-square using our java program
      java -jar LDdecay.jar --file test.vcf.gz --out test.cor.txt --windowSize 100
###### --file: input file, should be bgzip compressed file and indexed with tabix;
###### --out: output file, two columns (distance and mean r-square)
###### --windowSize: (kb), default 100;
##### 2. plot the figures using R:
      data = read.table("test.cor.txt",head =T)
      plot(data[,1:2],type="l",col="blue",main="LD decay",xlab="Distance(Kb)",xlim=c(0,1000),ylab=expression(r^{2}),bty="n")
