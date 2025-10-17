package com.bikeshare.lab3;
import com.bikeshare.model.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Lab 3: Station Structural Testing")
public class StationStructualTest {

    String DStationName = "we";
    String DStationID = "2-x";
    String DStationAddress = "IDFK";
    double DLatitude = 0;
    double DLongitude = 0;
    int dCap = 100;

    @BeforeEach void init() {
        DStationName = "we";
        DStationID = "2-x";
        DStationAddress = "IDFK";
        DLatitude = 0;
        DLongitude = 0;
        dCap = 100;

    }

    @Test
    @DisplayName("General test for of a station")
    void stationInitTest(){
        Station actualStation = new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, dCap);
        String expectedToString = "Station{id='2-x', name='we', status=ACTIVE, bikes=0/100}";

        assertEquals(dCap,actualStation.getCapacity());
        assertEquals(DLatitude,actualStation.getLatitude());
        assertEquals(DLongitude,actualStation.getLongitude());
        assertEquals(DStationID,actualStation.getStationId());
        assertEquals(DStationName,actualStation.getName());
        assertEquals(DStationAddress,actualStation.getAddress());
        assertEquals(expectedToString,actualStation.toString());
        assertEquals(Objects.hash(DStationID),actualStation.hashCode());
    }

    @ParameterizedTest(name = "lat test, latitude = {0}")
    @ValueSource(ints = {-100,-91,-90,-2,-1,0,1,2,90,91,100})
    void latitudeTests(double latitude){
        if(latitude < -90 || latitude> 90){
            assertThrows(IllegalArgumentException.class,
                ()->new Station(DStationID, DStationName, DStationAddress, latitude, DLongitude, dCap));
        }else {
            assertDoesNotThrow(
                ()->new Station(DStationID, DStationName, DStationAddress, latitude, DLongitude, dCap));
        }
    }

    @ParameterizedTest(name = "lon test, longitute = {0}")
    @ValueSource(doubles = {-200,-181,-180,-2,-1,0,1,2,180,181,182})
    void longitudeTests(double longitude){
        if(longitude < -180 || longitude> 180 || Objects.isNull(longitude)){
            assertThrows(IllegalArgumentException.class,
                ()->new Station(DStationID, DStationName, DStationAddress, DLatitude, longitude, dCap));
        }else {
            assertDoesNotThrow(
                ()->new Station(DStationID, DStationName, DStationAddress, DLatitude, longitude, dCap));
        }
    }

    @Test
    @DisplayName("Status tests")
    void statusTests(){
        Station actualStation = new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, dCap);

        assertEquals(Station.StationStatus.ACTIVE,actualStation.getStatus());

        actualStation.deactivate();
        assertEquals(Station.StationStatus.INACTIVE,actualStation.getStatus());

        actualStation.activate();
        assertEquals(Station.StationStatus.EMPTY,actualStation.getStatus());

        actualStation.setMaintenance();
        assertEquals(Station.StationStatus.MAINTENANCE,actualStation.getStatus());
    }

    @Test
    @DisplayName("Status tests")
    void chargingTests() {
        Station actualStation = new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, dCap);

        assertFalse(actualStation.isChargingAvailable());

        actualStation.enableCharging(2);
        assertTrue(actualStation.isChargingAvailable());
        assertEquals(2.0,actualStation.getChargingRate());

        actualStation.disableCharging();
        assertFalse(actualStation.isChargingAvailable());
        assertThrows(IllegalArgumentException.class,()->actualStation.enableCharging(-1));
    }

    @Test
    @DisplayName("Simple Distance Tests")
    void distanceTest() {
        Station actualStation = new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, dCap);

        assertThrows(IllegalArgumentException.class,()->actualStation.distanceTo(null));
        assertEquals(0, actualStation.distanceTo(actualStation));

    }

    @Test
    @DisplayName("Random is, and gets")
    void isesAndGetsisTest() {
        Station actualStation = new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, dCap);
        Station actualStation2 = new Station("newS", "les", "d", DLatitude, DLongitude, dCap);

        actualStation2.deactivate();

        assertNull(actualStation2.getAvailableBike(null));
        assertNull(actualStation.getAvailableBike(null));
        assertEquals(100, actualStation.getAvailableDocks());
        assertEquals(100, actualStation.getCapacity());
        assertEquals(0, actualStation.getTotalBikeCount());
        assertFalse(actualStation.isFull());
        assertTrue(actualStation.isEmpty());
        assertTrue(actualStation.equals(actualStation));
        assertFalse(actualStation.equals(null));
        assertFalse(actualStation.equals(2));
        assertFalse(actualStation.equals(Station.StationStatus.class));
        assertFalse(actualStation.equals(actualStation2));
        assertFalse(actualStation2.equals(actualStation));
    }

    @Test
    @DisplayName("Throws by creation tests")
    void throwsTests() {

        assertThrows(IllegalArgumentException.class,
            ()->new Station(null, DStationName, DStationAddress, DLatitude, DLongitude, dCap));

        assertThrows(IllegalArgumentException.class, 
            ()->new Station("  ", DStationName, DStationAddress, DLatitude, DLongitude, dCap));

        assertDoesNotThrow(()->new Station(DStationID, DStationName, null, DLatitude, DLongitude, dCap));

        assertDoesNotThrow(()->new Station(DStationID, DStationName, "  ", DLatitude, DLongitude, dCap));

        assertDoesNotThrow(
            ()->new Station(DStationID, DStationName, "  ", DLatitude, DLongitude, dCap)
                    .getAvailableBikesByType(null));

        assertDoesNotThrow(()->new Station(DStationID, DStationName, "  ", DLatitude, DLongitude, dCap)
                                .getAllBikes());

        assertDoesNotThrow(
            ()->new Station(DStationID, DStationName, "  ",DLatitude, DLongitude, dCap)
                .getReservedBikeIds());                    

        assertThrows(IllegalArgumentException.class,
            ()->new Station(DStationID, "", DStationAddress, DLatitude, DLongitude, dCap));

        assertThrows(IllegalArgumentException.class,
            ()->new Station(DStationID, null, DStationAddress, DLatitude, DLongitude, dCap));

        assertThrows(IllegalArgumentException.class,
            ()->new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, -1));

        assertThrows(IllegalArgumentException.class,
            ()->new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, 101));

        assertThrows(IllegalStateException.class,
            ()-> new Station(DStationID,DStationName, DStationAddress,DLatitude, DLongitude, dCap)
                .chargeElectricBikes(10));

        assertThrows(IllegalArgumentException.class,
            ()-> new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, dCap)
                .addBike(null));

        assertThrows(IllegalArgumentException.class,
            ()-> new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, dCap)
                .removeBike(null));

        assertThrows(IllegalArgumentException.class,
            ()-> new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, dCap)
                .reserveBike(null));

        assertThrows(IllegalArgumentException.class,
            ()-> new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, dCap)
                .cancelReservation(null));
    }
}
