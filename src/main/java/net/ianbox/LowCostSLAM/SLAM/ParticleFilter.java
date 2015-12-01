package net.ianbox.LowCostSLAM.SLAM;

import java.util.HashSet;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

public class ParticleFilter implements Localizer, Runnable {

	private static final Logger log = Logger.getLogger(ParticleFilter.class
			.getName());

	void sense() {

	}

	/**
	 * Resampling wheel algorithm from udacity
	 * 
	 * @param in
	 * @return
	 */
	static List<Particle> resampling(List<Particle> in) {

		assert in.size() > 0;

		double maxWeight = 0;
		for (Particle p : in) {
			if (maxWeight < p.weight) {
				maxWeight = p.weight;
			}
		}

		List<Particle> out = new LinkedList<Particle>();

		Random rand = new Random();
		double beta = 0;
		int index = rand.nextInt(in.size());

		for (int i = 0; i < in.size(); i++) {
			beta += rand.nextDouble() * 2 * maxWeight;
			while (beta > in.get(index).weight) {
				beta -= in.get(index).weight;
				index = (index + 1) % in.size();
			}
			out.add(in.get(index));
		}

		return out;

	}

	/**
	 * Normalize the weights of the particles
	 * 
	 * @param parts
	 * @return
	 */
	static List<Particle> normalize(List<Particle> parts) {
		assert parts.size() > 0;

		double totalWeight = 0;
		for (Particle p : parts) {
			totalWeight += p.weight;
		}

		Set<Particle> deduplicates = new HashSet<Particle>();
		deduplicates.addAll(parts);
		for (Particle p : deduplicates) {
			p.weight /= totalWeight;
		}

		return parts;
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}
}
