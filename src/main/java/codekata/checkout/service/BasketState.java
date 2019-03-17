package codekata.checkout.service;

/**
 * Enum to define the {@link codekata.checkout.domain.Basket} states
 */
public enum BasketState {

    IN_PROGRESS("IN_PROGRESS"), COMPLETE("COMPLETE");

    private final String state;

    BasketState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
