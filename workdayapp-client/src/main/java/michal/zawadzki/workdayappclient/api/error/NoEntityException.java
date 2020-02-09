package michal.zawadzki.workdayappclient.api.error;

public class NoEntityException extends RuntimeException{

    public NoEntityException(String message) {
        super(message);
    }

}