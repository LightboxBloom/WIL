<<<<<<< HEAD
package com.katherine.bloomuii.Games.MatchShape;

import android.graphics.drawable.Drawable;

public class Shape
{
    String shape_Name;
    Drawable shape_Resource;

    public String getShape_Name() {
        return shape_Name;
    }

    public void setShape_Name(String shape_Name) {
        this.shape_Name = shape_Name;
    }

    public Drawable getShape_Resource() {
        return shape_Resource;
    }

    public void setShape_Resource(Drawable shape_Resource) {
        this.shape_Resource = shape_Resource;
    }



    public Shape(String shape_Name, Drawable shape_Resource) {
        this.shape_Name = shape_Name;
        this.shape_Resource = shape_Resource;
    }


}
=======
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
>>>>>>> master
