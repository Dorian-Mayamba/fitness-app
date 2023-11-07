package uk.ac.aston.cs3mdd.fitnessapp.products;

import java.io.Serializable;

public class FoodProduct implements Serializable{
   private int id;

   private String title;

   private String image;

   private String imageType;

   @Override
   public String toString() {
      return "FoodProduct{" +
              "id=" + id +
              ", title='" + title + '\'' +
              ", image='" + image + '\'' +
              ", imageType='" + imageType + '\'' +
              '}';
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public String getImageType() {
      return imageType;
   }

   public void setImageType(String imageType) {
      this.imageType = imageType;
   }
}
