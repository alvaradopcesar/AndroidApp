package iosco.app.model.entity;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Victor Casas on 09/02/2016.
 */
public class Seccion {
    private int id;
    private ImageView icon;
    private TextView text;
    private RelativeLayout container;
    private int imageA;
    private int imageB;
    private int colorA;
    private int colorB;

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }

    public RelativeLayout getContainer() {
        return container;
    }

    public void setContainer(RelativeLayout container) {
        this.container = container;
    }

    public int getImageA() {
        return imageA;
    }

    public void setImageA(int imageA) {
        this.imageA = imageA;
    }

    public int getImageB() {
        return imageB;
    }

    public void setImageB(int imageB) {
        this.imageB = imageB;
    }

    public int getColorA() {
        return colorA;
    }

    public void setColorA(int colorA) {
        this.colorA = colorA;
    }

    public int getColorB() {
        return colorB;
    }

    public void setColorB(int colorB) {
        this.colorB = colorB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void changeActive(boolean active){
        if(active){
            icon.setImageResource(imageB);
            container.setBackgroundColor(colorB);
            text.setTextColor(colorA);
        }else{
            icon.setImageResource(imageA);
            container.setBackgroundColor(colorA);
            text.setTextColor(colorB);
        }
    }
}
