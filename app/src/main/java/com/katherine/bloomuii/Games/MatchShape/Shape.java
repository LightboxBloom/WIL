package com.katherine.bloomuii.Games.MatchShape;

import android.graphics.drawable.Drawable;
//Shape
//Object created to handle all shape objects
//Matthew Talbot 02/09/2020 v2
public class Shape
{
    String shapeName;
    Drawable shapeResource;

    public String getShapeName() {
        return shapeName;
    }

    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    public Drawable getShapeResource() {
        return shapeResource;
    }

    public void setShapeResource(Drawable shapeResource) {
        this.shapeResource = shapeResource;
    }

    /*    Shape
    Constructor for object class.
    Version 1  */
    public Shape(String shapeName, Drawable shapeResource) {
        this.shapeName = shapeName;
        this.shapeResource = shapeResource;
    }

}
