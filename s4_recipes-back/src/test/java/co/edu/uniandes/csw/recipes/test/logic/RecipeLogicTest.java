/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.test.logic;

import co.edu.uniandes.csw.recipes.ejb.RecipeLogic;
import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import co.edu.uniandes.csw.recipes.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recipes.persistence.RecipePersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.*;

/**
 *
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class RecipeLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private RecipeLogic recipeLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<RecipeEntity> data = new ArrayList<>();


    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RecipeEntity.class.getPackage())
                .addPackage(RecipeLogic.class.getPackage())
                .addPackage(RecipePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from RecipeEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            RecipeEntity entity = factory.manufacturePojo(RecipeEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createRecipeTest() throws BusinessLogicException {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        RecipeEntity result = recipeLogic.createRecipe(newEntity);
        Assert.assertNotNull(result);
        RecipeEntity entity = em.find(RecipeEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createRecipeConMismoNombreTest() throws BusinessLogicException {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setName(data.get(0).getName());
        recipeLogic.createRecipe(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createRecipeTestConNombreInvalido() throws BusinessLogicException {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setName("");
        recipeLogic.createRecipe(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createRecipeTestConNombreInvalido2() throws BusinessLogicException {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setName(null);
        recipeLogic.createRecipe(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createRecipeTestConNombreInvalido3() throws BusinessLogicException {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setName("llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll");
        recipeLogic.createRecipe(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createRecipeTestConDescripcionInvalida() throws BusinessLogicException {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setDescription("");
        recipeLogic.createRecipe(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createRecipeTestConDescripcionInvalida2() throws BusinessLogicException {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setDescription(null);
        recipeLogic.createRecipe(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createRecipeTestConDescripcionInvalida3() throws BusinessLogicException {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setDescription("lkasnlkncklnclascnalcnlasncalsncasklcnlasnlasnkclandjvnkajbnjkasnckajsnclaskcnalskcnslakcnalkscnklascnklaslalskanckasncklanckajsckajscnkjasncjkasncjkasncjkasncjkasncjkasnckjasncajksncajkscnaskjcnaskjcnajkscnkjasncjkasncsjkancajkcsnkjascnjkasc");
        recipeLogic.createRecipe(newEntity);
    }
}
