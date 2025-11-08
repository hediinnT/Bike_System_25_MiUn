package com.bikeshare.lab3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.bikeshare.model.Bike;
import com.bikeshare.model.Station;
import com.bikeshare.model.Bike.BikeType;

/**
 * Cleaner and more readable versions of the integration tests for Station + Bike.
 * Small refactors: constants for defaults, helper methods for setup, clearer test names
 * and inline comments for Arrange / Act / Assert structure.
 */
@DisplayName("Lab 3: Station + Bike Integration Testing")
public class StationBikeIntigrationTest {

    private static final String DEFAULT_STATION_NAME = "we";
    private static final String DEFAULT_STATION_ID = "2-x";
    private static final String DEFAULT_STATION_ADDRESS = "IDFK";
    private static final double DEFAULT_LATITUDE = 0.0;
    private static final double DEFAULT_LONGITUDE = 0.0;
    private static final int DEFAULT_CAPACITY = 10;

    private Station station;
    private Bike bike;

    @BeforeEach
    void setUp() {
        station = null;
        bike = null;
    }

    @AfterEach
    void tearDown() {
        station = null;
        bike = null;
    }

    // Helper factory methods to make tests more concise and readable.
    private Station newStation() {
        return new Station(DEFAULT_STATION_ID, DEFAULT_STATION_NAME, DEFAULT_STATION_ADDRESS,
                DEFAULT_LATITUDE, DEFAULT_LONGITUDE, DEFAULT_CAPACITY);
    }

    private Station newStation(int capacity) {
        return new Station(DEFAULT_STATION_ID, DEFAULT_STATION_NAME, DEFAULT_STATION_ADDRESS,
                DEFAULT_LATITUDE, DEFAULT_LONGITUDE, capacity);
    }

    private Bike newBike(String id, BikeType type) {
        return new Bike(id, type);
    }

    @Test
    @DisplayName("Add a bike then remove it updates counts correctly")
    void addAndRemoveBikeTest() {
        // Arrange
        station = newStation();
        bike = newBike("12", BikeType.ELECTRIC);

        // Act
        station.addBike(bike);

        // Assert
        assertEquals(1, station.getAvailableBikeCount(), "Available count should be 1 after adding a bike");
        assertEquals(bike, station.getAvailableBike(BikeType.ELECTRIC), "Should retrieve the same electric bike");
        assertFalse(station.isEmpty(), "Station should not be empty after adding a bike");

        // Act: remove
        station.removeBike("12");

        // Assert
        assertEquals(0, station.getAvailableBikeCount(), "Available count should be 0 after removal");
    }

    @ParameterizedTest
    @EnumSource(value = Station.StationStatus.class, names = {"ACTIVE", "INACTIVE", "FULL", "MAINTENANCE"})
    @DisplayName("Adding a bike behaves correctly for different station statuses")
    void addBikeDifferentStatesTest(Station.StationStatus status) {
        // Arrange
        station = newStation();
        bike = newBike("12", BikeType.ELECTRIC);

        // Act / Assert per status
        switch (status) {
            case ACTIVE:
                // If a bike itself is sent to maintenance, adding it should be rejected.
                bike.sendToMaintenance();
                assertThrows(IllegalStateException.class, () -> station.addBike(bike));
                break;
            case INACTIVE:
                // Inactive station should reject new bikes
                station.deactivate();
                assertThrows(IllegalStateException.class, () -> station.addBike(bike));
                break;
            case MAINTENANCE:
                // Station in maintenance should accept bikes
                station.setMaintenance();
                assertDoesNotThrow(() -> station.addBike(bike));
                break;
            case FULL:
                // When station capacity is full adding another bike should throw
                Station small = newStation(1);
                Bike first = newBike("12", BikeType.ELECTRIC);
                Bike extra = newBike("13", BikeType.STANDARD);
                small.addBike(first);
                assertThrows(IllegalStateException.class, () -> small.addBike(extra));
                break;
            default:
                break;
        }
    }

