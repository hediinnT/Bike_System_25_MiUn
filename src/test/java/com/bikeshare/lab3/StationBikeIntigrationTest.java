package com.bikeshare.lab3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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

@DisplayName("Lab 3: Station + Bike Integration Testing")
public class StationBikeIntigrationTest {
    
    String DStationName = "we";
    String DStationID = "2-x";
    String DStationAddress = "IDFK";
    double DLatitude = 0;
    double DLongitude = 0;
    int dCap = 10;
    Station s = null;
    Bike b = null;

    @BeforeEach void init() {
        DStationName = "we";
        DStationID = "2-x";
        DStationAddress = "IDFK";
        DLatitude = 0;
        DLongitude = 0;
        dCap = 10;
    }

    @AfterEach void destruct(){
        s = null;
        b = null;
        dCap = 10;
    }

    @Test
    @DisplayName("Add and remove bike")
    void addAndRemoveBikeTest(){
        s = new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, dCap);
        b = new Bike("12", BikeType.ELECTRIC);

        s.addBike(b);

        assertEquals(1, s.getAvailableBikeCount());
        assertEquals(b, s.getAvailableBike(BikeType.ELECTRIC));
        assertFalse(s.isEmpty());

        s.removeBike("12");

        assertEquals(0, s.getAvailableBikeCount());
    }

    @ParameterizedTest
    @EnumSource(value = Station.StationStatus.class, names = {"ACTIVE","INACTIVE","FULL","MAINTENANCE"})
    @DisplayName("Add bike diffrent stats of Station")
    void addBikeDiffrentStatesTest(Station.StationStatus stat){
        s = new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, dCap);
        b = new Bike("12", BikeType.ELECTRIC);

        switch(stat) {
            case ACTIVE:
                b.sendToMaintenance();
                assertThrows(IllegalStateException.class,()->s.addBike(b));
                break;
            case INACTIVE:
                s.deactivate();
                assertThrows(IllegalStateException.class, ()->s.addBike(b));
                break;
            case MAINTENANCE:
                s.setMaintenance();
                assertDoesNotThrow(()->s.addBike(b));
                break;
 
            case FULL:
                Bike b2 = new Bike("13", BikeType.STANDARD);
                s = new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, 1);
                s.addBike(b);
                assertThrows(IllegalStateException.class, ()->s.addBike(b2));
                break;
            default:
                break;
        }


    }
    @Test
    @DisplayName("Remove bikes hit Throws")
    void removeBikeTest(){
        s = new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, 2);
        b = new Bike("12", BikeType.ELECTRIC);

        s.addBike(b);
       
        assertThrows(IllegalStateException.class, ()->s.removeBike("1"));

        s.reserveBike("12");
        assertThrows(IllegalStateException.class, ()->s.removeBike("12"));

    }

    @Test
    @DisplayName("Reseerve bikes hit Throws, and some method")
    void reservBikeTest(){
        s = new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, dCap);
        b = new Bike("12", BikeType.ELECTRIC);
        Bike b2 = new Bike("13", BikeType.ELECTRIC);
        Bike b3 = new Bike("14", BikeType.STANDARD);

        s.addBike(b);
        s.addBike(b2);
        s.addBike(b3);
       
        assertThrows(IllegalStateException.class, ()->s.reserveBike("1"));

        s.reserveBike("12");
        assertThrows(IllegalStateException.class, ()->s.reserveBike("12"));


        s.activate();

        assertTrue(s.getAvailableBike(BikeType.ELECTRIC).equals(b2));
        assertTrue(s.getAvailableBike(BikeType.STANDARD).equals(b3));
        assertFalse(s.getAvailableBike(BikeType.STANDARD).equals(null));
        assertFalse(s.getAvailableBike(BikeType.STANDARD).equals(s));
        assertFalse(s.getAvailableBike(BikeType.STANDARD).equals(b2));

        assertEquals(2, s.getAvailableBikeCount());

    }

    @Test
    @DisplayName("Cancel reseerve bikes hit Throws")
    void cancelReservationTest(){
        s = new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude, dCap);
        b = new Bike("12", BikeType.ELECTRIC);
        Bike b2 = new Bike("13", BikeType.STANDARD);

        Bike b3 = new Bike("14", BikeType.ELECTRIC);

        s.addBike(b);
        s.addBike(b2);
        s.addBike(b3);

        assertThrows(IllegalStateException.class, ()->s.cancelReservation("1"));
        assertThrows(IllegalStateException.class, ()->s.cancelReservation("13"));

        s.reserveBike("12");
        s.reserveBike("13");

        assertEquals(2, s.getReservedBikeIds().size());
        assertEquals(1, s.getAvailableBikeCount());

        s.cancelReservation("12");
        
        // This should not be True 
        assertEquals(1, s.getAvailableBikeCount());
        assertEquals(1, s.getReservedBikeIds().size());




    }


    @Test
    @DisplayName("Charging eletric bikes")
    void chargeElectricBikesTest(){
        s = new Station(DStationID, DStationName, DStationAddress, DLatitude, DLongitude,dCap );
        s.enableCharging(1);
        b = new Bike("12", BikeType.ELECTRIC);
        Bike b2 = new Bike("13", BikeType.STANDARD);
        s.addBike(b);
        s.addBike(b2);

        s.chargeElectricBikes(20);
        assertEquals(100, s.getAvailableBike(Bike.BikeType.ELECTRIC).getBatteryLevel());

        // This Should be fixed in the chargeEleticBikes it should check input or catch illegalArguments
        assertThrows(IllegalArgumentException.class, ()->s.chargeElectricBikes(120));
        assertThrows(IllegalArgumentException.class, ()->s.chargeElectricBikes(-1));
    
    }


}
