/**
 * @auhtor Sullivan Fair (sffair)
 */

package com.sffair.sffair_Experiment4.animal;

public class Animal
{
    private String species;
    private String habitat;
    private String call;

    public Animal()
    {}

    public Animal(String species, String habitat, String call)
    {
        this.species = species;
        this.habitat = habitat;
        this.call = call;
    }

    public String getSpecies() {
        return this.species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getHabitat() {
        return this.habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getCall() {
        return this.call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    @Override
    public String toString() {
        return species + " "
                + habitat + " "
                + call;
    }
}
