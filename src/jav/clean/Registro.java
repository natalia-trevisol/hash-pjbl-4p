package jav.clean;

public class Registro {
    private final int codigo;

    public Registro(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return String.format("%09d", codigo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof jav.clean.Registro)) return false;
        jav.clean.Registro r = (jav.clean.Registro) o;
        return this.codigo == r.codigo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);
    }
}

