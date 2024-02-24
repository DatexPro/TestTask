import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


/**
 * Input
 * 7
 * C 1.1 8.15.1 P 15.10.2012 83
 * C 1 10.1 P 01.12.2012 65
 * C 1.1 5.5.1 P 01.11.2012 117
 * D 1.1 8 P 01.01.2012-01.12.2012
 * C 3 10.2 N 02.10.2012 100
 * D 1 * P 8.10.2012-20.11.2012
 * D 3 10 P 01.12.2012
 * Output
 * 83
 * 100
 * -
 */


public class WebhostingLogic {
    private final Scanner inInt = new Scanner(System.in);
    private final Scanner inLine = new Scanner(System.in);
    private final List<String> lines = new ArrayList<>();
//            Arrays.asList("C 1.1 8.15.1 P 15.10.2012 83",
//            "C 1 10.1 P 01.12.2012 65",
//            "C 1.1 5.5.1 P 01.11.2012 117",
//            "D 1.1 8 P 01.01.2012-01.12.2012",
//            "C 3 10.2 N 02.10.2012 100",
//            "D 1 * P 8.10.2012-20.11.2012",
//            "D 3 10 P 01.12.2012");
    private DateTimeFormatter[] formatters = {
            DateTimeFormatter.ofPattern("dd.MM.yyyy"),
            DateTimeFormatter.ofPattern("d.MM.yyyy"),
            DateTimeFormatter.ofPattern("dd.M.yyyy"),
            DateTimeFormatter.ofPattern("d.M.yyyy")
    };
    private int count = 100000;
    private boolean checkString = true;
    private final List<Record> recordList = new ArrayList<>();

    /**
     * Check new line if is not empty
     */
    public void checkLine() {
        int countLines = inInt.nextInt();
        int i = 0;
        while (checkString && i < countLines) {
            String line = inLine.nextLine();
            if (lines.size() <= count) {
                if (!line.isEmpty()) {
                    lines.add(line);
                } else {
                    checkString = false;
                }
            } else {
                System.out.println("Your line very big, please enter line less");
            }
            i++;
        }
        checkWaitingTimeline();
    }

    public void checkWaitingTimeline() {
        List<Object> characterList = new ArrayList<>();
        for (String s : lines) {
            characterList.add(List.of(s.split(" ")));
        }
        for (Object list : characterList) {
            if (list instanceof List<?>) {
                List<String> dataList = (List) list;
                if (dataList.get(0).equals(CharscterCode.WAITING_TIMELINE.getCodeToString())) {
                    recordList.add(saveRecord(dataList));
                } else if (dataList.get(0).equals(CharscterCode.QUERY.getCodeToString())) {
                    handleQuery(dataList);
                }
            }
        }
    }

    public void handleQuery(List<String> dataList) {
        String serv = dataList.get(1);
        int serviceId;
        int questionTypeId;
        LocalDate startDate = null;
        LocalDate endDate = null;
        int variationId = 0;
        int categoryId = 0;
        int subCategoryId = 0;
        if (serv.equals("*")) {
            serviceId = 0;
        } else if (checkInteger(serv)) {
            String[] variationRange = serv.split("\\.");
            serviceId = Integer.parseInt(variationRange[0]);
            if (variationRange.length == 2) {
                variationId = Integer.parseInt(variationRange[1]);
            }
        } else {
            serviceId = Integer.parseInt(serv);
        }
        String service = dataList.get(2);
        if (service.equals("*")) {
            questionTypeId = 0;
        } else if (checkInteger(service)) {
            String[] serviceRange = service.split("\\.");
            questionTypeId = Integer.parseInt(serviceRange[0]);
            if (serviceRange[1].equals("*")) {
                categoryId = 0;
            } else if (serviceRange.length == 2) {
                categoryId = Integer.parseInt(serviceRange[1]);
            }
            if (serviceRange.length == 3) {
                subCategoryId = Integer.parseInt(serviceRange[2]);
            }
        } else {
            questionTypeId = Integer.parseInt(dataList.get(2));
        }
        char responseType = dataList.get(3).charAt(0);
        if (checkDate(dataList.get(4))) {
            String[] dateRange = dataList.get(4).split("-");
            for (DateTimeFormatter formatter : formatters) {
                try {
                    startDate = LocalDate.parse(dateRange[0], formatter);
                    endDate = LocalDate.parse(dateRange[1], formatter);
                    break;
                } catch (DateTimeParseException e) {
                }
            }
        }
        int totalWaitingTime = 0;
        int matchingRecords = 0;
        for (Record record : recordList) {
            if (record.matchesQuery(new Query(serviceId, variationId, questionTypeId, categoryId, subCategoryId, responseType, startDate, endDate))) {
                totalWaitingTime += record.getTime();
                matchingRecords++;
            }
        }

        if (matchingRecords > 0) {
            int averageWaitingTime = totalWaitingTime / matchingRecords;
            System.out.println(averageWaitingTime);
        } else {
            System.out.println("-");
        }
    }

    public Record saveRecord(List<String> dataList) {
        String serv = dataList.get(1);
        int serviceId;
        int questionTypeId;
        int variationId = 0;
        int categoryId = 0;
        int subCategoryId = 0;
        int time = 0;
        if (serv.equals("*")) {
            serviceId = 0;
        } else if (checkInteger(serv)) {
            String[] variationRange = serv.split("\\.");
            serviceId = Integer.parseInt(variationRange[0]);
            if (variationRange.length == 2) {
                variationId = Integer.parseInt(variationRange[1]);
            }
        } else {
            serviceId = Integer.parseInt(serv);
        }
        String service = dataList.get(2);
        if (service.equals("*")) {
            questionTypeId = 0;
        } else if (checkInteger(service)) {
            String[] serviceRange = service.split("\\.");
            questionTypeId = Integer.parseInt(serviceRange[0]);
            if (serviceRange[1].equals("*")) {
                categoryId = 0;
            }
            if (serviceRange.length >= 2) {
                categoryId = Integer.parseInt(serviceRange[1]);
            }
            if (serviceRange.length == 3) {
                subCategoryId = Integer.parseInt(serviceRange[2]);
            }
        } else {
            questionTypeId = Integer.parseInt(dataList.get(2));
        }
        char response = dataList.get(3).charAt(0);
        String period = dataList.get(4);
        if (dataList.size() == 6) {
            time = Integer.parseInt(dataList.get(5));
        }
        return new Record(serviceId, variationId, questionTypeId, categoryId, subCategoryId, response, period, time);
    }

    public boolean checkDate(String period) {
        return period.contains("-");
    }

    public boolean checkInteger(String variation) {
       return variation.contains(".");
    }
}
