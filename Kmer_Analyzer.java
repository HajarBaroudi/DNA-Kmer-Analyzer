package kmerAnalyzer;

import java.util.*;

public class Kmer_Analyzer{
	private class Node {
		Integer count;
		Node parent;
		Node left;
		Node right;
		String kmer;
		
		public Node getParent() {return parent;}
		public Node getLeft() {return left;}
		public Node getRight() {return right;}
		public void setParent(Node p) {parent = p;}
		public void setLeft(Node l) {left = l;}
		public void setRight(Node r) {right = r;}
		
		public Integer getCount(){return count;}
		public String getKmer(){return kmer;}
		public void setCount(Integer c){count = c;}
		public void setKmer(String k){kmer = k;}
	}
	
	private class KmerComparator implements Comparator<Node> {
		@Override
		public int compare(Node a, Node b) {
			if(a.getCount()> b.getCount()) {
				return -1;
			}
			else if(b.getCount()> a.getCount()) {
				return 1;
			}
			else {
				return 0;
			}
		
		}
	}
	
	private int size;
	private Node root;
	
	public int size() { return size;}
	public boolean isEmpty() { return size==0;}
	
	public void printPositions() { 
		Node curr = root;
		helper(curr);
	
	}
	
	public void helper(Node curr) {
		if (curr != null) {
			helper(curr.left);
			System.out.println(curr.kmer + " " + curr.count);
			helper(curr.right);
		}
	}
	
	public Integer get(String kmer) {
		Node curr = root;
		if (isEmpty()) 
			return 0;
		
		while(curr != null)	{
			int value = curr.kmer.compareTo(kmer);
			if (value == 0) {
				return curr.count;
			}
			else if (value > 0) {//go left
				curr = curr.left;
			}
			else if(value < 0) {//go right
				curr = curr.right;
			}
			
		}
		
		return 0;
	}
	
	public int addCount(String kmer) {
		Node curr = root;
		Node currParent = root;
		if (isEmpty()) {
			Node newKmer = new Node();
			root = newKmer;
			root.kmer = kmer;
			root.count = 1;
			size ++;
			return 1;
		}
		
		while(curr != null)	{
			currParent = curr;
			int value = curr.kmer.compareTo(kmer);
			if (value == 0) {
				curr.count += 1;
				return curr.count;
			}
			else if (value > 0) {//go left
				curr = curr.left;
			}
			else if(value < 0) {//go right
				curr = curr.right;
			}
		
		}
		
		int placement = currParent.kmer.compareTo(kmer);
		if(placement > 0) {
			currParent.left = new Node();
			currParent.left.kmer = kmer;
			currParent.left.count = 1;
			currParent.left.parent = currParent;
		}
		else {
			currParent.right = new Node();
			currParent.right.kmer = kmer;
			currParent.right.count = 1;
			currParent.right.parent = currParent;
		}
		size++;
		return 1;
	}
	
	public Integer remove(String kmer) {
		Node curr = root;
		if (isEmpty()) {
			return 0;
		}
		
		while(curr != null) {
			int placement = curr.kmer.compareTo(kmer);
			
			if(placement == 0) {
				break;
			}
			else if(placement > 0) {
				curr = curr.left;
			}
			else if(placement < 0) {
				curr = curr.right;
			}
			
		}
		
		if (curr == null) {
			return 0;
		}
		
		int removed = curr.count;
		
		
		
		if(curr.left == null && curr.right == null) {//both are null
			if (curr == root) {
				root = null;
			}
			else if(curr.kmer.compareTo(curr.parent.kmer) < 0) {
				curr.parent.left = null;
			}
			else {
				curr.parent.right = null;
			}
		}
		
		else if(curr.left == null && curr.right != null) {//left is null
			if (curr == root) {
				root = curr.right;
				root.parent = null;
			}
			else if (curr.kmer.compareTo(curr.parent.kmer) < 0) { //curr is on the left
				curr.parent.left = curr.right;
				curr.right.parent = curr.parent;
			}
			else { //curr is on the right
				curr.parent.right = curr.right;
				curr.right.parent = curr.parent;
			}
		}
		
		
		else if(curr.left != null && curr.right == null) {//right is null
			if (curr == root) {
				root = curr.left;
				root.parent = null;
			}
			else if (curr.kmer.compareTo(curr.parent.kmer) < 0) { //curr is on the left
				curr.parent.left = curr.left;
				curr.left.parent = curr.parent;
			}
			else { //curr is on the right
				curr.parent.right = curr.left;
				curr.left.parent = curr.parent;
			}
			
		}
		
		else { //neither are null
			Node greatest = curr.left;
			while(greatest.right != null) {
				greatest = greatest.right;
			}
			
			curr.kmer = greatest.kmer;
			curr.count = greatest.count;
			
			if(greatest.parent.left == greatest) {
				 greatest.parent.left = null;
			}
			else {
				 greatest.parent.right = null;
			}
		}
		
		size --;
		return removed;
		
	}
	
	public String [] topFive() {
		String[] leading =  new String[5];
		
		PriorityQueue<Node> queue = order();
		
		for(int i = 0; i < leading.length; i++) {
			leading[i] = queue.poll().getKmer();
		}
		
		return leading;
	}
	
	public PriorityQueue<Node> order(){
		Comparator<Node> compare = new KmerComparator();
		PriorityQueue<Node> queue = new PriorityQueue<>(compare);
		Node curr = root;
		orderHelper(curr, queue);
		return queue;
	}
	
	
	public Node orderHelper(Node curr, PriorityQueue<Node> queue) {
		if (curr == null) {
			return null;
		}
		
		orderHelper(curr.getLeft(), queue);
		queue.add(curr);
		orderHelper(curr.getRight(), queue);
		return curr;
	}
	

	
	
	public static void main (String args []) {
		String dna = "AGCCCTCCAGGACAGGCTGCATCAGAAGAGGCCATCAAGCACATCA";
		
		Kmer_Analyzer analyzer = new Kmer_Analyzer();
		
		for(int i = 0; i < dna.length() - 4 + 1; i++) {
			String kmer = dna.substring(i , i + 4);
			analyzer.addCount(kmer);
		}
		
		String[] top = analyzer.topFive();
		
		for(int i = 0; i < 5; i++) {
			System.out.println(top[i]);
		}
		
	}

}

