package jav.commented;

/**
 * Representa um registro identificado por um código de 9 dígitos.
 * Implementa equals() e hashCode() para suportar comparações e uso em Hash.
 */
public class Registro {
    private final int codigo; // código do registro (0..999_999_999)

    public Registro(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return String.format("%09d", codigo); // exibe com 9 dígitos
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;             // mesmo objeto
        if (!(o instanceof Registro)) return false; // checa tipo
        Registro r = (Registro) o;
        return this.codigo == r.codigo;         // compara código
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);        // hash baseado no código
    }
}
