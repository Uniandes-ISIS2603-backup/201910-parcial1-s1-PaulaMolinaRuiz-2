/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.dtos;

import co.edu.uniandes.csw.recipes.entities.IngredientEntity;

/**
 *
 * @author estudiante
 */
public class IngredientDTO {
    
        private String name;
        private Long calories;
        private Long id;
        
    public IngredientDTO() {
    }
    
    public IngredientDTO(IngredientEntity ingredientEntity) {
        if (ingredientEntity != null) {
            this.id = ingredientEntity.getId();
            this.name = ingredientEntity.getName();
            this.calories = ingredientEntity.getCalories();             
        }
    }
    
    public IngredientEntity toEntity() {
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setId(this.getId());
        ingredientEntity.setName(this.getName());
        ingredientEntity.setCalories(this.getCalories());
        return ingredientEntity;
    }
        
        
        
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the calories
     */
    public Long getCalories() {
        return calories;
    }

    /**
     * @param calories the calories to set
     */
    public void setCalories(Long calories) {
        this.calories = calories;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
}
