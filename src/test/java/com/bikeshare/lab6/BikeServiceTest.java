package com.bikeshare.lab6;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bikeshare.model.Bike;
import com.bikeshare.model.BikeType;
import com.bikeshare.model.Station;
import com.bikeshare.repository.BikeRepository;
import com.bikeshare.service.BikeService;
import com.bikeshare.service.exception.BikeNotFoundException;
import com.bikeshare.service.exception.BikeNotAvailableException;
import com.bikeshare.service.exception.InvalidBikeOperationException;

@ExtendWith(MockitoExtension.class)
public class BikeServiceTest {

    @Mock
    private BikeRepository bikeRepository;

    @InjectMocks
    private BikeService bikeService;

    private static final String BIKE_ID = "bike123";
    private static final String USER_ID = "user123";
    private static final String STATION_ID = "station123";

    @BeforeEach
    void setUp() {
        // Common test setup if needed
    }

    private Bike newBike(String id, Bike.BikeType type) {
        return new Bike(id, type);
    }

    @Test
    void createBike_ValidInput_Success() {
        // Arrange
        when(bikeRepository.existsById(BIKE_ID)).thenReturn(false);
        Bike bike = newBike(BIKE_ID, Bike.BikeType.STANDARD);
        when(bikeRepository.save(any(Bike.class))).thenReturn(bike);

        // Act
        Bike result = bikeService.createBike(BIKE_ID, BikeType.STANDARD);

        // Assert
        assertNotNull(result);
        assertEquals(BIKE_ID, result.getBikeId());
        verify(bikeRepository).save(any(Bike.class));
    }

    @Test
    void createBike_DuplicateId_ThrowsException() {
        // Arrange
        when(bikeRepository.existsById(BIKE_ID)).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> bikeService.createBike(BIKE_ID, BikeType.STANDARD));
        verify(bikeRepository, never()).save(any(Bike.class));
    }

    @Test
    void findBikeById_NonExistentBike_ThrowsException() {
        // Arrange
        when(bikeRepository.findById(BIKE_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BikeNotFoundException.class, 
            () -> bikeService.findBikeById(BIKE_ID));
    }

    @Test
    void rentBike_BikeInMaintenance_ThrowsException() {
        // Arrange
        Bike bike = newBike(BIKE_ID, Bike.BikeType.STANDARD);
        bike.sendToMaintenance();
        when(bikeRepository.findById(BIKE_ID)).thenReturn(Optional.of(bike));

        // Act & Assert
        assertThrows(BikeNotAvailableException.class, 
            () -> bikeService.rentBike(BIKE_ID, USER_ID));
    }


    @Test
    void returnBike_BikeNotInUse_ThrowsException() {
        // Arrange
        Bike bike = newBike(BIKE_ID, Bike.BikeType.STANDARD);
        Station station = new Station(STATION_ID, "Test Station", "Address", 0.0, 0.0, 5);
        when(bikeRepository.findById(BIKE_ID)).thenReturn(Optional.of(bike));

        // Act & Assert
        assertThrows(InvalidBikeOperationException.class, 
            () -> bikeService.returnBike(BIKE_ID, station));
    }

    @Test
    void markForMaintenance_BikeInUse_ThrowsException() {
        // Arrange
        Bike bike = newBike(BIKE_ID, Bike.BikeType.STANDARD);
        bike.startRide();
        when(bikeRepository.findById(BIKE_ID)).thenReturn(Optional.of(bike));

        // Act & Assert
        assertThrows(InvalidBikeOperationException.class, 
            () -> bikeService.markForMaintenance(BIKE_ID, "Test reason"));
    }


    @Test
    void findBikeById_ExistingBike_ReturnsBike() {
        // Arrange
        Bike bike = newBike(BIKE_ID, Bike.BikeType.STANDARD);
        when(bikeRepository.findById(BIKE_ID)).thenReturn(Optional.of(bike));

        // Act
        Bike result = bikeService.findBikeById(BIKE_ID);

        // Assert
        assertNotNull(result);
        assertEquals(BIKE_ID, result.getBikeId());
    }

    @Test
    void rentBike_AvailableBike_Success() {
        // Arrange
        Bike bike = newBike(BIKE_ID, Bike.BikeType.STANDARD);
        when(bikeRepository.findById(BIKE_ID)).thenReturn(Optional.of(bike));
        when(bikeRepository.save(any(Bike.class))).thenReturn(bike);

        // Act & Assert
        assertDoesNotThrow(() -> bikeService.rentBike(BIKE_ID, USER_ID));
        verify(bikeRepository).save(any(Bike.class));
    }

    @Test
    void returnBike_ValidReturn_Success() {
        // Arrange
        Bike bike = newBike(BIKE_ID, Bike.BikeType.STANDARD);
        bike.startRide(); // Make bike unavailable
        Station station = new Station(STATION_ID, "Test Station", "Address", 0.0, 0.0, 5);
        
        when(bikeRepository.findById(BIKE_ID)).thenReturn(Optional.of(bike));
        when(bikeRepository.save(any(Bike.class))).thenReturn(bike);

        // Act & Assert
        assertDoesNotThrow(() -> bikeService.returnBike(BIKE_ID, station));
        verify(bikeRepository).save(any(Bike.class));
    }

    @Test
    void getBikeStatistics_ReturnsCorrectStats() {
        // Arrange
        List<Bike> bikes = Arrays.asList(
            newBike("bike1", Bike.BikeType.STANDARD),
            newBike("bike2", Bike.BikeType.ELECTRIC)
        );
        when(bikeRepository.count()).thenReturn(2L);
        when(bikeRepository.countAvailable()).thenReturn(1L);
        when(bikeRepository.findAll()).thenReturn(bikes);

        // Act
        BikeService.BikeStatistics stats = bikeService.getBikeStatistics();

        // Assert
        assertEquals(2, stats.getTotalBikes());
        assertEquals(1, stats.getAvailableBikes());
        assertEquals(0.5, stats.getAvailabilityRate(), 0.01);
    }

    @Test
    void markForMaintenance_ValidBike_Success() {
        // Arrange
        Bike bike = newBike(BIKE_ID, Bike.BikeType.STANDARD);
        when(bikeRepository.findById(BIKE_ID)).thenReturn(Optional.of(bike));
        when(bikeRepository.save(any(Bike.class))).thenReturn(bike);

        // Act & Assert
        assertDoesNotThrow(() -> bikeService.markForMaintenance(BIKE_ID, "Test reason"));
        verify(bikeRepository).save(any(Bike.class));
    }
	@Test
	void failTestToSeeWhatHappens(){
		assert(false);
	}
}
