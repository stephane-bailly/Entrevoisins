
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;
    private ListNeighbourActivity mActivity;
    private NeighbourApiService mApiService;
    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {

        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        mApiService = DI.getNewInstanceApiService();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */

        @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        // add allOf and idDisplayed to avoid ambiguous view matcher ( view pager returns 2 view with same id ( My Neigbours and Favorites)
            onView(allOf(ViewMatchers.withId(R.id.list_neighbours),isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }
    // 1er test : on clique sur un élément de la liste, l'écran de détail est lancé
    @Test
    public void myNeighboursList_itemClickAction_shouldDisplayItemDetails() {
        // add allOf and idDisplayed to avoid ambiguous view matcher ( view pager returns 2 view with same id ( My Neigbours and Favorites)
       // perform a click on first item
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // check if the details view is shown
        onView(ViewMatchers.withId(R.id.ConstraintLayoutMain)).check(matches(isDisplayed()));
    }

    // 2em test : on clique sur un élément de la liste, l'écran de détail est lancé , le textView indiquant le nom du voisin est bien rempli
    @Test
    public void detailsNeighbourView_TextViewText_shouldReturnNeighbourName() {
        // add allOf and idDisplayed to avoid ambiguous view matcher ( view pager returns 2 view with same id ( My Neigbours and Favorites)
        // perform a click on first item
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // check if TextViewText match first neighbour name ("caroline")
        onView(ViewMatchers.withId(R.id.textView_NomVoisin)).check(matches(withText("Caroline")));
        }

    /**
     * When we delete an item, the item is no more shown
     */

    // 3eme test : test au clic sur le bouton suppression, la liste compte un voisin de moins
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // add allOf and idDisplayed to avoid ambiguous view matcher ( view pager returns 2 view with same id ( My Neigbours and Favorites)
        // Given : We remove the element at position 2
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),isDisplayed())).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),isDisplayed())).check(withItemCount(ITEMS_COUNT-1));
    }

    // 4ème test l'onglet favoris n'affiche que les voisins favoris
    @Test
    public void myFavoritesTab_onClickAction_shouldReturnOnlyFavoritesItems() {
        // check favorites count and favorites names

        // perfom click on favorites tab
        onView(withContentDescription("Favorites")).perform(click());
        // check 4 favorites are returned ( favorites return from default favoritesNeighbours list)
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),isDisplayed())).check(withItemCount(2));

        // Perform click on the first item
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // check if TextViewText match first neighbour name ("Jack")
        onView(ViewMatchers.withId(R.id.textView_NomVoisin)).check(matches(withText("Jack")));

        // return back to favorites tab
        onView(allOf(ViewMatchers.withId(R.id.imageButton_Back),isDisplayed())).perform(click());

        // Perform click on the second item
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        // check if TextViewText match second neighbour name ("Elodie")
        onView(ViewMatchers.withId(R.id.textView_NomVoisin)).check(matches(withText("Elodie")));
    }


}