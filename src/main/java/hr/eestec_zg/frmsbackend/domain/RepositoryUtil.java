package hr.eestec_zg.frmsbackend.domain;

class RepositoryUtil {

    private static final String LIKE_OPERATOR = "%";

    static String likeTerm(String parameter) {
        if (parameter == null) {
            return LIKE_OPERATOR;
        }
        return LIKE_OPERATOR + parameter.toLowerCase() + LIKE_OPERATOR;
    }

}
