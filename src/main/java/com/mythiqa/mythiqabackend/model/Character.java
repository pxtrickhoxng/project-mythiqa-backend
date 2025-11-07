package com.mythiqa.mythiqabackend.model;

import com.mythiqa.mythiqabackend.converter.RichEditorConverter;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "characters")
public class Character {
    @Id
    @Column(name = "character_id")
    private String characterId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "nicknames")
    private String nicknames; // Will be stored as JSON array string

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "gender_custom")
    private String genderCustom;

    @Column(name = "race_or_species")
    private String raceOrSpecies;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "faction")
    private String faction;

    @Column(name = "personality_traits")
    private String personalityTraits; // Will be stored as JSON array string

    @Column(name = "speech_patterns")
    private String speechPatterns;

    @Column(name = "appearance", columnDefinition = "json")
    private String appearance;

    @Column(name = "backstory", columnDefinition = "text")
    private String backstory;

    @Column(name = "general_notes", columnDefinition = "json")
    @Convert(converter = RichEditorConverter.class)
    private RichEditor generalNotes; // Will store Tiptap JSON format similar to ChapterContent

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

    public enum Gender {
        MALE,
        FEMALE,
        CUSTOM
    }

    public Character() {}

    public Character(String characterId, String name, String image, String nicknames, Book book,
                    Integer age, Gender gender, String genderCustom, String raceOrSpecies,
                    String role, String faction, String personalityTraits, String speechPatterns,
                    String appearance, String backstory, RichEditor generalNotes) {
        this.characterId = characterId;
        this.name = name;
        this.image = image;
        this.nicknames = nicknames;
        this.book = book;
        this.age = age;
        this.gender = gender;
        this.genderCustom = genderCustom;
        this.raceOrSpecies = raceOrSpecies;
        this.role = role;
        this.personalityTraits = personalityTraits;
        this.speechPatterns = speechPatterns;
        this.appearance = appearance;
        this.backstory = backstory;
        this.generalNotes = generalNotes;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNicknames() {
        return nicknames;
    }

    public void setNicknames(String nicknames) {
        this.nicknames = nicknames;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getGenderCustom() {
        return genderCustom;
    }

    public void setGenderCustom(String genderCustom) {
        this.genderCustom = genderCustom;
    }

    public String getRaceOrSpecies() {
        return raceOrSpecies;
    }

    public void setRaceOrSpecies(String raceOrSpecies) {
        this.raceOrSpecies = raceOrSpecies;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public String getPersonalityTraits() {
        return personalityTraits;
    }

    public void setPersonalityTraits(String personalityTraits) {
        this.personalityTraits = personalityTraits;
    }

    public String getSpeechPatterns() {
        return speechPatterns;
    }

    public void setSpeechPatterns(String speechPatterns) {
        this.speechPatterns = speechPatterns;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public String getBackstory() {
        return backstory;
    }

    public void setBackstory(String backstory) {
        this.backstory = backstory;
    }

    public RichEditor getGeneralNotes() {
        return generalNotes;
    }

    public void setGeneralNotes(RichEditor generalNotes) {
        this.generalNotes = generalNotes;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
