package ro.mpp2024.domain;

public class Honey {
    private String sortiment;
    private String cantitate;
    private double concentratiePolenLaSuta;
    private double concentratieGlucozaLaSuta;
    private double concentratieFructozaLaSuta;

    public Honey(String sortiment, String cantitate,
                 double concentratiePolenLaSuta,
                 double concentratieGlucozaLaSuta,
                 double concentratieFructozaLaSuta) {
        this.sortiment = sortiment;
        this.cantitate = cantitate;
        this.concentratiePolenLaSuta = concentratiePolenLaSuta;
        this.concentratieGlucozaLaSuta = concentratieGlucozaLaSuta;
        this.concentratieFructozaLaSuta = concentratieFructozaLaSuta;
    }

    // Getters and setters
    public String getSortiment() { return sortiment; }
    public void setSortiment(String sortiment) { this.sortiment = sortiment; }

    public String getCantitate() { return cantitate; }
    public void setCantitate(String cantitate) { this.cantitate = cantitate; }

    public double getConcentratiePolenLaSuta() { return concentratiePolenLaSuta; }
    public void setConcentratiePolenLaSuta(double c) { this.concentratiePolenLaSuta = c; }

    public double getConcentratieGlucozaLaSuta() { return concentratieGlucozaLaSuta; }
    public void setConcentratieGlucozaLaSuta(double c) { this.concentratieGlucozaLaSuta = c; }

    public double getConcentratieFructozaLaSuta() { return concentratieFructozaLaSuta; }
    public void setConcentratieFructozaLaSuta(double c) { this.concentratieFructozaLaSuta = c; }

    @Override
    public String toString() {
        return "Honey{" +
                "sortiment='" + sortiment + '\'' +
                ", cantitate='" + cantitate + '\'' +
                ", polen=" + concentratiePolenLaSuta +
                ", glucoza=" + concentratieGlucozaLaSuta +
                ", fructoza=" + concentratieFructozaLaSuta +
                '}';
    }
}
