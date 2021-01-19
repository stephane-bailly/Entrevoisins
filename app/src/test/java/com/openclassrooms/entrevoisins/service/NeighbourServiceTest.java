package com.openclassrooms.entrevoisins.service;

import android.provider.Settings;
import android.util.Log;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    // test des trois fonctionnalités



    // 1er test : lister mes voisins ;
    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }
    //2em test : ajouter un voisin ;
    @Test
    public void addNeighbourWithSuccess() {
        // Create a new neighbour
        Neighbour neighbourToAdd = new Neighbour(100, "TestUser", "", "test address","+33 6 86 57 90 14",  "test message");
        // add the neighbour to the neighbours list
        service.createNeighbour(neighbourToAdd);
        // check if the new neighbour is on neighbours list
        assertTrue(service.getNeighbours().contains(neighbourToAdd));


    }

    //3eme test : suppression d’un voisin
    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }
}
