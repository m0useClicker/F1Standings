package m0useclicker.f1standings;

/**
 * Exception occurs when unexpected number of elements found.
 */
class UnexpectedNumberOfElementsFound extends Exception {

    public UnexpectedNumberOfElementsFound(String elementCssQuery, int numberOfItemsFound) {
        super("Unexpected number of elements (" + numberOfItemsFound + ") found by query: " + elementCssQuery);
    }
}
