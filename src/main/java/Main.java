import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.TransactionDataFetcher;
import com.smallworld.data.Transaction;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        try {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("transactions.json");

            // Creating an ObjectMapper to parse the JSON data
            ObjectMapper objectMapper = new ObjectMapper();

            List<Transaction> transactions = objectMapper.readValue(inputStream, new TypeReference<List<Transaction>>() {
            });

            //list of transactions, you can pass it to TransactionDataFetcher
            TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);

            // Example usage of methods
            double totalAmount = dataFetcher.getTotalTransactionAmount();
            double totalTransactionAmountSentBy = dataFetcher.getTotalTransactionAmountSentBy("Aunt Polly");
            double maxAmount = dataFetcher.getMaxTransactionAmount();
            List<Transaction> top3Transactions = dataFetcher.getTop3TransactionsByAmount();
            long uniqueClientsCount = dataFetcher.countUniqueClients();
            boolean hasComplianceIssue = dataFetcher.hasOpenComplianceIssues("Tom Shelby");
            List<String> solvedIssueMessages = dataFetcher.getAllSolvedIssueMessages();
            Set<Integer> unsolvedIssueIds = dataFetcher.getUnsolvedIssueIds();
            Map<String, Transaction> transactionsByBeneficiary = dataFetcher.getTransactionsByBeneficiaryName();
            Optional<String> topSender = dataFetcher.getTopSender();

            // Print the results
            System.out.println("Total Transaction Amount: " + totalAmount);
            System.out.println("Total Transaction Amount Sent By: " + totalTransactionAmountSentBy);
            System.out.println("Max Transaction Amount: " + maxAmount);
            System.out.println("Top 3 Transactions by Amount: ");
            for (Transaction transaction : top3Transactions) {
                System.out.println(transaction);
            }
            System.out.println("Number of Unique Clients: " + uniqueClientsCount);
            System.out.println("Has Compliance Issue for Tom Shelby: " + hasComplianceIssue);
            System.out.println("Solved Issue Messages: " + solvedIssueMessages);
            System.out.println("Unsolved Issue IDs: " + unsolvedIssueIds);
            System.out.println("Transactions by Beneficiary Name: " + transactionsByBeneficiary);
            System.out.println("Top Sender: " + topSender.orElse("None"));

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
