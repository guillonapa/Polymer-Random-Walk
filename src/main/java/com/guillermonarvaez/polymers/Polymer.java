package com.guillermonarvaez.polymers;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.commons.math3.util.Pair;

/**
 * A representation of a polymer, i.e. a set of linked monomers.
 * <p>
 * NOTE: In two and three dimensions, it is possible that monomers overlap
 * with each other.
 */
public class Polymer {

	// the root of the polymer
	private Monomer root;
	// the tail of the polymer
	private Monomer tail;
	// the length of the polymer
	private int length;

	/**
	 * Creates an instance of the polymer of the specified size and dimension.
	 * 
	 * @param dimension the dimension of the polymer
	 * @param size the number of monomers to be in the polymer
	 */
	public Polymer(final MonomerDimension dimension, final int size) {
		if (dimension == MonomerDimension.TWO_DIMENSIONAL_RECTANGULAR) {
			generateRectangularPolymer(size);
		} else {
			root = new Monomer(dimension);
			Monomer previous = root;
			for (int i = 0; i < size; i++) {
				final Monomer current = new Monomer(dimension);
				previous.setNext(current);
				current.setPrevious(previous);
				// then set the coordinates
				if (dimension == MonomerDimension.THREE_DIMENSIONAL) {
					current.setCoorsFromPrev(previous.getXCoor(), previous.getYCoor(), previous.getZCoor());
				} else {
					current.setCoorsFromPrev(previous.getXCoor(), previous.getYCoor());
				}
				previous = current;
			}
			tail = previous;
			length = size;
		}
	}

	/**
	 * Generate a polymer that only grows parallel or anti-parallel to the
	 * x or y axis. This type of polymer does not allow for any 'vertex' to
	 * be crossed twice.
	 * 
	 * @param size the maximum size of the polymer
	 * @return the root (monomer) of the generated polymer
	 */
	private Monomer generateRectangularPolymer(final int size) {
		root = new Monomer(MonomerDimension.TWO_DIMENSIONAL_RECTANGULAR);
		HashSet<Pair<Integer, Integer>> vertices = new HashSet<>();
		vertices.add(root.getHeadCoors());
		vertices.add(root.getTailCoors());

		Monomer current = root;
		int length = 1;
		for (int i = 0; i < size; i++) {
			ArrayList<Pair<Integer, Integer>> availableVertices = findAvailableVertices(current.getTailCoors(), vertices);
			if (availableVertices.isEmpty()) {
				break;
			}
			Pair<Integer, Integer> nextVertex = getRandomVertex(availableVertices);
			Monomer next = new Monomer(MonomerDimension.TWO_DIMENSIONAL_RECTANGULAR);
			next.setCoors(current.getTailCoors().getFirst(), current.getTailCoors().getSecond());
			next.setNewRectangularOrientation(nextVertex);
			current.setNext(next);
			next.setPrevious(current);
			vertices.add(next.getHeadCoors());
			vertices.add(next.getTailCoors());
			length += 1;
			current = next;
		}
		this.length = length;
		tail = current;
		return root;
	}

	/**
	 * Get all the vertices that have not been occupied by the polymer and are reachable
	 * by one step from the starting vertex.
	 * 
	 * @param start the starting coordinates
	 * @param vertices the visited vertices
	 * @return a list of available vertices (might be empty)
	 */
	private ArrayList<Pair<Integer, Integer>> findAvailableVertices(Pair<Integer, Integer> start, HashSet<Pair<Integer, Integer>> vertices) {
		ArrayList<Pair<Integer, Integer>> result = new ArrayList<>();
		ArrayList<Pair<Integer, Integer>> potentialVertices = new ArrayList<>();
		potentialVertices.add(new Pair<Integer,Integer>(start.getFirst() + 1, start.getSecond()));
		potentialVertices.add(new Pair<Integer,Integer>(start.getFirst() - 1, start.getSecond()));
		potentialVertices.add(new Pair<Integer,Integer>(start.getFirst(), start.getSecond() + 1));
		potentialVertices.add(new Pair<Integer,Integer>(start.getFirst(), start.getSecond() - 1));

		for (Pair<Integer, Integer> vertex : potentialVertices) {
			if (!vertices.contains(vertex)) {
				result.add(vertex);
			}
		}

		return result;
	}

	/**
	 * Given a non-null and non-empty list, it returns a random
	 * element of the list (a random vertex).
	 * 
	 * @param availableVertices the available vertices
	 * @return a random vertex
	 */
	private Pair<Integer, Integer> getRandomVertex(ArrayList<Pair<Integer, Integer>> availableVertices) {
		int index = (int) (Math.random() * availableVertices.size());
		return availableVertices.get(index);
	}

	/**
	 * Get the monomer that is the root of the polymer.
	 */
	public Monomer getRoot() {
		return root;
	}

	/**
	 * Get the length of the polymer, i.e. the number of monomers.
	 */
	public int getLength() {
		return this.length;
	}

	/**
	 * Get the coordinates of the final position of the polymer. These are
	 * the coordinates where the next monomer would start from, and NOT the 
	 * coordinates where the last monomer starts from.
	 */
	public double[] getFinalPositions() {
		MonomerDimension dimension = root.getDimension();
		Monomer placeholder = new Monomer(dimension);
		switch (dimension) {
			case TWO_DIMENSIONAL_RECTANGULAR:
				Pair<Integer, Integer> endpoints = tail.getTailCoors();
				return new double[] { endpoints.getFirst(), endpoints.getSecond() };
			case TWO_DIMENSIONAL:
				placeholder.setCoorsFromPrev(tail.getXCoor(), tail.getYCoor());
				return new double[] { placeholder.getXCoor(), placeholder.getYCoor() };
			case THREE_DIMENSIONAL:
				placeholder.setCoorsFromPrev(tail.getXCoor(), tail.getYCoor(), tail.getZCoor());
				return new double[] { placeholder.getXCoor(), placeholder.getYCoor(), placeholder.getZCoor() };
			default:
				return null;
		}
	}

	/**
	 * Print the polymer to the console.
	 */
	public void printPolymer() {
		printPolymer(System.out);
	}

	/**
	 * Print the polymer using the PrintStream.
	 * 
	 * @param stream the PrintStream to use
	 * @throws FileNotFoundException
	 */
	public void printPolymer(final PrintStream stream) {
		if (root.getDimension() == MonomerDimension.THREE_DIMENSIONAL) {
			stream.println("x-coordinate, y-coordinate, z-coordinate");
		} else {
			stream.println("x-coordinate, y-coordinate");
		}

		Monomer current = root;
		while (current != null) {
			stream.println(current);
			current = current.getNext();
		}
		stream.close();
	}
}
