package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog
        Blog blog = blogRepository2.findById(blogId).get();
        Image image = new Image();
        image.setDescription(description);
        image.setDimensions(dimensions);
        image.setBlog(blog);

        blog.getImageList().add(image);

        blogRepository2.save(blog);

        return image;
    }

    public void deleteImage(Integer id){

        Image image = imageRepository2.findById(id).get();
        Blog blog = image.getBlog();

        blog.getImageList().remove(image);

        blogRepository2.save(blog);

        imageRepository2.deleteById(id);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`

        Image image = imageRepository2.findById(id).get();
        String imageDimention = image.getDimensions();

        int length = imageDimention.charAt(0) - '0';
        int breadth = imageDimention.charAt(2) - '0';

        int screenLength = screenDimensions.charAt(0) -'0';
        int screenBreadth = screenDimensions.charAt(2) - '0';

        int count = 0;

        while(length<=screenLength && breadth<=screenBreadth){

            // row wise
            int tempBreadth = screenBreadth;
            while(tempBreadth>=breadth){
                tempBreadth -= breadth;
                count++;
            }

            screenLength -= length;

        }

        return count;

    }
}
