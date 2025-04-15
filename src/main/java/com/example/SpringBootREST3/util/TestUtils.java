package com.example.SpringBootREST3.util;

public class TestUtils {

    public static String testMethod(String input){
        return input.toUpperCase();
    }

    private String pvMethod(Integer id) {
        return String.valueOf(id + 10);
    }

    public void checkPet(String heroName){
        String pet = "";
        switch (heroName){
            case "Feluda":
            case "Byomkesh":
                pet = "Mogojastro";
                break;
            case "Tintin":
                pet = "Snowy";
                break;
            case "Asterix":
                pet = "Obelix";
                break;
            default:
                pet = "Other";
        }
        System.out.println(pet);
    }

    public String checkPetThrowsException(String heroName) {
        heroName = null;
        return heroName.toUpperCase();
    }
}
