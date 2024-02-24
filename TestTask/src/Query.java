import java.time.LocalDate;

class Query {
    private int serviceId;
    private int variationId;
    private int questionTypeId;
    private int categoryId;
    private int subCategoryId;
    private char responseType;
    private LocalDate startDate;
    private LocalDate endDate;

    public Query(int serviceId, int variationId, int questionTypeId, int categoryId, int subCategoryId, char responseType, LocalDate startDate, LocalDate endDate) {
        this.serviceId = serviceId;
        this.variationId = variationId;
        this.questionTypeId = questionTypeId;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.responseType = responseType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getVariationId() {
        return variationId;
    }

    public void setVariationId(int variationId) {
        this.variationId = variationId;
    }

    public int getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(int questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public char getResponseType() {
        return responseType;
    }

    public void setResponseType(char responseType) {
        this.responseType = responseType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
