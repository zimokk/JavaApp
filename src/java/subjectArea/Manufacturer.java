/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subjectArea;

public class Manufacturer {
    protected String title;
    protected String country;
    
    public Manufacturer(String title,  String country){
        this.title = title;
        this.country = country;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }
}
