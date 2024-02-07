/**
 * @author Sullivan Fair (sffair)
 */

package com.sffair.sffair_Experiment4.animal;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
public class AnimalController
{
    HashMap<String, Animal> animalList = new HashMap<>();

    @GetMapping("/animal")
    public @ResponseBody HashMap<String, Animal> getAllAnimals()
    {
        return animalList;
    }

    @PostMapping("/animal")
    public @ResponseBody String createAnimal(@RequestBody Animal animal)
    {
        System.out.println(animal);
        animalList.put(animal.getSpecies(), animal);
        return "New animal " + animal.getSpecies() + " added to the Zoo!";
    }

    @GetMapping("/animal/{species}")
    public @ResponseBody Animal getAnimal(@PathVariable String species)
    {
        return animalList.get(species);
    }

    @PutMapping("/animal/{species}")
    public @ResponseBody Animal updateAnimal(@PathVariable String species, @RequestBody Animal animal)
    {
        animalList.replace(species, animal);
        return animalList.get(species);
    }

    @DeleteMapping("/animal/{species}")
    public @ResponseBody HashMap<String, Animal> deleteAnimal(@PathVariable String species)
    {
        animalList.remove(species);
        return animalList;
    }
}
