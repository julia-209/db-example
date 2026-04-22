package entity;

import entity.Block;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "page_versions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"page_id", "version_number"}))
public class PageVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", nullable = false)
    private Page page;

    @Column(name = "version_number")
    private int versionNumber;

    @Column(name = "title_snapshot", nullable = false)
    private String titleSnapshot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "change_comment")
    private String changeComment;

    @OneToMany(mappedBy = "pageVersion")
    private List<Block> blocks;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public void setTitleSnapshot(String titleSnapshot) {
        this.titleSnapshot = titleSnapshot;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setChangeComment(String changeComment) {
        this.changeComment = changeComment;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public Long getId() {
        return id;
    }

    public Page getPage() {
        return page;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public String getTitleSnapshot() {
        return titleSnapshot;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getChangeComment() {
        return changeComment;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
