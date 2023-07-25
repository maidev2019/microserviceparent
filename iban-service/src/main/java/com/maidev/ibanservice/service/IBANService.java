package com.maidev.ibanservice.service;

import java.util.Map;
import java.util.Random;

import com.maidev.ibanservice.model.Iban;

public class IBANService {
    private static Map<String,String> countries = Map.of("Deutschland","DE", "Großbritanien", "GB", "Griechenland", "GR", "Türkei", "TR");
    private static Map<String,Integer> country_length =  Map.of("Deutschland",22, "Großbritanien",22, "Griechenland",27, "Türkei",26 );
    private static Map<String,Integer> letter_map = Map.ofEntries(
        Map.entry("A", 10), Map.entry("B", 11), Map.entry("C", 12), Map.entry("D", 13), Map.entry("E", 14), Map.entry("F", 15), Map.entry("G", 16), Map.entry("H", 17), Map.entry("I", 18), Map.entry("J", 19), Map.entry("K", 20), Map.entry("L", 21), Map.entry("M", 22), Map.entry("N", 23), Map.entry("O", 24), Map.entry("P", 25), Map.entry("Q", 26), Map.entry("R", 27), Map.entry("S", 28), Map.entry("T", 29), Map.entry("U", 30), Map.entry("V", 31), Map.entry("W", 32), Map.entry("X", 33), Map.entry("Y", 34), Map.entry("Z", 35)
        );

    private static Random random = new Random();
    private static String[] Greek_sort = new String[]{"010", "011", "014", "017"};
    private static String[]  BARCLAYS_sort = new String[]{"200318", "200326", "200353", "200378", "200380", "200384", "200395", "200401", "200404"};

    private static String getRandomNumber(int length) {
        StringBuilder number = new StringBuilder(String.valueOf(Math.random()).substring(2, 12));
        while(number.length() < length){
            number.append(String.valueOf(Math.random()).substring(2, 12));
        }
        return number.substring(0,length);
    }

    private static String bankAccountKey(String checkString) {
        String  str = checkString,
                deb = str.substring(0,12);
        int length = Math.min(str.length(), 12);
        while (!(str = str.substring(length)).equals("")) {
            str = (Long.parseLong(deb) % 97) + str;
            length = Math.min(str.length(), 12);
            deb = str.substring(0,length);
        }
        long rest =98-Long.parseLong(deb)%97;
        return rest < 10 ? "0"+rest: ""+rest;
    }

    private static String calcChecksum(String countryCode, String randomPart) {
        String checkString = randomPart + countryCode + "00";
        checkString = replaceChars(checkString);
        var checksum = bankAccountKey(checkString);

        return countryCode + checksum + randomPart;
    }
    private static String replaceChars( String conv_string) {
        for(var i = 0; i < conv_string.length(); i++) {
            String conv_char = String.valueOf(conv_string.charAt(i));
            if(letter_map.containsKey(conv_char)) {
                var IBAN_conv = letter_map.get(conv_char);
                conv_string = conv_string.replace(conv_char, IBAN_conv.toString());
            }
        }
        return conv_string;
    }

    public static Iban generateIBAN(String country){
        if(!countries.containsKey(country)) {
            return null;
        }
        int IBAN_length = country_length.get(country);  // TR = 26
        String ISO = countries.get(country);            // TR = 2
        int index;

        switch (countries.get(country)){
            case "DE":
                var DE_randomPart = "50010517" + getRandomNumber(IBAN_length - 12);
                var DE_IBAN = calcChecksum(ISO, DE_randomPart);
                return new Iban("DE", DE_IBAN);
            // Method C1, Variant 2, Account 5559749181, Bank Code 50010517, Check digit 1 is at position 10, expected check digit: 1. Overview of the calculation: Take the digits in positions 1 to 9 - here: 555974918 -, Multiply them from the left to the right by the weights 1,2,1,2,1,2,1,2,1, add the transverse sums of the products, form the remainder of the division by 11, subtract the result from 10, Result modulo 11 is the expected reference number.

            case "GB":
                var UK_BIC = "BARC";// _.sample(UK_Banks); // lets brute force some first
                var UK_middle_characters = 4;
                var UK_numeric_suffix = IBAN_length - 4 - UK_middle_characters;
                index = random.nextInt(BARCLAYS_sort.length);
                var UK_numeric_part = "" + BARCLAYS_sort[index] + getRandomNumber(UK_numeric_suffix - 6);
                var UK_cand = UK_BIC + UK_numeric_part + "";
                var UK_IBAN = calcChecksum(ISO, UK_cand);
                return new Iban("GB", UK_IBAN);

            case "GR":
                index = random.nextInt(Greek_sort.length);
                var GR_numeric_part = Greek_sort[index] + getRandomNumber(IBAN_length - 7);
                var GR_IBAN = calcChecksum(ISO, GR_numeric_part);
                return new Iban("GR", GR_IBAN);

            case "TR":
            //int IBAN_length = country_length.get(country);  // TR = 26
            //String ISO = countries.get(country);            // TR = 2
                var TR_numeric_part = "00062" + getRandomNumber(IBAN_length - 9);
                var TR_IBAN = calcChecksum(ISO, TR_numeric_part);
                return new Iban("TR", TR_IBAN);

            default:
                break;
        }
        return null ;
    }

    public static boolean validate(String toValidateIBAN) {

        return false;
    }
}
