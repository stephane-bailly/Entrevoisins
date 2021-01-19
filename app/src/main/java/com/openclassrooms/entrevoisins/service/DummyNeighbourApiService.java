package com.openclassrooms.entrevoisins.service;

import android.util.Log;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     *  Boucle sur la liste des voisins (neighbours) et ajoute ceux avec l'attribut favorite a true dans la
     *  liste des voisins favoris ( favoritesNeighbours )
     * @return la liste des voisins avec l'attribut isFavorite a true
     */
    @Override
    public List<Neighbour> getFavoritesNeighbours() {
        List<Neighbour> favoritesNeighbours = new ArrayList<Neighbour>();
        for (Neighbour voisin : neighbours){
            if (voisin.isFavorite()){
                favoritesNeighbours.add(voisin);
            }
        }
        return favoritesNeighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    @Override
    public void SwitchNeighbourIsFavorite(Neighbour neighbour) {

        boolean NewFavoriteStatus = !(neighbour.isFavorite());
        neighbours.get(neighbours.indexOf(neighbour)).setFavorite(NewFavoriteStatus);

    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }
}
