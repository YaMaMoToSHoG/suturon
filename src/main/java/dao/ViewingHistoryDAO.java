package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import servlet.DatabaseConnection;
import servlet.ViewingHistory;

/**
 * 視聴履歴を操作するDAO
 */
public class ViewingHistoryDAO {

    /**
     * 視聴履歴をIDで取得
     */
    public ViewingHistory findById(Integer id) {
        String sql = "SELECT id, student_id, video_id, watched_duration, is_completed, created_at, last_watched_at FROM viewing_history WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToViewingHistory(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生徒と動画IDで視聴履歴を取得
     */
    public ViewingHistory findByStudentIdAndVideoId(Integer studentId, Integer videoId) {
        String sql = "SELECT id, student_id, video_id, watched_duration, is_completed, created_at, last_watched_at FROM viewing_history WHERE student_id = ? AND video_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, videoId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToViewingHistory(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生徒の全視聴履歴を取得
     */
    public List<ViewingHistory> findByStudentId(Integer studentId) {
        String sql = "SELECT id, student_id, video_id, watched_duration, is_completed, created_at, last_watched_at FROM viewing_history WHERE student_id = ? ORDER BY last_watched_at DESC";
        List<ViewingHistory> histories = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                histories.add(mapResultSetToViewingHistory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return histories;
    }

    /**
     * 生徒が完了した動画を取得
     */
    public List<ViewingHistory> findCompletedByStudentId(Integer studentId) {
        String sql = "SELECT id, student_id, video_id, watched_duration, is_completed, created_at, last_watched_at FROM viewing_history WHERE student_id = ? AND is_completed = true ORDER BY last_watched_at DESC";
        List<ViewingHistory> histories = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                histories.add(mapResultSetToViewingHistory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return histories;
    }

    /**
     * 生徒が未完了の動画を取得
     */
    public List<ViewingHistory> findNotCompletedByStudentId(Integer studentId) {
        String sql = "SELECT id, student_id, video_id, watched_duration, is_completed, created_at, last_watched_at FROM viewing_history WHERE student_id = ? AND is_completed = false ORDER BY last_watched_at DESC";
        List<ViewingHistory> histories = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                histories.add(mapResultSetToViewingHistory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return histories;
    }

    /**
     * 動画の視聴者全員の履歴を取得
     */
    public List<ViewingHistory> findByVideoId(Integer videoId) {
        String sql = "SELECT id, student_id, video_id, watched_duration, is_completed, created_at, last_watched_at FROM viewing_history WHERE video_id = ? ORDER BY last_watched_at DESC";
        List<ViewingHistory> histories = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, videoId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                histories.add(mapResultSetToViewingHistory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return histories;
    }

    /**
     * 全視聴履歴を取得
     */
    public List<ViewingHistory> findAll() {
        String sql = "SELECT id, student_id, video_id, watched_duration, is_completed, created_at, last_watched_at FROM viewing_history ORDER BY last_watched_at DESC";
        List<ViewingHistory> histories = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                histories.add(mapResultSetToViewingHistory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return histories;
    }

    /**
     * 視聴履歴を新規登録
     */
    public boolean insert(ViewingHistory history) {
        String sql = "INSERT INTO viewing_history (student_id, video_id, watched_duration, is_completed) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, history.getStudentId());
            pstmt.setInt(2, history.getVideoId());
            pstmt.setInt(3, history.getWatchedDuration());
            pstmt.setBoolean(4, history.getIsCompleted());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        history.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 視聴履歴を更新
     */
    public boolean update(ViewingHistory history) {
        String sql = "UPDATE viewing_history SET watched_duration = ?, is_completed = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, history.getWatchedDuration());
            pstmt.setBoolean(2, history.getIsCompleted());
            pstmt.setInt(3, history.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 視聴履歴を削除
     */
    public boolean delete(Integer id) {
        String sql = "DELETE FROM viewing_history WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 動画の視聴ユーザー数を取得
     */
    public long countByVideoId(Integer videoId) {
        String sql = "SELECT COUNT(*) FROM viewing_history WHERE video_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, videoId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 動画の完了ユーザー数を取得
     */
    public long countCompletedByVideoId(Integer videoId) {
        String sql = "SELECT COUNT(*) FROM viewing_history WHERE video_id = ? AND is_completed = true";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, videoId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 生徒の学習進捗率を計算（完了した動画数 / 総視聴数）
     */
    public double getStudentProgressRate(Integer studentId) {
        String sql = "SELECT COUNT(CASE WHEN is_completed = true THEN 1 END) * 100.0 / COUNT(*) FROM viewing_history WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * 動画の完了率を計算
     */
    public double getVideoCompletionRate(Integer videoId) {
        String sql = "SELECT COUNT(CASE WHEN is_completed = true THEN 1 END) * 100.0 / COUNT(*) FROM viewing_history WHERE video_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, videoId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * ResultSetをViewingHistoryエンティティにマップ
     */
    private ViewingHistory mapResultSetToViewingHistory(ResultSet rs) throws SQLException {
        ViewingHistory history = new ViewingHistory();
        history.setId(rs.getInt("id"));
        history.setStudentId(rs.getInt("student_id"));
        history.setVideoId(rs.getInt("video_id"));
        history.setWatchedDuration(rs.getInt("watched_duration"));
        history.setIsCompleted(rs.getBoolean("is_completed"));
        Timestamp createdAtTs = rs.getTimestamp("created_at");
        if (createdAtTs != null) {
            history.setCreatedAt(createdAtTs.toLocalDateTime());
        }
        Timestamp lastWatchedAtTs = rs.getTimestamp("last_watched_at");
        if (lastWatchedAtTs != null) {
            history.setLastWatchedAt(lastWatchedAtTs.toLocalDateTime());
        }
        return history;
    }
}

