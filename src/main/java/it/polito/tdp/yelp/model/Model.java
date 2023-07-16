package it.polito.tdp.yelp.model;

import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	YelpDao dao;
	Map<String,User> idMap;
	Graph<User,DefaultWeightedEdge> grafo;
	
	public Model() {
		dao=new YelpDao();
		
		idMap= new HashMap<>();
		dao.getAllUsersMappa(idMap);
	}
	
	public void creaGrafo(int Nrecensioni, int anno) {
		grafo = new SimpleWeightedGraph<User,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		List<User> listaVertici = dao.creaVertici(Nrecensioni, idMap);
		Graphs.addAllVertices(grafo, listaVertici);
		
		List<Adiacenza> listaArchi = dao.creaArchi( anno, Nrecensioni, idMap);
		for(Adiacenza a : listaArchi) {
			Graphs.addEdgeWithVertices(grafo, a.getU1(), a.getU2(), a.getPeso());
		}
		
		System.out.println("Vertici : " + grafo.vertexSet().size());
		System.out.println("Archi : "+grafo.edgeSet().size());
		
	}
	
	
	
	
	
	
	
	public int getNumeroVertici() {
		if(grafo!=null) {
			return grafo.vertexSet().size();
		}
		else {
			return 0;	
		}
	}
	
	public int getNumeroArchi() {
		if(grafo!=null) {
			return grafo.edgeSet().size();
		}
		else {
			return 0;	
		}
	}

	public boolean grafoCreato() {
		if(grafo!=null) {
			return true;
		}
		else
		return false;
	}
}
