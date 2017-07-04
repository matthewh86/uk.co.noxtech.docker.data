package uk.co.noxtech.docker.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.i18n.phonenumbers.CountryCodeToRegionCodeMap;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import java.io.IOException;
import java.util.UUID;

public final class Telephone {

    private final UUID id;

    private final PhoneNumber phoneNumber;

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    public Telephone(String defaultRegion, String number) throws NumberParseException {
        this.id = UUID.randomUUID();
        this.phoneNumber = phoneNumberUtil.parse(number, defaultRegion);

    }

    public Telephone(String defaultRegion, String number, String countryCode) throws NumberParseException {
        this.id = UUID.randomUUID();
        int countryCodeForRegion = phoneNumberUtil.getCountryCodeForRegion(countryCode);
        this.phoneNumber = phoneNumberUtil.parse("+" + countryCodeForRegion + number, defaultRegion);
    }

    public UUID getId() {
        return id;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public String getE164Number() {
        return phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
    }

    @Override
    public String toString() {
        return "id: " + id+ " E.164: " + getE164Number();
    }

    public String toJsonString() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

    public static Telephone parseJsonAsTelephone(String json) throws IOException {
        return new ObjectMapper().readValue(json, Telephone.class);
    }
}
