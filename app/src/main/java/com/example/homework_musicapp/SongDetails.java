package com.example.homework_musicapp;

public class SongDetails {
    private String name;
    private String description;
    private int avatar;
    private int songId;

    public SongDetails(String name, String description, int avatar, int songId) {
        this.name = name;
        this.description = description;
        this.avatar = avatar;
        this.songId = songId;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getAvatar() {
        return avatar;
    }

    public int getSongId() {
        return songId;
    }
}
