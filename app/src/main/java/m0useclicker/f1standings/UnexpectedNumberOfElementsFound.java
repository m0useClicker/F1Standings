package m0useclicker.f1standings;

/**
 * Exception occurs when unexpected number of elements found.
 */
class UnexpectedNumberOfElementsFound extends Exception {
    private int numberOfItemsFound;
    private String cssQuery;

    public UnexpectedNumberOfElementsFound(String elementCssQuery, int numberOfItemsFound) {
        super("Unexpected number of elements (" + numberOfItemsFound + ") found by query: " + elementCssQuery);
        cssQuery = elementCssQuery;
        this.numberOfItemsFound = numberOfItemsFound;
    }

    public int getNumberOfItemsFound()
    {
        return numberOfItemsFound;
    }

    public String getCssQuery()
    {
        return cssQuery;
    }
}
