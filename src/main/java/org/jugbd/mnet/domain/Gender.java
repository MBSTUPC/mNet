package org.jugbd.mnet.domain;

/**
 * Created by Raqibul Islam on 7/1/14.
 */
public enum Gender {

    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private String label;

    Gender(String label) {
        this.label = label;
    }
}
