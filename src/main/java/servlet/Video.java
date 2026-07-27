package servlet;

import java.time.LocalDateTime;

/**
 * 動画エンティティ
 */
public class Video {
    private Integer id;
    private Integer teacherId;
    private String title;
    private String description;
    private String filePath;
    private Integer duration;  // 秒単位
    private LocalDateTime uploadDate;

    // コンストラクタ
    public Video() {}

    public Video(String title, String description, String filePath, Integer duration, Integer teacherId) {
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.duration = duration;
        this.teacherId = teacherId;
    }

    public Video(Integer id, Integer teacherId, String title, String description, String filePath, Integer duration) {
        this.id = id;
        this.teacherId = teacherId;
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.duration = duration;
    }

    // ゲッター・セッター
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", teacherId=" + teacherId +
                ", title='" + title + '\'' +
                ", filePath='" + filePath + '\'' +
                ", duration=" + duration +
                ", uploadDate=" + uploadDate +
                '}';
    }
}
