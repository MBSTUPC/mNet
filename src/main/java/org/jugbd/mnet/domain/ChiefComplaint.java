package org.jugbd.mnet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jugbd.mnet.domain.enums.ChildBornWith;

import javax.persistence.*;

/**
 * @author Bazlur Rahman Rokon
 * @date 11/26/2014.
 */
@Entity
public class ChiefComplaint extends PersistentObject implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer hoursOfBurn;
    private Integer daysOfBurns;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private ChildBornWith childBornWith;

    private Integer hoursOfTrauma;
    private Integer daysOfTrauma;

    private Integer hoursOfInfection;
    private Integer daysOfInfection;

    private Integer daysOfUlcerOrSwellingFor; //Presented with ulcer or swelling for
    private Integer monthOfUlcerOrSwellingFor; //Presented with ulcer or swelling for
    private Integer yearsOfUlcerOrSwellingFor; //Presented with ulcer or swelling for

    private String breastRelatedComplaint; //TODO have to figure out what they meant actually

    private String presentIllness;

    @JsonIgnore
    @OneToOne(mappedBy = "chiefComplaint")
    private Register register;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHoursOfBurn() {
        return hoursOfBurn;
    }

    public void setHoursOfBurn(Integer hoursOfBurn) {
        this.hoursOfBurn = hoursOfBurn;
    }

    public Integer getDaysOfBurns() {
        return daysOfBurns;
    }

    public void setDaysOfBurns(Integer daysOfBurns) {
        this.daysOfBurns = daysOfBurns;
    }

    public ChildBornWith getChildBornWith() {
        return childBornWith;
    }

    public void setChildBornWith(ChildBornWith childBornWith) {
        this.childBornWith = childBornWith;
    }

    public Integer getHoursOfTrauma() {
        return hoursOfTrauma;
    }

    public void setHoursOfTrauma(Integer hoursOfTrauma) {
        this.hoursOfTrauma = hoursOfTrauma;
    }

    public Integer getDaysOfTrauma() {
        return daysOfTrauma;
    }

    public void setDaysOfTrauma(Integer daysOfTrauma) {
        this.daysOfTrauma = daysOfTrauma;
    }

    public Integer getHoursOfInfection() {
        return hoursOfInfection;
    }

    public void setHoursOfInfection(Integer hoursOfInfection) {
        this.hoursOfInfection = hoursOfInfection;
    }

    public Integer getDaysOfInfection() {
        return daysOfInfection;
    }

    public void setDaysOfInfection(Integer daysOfInfection) {
        this.daysOfInfection = daysOfInfection;
    }

    public Integer getDaysOfUlcerOrSwellingFor() {
        return daysOfUlcerOrSwellingFor;
    }

    public void setDaysOfUlcerOrSwellingFor(Integer daysOfUlcerOrSwellingFor) {
        this.daysOfUlcerOrSwellingFor = daysOfUlcerOrSwellingFor;
    }

    public Integer getMonthOfUlcerOrSwellingFor() {
        return monthOfUlcerOrSwellingFor;
    }

    public void setMonthOfUlcerOrSwellingFor(Integer monthOfUlcerOrSwellingFor) {
        this.monthOfUlcerOrSwellingFor = monthOfUlcerOrSwellingFor;
    }

    public Integer getYearsOfUlcerOrSwellingFor() {
        return yearsOfUlcerOrSwellingFor;
    }

    public void setYearsOfUlcerOrSwellingFor(Integer yearsOfUlcerOrSwellingFor) {
        this.yearsOfUlcerOrSwellingFor = yearsOfUlcerOrSwellingFor;
    }

    public String getBreastRelatedComplaint() {
        return breastRelatedComplaint;
    }

    public void setBreastRelatedComplaint(String breastRelatedComplaint) {
        this.breastRelatedComplaint = breastRelatedComplaint;
    }

    public String getPresentIllness() {
        return presentIllness;
    }

    public void setPresentIllness(String presentIllness) {
        this.presentIllness = presentIllness;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }
}
