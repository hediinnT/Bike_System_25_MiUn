package com.bikeshare.lab3;
import com.bikeshare.model.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.nullable;


public class StationStructualTest {

    String DefaultStationName = "we";
    String DefaultStationID = "2-x";
    String DefaultStationAddress = "IDFK";
    double DLatitude = 0;
    double DefaultLongitude = 0;
    int dCap = 100;


    @BeforeEach void init() {
        DefaultStationName = "we";
        DefaultStationID = "2-x";
        DefaultStationAddress = "IDFK";
        DLatitude = 0;
        DefaultLongitude = 0;
        dCap = 100;

    }
    @Test
    @DisplayName("General test for of a station")
    void stationInitTest(){
        Station actualStation = new Station(DefaultStationID,
                DefaultStationName,DefaultStationAddress,
                DLatitude,DefaultLongitude, dCap);
        String expectedToString = "Station{id='2-x', name='we', status=ACTIVE, bikes=0/100}";

        assertEquals(dCap,actualStation.getCapacity());
        assertEquals(DLatitude,actualStation.getLatitude());
        assertEquals(DefaultLongitude,actualStation.getLongitude());
        assertEquals(DefaultStationID,actualStation.getStationId());
        assertEquals(DefaultStationName,actualStation.getName());
        assertEquals(DefaultStationAddress,actualStation.getAddress());
        assertEquals(expectedToString,actualStation.toString());
        assertEquals(Objects.hash(DefaultStationID),actualStation.hashCode());

    }
    @ParameterizedTest(name = "lat test, latitude = {0}")
    @ValueSource(ints = {-100,-91,-90,-2,-1,0,1,2,90,91,100})
    void latitudeTests(double latitude){

        if(latitude < -90 || latitude> 90){
            assertThrows(IllegalArgumentException.class,()->new Station(DefaultStationID,
                    DefaultStationName,DefaultStationAddress,
                    latitude,DefaultLongitude, dCap));
        }else {
            assertDoesNotThrow(()->new Station(DefaultStationID,
                    DefaultStationName,DefaultStationAddress,
                    latitude,DefaultLongitude, dCap));
        }


    }

    @ParameterizedTest(name = "lon test, longitute = {0}")
    @ValueSource(doubles = {-200,-181,-180,-2,-1,0,1,2,180,181,182})
    void longitudeTests(double longitude){

        if(longitude < -180 || longitude> 180 || Objects.isNull(longitude)){
            assertThrows(IllegalArgumentException.class,()->new Station(DefaultStationID,
                    DefaultStationName,DefaultStationAddress,
                    DLatitude,longitude, dCap));
        }else {
            assertDoesNotThrow(()->new Station(DefaultStationID,
                    DefaultStationName,DefaultStationAddress,
                    DLatitude,longitude, dCap));
        }


    }

    @Test
    @DisplayName("Status tests")
    void statusTests(){
           Station actualStation = new Station(DefaultStationID,
                DefaultStationName,DefaultStationAddress,
                   DLatitude,DefaultLongitude, dCap);

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
        Station actualStation = new Station(DefaultStationID,
                DefaultStationName, DefaultStationAddress,
                DLatitude, DefaultLongitude, dCap);

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
        Station actualStation = new Station(DefaultStationID,
                DefaultStationName, DefaultStationAddress,
                DLatitude, DefaultLongitude, dCap);


        assertThrows(IllegalArgumentException.class,()->actualStation.distanceTo(null));

        assertEquals(0, actualStation.distanceTo(actualStation));

    }
    @Test
    @DisplayName("Random is, and gets")
    void isesAndGetsisTest() {
        Station actualStation = new Station(DefaultStationID,
                DefaultStationName, DefaultStationAddress,
                DLatitude, DefaultLongitude, dCap);

        Station actualStation2 = new Station("newS",
                "les", "d",
                DLatitude, DefaultLongitude, dCap);

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
        assertFalse(actualStation.equals(new BikeTypeBasicTest()));
        assertFalse(actualStation.equals(actualStation2));
        assertFalse(actualStation2.equals(actualStation));

    }

    @Test
    @DisplayName("Throws by creation tests")
    void throwsTests() {

        assertThrows(IllegalArgumentException.class,()->new Station(null,
                    DefaultStationName, DefaultStationAddress,
                    DLatitude, DefaultLongitude, dCap));

        assertThrows(IllegalArgumentException.class,()->new Station("  ",
                    DefaultStationName, DefaultStationAddress,
                    DLatitude, DefaultLongitude, dCap));

        assertDoesNotThrow(()->new Station(DefaultStationID,
                    DefaultStationName, null,
                    DLatitude, DefaultLongitude, dCap));

        assertDoesNotThrow(()->new Station(DefaultStationID,
                    DefaultStationName, "  ",
                    DLatitude, DefaultLongitude, dCap));

        assertDoesNotThrow(()->new Station(DefaultStationID,
                    DefaultStationName, "  ",
                    DLatitude, DefaultLongitude, dCap).getAvailableBikesByType(null));

        assertDoesNotThrow(()->new Station(DefaultStationID,
                    DefaultStationName, "  ",
                    DLatitude, DefaultLongitude, dCap).getAllBikes());

        assertDoesNotThrow(()->new Station(DefaultStationID,
                    DefaultStationName, "  ",
                    DLatitude, DefaultLongitude, dCap).getReservedBikeIds());


        assertThrows(IllegalArgumentException.class,()->new Station(DefaultStationID,
                    "", DefaultStationAddress,
                    DLatitude, DefaultLongitude, dCap));

        assertThrows(IllegalArgumentException.class,()->new Station(DefaultStationID,
                    null, DefaultStationAddress,
                    DLatitude, DefaultLongitude, dCap));

        assertThrows(IllegalArgumentException.class,()->new Station(DefaultStationID,
                    DefaultStationName, DefaultStationAddress,
                    DLatitude, DefaultLongitude, -1));

        assertThrows(IllegalArgumentException.class,()->new Station(DefaultStationID,
                    DefaultStationName, DefaultStationAddress,
                    DLatitude, DefaultLongitude, 101));

        assertThrows(IllegalStateException.class,()-> new Station(DefaultStationID,
                    DefaultStationName, DefaultStationAddress,
                    DLatitude, DefaultLongitude, dCap).chargeElectricBikes(10));

        assertThrows(IllegalArgumentException.class,()-> new Station(DefaultStationID,
                    DefaultStationName, DefaultStationAddress,
                    DLatitude, DefaultLongitude, dCap).addBike(null));

        assertThrows(IllegalArgumentException.class,()-> new Station(DefaultStationID,
                    DefaultStationName, DefaultStationAddress,
                    DLatitude, DefaultLongitude, dCap).removeBike(null));

        assertThrows(IllegalArgumentException.class,()-> new Station(DefaultStationID,
                    DefaultStationName, DefaultStationAddress,
                    DLatitude, DefaultLongitude, dCap).reserveBike(null));

        assertThrows(IllegalArgumentException.class,()-> new Station(DefaultStationID,
                    DefaultStationName, DefaultStationAddress,
                    DLatitude, DefaultLongitude, dCap).cancelReservation(null));




    }


}