    @Test
    @DisplayName("Removing non-existent or reserved bikes throws")
    void removeBikeTest() {
        // Arrange
        station = newStation(2);
        bike = newBike("12", BikeType.ELECTRIC);
        station.addBike(bike);

        // Non-existent id
        assertThrows(IllegalStateException.class, () -> station.removeBike("1"));

        // Reserved bike cannot be removed
        station.reserveBike("12");
        assertThrows(IllegalStateException.class, () -> station.removeBike("12"));
    }

    @Test
    @DisplayName("Reserving bikes enforces constraints and availability updates")
    void reserveBikeTest() {
        // Arrange
        station = newStation();
        Bike b1 = newBike("12", BikeType.ELECTRIC);
        Bike b2 = newBike("13", BikeType.ELECTRIC);
        Bike b3 = newBike("14", BikeType.STANDARD);

        station.addBike(b1);
        station.addBike(b2);
        station.addBike(b3);

        // Reserving a non-existent id should throw
        assertThrows(IllegalStateException.class, () -> station.reserveBike("1"));

        // Reserve then re-reserve should throw
        station.reserveBike("12");
        assertThrows(IllegalStateException.class, () -> station.reserveBike("12"));

        // Activate station then check available bikes by type
        station.activate();
        assertTrue(station.getAvailableBike(BikeType.ELECTRIC).equals(b2), "Electric available bike should be b2");
        assertTrue(station.getAvailableBike(BikeType.STANDARD).equals(b3), "Standard available bike should be b3");
        assertFalse(station.getAvailableBike(BikeType.STANDARD).equals(null));
        assertFalse(station.getAvailableBike(BikeType.STANDARD).equals(station));
        assertFalse(station.getAvailableBike(BikeType.STANDARD).equals(b2));

        // Two bikes reserved -> available count should reflect only the unreserved bike
        assertEquals(2, station.getAvailableBikeCount());
    }

    @Test
    @DisplayName("Canceling reservations updates availability and reserved list")
    void cancelReservationTest() {
        // Arrange
        station = newStation();
        Bike b1 = newBike("12", BikeType.ELECTRIC);
        Bike b2 = newBike("13", BikeType.STANDARD);
        Bike b3 = newBike("14", BikeType.ELECTRIC);

        station.addBike(b1);
        station.addBike(b2);
        station.addBike(b3);

        // Invalid cancels
        assertThrows(IllegalStateException.class, () -> station.cancelReservation("1"));
        assertThrows(IllegalStateException.class, () -> station.cancelReservation("13"));

        // Make reservations
        station.reserveBike("12");
        station.reserveBike("13");

        assertEquals(2, station.getReservedBikeIds().size(), "Two bikes should be reserved");
        assertEquals(1, station.getAvailableBikeCount(), "Only one bike should be available");

        // Cancel one reservation
        station.cancelReservation("12");

        // Canceling should free the bike back to available but reserved list decreases
        assertEquals(1, station.getAvailableBikeCount(), "Available count remains 1 after cancel (one reserved remains)");
        assertEquals(1, station.getReservedBikeIds().size(), "One reservation should remain");
    }

    @Test
    @DisplayName("Charging electric bikes respects bounds and sets battery level")
    void chargeElectricBikesTest() {
        // Arrange
        station = newStation();
        station.enableCharging(1);
        bike = newBike("12", BikeType.ELECTRIC);
        Bike standard = newBike("13", BikeType.STANDARD);
        station.addBike(bike);
        station.addBike(standard);

        // Act: charge valid percentage
        station.chargeElectricBikes(20);

        // Assert: battery level should be capped at 100
        assertEquals(100, station.getAvailableBike(BikeType.ELECTRIC).getBatteryLevel());

        // Invalid percentages should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> station.chargeElectricBikes(120));
        assertThrows(IllegalArgumentException.class, () -> station.chargeElectricBikes(-1));
    }
}