package uk.co.noxtech.docker.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import java.io.IOException;
import java.util.UUID;

import static com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.E164;

public final class Telephone {

    private final UUID id;

    private final PhoneNumber phoneNumber;

    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    public Telephone() {
        this(new PhoneNumber());
    }

    public Telephone(PhoneNumber phoneNumber) {
        this.id = UUID.randomUUID();
        this.phoneNumber = phoneNumber;
    }

    public UUID getId() {
        return id;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    @JsonIgnore
    public String getE164Number() {
        return phoneNumberUtil.format(phoneNumber, E164);
    }

    @Override
    public String toString() {
        return "id: " + id + " phoneNumber: " + phoneNumber.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Telephone telephone = (Telephone) o;

        if (id != null ? !id.equals(telephone.id) : telephone.id != null) return false;

        // TODO: find out why ObjectMapper creates a different object from JSON generated
        return phoneNumber != null ?
                phoneNumberUtil.format(phoneNumber, E164)
                        .equals(phoneNumberUtil.format(telephone.phoneNumber, E164))
                : telephone.phoneNumber == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    public String toJsonString() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

    public static Telephone parseJsonAsTelephone(String json) throws IOException {
        return new ObjectMapper().readValue(json, Telephone.class);
    }

    public static PhoneNumber createPhoneNumber(String number, String defaultRegionCode) throws NumberParseException {
        return phoneNumberUtil.parse(number, defaultRegionCode);
    }

    public static PhoneNumber createPhoneNumber(String number, String defaultRegionCode, String regionCode) throws NumberParseException {
        int countryCodeForRegion = phoneNumberUtil.getCountryCodeForRegion(regionCode);
        return phoneNumberUtil.parse("+" + countryCodeForRegion + "" + number, defaultRegionCode);
    }
}
