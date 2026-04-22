package entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "blocks")
    public class Block {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        // связь с версией страницы
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "page_version_id", nullable = false)
        private PageVersion pageVersion;

        // родительский блок (дерево)
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "parent_block_id")
        private Block parent;

        // дочерние блоки
        @OneToMany(mappedBy = "parent")
        private List<Block> children;

        // тип блока (text, header, code...)
        @Column(nullable = false)
        private String type;

        // порядок
        @Column(nullable = false)
        private int position;

        // JSON контент
        @Column(name = "content_json", columnDefinition = "jsonb", nullable = false)
        private String contentJson;

        // дата создания
        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @PrePersist
        public void onCreate() {
            createdAt = LocalDateTime.now();
        }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPageVersion(PageVersion pageVersion) {
        this.pageVersion = pageVersion;
    }

    public void setParent(Block parent) {
        this.parent = parent;
    }

    public void setChildren(List<Block> children) {
        this.children = children;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setContentJson(String contentJson) {
        this.contentJson = contentJson;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public PageVersion getPageVersion() {
        return pageVersion;
    }

    public Block getParent() {
        return parent;
    }

    public List<Block> getChildren() {
        return children;
    }

    public String getType() {
        return type;
    }

    public int getPosition() {
        return position;
    }

    public String getContentJson() {
        return contentJson;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
