package com.smallworld;

import com.smallworld.data.Transaction;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionDataFetcherTest {
    @Test
    public void test_GetTotalTransactionAmount_ShouldReturnSum() {
        Transaction transaction1 = Mockito.mock(Transaction.class);
        Transaction transaction2 = Mockito.mock(Transaction.class);
        Transaction transaction3 = Mockito.mock(Transaction.class);

        // Set up the behavior of the mocked objects
        when(transaction1.getAmount()).thenReturn(500.0);
        when(transaction2.getAmount()).thenReturn(300.0);
        when(transaction3.getAmount()).thenReturn(700.0);

        // Create a list of mock transactions
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        // Create an instance of TransactionDataFetcher with the list of mock transactions
        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);

        // Call the method under test
        double totalAmount = dataFetcher.getTotalTransactionAmount();

        // Assert the result with a delta value of 0.001 (acceptable precision error for double values)
        assertEquals(1500.0, totalAmount, 0.001);
    }

    @Test
    public void test_GetTotalTransactionAmountSentBy_ShouldReturnTotalAmount() {
        // Create a list of mock Transaction objects
        Transaction transaction1 = mock(Transaction.class);
        when(transaction1.getSenderFullName()).thenReturn("John Doe");
        when(transaction1.getAmount()).thenReturn(100.0);

        Transaction transaction2 = mock(Transaction.class);
        when(transaction2.getSenderFullName()).thenReturn("Jane Smith");
        when(transaction2.getAmount()).thenReturn(200.0);

        Transaction transaction3 = mock(Transaction.class);
        when(transaction3.getSenderFullName()).thenReturn("John Doe");
        when(transaction3.getAmount()).thenReturn(300.0);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        // Create the TransactionDataFetcher instance with the mocked list of transactions
        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);

        // Test the method
        double totalAmount = dataFetcher.getTotalTransactionAmountSentBy("John Doe");
        assertEquals(400.0, totalAmount, 0.001); // 100.0 + 300.0 = 400.0
    }


    @Test
    public void test_GetMaxTransactionAmount_ShouldReturnHighestTransaction() {
        Transaction transaction1 = Mockito.mock(Transaction.class);
        Transaction transaction2 = Mockito.mock(Transaction.class);
        Transaction transaction3 = Mockito.mock(Transaction.class);
        when(transaction1.getAmount()).thenReturn(100.0);
        when(transaction2.getAmount()).thenReturn(200.0);
        when(transaction3.getAmount()).thenReturn(300.0);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);
        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        double maxAmount = dataFetcher.getMaxTransactionAmount();
        assertEquals(300.0, maxAmount, 0.001);
    }

    @Test
    public void test_CountUniqueClients_ShouldReturnCount() {
        Transaction transaction1 = Mockito.mock(Transaction.class);
        Transaction transaction2 = Mockito.mock(Transaction.class);
        Transaction transaction3 = Mockito.mock(Transaction.class);

        when(transaction1.getSenderFullName()).thenReturn("John Doe");
        when(transaction1.getBeneficiaryFullName()).thenReturn("Jane Smith");
        when(transaction2.getSenderFullName()).thenReturn("John Doe");
        when(transaction2.getBeneficiaryFullName()).thenReturn("Alex Johnson");
        when(transaction3.getSenderFullName()).thenReturn("Jane Smith");
        when(transaction3.getBeneficiaryFullName()).thenReturn("Tom Johnson");

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);
        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        long uniqueClientsCount = dataFetcher.countUniqueClients();
        assertEquals(4, uniqueClientsCount);
    }

    @Test
    public void test_HasOpenComplianceIssues_ClientWithOpenIssue() {
        //case-I
        Transaction transaction1 = Mockito.mock(Transaction.class);
        Transaction transaction2 = Mockito.mock(Transaction.class);
        Transaction transaction3 = Mockito.mock(Transaction.class);

        when(transaction1.getSenderFullName()).thenReturn("John Doe");
        when(transaction1.getBeneficiaryFullName()).thenReturn("Jane Smith");
        when(transaction1.getIssueId()).thenReturn(1);
        when(transaction1.isIssueSolved()).thenReturn(false);
        when(transaction2.getSenderFullName()).thenReturn("John Doe");
        when(transaction2.getBeneficiaryFullName()).thenReturn("Alex Johnson");
        when(transaction2.getIssueId()).thenReturn(null);
        when(transaction2.isIssueSolved()).thenReturn(true);
        when(transaction3.getSenderFullName()).thenReturn("Jane Smith");
        when(transaction3.getBeneficiaryFullName()).thenReturn("Tom Johnson");
        when(transaction3.getIssueId()).thenReturn(2);
        when(transaction3.isIssueSolved()).thenReturn(false);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);
        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        boolean hasOpenIssue = dataFetcher.hasOpenComplianceIssues("John Doe");
        assertTrue(hasOpenIssue);
    }

    @Test
    public void test_HasOpenComplianceIssues_ClientWithoutOpenIssue() {
        //CASE-II
        Transaction transaction1 = Mockito.mock(Transaction.class);
        Transaction transaction2 = Mockito.mock(Transaction.class);

        when(transaction1.getSenderFullName()).thenReturn("John Doe");
        when(transaction1.getBeneficiaryFullName()).thenReturn("Jane Smith");
        when(transaction1.getIssueId()).thenReturn(null);
        when(transaction1.isIssueSolved()).thenReturn(true);
        when(transaction2.getSenderFullName()).thenReturn("John Doe");
        when(transaction2.getBeneficiaryFullName()).thenReturn("Alex Johnson");
        when(transaction2.getIssueId()).thenReturn(1);
        when(transaction2.isIssueSolved()).thenReturn(true);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        boolean hasOpenIssue = dataFetcher.hasOpenComplianceIssues("John Doe");
        assertFalse(hasOpenIssue);
    }

    @Test
    public void test_GetTransactionsByBeneficiaryName_ShouldReturnIndexedBeneficiaryName() {
        Transaction transaction1 = Mockito.mock(Transaction.class);
        Transaction transaction2 = Mockito.mock(Transaction.class);
        Transaction transaction3 = Mockito.mock(Transaction.class);

        when(transaction1.getBeneficiaryFullName()).thenReturn("Jane Smith");
        when(transaction2.getBeneficiaryFullName()).thenReturn("Alex Johnson");
        when(transaction3.getBeneficiaryFullName()).thenReturn("Tom Johnson");

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);
        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        Map<String, Transaction> transactionsByBeneficiaryName = dataFetcher.getTransactionsByBeneficiaryName();
        Map<String, Transaction> expectedTransactionsByBeneficiaryName = new HashMap<>();
        expectedTransactionsByBeneficiaryName.put("Jane Smith", transaction1);
        expectedTransactionsByBeneficiaryName.put("Alex Johnson", transaction2);
        expectedTransactionsByBeneficiaryName.put("Tom Johnson", transaction3);

        assertEquals(expectedTransactionsByBeneficiaryName, transactionsByBeneficiaryName);
    }

    @Test
    public void test_GetUnsolvedIssueIds_ShouldReturnIds() {
        Transaction transaction1 = Mockito.mock(Transaction.class);
        Transaction transaction2 = Mockito.mock(Transaction.class);
        Transaction transaction3 = Mockito.mock(Transaction.class);

        when(transaction1.getIssueId()).thenReturn(1);
        when(transaction1.isIssueSolved()).thenReturn(false);
        when(transaction2.getIssueId()).thenReturn(null);
        when(transaction2.isIssueSolved()).thenReturn(true);
        when(transaction3.getIssueId()).thenReturn(3);
        when(transaction3.isIssueSolved()).thenReturn(false);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);
        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        Set<Integer> unsolvedIssueIds = dataFetcher.getUnsolvedIssueIds();
        Set<Integer> expectedUnsolvedIssueIds = new HashSet<>();
        expectedUnsolvedIssueIds.add(1);
        expectedUnsolvedIssueIds.add(3);
        assertEquals(expectedUnsolvedIssueIds, unsolvedIssueIds);
    }

    @Test
    public void test_GetAllSolvedIssueMessages_ShouldReturnMessages() {
        Transaction transaction1 = Mockito.mock(Transaction.class);
        Transaction transaction2 = Mockito.mock(Transaction.class);
        Transaction transaction3 = Mockito.mock(Transaction.class);

        when(transaction1.isIssueSolved()).thenReturn(true);
        when(transaction1.getIssueMessage()).thenReturn("Issue 1 solved");
        when(transaction2.isIssueSolved()).thenReturn(false); // Issue not solved
        when(transaction3.isIssueSolved()).thenReturn(true);
        when(transaction3.getIssueMessage()).thenReturn("Issue 3 solved");

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);
        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        List<String> solvedIssueMessages = dataFetcher.getAllSolvedIssueMessages();
        List<String> expectedSolvedIssueMessages = Arrays.asList("Issue 1 solved", "Issue 3 solved");
        assertEquals(expectedSolvedIssueMessages, solvedIssueMessages);
    }


    @Test
    public void testGetTop3TransactionsByAmount_ShouldReturnTopTransactions() {
        Transaction transaction1 = Mockito.mock(Transaction.class);
        Transaction transaction2 = Mockito.mock(Transaction.class);
        Transaction transaction3 = Mockito.mock(Transaction.class);

        when(transaction1.getAmount()).thenReturn(500.0);
        when(transaction2.getAmount()).thenReturn(300.0);
        when(transaction3.getAmount()).thenReturn(700.0);
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);
        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        List<Transaction> top3Transactions = dataFetcher.getTop3TransactionsByAmount();
        List<Transaction> expectedTop3Transactions = transactions.stream()
                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
                .limit(3)
                .collect(Collectors.toList());

        assertEquals(expectedTop3Transactions, top3Transactions);
    }

    @Test
    public void test_GetTopSender_ShouldReturnNameOfTopSender() {
        Transaction transaction1 = Mockito.mock(Transaction.class);
        Transaction transaction2 = Mockito.mock(Transaction.class);
        Transaction transaction3 = Mockito.mock(Transaction.class);

        when(transaction1.getSenderFullName()).thenReturn("John");
        when(transaction2.getSenderFullName()).thenReturn("Jane");
        when(transaction3.getSenderFullName()).thenReturn("John");
        when(transaction1.getAmount()).thenReturn(500.0);
        when(transaction2.getAmount()).thenReturn(300.0);
        when(transaction3.getAmount()).thenReturn(700.0);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);
        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        Optional<String> topSender = dataFetcher.getTopSender();
        assertEquals(Optional.of("John"), topSender);
    }

}

