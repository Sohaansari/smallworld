package com.smallworld;

import com.smallworld.data.Transaction;

import java.util.*;
import java.util.stream.Collectors;

public class TransactionDataFetcher {

    private final List<Transaction> transactions;

    public TransactionDataFetcher(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() {
        double totalAmount = 0.0;
        for (Transaction transaction : transactions) {
            totalAmount += transaction.getAmount();
        }
        return totalAmount;
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        double totalAmount = 0.0;
        for (Transaction transaction : transactions) {
            if (transaction.getSenderFullName().equals(senderFullName)) {
                totalAmount += transaction.getAmount();
            }
        }
        return totalAmount;
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() {
        return transactions.stream().mapToDouble(Transaction::getAmount).max().orElse(0.0);
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() {
        //initializing hashset for unique client names
        Set<String> uniqueClientsCount = new HashSet<>();
        for (Transaction transaction : transactions) {
            uniqueClientsCount.add(transaction.getSenderFullName());
            uniqueClientsCount.add(transaction.getBeneficiaryFullName());
        }
        return uniqueClientsCount.size();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {
        for (Transaction transaction : transactions) {
            if (clientFullName.equals(transaction.getSenderFullName())
                    || clientFullName.equals(transaction.getBeneficiaryFullName())) {
                if (transaction.getIssueId() != null && !transaction.isIssueSolved()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, Transaction> getTransactionsByBeneficiaryName() {
        Map<String, Transaction> transactionsByBeneficiaryName = new HashMap<>();
        for (Transaction transaction : transactions) {
            String beneficiaryName = transaction.getBeneficiaryFullName();
            transactionsByBeneficiaryName.put(beneficiaryName, transaction);
        }
        return transactionsByBeneficiaryName;
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {
        //using hashset for unique UnsolvedIssueIds
        Set<Integer> unsolvedIssueIds = new HashSet<>();
        for (Transaction transaction : transactions) {
            if (transaction.getIssueId() != null && !transaction.isIssueSolved()) {
                unsolvedIssueIds.add(transaction.getIssueId());
            }
        }
        return unsolvedIssueIds;
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() {
        List<String> solvedIssueMessages = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.isIssueSolved()) {
                solvedIssueMessages.add(transaction.getIssueMessage());
            }
        }
        return solvedIssueMessages;
    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    public List<Transaction> getTop3TransactionsByAmount() {
        return transactions.stream().sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
                .limit(3).collect(Collectors.toList());
    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    public Optional<String> getTopSender() {
        Map<String, Double> topSenderAmount = transactions.stream().collect(
                Collectors.groupingBy(Transaction::getSenderFullName,
                        Collectors.summingDouble(Transaction::getAmount)));

        return topSenderAmount.entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

}
