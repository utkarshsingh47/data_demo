package com.utkarsh.datedemo;

public class DateModel {
    private String date;
    private boolean checked;
    private boolean imageClicked;

    public DateModel() {
        // Default constructor for Firebase
    }

    public DateModel(String date, boolean checked, boolean imageClicked) {
        this.date = date;
        this.checked = checked;
        this.imageClicked = imageClicked;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isImageClicked() {
        return imageClicked;
    }

    public void setImageClicked(boolean imageClicked) {
        this.imageClicked = imageClicked;
    }
}
