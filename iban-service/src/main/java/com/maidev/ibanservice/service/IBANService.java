package com.maidev.ibanservice.service;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.maidev.ibanservice.model.Iban;

@Service
public class IBANService {
    private class CountryInfo {
        private String isoCode;
        private int length;
        private String[] sortCodes;

        CountryInfo(String isoCode, int length, String[] sortCodes) {
            this.isoCode = isoCode;
            this.length = length;
            this.sortCodes = sortCodes;
        }
    }

    private Map<String, CountryInfo> countries = Map.of(
            "Deutschland", new CountryInfo("DE", 22, null),
            "Großbritanien", new CountryInfo("GB", 22, new String[]{"200318", "200326", "200353", "200378", "200380", "200384", "200395", "200401", "200404"}),
            "Griechenland", new CountryInfo("GR", 27, new String[]{"010", "011", "014", "017"}),
            "Türkei", new CountryInfo("TR", 26, null)
    );

    private Map<Character, Integer> letter_map = new HashMap<>();

    {
        for (char c = 'A'; c <= 'Z'; c++) {
            letter_map.put(c, c - 'A' + 10);
        }
    }

    private Random random = new Random();

    private String getRandomNumber(int length) {
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < length; i++) {
            number.append(random.nextInt(10));
        }
        return number.toString();
    }

    private String bankAccountKey(String checkString) {
        String str = checkString;
        String deb = str.substring(0, 12);
        int length = Math.min(str.length(), 12);
        while (!(str = str.substring(length)).equals("")) {
            str = (Long.parseLong(deb) % 97) + str;
            length = Math.min(str.length(), 12);
            deb = str.substring(0, length);
        }
        long rest = 98 - Long.parseLong(deb) % 97;
        return (rest < 10 ? "0" : "") + rest;
    }

    private String calcChecksum(String countryCode, String randomPart) {
        String checkString = randomPart + countryCode + "00";
        checkString = replaceChars(checkString);
        String checksum = bankAccountKey(checkString);

        return countryCode + checksum + randomPart;
    }

    private String replaceChars(String conv_string) {
        StringBuilder result = new StringBuilder(conv_string.length());
        for (char c : conv_string.toCharArray()) {
            int value = letter_map.containsKey(c) ? letter_map.get(c) : Character.getNumericValue(c);
            result.append(value);
        }
        return result.toString();
    }

    private String generateRandomNumericPart(int length, String[] sortCodes) {
        if (sortCodes != null) {
            String sortCode = sortCodes[random.nextInt(sortCodes.length)];
            int numericSuffixLength = length - sortCode.length() - 6;
            return sortCode + getRandomNumber(numericSuffixLength);
        }
        return "00062" + getRandomNumber(length - 9);
    }

    public Iban generateIBAN(String country) {
        CountryInfo countryInfo = countries.get(country);
        if (countryInfo == null) {
            return null;
        }

        int IBAN_length = countryInfo.length;
        String ISO = countryInfo.isoCode;

        String randomPart = getRandomNumber(IBAN_length - 12);
        String numericPart = generateRandomNumericPart(IBAN_length, countryInfo.sortCodes);

        String IBAN = calcChecksum(ISO, numericPart + randomPart);
        return new Iban(ISO, IBAN);
    }

    public boolean validateIBAN(String iban) {
       String countryCode = iban.substring(0, 2);
        int IBAN_length = iban.length();

        CountryInfo countryInfo = countries.values().stream()
                .filter(info -> info.isoCode.equals(countryCode))
                .findFirst()
                .orElse(null);

        if (countryInfo == null || IBAN_length != countryInfo.length) {
            return false;
        }

        String numericPart = iban.substring(4) + iban.substring(0, 4);
        numericPart = replaceChars(numericPart);
        BigInteger numericValue = new BigInteger(numericPart);

        // Perform modulo-97 check
        if (numericValue.mod(BigInteger.valueOf(97)).intValue() != 1) {
            return false;
        }

        return true;
    }
}
