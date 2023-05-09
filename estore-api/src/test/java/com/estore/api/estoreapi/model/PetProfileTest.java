package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Profiles.PetProfile;
import com.estore.api.estoreapi.model.Profiles.Profile;


@Tag("Model-tier")
public class PetProfileTest {

    public void testCtor(){

        String expected_name = "Max";
        int expected_age = 12;
        String expected_breed = "lhasa-apso";
        String expected_temperament = "mean";
        String expected_extraInfo = "NA";
        boolean expected_nails = true;
        boolean expected_ear = false;
        boolean expected_bath = false;
        String expected_style = "short";
        boolean expected_cut = false;
        String expected_username = "Cozmic";

        PetProfile petprofile = new PetProfile(expected_name, expected_age, expected_breed, expected_temperament, expected_extraInfo, expected_nails, expected_ear, expected_bath, expected_style, expected_cut, expected_username);

        assertEquals(expected_name, petprofile.getName());
        assertEquals(expected_age, petprofile.getAge());
        assertEquals(expected_breed, petprofile.getBreed());
        assertEquals(expected_temperament, petprofile.getTemperament());
        assertEquals(expected_extraInfo, petprofile.getExtraInfo());
        assertEquals(expected_nails, petprofile.getNails());
        assertEquals(expected_ear, petprofile.getEar());
        assertEquals(expected_bath, petprofile.getBath());
        assertEquals(expected_style, petprofile.getStyle());
        assertEquals(expected_cut, petprofile.getCut());
        assertEquals(expected_username, petprofile.getUsername());

    }

    @Test
    public void testName(){

        String name = "Max";
        int expected_age = 12;
        String expected_breed = "lhasa-apso";
        String expected_temperament = "mean";
        String expected_extraInfo = "NA";
        boolean expected_nails = true;
        boolean expected_ear = false;
        boolean expected_bath = false;
        String expected_style = "short";
        boolean expected_cut = false;
        String expected_username = "Cozmic";

        PetProfile petprofile = new PetProfile(name, expected_age, expected_breed, expected_temperament, expected_extraInfo, expected_nails, expected_ear, expected_bath, expected_style, expected_cut, expected_username);

        String expected_name = "Maxwell";

        petprofile.setName(expected_name);


        assertEquals(expected_name, petprofile.getName());

    }

    @Test
    public void testAge(){
        String expected_name = "Max";
        int age = 12;
        String expected_breed = "lhasa-apso";
        String expected_temperament = "mean";
        String expected_extraInfo = "NA";
        boolean expected_nails = true;
        boolean expected_ear = false;
        boolean expected_bath = false;
        String expected_style = "short";
        boolean expected_cut = false;
        String expected_username = "Cozmic";

        PetProfile petprofile = new PetProfile(expected_name, age, expected_breed, expected_temperament, expected_extraInfo, expected_nails, expected_ear, expected_bath, expected_style, expected_cut, expected_username);

        int expected_age = 13;

        petprofile.setAge(expected_age);


        assertEquals(expected_age, petprofile.getAge());


    }

    @Test
    public void testBreed(){

        String expected_name = "Max";
        int expected_age = 12;
        String breed = "lhasa-apso";
        String expected_temperament = "mean";
        String expected_extraInfo = "NA";
        boolean expected_nails = true;
        boolean expected_ear = false;
        boolean expected_bath = false;
        String expected_style = "short";
        boolean expected_cut = false;
        String expected_username = "Cozmic";

        PetProfile petprofile = new PetProfile(expected_name, expected_age, breed, expected_temperament, expected_extraInfo, expected_nails, expected_ear, expected_bath, expected_style, expected_cut, expected_username);
        
        String expected_breed = "poodle";

        petprofile.setBreed(expected_breed);


        assertEquals(expected_breed, petprofile.getBreed());


    }

    @Test
    public void testTemperament(){

        String expected_name = "Max";
        int expected_age = 12;
        String expected_breed = "lhasa-apso";
        String temperament = "mean";
        String expected_extraInfo = "NA";
        boolean expected_nails = true;
        boolean expected_ear = false;
        boolean expected_bath = false;
        String expected_style = "short";
        boolean expected_cut = false;
        String expected_username = "Cozmic";

        PetProfile petprofile = new PetProfile(expected_name, expected_age, expected_breed, temperament, expected_extraInfo, expected_nails, expected_ear, expected_bath, expected_style, expected_cut, expected_username);
        
        String expected_temperament = "nice";

        petprofile.setTemperament(expected_temperament);


        assertEquals(expected_temperament, petprofile.getTemperament());

    }

    @Test
    public void testExtraInfo(){

        String expected_name = "Max";
        int expected_age = 12;
        String expected_breed = "lhasa-apso";
        String expected_temperament = "mean";
        String extraInfo = "NA";
        boolean expected_nails = true;
        boolean expected_ear = false;
        boolean expected_bath = false;
        String expected_style = "short";
        boolean expected_cut = false;
        String expected_username = "Cozmic";

        PetProfile petprofile = new PetProfile(expected_name, expected_age, expected_breed, expected_temperament, extraInfo, expected_nails, expected_ear, expected_bath, expected_style, expected_cut, expected_username);
        
        String expected_extraInfo = "Be gentle";

        petprofile.setExtraInfo(expected_extraInfo);


        assertEquals(expected_extraInfo, petprofile.getExtraInfo());

    }

