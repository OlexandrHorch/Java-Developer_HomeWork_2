package entity;

public class Skill {
    private long id;
    private String area;
    private String level;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", area='" + area + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}