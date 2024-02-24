import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Record {
    private int serviceId;
    private int variationId;
    private int questionTypeId;
    private int categoryId;
    private int subCategoryId;
    private char responseType;
    private String date;
    private int time;

    public Record(int serviceId, int variationId, int questionTypeId, int categoryId, int subCategoryId, char responseType, String date, int time) {
        this.serviceId = serviceId;
        this.variationId = variationId;
        this.questionTypeId = questionTypeId;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.responseType = responseType;
        this.date = date;
        this.time = time;
    }

    public boolean matchesQuery(Query query) {
        if (query.getServiceId() != 0 && serviceId != query.getServiceId()) {
            return false;
        }
        if (query.getVariationId() != 0 && variationId != query.getVariationId()) {
            return false;
        }
        if (query.getQuestionTypeId() != 0 && questionTypeId != query.getQuestionTypeId()) {
            return false;
        }
        if (query.getCategoryId() != 0 && this.categoryId != query.getCategoryId()) {
            return false;
        }
        if (query.getSubCategoryId() != 0 && this.subCategoryId != query.getSubCategoryId()) {
            return false;
        }
        if (this.responseType != query.getResponseType()) {
            return false;
        }
        if (query.getStartDate() != null && query.getEndDate() != null) {
            LocalDate recordDate = LocalDate.parse(this.date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            LocalDate queryStartDate = query.getStartDate();
            LocalDate queryEndDate = query.getEndDate();
            if (!recordDate.isEqual(queryStartDate) && !recordDate.isEqual(queryEndDate) &&
                    (recordDate.isBefore(queryStartDate) || recordDate.isAfter(queryEndDate))) {
                return false;
            }
        }
        return true;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

