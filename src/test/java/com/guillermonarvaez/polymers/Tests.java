package com.guillermonarvaez.polymers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class Tests {

    @Test
    public void shouldBuildTwoDimensionalPolymer() {
        Polymer polymer = new Polymer(MonomerDimension.TWO_DIMENSIONAL, 1000);
        assertEquals(polymer.getLength(), 1000);
    }

    @Test
    public void shouldBuildTwoDimensionalRectangularPolymer() {
        Polymer polymer = new Polymer(MonomerDimension.TWO_DIMENSIONAL_RECTANGULAR, 1000);
        assertTrue(polymer.getLength() > 0);
        assertTrue(polymer.getLength() <= 1000);
    }

    @Test
    public void shouldBuildThreeDimensionalPolymer() {
        Polymer polymer = new Polymer(MonomerDimension.THREE_DIMENSIONAL, 1000);
        assertEquals(polymer.getLength(), 1000);
    }
}
