/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.ejb;

import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import co.edu.uniandes.csw.recipes.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recipes.persistence.RecipePersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author CesarF
 */
@Stateless
public class RecipeLogic {
    @Inject
    private RecipePersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
    private static final Logger LOGGER = Logger.getLogger(RecipeLogic.class.getName());


    public RecipeEntity getRecipe(Long id) {
        return persistence.find(id);
    }

    //TODO crear el método createRecipe
    public RecipeEntity createRecipe(RecipeEntity recipeEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la receta");

        // Verifica la regla de negocio que dice que el nombre es valido si no es vacio o nulo
        if (!validateName(recipeEntity.getName())) {
            throw new BusinessLogicException("El nombre es inválido");
        }
        // Verifica la regla de negocio que dice que la descripcion es valida si no es vacia o nula
        if (!validateDescription(recipeEntity.getDescription())) {
            throw new BusinessLogicException("La descripcion es inválida");
        }
        // Verifica la regla de negocio que dice que no puede haber dos recetas con el mismo nombre
        if (persistence.findByName(recipeEntity.getName()) != null) {
            throw new BusinessLogicException("Ya existe una Receta con el nombre \"" + recipeEntity.getName() + "\"");
        }
        
        if(recipeEntity.getName().length()> 30)
        {
            throw new BusinessLogicException("La descripcion es inválida");
        }
        if(recipeEntity.getDescription().length()> 150)
        {
            throw new BusinessLogicException("La descripcion es inválida");
        }

        persistence.create(recipeEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la receta");
        return recipeEntity;
    }
    
     private boolean validateName(String name) {
        return !(name == null || name.isEmpty());
    }
     
    private boolean validateDescription(String description) {
        return !(description == null || description.isEmpty());
    }


}
