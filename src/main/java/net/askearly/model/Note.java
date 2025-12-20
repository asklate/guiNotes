package net.askearly.model;

public class Note {

    private long id;
    private String title;
    private String content;
    private String filename;
    private String createdDt;
    private String updatedDt;

    public Note(long id, String title, String content, String filename, String createdDt, String updatedDt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.filename = filename;
        this.createdDt = createdDt;
        this.updatedDt = updatedDt;
    }

    public Note() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(String createdDt) {
        this.createdDt = createdDt;
    }

    public String getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(String updatedDt) {
        this.updatedDt = updatedDt;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", filename='" + filename + '\'' +
                ", createdDt='" + createdDt + '\'' +
                ", updatedDt='" + updatedDt + '\'' +
                '}';
    }
}
