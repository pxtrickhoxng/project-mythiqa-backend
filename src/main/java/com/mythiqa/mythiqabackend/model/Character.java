package com.mythiqa.mythiqabackend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mythiqa.mythiqabackend.converter.AppearanceJsonConverter;
import com.mythiqa.mythiqabackend.converter.ListToJsonConverter;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "characters")
public class Character {
    @Id
    @Column(name = "character_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String characterId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "nicknames")
    @Convert(converter = ListToJsonConverter.class)
    private List<String> nicknames; // Stored as JSON array

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "race_or_species")
    private String raceOrSpecies;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "faction")
    private String faction;

    @Column(name = "personality_traits")
    @Convert(converter = ListToJsonConverter.class)
    private List<String> personalityTraits;

    @Column(name = "speech_patterns")
    private String speechPatterns;

    @Column(name = "appearance", columnDefinition = "json")
    @Convert(converter = AppearanceJsonConverter.class)
    private Appearance appearance; // Stored as structured object

    @Column(name = "backstory", columnDefinition = "text")
    private String backstory;

    @Column(name = "general_notes", columnDefinition = "text")
    private String generalNotes;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

    public enum Gender {
        MALE,
        FEMALE,
        OTHER;

        @JsonCreator
        public static Gender fromValue(String value) {
            if (value == null) return null;
            switch (value.toLowerCase()) {
                case "male": return MALE;
                case "female": return FEMALE;
                case "other": return OTHER;
                default: return OTHER;
            }
        }

        @JsonValue
        public String toValue() {
            switch (this) {
                case MALE: return "male";
                case FEMALE: return "female";
                case OTHER: default: return "other";
            }
        }
    }

    public enum Role {
        PROTAGONIST,
        ANTAGONIST,
        SIDE_CHARACTER;

        @JsonCreator
        public static Role fromValue(String value) {
            if (value == null) return null;
            switch (value.toLowerCase()) {
                case "protagonist": return PROTAGONIST;
                case "antagonist": return ANTAGONIST;
                case "side_character": return SIDE_CHARACTER;
            }
            throw new IllegalArgumentException("Unknown value: " + value);
        }

        @JsonValue
        public String toValue() {
            switch (this) {
                case PROTAGONIST: return "protagonist";
                case ANTAGONIST: return "antagonist";
                case SIDE_CHARACTER: return "side_character";
            }
            throw new IllegalArgumentException("Unknown value: " + this);
        }
    }

    public Character() {}

    public Character(String characterId, String name, String image, List<String> nicknames, Book book,
                    Integer age, Gender gender, String raceOrSpecies,
                    Role role, String faction, List<String> personalityTraits, String speechPatterns,
                    Appearance appearance, String backstory, String generalNotes) {
        this.characterId = characterId;
        this.name = name;
        this.image = image;
        this.nicknames = nicknames;
        this.book = book;
        this.age = age;
        this.gender = gender;
        this.raceOrSpecies = raceOrSpecies;
        this.role = role;
        this.faction = faction;
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

    public List<String> getNicknames() {
        return nicknames;
    }

    public void setNicknames(List<String> nicknames) {
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

    public String getRaceOrSpecies() {
        return raceOrSpecies;
    }

    public void setRaceOrSpecies(String raceOrSpecies) {
        this.raceOrSpecies = raceOrSpecies;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public List<String> getPersonalityTraits() {
        return personalityTraits;
    }

    public void setPersonalityTraits(List<String> personalityTraits) {
        this.personalityTraits = personalityTraits;
    }

    public String getSpeechPatterns() {
        return speechPatterns;
    }

    public void setSpeechPatterns(String speechPatterns) {
        this.speechPatterns = speechPatterns;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public void setAppearance(Appearance appearance) {
        this.appearance = appearance;
    }

    public String getBackstory() {
        return backstory;
    }

    public void setBackstory(String backstory) {
        this.backstory = backstory;
    }

    public String getGeneralNotes() {
        return generalNotes;
    }

    public void setGeneralNotes(String generalNotes) {
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
