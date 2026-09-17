package entidades.enums;
public enum CategoriaProduto {
    BEBIDA_GELADA,
    BEBIDA_QUENTE,
    SALGADO,
    DOCE;

    public static CategoriaProduto fromDB(String valor) {
        return switch (valor) {
            case "Bebida Quente" -> BEBIDA_QUENTE;
            case "Bebida Fria"   -> BEBIDA_GELADA;
            case "Salgado"       -> SALGADO;
            case "Doce"          -> DOCE;
            default -> throw new IllegalArgumentException("Categoria desconhecida: " + valor);
        };
    }

    public String toDB() {
        return switch (this) {
            case BEBIDA_QUENTE -> "Bebida Quente";
            case BEBIDA_GELADA -> "Bebida Fria";
            case SALGADO       -> "Salgado";
            case DOCE          -> "Doce";
        };
    }

    }
