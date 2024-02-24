public enum CharscterCode {
    FIRST_ANSWER('P'),
    NEXT_ANSWER('N'),
    WAITING_TIMELINE('C'),
    QUERY('D');

    private final char code;

    CharscterCode(char code) {
        this.code = code;
    }

    public String getCodeToString() {
        return Character.toString(code);
        // or return "" + code;
    }
    public char getCode() {
        return code;
    }
}
