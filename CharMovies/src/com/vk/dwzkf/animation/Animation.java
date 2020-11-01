package com.vk.dwzkf.animation;

import java.util.ArrayList;
import java.util.List;

public class Animation {
    private int currImage = 0;
    private List<Image> images = new ArrayList<>();

    public void addImage(Image image) {
        images.add(image);
    }

    public char[][] getCharsImage() {
        return images.get(currImage).getImage();
    }

    public Image getImage() {
        return images.get(currImage);
    }

    public Image getImageAndNext() {
        Image image = getImage();
        currImage++;
        currImage = currImage % images.size();
        return image;
    }

    public int imagesCount() {
        return images.size();
    }
}