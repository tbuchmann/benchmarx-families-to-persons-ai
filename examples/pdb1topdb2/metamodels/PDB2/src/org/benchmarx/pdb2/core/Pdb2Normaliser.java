package org.benchmarx.pdb2.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pdb2.Person;

public class Pdb2Normaliser implements Comparator<Person> {
	@Override
	public int compare(Person o1, Person o2) {
		return o1.getId().compareTo(o2.getId());
	}

	public void normalize(List<Person> persons){
		Comparator<Person> comparator = new Pdb2Normaliser();
		Collections.sort(persons, comparator);
	}
}