    @Test
    public void testNails(){

        String expected_name = "Max";
        int expected_age = 12;
        String expected_breed = "lhasa-apso";
        String expected_temperament = "mean";
        String expected_extraInfo = "NA";
        boolean nails = true;
        boolean expected_ear = false;
        boolean expected_bath = false;
        String expected_style = "short";
        boolean expected_cut = false;
        String expected_username = "Cozmic";

        PetProfile petprofile = new PetProfile(expected_name, expected_age, expected_breed, expected_temperament, expected_extraInfo, nails, expected_ear, expected_bath, expected_style, expected_cut, expected_username);
        
        boolean expected_nails = false;

        petprofile.setNail(expected_nails);


        assertEquals(expected_nails, petprofile.getNails());

    }

    @Test
    public void testEars(){

        String expected_name = "Max";
        int expected_age = 12;
        String expected_breed = "lhasa-apso";
        String expected_temperament = "mean";
        String expected_extraInfo = "NA";
        boolean expected_nails = true;
        boolean ear = false;
        boolean expected_bath = false;
        String expected_style = "short";
        boolean expected_cut = false;
        String expected_username = "Cozmic";

        PetProfile petprofile = new PetProfile(expected_name, expected_age, expected_breed, 
        expected_temperament, expected_extraInfo, expected_nails, ear, expected_bath, 
        expected_style, expected_cut, expected_username);

        boolean expected_ear = true;

        petprofile.setEarClean(expected_ear);


        assertEquals(expected_ear, petprofile.getEar());
        
    }

    @Test
    public void testBath(){

        String expected_name = "Max";
        int expected_age = 12;
        String expected_breed = "lhasa-apso";
        String expected_temperament = "mean";
        String expected_extraInfo = "NA";
        boolean expected_nails = true;
        boolean expected_ear = false;
        boolean bath = false;
        String expected_style = "short";
        boolean expected_cut = false;
        String expected_username = "Cozmic";

        PetProfile petprofile = new PetProfile(expected_name, expected_age, expected_breed, 
        expected_temperament, expected_extraInfo, expected_nails, expected_ear, bath, 
        expected_style, expected_cut, expected_username);

        boolean expected_bath = true;

        petprofile.setBath(expected_bath);


        assertEquals(expected_bath, petprofile.getBath());
        
    }

    @Test
    public void testStyle(){

        String expected_name = "Max";
        int expected_age = 12;
        String expected_breed = "lhasa-apso";
        String expected_temperament = "mean";
        String expected_extraInfo = "NA";
        boolean expected_nails = true;
        boolean expected_ear = false;
        boolean expected_bath = false;
        String style = "short";
        boolean expected_cut = false;
        String expected_username = "Cozmic";

        PetProfile petprofile = new PetProfile(expected_name, expected_age, expected_breed, 
        expected_temperament, expected_extraInfo, expected_nails, expected_ear, expected_bath, 
        style, expected_cut, expected_username);

        String expected_style = "long";

        petprofile.setStyle(expected_style);


        assertEquals(expected_style, petprofile.getStyle());
        
    }

    @Test
    public void testCut(){

        String expected_name = "Max";
        int expected_age = 12;
        String expected_breed = "lhasa-apso";
        String expected_temperament = "mean";
        String expected_extraInfo = "NA";
        boolean expected_nails = true;
        boolean expected_ear = false;
        boolean expected_bath = false;
        String expected_style = "short";
        boolean cut = false;
        String expected_username = "Cozmic";

        boolean expected_cut = true;

        PetProfile petprofile = new PetProfile(expected_name, expected_age, expected_breed, 
        expected_temperament, expected_extraInfo, expected_nails, expected_ear, expected_bath, 
        expected_style, cut, expected_username);

        petprofile.setCut(expected_cut);


        assertEquals(expected_cut, petprofile.getCut());
        
    }

    @Test
    public void testUsername(){

        String expected_name = "Max";
        int expected_age = 12;
        String expected_breed = "lhasa-apso";
        String expected_temperament = "mean";
        String expected_extraInfo = "NA";
        boolean expected_nails = true;
        boolean expected_ear = false;
        boolean expected_bath = false;
        String expected_style = "short";
        boolean expected_cut = false;
        String username = "Cozmic";

        String expected_username = "biddy";

        PetProfile petprofile = new PetProfile(expected_name, expected_age, expected_breed, 
        expected_temperament, expected_extraInfo, expected_nails, expected_ear, expected_bath, 
        expected_style, expected_cut, username);

        petprofile.setUsername(expected_username);


        assertEquals(expected_username, petprofile.getUsername());
        
    }


    @Test
    public void testToString(){

        //Setup
        String expected_name = "Max";
        int expected_age = 12;
        String expected_breed = "lhasa-apso";
        String expected_temperament = "mean";
        String expected_extraInfo = "NA";
        boolean expected_nails = true;
        boolean expected_ear = false;
        boolean expected_bath = false;
        String expected_style = "short";
        boolean expected_cut = false;
        String expected_username = "Cozmic";

        PetProfile petprofile = new PetProfile(expected_name, expected_age, expected_breed, expected_temperament, expected_extraInfo, expected_nails, expected_ear, expected_bath, expected_style, expected_cut, expected_username);

        String expected_string = String.format(PetProfile.STRING_FORMAT,expected_name, expected_age, expected_breed, expected_temperament, expected_extraInfo, expected_nails, expected_ear, expected_bath, expected_style, expected_cut);
        
        //Invoke
        String actual_string = petprofile.toString();

        //Analyze
        assertEquals(expected_string,actual_string);

    }

    
}
