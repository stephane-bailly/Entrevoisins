package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class SwitchNeighbourFavoriteStatusEvent {

    /**
     * Neighbour to show details
     */
    public Neighbour voisin;

    /**
     * Constructor.
     * @param neighbour
     */

    public SwitchNeighbourFavoriteStatusEvent(Neighbour neighbour) {
        this.voisin = neighbour;
    }

}
