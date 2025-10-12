package com.bikeshare.lab2;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.bikeshare.model.MembershipType;
import com.bikeshare.model.User;

/**
 * Lab 2 Template: Black Box Testing for User class
 * 
 
 * 
 * TODO for students:
 * - Challenge 2.1: Add Equivalence Partitioning tests for email validation, name, telephone number (With GenAI help), and fund addition
 * - Challenge 2.2: Add Boundary Value Analysis tests for fund addition
 * - Challenge 2.3: Add Decision Table tests for phone number validation did you mean membership
 * - Optional Challenge 2.4: Add error scenario tests
 */

// This test is just an example to get you started. You will need to add more tests as per the challenges.
@DisplayName("Verify name handling in User class")
class UserBlackBoxTest {
    String validPersonnummer;

    String expectedFirstName;
    String expectedLastName;
    String validEmail;

    @BeforeEach void init(){
        validPersonnummer = "901101-1237";
        expectedFirstName = "John";
        expectedLastName = "Doe";
        validEmail = "valid@email.test";

    }
    @Test
    @DisplayName("Should store and retrieve user names correctly")
    void shouldStoreAndRetrieveUserNamesCorrectly() {
        // Arrange - Set up test data
        // Valid Swedish personnummer
        
        // Act - Execute the method under Test
        User user = new User(validPersonnummer, validEmail, expectedFirstName, expectedLastName);
        String actualFirstName = user.getFirstName();
        String actualLastName = user.getLastName();
        String actualFullName = user.getFullName();
        
        // Assert - Verify the expected outcome
        assertNotNull(user, "User should be created successfully");
        assertEquals(expectedFirstName, actualFirstName, "First name should match");
        assertEquals(expectedLastName, actualLastName, "Last name should match");
        assertEquals("John Doe", actualFullName, "Full name should be formatted correctly");
    }
    
 // Challenge 2.1: Add Equivalence Partitioning tests for email validation, name, telephone number (With GenAI help), and fund addition

    @ParameterizedTest(name="Valid Email Partion = {0}")
    @ValueSource(strings = {"heto2200@student.miun.se","2022.195@student.setur.fo"})
    @DisplayName("Valid Email Partion")
    void validEmailPartition(String email){
            // Hint: Test valid emails (user@domain.com) and invalid emails (missing @, empty, etc.)

        String validEmail = email;
        // Act - Execute the method under test

        
        Runnable actualExecutable = () -> new User(validPersonnummer, validEmail, expectedFirstName, expectedLastName);

    
            // Assert - Verify the expected outcome
        assertDoesNotThrow(()->actualExecutable.run());
    }

    @ParameterizedTest(name="Invalid Email Partion = {0}")
    @ValueSource(strings = {"this.will.not.work@","this.will.not.work","this.is.@not.valid","this.@will.not.work", ""})
    @DisplayName("Invalid Email Partion")
    void invalidEmailPartition(String email){
        // Hint: Test valid emails (user@domain.com) and invalid emails (missing @, empty, etc.)
    

        String validEmail = email;
        // Act - Execute the method under test

        
        Runnable actualExecutable = () -> new User(validPersonnummer, validEmail, expectedFirstName, expectedLastName);

    
            // Assert - Verify the expected outcome
        assertThrows(IllegalArgumentException.class,()->actualExecutable.run());
    }

    @ParameterizedTest(name = "Valid Phone Number Partion = {0}")
    @ValueSource(strings = {"077-777 77 77","+46 77 777 77 77"})
    @DisplayName("Valid Phone Number Partion")
    void validPhoneNumber (String phoneNum){

        User user = new User(validPersonnummer, validEmail, expectedFirstName, expectedLastName);
        // Act - Execute the method under test

        

    
            // Assert - Verify the expected outcome
        assertDoesNotThrow(()->user.setPhoneNumber(phoneNum));
    }

    @ParameterizedTest(name ="Invalid Phone Number Partion = {0}")
    @ValueSource(strings = {"Å","22 11","t a","1777777777", "1177777777" , "+56777777777",""})
    @DisplayName("Invalid Phone Number Partion")
    void invalidPhoneNumber(String phoneNum){
    
        
            // Act - Execute the method under test
            User user = new User(validPersonnummer, validEmail, expectedFirstName, expectedLastName);


            // Assert - Verify the expected outcome
            if(phoneNum.length() <1){
                user.setPhoneNumber(phoneNum);
                assertNull(user.getPhoneNumber());

            }else{
                assertThrows(IllegalArgumentException.class,()-> user.setPhoneNumber(phoneNum));
            }
       
    }

