package m0useclicker.f1standings;

/**
 * Pilot class
 */
class Pilot {
    private int position;
    private String name;
    private String nationality;
    private String team;
    private int points;

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public String getTeam() {
        return team;
    }

    public int getPoints() {
        return points;
    }

    public Pilot(int position, String name, String nationality, String team, int points) {
        this.position = position;
        this.name = name;
        this.nationality = nationality;
        this.team = team;
        this.points = points;
    }
}
