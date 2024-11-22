package com.example.demo.notes.services;

import com.example.demo.notes.entity.Note;
import com.example.demo.notes.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note createNote(String auteur, String titre, String contenu) {
        Note note = new Note();
        note.setAuteur(auteur);
        note.setTitre(titre);
        note.setContenu(contenu);
        note.setDateCreation(LocalDateTime.now());
        note.setDateModification(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }
}
