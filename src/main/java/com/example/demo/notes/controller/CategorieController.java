package com.example.demo.notes.controller;

import com.example.demo.notes.entity.Categorie;
import com.example.demo.notes.services.CategorieService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class CategorieController {

    private final CategorieService categorieSercice;

    public CategorieController(CategorieService categorieSercice) {
        this.categorieSercice = categorieSercice;
    }

    // Create new Categorie
    @PostMapping("/categorie")
    public ResponseEntity<Categorie> createCategorie(@RequestBody Categorie categorieRequest) {
        // Call the NoteService to handle categorie creation
        Categorie createdCategorie = categorieSercice.createCategorie(
        		categorieRequest.getLibelle()
        		);
        return ResponseEntity.ok(createdCategorie);
    }
    
    // Get all Users
    @GetMapping("/categories")
    public ResponseEntity<List<Categorie>> getAllCategorie() {
        List<Categorie> categorie = categorieSercice.getAllCategorie();
        return ResponseEntity.ok(categorie);
    }
    
    // Update a User by ID
    @PutMapping("/categorie/{id}")
    public ResponseEntity<Categorie> updateCategorie(
            @PathVariable Long id,
            @RequestBody Categorie categorieRequest
    ) {
        Optional<Categorie> optionalCategorie = categorieSercice.getAllCategorie()
                .stream()
                .filter(categorie -> categorie.getId().equals(id))
                .findFirst();

        if (optionalCategorie.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Categorie categorieToUpdate = optionalCategorie.get();
        categorieToUpdate.setLibelle(categorieRequest.getLibelle());

        // Persist the updated user
        Categorie updatedCategorie = categorieSercice.updateCategorie(categorieToUpdate);

        return ResponseEntity.ok(updatedCategorie);
    }

    // Delete a User by ID
    @DeleteMapping("/categorie/{id}")
    public ResponseEntity<Void> deleteCategorieById(@PathVariable Long id) {
        Optional<Categorie> optionalCategorie = categorieSercice.getAllCategorie()
                .stream()
                .filter(categorie -> categorie.getId().equals(id))
                .findFirst();

        if (optionalCategorie.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        categorieSercice.deleteCategorie(id);
        return ResponseEntity.noContent().build();
    }
}
