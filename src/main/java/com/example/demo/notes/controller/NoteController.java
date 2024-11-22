package com.example.demo.notes.controller;

import com.example.demo.notes.entity.Note;
import com.example.demo.notes.services.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // Créer une nouvelle note
    @PostMapping("/note")
    public ResponseEntity<Note> createNote(@RequestBody Note noteRequest) {
        Note createdNote = noteService.createNote(
                noteRequest.getAuteur(),
                noteRequest.getTitre(),
                noteRequest.getContenu()
        );
        return ResponseEntity.ok(createdNote);
    }

    // Récupérer toutes les notes
    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    // Récupérer une note par ID
    @GetMapping("/note/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Optional<Note> note = noteService.getAllNotes()
                .stream()
                .filter(n -> n.getId().equals(id))
                .findFirst();

        return note.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mettre à jour une note par ID
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(
            @PathVariable Long id,
            @RequestBody Note noteRequest
    ) {
        Optional<Note> optionalNote = noteService.getAllNotes()
                .stream()
                .filter(note -> note.getId().equals(id))
                .findFirst();

        if (optionalNote.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Note noteToUpdate = optionalNote.get();
        noteToUpdate.setTitre(noteRequest.getTitre());
        noteToUpdate.setContenu(noteRequest.getContenu());
        noteToUpdate.setAuteur(noteRequest.getAuteur());

        // Persist the updated note
        Note updatedNote = noteService.createNote(
                noteToUpdate.getAuteur(),
                noteToUpdate.getTitre(),
                noteToUpdate.getContenu()
        );

        return ResponseEntity.ok(updatedNote);
    }

    // Supprimer une note par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        Optional<Note> optionalNote = noteService.getAllNotes()
                .stream()
                .filter(note -> note.getId().equals(id))
                .findFirst();

        if (optionalNote.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Supprimez simplement cette note dans le repository
        noteService.getAllNotes().remove(optionalNote.get());

        return ResponseEntity.noContent().build();
    }
}
