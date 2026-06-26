# DNA K-mer Analyzer and Frequency Tracker

# Overview 

This program analyzes DNA sequences by breaking them down into substrings of four called K-mers (e.g., TACGAA would be divided into 4-mers of TACG, ACGA, and CGAA). K-mer analysis is commonly used in bioinformatics to efficiently identify and analyze various DNA samples. The program outputs the five most recurring 4-mers in a DNA sequence, which can be used to compare and classify DNA samples. 


# Development

The program uses a custom-built, alphabetically organized BST to store each unique K-mer and its count. The program allows for the removal of individual K-mers with zero, one, or two children, and retrieval of K-mer counts with an average runtime of O(log n) for both. The program keeps track of the five K-mers with the highest counts using a modified version of Java’s built-in PriorityQueue. 


# Execution

The program runs on a 50,000 bp DNA sequence hard-coded into the main method. It creates a K-mer analyzer object, then loops over the DNA sequence, extracting K-mers of size 4 and adding them to the analyzer’s BST. It then calls topFive() on the analyzer to retrieve the five most recurring 4-mers, storing them in an array of strings, which it then loops over, printing each of the five.


# Example

DNA sequence: "AGCCCTCCAGGACAGGCTGCATCAGAAGAGGCCATCAAGCACATCA"

Output: 
ATCA
CATC
AGGC
CAGG
TCAA








