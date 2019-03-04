/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.dtos;

import co.edu.uniandes.csw.recipes.entities.IngredientEntity;
import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CesarF
 */
public class RecipeDetailDTO extends RecipeDTO {
    
    private List<IngredientDTO> ingredientes;
    public RecipeDetailDTO(){
    
    }
    
    public RecipeDetailDTO(RecipeEntity entity){
        super(entity);
        if (entity != null) {
            ingredientes = new ArrayList<>();
            for (IngredientEntity entityBooks : entity.getIngredientes()) {
                ingredientes.add(new IngredientDTO(entityBooks));
            }
        }
    }
    
    @Override
    public RecipeEntity toEntity() {
        RecipeEntity entity = super.toEntity();
        if (ingredientes != null) {
            List<IngredientEntity> ingredientesEntity = new ArrayList<>();
            for (IngredientDTO dtoIngrediente : ingredientes) {
                ingredientesEntity.add(dtoIngrediente.toEntity());
            }
            entity.setIngredientes(ingredientesEntity);
        }
        return entity;
    }

    /**
     * @return the ingredientes
     */
    public List<IngredientDTO> getIngredientes() {
        return ingredientes;
    }

    /**
     * @param ingredientes the ingredientes to set
     */
    public void setIngredientes(List<IngredientDTO> ingredientes) {
        this.ingredientes = ingredientes;
    }
    
}