 @ParameterizedTest(name="Valid Name Partion = {0}")
    @ValueSource(strings = {"Name Nameson","Ör Årson","Heðin Tórstún"})
    @DisplayName("Valid Name Partion")
    void validNamePartition (String fullName){

        String expectedFirstName = fullName.split(" ")[0];
        String expectedLastName = fullName.split(" ")[1];
        String validEmail = "valid@email.test";
        // Act - Execute the method under test

        
        Runnable actualExecutable = () -> new User(validPersonnummer, validEmail, expectedFirstName, expectedLastName);

    
            // Assert - Verify the expected outcome
        assertDoesNotThrow(()->actualExecutable.run());
    }
    @DisplayName("Invalid Name Partion")
   @ParameterizedTest(name ="Invalid Name Partion = {0}")
    @ValueSource(strings = {"Å","22 11","t a",""})
    void invalidFullNames(String fullName){
    
        String validEmail = "valid@email.com";
        if(fullName.length() < 2){
            String expectedFirstName = fullName;
            String expectedLastName  = "";
            // Act - Execute the method under test
            Runnable actualExecutable = () -> new User(validPersonnummer, validEmail, expectedFirstName, expectedLastName);

            // Assert - Verify the expected outcome
            assertThrows(IllegalArgumentException.class,()->actualExecutable.run());
        }else{
            String expectedFirstName = fullName.split(" ")[0];
            String expectedLastName  = fullName.split(" ")[1];
            // Act - Execute the method under test
            Runnable actualExecutable = () -> new User(validPersonnummer, validEmail, expectedFirstName, expectedLastName);

            // Assert - Verify the expected outcome
            assertThrows(IllegalArgumentException.class,()->actualExecutable.run());
        }
    }
 
   @ParameterizedTest(name ="Should add funds =  \"{0}\"")
    @DisplayName("Should add funds")
    @ValueSource(doubles= {1,2,3,1000,0.1, 0.01})
    void shouldAddFunds(double fund){
        User user = new User(validPersonnummer, validEmail, expectedFirstName, expectedLastName);

        user.addFunds(fund);
        assertEquals(fund, user.getAccountBalance());        
    }

    @ParameterizedTest(name ="Should not add funds =  \"{0}\"")
    @DisplayName("Should Not add funds")
    @ValueSource(doubles= {-1,-0.2,10000,-0.01,0.001, 0})
    void shouldNotAddFunds(double fund){
        User user = new User(validPersonnummer, validEmail, expectedFirstName, expectedLastName);

        Runnable actualAdd = () -> user.addFunds(fund);

        assertThrows(IllegalArgumentException.class,() -> actualAdd.run());        
    }


    @ParameterizedTest(name ="Boundary analysis testing =  \"{0}\"")
    @DisplayName("Boundary analysis testing")
    @ValueSource(doubles= {-2,-1 ,-0.1,-0.01,-0.001,0 ,0.001 ,0.01 ,0.1 ,1, 1000, 2000,10000})
    void boundaryAnal(double fund){
    
    // TODO: Challenge 2.2 - Add Boundary Value Analysis tests for fund addition
    // Hint: Test minimum (0.01), maximum (1000.00), and invalid amounts (0, negative, > 1000)
    

        // Arrange - Set up test data
        User user = new User(validPersonnummer, validEmail, expectedFirstName, expectedLastName);
    
        // Act - Execute the method under test
        if(fund>0.009 && fund < 1001){
            user.addFunds(fund);
            assertEquals(fund, user.getAccountBalance());        
        }
        else{

            assertThrows(IllegalArgumentException.class,() -> user.addFunds(fund));        
        }

        // Assert - Verify the expected outcome


    }
    

    @Test
    @DisplayName("Membership test's ")
    void membership(){
        User user = new User(validPersonnummer, validEmail, expectedFirstName, expectedLastName);


        System.out.println("tese"+MembershipType.BASIC.getDiscountRate());
        System.out.println("tese"+MembershipType.CORPORATE.getDiscountRate());
        System.out.println("tese"+MembershipType.PREMIUM.getDiscountRate());
        System.out.println("tese"+MembershipType.STUDENT.getDiscountRate());
        System.out.println("tese"+MembershipType.VIP.getDiscountRate());

        assertAll(

            () -> assertTrue(MembershipType.BASIC.getDiscountRate()<MembershipType.STUDENT.getDiscountRate()),
            () -> assertTrue(MembershipType.BASIC.getDiscountRate()<MembershipType.PREMIUM.getDiscountRate()),
            () -> assertTrue(MembershipType.BASIC.getDiscountRate()<MembershipType.VIP.getDiscountRate()),
            () -> assertTrue(MembershipType.BASIC.getDiscountRate()<MembershipType.CORPORATE.getDiscountRate()),
            () -> assertTrue(MembershipType.STUDENT.getDiscountRate()!=MembershipType.PREMIUM.getDiscountRate()),
            () -> assertTrue(MembershipType.STUDENT.getDiscountRate()!=MembershipType.VIP.getDiscountRate()),
            () -> assertTrue(MembershipType.STUDENT.getDiscountRate()!=MembershipType.CORPORATE.getDiscountRate()),
            () -> assertTrue(MembershipType.VIP.getDiscountRate()!=MembershipType.PREMIUM.getDiscountRate()),
            () -> assertTrue(MembershipType.VIP.getDiscountRate()!=MembershipType.CORPORATE.getDiscountRate()),
            () -> assertTrue(MembershipType.PREMIUM.getDiscountRate()!=MembershipType.CORPORATE.getDiscountRate())
        );
        
    }


    // TODO: Challenge 2.3 - Add Decision Table tests for phone number validation did you mean membership ?
    // Hint: Test Swedish phone formats (+46701234567, 0701234567) and invalid formats
    



    // TODO: Challenge 2.4 - Add error scenario tests
    // Hint: Test insufficient balance, invalid inputs, state violations
}
