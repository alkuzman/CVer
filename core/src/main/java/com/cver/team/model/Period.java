package com.cver.team.model;

import java.time.LocalDate;

/**
 * Created by Dimitar on 7/7/2016.
 */
public class Period {

    private LocalDate startDate;

    private LocalDate endDate;

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
