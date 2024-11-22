package com.example.demo.notes.services;

import com.example.demo.notes.entity.Categorie;
import com.example.demo.notes.repository.CategorieRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategorieService {

    // Replace with your actual repository injection
    private final CategorieRepository categorieRepository;

    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }
    
    public Categorie createCategorie(String libelle) {
    	Categorie categorie = new Categorie();
    	categorie.setLibelle(libelle);
    	categorie.setDateCreation(LocalDateTime.now());
    	categorie.setDateModification(LocalDateTime.now());
        return categorieRepository.save(categorie);
    }

    public List<Categorie> getAllCategorie() {
        return categorieRepository.findAll();
    }

    public Categorie updateCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public void deleteCategorie(Long id) {
    	categorieRepository.deleteById(id);
    }
}
