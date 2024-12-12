package prueba.bancodebogota.backend.domain.enums;

public enum TipoDocumento {
    CEDULA("C"),
    PASAPORTE("P");

    private final String value;

    TipoDocumento(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TipoDocumento findByValue(String value) {
        TipoDocumento result = null;
        for (TipoDocumento docType : values()) {
            System.out.println(docType.getValue());
            if (docType.getValue().equalsIgnoreCase(value)) {
                result = docType;
                break;
            }
        }
        return result;
    }
}
