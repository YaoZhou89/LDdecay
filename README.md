# LDdecay
Calculate the r-square using our java program and plot the LD decay in R

### Usage:
      java -jar LDdecay.jar --file test.vcf.gz --out test.cor.txt --windowSize 100
###### --file: input file, should be bgzip compressed file and indexed with tabix;
###### --out: output file, two columns (distance and mean r-square)
###### --windowSize: (kb), default 100;
