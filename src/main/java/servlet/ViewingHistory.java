package servlet;

import java.time.LocalDateTime;

/**
 * 視聴履歴エンティティ
 */
public class ViewingHistory {
    private Integer id;
    private Integer studentId;
    private Integer videoId;
    private Integer watchedDuration;  // 秒単位
    private Boolean isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime lastWatchedAt;

    // コンストラクタ
    public ViewingHistory() {}

    public ViewingHistory(Integer studentId, Integer videoId) {
        this.studentId = studentId;
        this.videoId = videoId;
        this.watchedDuration = 0;
        this.isCompleted = false;
    }

    public ViewingHistory(Integer studentId, Integer videoId, Integer watchedDuration, Boolean isCompleted) {
        this.studentId = studentId;
        this.videoId = videoId;
        this.watchedDuration = watchedDuration;
        this.isCompleted = isCompleted;
    }

    // ゲッター・セッター
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getWatchedDuration() {
        return watchedDuration;
    }

    public void setWatchedDuration(Integer watchedDuration) {
        this.watchedDuration = watchedDuration;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastWatchedAt() {
        return lastWatchedAt;
    }

    public void setLastWatchedAt(LocalDateTime lastWatchedAt) {
        this.lastWatchedAt = lastWatchedAt;
    }

    @Override
    public String toString() {
        return "ViewingHistory{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", videoId=" + videoId +
                ", watchedDuration=" + watchedDuration +
                ", isCompleted=" + isCompleted +
                ", createdAt=" + createdAt +
                ", lastWatchedAt=" + lastWatchedAt +
                '}';
    }
}
