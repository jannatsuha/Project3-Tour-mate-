package com.group.avengers.tourmate.Classes;

public class CameraContainer {

    private String imageName;

    private String imageURL;

    private String dateTime;

    private String id;

    public CameraContainer() {

    }

    public CameraContainer(String name, String url,String dateTime) {

        this.imageName = name;
        this.imageURL= url;
        this.dateTime=dateTime;
    }

    public CameraContainer( String id,String imageName, String imageURL, String dateTime) {
        this.id = id;
        this.imageName = imageName;
        this.imageURL = imageURL;
        this.dateTime = dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getId() {
        return id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageURL() {
        return imageURL;
    }
}
