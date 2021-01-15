package com.openclassrooms.entrevoisins.events;

import android.util.Log;

import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Event fired when a user click on a List item
 */
public class ShowNeighbourDetailsEvent {

    /**
     * Neighbour to show details
     */
    public Neighbour voisin;

    /**
     * Constructor.
     * @param neighbour
     */
    public ShowNeighbourDetailsEvent(Neighbour neighbour) {
        this.voisin = neighbour;
     }


}
