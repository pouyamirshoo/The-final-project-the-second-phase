package com.example.finalprojectsecondphase.util.validation;

import com.example.finalprojectsecondphase.exception.WrongImageInputException;
import org.springframework.stereotype.Component;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
@Component
public class TakeAndCheckImage {
    public byte[] expertImage(String path) {
        byte[] image = new byte[0];
        File inputImage = new File(path);
        String mimetype= new MimetypesFileTypeMap().getContentType(inputImage);
        String type = mimetype.split("/")[0];
        if(type.equals("image")) {
            try {
                image = Files.readAllBytes(inputImage.toPath());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return image;
        }
        else
            throw new WrongImageInputException("this file is not jpg");
    }
}
