package net.askearly.model;

public class Journal {

    private long id;
    private String content;
    private String createdDt;

    public Journal() {

    }

    public Journal(long id, String content, String createdDt) {
        this.id = id;
        this.content = content;
        this.createdDt = createdDt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(String createdDt) {
        this.createdDt = createdDt;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdDt='" + createdDt + '\'' +
                '}';
    }
}
