package com.eleganceinfoways.pigeeback.model;

/**
 * Created by drindia19 on 8/2/2016.
 */
public class FacebookPojo {

    public String id;

    public Picture picture;

    public String first_name;

    public String email;

    public String name;

    public String link;

    public String last_name;

    public String birthday;

    public String gender;





    public class Picture{

        public Data data;
    }


    public class Data {

        public String is_silhouette;

        public String url;
    }

}